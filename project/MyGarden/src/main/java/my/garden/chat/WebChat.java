package my.garden.chat;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value="/chatcontrol", configurator=HttpSessionSetter.class)
public class WebChat {

	// �쎒�냼耳� Session�씠 �븘�슂�븿. �씠 Session�� http�쓽 session怨쇰뒗 �떎瑜� �븷�빞. Session�씠 �겢�씪�씠�뼵�듃�쓽 �젙蹂대�� �떞怨좎엳�뼱.
	private static Set<Session> clientSet = Collections.synchronizedSet(new HashSet<Session>()); 
	// �굹以묒뿉 �룞湲고솕 泥섎━瑜� �븯湲� �쐞�빐�꽌 synchronizedSet�쓣 �궗�슜�븿(for臾몄씠 �룎怨좎엳�뒗 以묒뿏 add�� remove媛� �뿀�슜�릺吏� �븡�룄濡� �븯湲� �쐞�빐.)

	public static Map<String, Session> clients = Collections.synchronizedMap(new HashMap<>()); 
	
	@OnMessage
	public void onMessage(String message, Session session) throws Exception{
		/*HttpSession hsession = (HttpSession)ec.getUserProperties().get("httpSession");
		String id = (String)hsession.getAttribute("loginId");
		String name = (String)hsession.getAttribute("loginName"); // 吏�湲� �젒�냽�븳 �궗�엺�씠 �늻援ъ씤吏� �븣 �닔 �엳�쓬*/
		String[] arr = message.split(" : ");
		String id = arr[0];
		String msg = arr[1];
		Session client = clients.get(id); // 愿�由ъ옄�굹 �겢�씪�씠�뼵�듃 紐⑤몢 client�뿉 �떞湲곌쾶 �뤌
		Session admin = clients.get("admin123@naver.com");
	
		if(client != admin) { // 留뚯빟 client媛� 愿�由ъ옄媛� �븘�땲�씪硫�(利�, �냼鍮꾩옄媛� 硫붿꽭吏�瑜� 蹂대궦�떎硫�)
			admin.getBasicRemote().sendText(id + " : " + msg); // client�쓽 硫붿꽭吏��뒗 愿�由ъ옄�뿉寃� �쟾�떖�릺�뼱�빞 �븯怨�
		}else if(client == admin) { // 留뚯빟 client媛� 愿�由ъ옄�씪硫�(利�, 愿�由ъ옄媛� 硫붿꽭吏�瑜� 蹂대궦�떎硫�)
			String cid = arr[2];
			Session toclient = clients.get(cid);
			toclient.getBasicRemote().sendText(msg);
		}	
	}

	@OnOpen // 硫붿꽌�뱶�쓽 �씠由꾩� �븘臾대젃寃뚮굹 吏��뼱�룄 �릺吏�留� �뼱�끂�뀒�씠�뀡 OnOpen�� 瑗� 遺숈뿬以섏빞 �븿.
	public void onOpen(Session session, EndpointConfig ec) {
		HttpSession hsession = (HttpSession)ec.getUserProperties().get("httpSession");
		String id = (String)hsession.getAttribute("loginId");
		String name = (String)hsession.getAttribute("loginName");
		System.out.println(name + "媛� �젒�냽�븿");
		clients.put(id, session);
		clientSet.add(session);
	}

	@OnClose
	public void onClose(Session session) { 
		clientSet.remove(session);
	}

	@OnError
	public void onError(Throwable t) {
		t.printStackTrace();
	}
}

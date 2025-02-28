package my.garden.schedule;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Component;

import my.garden.daoImpl.ShoppingDAOImpl;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ScheduleTask {

  private ThreadPoolTaskScheduler scheduler;

  @Autowired
  ShoppingDAOImpl sdao;

  private int count = 0;

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public void stopScheduler() {
    scheduler.shutdown();
  }

  public void startScheduler(String orderNo) {
    scheduler = new ThreadPoolTaskScheduler();
    scheduler.initialize();
    // �뒪耳�伊대윭媛� �떆�옉�릺�뒗 遺�遺�
    scheduler.schedule(getRunnable(orderNo), getTrigger());
  }

  private Runnable getRunnable(String orderNo) {
    return () -> {
      try {
        count++;
        System.out.println("�뒪耳�以꾨윭 �솕�쓬 + : " + count);
        if (count == 2) {
          sdao.completeShipping(Long.parseLong(orderNo));
          count = 0;
          this.stopScheduler();
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    };
  }

  private Trigger getTrigger() {
    // �옉�뾽 二쇨린 �꽕�젙
    return new PeriodicTrigger(10, TimeUnit.MINUTES);
  }

}
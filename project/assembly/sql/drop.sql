drop table visit;
drop table freeboard;
drop SEQUENCE freeboard_seq;
drop table freecomments;
drop SEQUENCE freecomments_seq;
drop table questionboard;
drop SEQUENCE questionboard_seq;
drop table questioncomments;
drop SEQUENCE questioncomments_seq;
drop table noticeboard;
drop SEQUENCE noticeboard_seq;
drop table noticecomments;
drop SEQUENCE noticecomments_seq;
drop table burger;
drop table set_menu;
drop table mc_morning;
drop table happy_meal;
drop table happy;
drop table snack_side;
drop table mc_cafe;
drop table beverage;
drop table desert;
drop table game;
drop table members;
drop sequence member_seq;
drop table study;
drop table supportboard;
drop sequence support_seq;

COMMIT;

select * from user_tables;

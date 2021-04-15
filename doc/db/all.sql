drop table if exists `chapter`;
create table `chapter`(
    `id` char(8) not null comment 'ID',
    `course_id` char(8) comment '课程id',
    `name` varchar(100) comment '名称',
    primary key (`id`)
) engine=innodb default charset=utf8mb4 comment '大章';


insert into `chapter` (id, course_id, name) values ('00000000','00000000','测试大章一');
insert into `chapter` (id, course_id, name) values ('00000001','00000000','测试大章二');
insert into `chapter` (id, course_id, name) values ('00000002','00000000','测试大章3');
insert into `chapter` (id, course_id, name) values ('00000003','00000000','测试大章4');
insert into `chapter` (id, course_id, name) values ('00000004','00000000','测试大章5');
insert into `chapter` (id, course_id, name) values ('00000005','00000000','测试大章6');
insert into `chapter` (id, course_id, name) values ('00000006','00000000','测试大章7');
insert into `chapter` (id, course_id, name) values ('00000007','00000000','测试大章8');
insert into `chapter` (id, course_id, name) values ('00000008','00000000','测试大章9');
insert into `chapter` (id, course_id, name) values ('00000009','00000000','测试大章十');
insert into `chapter` (id, course_id, name) values ('00000010','00000000','测试大章十一');
insert into `chapter` (id, course_id, name) values ('00000011','00000000','测试大章十二');
insert into `chapter` (id, course_id, name) values ('00000012','00000000','测试大章十三');


drop table if exists `section`;
create table `section`(
    `id` char(8) not null comment 'ID',
    `title` char(8) comment '标题',
    `course_id` char(8) comment '课程|course.id',
    `chapter_id` char(8) comment '大章|chapter.id',
    `video` varchar(200) comment '视频',
    `time` INT comment '时长|单位秒',
    `charge` char(1) comment '收费|C 收费; F 免费',
    `sort` INT comment '顺序',
    `create_at` DATETIME(3) comment '创建时间',
    `update_at` DATETIME(3) comment '修改时间',
    primary key (`id`)
) engine=innodb default charset=utf8mb4 comment '小节';

insert into `section`(id,title,course_id,chapter_id,video,time,charge,sort,create_at,update_at)
values ('00000001','测试小节01','00000000','00000000','',500,'F',1,now(),now());



















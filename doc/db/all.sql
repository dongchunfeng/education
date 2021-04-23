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


drop table if exists `course`;
create table `course`(
    `id` char(8) not null default '' comment 'ID',
    `name` varchar(50) not null comment '名称',
    `summary` varchar(5000) comment '概述',
    `time` int default 0 comment '时长|单位秒',
    `price` decimal (8,2) not null default 0.00 comment '价格(元)',
    `image` varchar(100) comment '封面',
    `level` char(1) not null comment '级别|枚举[CourseLevelEnum]: ONE("1","初级"),TOW("2","中级"),THREE("3","高级")',
    `charge` char(1) comment '收费|枚举[CourseChargeEnum]: CHARGE("C", "收费"),FREE("F", "免费")',
    `status` char(1) comment '状态|枚举[CourseStatusEnum]: PUBLISH("P", "发布"),DRAFT("D", "草稿")',
    `enroll` integer default 0 comment '报名数',
    `sort` int comment '顺序',
    `create_at` DATETIME(3) comment '创建时间',
    `update_at` DATETIME(3) comment '修改时间',
    primary key (`id`)
) engine=innodb default charset=utf8mb4 comment '课程表';

drop table if exists `category`;
create table `category`(
    `id` char(8) not null default '' comment 'ID',
    `parent` char(8) not null comment '父id',
    `name` varchar(50) comment '名称',
    `sort` int default 0 comment '顺序',
    primary key (`id`)
) engine=innodb default charset=utf8mb4 comment '分类表';

insert into category (id, parent, name, sort) values ('00000100','00000000','前端技术',100);
insert into category (id, parent, name, sort) values ('00000101','00000100','html/css',101);
insert into category (id, parent, name, sort) values ('00000102','00000100','javascript',102);
insert into category (id, parent, name, sort) values ('00000103','00000100','vue.js',103);
insert into category (id, parent, name, sort) values ('00000104','00000100','react.js',104);
insert into category (id, parent, name, sort) values ('00000105','00000100','angular.js',105);
insert into category (id, parent, name, sort) values ('00000106','00000100','node.js',106);
insert into category (id, parent, name, sort) values ('00000107','00000100','jquery',107);
insert into category (id, parent, name, sort) values ('00000108','00000100','小程序',108);
insert into category (id, parent, name, sort) values ('00000200','00000000','后端技术',200);
insert into category (id, parent, name, sort) values ('00000201','00000200','java',201);
insert into category (id, parent, name, sort) values ('00000202','00000200','spring cloud',202);
insert into category (id, parent, name, sort) values ('00000203','00000200','spring boot',203);
insert into category (id, parent, name, sort) values ('00000204','00000200','ssm',204);
insert into category (id, parent, name, sort) values ('00000205','00000200','python',205);
insert into category (id, parent, name, sort) values ('00000206','00000200','爬虫',206);
insert into category (id, parent, name, sort) values ('00000300','00000000','移动开发',300);
insert into category (id, parent, name, sort) values ('00000301','00000300','android',301);
insert into category (id, parent, name, sort) values ('00000302','00000300','ios',302);
insert into category (id, parent, name, sort) values ('00000303','00000300','react native',303);
insert into category (id, parent, name, sort) values ('00000304','00000300','ionic',304);
insert into category (id, parent, name, sort) values ('00000400','00000000','前沿技术',400);
insert into category (id, parent, name, sort) values ('00000401','00000400','微服务',401);
insert into category (id, parent, name, sort) values ('00000402','00000400','区块链',402);
insert into category (id, parent, name, sort) values ('00000403','00000400','机器学习',403);
insert into category (id, parent, name, sort) values ('00000404','00000400','深度学习',404);
insert into category (id, parent, name, sort) values ('00000405','00000400','数据分析&挖掘',405);
insert into category (id, parent, name, sort) values ('00000500','00000000','云计算&大数据',500);
insert into category (id, parent, name, sort) values ('00000501','00000500','大数据',501);
insert into category (id, parent, name, sort) values ('00000502','00000500','hadoop',502);

drop table if exists `course_category`;
create table `course_category`(
    `id` char(8) not null default '' comment 'ID',
    `course_id` char(8) comment '课程|course.id',
    `category_id` char(8) comment '分类|category.id',
    primary key (`id`)
) engine=innodb default charset=utf8mb4 comment '课程分类';

drop table if exists `course_content`;
create table `course_content`(
    `id` char(8) not null default '' comment '课程id',
    `content` mediumtext not null comment '课程内容',
    primary key (`id`)
) engine=innodb default charset=utf8mb4 comment '课程内容';

drop table if exists `teacher`;
create table `teacher`(
    `id` char(8) not null default '' comment 'id',
    `name` varchar(50) not null comment '姓名',
    `nickname` varchar(100) comment '昵称',
    `image` varchar(100) comment '头像',
    `position` varchar(100) comment '职位',
    `motto` varchar(100) comment '座右铭',
    `intro` varchar(500) comment '简介',
    primary key (`id`)
) engine=innodb default charset=utf8mb4 comment '讲师';

alter table `course` add column (`teacher_id` char(8) comment '讲师|teacher.id' );



/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2023/5/31 22:53:21                           */
/*==============================================================*/

create database cms;

use cms;

drop table if exists brand;

drop table if exists category;

drop table if exists com_specification;

drop table if exists commodity;

drop table if exists customer;

drop table if exists order_detail;

drop table if exists orders;

drop table if exists orders_goods;

drop table if exists specification;

drop table if exists supplier;

/*==============================================================*/
/* Table: brand                                                 */
/*==============================================================*/
create table brand
(
   bra_name             varchar(20) not null,
   bra_website          varchar(100) not null,
   bra_photo            varchar(200),
   primary key (bra_name)
);

/*==============================================================*/
/* Table: category                                              */
/*==============================================================*/
create table category
(
   cat_name             varchar(20) not null,
   cat_photo            varchar(200) not null,
   primary key (cat_name)
);

/*==============================================================*/
/* Table: com_specification                                     */
/*==============================================================*/
create table com_specification
(
   co_no                char(12) not null,
   spe_no               varchar(10) not null,
   com_spe_description  varchar(300) not null,
   primary key (co_no, spe_no)
);

/*==============================================================*/
/* Table: commodity                                             */
/*==============================================================*/
create table commodity
(
   co_no                char(12) not null,
   bra_name             varchar(20) not null,
   cat_name             varchar(20) not null,
   co_name              varchar(70) not null,
   co_type              varchar(20) not null,
   co_jl                varchar(10) not null,
   co_market_prose      numeric(10,2) not null,
   co_sale_prise        numeric(10,2) not null,
   co_cost_prise        numeric(10,2) not null,
   co_photo             varchar(200),
   co_introduce         varchar(200),
   co_num               int not null,
   primary key (co_no)
);

/*==============================================================*/
/* Table: customer                                              */
/*==============================================================*/
create table customer
(
   cus_no               varchar(20) not null,
   cus_name             varchar(20) not null,
   cus_tel              varchar(11) not null,
   cus_site             varchar(200) not null,
   cus_pwd              varchar(30) not null,
   primary key (cus_no)
);

/*==============================================================*/
/* Table: order_detail                                          */
/*==============================================================*/
create table order_detail
(
   co_no                char(12) not null,
   ord_no               varchar(20) not null,
   ord_det_no           varchar(20) not null,
   ord_det_prise        numeric(16,2) not null,
   ord_det_num          int not null,
   ord_det_sumprise     numeric(16,2) not null,
   primary key (co_no, ord_no, ord_det_no)
);

/*==============================================================*/
/* Table: orders                                                */
/*==============================================================*/
create table orders
(
   ord_no               varchar(20) not null,
   cus_no               varchar(20) not null,
   ord_time             date not null,
   ord_sumprise         numeric(16,2) not null,
   primary key (ord_no)
);

/*==============================================================*/
/* Table: orders_goods                                          */
/*==============================================================*/
create table orders_goods
(
   co_no                char(12) not null,
   su_no                varchar(20) not null,
   ord_go_num           int not null,
   ord_go_time          datetime not null,
   primary key (co_no, su_no)
);

/*==============================================================*/
/* Table: specification                                         */
/*==============================================================*/
create table specification
(
   spe_no               varchar(10) not null,
   spe_name             varchar(100) not null,
   primary key (spe_no)
);

/*==============================================================*/
/* Table: supplier                                              */
/*==============================================================*/
create table supplier
(
   su_no                varchar(20) not null,
   su_name              varchar(20) not null,
   su_contact           varchar(20) not null,
   su_con_tel           varchar(11) not null,
   su_introduce         varchar(200),
   su_pwd               varchar(30) not null,
   primary key (su_no)
);

alter table com_specification add constraint FK_Relationship_2 foreign key (co_no)
      references commodity (co_no) on delete restrict on update restrict;

alter table com_specification add constraint FK_Relationship_3 foreign key (spe_no)
      references specification (spe_no) on delete restrict on update restrict;

alter table commodity add constraint FK_Relationship_1 foreign key (bra_name)
      references brand (bra_name) on delete restrict on update restrict;

alter table commodity add constraint FK_Relationship_4 foreign key (cat_name)
      references category (cat_name) on delete restrict on update restrict;

alter table order_detail add constraint FK_Relationship_6 foreign key (co_no)
      references commodity (co_no) on delete restrict on update restrict;

alter table order_detail add constraint FK_Relationship_7 foreign key (ord_no)
      references orders (ord_no) on delete restrict on update restrict;

alter table orders add constraint FK_Relationship_8 foreign key (cus_no)
      references customer (cus_no) on delete restrict on update restrict;

alter table orders_goods add constraint FK_Relationship_10 foreign key (su_no)
      references supplier (su_no) on delete restrict on update restrict;

alter table orders_goods add constraint FK_Relationship_9 foreign key (co_no)
      references commodity (co_no) on delete restrict on update restrict;


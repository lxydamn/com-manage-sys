
-- create trigger supply_after
-- after insert on orders_goods for each row
-- update commodity set commodity.co_num= commodity.co_num+new.ord_go_num
-- where commodity.co_no=new.co_no
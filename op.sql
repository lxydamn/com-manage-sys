
-- create trigger supply_after
-- after insert on orders_goods for each row
-- update commodity set commodity.co_num= commodity.co_num+new.ord_go_num
-- where commodity.co_no=new.co_no

-- create trigger modify_commodity_num
--     before insert on order_detail for each row
--     begin
--         if((select co_num from commodity where co_no=NEW.co_no) > NEW.ord_det_num) then
--             update commodity set co_num=co_num-NEW.ord_det_num
--                 where co_no=NEW.co_no;
--         else
--             SIGNAL SQLSTATE 'HY000' SET MESSAGE_TEXT = '商品库存数量不足';
--         end if;

--     end;
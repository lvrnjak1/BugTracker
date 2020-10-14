alter table projects add column code varchar(255);
update projects set code = '123abc' where id = 1;
update projects set code = '456xyz' where id = 2;
update projects set code = '000asd' where id = 3;
insert into category values (1,'Smartfony',true);
insert into category values (2,'Laptopy',true);
insert into category values (3,'Pozdespoly komputerowe',false);
select * from category;

insert into "user" values (1,true,'123@123.pl','Uzytkownik','Nr1','haslo123','www.zdjecie.pl');
insert into "user" values (2,false,'321@321.com','Uzytkownik2','Nr2','12321','www.test.pl');
insert into "user" values (3,false,'test@test.gg','User','Testowy','qwerty123','www.www.pl');
select * from "user";

insert into product  values(1,'Ekran "6,5cala 6/128GB"','Samsung Galaxy S20',null,'www.picture.url','skusku',true,1);
insert into product values (2,'Procesor Intel, dysk tysionc, cztery ka xD','MacBook Air M1',null,'macbook.url','skuu',true,2);
insert into product values(3,'Swietnie kopie bitkojna','GeForceRTX4090',null,'picture','sku321',false,3);
select * from product;

insert into opinion values (1,'[{"advantage": "Super jest"}]',5,null,1,1);
insert into opinion values (2,'[{"advantage": "No tak srednio bym powiedzial"}]',1,'[{"disadvantage": "zdjecieSlabegoProduktu".pl}]',2,1);
insert into opinion values (3,'[{"advantage": "Polecam serdecznie. Pieknie dogrzewa w domku"}]',5,null,3,2);
select * from opinion;

insert into suggestion values (1,'No ja to bym dorzucil piaty aparat',1,null,1,2);
insert into suggestion values (2,'Moze troche drozej? Bo musze tylko 3 miesiace pracowac zeby kupic',2,null,1,2);
insert into suggestion values (3,'Przecietny mu**yn wykopalby tego bitkojna szybciej. Wiencej mocy!',3,null,1,3);
select * from suggestion;

insert into review values (1,'Bedzie i 6 aparat','done',1);
insert into review values (2,'Zwiekszamy cene, portfel wytrzyma','done',2);
insert into review values (3,'W takim razie prosze wyposazyc sie w mu**yna','declined',3);
select * from review;

update suggestion set review_review_id=1 where suggestion_id=1;
update suggestion set review_review_id=2 where suggestion_id=2;
update suggestion set review_review_id=3 where suggestion_id=3;
select * from suggestion;

insert into category_products values (1,1);
insert into category_products values (2,2);
insert into category_products values (3,3);
select * from category_products;
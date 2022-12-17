insert into category values (default, 'Smartfony',true);
insert into category values (default, 'Laptopy',true);
insert into category values (default, 'Pozdespoly komputerowe',false);

insert into "user" values (default, true,'admin@admin.com','Uzytkownik','Nr1','$2y$10$TpEZyGw9kU4bBDhTgDjU4ebSFi0j3yoBz/5IOoFLLxd.h9lY2x3ge','www.zdjecie.pl'); -- Haslo: admin
insert into "user" values (default, false,'321@321.com','Uzytkownik2','Nr2','12321','www.test.pl');
insert into "user" values (default, false,'test@test.gg','User','Testowy','qwerty123','www.www.pl');

insert into product  values(default, 'Ekran "6,5cala 6/128GB"','Samsung Galaxy S20',null,'www.picture.url','skusku',true,1);
insert into product values (default, 'Procesor Intel, dysk tysionc, cztery ka xD','MacBook Air M1',null,'macbook.url','skuu',true,2);
insert into product values(default, 'Swietnie kopie bitkojna','GeForceRTX4090',null,'picture','sku321',false,3);

insert into opinion values (default, '["Super jest"]',5,null,1,1);
insert into opinion values (default, '["No tak srednio bym powiedzial"]',1,'["zdjecieSlabegoProduktu.pl"]',2,1);
insert into opinion values (default, '["Polecam serdecznie. Pieknie dogrzewa w domku"]',5,null,3,2);

insert into suggestion values (default, 'No ja to bym dorzucil piaty aparat',1,null,1,2);
insert into suggestion values (default, 'Moze troche drozej? Bo musze tylko 3 miesiace pracowac zeby kupic',2,null,1,2);
insert into suggestion values (default, 'Przecietny mu**yn wykopalby tego bitkojna szybciej. Wiencej mocy!',3,null,1,3);

insert into review values (default, 'Bedzie i 6 aparat','done',1);
insert into review values (default, 'Zwiekszamy cene, portfel wytrzyma','done',2);
insert into review values (default, 'W takim razie prosze wyposazyc sie w mu**yna','declined',3);

update suggestion set review_review_id=1 where suggestion_id=1;
update suggestion set review_review_id=2 where suggestion_id=2;
update suggestion set review_review_id=3 where suggestion_id=3;

insert into category_products values (1,1);
insert into category_products values (2,2);
insert into category_products values (3,3);

update opinion set product_id=1 where opinion_id=1;
update opinion set product_id=2 where opinion_id=2;
update opinion set product_id=3 where opinion_id=3;

update opinion set user_id=1 where opinion_id=1;
update opinion set user_id=2 where opinion_id=2;
update opinion set user_id=3 where opinion_id=3;
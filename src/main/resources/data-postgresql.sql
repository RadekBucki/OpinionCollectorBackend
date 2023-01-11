insert into category values (default, 'Smartphones',true);
insert into category values (default, 'Laptops',true);
insert into category values (default, 'Hardware',false);

insert into "user" values (default, true,'admin@admin.com','Uzytkownik','Nr1','$2a$10$Azj6vAZcW7lAKcC5zhQNqurMi3wZHivjLXoWrFq6XyqQqgMeSKEPy','https://img2.looper.com/img/gallery/the-best-episodes-of-breaking-bad/l-intro-1609890074.jpg'); -- Password: admin123 | jwtSecret: mySecretKey
insert into "user" values (default, false,'321@321.com','Uzytkownik2','Nr2','$2a$10$4sMsUd3mETOBM2Zd109Z6.X6v0E0Q7Mul5si3Us0ZQLGtsCXz1qIa','https://i0.wp.com/applauss.com/wp-content/uploads/2017/02/jesse-pinkman-quiz.jpg?fit=1200%2C800&ssl=1'); -- Password: 12345678 | jwtSecret: mySecretKey
insert into "user" values (default, false,'test@test.gg','User','Testowy','$2a$10$ccpn9Ue5AtrJoIeFVLDLo.HiuPqNbJH08BM0lbxCvr4vE5Qtta7bC','https://i.ytimg.com/vi/V94Y1CqiBfA/hqdefault.jpg'); -- Password: qwerty123 | jwtSecret: mySecretKey

insert into product  values(default, 'Ekran "6,5cala 6/128GB"','Samsung Galaxy S20',5.0,'https://www.mistershopking.com/8877/samsung-galaxy-s20-g980f-8128gb-dual-sim-grey-italia.jpg','skusku',true,1);
insert into product values (default, 'Procesor Intel, 1000GB, 4K display','MacBook Air M1',1.0,'https://www.macworld.com/wp-content/uploads/2021/03/macbook-air-2108-hero2-100779122-orig-7.jpeg?quality=50&strip=all','skuu',true,2);
insert into product values(default, 'Mines bitcoin perfectly','GeForceRTX4090',5.0,'https://techstory.in/wp-content/uploads/2021/12/Newsycanuse-1024x626.jpg','sku321',false,3);

insert into opinion values (default, '["Its great"]',5,null,1,1);
insert into opinion values (default, '["Not so good I would say"]',1,'https://i.ytimg.com/vi/nGHpgArkKOw/maxresdefault.jpg',2,1);
insert into opinion values (default, '["Really recommend. Heats all my house."]',5,null,3,2);

insert into suggestion values (default, 'I would add 5th camera',1,null,1,2);
insert into suggestion values (default, 'Maybe more expensive? I have to work only 3 months to buy it...',2,null,1,2);
insert into suggestion values (default, 'Average **N word here** would mine that bitcoin faster. More power!',3,null,1,3);

insert into review values (default, '6th camera on its way','done',1);
insert into review values (default, 'Increasing the price, wallet will resist','done',2);
insert into review values (default, 'So please equip yourself with **N word here**','declined',3);

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
insert into book
(
   name
  ,author_id
  ,genre_id
)
values
(
   'Лезвие бритвы'
  ,(select id from author a where a.name = 'Иван' and a.lastname = 'Ефремов')
  ,(select id from genre g where g.name = 'Научная фантастика')
);
insert into book
(
   name
  ,author_id
  ,genre_id
)
values
(
   'Собачье сердце'
  ,(select id from author a where a.name = 'Михаил' and a.lastname = 'Булгаков')
  ,(select id from genre g where g.name = 'Повесть')
);
insert into book
(
   name
  ,author_id
  ,genre_id
)
values
(
   'Белый клык'
  ,(select id from author a where a.name = 'Джек' and a.lastname = 'Лондон')
  ,(select id from genre g where g.name = 'Повесть')
);
insert into book
(
   name
  ,author_id
  ,genre_id
)
values
(
   'Морфий'
  ,(select id from author a where a.name = 'Михаил' and a.lastname = 'Булгаков')
  ,(select id from genre g where g.name = 'Рассказ')
);
insert into book
(
   name
  ,author_id
  ,genre_id
)
values
(
   'Роковые яйца'
  ,(select id from author a where a.name = 'Михаил' and a.lastname = 'Булгаков')
  ,(select id from genre g where g.name = 'Повесть')
);
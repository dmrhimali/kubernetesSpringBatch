-- https://github.com/jdaarevalo/docker_postgres_with_data/blob/main/sql/fill_tables.sql
set session my.number_of_members = '1000';
set session my.number_of_departments = '3';
INSERT INTO public."member"
SELECT
       id,
       (array['Jill', 'Joe', 'Jack', 'Greg', 'Silvester', 'Tom', 'Toby', 'Scott', 'Alex'])[floor(random() * 9 + 1)],
       concat('00', id%current_setting('my.number_of_departments')::int)
FROM GENERATE_SERIES(1, current_setting('my.number_of_members')::int) as id;


-- set session my.number_of_coutries = '10';
-- select id, concat('Country ', id)
-- FROM GENERATE_SERIES(1, current_setting('my.number_of_coutries')::int) as id;
--
-- id concat
-- -- ----------
-- 1  Country 1
-- 2  Country 2
-- 3  Country 3
-- 4  Country 4
-- 5  Country 5
-- 6  Country 6
-- 7  Country 7
-- 8  Country 8
-- 9  Country 9
-- 10 Country 10

-- set session my.number_of_coutries = '10';
-- select id, concat('Country ', id%3)
-- FROM GENERATE_SERIES(1, current_setting('my.number_of_coutries')::int) as id;
--
-- id concat
-- -- ---------
-- 1  Country 1
-- 2  Country 2
-- 3  Country 0
-- 4  Country 1
-- 5  Country 2
-- 6  Country 0
-- 7  Country 1
-- 8  Country 2
-- 9  Country 0
-- 10 Country 1

-- set session my.number_of_members = '10';
-- set session my.number_of_departments = '10';
-- select id, concat('00', id%current_setting('my.number_of_departments')::int)
-- FROM GENERATE_SERIES(1, current_setting('my.number_of_coutries')::int) as id;
--
-- id concat
-- -- ------
-- 1  001
-- 2  002
-- 3  003
-- 4  004
-- 5  005
-- 6  006
-- 7  007
-- 8  008
-- 9  009
-- 10 000

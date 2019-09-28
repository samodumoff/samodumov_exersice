# samodumov_exersice

Перед запуском программы необходимо выполнить следующие команды в SQL Shell(psql):

CREATE ROLE user_samodumov WITH PASSWORD 'user_samodumov';

ALTER ROLE user_samodumov WITH LOGIN;

CREATE DATABASE samodumov_db OWNER user_samodumov;

\c samodumov_db user_samodumov

user_samodumov

CREATE TABLE objects (
	ID 				serial 	PRIMARY KEY,
	PRODUCT_NAME 	TEXT 				NOT NULL 
);

CREATE TABLE purchase (
	ID 				serial 	PRIMARY KEY,
	PRODUCT_NAME 	TEXT 				NOT NULL,
	PRODUCT_COUNT 	INT 				NOT NULL,
	PRICE 			double precision 	NOT NULL,
	DATE_PURCHASE 	TIMESTAMP 
);

CREATE TABLE store (
	ID 				serial 	PRIMARY KEY,
	PRODUCT_NAME 	TEXT 				NOT NULL,
	PRICE 			double precision 	NOT NULL
);

CREATE TABLE sale (
	ID 				serial 	PRIMARY KEY,
	PRODUCT_NAME 	TEXT 				NOT NULL,
	PRODUCT_COUNT 	INT 				NOT NULL,
	PRICE 			double precision 	NOT NULL,
	DATE_PURCHASE 	TIMESTAMP NOT NULL,
	PROFIT double precision NOT NULL
);
Класс для запуска программы Program, package - Program (Program.Program);
При запуске программы необходимо ввести порт, по которому PostgreSQL принимает запросы. База данных должна работать локально. 

Список доступных команд для работы программы (регистр важен):

NEWPRODUCT name_product - например NEWPRODUCT iphone;
PURCHASE name_product count_products product_price date_of_purchase - например PURCHASE iphone 1 1000 01.01.2000;
DEMAND name_product count_products product_price date_of_demand - например DEMAND iphone 1 5000 02.01.2000;
SALESREPORT name_product date_of_report - например SALESREPORT iphone 03.01.2000;

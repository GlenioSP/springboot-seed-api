CREATE SCHEMA product AUTHORIZATION sa;

CREATE TABLE product(
  id IDENTITY NOT NULL PRIMARY KEY,
  product_id VARCHAR(50),
  description VARCHAR(100),
  image_url VARCHAR(100),
  price NUMBER(13,2) NOT NULL,
);
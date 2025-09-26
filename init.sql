CREATE USER IF NOT EXISTS 'franchise_user'@'%' IDENTIFIED WITH mysql_native_password BY '';
GRANT ALL PRIVILEGES ON franchises.* TO 'franchise_user'@'%';
FLUSH PRIVILEGES;

CREATE DATABASE IF NOT EXISTS franchises;
USE franchises;

CREATE TABLE franchise (
                           id CHAR(36) PRIMARY KEY,
                           name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE branch (
                        id CHAR(36) PRIMARY KEY,
                        franchise_id CHAR(36) NOT NULL,
                        name VARCHAR(255) NOT NULL,
                        CONSTRAINT fk_branch_franchise FOREIGN KEY (franchise_id) REFERENCES franchise(id),
                        CONSTRAINT uq_branch_name UNIQUE (franchise_id, name)
);

CREATE TABLE product (
                         id CHAR(36) PRIMARY KEY,
                         name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE branch_product (
                                branch_id CHAR(36) NOT NULL,
                                product_id CHAR(36) NOT NULL,
                                stock INT NOT NULL DEFAULT 0,
                                PRIMARY KEY (branch_id, product_id),
                                CONSTRAINT fk_branchproduct_branch FOREIGN KEY (branch_id) REFERENCES branch(id),
                                CONSTRAINT fk_branchproduct_product FOREIGN KEY (product_id) REFERENCES product(id)
);

CREATE USER IF NOT EXISTS 'franchise_user'@'%' IDENTIFIED WITH mysql_native_password BY '';
GRANT ALL PRIVILEGES ON franchises.* TO 'franchise_user'@'%';
FLUSH PRIVILEGES;

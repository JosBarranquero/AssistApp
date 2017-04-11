-- User that will manage the App database
CREATE USER 'assistapp'@'localhost' IDENTIFIED BY 'health123';

DROP DATABASE IF EXISTS AssistApp_DB;
CREATE DATABASE AssistApp_DB CHARACTER SET utf8 COLLATE utf8_general_ci;

-- Granting all privileges to this user
GRANT ALL PRIVILEGES ON AssistApp_DB.* TO 'assistapp'@'localhost';
FLUSH PRIVILEGES;

USE AssistApp_DB;

-- Table definitions
CREATE TABLE Users (
    id INTEGER NOT NULL AUTO_INCREMENT,
    idDoc VARCHAR(9) NOT NULL,
    password VARCHAR(64) NOT NULL,
    name VARCHAR(25) NOT NULL,
    surname VARCHAR(45) NOT NULL,
    type ENUM("patient", "nurse") NOT NULL,
    img VARCHAR(60) NOT NULL,
    email VARCHAR(40) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE Messages (
    id INTEGER NOT NULL AUTO_INCREMENT,
    content VARCHAR(200) NOT NULL,
    sender INTEGER NOT NULL,
    receiver INTEGER NOT NULL,
    date DATETIME NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (sender) REFERENCES Users (id) ON UPDATE CASCADE ON DELETE RESTRICT,
    FOREIGN KEY (receiver) REFERENCES Users (id) ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE assists (
    idPat INTEGER NOT NULL,
    idNur INTEGER NOT NULL,
    PRIMARY KEY (idPat, idNur),
    FOREIGN KEY (idPat) REFERENCES Users (id) ON UPDATE CASCADE ON DELETE RESTRICT,
    FOREIGN KEY (idNur) REFERENCES Users (id) ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE MedData (
    id INTEGER NOT NULL AUTO_INCREMENT,
    idPat INTEGER UNIQUE NOT NULL,
    sex ENUM("m", "f") NOT NULL,
    nationality VARCHAR(30) NOT NULL,
    residence VARCHAR(100) NOT NULL,
    job VARCHAR(30) NOT NULL,
    smoker BOOLEAN NOT NULL,
    alcohol BOOLEAN NOT NULL,
    drugs BOOLEAN NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (idPat) REFERENCES Users (id) ON UPDATE CASCADE ON DELETE RESTRICT
);

 CREATE TABLE MedRecord (
    id INTEGER NOT NULL AUTO_INCREMENT,
    idData INTEGER NOT NULL,
    reason VARCHAR(75) NOT NULL,
    antecedents VARCHAR(140) NOT NULL,
    hospitalised BOOLEAN NOT NULL,
    date DATETIME NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (idData) REFERENCES MedData (id) ON UPDATE CASCADE ON DELETE RESTRICT
);

INSERT INTO Users (idDoc, password, name, surname, type, img, email) SELECT '12345678A', SHA2('Aa123456', 256), 'José Antonio', 'Barranquero', 2, 'profiles/Jose.jpg', 'joseantbarranquero@gmail.com';

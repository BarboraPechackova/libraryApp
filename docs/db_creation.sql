DROP TABLE IF EXISTS Reservation;
DROP TYPE IF EXISTS ReservationState;
DROP TABLE IF EXISTS Book_loan;
DROP TABLE IF EXISTS Role;
DROP TABLE IF EXISTS Book_cover;
DROP TABLE IF EXISTS Profile_picture;
DROP TABLE IF EXISTS Picture;
DROP TABLE IF EXISTS Rating;
DROP TABLE IF EXISTS Book;
DROP TYPE IF EXISTS BookState;
DROP TABLE IF EXISTS Library_user;



CREATE TABLE Library_user(
    "id" SERIAL PRIMARY KEY,
    "username" VARCHAR(255) NOT NULL,
    "password" VARCHAR(255) NOT NULL,
    "first_name" VARCHAR(255) NOT NULL,
    "surname" VARCHAR(255) NOT NULL,
    "email" VARCHAR(255) NOT NULL,
    "phone" VARCHAR(15) NOT NULL,
    "bank_account" VARCHAR(255) NULL
);

CREATE TYPE BookState AS ENUM ('VOLNA', 'REZERVOVANA', 'VYPUJCENA');

CREATE TABLE Book (
    "id" SERIAL PRIMARY KEY,
    "name" VARCHAR(255) NOT NULL,
    "author" VARCHAR(255) NOT NULL,
    "description" TEXT NOT NULL,
    "price" INTEGER NOT NULL,
    "isbn" VARCHAR(255) NOT NULL,
    "state" BookState NOT NULL DEFAULT 'VOLNA',
    "visible" BOOLEAN NOT NULL,
    "id_user" INT NOT NULL,
    FOREIGN KEY (id_user) REFERENCES Library_user(id)
);

CREATE TABLE Picture(
    "id" SERIAL PRIMARY KEY,
    "upload_ts" TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "picture" bytea NOT NULL,
    "type" VARCHAR(255) NOT NULL
);

CREATE TABLE Rating(
    "id" SERIAL PRIMARY KEY,
    "points" SMALLINT NOT NULL,
    "note" TEXT NOT NULL,
    "id_user" INT NOT NULL,
    "id_book" INT NOT NULL,
    FOREIGN KEY (id_user) REFERENCES Library_user(id),
    FOREIGN KEY (id_book) REFERENCES Book(id)
);

CREATE TABLE Profile_picture(
    "id" SERIAL PRIMARY KEY,
    "ts_from" TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
    "ts_to" TIMESTAMP(0) WITHOUT TIME ZONE,
    "id_picture" INT NOT NULL,
    "id_user" INT NOT NULL,
    FOREIGN KEY (id) REFERENCES Picture(id),
    FOREIGN KEY (id_user) REFERENCES Library_user(id)
);

CREATE TABLE Book_cover(
    "id" SERIAL PRIMARY KEY,
    "id_picture" INT NOT NULL,
    "id_book" INT NOT NULL,
    FOREIGN KEY (id) REFERENCES Picture(id),
    FOREIGN KEY (id_book) REFERENCES Book(id)
);

CREATE TABLE Role(
    "id" SERIAL PRIMARY KEY,
    "role" VARCHAR(255) NOT NULL,
    "id_user" INT NOT NULL,
    FOREIGN KEY (id_user) REFERENCES Library_user(id)
);

CREATE TABLE Book_loan(
    "id" SERIAL PRIMARY KEY,
    "date_from" DATE NOT NULL,
    "date_to" DATE NOT NULL,
    "price" INTEGER NOT NULL,
    "returned" BOOLEAN NOT NULL,
    "id_user" INT NOT NULL,
    "id_book" INT NOT NULL,
    FOREIGN KEY (id_user) REFERENCES Library_user(id),
    FOREIGN KEY (id_book) REFERENCES Book(id)
);

CREATE TYPE ReservationState AS ENUM ('AKTIVNI', 'ZRUSENA', 'VYDANA');

CREATE TABLE Reservation(
    "id" SERIAL PRIMARY KEY,
    "reservation_ts" TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "state" ReservationState NOT NULL DEFAULT 'AKTIVNI',
    "id_user" INT NOT NULL,
    "id_book" INT NOT NULL,
    FOREIGN KEY (id_user) REFERENCES Library_user(id),
    FOREIGN KEY (id_book) REFERENCES Book(id)
);

ALTER TABLE Library_user
    ADD COLUMN "password" VARCHAR(255) NOT NULL;

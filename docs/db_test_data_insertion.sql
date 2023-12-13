INSERT INTO Library_user (username, first_name, surname, email, phone, bank_account) VALUES
                                                                                         ('john_doe', 'John', 'Doe', 'john.doe@example.com', '1234567890', '123-456-789'),
                                                                                         ('jane_smith', 'Jane', 'Smith', 'jane.smith@example.com', '2345678901', '234-567-890'),
                                                                                         ('alice_wonder', 'Alice', 'Wonder', 'alice.wonder@example.com', '3456789012', '345-678-901'),
                                                                                         ('bob_builder', 'Bob', 'Builder', 'bob.builder@example.com', '4567890123', '456-789-012'),
                                                                                         ('charlie_chaplin', 'Charlie', 'Chaplin', 'charlie.chaplin@example.com', '5678901234', '567-890-123'),
                                                                                         ('diana_prince', 'Diana', 'Prince', 'diana.prince@example.com', '6789012345', '678-901-234'),
                                                                                         ('edward_scissorhands', 'Edward', 'Scissorhands', 'edward.scissorhands@example.com', '7890123456', '789-012-345');
INSERT INTO Book (name, author, description, price, isbn, state, visible, id_user) VALUES
                                                                                       ('Book Title 1', 'Author Name', 'Description here', 100, 'ISBN001', 'VOLNA', TRUE, 1),
                                                                                       ('Book Title 2', 'Author Name', 'Description here', 150, 'ISBN002', 'VOLNA', TRUE, 2),
                                                                                       ('Mystery of the Moon', 'Luna Light', 'A fascinating lunar mystery.', 120, 'ISBN003', 'VOLNA', TRUE, 3),
                                                                                       ('The Future of Tech', 'Alan Turing', 'Exploring future technologies.', 200, 'ISBN004', 'VOLNA', TRUE, 4),
                                                                                       ('Journey Through Time', 'H.G. Wells', 'An adventurous time travel story.', 180, 'ISBN005', 'VOLNA', TRUE, 5),
                                                                                       ('Art of the Ages', 'Leonardo Da Vinci', 'Exploring historical art.', 250, 'ISBN006', 'VOLNA', TRUE, 1),
                                                                                       ('Secrets of the Sea', 'Marie Tharp', 'Unveiling the oceanic mysteries.', 160, 'ISBN007', 'VOLNA', TRUE, 2);

-- INSERT INTO Picture (upload_ts, picture, type) VALUES
--                                                    (NOW(), '\\x', 'image/jpeg'),
--                                                    (NOW(), '\\x', 'image/png'),
--                                                    (NOW(), '\\x', 'image/jpeg'),
--                                                    (NOW(), '\\x', 'image/jpeg'),
--                                                    (NOW(), '\\x', 'image/jpeg'),
--                                                    (NOW(), '\\x', 'image/png'),
--                                                    (NOW(), '\\x', 'image/png');

INSERT INTO Rating (points, note, id_user, id_book) VALUES
                                                        (5, 'Great book', 1, 1),
                                                        (4, 'Good read', 2, 2),
                                                        (3, 'Average read', 3, 3),
                                                        (2, 'Could be better', 4, 4),
                                                        (4, 'Really enjoyed it', 5, 5),
                                                        (5, 'Outstanding', 1, 6),
                                                        (1, 'Not my type', 2, 7);

-- INSERT INTO Profile_picture (ts_from, ts_to, id_picture, id_user) VALUES
--                                                                       (NOW(), NULL, 1, 1),
--                                                                       (NOW(), NULL, 2, 2),
--                                                                       (NOW(), '\\x', 'image/jpeg'),
--                                                                       (NOW(), '\\x', 'image/jpeg'),
--                                                                       (NOW(), '\\x', 'image/jpeg'),
--                                                                       (NOW(), '\\x', 'image/png'),
--                                                                       (NOW(), '\\x', 'image/png');
--
-- INSERT INTO Book_cover (id_picture, id_book) VALUES
--                                                  (1, 1),
--                                                  (2, 2),
--                                                  (3, 3),
--                                                  (4, 4),
--                                                  (5, 5),
--                                                  (6, 6),
--                                                  (7, 7);

INSERT INTO Role (role, id_user) VALUES
                                     ('ADMIN', 1),
                                     ('USER', 2),
                                     ('USER', 3),
                                     ('USER', 4),
                                     ('USER', 5),
                                     ('USER', 6),
                                     ('USER', 7);

INSERT INTO Book_loan (date_from, date_to, price, returned, id_user, id_book) VALUES
                                                                                  ('2020-01-01', '2020-01-15', 50, FALSE, 1, 1),
                                                                                  ('2020-02-01', '2020-02-15', 60, TRUE, 2, 2),
                                                                                  ('2020-03-01', '2020-03-15', 70, FALSE, 3, 3),
                                                                                  ('2020-04-01', '2020-04-15', 80, TRUE, 4, 4),
                                                                                  ('2020-05-01', '2020-05-15', 90, FALSE, 5, 5),
                                                                                  ('2020-06-01', '2020-06-15', 100, TRUE, 1, 6),
                                                                                  ('2020-07-01', '2020-07-15', 110, FALSE, 2, 7);

INSERT INTO Reservation (reservation_ts, state, date_to, date_from, id_user, id_book) VALUES
                                                                                          (NOW(), 'AKTIVNI', '2023-01-01', '2023-01-15', 1, 1),
                                                                                          (NOW(), 'AKTIVNI', '2023-01-16', '2023-01-30', 2, 2),
                                                                                          (NOW(), 'AKTIVNI', '2023-02-01', '2023-02-15', 3, 3),
                                                                                          (NOW(), 'AKTIVNI', '2023-02-16', '2023-03-01', 4, 4),
                                                                                          (NOW(), 'AKTIVNI', '2023-03-02', '2023-03-16', 5, 5),
                                                                                          (NOW(), 'AKTIVNI', '2023-03-17', '2023-03-31', 1, 6),
                                                                                          (NOW(), 'AKTIVNI', '2023-04-01', '2023-04-15', 2, 7);

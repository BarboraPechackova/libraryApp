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


1,Brave New World ,Aldous Huxley,"Aldous Huxley's Brave New World is one of the best American novels of the 20th century and surprisingly apt at predicting future scientific advancements, including reproductive technology and psychological conditioning. In a dystopia whose citizens are genetically engineered and set into a hierarchy, the novel's protagonist, ""Alpha"" psychologist Bernard, challenges the system and the forces behind it by daring to explore the old world...and even bring part of it back with him. Controversial and thought-provoking, Brave New World is a novel that deserves continued analysis and enjoyment for years to come.",140,ISBN001,VOLNA,true,1
2,Death on the Nile,Agatha Christie,"Death on the Nile is a work of detective fiction by British writer Agatha Christie, published in the UK by the Collins Crime Club on 1 November 1937 and in the US by Dodd, Mead and Company the following year. The UK edition retailed at seven shillings and sixpence (7/6) and the US edition at $2.00.

The book features the Belgian detective Hercule Poirot. The action takes place in Egypt, mostly on the River Nile. The novel is unrelated to Christie's earlier short story of the same name, which featured Parker Pyne as the detective.",20,ISBN00220,VOLNA,true,1
3,Twilight,Stephenie Meyer,"Twilight (stylized as twilight) is a 2005 young adult vampire-romance novel[7][8] by author Stephenie Meyer. It is the first book in the Twilight series, and introduces seventeen-year-old Isabella ""Bella"" Swan, who moves from Phoenix, Arizona, to Forks, Washington. She is endangered after falling in love with Edward Cullen, a 103-year-old vampire frozen in his 17-year-old body. Additional novels in the series are New Moon, Eclipse, and Breaking Dawn.",120,ISBN003,VOLNA,true,1
4,One Flew Over the Cuckoo's Nest,Ken Kesey,"One Flew Over the Cuckoo's Nest is a novel by Ken Kesey published in 1962. Set in an Oregon psychiatric hospital, the narrative serves as a study of institutional processes and the human mind, including a critique of psychiatry and a tribute to individualistic principles.[citation needed] It was adapted into the Broadway (and later off-Broadway) play One Flew Over the Cuckoo's Nest by Dale Wasserman in 1963. Bo Goldman adapted the novel into a 1975 film of the same name directed by Miloš Forman, which won five Academy Awards.",200,ISBN004,VOLNA,false,1
5,Válka s mloky,Karel Čapek,"The book does not have any single protagonist, but instead looks at the development of the Newts from a broad societal perspective. At various points the narrator's register seems to slip into that of a journalist, historian or anthropologist. The three most central characters are Captain J. van Toch, the seaman who discovers the Newts; Mr Gussie H. Bondy, the industrialist who leads the development of the Newt industry; and Mr Povondra, Mr Bondy's doorman. They all reoccur throughout the book, but none can be said to drive the narrative in any significant way. All three are Czech.",180,ISBN005,VOLNA,true,1
6,Animal Farm,George Orwell,"Animal Farm is a beast fable, in the form of a satirical allegorical novella, by George Orwell, first published in England on 17 August 1945. It tells the story of a group of anthropomorphic farm animals who rebel against their human farmer, hoping to create a society where the animals can be equal, free, and happy. Ultimately, the rebellion is betrayed and, under the dictatorship of a pig named Napoleon, the farm ends up in a state as bad as it was before.",250,ISBN006,VOLNA,true,1
7,The Great Gatsby,F. Scott Fitzgerald,"The Great Gatsby is a 1925 novel by American writer F. Scott Fitzgerald. Set in the Jazz Age on Long Island, near New York City, the novel depicts first-person narrator Nick Carraway's interactions with mysterious millionaire Jay Gatsby and Gatsby's obsession to reunite with his former lover, Daisy Buchanan.",160,ISBN007,VOLNA,true,1
29,Fahrenheit 451,Ray Bradbury,"This classic of world literature, Fahrenheit 451 follows Guy Montag, a firefighter whose job is to burn books along with the houses where the now illegal texts are hidden. But once Guy meets his young neighbor, Clarisse, who teaches him about the freedom and imagination of the past, he begins to question his role in the world and what's at stake when we deny basic truths. Striking in its satire with a message more important than ever, Ray Bradbury's deftly written novel remains a must-read for any fan of fiction.",150,ISBN008,VOLNA,true,1
36,1984,George Orwell,"George Orwell's classic tale of governmental oversight and constraints of freethinking remains relevant in modern discourse as the term ""Orwellian"" is used and misused constantly. But the book that coined that term, 1984, is a thought-provoking and expertly crafted novel.

The story follows Winston Smith, a skillful worker rewriting history in the Ministry of Truth, who starts to look back fondly on the days before the Party took over the region of Oceania and implemented their cult of personality devoid of free thought and individuality. With beautiful prose and incredible world-building, Orwell's 1984 is more relevant today than ever before, and a must-read for the times.",120,024851-52S,VOLNA,true,1
40,Lolita,Vladimir Nabokov,"Lolita is a 1955 novel written by Russian-American novelist Vladimir Nabokov which addresses hebephilia. The protagonist is a French literature professor who moves to New England and writes under the pseudonym Humbert Humbert. He describes his obsession with a 12-year-old ""nymphet"", Dolores Haze, whom he kidnaps and sexually abuses after becoming her stepfather. Privately, he calls her ""Lolita"", the Spanish nickname for Dolores. The novel was originally written in English, but fear of censorship in the U.S. (where Nabokov lived) and Britain led to it being first published in Paris, France, in 1955 by Olympia Press.",50,ISBN12314,VOLNA,false,1
41,Spalovač mrtvol,Ladislav Fuks,"Příběh o ""spalovači mrtvol"", trochu podivínském a pilném zaměstnanci pražského krematoria Karlu Kopfrkinglovi, patří do prvního období spisovatelovy tvorby. Vyšel roku 1967 a spojoval bolestnou reakci na předválečné dramatické situace republiky a celé Evropy s groteskními a ""černými"" psychologickými a dějovými motivy. Jestliže se tato próza označuje jako psychologický horor, správně to vystihuje autorovu a vypravěčovu fascinaci zlem a smrtí. Charakterizovala i titulní postavu, jež se v průběhu překotných událostí a pod vlivem mefistofelského přítele ochotně přizpůsobovala nabídnuté příležitosti ke kariéře ""vyvoleného""",10,ISBN0107,VOLNA,true,1
42,Call Me by Your Name,André Aciman,"Call Me by Your Name is a 2007 coming-of-age novel by American writer André Aciman that centers on a blossoming romantic relationship between an intellectually precocious, curious, and pretentious 17-year-old American-Italian Jewish boy named Elio Perlman and a visiting 24-year-old American Jewish scholar named Oliver in 1980s Italy. The novel chronicles their summer romance and the 20 years that follow. A sequel to the novel, Find Me, was released in October 2019.",140,0-374-29921-8,VOLNA,true,1

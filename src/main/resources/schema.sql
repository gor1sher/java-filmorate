--CREATE TABLE mpa_rating (
--    id INT PRIMARY KEY,
--    name VARCHAR(10) NOT NULL
--);
--
--CREATE TABLE genres (
--    id INT PRIMARY KEY,
--    name VARCHAR(255) NOT NULL
--);
--
--CREATE TABLE users (
--    id BIGINT AUTO_INCREMENT PRIMARY KEY,
--    email VARCHAR(255) NOT NULL,
--    login VARCHAR(255) NOT NULL,
--    name VARCHAR(255) NOT NULL,
--    birthday DATE,
--    listFriends VARCHAR(255)
--);
--
--CREATE TABLE films (
--    id BIGINT AUTO_INCREMENT PRIMARY KEY,
--    name VARCHAR(255) NOT NULL,
--    description TEXT,
--    releaseDate DATE,
--    duration INT,
--    likeList VARCHAR(255),
--    genre_id VARCHAR(255),
--    mpaRating_id INT,
--    FOREIGN KEY (genre_id) REFERENCES genres(id),
--    FOREIGN KEY(mpaRating_id) REFERENCES mpa_rating(id)
--);


--INSERT INTO mpa_rating (id, name)
--VALUES
--(1, 'G'),
--(2, 'PG'),
--(3, 'PG_13'),
--(4, 'R'),
--(5, 'NC_17');
--
--INSERT INTO genres (id, name)
--VALUES
--(1, 'COMEDY'),
--(2, 'DRAMA'),
--(3, 'ANIMATION'),
--(4, 'THRILLER'),
--(5, 'DOCUMENTARY'),
--(6, 'ACTION');


INSERT INTO users (email, login, name, birthday, listFriends)
VALUES
('user1@example.com', 'user1', 'John Doe', '1990-01-01', '2,3'),
('user2@example.com', 'user2', 'Jane Smith', '1985-05-15', '1,3'),
('user3@example.com', 'user3', 'Alice Johnson', '1995-08-20', '2,1');
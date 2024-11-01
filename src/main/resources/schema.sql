--CREATE TABLE users (
--    id BIGINT,
--    email VARCHAR(255) NOT NULL,
--    login VARCHAR(255) NOT NULL,
--    name VARCHAR(255) NOT NULL,
--    birthday DATE,
--    listFriends VARCHAR(255)
--);
--
--CREATE TABLE films (
--    id BIGINT,
--    name VARCHAR(255) NOT NULL,
--    description TEXT,
--    releaseDate DATE,
--    duration INT,
--    likeList VARCHAR(255),
--    genre VARCHAR(255),6
--    mpaRating VARCHAR(255)
--);
INSERT INTO users (id, email, login, name, birthday, listFriends)
VALUES
(1, 'user1@example.com', 'user1', 'John Doe', '1990-01-01', 'friend1,friend2'),
(2, 'user2@example.com', 'user2', 'Jane Smith', '1985-05-15', 'friend3,friend4'),
(3, 'user3@example.com', 'user3', 'Alice Johnson', '1995-08-20', 'friend5,friend6');

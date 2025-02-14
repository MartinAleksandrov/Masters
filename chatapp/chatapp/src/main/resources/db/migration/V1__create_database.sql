CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       username VARCHAR(255) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       active BOOLEAN NOT NULL DEFAULT TRUE
);

-- Създаване на таблицата `channels`
CREATE TABLE channels (
                          id SERIAL PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          owner_id BIGINT NOT NULL,
                          FOREIGN KEY (owner_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Създаване на таблицата `channel_members`
CREATE TABLE channel_members (
                                 channel_id BIGINT NOT NULL,
                                 user_id BIGINT NOT NULL,
                                 role VARCHAR(50) NOT NULL DEFAULT 'GUEST',
                                 PRIMARY KEY (channel_id, user_id),
                                 FOREIGN KEY (channel_id) REFERENCES channels(id) ON DELETE CASCADE,
                                 FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Създаване на таблицата `messages`
CREATE TABLE messages (
                          id SERIAL PRIMARY KEY,
                          sender_id BIGINT NOT NULL,
                          receiver_id BIGINT,
                          channel_id BIGINT,
                          content TEXT NOT NULL,
                          timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          FOREIGN KEY (sender_id) REFERENCES users(id) ON DELETE CASCADE,
                          FOREIGN KEY (receiver_id) REFERENCES users(id) ON DELETE CASCADE,
                          FOREIGN KEY (channel_id) REFERENCES channels(id) ON DELETE CASCADE
);

-- Създаване на таблицата `user_friends`
CREATE TABLE user_friends (
                              user_id BIGINT NOT NULL,
                              friend_id BIGINT NOT NULL,
                              PRIMARY KEY (user_id, friend_id),
                              FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                              FOREIGN KEY (friend_id) REFERENCES users(id) ON DELETE CASCADE
);
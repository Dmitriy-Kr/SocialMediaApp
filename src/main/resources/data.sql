-- Insert users
INSERT INTO app_user (username, password) VALUES
('alice', 'password123'),
('bob', 'password456'),
('charlie', 'password789');

-- Insert posts
INSERT INTO post (title, body, author_id) VALUES
('Post by Alice', 'This is Alice''s first post.', 1),
('Bob''s Thoughts', 'Bob is sharing his thoughts.', 2),
('Charlie''s Story', 'Here is a story from Charlie.', 3),
('Alice''s Update', 'Alice shares another update.', 1);

-- Insert followers (user_id follows follower_id)
INSERT INTO user_followers (user_id, follower_id) VALUES
(1, 3), -- Alice follows Charlie
(3, 1); -- Charlie follows Alice

-- Insert liked posts
INSERT INTO user_liked_posts (user_id, post_id) VALUES
(1, 3), -- Alice likes Charlie's post
(2, 1), -- Bob likes Alice's post
(3, 4); -- Charlie likes Alice's update
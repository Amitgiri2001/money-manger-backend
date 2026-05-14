-- Expense Categories

INSERT INTO txn_classification
(level, name, description, user_id)
VALUES
('CATEGORY', 'FOOD', 'Food and dining expenses', NULL);

INSERT INTO txn_classification
(level, name, description, user_id)
VALUES
('CATEGORY', 'RENT', 'House rent expenses', NULL);

INSERT INTO txn_classification
(level, name, description, user_id)
VALUES
('CATEGORY', 'TRAVEL', 'Travel expenses', NULL);

INSERT INTO txn_classification
(level, name, description, user_id)
VALUES
('CATEGORY', 'SHOPPING', 'Shopping expenses', NULL);

INSERT INTO txn_classification
(level, name, description, user_id)
VALUES
('CATEGORY', 'ENTERTAINMENT', 'Entertainment expenses', NULL);

INSERT INTO txn_classification
(level, name, description, user_id)
VALUES
('CATEGORY', 'BILLS', 'Utility bills', NULL);

INSERT INTO txn_classification
(level, name, description, user_id)
VALUES
('CATEGORY', 'HEALTH', 'Medical expenses', NULL);

-- Income Categories

INSERT INTO txn_classification
(level, name, description, user_id)
VALUES
('CATEGORY', 'SALARY', 'Monthly salary', NULL);

INSERT INTO txn_classification
(level, name, description, user_id)
VALUES
('CATEGORY', 'FREELANCING', 'Freelance income', NULL);

INSERT INTO txn_classification
(level, name, description, user_id)
VALUES
('CATEGORY', 'BONUS', 'Bonus income', NULL);

-- Investment Categories

INSERT INTO txn_classification
(level, name, description, user_id)
VALUES
('CATEGORY', 'STOCKS', 'Stock market investments', NULL);

INSERT INTO txn_classification
(level, name, description, user_id)
VALUES
('CATEGORY', 'MUTUAL FUND', 'Mutual fund investments', NULL);

INSERT INTO txn_classification
(level, name, description, user_id)
VALUES
('CATEGORY', 'CRYPTO', 'Cryptocurrency investments', NULL);
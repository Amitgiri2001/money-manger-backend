ALTER TABLE txn_classification
ADD COLUMN if not exists parent_id BIGINT;

ALTER TABLE txn_classification
ADD CONSTRAINT fk_txn_classification_parent
FOREIGN KEY (parent_id)
REFERENCES txn_classification(id);

UPDATE txn_classification c
SET parent_id = (
    SELECT id
    FROM txn_classification t
    WHERE t.level = 'TYPE'
    AND t.name = 'EXPENSE'
)
WHERE c.level = 'CATEGORY'
AND c.name IN ('FOOD', 'TRAVEL', 'SHOPPING', 'HEALTH', 'ENTERTAINMENT','BILLS','PERSONAL');

UPDATE txn_classification c
SET parent_id = (
    SELECT id
    FROM txn_classification t
    WHERE t.level = 'TYPE'
    AND t.name = 'INCOME'
)
WHERE c.level = 'CATEGORY'
AND c.name IN ('SALARY','FREELANCING','BONUS');

UPDATE txn_classification c
SET parent_id = (
    SELECT id
    FROM txn_classification t
    WHERE t.level = 'TYPE'
    AND t.name = 'INVESTMENT'
)
WHERE c.level = 'CATEGORY'
AND c.name IN ('STOCKS','MUTUAL FUND', 'CRYPTO');

UPDATE txn_classification c
SET parent_id = (
    SELECT id
    FROM txn_classification t
    WHERE t.level = 'TYPE'
    AND t.name = 'LENDING'
)
WHERE c.level = 'CATEGORY'
AND c.name IN ('RECEIVABLE');
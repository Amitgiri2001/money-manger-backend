-- =========================================
-- ADD NEW COLUMNS TO TXN
-- =========================================

ALTER TABLE txn
ADD COLUMN IF NOT EXISTS type_classifier_id BIGINT;

ALTER TABLE txn
ADD COLUMN IF NOT EXISTS cat_classifier_id BIGINT;

-- =========================================
-- ADD FOREIGN KEYS
-- =========================================

ALTER TABLE txn
ADD CONSTRAINT fk_txn_type_classifier
FOREIGN KEY (type_classifier_id)
REFERENCES txn_classification(id);


ALTER TABLE txn
ADD CONSTRAINT fk_txn_category_classifier
FOREIGN KEY (cat_classifier_id)
REFERENCES txn_classification(id);


-- =========================
-- UPDATE TYPE CLASSIFICATION
-- =========================

UPDATE txn
SET type_classifier_id = (
    SELECT id
    FROM txn_classification
    WHERE level = 'TYPE'
    AND name = txn.type
);

-- =========================
-- UPDATE CATEGORY CLASSIFICATION
-- =========================

UPDATE txn
SET cat_classifier_id = (
    SELECT id
    FROM txn_classification
    WHERE level = 'CATEGORY'
    AND name = txn.category
);



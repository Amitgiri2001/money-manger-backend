
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

-- =========================
-- ADD FOREIGN KEY CONSTRAINTS
-- =========================
/*
ALTER TABLE txn
ADD CONSTRAINT fk_txn_type_classifier
FOREIGN KEY (type_classifier_id)
REFERENCES txn_classification(id);

ALTER TABLE txn
ADD CONSTRAINT fk_txn_cat_classifier
FOREIGN KEY (cat_classifier_id)
REFERENCES txn_classification(id);
*/

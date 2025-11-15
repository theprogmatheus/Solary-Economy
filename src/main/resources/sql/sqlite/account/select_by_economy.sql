SELECT
    id, name_id, name, owner_id, balance, economy_id, flags
FROM %table_prefix%_account
WHERE economy_id = ?;
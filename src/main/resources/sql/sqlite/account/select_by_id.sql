SELECT
    id, name_id, name, owner_id, balance, economy_id, flags
FROM %prefix%_account
WHERE id = ?;
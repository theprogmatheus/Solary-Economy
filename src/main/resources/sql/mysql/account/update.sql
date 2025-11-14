UPDATE %prefix%_account
SET owner_id = UNHEX(REPLACE(?, '-', '')),
    balance = ?,
    economy_id = ?,
    flags = ?
WHERE name_id = ?;
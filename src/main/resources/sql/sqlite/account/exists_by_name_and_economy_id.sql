SELECT 1
FROM %prefix%_account
WHERE name_id = ? AND economy_id = ?
LIMIT 1;
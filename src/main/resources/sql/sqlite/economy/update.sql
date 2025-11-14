UPDATE %prefix%_economy
SET name = ?,
    description = ?,
    command = ?,
    command_aliases = ?,
    currency_symbol = ?,
    currency_name = ?,
    currency_name_plural = ?,
    flags = ?
WHERE name_id = ?;
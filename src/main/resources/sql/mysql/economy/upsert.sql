INSERT INTO %prefix%_economy (
    name_id, name, description, command, command_aliases,
    currency_symbol, currency_name, currency_name_plural, flags
) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
ON DUPLICATE KEY UPDATE
    name = VALUES(name),
    description = VALUES(description),
    command = VALUES(command),
    command_aliases = VALUES(command_aliases),
    currency_symbol = VALUES(currency_symbol),
    currency_name = VALUES(currency_name),
    currency_name_plural = VALUES(currency_name_plural),
    flags = VALUES(flags);
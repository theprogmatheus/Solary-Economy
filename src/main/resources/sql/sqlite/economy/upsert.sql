INSERT INTO %table_prefix%_economy (
    id, name_id, name, description, command, command_aliases,
    currency_symbol, currency_name, currency_name_plural, flags
)
VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
ON CONFLICT(name_id) DO UPDATE SET
    name = excluded.name,
    description = excluded.description,
    command = excluded.command,
    command_aliases = excluded.command_aliases,
    currency_symbol = excluded.currency_symbol,
    currency_name = excluded.currency_name,
    currency_name_plural = excluded.currency_name_plural,
    flags = excluded.flags;
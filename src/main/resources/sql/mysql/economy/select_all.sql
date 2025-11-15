SELECT
    id, name_id, name, description, command, command_aliases,
    currency_symbol, currency_name, currency_name_plural, flags
FROM %table_prefix%_economy;
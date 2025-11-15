CREATE TABLE IF NOT EXISTS %table_prefix%_economy (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name_id TEXT NOT NULL UNIQUE,
    name TEXT NOT NULL,
    description TEXT NOT NULL,
    command TEXT NOT NULL,
    command_aliases TEXT NOT NULL,
    currency_symbol TEXT NOT NULL,
    currency_name TEXT NOT NULL,
    currency_name_plural TEXT NOT NULL,
    flags INTEGER NOT NULL DEFAULT 0
);
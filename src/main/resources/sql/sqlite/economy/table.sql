CREATE TABLE IF NOT EXISTS %prefix%_economy (
    name_id TEXT NOT NULL,
    name TEXT NOT NULL,
    description TEXT,
    command TEXT NOT NULL,
    command_aliases TEXT NOT NULL DEFAULT '',
    currency_symbol TEXT NOT NULL,
    currency_name TEXT NOT NULL,
    currency_name_plural TEXT NOT NULL,
    flags INTEGER NOT NULL DEFAULT 0,
    PRIMARY KEY (name_id)
);
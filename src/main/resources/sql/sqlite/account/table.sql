CREATE TABLE IF NOT EXISTS %prefix%_account (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name_id TEXT NOT NULL,
    name TEXT NOT NULL,
    owner_id BLOB NOT NULL,
    balance TEXT NOT NULL DEFAULT '0.00',
    economy_id INTEGER NOT NULL,
    flags INTEGER NOT NULL DEFAULT 0,

    UNIQUE (name_id, economy_id),

    FOREIGN KEY (economy_id)
        REFERENCES %prefix%_economy(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);
CREATE TABLE IF NOT EXISTS %prefix%_account (
    name_id TEXT NOT NULL,
    owner_id BLOB NOT NULL,
    balance DECIMAL(19,4) NOT NULL DEFAULT 0.0000,
    economy_id TEXT NOT NULL,
    flags INTEGER NOT NULL DEFAULT 0,
    PRIMARY KEY (name_id),
    FOREIGN KEY (economy_id) REFERENCES %prefix%_economy(name_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);
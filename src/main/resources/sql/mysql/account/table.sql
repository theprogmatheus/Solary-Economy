CREATE TABLE IF NOT EXISTS %prefix%_account (
    name_id VARCHAR(64) NOT NULL,
    owner_id BINARY(16) NOT NULL,
    balance DECIMAL(19,4) NOT NULL DEFAULT 0.0000,
    economy_id VARCHAR(32) NOT NULL,
    flags INT UNSIGNED NOT NULL DEFAULT 0,
    PRIMARY KEY (name_id),
    INDEX idx_account_owner_id (owner_id),
    INDEX idx_account_economy_id (economy_id),
    FOREIGN KEY (economy_id) REFERENCES %prefix%_economy(name_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
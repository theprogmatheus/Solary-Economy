CREATE TABLE IF NOT EXISTS %table_prefix%_account (
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    name_id VARCHAR(64) NOT NULL,
    name VARCHAR(128) NOT NULL,
    owner_id BINARY(16) NOT NULL,
    balance DECIMAL(38,2) NOT NULL DEFAULT 0,
    economy_id BIGINT UNSIGNED NOT NULL,
    flags INT UNSIGNED NOT NULL DEFAULT 0,

    PRIMARY KEY (id),
    UNIQUE KEY uq_account_name_and_economy (name_id, economy_id),

    INDEX idx_owner_id (owner_id),
    INDEX idx_economy_id (economy_id),

    CONSTRAINT fk_account_economy
        FOREIGN KEY (economy_id)
        REFERENCES %table_prefix%_economy(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
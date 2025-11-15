CREATE TABLE IF NOT EXISTS %table_prefix%_economy (
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    name_id VARCHAR(64) NOT NULL,
    name VARCHAR(128) NOT NULL,
    description VARCHAR(255) NOT NULL,
    command VARCHAR(64) NOT NULL,
    command_aliases TEXT NOT NULL,
    currency_symbol VARCHAR(8) NOT NULL,
    currency_name VARCHAR(64) NOT NULL,
    currency_name_plural VARCHAR(64) NOT NULL,
    flags INT UNSIGNED NOT NULL DEFAULT 0,

    PRIMARY KEY (id),
    UNIQUE KEY uq_economy_name_id (name_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
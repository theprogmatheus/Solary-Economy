CREATE TABLE IF NOT EXISTS %prefix%_economy (
    name_id VARCHAR(32) NOT NULL,
    name VARCHAR(64) NOT NULL,
    description TINYTEXT,
    command VARCHAR(32) NOT NULL,
    command_aliases VARCHAR(128) NOT NULL DEFAULT '',
    currency_symbol CHAR(4) NOT NULL,
    currency_name VARCHAR(32) NOT NULL,
    currency_name_plural VARCHAR(32) NOT NULL,
    flags INT UNSIGNED NOT NULL DEFAULT 0,
    PRIMARY KEY (name_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
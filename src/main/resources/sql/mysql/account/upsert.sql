INSERT INTO %table_prefix%_account (
    id, name_id, name, owner_id, balance, economy_id, flags
)
VALUES (?, ?, ?, ?, ?, ?, ?)
ON DUPLICATE KEY UPDATE
    name = VALUES(name),
    owner_id = VALUES(owner_id),
    balance = VALUES(balance),
    flags = VALUES(flags);
INSERT INTO %prefix%_account (
    name_id, owner_id, balance, economy_id, flags
) VALUES (
    ?, UNHEX(REPLACE(?, '-', '')), ?, ?, ?
)
ON DUPLICATE KEY UPDATE
    owner_id = VALUES(owner_id),
    balance = VALUES(balance),
    economy_id = VALUES(economy_id),
    flags = VALUES(flags);
INSERT INTO %prefix%_account (
    name_id, owner_id, balance, economy_id, flags
) VALUES (?, ?, ?, ?, ?)
ON CONFLICT(name_id) DO UPDATE SET
    owner_id = excluded.owner_id,
    balance = excluded.balance,
    economy_id = excluded.economy_id,
    flags = excluded.flags;
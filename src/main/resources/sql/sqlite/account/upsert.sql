INSERT INTO %prefix%_account (
    id, name_id, name, owner_id, balance, economy_id, flags
)
VALUES (?, ?, ?, ?, ?, ?, ?)
ON CONFLICT(name_id, economy_id) DO UPDATE SET
    name = excluded.name,
    owner_id = excluded.owner_id,
    balance = excluded.balance,
    flags = excluded.flags;
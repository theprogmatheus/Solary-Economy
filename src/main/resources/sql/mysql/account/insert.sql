INSERT INTO %prefix%_account (
    name_id, owner_id, balance, economy_id, flags
) VALUES (?, UNHEX(REPLACE(?, '-', '')), ?, ?, ?);
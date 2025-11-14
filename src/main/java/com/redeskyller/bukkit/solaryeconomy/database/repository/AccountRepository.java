package com.redeskyller.bukkit.solaryeconomy.database.repository;

import com.redeskyller.bukkit.solaryeconomy.database.entity.AccountEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountRepository {

    Optional<AccountEntity> findByNameId(String nameId);

    List<AccountEntity> findByOwnerId(UUID ownerId);

    void save(AccountEntity accountEntity);

    void delete(String nameId);

}

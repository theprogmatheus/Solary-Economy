package com.redeskyller.bukkit.solaryeconomy.database.repository;

import com.redeskyller.bukkit.solaryeconomy.database.entity.AccountEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountRepository {

    void createTableIfNotExists();

    Optional<AccountEntity> findById(long id);

    Optional<AccountEntity> findByNameIdAndEconomy(String nameId, long economyId);

    List<AccountEntity> findByOwner(UUID ownerId);

    List<AccountEntity> findByEconomy(long economyId);

    void save(AccountEntity account);

    void deleteById(long id);

    boolean exists(String nameId, long economyId);

}

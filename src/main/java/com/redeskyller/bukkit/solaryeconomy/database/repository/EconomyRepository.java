package com.redeskyller.bukkit.solaryeconomy.database.repository;

import com.redeskyller.bukkit.solaryeconomy.database.entity.EconomyEntity;

import java.util.Optional;

public interface EconomyRepository {

    Optional<EconomyEntity> findByNameId(String nameId);

    void save(EconomyEntity economyEntity);

    void delete(String nameId);

}

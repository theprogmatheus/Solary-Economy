package com.redeskyller.bukkit.solaryeconomy.database.repository;

import com.redeskyller.bukkit.solaryeconomy.database.entity.EconomyEntity;

import java.util.List;
import java.util.Optional;

    public interface EconomyRepository {

    Optional<EconomyEntity> findById(long id);

    Optional<EconomyEntity> findByNameId(String nameId);

    List<EconomyEntity> findAll();

    void save(EconomyEntity economy);

    void deleteById(long id);

    boolean existsByNameId(String nameId);

}

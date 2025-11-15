package com.redeskyller.bukkit.solaryeconomy.database.repository.impl;

import com.redeskyller.bukkit.solaryeconomy.database.SqlQueryLoader;
import com.redeskyller.bukkit.solaryeconomy.database.entity.EconomyEntity;
import com.redeskyller.bukkit.solaryeconomy.database.repository.EconomyRepository;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RequiredArgsConstructor
public class EconomyRepositoryImpl implements EconomyRepository {

    private final HikariDataSource dataSource;
    private final SqlQueryLoader loader;
    private final Logger logger;

    @Override
    public void createTableIfNotExists() {
        String sql = loader.getQuery("economy/table");
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.severe("Error executing createTableIfNotExists (Economy): " + e.getMessage());
        }
    }

    @Override
    public Optional<EconomyEntity> findById(long id) {
        String sql = loader.getQuery("economy/select_by_id");
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return Optional.empty();
                return Optional.of(map(rs));
            }

        } catch (SQLException e) {
            logger.severe("Error executing findById (Economy): " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<EconomyEntity> findByNameId(String nameId) {
        String sql = loader.getQuery("economy/select_by_name_id");
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nameId);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return Optional.empty();
                return Optional.of(map(rs));
            }

        } catch (SQLException e) {
            logger.severe("Error executing findByNameId (Economy): " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public List<EconomyEntity> findAll() {
        String sql = loader.getQuery("economy/select_all");
        List<EconomyEntity> result = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.add(map(rs));
                }
            }

            return result;

        } catch (SQLException e) {
            logger.severe("Error executing findAll (Economy): " + e.getMessage());
            return result;
        }
    }

    @Override
    public void save(EconomyEntity economy) {
        List<String> queries = loader.getQueries("economy/upsert");
        try (Connection conn = dataSource.getConnection()) {

            for (String sql : queries) {
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setLong(1, economy.getId());
                    ps.setString(2, economy.getNameId());
                    ps.setString(3, economy.getName());
                    ps.setString(4, economy.getDescription());
                    ps.setString(5, economy.getCommand());
                    ps.setString(6, economy.getCommandAliases());
                    ps.setString(7, economy.getCurrencySymbol());
                    ps.setString(8, economy.getCurrencyName());
                    ps.setString(9, economy.getCurrencyNamePlural());
                    ps.setInt(10, economy.getFlags());
                    ps.executeUpdate();
                }
            }

        } catch (SQLException e) {
            logger.severe("Error executing save (Economy): " + e.getMessage());
        }
    }

    @Override
    public void deleteById(long id) {
        String sql = loader.getQuery("economy/delete_by_id");
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            logger.severe("Error executing deleteById (Economy): " + e.getMessage());
        }
    }

    @Override
    public boolean existsByNameId(String nameId) {
        String sql = loader.getQuery("economy/exists_by_name_id");
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nameId);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return false;
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            logger.severe("Error executing existsByNameId (Economy): " + e.getMessage());
            return false;
        }
    }

    private EconomyEntity map(ResultSet rs) throws SQLException {
        return new EconomyEntity(
                rs.getLong("id"),
                rs.getString("name_id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getString("command"),
                rs.getString("command_aliases"),
                rs.getString("currency_symbol"),
                rs.getString("currency_name"),
                rs.getString("currency_name_plural"),
                rs.getInt("flags")
        );
    }
}

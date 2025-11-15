package com.redeskyller.bukkit.solaryeconomy.database.repository.impl;

import com.redeskyller.bukkit.solaryeconomy.database.SqlQueryLoader;
import com.redeskyller.bukkit.solaryeconomy.database.entity.AccountEntity;
import com.redeskyller.bukkit.solaryeconomy.database.repository.AccountRepository;
import com.redeskyller.bukkit.solaryeconomy.util.UUIDUtils;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountRepository {

    private final HikariDataSource dataSource;
    private final SqlQueryLoader loader;
    private final Logger logger;

    @Override
    public void createTableIfNotExists() {
        String sql = loader.getQuery("account/table");
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.severe("Error executing createTableIfNotExists (Account): " + e.getMessage());
        }
    }

    @Override
    public Optional<AccountEntity> findById(long id) {
        String sql = loader.getQuery("account/select_by_id");
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return Optional.empty();
                return Optional.of(map(rs));
            }
        } catch (SQLException e) {
            logger.severe("Error executing findById (Account): " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<AccountEntity> findByNameIdAndEconomy(String nameId, long economyId) {
        String sql = loader.getQuery("account/select_by_name_and_economy_id");
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nameId);
            ps.setLong(2, economyId);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return Optional.empty();
                return Optional.of(map(rs));
            }
        } catch (SQLException e) {
            logger.severe("Error executing findByNameIdAndEconomy (Account): " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public List<AccountEntity> findByOwner(UUID ownerId) {
        String sql = loader.getQuery("account/select_by_owner");
        List<AccountEntity> result = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setBytes(1, UUIDUtils.uuidToBytes(ownerId));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.add(map(rs));
                }
            }

            return result;
        } catch (SQLException e) {
            logger.severe("Error executing findByOwner (Account): " + e.getMessage());
            return result;
        }
    }

    @Override
    public List<AccountEntity> findByEconomy(long economyId) {
        String sql = loader.getQuery("account/select_by_economy");
        List<AccountEntity> result = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, economyId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) result.add(map(rs));
            }

            return result;
        } catch (SQLException e) {
            logger.severe("Error executing findByEconomy (Account): " + e.getMessage());
            return result;
        }
    }

    @Override
    public void save(AccountEntity account) {
        List<String> queries = loader.getQueries("account/upsert");
        try (Connection conn = dataSource.getConnection()) {
            for (String sql : queries) {
                try (PreparedStatement ps = conn.prepareStatement(sql)) {

                    ps.setLong(1, account.getId());
                    ps.setString(2, account.getNameId());
                    ps.setString(3, account.getName());
                    ps.setBytes(4, UUIDUtils.uuidToBytes(account.getOwnerId()));
                    ps.setBigDecimal(5, account.getBalance());
                    ps.setLong(6, account.getEconomyId());
                    ps.setInt(7, account.getFlags());
                    ps.executeUpdate();
                }
            }

        } catch (SQLException e) {
            logger.severe("Error executing save (Account): " + e.getMessage());
        }
    }

    @Override
    public void deleteById(long id) {
        String sql = loader.getQuery("account/delete_by_id");
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            logger.severe("Error executing deleteById (Account): " + e.getMessage());
        }
    }

    @Override
    public boolean exists(String nameId, long economyId) {
        String sql = loader.getQuery("account/exists_by_name_and_economy_id");

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nameId);
            ps.setLong(2, economyId);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return false;
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            logger.severe("Error executing exists (Account): " + e.getMessage());
            return false;
        }
    }

    private AccountEntity map(ResultSet rs) throws SQLException {
        return new AccountEntity(
                rs.getLong("id"),
                rs.getString("name_id"),
                rs.getString("name"),
                UUIDUtils.bytesToUuid(rs.getBytes("owner_id")),
                rs.getBigDecimal("balance"),
                rs.getLong("economy_id"),
                rs.getInt("flags")
        );
    }
}

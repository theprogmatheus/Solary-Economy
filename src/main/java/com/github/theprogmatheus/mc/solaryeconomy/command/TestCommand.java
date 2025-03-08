package com.github.theprogmatheus.mc.solaryeconomy.command;

import com.github.theprogmatheus.mc.solaryeconomy.SolaryEconomy;
import com.github.theprogmatheus.mc.solaryeconomy.database.DatabaseManager;
import com.github.theprogmatheus.mc.solaryeconomy.model.Counter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class TestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        String userId = (sender instanceof Player) ? ((Player) sender).getUniqueId().toString() : sender.getName();
        DatabaseManager db = SolaryEconomy.getDatabaseManager();
        try {
            Counter counter = db.getCountersDao().queryForId(userId);
            if (counter == null) counter = new Counter(userId, 0);

            counter.increment();

            db.getCountersDao().createOrUpdate(counter);

            sender.sendMessage("§aContador incrementado: " + counter.getCount());

        } catch (SQLException e) {
            throw new RuntimeException("Unable to SQL", e);
        }
        return false;
    }
}

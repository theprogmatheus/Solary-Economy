package com.github.theprogmatheus.mc.solaryeconomy.service;

import com.github.theprogmatheus.mc.solaryeconomy.config.Config;
import com.github.theprogmatheus.mc.solaryeconomy.config.Env;
import com.github.theprogmatheus.mc.solaryeconomy.config.LangConfiguration;
import com.github.theprogmatheus.mc.solaryeconomy.task.EnvLoaderTask;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

@Getter
public class ConfigurationService implements Service {

    public static String DEFAULT_LANG_TAG = "ptBR";

    private final JavaPlugin plugin;

    private Config defaultConfig;
    private LangConfiguration langConfig;

    public ConfigurationService(JavaPlugin plugin) {
        this.plugin = plugin;
    }


    @Override
    public void startup() {
        this.defaultConfig = new Config(plugin, new File(plugin.getDataFolder(), "config.yml"), new EnvLoaderTask()).createDefaultConfigFileIfNotExists().load();
        this.langConfig = (LangConfiguration) new LangConfiguration(this.plugin, Env.LANG != null ? Env.LANG : DEFAULT_LANG_TAG).createDefaultConfigFileIfNotExists().load();
    }

    @Override
    public void shutdown() {
        // nothing yet
    }

}

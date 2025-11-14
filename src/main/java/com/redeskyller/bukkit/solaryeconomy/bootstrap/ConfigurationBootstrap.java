package com.redeskyller.bukkit.solaryeconomy.bootstrap;

import com.redeskyller.bukkit.solaryeconomy.config.ConfigurationManager;
import com.redeskyller.bukkit.solaryeconomy.config.env.Config;
import com.redeskyller.bukkit.solaryeconomy.config.lang.MessageManager;
import com.redeskyller.bukkit.solaryeconomy.util.LocaleUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Locale;

@Getter
@RequiredArgsConstructor
public class ConfigurationBootstrap {

    private final JavaPlugin plugin;

    private ConfigurationManager configurationManager;
    private MessageManager messageManager;

    public void initializeConfigurations() {
        this.configurationManager = new ConfigurationManager(plugin.getLogger(), plugin.getDataFolder())
                .addConfigurationClass(Config.class)
                .mapConfigurationClasses();

        String langDefault = Config.LANG_DEFAULT.getValue();
        Boolean langIndividual = Config.LANG_INDIVIDUAL.getValue();
        Locale defaultLocale = LocaleUtils.getLocaleByString(langDefault);

        this.messageManager = new MessageManager(
                plugin.getLogger(),
                new File(plugin.getDataFolder(), "lang"),
                "lang",
                defaultLocale
        );
        this.messageManager.setIndividualLang(langIndividual);
        this.messageManager.loadLanguages();
    }

    public void reloadConfigurations() {
        this.configurationManager.mapConfigurationClasses();
        this.messageManager.loadLanguages();
    }

}

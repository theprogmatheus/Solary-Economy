package com.github.theprogmatheus.mc.solaryeconomy.task;

import com.github.theprogmatheus.mc.solaryeconomy.config.Config;
import com.github.theprogmatheus.mc.solaryeconomy.config.Env;
import com.github.theprogmatheus.mc.solaryeconomy.exception.ConfigurationLoadException;
import org.bukkit.configuration.ConfigurationSection;

import java.lang.reflect.Field;
import java.util.function.Consumer;

public class EnvLoaderTask implements Consumer<Config> {

    @Override
    public void accept(Config config) {
        // parse config to env class
        for (Field field : Env.class.getDeclaredFields()) {
            Object value = getConfigValue(config, field.getName().toLowerCase());
            if (value == null) continue;
            try {
                field.set(null, value);
            } catch (IllegalAccessException e) {
                throw new ConfigurationLoadException("Cant set config env class field value: " + field.getName() + "=" + value, e);
            }
        }
    }

    private Object getConfigValue(ConfigurationSection section, String path) {
        Object value = section.get(path);
        if (value != null)
            return value;

        if (path.contains("_")) {

            value = section.get(path.replace("_", "."));
            if (value != null)
                return value;

            String[] parts = path.split("_");
            ConfigurationSection nextSection = section.getConfigurationSection(parts[0]);

            if (nextSection != null)
                return getConfigValue(nextSection, path.substring((parts[0].length() + 1)));
        }
        return value;
    }


}

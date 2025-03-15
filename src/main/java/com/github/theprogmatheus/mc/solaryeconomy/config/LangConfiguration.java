package com.github.theprogmatheus.mc.solaryeconomy.config;

import com.github.theprogmatheus.mc.solaryeconomy.task.LangLoaderTask;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

@Getter
public class LangConfiguration extends Config {

    private final String langTag;

    public LangConfiguration(JavaPlugin plugin, String langTag) {
        super(plugin, new File(new File(plugin.getDataFolder(), "langs"), "lang-".concat(langTag).concat(".yml")));
        this.langTag = langTag;
        this.setLoadCallback(new LangLoaderTask());
    }

}

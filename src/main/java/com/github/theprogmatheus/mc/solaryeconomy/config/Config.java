package com.github.theprogmatheus.mc.solaryeconomy.config;

import com.github.theprogmatheus.mc.solaryeconomy.exception.ConfigurationLoadException;
import com.github.theprogmatheus.mc.solaryeconomy.exception.ConfigurationWriteException;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Getter
@Setter
public class Config extends YamlConfiguration {

    private final JavaPlugin plugin;
    private final File file;

    private Consumer<Config> loadCallback;

    public Config(JavaPlugin plugin, File file) {
        this(plugin, file, null);
    }

    public Config(JavaPlugin plugin, File file, Consumer<Config> loadCallback) {
        if (plugin == null) throw new IllegalArgumentException("Plugin cant be null.");
        if (file == null) throw new IllegalArgumentException("File cant be null.");

        this.plugin = plugin;
        this.file = file;
        this.loadCallback = loadCallback;
    }

    public CompletableFuture<Config> loadAsync() {
        return CompletableFuture.supplyAsync(this::load);
    }

    public Config load() {
        try {
            this.load(this.file);
            if (this.loadCallback != null)
                this.loadCallback.accept(this);
        } catch (Exception e) {
            throw new ConfigurationLoadException("Unable to load configuration", e);
        }
        return this;
    }

    public CompletableFuture<Config> createDefaultConfigFileIfNotExistsAsync() {
        return CompletableFuture.supplyAsync(this::createDefaultConfigFileIfNotExists);
    }

    public Config createDefaultConfigFileIfNotExists() {
        return this.file.exists() ? this : this.createDefaultConfigFile();
    }

    public CompletableFuture<Config> createDefaultConfigFileAsync() {
        return CompletableFuture.supplyAsync(this::createDefaultConfigFile);
    }

    public Config createDefaultConfigFile() {
        try (InputStream inputStream = this.plugin.getResource(this.file.getName())) {

            if (inputStream == null)
                throw new ConfigurationWriteException("Cant find resource by name: " + this.file.getName());

            if (this.file.getParentFile() != null && !this.file.getParentFile().exists())
                this.file.getParentFile().mkdirs();

            try (FileOutputStream outputStream = new FileOutputStream(this.file)) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }
            }

        } catch (Exception e) {
            throw new ConfigurationWriteException("Unable to create default config file", e);
        }
        return this;
    }

    @Override
    public String getString(String path) {
        String string = super.getString(path);
        if (string != null)
            return string.replace("&", "§");
        return null;
    }

    @Override
    public String getString(String path, String def) {
        String string = super.getString(path, def);
        if (string != null)
            return string.replace("&", "§");
        return null;
    }


    @Override
    public List<String> getStringList(String path) {
        List<String> list = super.getStringList(path);
        if (list != null)
            return list.stream().map(string -> string != null ? string.replace("&", "§") : null).collect(Collectors.toList());
        return null;
    }
}

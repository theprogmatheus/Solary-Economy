package com.redeskyller.bukkit.solaryeconomy.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Logger;
import java.util.stream.Stream;

import static com.redeskyller.bukkit.solaryeconomy.config.ConfigurationManager.LOG_FORMAT;

@RequiredArgsConstructor
@Getter
public class ConfigurationFile extends YamlConfiguration {


    private final Logger logger;
    private final File file;
    private final String resourcePath;


    public ConfigurationFile createIfNotExistsAndLoad() {
        return createIfNotExists().load();
    }

    public ConfigurationFile createIfNotExists() {
        if (this.file.exists())
            return this;

        var parent = this.file.getParentFile();
        if (parent != null && !parent.exists())
            parent.mkdirs();

        var classLoader = getClass().getClassLoader();
        var resourceName = getResourceName();

        try (var resourceStream = classLoader.getResourceAsStream(resourceName)) {
            if (resourceStream == null)
                throw new FileNotFoundException(String.format("Resource %s not found in the JarFile.", resourceName));


            Files.copy(resourceStream, this.file.toPath());
        } catch (Exception e) {
            log(String.format("Failed to create file: %s", this.file.getPath()), e);
        }
        return this;
    }

    public boolean existsDefaultResource() {
        return getClass().getClassLoader().getResource(getResourceName()) != null;
    }


    public String getResourceName() {
        return this.resourcePath == null || this.resourcePath.isBlank() ? this.file.getName() : this.resourcePath;
    }

    public ConfigurationFile load() {
        var filePath = this.file.getPath();

        log(String.format("Loading configuration file: %s", filePath));
        try {
            this.load(this.file);
            log(String.format("Configuration file loaded successfully: %s", filePath));
        } catch (IOException | InvalidConfigurationException e) {
            log(String.format("Failed to load this file: %s", filePath), e);
        }

        return this;
    }

    private void log(String message) {
        log(message, null);
    }

    private void log(String message, Throwable cause) {
        if (cause == null)
            this.logger.info(String.format(LOG_FORMAT, message));
        else {
            this.logger.severe(String.format(LOG_FORMAT, message));
            Stream.of(cause.getStackTrace()).map(StackTraceElement::toString).forEach(line -> this.logger.severe("  at ".concat(line)));
        }
    }

}
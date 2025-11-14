package com.redeskyller.bukkit.solaryeconomy.config;

import lombok.RequiredArgsConstructor;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class ConfigurationManager {


    public static final String LOG_FORMAT = "[CONFIG] %s";

    private final Logger logger;
    private final File dataFolder;
    private final Map<String, ConfigurationFile> configs = new ConcurrentHashMap<>();
    private final List<Class<?>> configClasses = new ArrayList<>();


    public ConfigurationManager mapConfigurationClasses() {
        for (var configClass : configClasses) {
            var configAnnotation = getConfigurationAnnotation(configClass);

            if (configAnnotation == null)
                continue;

            var configName = configAnnotation.value();
            var configResourcePath = configAnnotation.resourcePath();

            if (configName == null || configName.isBlank())
                continue;

            var configFile = configResourcePath == null || configResourcePath.isBlank() ?
                    register(configName) : register(configName, configResourcePath);

            Stream.of(configClass.getFields()).forEach(field -> injectFieldConfigValue(field, configFile));
        }
        return this;
    }

    private Configuration getConfigurationAnnotation(Class<?> configClass) {
        if (configClass == null)
            return null;
        return configClass.getAnnotation(Configuration.class);
    }

    private void injectFieldConfigValue(Field field, ConfigurationFile config) {
        if (field == null || config == null)
            return;

        var modifiers = field.getModifiers();
        if (!(Modifier.isPublic(modifiers) && Modifier.isStatic(modifiers) &&
                ConfigurationHolder.class.isAssignableFrom(field.getType())))
            return;

        try {
            field.setAccessible(true);
            if (field.get(null) instanceof ConfigurationHolder<?>) {
                var holder = (ConfigurationHolder<?>) field.get(null);
                var configValue = config.get(holder.getPath());
                var expectedType = holder.getType();

                Object coercedValue = tryCoerce(configValue, expectedType);

                if (coercedValue != null)
                    holder.setValue(coercedValue);
                else
                    logger.warning(String.format(LOG_FORMAT, "Type mismatch on config path '" + holder.getPath() + "'. Expected " + holder.getType().getSimpleName() + ", got " + (configValue != null ? configValue.getClass().getSimpleName() : "null")));
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


    private Object tryCoerce(Object value, Class<?> expectedType) {
        if (value == null)
            return null;

        if (expectedType.isInstance(value))
            return value;


        // Converter Number para outro Number esperado (exemplo: Integer → Double)
        if (value instanceof Number) {
            Number number = (Number) value;
            if (expectedType == Double.class || expectedType == double.class) {
                return number.doubleValue();
            } else if (expectedType == Integer.class || expectedType == int.class) {
                return number.intValue();
            } else if (expectedType == Long.class || expectedType == long.class) {
                return number.longValue();
            } else if (expectedType == Float.class || expectedType == float.class) {
                return number.floatValue();
            } else if (expectedType == Short.class || expectedType == short.class) {
                return number.shortValue();
            } else if (expectedType == Byte.class || expectedType == byte.class) {
                return number.byteValue();
            }
        }

        if (expectedType == String.class)
            return String.valueOf(value);


        if ((expectedType == Boolean.class || expectedType == boolean.class) && value instanceof String)
            return Boolean.parseBoolean((String) value);

        return null;
    }


    public ConfigurationManager addConfigurationClass(Class<?> configClass) {
        if (isConfigurationClass(configClass))
            configClasses.add(configClass);
        return this;
    }

    public boolean isConfigurationClass(Class<?> clazz) {
        return clazz == null || clazz.isAnnotationPresent(Configuration.class);
    }

    public ConfigurationFile register(String name) {
        return register(name, name);
    }

    public ConfigurationFile register(String name, String resourcePath) {
        var file = new File(dataFolder, name);

        var config = new ConfigurationFile(logger, file, resourcePath)
                .createIfNotExistsAndLoad();

        configs.put(name.toLowerCase(), config);
        return config;
    }

    public ConfigurationFile get(String name) {
        return configs.get(name.toLowerCase());
    }

    public void reload(String name) {
        var config = get(name);
        if (config != null)
            config.load();
    }

    public void reloadAll() {
        configs.values().forEach(ConfigurationFile::load);
    }

    public boolean isRegistered(String name) {
        return configs.containsKey(name.toLowerCase());
    }

}
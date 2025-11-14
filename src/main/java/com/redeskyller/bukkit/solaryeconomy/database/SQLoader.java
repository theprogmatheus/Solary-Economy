package com.redeskyller.bukkit.solaryeconomy.database;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class SQLoader {

    private final String prefix;
    private final String dialect;
    private final Map<String, String> cache = new ConcurrentHashMap<>();

    public SQLoader(String prefix, String dialect) {
        this.prefix = prefix == null ? "" : prefix;
        this.dialect = normalizeDialect(dialect);
    }

    private String normalizeDialect(String d) {
        if (d == null) return "sqlite";
        String lower = d.trim().toLowerCase();
        if (lower.contains("sqlite")) return "sqlite";
        return "mysql";
    }

    /**
     * Load SQL content from resource, applying %prefix% replacement.
     * The path can be:
     * - "table/create" (will become "sql/{dialect}/table/create.sql")
     * - "sql/mysql/table/create.sql" (absolute inside resources)
     */
    public String load(String path) {
        Objects.requireNonNull(path, "path");
        String key = dialect + ":" + path;
        String cached = cache.get(key);
        if (cached != null) return cached;

        String resourcePath = buildResourcePath(path);
        String sql = readResource(resourcePath).replace("%prefix%", prefix);
        cache.put(key, sql);
        return sql;
    }

    private String buildResourcePath(String path) {
        String p = path.trim();
        if (!p.startsWith("sql/")) {
            // ensure .sql extension
            if (!p.endsWith(".sql")) p = p + ".sql";
            return "sql/" + dialect + "/" + p;
        } else {
            // user provided full resource path
            if (!p.endsWith(".sql")) p = p + ".sql";
            return p;
        }
    }

    private String readResource(String resourcePath) {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(resourcePath)) {
            if (is == null) {
                throw new IllegalArgumentException("SQL resource not found: " + resourcePath);
            }
            byte[] bytes = is.readAllBytes();
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public void clearCache() {
        cache.clear();
    }

    public boolean isCached(String path) {
        String key = dialect + ":" + path;
        return cache.containsKey(key);
    }
}
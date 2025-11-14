package com.redeskyller.bukkit.solaryeconomy.util;

import java.nio.ByteBuffer;
import java.util.UUID;

public class UUIDUtils {

    private UUIDUtils() {
    }

    public static byte[] uuidToBytes(UUID uuid) {
        ByteBuffer buffer = ByteBuffer.allocate(16);
        buffer.putLong(uuid.getMostSignificantBits());
        buffer.putLong(uuid.getLeastSignificantBits());
        return buffer.array();
    }

    public static UUID bytesToUuid(byte[] bytes) {
        if (bytes == null || bytes.length != 16) {
            throw new IllegalArgumentException("UUID byte array must be 16 bytes");
        }

        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        long high = buffer.getLong();
        long low = buffer.getLong();
        return new UUID(high, low);
    }

    public static byte[] uuidStringToBytes(String uuidString) {
        return uuidToBytes(UUID.fromString(uuidString));
    }

    public static String bytesToUuidString(byte[] bytes) {
        return bytesToUuid(bytes).toString();
    }
}

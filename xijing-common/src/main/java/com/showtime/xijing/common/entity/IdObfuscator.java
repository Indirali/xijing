package com.showtime.xijing.common.entity;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class IdObfuscator {
    private static final byte[] CHECKSUM_SALT = toBytes("X;#tqa>k17iJ:jUKO@g!;uA&B%C;q`+yKANd");

    // The least significant 32 bits of a 64-bit ID are shuffled based on a 2-byte checksum.
    // The most significant 32 bits remain the same.
    private static final byte[][][] SHUFFLED_BIT_INDEXES;

    // long ID (8 bytes) + checksum (2 bytes)
    private static final int ENCODED_BYTES = 8 + 2;

    // bit masks to retrieve most significant 32 bits of a 64-bit integer
    private static final long MOST_SIG_MASK = ((1L << 32) - 1) << 32;

    public static final Charset UTF8_CHARSET = StandardCharsets.UTF_8;

    static {
        List<Byte> normalBitIndexes = new ArrayList<>();
        for (byte b = 0; b < Integer.SIZE; b++) {
            normalBitIndexes.add(b);
        }

        SHUFFLED_BIT_INDEXES = new byte[256][256][Integer.SIZE];
        for (int i = 0; i < 256; i++) {
            for (int j = 0; j < 256; j++) {
                List<Byte> bitIndexes = new ArrayList<>(normalBitIndexes);
                Collections.shuffle(bitIndexes, new Random((i << 8) + j));
                for (int k = 0; k < Integer.SIZE; k++) {
                    SHUFFLED_BIT_INDEXES[i][j][k] = bitIndexes.get(k);
                }
            }
        }
    }

    public static String encode(long id) {
        return Base62.encode(prepareForEncode(id));
    }

    public static String encodeDecFormat(long id) {
        return new BigInteger(1, prepareForEncode(id)).toString();
    }

    public static byte[] toBytes(String s) {
        return s.getBytes(UTF8_CHARSET);
    }

    private static byte[] prepareForEncode(long id) {
        byte[] checksum = computeChecksum(id);

        long shuffledId = shuffle(id, checksum);

        ByteBuffer bb = ByteBuffer.allocate(ENCODED_BYTES);
        bb.putLong(shuffledId);
        bb.put(checksum);
        return bb.array();
    }

    public static long decode(String idStr) {
        ByteBuffer bb = ByteBuffer.wrap(decodeBase62(idStr));

        long shuffledId = bb.getLong();
        byte[] checksum = new byte[2];
        bb.get(checksum);

        long id = unShuffle(shuffledId, checksum);

        if (!Arrays.equals(checksum, computeChecksum(id))) {
            throw new IllegalArgumentException("Checksum mismatch for encoded ID string: " + idStr);
        }

        return id;
    }

    private static byte[] decodeBase62(String idStr) {
        byte[] b = Base62.decode(idStr);
        if (b[0] == 0) {
            // throw away the sign byte
            b = Arrays.copyOfRange(b, 1, b.length);
        }
        if (b.length > ENCODED_BYTES) {
            throw new IllegalArgumentException("Invalid obfuscated ID: " + idStr);
        }
        if (b.length < ENCODED_BYTES) {
            byte[] nb = new byte[ENCODED_BYTES];
            System.arraycopy(b, 0, nb, nb.length-b.length, b.length);
            return nb;
        }
        return b;
    }

    private static long shuffle(long id, byte[] checksum) {
        byte[] bitIndexes = getBitIndexes(checksum);
        long shuffledId = 0;
        for (int i = 0; i < Integer.SIZE; i++) {
            shuffledId |= ((id >>> bitIndexes[i]) & 1) << i;
        }
        shuffledId |= id & MOST_SIG_MASK;
        return shuffledId;
    }

    private static long unShuffle(long shuffledId, byte[] checksum) {
        byte[] bitIndexes = getBitIndexes(checksum);
        long id = 0;
        for (int i = 0; i < Integer.SIZE; i++) {
            id |= ((shuffledId >>> i) & 1) << bitIndexes[i];
        }
        id |= shuffledId & MOST_SIG_MASK;
        return id;
    }

    private static byte[] computeChecksum(long id) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        ByteBuffer bb = ByteBuffer.allocate(8);
        bb.putLong(id);

        md.update(bb.array());
        return Arrays.copyOf(md.digest(CHECKSUM_SALT), 2);
    }

    private static byte[] getBitIndexes(byte[] checksum) {
        final int minByte = Byte.MIN_VALUE;
        return SHUFFLED_BIT_INDEXES[checksum[0] - minByte][checksum[1] - minByte];
    }

    private static class Base62 {
        private static final char[] ALPHABET;
        private static final BigInteger ALPHABET_SIZE;

        static {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i <= 9; i++) {
                sb.append(i);
            }
            for (char c = 'a'; c <= 'z'; c++) {
                sb.append(c);
            }
            for (char c = 'A'; c <= 'Z'; c++) {
                sb.append(c);
            }
            ALPHABET = sb.toString().toCharArray();
            ALPHABET_SIZE = BigInteger.valueOf(ALPHABET.length);
        }

        public static String encode(byte[] b) {
            BigInteger n = new BigInteger(1, b);
            StringBuilder result = new StringBuilder();
            while (n.signum() == 1) {
                BigInteger[] d = n.divideAndRemainder(ALPHABET_SIZE);
                result.append(ALPHABET[d[1].intValue()]);
                n = d[0];
            }
            return result.length() == 0 ? Character.toString(ALPHABET[0]) : result.reverse().toString();
        }

        private static int decode(char c) {
            if (c >= '0' && c <= '9') {
                return c - '0';
            } else if (c >= 'a' && c <= 'z') {
                return (c - 'a') + ('9' - '0' + 1);
            } else if (c >= 'A' && c <= 'Z') {
                return (c - 'A') + ('9' - '0' + 1) + ('z' - 'a' + 1);
            } else {
                throw new IllegalArgumentException("Invalid base62 character: " + c);
            }
        }

        public static byte[] decode(String s) {
            BigInteger n = BigInteger.valueOf(0);
            for (int i = 0; i < s.length(); i++) {
                n = n.multiply(ALPHABET_SIZE).add(BigInteger.valueOf(decode(s.charAt(i))));
            }
            return n.toByteArray();
        }
    }
}

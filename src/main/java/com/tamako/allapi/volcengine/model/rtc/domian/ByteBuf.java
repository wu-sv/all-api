package com.tamako.allapi.volcengine.model.rtc.domian;


import lombok.NoArgsConstructor;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Map;
import java.util.TreeMap;

/**
 * ByteBuf
 *
 * @author Tamako
 * @since 2024/11/12 16:00
 */
@NoArgsConstructor
public class ByteBuf {
    /**
     * buffer
     */
    ByteBuffer buffer = ByteBuffer.allocate(1024).order(ByteOrder.LITTLE_ENDIAN);

    /**
     * construct with bytes
     *
     * @param bytes bytes
     */
    public ByteBuf(byte[] bytes) {
        this.buffer = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN);
    }

    /**
     * get buffer
     *
     * @return buffer
     */
    public byte[] asBytes() {
        byte[] out = new byte[buffer.position()];
        buffer.rewind();
        buffer.get(out, 0, out.length);
        return out;
    }

    /**
     * pack short
     * packUint16
     *
     * @param v short
     * @return this
     */
    public ByteBuf put(short v) {
        buffer.putShort(v);
        return this;
    }

    /**
     * pack bytes
     *
     * @param v bytes
     * @return this
     */
    public ByteBuf put(byte[] v) {
        put((short) v.length);
        buffer.put(v);
        return this;
    }

    /**
     * pack int
     * packUint32
     *
     * @param v int
     * @return this
     */
    public ByteBuf put(int v) {
        buffer.putInt(v);
        return this;
    }

    /**
     * pack long
     *
     * @param v long
     * @return this
     */
    public ByteBuf put(long v) {
        buffer.putLong(v);
        return this;
    }

    /**
     * pack string
     *
     * @param v string
     * @return this
     */
    public ByteBuf put(String v) {
        return put(v.getBytes());
    }

    /**
     * pack map
     *
     * @param extra extra
     * @return this
     */
    public ByteBuf put(TreeMap<Short, String> extra) {
        put((short) extra.size());

        for (Map.Entry<Short, String> pair : extra.entrySet()) {
            put(pair.getKey());
            put(pair.getValue());
        }

        return this;
    }

    /**
     * pack int map
     *
     * @param extra extra
     * @return this
     */
    public ByteBuf putIntMap(TreeMap<Short, Integer> extra) {
        put((short) extra.size());

        for (Map.Entry<Short, Integer> pair : extra.entrySet()) {
            put(pair.getKey());
            put(pair.getValue());
        }

        return this;
    }

    /**
     * read short
     *
     * @return short
     */
    public short readShort() {
        return buffer.getShort();
    }

    /**
     * read int
     *
     * @return int
     */
    public int readInt() {
        return buffer.getInt();
    }

    /**
     * read bytes
     *
     * @return bytes
     */
    public byte[] readBytes() {
        short length = readShort();
        byte[] bytes = new byte[length];
        buffer.get(bytes);
        return bytes;
    }

    /**
     * read string
     *
     * @return string
     */
    public String readString() {
        byte[] bytes = readBytes();
        return new String(bytes);
    }

    /**
     * read map
     *
     * @return map
     */
    public TreeMap<Short, String> readMap() {
        TreeMap<Short, String> map = new TreeMap<>();
        short length = readShort();
        for (short i = 0; i < length; ++i) {
            short k = readShort();
            String v = readString();
            map.put(k, v);
        }
        return map;
    }

    /**
     * read int map
     *
     * @return int map
     */
    public TreeMap<Short, Integer> readIntMap() {
        TreeMap<Short, Integer> map = new TreeMap<>();
        short length = readShort();
        for (short i = 0; i < length; ++i) {
            short k = readShort();
            Integer v = readInt();
            map.put(k, v);
        }
        return map;
    }
}

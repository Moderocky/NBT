package mx.kenzie.nbt;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

public record NBTByteArray(byte[] value) implements NBTValue<byte[]>, NBT, NBTArray<Byte> {
    public NBTByteArray(Object value) {
        this((byte[]) value);
    }

    public NBTByteArray(InputStream stream) throws IOException {
        this(decodeBytes(stream));
    }

    static byte[] decodeBytes(InputStream stream) throws IOException {
        final byte[] bytes = new byte[NBTInt.decodeInt(stream)];
        final int read = stream.read(bytes);
        assert read == bytes.length;
        return bytes;
    }

    @Override
    public String toString() {
        return Arrays.toString(value);
    }

    @Override
    public void write(OutputStream stream) throws IOException {
        NBTInt.encodeInt(stream, value.length);
        stream.write(value);
    }

    @Override
    public Tag tag() {
        return Tag.BYTE_ARRAY;
    }

    @Override
    public Byte[] toArray() {
        final Byte[] bytes = new Byte[value.length];
        for (int i = 0; i < value.length; i++) bytes[i] = value[i];
        return bytes;
    }

    @Override
    public int size() {
        return value.length;
    }

}

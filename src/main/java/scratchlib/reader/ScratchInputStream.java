package scratchlib.reader;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;


/**
 * Extension of {@code InputStream} for reading more types of data than just
 * bytes. Behaves as a wrapper for any other desired stream.
 */
public class ScratchInputStream extends InputStream
{
    private final DataInputStream in;

    /**
     * @param in The input stream to wrap.
     */
    public ScratchInputStream(InputStream in)
    {
        this.in = in instanceof DataInputStream ? (DataInputStream) in
                : new DataInputStream(in);
    }

    @Override
    public int read() throws IOException
    {
        return in.read();
    }

    @Override
    public long skip(long n) throws IOException
    {
        return in.skip(n);
    }

    @Override
    public int available() throws IOException
    {
        return in.available();
    }

    @Override
    public void close() throws IOException
    {
        in.close();
    }

    @Override
    public synchronized void mark(int readlimit)
    {
        in.mark(readlimit);
    }

    @Override
    public synchronized void reset() throws IOException
    {
        in.reset();
    }

    @Override
    public boolean markSupported()
    {
        return in.markSupported();
    }

    /**
     * Reads the given amount of bytes into an array of exactly that length.
     * 
     * @param length The amount of bytes to read.
     * @return The bytes read.
     * @throws IOException
     */
    public byte[] readFully(int length) throws IOException
    {
        byte[] bytes = new byte[length];
        in.readFully(bytes);

        return bytes;
    }

    /**
     * Reads a US-ASCII string of the given length, measured in bytes.
     * 
     * @param length The string length in bytes.
     * @return The string read.
     * @throws IOException
     */
    public String readString(int length) throws IOException
    {
        return new String(readFully(length), StandardCharsets.US_ASCII);
    }

    /**
     * Reads 4 bytes (big-endian) and converts them to an int.
     * 
     * @return The int read.
     * @throws IOException
     */
    public int read32bitUnsignedInt() throws IOException
    {
        return in.readInt();
    }

    /**
     * Reads 3 bytes (big-endian) and converts them to an int.
     * 
     * @return The int read.
     * @throws IOException
     */
    public int read24bitUnsignedInt() throws IOException
    {
        byte b0 = in.readByte();
        byte b1 = in.readByte();
        byte b2 = in.readByte();

        return ((b0 & 0xFF) << 16) | ((b1 & 0xFF) << 8) | (b2 & 0xFF);
    }

    /**
     * Reads 2 bytes (big-endian) and converts them to an int.
     * 
     * @return The int read.
     * @throws IOException
     */
    public int read16bitUnsignedInt() throws IOException
    {
        return in.readUnsignedShort();
    }

    /**
     * Reads 8 bytes (big-endian) and converts them to a double.
     * 
     * @return The double read.
     * @throws IOException
     */
    public double read64bitDecimal() throws IOException
    {
        return in.readDouble();
    }
}

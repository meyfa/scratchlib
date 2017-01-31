package de.jangobrick.scratchlib.writer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;


/**
 * Extension of {@code ByteArrayOutputStream} for writing more types of data
 * than just bytes.
 */
public class ScratchOutputStream extends ByteArrayOutputStream
{
    /**
     * Converts the string to US-ASCII bytes and writes them out.
     * 
     * @param s The string to write.
     * @throws IOException
     */
    public void writeString(String s) throws IOException
    {
        write(s.getBytes(StandardCharsets.US_ASCII));
    }

    /**
     * Converts the int to 4 bytes (big-endian) and writes them out.
     * 
     * @param i The int to write.
     * @throws IOException
     */
    public void write32bitUnsignedInt(int i) throws IOException
    {
        write(ByteBuffer.allocate(4).putInt(i).array());
    }

    /**
     * Converts the int to 3 bytes (big-endian) and writes them out. The fourth
     * byte is discarded in the process, effectively reducing the number range.
     * 
     * @param i The int to write.
     * @throws IOException
     */
    public void write24bitUnsignedInt(int i) throws IOException
    {
        write(ByteBuffer.allocate(4).putInt(i).array(), 1, 3);
    }

    /**
     * Converts the int to 2 bytes (big-endian) and writes them out. The third
     * and fourth bytes are discarded in the process, and so the number is
     * equivalent to a short.
     * 
     * @param i The int to write.
     * @throws IOException
     */
    public void write16bitUnsignedInt(int i) throws IOException
    {
        write(ByteBuffer.allocate(4).putInt(i).array(), 2, 2);
    }

    /**
     * Converts the double to 8 bytes (big-endian) and writes them out.
     * 
     * @param d The double to write.
     * @throws IOException
     */
    public void write64bitDecimal(double d) throws IOException
    {
        write(ByteBuffer.allocate(8).putDouble(d).array());
    }
}

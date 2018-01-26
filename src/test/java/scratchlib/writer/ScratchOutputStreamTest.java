package scratchlib.writer;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.Test;


public class ScratchOutputStreamTest
{
    private static class MockOutputStream extends ByteArrayOutputStream
    {
        private int flushed, closed;

        @Override
        public void flush() throws IOException
        {
            super.flush();
            ++flushed;
        }

        @Override
        public void close() throws IOException
        {
            super.close();
            ++closed;
        }
    }

    @Test
    public void writesBytes() throws IOException
    {
        MockOutputStream out = new MockOutputStream();

        @SuppressWarnings("resource")
        ScratchOutputStream obj = new ScratchOutputStream(out);
        obj.write(0x00);
        obj.write(new byte[] { 0x7F, (byte) 0xFF });
        obj.write(new byte[] { 0x00, 0x01, 0x02, 0x03 }, 1, 2);

        assertEquals(0, out.flushed);
        assertEquals(0, out.closed);
        assertArrayEquals(new byte[] { 0x00, 0x7F, (byte) 0xFF, 0x01, 0x02 },
                out.toByteArray());
    }

    @Test
    public void flushes() throws IOException
    {
        MockOutputStream out = new MockOutputStream();

        @SuppressWarnings("resource")
        ScratchOutputStream obj = new ScratchOutputStream(out);
        obj.flush();

        assertEquals(1, out.flushed);
        assertEquals(0, out.closed);
        assertArrayEquals(new byte[] {}, out.toByteArray());
    }

    @Test
    public void closes() throws IOException
    {
        MockOutputStream out = new MockOutputStream();

        ScratchOutputStream obj = new ScratchOutputStream(out);
        obj.close();

        assertEquals(1, out.flushed);
        assertEquals(1, out.closed);
        assertArrayEquals(new byte[] {}, out.toByteArray());
    }

    @Test
    public void writesString() throws IOException
    {
        MockOutputStream out = new MockOutputStream();

        @SuppressWarnings("resource")
        ScratchOutputStream obj = new ScratchOutputStream(out);
        obj.writeString("BloxExpV01");

        assertEquals(0, out.flushed);
        assertEquals(0, out.closed);
        assertArrayEquals(
                new byte[] { 'B', 'l', 'o', 'x', 'E', 'x', 'p', 'V', '0', '1' },
                out.toByteArray());
    }

    @Test
    public void writesInt32() throws IOException
    {
        MockOutputStream out = new MockOutputStream();

        @SuppressWarnings("resource")
        ScratchOutputStream obj = new ScratchOutputStream(out);
        obj.write32bitUnsignedInt(0);
        obj.write32bitUnsignedInt(2_147_483_647);
        obj.write32bitUnsignedInt(-1);
        obj.write32bitUnsignedInt(-2_147_483_648);

        assertEquals(0, out.flushed);
        assertEquals(0, out.closed);
        assertArrayEquals(new byte[] {
                // 0
                0, 0, 0, 0,
                // 2_147_483_647
                0x7F, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
                // -1
                (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
                // -2_147_483_648
                (byte) 0x80, 0x00, 0x00, 0x00//
        }, out.toByteArray());
    }

    @Test
    public void writesUnsignedInt24() throws IOException
    {
        MockOutputStream out = new MockOutputStream();

        @SuppressWarnings("resource")
        ScratchOutputStream obj = new ScratchOutputStream(out);
        obj.write24bitUnsignedInt(0);
        obj.write24bitUnsignedInt((1 << 24) - 1);

        assertEquals(0, out.flushed);
        assertEquals(0, out.closed);
        assertArrayEquals(new byte[] {
                // 0
                0, 0, 0,
                // 2^24-1
                (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,//
        }, out.toByteArray());
    }

    @Test
    public void writesUnsignedInt16() throws IOException
    {
        MockOutputStream out = new MockOutputStream();

        @SuppressWarnings("resource")
        ScratchOutputStream obj = new ScratchOutputStream(out);
        obj.write16bitUnsignedInt(0);
        obj.write16bitUnsignedInt((1 << 16) - 1);

        assertEquals(0, out.flushed);
        assertEquals(0, out.closed);
        assertArrayEquals(new byte[] {
                // 0
                0, 0,
                // 2^16-1
                (byte) 0xFF, (byte) 0xFF,//
        }, out.toByteArray());
    }

    @Test
    public void writesDecimal64() throws IOException
    {
        MockOutputStream out = new MockOutputStream();

        @SuppressWarnings("resource")
        ScratchOutputStream obj = new ScratchOutputStream(out);
        obj.write64bitDecimal(0);
        obj.write64bitDecimal(1.2345);
        obj.write64bitDecimal(-1.2345);
        obj.write64bitDecimal(1_234_567_890.0);

        assertEquals(0, out.flushed);
        assertEquals(0, out.closed);
        assertArrayEquals(new byte[] {
                // 0
                0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
                // 1.2345
                0x3F, (byte) 0xF3, (byte) 0xC0, (byte) 0x83, 0x12, 0x6E,
                (byte) 0x97, (byte) 0x8D,
                // -1.2345
                (byte) 0xBF, (byte) 0xF3, (byte) 0xC0, (byte) 0x83, 0x12, 0x6E,
                (byte) 0x97, (byte) 0x8D,
                // 1_234_567_890.0
                (byte) 0x41, (byte) 0xD2, 0x65, (byte) 0x80, (byte) 0xB4,
                (byte) 0x80, 0x00, 0x00,//
        }, out.toByteArray());
    }
}

package scratchlib.reader;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.Test;


public class ScratchInputStreamTest
{
    private static class MockInputStream extends ByteArrayInputStream
    {
        private int closed;
        private boolean setMarkSupported = true;

        public MockInputStream(byte[] bytes)
        {
            super(bytes);
        }

        @Override
        public void close() throws IOException
        {
            super.close();
            ++closed;
        }

        private int getPosition()
        {
            return pos;
        }

        private int getMark()
        {
            return mark;
        }

        @Override
        public boolean markSupported()
        {
            return setMarkSupported;
        }
    }

    @Test
    public void readsBytes() throws IOException
    {
        MockInputStream in = new MockInputStream(new byte[] { 1, 2, 3 });
        @SuppressWarnings("resource")
        ScratchInputStream obj = new ScratchInputStream(in);

        assertEquals(1, obj.read());
        assertEquals(2, obj.read());
        assertEquals(3, obj.read());
        assertEquals(-1, obj.read());

        assertEquals(0, in.closed);
    }

    @Test
    public void skips() throws IOException
    {
        MockInputStream in = new MockInputStream(new byte[] { 1, 2, 3 });
        @SuppressWarnings("resource")
        ScratchInputStream obj = new ScratchInputStream(in);

        obj.skip(2);
        assertEquals(2, in.getPosition());

        assertEquals(0, in.closed);
    }

    @Test
    public void returnsAvailable() throws IOException
    {
        MockInputStream in = new MockInputStream(new byte[] { 1, 2, 3 });
        @SuppressWarnings("resource")
        ScratchInputStream obj = new ScratchInputStream(in);

        assertEquals(3, obj.available());

        assertEquals(0, in.closed);
    }

    @Test
    public void closes() throws IOException
    {
        MockInputStream in = new MockInputStream(new byte[] {});
        ScratchInputStream obj = new ScratchInputStream(in);

        obj.close();
        assertEquals(1, in.closed);
    }

    @Test
    public void marks() throws IOException
    {
        MockInputStream in = new MockInputStream(new byte[] { 1, 2, 3 });
        @SuppressWarnings("resource")
        ScratchInputStream obj = new ScratchInputStream(in);

        obj.skip(2);
        obj.mark(1);
        assertEquals(2, in.getMark());

        assertEquals(0, in.closed);
    }

    @Test
    public void resets() throws IOException
    {
        MockInputStream in = new MockInputStream(new byte[] { 1, 2, 3, 4, 5 });
        @SuppressWarnings("resource")
        ScratchInputStream obj = new ScratchInputStream(in);

        obj.skip(2);
        obj.mark(10);
        assertEquals(3, obj.read());
        assertEquals(4, obj.read());

        obj.reset();
        assertEquals(3, obj.read());

        assertEquals(0, in.closed);
    }

    @Test
    public void returnsMarkSupported()
    {
        MockInputStream in = new MockInputStream(new byte[] { 1, 2, 3, 4, 5 });
        @SuppressWarnings("resource")
        ScratchInputStream obj = new ScratchInputStream(in);

        in.setMarkSupported = true;
        assertTrue(obj.markSupported());

        in.setMarkSupported = false;
        assertFalse(obj.markSupported());
    }

    @Test
    public void readsFully() throws IOException
    {
        MockInputStream in = new MockInputStream(new byte[] { 1, 2, 3, 4, 5 });
        @SuppressWarnings("resource")
        ScratchInputStream obj = new ScratchInputStream(in);

        assertArrayEquals(new byte[] { 1, 2, 3 }, obj.readFully(3));

        assertEquals(0, in.closed);
    }

    @Test
    public void readsString() throws IOException
    {
        MockInputStream in = new MockInputStream(new byte[] { 'H', 'e', 'l',
                'l', 'o', ' ', 'W', 'o', 'r', 'l', 'd', 1, 2, 3 });
        @SuppressWarnings("resource")
        ScratchInputStream obj = new ScratchInputStream(in);

        assertEquals("Hello World", obj.readString(11));

        assertEquals(0, in.closed);
    }

    @Test
    public void readsInt32() throws IOException
    {
        MockInputStream in = new MockInputStream(new byte[] {
                // 0
                0, 0, 0, 0,
                // 2_147_483_647
                0x7F, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
                // -1
                (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
                // -2_147_483_648
                (byte) 0x80, 0x00, 0x00, 0x00//
        });
        @SuppressWarnings("resource")
        ScratchInputStream obj = new ScratchInputStream(in);

        assertEquals(0, obj.read32bitUnsignedInt());
        assertEquals(2_147_483_647, obj.read32bitUnsignedInt());
        assertEquals(-1, obj.read32bitUnsignedInt());
        assertEquals(-2_147_483_648, obj.read32bitUnsignedInt());

        assertEquals(0, in.closed);
    }

    @Test
    public void readsUnsignedInt24() throws IOException
    {
        MockInputStream in = new MockInputStream(new byte[] {
                // 0
                0, 0, 0,
                // 2^24-1
                (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,//
        });
        @SuppressWarnings("resource")
        ScratchInputStream obj = new ScratchInputStream(in);

        assertEquals(0, obj.read24bitUnsignedInt());
        assertEquals((1 << 24) - 1, obj.read24bitUnsignedInt());

        assertEquals(0, in.closed);
    }

    @Test
    public void readsUnsignedInt16() throws IOException
    {
        MockInputStream in = new MockInputStream(new byte[] {
                // 0
                0, 0,
                // 2^16-1
                (byte) 0xFF, (byte) 0xFF,//
        });
        @SuppressWarnings("resource")
        ScratchInputStream obj = new ScratchInputStream(in);

        assertEquals(0, obj.read16bitUnsignedInt());
        assertEquals((1 << 16) - 1, obj.read16bitUnsignedInt());

        assertEquals(0, in.closed);
    }

    @Test
    public void readsDecimal46() throws IOException
    {
        MockInputStream in = new MockInputStream(new byte[] {
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
        });
        @SuppressWarnings("resource")
        ScratchInputStream obj = new ScratchInputStream(in);

        assertEquals(0, obj.read64bitDecimal(), 0.00000001);
        assertEquals(1.2345, obj.read64bitDecimal(), 0.00000001);
        assertEquals(-1.2345, obj.read64bitDecimal(), 0.00000001);
        assertEquals(1_234_567_890.0, obj.read64bitDecimal(), 0.00000001);

        assertEquals(0, in.closed);
    }
}

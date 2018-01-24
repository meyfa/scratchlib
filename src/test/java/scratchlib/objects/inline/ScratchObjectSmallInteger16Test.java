package scratchlib.objects.inline;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

import org.junit.Test;

import scratchlib.objects.ScratchReferenceTable;
import scratchlib.project.ScratchProject;
import scratchlib.project.ScratchVersion;
import scratchlib.reader.ScratchInputStream;
import scratchlib.writer.ScratchOutputStream;


public class ScratchObjectSmallInteger16Test
{
    @Test
    public void performsTypeConversions()
    {
        ScratchObjectSmallInteger16 obj = new ScratchObjectSmallInteger16(
                (short) -42);

        assertEquals(-42, obj.doubleValue(), 0.000000001);
        assertEquals(-42, obj.intValue());
        assertEquals(new BigDecimal("-42"), obj.toBigDecimal());
        assertEquals(new BigInteger("-42"), obj.toBigInteger());
    }

    @Test
    public void writesCorrectly() throws IOException
    {
        ScratchProject project = new ScratchProject(ScratchVersion.SCRATCH14);
        ScratchObjectSmallInteger16 obj = new ScratchObjectSmallInteger16(
                (short) -42);

        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        obj.writeTo(new ScratchOutputStream(bout), new ScratchReferenceTable(),
                project);

        assertArrayEquals(new byte[] {
                // class id
                5,
                // 16-bit int
                (byte) 0xFF, (byte) 0xD6,
                // end
        }, bout.toByteArray());
    }

    @Test
    public void readsCorrectly() throws IOException
    {
        ScratchProject project = new ScratchProject(ScratchVersion.SCRATCH14);
        ScratchObjectSmallInteger16 obj = new ScratchObjectSmallInteger16();

        ByteArrayInputStream bin = new ByteArrayInputStream(new byte[] {
                // 16-bit int
                (byte) 0xFF, (byte) 0xD6,
                // end
        });
        obj.readFrom(5, new ScratchInputStream(bin), project);

        assertEquals(-42, obj.intValue());
    }
}

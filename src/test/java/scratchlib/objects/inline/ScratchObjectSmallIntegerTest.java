package scratchlib.objects.inline;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

import org.junit.jupiter.api.Test;
import scratchlib.objects.ScratchReferenceTable;
import scratchlib.project.ScratchProject;
import scratchlib.project.ScratchVersion;
import scratchlib.reader.ScratchInputStream;
import scratchlib.writer.ScratchOutputStream;

import static org.junit.jupiter.api.Assertions.*;


public class ScratchObjectSmallIntegerTest
{
    @Test
    public void performsTypeConversions()
    {
        ScratchObjectSmallInteger obj = new ScratchObjectSmallInteger(-42);

        assertEquals(-42, obj.doubleValue(), 0.000000001);
        assertEquals(-42, obj.intValue());
        assertEquals(new BigDecimal("-42"), obj.toBigDecimal());
        assertEquals(new BigInteger("-42"), obj.toBigInteger());
    }

    @Test
    public void writesCorrectly() throws IOException
    {
        ScratchProject project = new ScratchProject(ScratchVersion.SCRATCH14);
        ScratchObjectSmallInteger obj = new ScratchObjectSmallInteger(-42);

        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        obj.writeTo(new ScratchOutputStream(bout), new ScratchReferenceTable(), project);

        assertArrayEquals(new byte[] {
                // class id
                4,
                // 32-bit int
                (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xD6,
                // end
        }, bout.toByteArray());
    }

    @Test
    public void readsCorrectly() throws IOException
    {
        ScratchProject project = new ScratchProject(ScratchVersion.SCRATCH14);
        ScratchObjectSmallInteger obj = new ScratchObjectSmallInteger();

        ByteArrayInputStream bin = new ByteArrayInputStream(new byte[] {
                // 32-bit int
                (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xD6,
                // end
        });
        obj.readFrom(4, new ScratchInputStream(bin), project);

        assertEquals(-42, obj.intValue());
    }
}

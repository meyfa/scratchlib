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


public class ScratchObjectFloatTest
{
    @Test
    public void performsTypeConversions()
    {
        ScratchObjectFloat obj = new ScratchObjectFloat(-42.5);

        assertEquals(-42.5, obj.doubleValue(), 0.000000001);
        assertEquals(-42, obj.intValue());
        assertEquals(new BigDecimal("-42.5"), obj.toBigDecimal());
        assertEquals(new BigInteger("-42"), obj.toBigInteger());
    }

    @Test
    public void writesCorrectly() throws IOException
    {
        ScratchProject project = new ScratchProject(ScratchVersion.SCRATCH14);
        ScratchObjectFloat obj = new ScratchObjectFloat(-42.5);

        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        obj.writeTo(new ScratchOutputStream(bout), new ScratchReferenceTable(),
                project);

        assertArrayEquals(new byte[] {
                // class id
                8,
                // ieee-754 double
                (byte) 0xC0, 0x45, 0x40, 0x00, 0x00, 0x00, 0x00, 0x00,
                // end
        }, bout.toByteArray());
    }

    @Test
    public void readsCorrectly() throws IOException
    {
        ScratchProject project = new ScratchProject(ScratchVersion.SCRATCH14);
        ScratchObjectFloat obj = new ScratchObjectFloat();

        ByteArrayInputStream bin = new ByteArrayInputStream(new byte[] {
                // ieee-754 double
                (byte) 0xC0, 0x45, 0x40, 0x00, 0x00, 0x00, 0x00, 0x00,
                // end
        });
        obj.readFrom(8, new ScratchInputStream(bin), project);

        assertEquals(-42.5, obj.doubleValue(), 0.000000001);
    }
}

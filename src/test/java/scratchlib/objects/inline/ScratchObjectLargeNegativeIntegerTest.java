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


public class ScratchObjectLargeNegativeIntegerTest
{
    @Test
    public void performsTypeConversions()
    {
        ScratchObjectLargeNegativeInteger obj = new ScratchObjectLargeNegativeInteger(new BigInteger("-1337"));

        assertEquals(-1337, obj.doubleValue(), 0.000000001);
        assertEquals(-1337, obj.intValue());
        assertEquals(new BigDecimal("-1337"), obj.toBigDecimal());
        assertEquals(new BigInteger("-1337"), obj.toBigInteger());
    }

    @Test
    public void throwsForPositiveSign()
    {
        assertThrows(IllegalArgumentException.class, () -> {
            new ScratchObjectLargeNegativeInteger(new BigInteger("1337"));
        });
    }

    @Test
    public void writesCorrectly() throws IOException
    {
        ScratchProject project = new ScratchProject(ScratchVersion.SCRATCH14);
        ScratchObjectLargeNegativeInteger obj = new ScratchObjectLargeNegativeInteger(new BigInteger("-1337"));

        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        obj.writeTo(new ScratchOutputStream(bout), new ScratchReferenceTable(), project);

        assertArrayEquals(new byte[] {
                // class id
                7,
                // number of bytes needed
                0, 2,
                // bytes
                0x5, 0x39,
                // end
        }, bout.toByteArray());
    }

    @Test
    public void readsCorrectly() throws IOException
    {
        ScratchProject project = new ScratchProject(ScratchVersion.SCRATCH14);
        ScratchObjectLargeNegativeInteger obj = new ScratchObjectLargeNegativeInteger();

        ByteArrayInputStream bin = new ByteArrayInputStream(new byte[] {
                // number of bytes needed
                0, 2,
                // bytes
                0x5, 0x39,
                // end
        });
        obj.readFrom(7, new ScratchInputStream(bin), project);

        assertEquals(new BigInteger("-1337"), obj.toBigInteger());
    }
}

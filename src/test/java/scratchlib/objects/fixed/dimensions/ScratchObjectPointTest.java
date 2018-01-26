package scratchlib.objects.fixed.dimensions;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.Test;

import scratchlib.objects.ScratchReferenceTable;
import scratchlib.objects.inline.ScratchObjectSmallInteger;
import scratchlib.project.ScratchProject;
import scratchlib.project.ScratchVersion;
import scratchlib.reader.ScratchInputStream;
import scratchlib.writer.ScratchOutputStream;


public class ScratchObjectPointTest
{
    @Test
    public void convertsJavaIntegers()
    {
        ScratchObjectPoint p0 = new ScratchObjectPoint(42, 37);
        assertEquals(42, p0.getX().intValue());
        assertEquals(37, p0.getY().intValue());

        ScratchObjectPoint p1 = new ScratchObjectPoint(1 << 27, 1 << 28);
        assertEquals(1 << 27, p1.getX().intValue());
        assertEquals(1 << 28, p1.getY().intValue());

        ScratchObjectPoint p2 = new ScratchObjectPoint(-70, -(1 << 28));
        assertEquals(-70, p2.getX().intValue());
        assertEquals(-(1 << 28), p2.getY().intValue());
    }

    @Test
    public void writesCorrectly() throws IOException
    {
        ScratchProject project = new ScratchProject(ScratchVersion.SCRATCH14);

        ScratchObjectPoint obj = new ScratchObjectPoint(
                new ScratchObjectSmallInteger(42),
                new ScratchObjectSmallInteger(37));

        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ScratchReferenceTable ref = new ScratchReferenceTable();

        obj.writeTo(new ScratchOutputStream(bout), ref, project);

        assertArrayEquals(new byte[] {
                // class id
                32,
                // x
                4, 0, 0, 0, 42,
                // y
                4, 0, 0, 0, 37,
                // end
        }, bout.toByteArray());
    }

    @Test
    public void readsCorrectly() throws IOException
    {
        ScratchProject project = new ScratchProject(ScratchVersion.SCRATCH14);

        ScratchObjectPoint obj = new ScratchObjectPoint();

        ByteArrayInputStream bin = new ByteArrayInputStream(new byte[] {
                // x
                4, 0, 0, 0, 42,
                // y
                4, 0, 0, 0, 37,
                // end
        });
        obj.readFrom(32, new ScratchInputStream(bin), project);

        assertEquals(42, obj.getX().intValue());
        assertEquals(37, obj.getY().intValue());
    }
}

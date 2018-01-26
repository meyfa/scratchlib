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


public class ScratchObjectRectangleTest
{
    @Test
    public void convertsJavaIntegers()
    {
        ScratchObjectRectangle r0 = new ScratchObjectRectangle(42, 37, 10, 20);
        assertEquals(42, r0.getX().intValue());
        assertEquals(37, r0.getY().intValue());
        assertEquals(10, r0.getWidth().intValue());
        assertEquals(20, r0.getHeight().intValue());

        ScratchObjectRectangle r1 = new ScratchObjectRectangle(1 << 27, 1 << 28,
                1 << 29, 1 << 30);
        assertEquals(1 << 27, r1.getX().intValue());
        assertEquals(1 << 28, r1.getY().intValue());
        assertEquals(1 << 29, r1.getWidth().intValue());
        assertEquals(1 << 30, r1.getHeight().intValue());

        ScratchObjectRectangle r2 = new ScratchObjectRectangle(-70, -(1 << 28),
                -10, -(1 << 29));
        assertEquals(-70, r2.getX().intValue());
        assertEquals(-(1 << 28), r2.getY().intValue());
        assertEquals(-10, r2.getWidth().intValue());
        assertEquals(-(1 << 29), r2.getHeight().intValue());
    }

    @Test
    public void writesCorrectly() throws IOException
    {
        ScratchProject project = new ScratchProject(ScratchVersion.SCRATCH14);

        ScratchObjectRectangle obj = new ScratchObjectRectangle(
                new ScratchObjectSmallInteger(42),
                new ScratchObjectSmallInteger(37),
                new ScratchObjectSmallInteger(10),
                new ScratchObjectSmallInteger(20));

        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ScratchReferenceTable ref = new ScratchReferenceTable();

        obj.writeTo(new ScratchOutputStream(bout), ref, project);

        assertArrayEquals(new byte[] {
                // class id
                33,
                // x
                4, 0, 0, 0, 42,
                // y
                4, 0, 0, 0, 37,
                // width
                4, 0, 0, 0, 10,
                // height
                4, 0, 0, 0, 20,
                // end
        }, bout.toByteArray());
    }

    @Test
    public void readsCorrectly() throws IOException
    {
        ScratchProject project = new ScratchProject(ScratchVersion.SCRATCH14);

        ScratchObjectRectangle obj = new ScratchObjectRectangle();

        ByteArrayInputStream bin = new ByteArrayInputStream(new byte[] {
                // x
                4, 0, 0, 0, 42,
                // y
                4, 0, 0, 0, 37,
                // width
                4, 0, 0, 0, 10,
                // height
                4, 0, 0, 0, 20,
                // end
        });
        obj.readFrom(33, new ScratchInputStream(bin), project);

        assertEquals(42, obj.getX().intValue());
        assertEquals(37, obj.getY().intValue());
        assertEquals(10, obj.getWidth().intValue());
        assertEquals(20, obj.getHeight().intValue());
    }
}

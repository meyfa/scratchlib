package scratchlib.objects.fixed.forms;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import scratchlib.objects.ScratchReferenceTable;
import scratchlib.objects.fixed.collections.ScratchObjectArray;
import scratchlib.objects.fixed.data.ScratchObjectByteArray;
import scratchlib.project.ScratchProject;
import scratchlib.project.ScratchVersion;
import scratchlib.reader.ScratchInputStream;
import scratchlib.writer.ScratchOutputStream;

import static org.junit.jupiter.api.Assertions.*;


public class ScratchObjectColorFormTest
{
    @Test
    public void convertsJavaIntegers()
    {
        ScratchObjectColorForm obj = new ScratchObjectColorForm(10, 20, 32,
                new ScratchObjectByteArray(), new ScratchObjectArray());

        assertEquals(10, obj.getWidth().intValue());
        assertEquals(20, obj.getHeight().intValue());
        assertEquals(32, obj.getDepth().intValue());
    }

    @Test
    public void writesCorrectly() throws IOException
    {
        ScratchProject project = new ScratchProject(ScratchVersion.SCRATCH14);

        ScratchObjectColorForm obj = new ScratchObjectColorForm(10, 20, 32,
                new ScratchObjectByteArray(new byte[] { 0x01, 0x02, 0x03, 0x04 }),
                new ScratchObjectArray());

        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ScratchReferenceTable ref = new ScratchReferenceTable();

        obj.createReferences(ref, project);
        obj.writeTo(new ScratchOutputStream(bout), ref, project);

        assertArrayEquals(new byte[] {
                // class id
                35,
                // width
                5, 0, 10,
                // height
                5, 0, 20,
                // depth
                5, 0, 32,
                // privateOffset
                1,
                // bits reference
                99, 0, 0, 2,
                // colorMap reference
                99, 0, 0, 3,
                // end
        }, bout.toByteArray());
    }

    @Test
    public void readsCorrectly() throws IOException
    {
        ScratchProject project = new ScratchProject(ScratchVersion.SCRATCH14);

        ScratchObjectColorForm obj = new ScratchObjectColorForm();

        ByteArrayInputStream bin = new ByteArrayInputStream(new byte[] {
                // width
                5, 0, 10,
                // height
                5, 0, 20,
                // depth
                5, 0, 32,
                // privateOffset
                1,
                // bits reference
                99, 0, 0, 2,
                // colorMap reference
                99, 0, 0, 3,
                // end
        });
        obj.readFrom(35, new ScratchInputStream(bin), project);

        assertEquals(10, obj.getWidth().intValue());
        assertEquals(20, obj.getHeight().intValue());
        assertEquals(32, obj.getDepth().intValue());

        ScratchReferenceTable ref = new ScratchReferenceTable();
        ref.insert(obj);
        ScratchObjectByteArray bits = new ScratchObjectByteArray();
        ref.insert(bits);
        ScratchObjectArray colorMap = new ScratchObjectArray();
        ref.insert(colorMap);

        obj.resolveReferences(ref);

        assertSame(bits, obj.getBits());
        assertSame(colorMap, obj.getColorMap());
    }
}

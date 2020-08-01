package scratchlib.objects.fixed.data;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import scratchlib.objects.ScratchReferenceTable;
import scratchlib.project.ScratchProject;
import scratchlib.project.ScratchVersion;
import scratchlib.reader.ScratchInputStream;
import scratchlib.writer.ScratchOutputStream;

import static org.junit.jupiter.api.Assertions.*;


public class ScratchObjectBitmapTest
{
    @Test
    public void throwsForIllegalLength()
    {
        assertThrows(IllegalArgumentException.class, () -> {
            new ScratchObjectBitmap(new byte[] { 0, 1, 2, 3, 100, 101, 102 });
        });
    }

    @Test
    public void returnsValue()
    {
        byte[] val = new byte[] { 0, 1, 2, 3, (byte) 200, (byte) 201,
                (byte) 202, (byte) 203 };
        ScratchObjectBitmap obj = new ScratchObjectBitmap(val);

        assertSame(val, obj.getValue());
    }

    @Test
    public void writesCorrectly() throws IOException
    {
        ScratchProject project = new ScratchProject(ScratchVersion.SCRATCH14);

        ScratchObjectBitmap obj = new ScratchObjectBitmap(new byte[] { 0, 1, 2,
                3, (byte) 200, (byte) 201, (byte) 202, (byte) 203 });

        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ScratchReferenceTable ref = new ScratchReferenceTable();

        obj.writeTo(new ScratchOutputStream(bout), ref, project);

        assertArrayEquals(new byte[] {
                // class id
                13,
                // length
                0, 0, 0, 2,
                // values
                0, 1, 2, 3, (byte) 200, (byte) 201, (byte) 202, (byte) 203,
                // end
        }, bout.toByteArray());
    }

    @Test
    public void readsCorrectly() throws IOException
    {
        ScratchProject project = new ScratchProject(ScratchVersion.SCRATCH14);

        ScratchObjectBitmap obj = new ScratchObjectBitmap();

        ByteArrayInputStream bin = new ByteArrayInputStream(new byte[] {
                // length
                0, 0, 0, 2,
                // values
                0, 1, 2, 3, (byte) 200, (byte) 201, (byte) 202, (byte) 203,
                // end
        });
        obj.readFrom(13, new ScratchInputStream(bin), project);

        assertArrayEquals(new byte[] { 0, 1, 2, 3, (byte) 200, (byte) 201,
                (byte) 202, (byte) 203 }, obj.getValue());
    }
}

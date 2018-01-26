package scratchlib.objects.fixed.data;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertSame;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.Test;

import scratchlib.objects.ScratchReferenceTable;
import scratchlib.project.ScratchProject;
import scratchlib.project.ScratchVersion;
import scratchlib.reader.ScratchInputStream;
import scratchlib.writer.ScratchOutputStream;


public class ScratchObjectByteArrayTest
{
    @Test
    public void returnsValue()
    {
        byte[] val = new byte[] { 42, 37, 0, 1 };
        ScratchObjectByteArray obj = new ScratchObjectByteArray(val);

        assertSame(val, obj.getValue());
    }

    @Test
    public void writesCorrectly() throws IOException
    {
        ScratchProject project = new ScratchProject(ScratchVersion.SCRATCH14);

        ScratchObjectByteArray obj = new ScratchObjectByteArray(
                new byte[] { 42, 37, 0, (byte) 0xFF });

        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ScratchReferenceTable ref = new ScratchReferenceTable();

        obj.writeTo(new ScratchOutputStream(bout), ref, project);

        assertArrayEquals(new byte[] {
                // class id
                11,
                // length
                0, 0, 0, 4,
                // values
                42, 37, 0, (byte) 0xFF,
                // end
        }, bout.toByteArray());
    }

    @Test
    public void readsCorrectly() throws IOException
    {
        ScratchProject project = new ScratchProject(ScratchVersion.SCRATCH14);

        ScratchObjectByteArray obj = new ScratchObjectByteArray();

        ByteArrayInputStream bin = new ByteArrayInputStream(new byte[] {
                // length
                0, 0, 0, 4,
                // values
                42, 37, 0, (byte) 0xFF,
                // end
        });
        obj.readFrom(11, new ScratchInputStream(bin), project);

        assertArrayEquals(new byte[] { 42, 37, 0, (byte) 0xFF },
                obj.getValue());
    }
}

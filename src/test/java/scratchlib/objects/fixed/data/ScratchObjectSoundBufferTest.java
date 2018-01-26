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


public class ScratchObjectSoundBufferTest
{
    @Test(expected = IllegalArgumentException.class)
    public void throwsForIllegalLength()
    {
        new ScratchObjectSoundBuffer(
                new byte[] { 0, 1, 100, 101, (byte) 200, });
    }

    @Test
    public void returnsValue()
    {
        byte[] val = new byte[] { 0, 1, 100, 101, (byte) 200, (byte) 201 };
        ScratchObjectSoundBuffer obj = new ScratchObjectSoundBuffer(val);

        assertSame(val, obj.getValue());
    }

    @Test
    public void writesCorrectly() throws IOException
    {
        ScratchProject project = new ScratchProject(ScratchVersion.SCRATCH14);

        ScratchObjectSoundBuffer obj = new ScratchObjectSoundBuffer(
                new byte[] { 0, 1, 100, 101, (byte) 200, (byte) 201 });

        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ScratchReferenceTable ref = new ScratchReferenceTable();

        obj.writeTo(new ScratchOutputStream(bout), ref, project);

        assertArrayEquals(new byte[] {
                // class id
                12,
                // length
                0, 0, 0, 3,
                // values
                0, 1, 100, 101, (byte) 200, (byte) 201
                // end
        }, bout.toByteArray());
    }

    @Test
    public void readsCorrectly() throws IOException
    {
        ScratchProject project = new ScratchProject(ScratchVersion.SCRATCH14);

        ScratchObjectSoundBuffer obj = new ScratchObjectSoundBuffer();

        ByteArrayInputStream bin = new ByteArrayInputStream(new byte[] {
                // length
                0, 0, 0, 3,
                // values
                0, 1, 100, 101, (byte) 200, (byte) 201
                // end
        });
        obj.readFrom(12, new ScratchInputStream(bin), project);

        assertArrayEquals(new byte[] { 0, 1, 100, 101, (byte) 200, (byte) 201 },
                obj.getValue());
    }
}

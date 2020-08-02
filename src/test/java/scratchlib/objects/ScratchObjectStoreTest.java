package scratchlib.objects;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import scratchlib.objects.fixed.collections.ScratchObjectArray;
import scratchlib.objects.fixed.data.ScratchObjectString;
import scratchlib.objects.fixed.data.ScratchObjectUtf8;
import scratchlib.objects.inline.ScratchObjectBoolean;
import scratchlib.project.ScratchProject;
import scratchlib.project.ScratchVersion;
import scratchlib.reader.ScratchInputStream;
import scratchlib.writer.ScratchOutputStream;

import static org.junit.jupiter.api.Assertions.*;


public class ScratchObjectStoreTest
{
    @Test
    public void writesCorrectFormat() throws IOException
    {
        ScratchProject project = new ScratchProject(ScratchVersion.SCRATCH14);

        ScratchObjectArray arr = new ScratchObjectArray();
        ScratchObjectUtf8 orphan = new ScratchObjectUtf8("foo");
        ScratchObjectStore obj = new ScratchObjectStore(arr, Collections.singletonList(orphan));

        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        obj.writeTo(new ScratchOutputStream(bout), project);

        assertArrayEquals(new byte[] {
                // header
                'O', 'b', 'j', 'S', 1, 'S', 't', 'c', 'h', 1,
                // size
                0, 0, 0, 2,
                // object: arr
                20, 0, 0, 0, 0,
                // object: orphan
                14, 0, 0, 0, 3, 'f', 'o', 'o',
                // end
        }, bout.toByteArray());
    }

    @Test
    public void throwsWhenReadingIncorrectHeader()
    {
        ScratchProject project = new ScratchProject(ScratchVersion.SCRATCH14);

        ByteArrayInputStream bin = new ByteArrayInputStream("ObjS\1Stttt".getBytes(StandardCharsets.UTF_8));

        assertThrows(IOException.class, () -> {
            ScratchObjectStore.readFrom(new ScratchInputStream(bin), project);
        });
    }

    @Test
    public void readsSimpleObject() throws IOException
    {
        ScratchProject project = new ScratchProject(ScratchVersion.SCRATCH14);

        ByteArrayInputStream bin = new ByteArrayInputStream(new byte[] {
                // header
                'O', 'b', 'j', 'S', 1, 'S', 't', 'c', 'h', 1,
                // size
                0, 0, 0, 1,
                // boolean 'true'
                2,
                // end
        });

        ScratchObjectStore obj = ScratchObjectStore.readFrom(new ScratchInputStream(bin), project);

        assertSame(ScratchObjectBoolean.TRUE, obj.get());
    }

    @Test
    public void readsComplexObject() throws IOException
    {
        ScratchProject project = new ScratchProject(ScratchVersion.SCRATCH14);

        ByteArrayInputStream bin = new ByteArrayInputStream(new byte[] {
                // header
                'O', 'b', 'j', 'S', 1, 'S', 't', 'c', 'h', 1,
                // size
                0, 0, 0, 3,
                // array with 3 items
                20, 0, 0, 0, 3,
                // first item: reference
                99, 0, 0, 2,
                // second item: nil
                1,
                // third item: reference
                99, 0, 0, 3,
                // first reference: string of length 0
                9, 0, 0, 0, 0,
                // second reference: string of length 0
                9, 0, 0, 0, 0,
                // end
        });

        ScratchObjectStore obj = ScratchObjectStore.readFrom(new ScratchInputStream(bin), project);

        assertTrue(obj.get() instanceof ScratchObjectArray);
        ScratchObjectArray arr = (ScratchObjectArray) obj.get();

        assertEquals(3, arr.size());

        assertTrue(arr.get(0) instanceof ScratchObjectString);
        assertSame(ScratchObject.NIL, arr.get(1));
        assertTrue(arr.get(2) instanceof ScratchObjectString);
    }

    @Test
    public void readsOrphanedObjects() throws IOException
    {
        ScratchProject project = new ScratchProject(ScratchVersion.SCRATCH14);

        ByteArrayInputStream bin = new ByteArrayInputStream(new byte[] {
                // header
                'O', 'b', 'j', 'S', 1, 'S', 't', 'c', 'h', 1,
                // size
                0, 0, 0, 3,
                // array with 0 items
                20, 0, 0, 0, 0,
                // first orphan: string of length 0
                9, 0, 0, 0, 0,
                // second orphan: string of length 0
                9, 0, 0, 0, 0,
                // end
        });

        ScratchObjectStore obj = ScratchObjectStore.readFrom(new ScratchInputStream(bin), project);

        assertTrue(obj.get() instanceof ScratchObjectArray);

        assertEquals(2, obj.getOrphanedFields().size());

        assertTrue(obj.getOrphanedFields().get(0) instanceof ScratchObjectString);
        assertTrue(obj.getOrphanedFields().get(1) instanceof ScratchObjectString);
    }
}

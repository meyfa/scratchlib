package scratchlib.objects;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.junit.Test;

import scratchlib.objects.fixed.collections.ScratchObjectArray;
import scratchlib.objects.fixed.data.ScratchObjectString;
import scratchlib.objects.fixed.data.ScratchObjectUtf8;
import scratchlib.objects.inline.ScratchObjectBoolean;
import scratchlib.project.ScratchProject;
import scratchlib.project.ScratchVersion;
import scratchlib.reader.ScratchInputStream;
import scratchlib.writer.ScratchOutputStream;


public class ScratchObjectStoreTest
{
    @Test
    public void writesCorrectFormat() throws IOException
    {
        ScratchProject project = new ScratchProject(ScratchVersion.SCRATCH14);

        ScratchObjectArray arr = new ScratchObjectArray();
        ScratchObjectUtf8 orphan = new ScratchObjectUtf8("foo");
        ScratchObjectStore obj = new ScratchObjectStore(arr,
                Arrays.asList(orphan));

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

    @Test(expected = IOException.class)
    public void throwsWhenReadingIncorrectHeader() throws IOException
    {
        ScratchProject project = new ScratchProject(ScratchVersion.SCRATCH14);

        ByteArrayInputStream bin = new ByteArrayInputStream(
                "ObjS\1Stttt".getBytes(StandardCharsets.UTF_8));

        ScratchObjectStore.readFrom(new ScratchInputStream(bin), project);
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

        ScratchObjectStore obj = ScratchObjectStore
                .readFrom(new ScratchInputStream(bin), project);

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

        ScratchObjectStore obj = ScratchObjectStore
                .readFrom(new ScratchInputStream(bin), project);

        assertThat(obj.get(), instanceOf(ScratchObjectArray.class));
        ScratchObjectArray arr = (ScratchObjectArray) obj.get();

        assertEquals(3, arr.size());

        assertThat(arr.get(0), instanceOf(ScratchObjectString.class));
        assertSame(ScratchObject.NIL, arr.get(1));
        assertThat(arr.get(2), instanceOf(ScratchObjectString.class));
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

        ScratchObjectStore obj = ScratchObjectStore
                .readFrom(new ScratchInputStream(bin), project);

        assertThat(obj.get(), instanceOf(ScratchObjectArray.class));

        assertEquals(2, obj.getOrphanedFields().size());

        assertThat(obj.getOrphanedFields().get(0),
                instanceOf(ScratchObjectString.class));
        assertThat(obj.getOrphanedFields().get(1),
                instanceOf(ScratchObjectString.class));
    }
}

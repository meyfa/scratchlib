package scratchlib.objects.fixed.collections;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

import org.junit.jupiter.api.Test;
import scratchlib.objects.ScratchObject;
import scratchlib.objects.ScratchReferenceTable;
import scratchlib.objects.inline.ScratchObjectBoolean;
import scratchlib.project.ScratchProject;
import scratchlib.project.ScratchVersion;
import scratchlib.reader.ScratchInputStream;
import scratchlib.writer.ScratchOutputStream;

import static org.junit.jupiter.api.Assertions.*;


public class ScratchObjectAbstractCollectionTest
{
    @Test
    public void returnsElements()
    {
        ScratchObjectAbstractCollection obj = new ScratchObjectAbstractCollection(
                42, Arrays.asList(ScratchObjectBoolean.TRUE,
                        ScratchObjectBoolean.FALSE)) {
        };

        assertSame(ScratchObjectBoolean.TRUE, obj.get(0));
        assertSame(ScratchObjectBoolean.FALSE, obj.get(1));
    }

    @Test
    public void setsElements()
    {
        ScratchObjectAbstractCollection obj = new ScratchObjectAbstractCollection(
                42, Arrays.asList(ScratchObjectBoolean.TRUE,
                        ScratchObjectBoolean.FALSE)) {
        };
        obj.set(1, ScratchObject.NIL);

        assertSame(ScratchObject.NIL, obj.get(1));
    }

    @Test
    public void addsElementsAtEnd()
    {
        ScratchObjectAbstractCollection obj = new ScratchObjectAbstractCollection(
                42) {
        };

        obj.add(ScratchObjectBoolean.TRUE);
        obj.add(ScratchObjectBoolean.FALSE);

        assertEquals(2, obj.size());
        assertSame(ScratchObjectBoolean.TRUE, obj.get(0));
        assertSame(ScratchObjectBoolean.FALSE, obj.get(1));
    }

    @Test
    public void addsElementsAtPosition()
    {
        ScratchObjectAbstractCollection obj = new ScratchObjectAbstractCollection(
                42) {
        };

        obj.add(0, ScratchObjectBoolean.TRUE);
        obj.add(1, ScratchObjectBoolean.FALSE);

        assertEquals(2, obj.size());
        assertSame(ScratchObjectBoolean.TRUE, obj.get(0));
        assertSame(ScratchObjectBoolean.FALSE, obj.get(1));
    }

    @Test
    public void removesElements()
    {
        ScratchObjectAbstractCollection obj = new ScratchObjectAbstractCollection(
                42, Arrays.asList(ScratchObjectBoolean.TRUE,
                        ScratchObjectBoolean.FALSE)) {
        };
        obj.remove(1);

        assertEquals(1, obj.size());
        assertSame(ScratchObjectBoolean.TRUE, obj.get(0));
    }

    @Test
    public void removesElementsByValue()
    {
        ScratchObjectAbstractCollection obj = new ScratchObjectAbstractCollection(
                42, Arrays.asList(ScratchObjectBoolean.TRUE,
                        ScratchObjectBoolean.FALSE)) {
        };
        obj.remove(ScratchObjectBoolean.FALSE);

        assertEquals(1, obj.size());
        assertSame(ScratchObjectBoolean.TRUE, obj.get(0));
    }

    @Test
    public void clearsElements()
    {
        ScratchObjectAbstractCollection obj = new ScratchObjectAbstractCollection(
                42, Arrays.asList(ScratchObjectBoolean.TRUE,
                        ScratchObjectBoolean.FALSE)) {
        };
        obj.clear();

        assertEquals(0, obj.size());
    }

    @Test
    public void iteratesOverAllElements()
    {
        ScratchObjectAbstractCollection obj = new ScratchObjectAbstractCollection(
                42, Arrays.asList(ScratchObjectBoolean.TRUE,
                        ScratchObjectBoolean.FALSE)) {
        };
        Iterator<ScratchObject> it = obj.iterator();

        assertSame(ScratchObjectBoolean.TRUE, it.next());
        assertSame(ScratchObjectBoolean.FALSE, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void streamsAllElements()
    {
        ScratchObjectAbstractCollection obj = new ScratchObjectAbstractCollection(
                42, Arrays.asList(ScratchObjectBoolean.TRUE,
                        ScratchObjectBoolean.FALSE)) {
        };

        assertArrayEquals(new ScratchObject[] { ScratchObjectBoolean.TRUE,
                ScratchObjectBoolean.FALSE }, obj.stream().toArray());
    }

    @Test
    public void createsReferences()
    {
        ScratchProject project = new ScratchProject(ScratchVersion.SCRATCH14);

        ScratchObjectAbstractCollection obj = new ScratchObjectAbstractCollection(
                42) {
        };
        obj.add(new ScratchObjectArray());
        obj.add(new ScratchObjectArray());

        ScratchReferenceTable ref = new ScratchReferenceTable();
        obj.createReferences(ref, project);

        assertEquals(3, ref.size());
    }

    @Test
    public void writesCorrectFormat() throws IOException
    {
        ScratchProject project = new ScratchProject(ScratchVersion.SCRATCH14);

        ScratchObjectAbstractCollection obj = new ScratchObjectAbstractCollection(
                42) {
        };
        obj.add(new ScratchObjectArray());
        obj.add(ScratchObjectBoolean.TRUE);

        ScratchReferenceTable ref = new ScratchReferenceTable();
        obj.createReferences(ref, project);

        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        obj.writeTo(new ScratchOutputStream(bout), ref, project);

        assertArrayEquals(new byte[] {
                // class id
                42,
                // size
                0, 0, 0, 2,
                // first element: reference
                99, 0, 0, 2,
                // second element: boolean 'true'
                2,
                // end
        }, bout.toByteArray());
    }

    @Test
    public void readsCorrectly() throws IOException
    {
        ScratchProject project = new ScratchProject(ScratchVersion.SCRATCH14);

        ScratchObjectAbstractCollection obj = new ScratchObjectAbstractCollection(
                42) {
        };

        ByteArrayInputStream bin = new ByteArrayInputStream(new byte[] {
                // size
                0, 0, 0, 2,
                // first element: reference
                99, 0, 0, 2,
                // second element: boolean 'true'
                2,
                // end
        });
        obj.readFrom(42, new ScratchInputStream(bin), project);

        ScratchReferenceTable ref = new ScratchReferenceTable();
        ScratchObjectArray arr = new ScratchObjectArray();
        ref.insert(obj);
        ref.insert(arr);
        obj.resolveReferences(ref);

        assertEquals(2, obj.size());
        assertSame(arr, obj.get(0));
        assertSame(ScratchObjectBoolean.TRUE, obj.get(1));
    }
}

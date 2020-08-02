package scratchlib.objects.fixed.collections;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.HashSet;

import org.junit.jupiter.api.Test;
import scratchlib.objects.ScratchObject;
import scratchlib.objects.ScratchReferenceTable;
import scratchlib.objects.fixed.data.ScratchObjectString;
import scratchlib.objects.inline.ScratchObjectBoolean;
import scratchlib.project.ScratchProject;
import scratchlib.project.ScratchVersion;
import scratchlib.reader.ScratchInputStream;
import scratchlib.writer.ScratchOutputStream;

import static org.junit.jupiter.api.Assertions.*;


public class ScratchObjectAbstractDictionaryTest
{
    @Test
    public void putsEntries()
    {
        ScratchObjectAbstractDictionary obj = new ScratchObjectAbstractDictionary(42) {
        };

        assertEquals(0, obj.size());

        ScratchObject key = new ScratchObjectString("foobar");
        ScratchObject val = new ScratchObjectArray();
        obj.put(key, val);

        assertEquals(1, obj.size());
        assertSame(val, obj.get(key));

        obj.put(key, val);

        assertEquals(1, obj.size());
    }

    @Test
    public void getsCorrectValue()
    {
        ScratchObjectAbstractDictionary obj = new ScratchObjectAbstractDictionary(42) {
        };

        ScratchObject key0 = new ScratchObjectString("foobar");
        ScratchObject val0 = new ScratchObjectArray();
        obj.put(key0, val0);

        ScratchObject key1 = new ScratchObjectString("foobar");
        ScratchObject val1 = new ScratchObjectArray();
        obj.put(key1, val1);

        ScratchObject key2 = ScratchObjectBoolean.FALSE;
        ScratchObject val2 = new ScratchObjectArray();
        obj.put(key2, val2);

        assertSame(val0, obj.get(key0));
        assertSame(val1, obj.get(key1));
        assertSame(val2, obj.get(key2));
    }

    @Test
    public void removesEntry()
    {
        ScratchObjectAbstractDictionary obj = new ScratchObjectAbstractDictionary(42) {
        };

        ScratchObject key0 = new ScratchObjectString("foobar");
        ScratchObject val0 = new ScratchObjectArray();
        obj.put(key0, val0);

        ScratchObject key1 = new ScratchObjectString("foobar");
        ScratchObject val1 = new ScratchObjectArray();
        obj.put(key1, val1);

        assertEquals(2, obj.size());

        obj.remove(key1);

        assertEquals(1, obj.size());
        assertSame(val0, obj.get(key0));
    }

    @Test
    public void returnsKeySet()
    {
        ScratchObjectAbstractDictionary obj = new ScratchObjectAbstractDictionary(42) {
        };

        ScratchObject key0 = new ScratchObjectString("foobar");
        ScratchObject val0 = new ScratchObjectArray();
        obj.put(key0, val0);

        ScratchObject key1 = new ScratchObjectString("foobar");
        ScratchObject val1 = new ScratchObjectArray();
        obj.put(key1, val1);

        assertEquals(new HashSet<>(Arrays.asList(key0, key1)), obj.keySet());
    }

    @Test
    public void returnsValues()
    {
        ScratchObjectAbstractDictionary obj = new ScratchObjectAbstractDictionary(42) {
        };

        ScratchObject key0 = new ScratchObjectString("foobar");
        ScratchObject val0 = new ScratchObjectArray();
        obj.put(key0, val0);

        ScratchObject key1 = new ScratchObjectString("foobar");
        ScratchObject val1 = new ScratchObjectArray();
        obj.put(key1, val1);

        assertEquals(new HashSet<>(Arrays.asList(val0, val1)), new HashSet<>(obj.values()));
    }

    @Test
    public void returnsEntrySet()
    {
        ScratchObjectAbstractDictionary obj = new ScratchObjectAbstractDictionary(42) {
        };

        ScratchObject key0 = new ScratchObjectString("foobar");
        ScratchObject val0 = new ScratchObjectArray();
        obj.put(key0, val0);

        ScratchObject key1 = new ScratchObjectString("foobar");
        ScratchObject val1 = new ScratchObjectArray();
        obj.put(key1, val1);

        assertEquals(new HashSet<>(Arrays.asList(
                // entry 0
                new AbstractMap.SimpleEntry<>(key0, val0),
                // entry 1
                new AbstractMap.SimpleEntry<>(key1, val1)//
        )), obj.entrySet());
    }

    @Test
    public void createsReferences()
    {
        ScratchProject project = new ScratchProject(ScratchVersion.SCRATCH14);

        ScratchObjectAbstractDictionary obj = new ScratchObjectAbstractDictionary(42) {
        };
        obj.put(new ScratchObjectString("foobar"), new ScratchObjectArray());
        obj.put(new ScratchObjectString("foobar"), new ScratchObjectArray());

        ScratchReferenceTable ref = new ScratchReferenceTable();
        obj.createReferences(ref, project);

        assertEquals(5, ref.size());
    }

    @Test
    public void writesCorrectFormat() throws IOException
    {
        ScratchProject project = new ScratchProject(ScratchVersion.SCRATCH14);

        ScratchObjectAbstractDictionary obj = new ScratchObjectAbstractDictionary(42) {
        };
        obj.put(new ScratchObjectString("foo"), new ScratchObjectArray());
        obj.put(new ScratchObjectString("bar"), ScratchObject.NIL);

        ScratchReferenceTable ref = new ScratchReferenceTable();
        obj.createReferences(ref, project);

        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        obj.writeTo(new ScratchOutputStream(bout), ref, project);

        assertArrayEquals(new byte[] {
                // class id
                42,
                // size (number of entries)
                0, 0, 0, 2,
                // first element key: reference
                99, 0, 0, 2,
                // first element value: reference
                99, 0, 0, 3,
                // second element key: reference
                99, 0, 0, 4,
                // second element value: nil
                1,
                // end
        }, bout.toByteArray());
    }

    @Test
    public void readsCorrectly() throws IOException
    {
        ScratchProject project = new ScratchProject(ScratchVersion.SCRATCH14);

        ScratchObjectAbstractDictionary obj = new ScratchObjectAbstractDictionary(42) {
        };

        ByteArrayInputStream bin = new ByteArrayInputStream(new byte[] {
                // size (number of entries)
                0, 0, 0, 2,
                // first element key: reference
                99, 0, 0, 2,
                // first element value: reference
                99, 0, 0, 3,
                // second element key: reference
                99, 0, 0, 4,
                // second element value: nil
                1,
                // end
        });
        obj.readFrom(42, new ScratchInputStream(bin), project);

        ScratchReferenceTable ref = new ScratchReferenceTable();
        ref.insert(obj);

        ScratchObjectString key0 = new ScratchObjectString("foo");
        ScratchObjectArray val0 = new ScratchObjectArray();
        ref.insert(key0);
        ref.insert(val0);
        ScratchObjectString key1 = new ScratchObjectString("bar");
        ref.insert(key1);

        obj.resolveReferences(ref);

        assertEquals(2, obj.size());
        assertSame(val0, obj.get(key0));
        assertSame(ScratchObject.NIL, obj.get(key1));
    }
}

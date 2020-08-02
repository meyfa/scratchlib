package scratchlib.objects.user.morphs;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import scratchlib.objects.ScratchObject;
import scratchlib.objects.fixed.collections.ScratchObjectArray;
import scratchlib.objects.fixed.collections.ScratchObjectOrderedCollection;
import scratchlib.objects.fixed.data.ScratchObjectAbstractString;
import scratchlib.objects.fixed.data.ScratchObjectUtf8;
import scratchlib.objects.fixed.dimensions.ScratchObjectPoint;
import scratchlib.objects.user.ScratchObjectCustomBlockDefinition;
import scratchlib.objects.user.ScratchUserClassObject.ClassVersion;

import static org.junit.jupiter.api.Assertions.*;


public class ScratchObjectScriptableMorphTest
{
    // ---- BASIC --------------------------------------------------------------

    @Test
    public void getsName()
    {
        final String field = ScratchObjectScriptableMorph.FIELD_OBJ_NAME;

        ScratchObjectScriptableMorph obj = new ScratchObjectScriptableMorph(42, new ClassVersion(3));

        obj.setField(field, new ScratchObjectUtf8("foobar"));
        assertEquals("foobar", obj.getName());
    }

    @Test
    public void setsName()
    {
        final String field = ScratchObjectScriptableMorph.FIELD_OBJ_NAME;

        ScratchObjectScriptableMorph obj = new ScratchObjectScriptableMorph(42, new ClassVersion(3));

        obj.setName("foobar");
        assertEquals("foobar", ((ScratchObjectAbstractString) obj.getField(field)).getValue());
    }

    // ---- CUSTOM BLOCKS ------------------------------------------------------

    @Test
    public void getsCustomBlockCount()
    {
        final String field = ScratchObjectScriptableMorph.FIELD_CUSTOM_BLOCKS;

        ScratchObjectScriptableMorph obj = new ScratchObjectScriptableMorph(42, new ClassVersion(3));

        ScratchObjectCustomBlockDefinition cb0 = new ScratchObjectCustomBlockDefinition();
        ScratchObjectCustomBlockDefinition cb1 = new ScratchObjectCustomBlockDefinition();
        obj.setField(field, new ScratchObjectOrderedCollection(Arrays.asList(cb0, cb1)));
        assertEquals(2, obj.getCustomBlockCount());

        obj.setField(field, ScratchObject.NIL);
        assertEquals(0, obj.getCustomBlockCount());
    }

    @Test
    public void getsCustomBlock()
    {
        final String field = ScratchObjectScriptableMorph.FIELD_CUSTOM_BLOCKS;

        ScratchObjectScriptableMorph obj = new ScratchObjectScriptableMorph(42, new ClassVersion(3));

        ScratchObjectCustomBlockDefinition cb0 = new ScratchObjectCustomBlockDefinition();
        ScratchObjectCustomBlockDefinition cb1 = new ScratchObjectCustomBlockDefinition();
        obj.setField(field, new ScratchObjectOrderedCollection(Arrays.asList(cb0, cb1)));

        assertSame(cb0, obj.getCustomBlock(0));
        assertSame(cb1, obj.getCustomBlock(1));
    }

    @Test
    public void addsCustomBlock()
    {
        final String field = ScratchObjectScriptableMorph.FIELD_CUSTOM_BLOCKS;

        ScratchObjectScriptableMorph obj = new ScratchObjectScriptableMorph(42, new ClassVersion(3));

        obj.addCustomBlock(new ScratchObjectCustomBlockDefinition());
        assertEquals(1, obj.getCustomBlockCount());

        obj.addCustomBlock(new ScratchObjectCustomBlockDefinition());
        assertEquals(2, obj.getCustomBlockCount());

        obj.setField(field, ScratchObject.NIL);
        obj.addCustomBlock(new ScratchObjectCustomBlockDefinition());
        assertEquals(1, obj.getCustomBlockCount());
    }

    @Test
    public void removesCustomBlock()
    {
        final String field = ScratchObjectScriptableMorph.FIELD_CUSTOM_BLOCKS;

        ScratchObjectScriptableMorph obj = new ScratchObjectScriptableMorph(42, new ClassVersion(3));

        ScratchObjectCustomBlockDefinition cb0 = new ScratchObjectCustomBlockDefinition();
        ScratchObjectCustomBlockDefinition cb1 = new ScratchObjectCustomBlockDefinition();
        obj.setField(field, new ScratchObjectOrderedCollection(Arrays.asList(cb0, cb1)));

        obj.removeCustomBlock(1);
        assertEquals(1, obj.getCustomBlockCount());
        assertSame(cb0, obj.getCustomBlock(0));

        obj.removeCustomBlock(cb0);
        assertEquals(0, obj.getCustomBlockCount());
    }

    @Test
    public void clearsCustomBlocks()
    {
        final String field = ScratchObjectScriptableMorph.FIELD_CUSTOM_BLOCKS;

        ScratchObjectScriptableMorph obj = new ScratchObjectScriptableMorph(42, new ClassVersion(3));

        ScratchObjectCustomBlockDefinition cb0 = new ScratchObjectCustomBlockDefinition();
        ScratchObjectCustomBlockDefinition cb1 = new ScratchObjectCustomBlockDefinition();
        obj.setField(field, new ScratchObjectOrderedCollection(Arrays.asList(cb0, cb1)));

        obj.clearCustomBlocks();
        assertEquals(0, obj.getCustomBlockCount());

        obj.setField(field, ScratchObject.NIL);
        obj.clearCustomBlocks();
        assertEquals(0, obj.getCustomBlockCount());
    }

    @Test
    public void streamsCustomBlocks()
    {
        final String field = ScratchObjectScriptableMorph.FIELD_CUSTOM_BLOCKS;

        ScratchObjectScriptableMorph obj = new ScratchObjectScriptableMorph(42, new ClassVersion(3));

        ScratchObjectCustomBlockDefinition cb0 = new ScratchObjectCustomBlockDefinition();
        ScratchObjectCustomBlockDefinition cb1 = new ScratchObjectCustomBlockDefinition();
        obj.setField(field, new ScratchObjectOrderedCollection(Arrays.asList(cb0, cb1)));

        assertEquals(Arrays.asList(cb0, cb1), obj.streamCustomBlocks().collect(Collectors.toList()));

        obj.setField(field, ScratchObject.NIL);
        assertEquals(0, obj.streamCustomBlocks().count());
    }

    // ---- SCRIPTS ------------------------------------------------------------

    @Test
    public void getsScriptCount()
    {
        final String field = ScratchObjectScriptableMorph.FIELD_BLOCKS_BIN;

        ScratchObjectScriptableMorph obj = new ScratchObjectScriptableMorph(42, new ClassVersion(3));

        assertEquals(0, obj.getScriptCount());

        ScratchObjectArray b0 = new ScratchObjectArray(Arrays.asList(//
                new ScratchObjectPoint(20, 20), // location
                new ScratchObjectArray() // blocks
        ));
        ScratchObjectArray b1 = new ScratchObjectArray(Arrays.asList(//
                new ScratchObjectPoint(20, 80), // location
                new ScratchObjectArray() // blocks
        ));
        obj.setField(field, new ScratchObjectArray(Arrays.asList(b0, b1)));

        assertEquals(2, obj.getScriptCount());
    }

    @Test
    public void getsScript()
    {
        final String field = ScratchObjectScriptableMorph.FIELD_BLOCKS_BIN;

        ScratchObjectScriptableMorph obj = new ScratchObjectScriptableMorph(42, new ClassVersion(3));

        ScratchObjectArray b0 = new ScratchObjectArray(Arrays.asList(//
                new ScratchObjectPoint(20, 20), // location
                new ScratchObjectArray() // blocks
        ));
        ScratchObjectArray b1 = new ScratchObjectArray(Arrays.asList(//
                new ScratchObjectPoint(20, 80), // location
                new ScratchObjectArray() // blocks
        ));
        obj.setField(field, new ScratchObjectArray(Arrays.asList(b0, b1)));

        assertSame(b0, obj.getScript(0));
        assertSame(b1, obj.getScript(1));
    }

    @Test
    public void getsScriptLocation()
    {
        final String field = ScratchObjectScriptableMorph.FIELD_BLOCKS_BIN;

        ScratchObjectScriptableMorph obj = new ScratchObjectScriptableMorph(42, new ClassVersion(3));

        ScratchObjectArray b0 = new ScratchObjectArray(Arrays.asList(//
                new ScratchObjectPoint(20, 20), // location
                new ScratchObjectArray() // blocks
        ));
        ScratchObjectArray b1 = new ScratchObjectArray(Arrays.asList(//
                new ScratchObjectPoint(20, 80), // location
                new ScratchObjectArray() // blocks
        ));
        obj.setField(field, new ScratchObjectArray(Arrays.asList(b0, b1)));

        assertSame(b0.get(0), obj.getScriptLocation(0));
        assertSame(b1.get(0), obj.getScriptLocation(1));
    }

    @Test
    public void getsScriptBody()
    {
        final String field = ScratchObjectScriptableMorph.FIELD_BLOCKS_BIN;

        ScratchObjectScriptableMorph obj = new ScratchObjectScriptableMorph(42, new ClassVersion(3));

        ScratchObjectArray b0 = new ScratchObjectArray(Arrays.asList(//
                new ScratchObjectPoint(20, 20), // location
                new ScratchObjectArray() // blocks
        ));
        ScratchObjectArray b1 = new ScratchObjectArray(Arrays.asList(//
                new ScratchObjectPoint(20, 80), // location
                new ScratchObjectArray() // blocks
        ));
        obj.setField(field, new ScratchObjectArray(Arrays.asList(b0, b1)));

        assertSame(b0.get(1), obj.getScriptBody(0));
        assertSame(b1.get(1), obj.getScriptBody(1));
    }

    @Test
    public void addsScript()
    {
        ScratchObjectScriptableMorph obj = new ScratchObjectScriptableMorph(42, new ClassVersion(3));

        obj.addScript(new ScratchObjectPoint(20, 20), new ScratchObjectArray());
        assertEquals(1, obj.getScriptCount());

        obj.addScript(new ScratchObjectArray(Arrays.asList(//
                new ScratchObjectPoint(20, 80), // location
                new ScratchObjectArray() // blocks
        )));
        assertEquals(2, obj.getScriptCount());
    }

    @Test
    public void removesScript()
    {
        final String field = ScratchObjectScriptableMorph.FIELD_BLOCKS_BIN;

        ScratchObjectScriptableMorph obj = new ScratchObjectScriptableMorph(42, new ClassVersion(3));

        ScratchObjectArray b0 = new ScratchObjectArray(Arrays.asList(//
                new ScratchObjectPoint(20, 20), // location
                new ScratchObjectArray() // blocks
        ));
        ScratchObjectArray b1 = new ScratchObjectArray(Arrays.asList(//
                new ScratchObjectPoint(20, 80), // location
                new ScratchObjectArray() // blocks
        ));
        obj.setField(field, new ScratchObjectArray(Arrays.asList(b0, b1)));

        obj.removeScript(1);
        assertEquals(1, obj.getScriptCount());
        assertSame(b0, obj.getScript(0));

        obj.removeScript(b0);
        assertEquals(0, obj.getScriptCount());
    }

    @Test
    public void clearsScripts()
    {
        final String field = ScratchObjectScriptableMorph.FIELD_BLOCKS_BIN;

        ScratchObjectScriptableMorph obj = new ScratchObjectScriptableMorph(42, new ClassVersion(3));

        ScratchObjectArray b0 = new ScratchObjectArray(Arrays.asList(//
                new ScratchObjectPoint(20, 20), // location
                new ScratchObjectArray() // blocks
        ));
        ScratchObjectArray b1 = new ScratchObjectArray(Arrays.asList(//
                new ScratchObjectPoint(20, 80), // location
                new ScratchObjectArray() // blocks
        ));
        obj.setField(field, new ScratchObjectArray(Arrays.asList(b0, b1)));

        obj.clearScripts();
        assertEquals(0, obj.getScriptCount());
    }

    @Test
    public void streamsScripts()
    {
        final String field = ScratchObjectScriptableMorph.FIELD_BLOCKS_BIN;

        ScratchObjectScriptableMorph obj = new ScratchObjectScriptableMorph(42, new ClassVersion(3));

        ScratchObjectArray b0 = new ScratchObjectArray(Arrays.asList(//
                new ScratchObjectPoint(20, 20), // location
                new ScratchObjectArray() // blocks
        ));
        ScratchObjectArray b1 = new ScratchObjectArray(Arrays.asList(//
                new ScratchObjectPoint(20, 80), // location
                new ScratchObjectArray() // blocks
        ));
        obj.setField(field, new ScratchObjectArray(Arrays.asList(b0, b1)));

        assertEquals(Arrays.asList(b0, b1), obj.streamScripts().collect(Collectors.toList()));
    }

    @Test
    public void streamsScriptBodies()
    {
        final String field = ScratchObjectScriptableMorph.FIELD_BLOCKS_BIN;

        ScratchObjectScriptableMorph obj = new ScratchObjectScriptableMorph(42, new ClassVersion(3));

        ScratchObjectArray b0 = new ScratchObjectArray(Arrays.asList(//
                new ScratchObjectPoint(20, 20), // location
                new ScratchObjectArray() // blocks
        ));
        ScratchObjectArray b1 = new ScratchObjectArray(Arrays.asList(//
                new ScratchObjectPoint(20, 80), // location
                new ScratchObjectArray() // blocks
        ));
        obj.setField(field, new ScratchObjectArray(Arrays.asList(b0, b1)));

        assertEquals(Arrays.asList(b0.get(1), b1.get(1)), obj.streamScriptBodies().collect(Collectors.toList()));
    }
}

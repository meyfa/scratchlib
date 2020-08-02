package scratchlib.objects.user;

import org.junit.jupiter.api.Test;
import scratchlib.objects.ScratchObject;
import scratchlib.objects.fixed.collections.ScratchObjectArray;
import scratchlib.objects.fixed.data.ScratchObjectAbstractString;
import scratchlib.objects.fixed.data.ScratchObjectUtf8;

import static org.junit.jupiter.api.Assertions.*;


public class ScratchObjectCustomBlockDefinitionTest
{
    @Test
    public void getsUserSpec()
    {
        final String field = ScratchObjectCustomBlockDefinition.FIELD_USER_SPEC;

        ScratchObjectCustomBlockDefinition obj = new ScratchObjectCustomBlockDefinition();

        obj.setField(field, new ScratchObjectUtf8("foo %bar"));
        assertEquals("foo %bar", obj.getUserSpec());

        obj.setField(field, ScratchObject.NIL);
        assertNull(obj.getUserSpec());
    }

    @Test
    public void setsUserSpec()
    {
        final String field = ScratchObjectCustomBlockDefinition.FIELD_USER_SPEC;

        ScratchObjectCustomBlockDefinition obj = new ScratchObjectCustomBlockDefinition();

        obj.setUserSpec("foo %bar");
        assertEquals("foo %bar", ((ScratchObjectAbstractString) obj.getField(field)).getValue());
    }

    @Test
    public void getsBody()
    {
        final String field = ScratchObjectCustomBlockDefinition.FIELD_BODY;

        ScratchObjectCustomBlockDefinition obj = new ScratchObjectCustomBlockDefinition();

        ScratchObjectArray blocks = new ScratchObjectArray();
        obj.setField(field, blocks);
        assertSame(blocks, obj.getBody());

        obj.setField(field, ScratchObject.NIL);
        assertNull(obj.getBody());
    }

    @Test
    public void setsBody()
    {
        final String field = ScratchObjectCustomBlockDefinition.FIELD_BODY;

        ScratchObjectCustomBlockDefinition obj = new ScratchObjectCustomBlockDefinition();

        ScratchObjectArray blocks = new ScratchObjectArray();
        obj.setBody(blocks);
        assertSame(blocks, obj.getField(field));
    }
}

package scratchlib.objects.user.morphs.ui;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import scratchlib.objects.ScratchObject;
import scratchlib.objects.fixed.data.ScratchObjectAbstractString;
import scratchlib.objects.fixed.data.ScratchObjectUtf8;


public class ScratchObjectUpdatingStringMorphTest
{
    @Test
    public void getsParameter()
    {
        ScratchObjectUpdatingStringMorph obj = new ScratchObjectUpdatingStringMorph();

        obj.setField(ScratchObjectUpdatingStringMorph.FIELD_PARAMETER,
                new ScratchObjectUtf8("foobar"));
        assertEquals("foobar", obj.getParameter());
    }

    @Test
    public void setsParameter()
    {
        ScratchObjectUpdatingStringMorph obj = new ScratchObjectUpdatingStringMorph();

        obj.setParameter("foobar");
        ScratchObject f = obj
                .getField(ScratchObjectUpdatingStringMorph.FIELD_PARAMETER);
        assertEquals("foobar", ((ScratchObjectAbstractString) f).getValue());
    }
}

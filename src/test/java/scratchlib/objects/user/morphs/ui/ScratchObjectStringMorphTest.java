package scratchlib.objects.user.morphs.ui;

import org.junit.jupiter.api.Test;
import scratchlib.objects.ScratchObject;
import scratchlib.objects.fixed.data.ScratchObjectAbstractString;
import scratchlib.objects.fixed.data.ScratchObjectUtf8;

import static org.junit.jupiter.api.Assertions.*;


public class ScratchObjectStringMorphTest
{
    @Test
    public void getsContents()
    {
        ScratchObjectStringMorph obj = new ScratchObjectStringMorph();

        obj.setField(ScratchObjectStringMorph.FIELD_CONTENTS,
                new ScratchObjectUtf8("foobar"));
        assertEquals("foobar", obj.getContents());
    }

    @Test
    public void setsContents()
    {
        ScratchObjectStringMorph obj = new ScratchObjectStringMorph();

        obj.setContents("foobar");
        ScratchObject f = obj.getField(ScratchObjectStringMorph.FIELD_CONTENTS);
        assertEquals("foobar", ((ScratchObjectAbstractString) f).getValue());
    }
}

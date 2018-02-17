package scratchlib.objects.user.media;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import scratchlib.objects.ScratchObject;
import scratchlib.objects.fixed.data.ScratchObjectAbstractString;
import scratchlib.objects.fixed.data.ScratchObjectUtf8;
import scratchlib.objects.user.ScratchUserClassObject.ClassVersion;


public class ScratchObjectMediaTest
{
    @Test
    public void getsName()
    {
        ScratchObjectMedia obj = new ScratchObjectMedia(42,
                new ClassVersion(1));

        obj.setField(ScratchObjectMedia.FIELD_MEDIA_NAME,
                new ScratchObjectUtf8("foobar"));
        assertEquals("foobar", obj.getName());
    }

    @Test
    public void setsName()
    {
        ScratchObjectMedia obj = new ScratchObjectMedia(42,
                new ClassVersion(1));

        obj.setName("foobar");
        ScratchObject f = obj.getField(ScratchObjectMedia.FIELD_MEDIA_NAME);
        assertEquals("foobar", ((ScratchObjectAbstractString) f).getValue());
    }
}

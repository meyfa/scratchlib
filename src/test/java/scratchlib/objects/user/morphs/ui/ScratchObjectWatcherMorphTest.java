package scratchlib.objects.user.morphs.ui;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class ScratchObjectWatcherMorphTest
{
    @Test
    public void getsTitleMorph()
    {
        ScratchObjectWatcherMorph obj = new ScratchObjectWatcherMorph();

        assertNotNull(obj.getTitleMorph());
    }

    @Test
    public void getsReadoutMorph()
    {
        ScratchObjectWatcherMorph obj = new ScratchObjectWatcherMorph();

        assertNotNull(obj.getReadoutMorph());
    }
}

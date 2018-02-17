package scratchlib.objects.user.morphs.ui;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

import org.junit.Test;


public class ScratchObjectWatcherMorphTest
{
    @Test
    public void getsTitleMorph()
    {
        ScratchObjectWatcherMorph obj = new ScratchObjectWatcherMorph();

        assertThat(obj.getTitleMorph(),
                instanceOf(ScratchObjectStringMorph.class));
    }

    @Test
    public void getsReadoutMorph()
    {
        ScratchObjectWatcherMorph obj = new ScratchObjectWatcherMorph();

        assertThat(obj.getReadoutMorph(),
                instanceOf(ScratchObjectUpdatingStringMorph.class));
    }
}

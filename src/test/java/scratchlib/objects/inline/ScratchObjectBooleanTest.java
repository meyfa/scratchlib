package scratchlib.objects.inline;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class ScratchObjectBooleanTest
{
    @Test
    public void returnsCorrectJavaValue()
    {
        assertTrue(ScratchObjectBoolean.TRUE.getValue());
        assertFalse(ScratchObjectBoolean.FALSE.getValue());
    }

    @Test
    public void returnsCorrectScratchValue()
    {
        assertSame(ScratchObjectBoolean.TRUE,
                ScratchObjectBoolean.valueOf(true));
        assertSame(ScratchObjectBoolean.FALSE,
                ScratchObjectBoolean.valueOf(false));
    }
}

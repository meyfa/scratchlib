package scratchlib.objects.inline;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


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
        assertSame(ScratchObjectBoolean.TRUE, ScratchObjectBoolean.valueOf(true));
        assertSame(ScratchObjectBoolean.FALSE, ScratchObjectBoolean.valueOf(false));
    }
}

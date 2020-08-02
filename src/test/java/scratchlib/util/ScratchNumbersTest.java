package scratchlib.util;

import java.math.BigInteger;

import org.junit.jupiter.api.Test;
import scratchlib.objects.inline.ScratchObjectAbstractNumber;
import scratchlib.objects.inline.ScratchObjectFloat;
import scratchlib.objects.inline.ScratchObjectLargeNegativeInteger;
import scratchlib.objects.inline.ScratchObjectLargePositiveInteger;
import scratchlib.objects.inline.ScratchObjectSmallInteger;
import scratchlib.objects.inline.ScratchObjectSmallInteger16;

import static org.junit.jupiter.api.Assertions.*;


public class ScratchNumbersTest
{
    private void assertNumber(Class<? extends ScratchObjectAbstractNumber> type,
            double value, ScratchObjectAbstractNumber actual)
    {
        assertTrue(type.isInstance(actual));
        assertEquals(value, actual.doubleValue(), 0.00000001);
    }

    @Test
    public void ofDouble()
    {
        assertNumber(ScratchObjectSmallInteger16.class, 0, ScratchNumbers.of(0.0));
        assertNumber(ScratchObjectSmallInteger16.class, 0, ScratchNumbers.of(-0.0));

        assertNumber(ScratchObjectSmallInteger16.class, 10, ScratchNumbers.of(10.0));
        assertNumber(ScratchObjectSmallInteger16.class, -10, ScratchNumbers.of(-10.0));

        assertNumber(ScratchObjectSmallInteger.class, 1_000_000, ScratchNumbers.of(1_000_000.0));
        assertNumber(ScratchObjectSmallInteger.class, -1_000_000, ScratchNumbers.of(-1_000_000.0));

        assertNumber(ScratchObjectLargePositiveInteger.class, 10_000_000_000L, ScratchNumbers.of(10_000_000_000.0));
        assertNumber(ScratchObjectLargeNegativeInteger.class, -10_000_000_000L, ScratchNumbers.of(-10_000_000_000.0));

        assertNumber(ScratchObjectFloat.class, 1.2345, ScratchNumbers.of(1.2345));
        assertNumber(ScratchObjectFloat.class, -1.2345, ScratchNumbers.of(-1.2345));
    }

    @Test
    public void ofLong()
    {
        assertNumber(ScratchObjectSmallInteger16.class, 0, ScratchNumbers.of(0L));
        assertNumber(ScratchObjectSmallInteger16.class, 0, ScratchNumbers.of(-0L));

        assertNumber(ScratchObjectSmallInteger16.class, 10, ScratchNumbers.of(10L));
        assertNumber(ScratchObjectSmallInteger16.class, -10, ScratchNumbers.of(-10L));

        assertNumber(ScratchObjectSmallInteger.class, 1_000_000, ScratchNumbers.of(1_000_000L));
        assertNumber(ScratchObjectSmallInteger.class, -1_000_000, ScratchNumbers.of(-1_000_000L));

        assertNumber(ScratchObjectLargePositiveInteger.class, 10_000_000_000L, ScratchNumbers.of(10_000_000_000L));
        assertNumber(ScratchObjectLargeNegativeInteger.class, -10_000_000_000L, ScratchNumbers.of(-10_000_000_000L));
    }

    @Test
    public void ofBigInteger()
    {
        assertNumber(ScratchObjectLargePositiveInteger.class, 0, ScratchNumbers.of(new BigInteger("0")));

        assertNumber(ScratchObjectLargePositiveInteger.class, 1000, ScratchNumbers.of(new BigInteger("1000")));
        assertNumber(ScratchObjectLargeNegativeInteger.class, -1000, ScratchNumbers.of(new BigInteger("-1000")));
    }
}

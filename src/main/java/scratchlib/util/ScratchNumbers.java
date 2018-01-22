package scratchlib.util;

import java.math.BigInteger;

import scratchlib.objects.inline.ScratchObjectAbstractNumber;
import scratchlib.objects.inline.ScratchObjectFloat;
import scratchlib.objects.inline.ScratchObjectLargeNegativeInteger;
import scratchlib.objects.inline.ScratchObjectLargePositiveInteger;
import scratchlib.objects.inline.ScratchObjectSmallInteger;
import scratchlib.objects.inline.ScratchObjectSmallInteger16;


/**
 * Utility class for converting Java number types into Scratch number types. The
 * types used are always the smallest possible.
 */
public class ScratchNumbers
{
    private ScratchNumbers()
    {
    }

    /**
     * Converts the given number into a Scratch number type. The result may or
     * may not be a floating-point number type, depending on whether the input
     * is close to a mathematical integer.
     *
     * @param value The number.
     * @return The number converted to a Scratch number type.
     */
    public static ScratchObjectAbstractNumber of(double value)
    {
        if (value == Math.rint(value)) {
            return of((long) value);
        }

        return new ScratchObjectFloat(value);
    }

    /**
     * Converts the given number into a Scratch number type. The class is chosen
     * to be the smallest still able to contain the input.
     *
     * @param value The number.
     * @return The number converted to a Scratch number type.
     */
    public static ScratchObjectAbstractNumber of(long value)
    {
        if (value < 0) {
            if (value < Integer.MIN_VALUE) {
                return new ScratchObjectLargeNegativeInteger(
                        BigInteger.valueOf(value));
            } else if (value < Short.MIN_VALUE) {
                return new ScratchObjectSmallInteger((int) value);
            } else {
                return new ScratchObjectSmallInteger16((short) value);
            }
        } else {
            if (value > Integer.MAX_VALUE) {
                return new ScratchObjectLargePositiveInteger(
                        BigInteger.valueOf(value));
            } else if (value > Short.MAX_VALUE) {
                return new ScratchObjectSmallInteger((int) value);
            } else {
                return new ScratchObjectSmallInteger16((short) value);
            }
        }
    }

    /**
     * Converts the given {@code BigInteger} into a Scratch number type. This is
     * either {@link ScratchObjectLargeNegativeInteger} or
     * {@link ScratchObjectLargePositiveInteger}, depending on the input's sign.
     *
     * @param value The number.
     * @return The number converted to a Scratch number type.
     */
    public static ScratchObjectAbstractNumber of(BigInteger value)
    {
        if (value.signum() < 0) {
            return new ScratchObjectLargeNegativeInteger(value);
        } else {
            return new ScratchObjectLargePositiveInteger(value);
        }
    }
}

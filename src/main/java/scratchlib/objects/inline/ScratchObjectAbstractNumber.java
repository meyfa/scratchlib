package scratchlib.objects.inline;

import java.math.BigDecimal;
import java.math.BigInteger;

import scratchlib.objects.ScratchObject;


/**
 * Base class for any Scratch number object.
 */
public abstract class ScratchObjectAbstractNumber extends ScratchObject
{
    /**
     * @param classID The ID of the class this object belongs to.
     */
    public ScratchObjectAbstractNumber(int classID)
    {
        super(classID);
    }

    /**
     * @return This number in double precision.
     */
    public abstract double doubleValue();

    /**
     * @return This number in 32-bit int precision.
     */
    public abstract int intValue();

    /**
     * @return This number as a {@link BigDecimal}.
     */
    public abstract BigDecimal toBigDecimal();

    /**
     * @return This number as a {@link BigInteger}.
     */
    public abstract BigInteger toBigInteger();
}

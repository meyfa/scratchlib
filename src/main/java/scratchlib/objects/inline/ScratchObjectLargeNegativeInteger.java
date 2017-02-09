package scratchlib.objects.inline;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

import scratchlib.objects.ScratchObject;
import scratchlib.objects.ScratchReferenceTable;
import scratchlib.project.ScratchProject;
import scratchlib.reader.ScratchInputStream;
import scratchlib.writer.ScratchOutputStream;


/**
 * Inline {@link ScratchObject} type for negative integers of arbitrary size,
 * stored by first writing a 16-bit length, followed by that many bytes.
 * 
 * <p>
 * Note that the sign bit is ignored when written, although the class property
 * is the negative integer for convenience.
 */
public class ScratchObjectLargeNegativeInteger
        extends ScratchObjectAbstractNumber
{
    /**
     * Class ID in binary files.
     */
    public static final int CLASS_ID = 7;

    private BigInteger value;

    /**
     * Empty constructor for yet-to-be-read instances.
     */
    public ScratchObjectLargeNegativeInteger()
    {
        super(CLASS_ID);
    }

    /**
     * @param value The integer value.
     */
    public ScratchObjectLargeNegativeInteger(BigInteger value)
    {
        super(CLASS_ID);

        if (value.signum() > 0) {
            throw new IllegalArgumentException("integer not negative");
        }
        this.value = value;
    }

    @Override
    public double doubleValue()
    {
        return value.doubleValue();
    }

    @Override
    public int intValue()
    {
        return value.intValue();
    }

    @Override
    public BigDecimal toBigDecimal()
    {
        return new BigDecimal(value);
    }

    @Override
    public BigInteger toBigInteger()
    {
        return value;
    }

    @Override
    public void writeTo(ScratchOutputStream out, ScratchReferenceTable ref,
            ScratchProject project) throws IOException
    {
        super.writeTo(out, ref, project);

        byte[] bytes = value.abs().toByteArray();
        out.write16bitUnsignedInt(bytes.length);
        out.write(bytes);
    }

    @Override
    public void readFrom(int id, ScratchInputStream in, ScratchProject project)
            throws IOException
    {
        super.readFrom(id, in, project);

        int length = in.read16bitUnsignedInt();
        byte[] bytes = in.readFully(length);
        this.value = new BigInteger(bytes).negate();
    }
}

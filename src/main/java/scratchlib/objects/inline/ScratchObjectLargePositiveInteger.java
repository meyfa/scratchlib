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
 * Inline {@link ScratchObject} type for positive integers of arbitrary size,
 * stored by first writing a 16-bit length, followed by that many bytes.
 */
public class ScratchObjectLargePositiveInteger
        extends ScratchObjectAbstractNumber
{
    /**
     * Class ID in binary files.
     */
    public static final int CLASS_ID = 6;

    private BigInteger value;

    /**
     * Empty constructor for yet-to-be-read instances.
     */
    public ScratchObjectLargePositiveInteger()
    {
        super(CLASS_ID);
    }

    /**
     * @param value The integer value.
     */
    public ScratchObjectLargePositiveInteger(BigInteger value)
    {
        super(CLASS_ID);

        if (value.signum() < 0) {
            throw new IllegalArgumentException("integer not positive");
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

        byte[] bytes = value.toByteArray();
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
        this.value = new BigInteger(bytes);
    }
}

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
 * Inline {@link ScratchObject} type for signed 32-bit integer numbers, i.e.
 * {@code int}.
 */
public class ScratchObjectSmallInteger extends ScratchObjectAbstractNumber
{
    /**
     * Class ID in binary files.
     */
    public static final int CLASS_ID = 4;

    private int value;

    /**
     * Empty constructor for yet-to-be-read instances.
     */
    public ScratchObjectSmallInteger()
    {
        super(CLASS_ID);
    }

    /**
     * @param value The integer value.
     */
    public ScratchObjectSmallInteger(int value)
    {
        super(CLASS_ID);
        this.value = value;
    }

    @Override
    public double doubleValue()
    {
        return value;
    }

    @Override
    public int intValue()
    {
        return value;
    }

    @Override
    public BigDecimal toBigDecimal()
    {
        return BigDecimal.valueOf(value);
    }

    @Override
    public BigInteger toBigInteger()
    {
        return BigInteger.valueOf(value);
    }

    @Override
    public void writeTo(ScratchOutputStream out, ScratchReferenceTable ref,
            ScratchProject project) throws IOException
    {
        super.writeTo(out, ref, project);
        out.write32bitUnsignedInt(value);
    }

    @Override
    public void readFrom(int id, ScratchInputStream in, ScratchProject project)
            throws IOException
    {
        super.readFrom(id, in, project);
        this.value = in.read32bitUnsignedInt();
    }
}

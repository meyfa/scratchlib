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
 * Inline {@link ScratchObject} type for decimal numbers, i.e. {@code double}.
 */
public class ScratchObjectFloat extends ScratchObjectAbstractNumber
{
    /**
     * Class ID in binary files.
     */
    public static final int CLASS_ID = 8;

    private double value;

    /**
     * Empty constructor for yet-to-be-read instances.
     */
    public ScratchObjectFloat()
    {
        super(CLASS_ID);
    }

    /**
     * @param value The decimal value.
     */
    public ScratchObjectFloat(double value)
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
        return (int) value;
    }

    @Override
    public BigDecimal toBigDecimal()
    {
        return BigDecimal.valueOf(value);
    }

    @Override
    public BigInteger toBigInteger()
    {
        return BigInteger.valueOf((int) value);
    }

    @Override
    public void writeTo(ScratchOutputStream out, ScratchReferenceTable ref, ScratchProject project) throws IOException
    {
        super.writeTo(out, ref, project);
        out.write64bitDecimal(value);
    }

    @Override
    public void readFrom(int id, ScratchInputStream in, ScratchProject project) throws IOException
    {
        super.readFrom(id, in, project);
        this.value = in.read64bitDecimal();
    }
}

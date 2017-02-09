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
 * Inline {@link ScratchObject} type for signed 16-bit integer numbers, i.e.
 * {@code short}.
 */
public class ScratchObjectSmallInteger16 extends ScratchObjectAbstractNumber
{
    /**
     * Class ID in binary files.
     */
    public static final int CLASS_ID = 5;

    private short value;

    /**
     * Empty constructor for yet-to-be-read instances.
     */
    public ScratchObjectSmallInteger16()
    {
        super(CLASS_ID);
    }

    /**
     * @param value The integer value.
     */
    public ScratchObjectSmallInteger16(short value)
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
        out.write16bitUnsignedInt(Short.toUnsignedInt(value));
    }

    @Override
    public void readFrom(int id, ScratchInputStream in, ScratchProject project)
            throws IOException
    {
        super.readFrom(id, in, project);
        this.value = (short) in.read16bitUnsignedInt();
    }
}

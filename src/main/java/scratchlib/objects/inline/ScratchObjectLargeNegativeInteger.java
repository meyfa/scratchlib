package scratchlib.objects.inline;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Objects;

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
public class ScratchObjectLargeNegativeInteger extends ScratchObject
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

    /**
     * @return The integer value.
     */
    public BigInteger getValue()
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

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + getClassID();
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ScratchObjectLargeNegativeInteger other = (ScratchObjectLargeNegativeInteger) obj;
        return Objects.equals(value, other.value);
    }
}

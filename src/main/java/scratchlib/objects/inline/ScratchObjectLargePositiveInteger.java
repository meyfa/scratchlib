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
 * Inline {@link ScratchObject} type for positive integers of arbitrary size,
 * stored by first writing a 16-bit length, followed by that many bytes.
 */
public class ScratchObjectLargePositiveInteger extends ScratchObject
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
        ScratchObjectLargePositiveInteger other = (ScratchObjectLargePositiveInteger) obj;
        return Objects.equals(value, other.value);
    }
}

package scratchlib.objects.inline;

import java.io.IOException;

import scratchlib.objects.ScratchObject;
import scratchlib.objects.ScratchReferenceTable;
import scratchlib.project.ScratchProject;
import scratchlib.reader.ScratchInputStream;
import scratchlib.writer.ScratchOutputStream;


/**
 * Inline {@link ScratchObject} type for decimal numbers, i.e. {@code double}.
 */
public class ScratchObjectFloat extends ScratchObject
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

    /**
     * @return The decimal value.
     */
    public double getValue()
    {
        return value;
    }

    @Override
    public void writeTo(ScratchOutputStream out, ScratchReferenceTable ref,
            ScratchProject project) throws IOException
    {
        super.writeTo(out, ref, project);
        out.write64bitDecimal(value);
    }

    @Override
    public void readFrom(int id, ScratchInputStream in, ScratchProject project)
            throws IOException
    {
        super.readFrom(id, in, project);
        this.value = in.read64bitDecimal();
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + getClassID();
        long temp = Double.doubleToLongBits(value);
        result = prime * result + (int) (temp ^ (temp >>> 32));
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
        ScratchObjectFloat other = (ScratchObjectFloat) obj;
        return Double.doubleToLongBits(value) == Double
                .doubleToLongBits(other.value);
    }
}

package scratchlib.objects.inline;

import java.io.IOException;

import scratchlib.objects.ScratchObject;
import scratchlib.objects.ScratchReferenceTable;
import scratchlib.project.ScratchProject;
import scratchlib.reader.ScratchInputStream;
import scratchlib.writer.ScratchOutputStream;


/**
 * Inline {@link ScratchObject} type for signed 32-bit integer numbers, i.e.
 * {@code int}.
 */
public class ScratchObjectSmallInteger extends ScratchObject
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

    /**
     * @return The integer value.
     */
    public int getValue()
    {
        return value;
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

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + getClassID();
        result = prime * result + value;
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
        ScratchObjectSmallInteger other = (ScratchObjectSmallInteger) obj;
        return value == other.value;
    }
}

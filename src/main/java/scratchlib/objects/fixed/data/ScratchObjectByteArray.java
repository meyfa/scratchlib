package scratchlib.objects.fixed.data;

import java.io.IOException;
import java.util.Arrays;

import scratchlib.objects.IScratchReferenceType;
import scratchlib.objects.ScratchObject;
import scratchlib.objects.ScratchReferenceTable;
import scratchlib.project.ScratchProject;
import scratchlib.reader.ScratchInputStream;
import scratchlib.writer.ScratchOutputStream;


/**
 * Byte array reference type. The bytes are written exactly as specified.
 */
public class ScratchObjectByteArray extends ScratchObject
        implements IScratchReferenceType
{
    /**
     * Class ID in binary files.
     */
    public static final int CLASS_ID = 11;

    private byte[] value;

    /**
     * Empty constructor for yet-to-be-read instances.
     */
    public ScratchObjectByteArray()
    {
        super(CLASS_ID);
    }

    /**
     * @param value The bytes.
     */
    public ScratchObjectByteArray(byte[] value)
    {
        super(CLASS_ID);
        this.value = value;
    }

    /**
     * @return The bytes stored in this array.
     */
    public byte[] getValue()
    {
        return value;
    }

    @Override
    public void writeTo(ScratchOutputStream out, ScratchReferenceTable ref,
            ScratchProject project) throws IOException
    {
        super.writeTo(out, ref, project);

        out.write32bitUnsignedInt(value.length);
        out.write(value);
    }

    @Override
    public void readFrom(int id, ScratchInputStream in, ScratchProject project)
            throws IOException
    {
        super.readFrom(id, in, project);

        int length = in.read32bitUnsignedInt();
        this.value = in.readFully(length);
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + getClassID();
        result = prime * result + Arrays.hashCode(value);
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
        ScratchObjectByteArray other = (ScratchObjectByteArray) obj;
        return Arrays.equals(value, other.value);
    }
}

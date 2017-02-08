package scratchlib.objects.fixed.data;

import java.io.IOException;

import scratchlib.objects.IScratchReferenceType;
import scratchlib.objects.ScratchObject;
import scratchlib.objects.ScratchReferenceTable;
import scratchlib.project.ScratchProject;
import scratchlib.reader.ScratchInputStream;
import scratchlib.writer.ScratchOutputStream;


/**
 * Byte array reference type with 4 * length bytes (i.e. 4 bytes per pixel).
 */
public class ScratchObjectBitmap extends ScratchObject
        implements IScratchReferenceType
{
    /**
     * Class ID in binary files.
     */
    public static final int CLASS_ID = 13;

    private byte[] value;

    /**
     * Empty constructor for yet-to-be-read instances.
     */
    public ScratchObjectBitmap()
    {
        super(CLASS_ID);
    }

    /**
     * @param value The bytes.
     * @throws IllegalArgumentException If value.length not multiple of 4.
     */
    public ScratchObjectBitmap(byte[] value)
    {
        super(CLASS_ID);

        if (value.length % 4 != 0) {
            throw new IllegalArgumentException("length not multiple of 4");
        }
        this.value = value;
    }

    /**
     * @return The bytes stored in this bitmap (4 per pixel).
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

        out.write32bitUnsignedInt(value.length / 4);
        out.write(value);
    }

    @Override
    public void readFrom(int id, ScratchInputStream in, ScratchProject project)
            throws IOException
    {
        super.readFrom(id, in, project);

        int length = in.read32bitUnsignedInt();
        this.value = in.readFully(length * 4);
    }
}

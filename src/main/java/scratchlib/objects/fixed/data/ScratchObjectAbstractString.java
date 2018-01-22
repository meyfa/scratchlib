package scratchlib.objects.fixed.data;

import java.io.IOException;
import java.nio.charset.Charset;

import scratchlib.objects.IScratchReferenceType;
import scratchlib.objects.ScratchObject;
import scratchlib.objects.ScratchReferenceTable;
import scratchlib.project.ScratchProject;
import scratchlib.reader.ScratchInputStream;
import scratchlib.writer.ScratchOutputStream;


/**
 * Base class for all Scratch reference types that store strings. They differ
 * only in the charset they use, and in their purpose.
 *
 * <p>
 * <b>US-ASCII</b>: {@link ScratchObjectString} (most used),
 * {@link ScratchObjectSymbol} (for internally-defined values).<br>
 * <b>UTF-8</b>: {@link ScratchObjectUtf8} (for user input).
 */
public abstract class ScratchObjectAbstractString extends ScratchObject
        implements IScratchReferenceType
{
    private final Charset charset;
    private String value;

    /**
     * @param classID The ID of the class this object belongs to.
     * @param charset The charset to use when reading/writing.
     */
    public ScratchObjectAbstractString(int classID, Charset charset)
    {
        super(classID);
        this.charset = charset;
    }

    /**
     * @param classID The ID of the class this object belongs to.
     * @param charset The charset to use when reading/writing.
     * @param value The String value.
     */
    public ScratchObjectAbstractString(int classID, Charset charset,
            String value)
    {
        super(classID);

        this.charset = charset;
        this.value = value;
    }

    /**
     * @return The charset used when reading/writing.
     */
    public Charset getCharset()
    {
        return charset;
    }

    /**
     * @return The String value.
     */
    public String getValue()
    {
        return value;
    }

    @Override
    public void writeTo(ScratchOutputStream out, ScratchReferenceTable ref,
            ScratchProject project) throws IOException
    {
        super.writeTo(out, ref, project);

        byte[] bytes = value.getBytes(charset);

        out.write32bitUnsignedInt(bytes.length);
        out.write(bytes);
    }

    @Override
    public void readFrom(int id, ScratchInputStream in, ScratchProject project)
            throws IOException
    {
        super.readFrom(id, in, project);

        int length = in.read32bitUnsignedInt();
        this.value = new String(in.readFully(length), charset);
    }
}

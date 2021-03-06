package scratchlib.objects.fixed.forms;

import java.io.IOException;

import scratchlib.objects.IScratchReferenceType;
import scratchlib.objects.ScratchObject;
import scratchlib.objects.ScratchObjects;
import scratchlib.objects.ScratchOptionalField;
import scratchlib.objects.ScratchReferenceTable;
import scratchlib.objects.fixed.collections.ScratchObjectArray;
import scratchlib.objects.fixed.data.ScratchObjectByteArray;
import scratchlib.objects.inline.ScratchObjectSmallInteger16;
import scratchlib.project.ScratchProject;
import scratchlib.reader.ScratchInputStream;
import scratchlib.writer.ScratchOutputStream;


/**
 * Fixed-format reference type for storing so-called "color forms", which are
 * similar to regular forms ({@link ScratchObjectForm}) but differ in that they
 * are usually 32-bit, have a bitmap instead of a byte array, and use a lookup
 * table for their colors.
 */
public class ScratchObjectColorForm extends ScratchObject implements IScratchReferenceType
{
    /**
     * Class ID in binary files.
     */
    public static final int CLASS_ID = 35;

    private ScratchObjectSmallInteger16 width, height, depth;
    private ScratchOptionalField privateOffset = new ScratchOptionalField(NIL);
    private ScratchOptionalField bits;
    private ScratchOptionalField colorMap;

    /**
     * Empty constructor for yet-to-be-read instances.
     */
    public ScratchObjectColorForm()
    {
        super(CLASS_ID);
    }

    /**
     * @param width The width of this form.
     * @param height The height of this form.
     * @param depth The depth of this form.
     * @param bits The byte array containing the pixel values.
     * @param colorMap The lookup map for color values.
     */
    public ScratchObjectColorForm(int width, int height, int depth, ScratchObjectByteArray bits,
                                  ScratchObjectArray colorMap)
    {
        this(new ScratchObjectSmallInteger16((short) width), new ScratchObjectSmallInteger16((short) height),
                new ScratchObjectSmallInteger16((short) depth), bits, colorMap);
    }

    /**
     * @param width The width of this form.
     * @param height The height of this form.
     * @param depth The depth of this form.
     * @param bits The byte array containing the pixel values.
     * @param colorMap The lookup map for color values.
     */
    public ScratchObjectColorForm(ScratchObjectSmallInteger16 width, ScratchObjectSmallInteger16 height,
            ScratchObjectSmallInteger16 depth, ScratchObjectByteArray bits, ScratchObjectArray colorMap)
    {
        super(CLASS_ID);

        this.width = width;
        this.height = height;
        this.depth = depth;
        this.bits = new ScratchOptionalField(bits);
        this.colorMap = new ScratchOptionalField(colorMap);
    }

    /**
     * @return The width of this form (pixels).
     */
    public ScratchObjectSmallInteger16 getWidth()
    {
        return width;
    }

    /**
     * @return The height of this form (pixels).
     */
    public ScratchObjectSmallInteger16 getHeight()
    {
        return height;
    }

    /**
     * @return The depth of this form (bits per pixel).
     */
    public ScratchObjectSmallInteger16 getDepth()
    {
        return depth;
    }

    /**
     * @return The "private offset" (seems to be unused).
     */
    public ScratchObject getPrivateOffset()
    {
        return privateOffset.get();
    }

    /**
     * @return This form's pixel data.
     */
    public ScratchObjectByteArray getBits()
    {
        return (ScratchObjectByteArray) bits.get();
    }

    /**
     * @return The lookup map for color values.
     */
    public ScratchObjectArray getColorMap()
    {
        return (ScratchObjectArray) colorMap.get();
    }

    @Override
    public boolean createReferences(ScratchReferenceTable ref, ScratchProject project)
    {
        if (!super.createReferences(ref, project)) {
            return false;
        }

        privateOffset.get().createReferences(ref, project);
        bits.get().createReferences(ref, project);
        colorMap.get().createReferences(ref, project);

        return true;
    }

    @Override
    public void resolveReferences(ScratchReferenceTable ref)
    {
        super.resolveReferences(ref);

        privateOffset.resolve(ref);
        bits.resolve(ref);
        colorMap.resolve(ref);
    }

    @Override
    public void writeTo(ScratchOutputStream out, ScratchReferenceTable ref, ScratchProject project) throws IOException
    {
        super.writeTo(out, ref, project);

        width.writeTo(out, ref, project);
        height.writeTo(out, ref, project);
        depth.writeTo(out, ref, project);
        ref.writeField(privateOffset.get(), out, project);
        ref.writeField(bits.get(), out, project);
        ref.writeField(colorMap.get(), out, project);
    }

    @Override
    public void readFrom(int id, ScratchInputStream in, ScratchProject project) throws IOException
    {
        super.readFrom(id, in, project);

        this.width = (ScratchObjectSmallInteger16) ScratchObjects.read(in, project).get();
        this.height = (ScratchObjectSmallInteger16) ScratchObjects.read(in, project).get();
        this.depth = (ScratchObjectSmallInteger16) ScratchObjects.read(in, project).get();
        this.privateOffset = ScratchObjects.read(in, project);
        this.bits = ScratchObjects.read(in, project);
        this.colorMap = ScratchObjects.read(in, project);
    }
}

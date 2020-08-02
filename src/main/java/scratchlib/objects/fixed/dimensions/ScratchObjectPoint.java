package scratchlib.objects.fixed.dimensions;

import java.io.IOException;

import scratchlib.objects.IScratchReferenceType;
import scratchlib.objects.ScratchObject;
import scratchlib.objects.ScratchObjects;
import scratchlib.objects.ScratchReferenceTable;
import scratchlib.objects.inline.ScratchObjectAbstractNumber;
import scratchlib.project.ScratchProject;
import scratchlib.reader.ScratchInputStream;
import scratchlib.util.ScratchNumbers;
import scratchlib.writer.ScratchOutputStream;


/**
 * Reference type representing a 2D point, consisting (in most cases) of two
 * 16-bit integers.
 */
public class ScratchObjectPoint extends ScratchObject implements IScratchReferenceType
{
    /**
     * Class ID in binary files.
     */
    public static final int CLASS_ID = 32;

    private ScratchObjectAbstractNumber x, y;

    /**
     * Empty constructor for yet-to-be-read instances.
     */
    public ScratchObjectPoint()
    {
        super(CLASS_ID);
    }

    /**
     * @param x The x value.
     * @param y The y value.
     */
    public ScratchObjectPoint(int x, int y)
    {
        this(ScratchNumbers.of(x), ScratchNumbers.of(y));
    }

    /**
     * @param x The x value.
     * @param y The y value.
     */
    public ScratchObjectPoint(ScratchObjectAbstractNumber x, ScratchObjectAbstractNumber y)
    {
        super(CLASS_ID);

        this.x = x;
        this.y = y;
    }

    /**
     * @return The x value.
     */
    public ScratchObjectAbstractNumber getX()
    {
        return x;
    }

    /**
     * @return The y value.
     */
    public ScratchObjectAbstractNumber getY()
    {
        return y;
    }

    @Override
    public void writeTo(ScratchOutputStream out, ScratchReferenceTable ref, ScratchProject project) throws IOException
    {
        super.writeTo(out, ref, project);

        x.writeTo(out, ref, project);
        y.writeTo(out, ref, project);
    }

    @Override
    public void readFrom(int id, ScratchInputStream in, ScratchProject project) throws IOException
    {
        super.readFrom(id, in, project);

        this.x = (ScratchObjectAbstractNumber) ScratchObjects.read(in, project).get();
        this.y = (ScratchObjectAbstractNumber) ScratchObjects.read(in, project).get();
    }
}

package scratchlib.objects.fixed.dimensions;

import java.io.IOException;

import scratchlib.objects.IScratchReferenceType;
import scratchlib.objects.ScratchObject;
import scratchlib.objects.ScratchObjects;
import scratchlib.objects.ScratchReferenceTable;
import scratchlib.objects.inline.ScratchObjectAbstractNumber;
import scratchlib.objects.inline.ScratchObjectSmallInteger16;
import scratchlib.project.ScratchProject;
import scratchlib.reader.ScratchInputStream;
import scratchlib.writer.ScratchOutputStream;


/**
 * Reference type representing a 2D rectangle, consisting of the x and y
 * coordinates as well as width and height values (all four are 16-bit).
 */
public class ScratchObjectRectangle extends ScratchObject
        implements IScratchReferenceType
{
    /**
     * Class ID in binary files.
     */
    public static final int CLASS_ID = 33;

    private ScratchObjectAbstractNumber x, y, w, h;

    /**
     * Empty constructor for yet-to-be-read instances.
     */
    public ScratchObjectRectangle()
    {
        super(CLASS_ID);
    }

    /**
     * @param x The x value.
     * @param y The y value.
     * @param width The width value.
     * @param height The height value.
     */
    public ScratchObjectRectangle(short x, short y, short width, short height)
    {
        this(new ScratchObjectSmallInteger16(x),
                new ScratchObjectSmallInteger16(y),
                new ScratchObjectSmallInteger16(width),
                new ScratchObjectSmallInteger16(height));
    }

    /**
     * @param x The x value.
     * @param y The y value.
     * @param width The width value.
     * @param height The height value.
     */
    public ScratchObjectRectangle(ScratchObjectAbstractNumber x,
            ScratchObjectAbstractNumber y, ScratchObjectAbstractNumber width,
            ScratchObjectAbstractNumber height)
    {
        super(CLASS_ID);

        this.x = x;
        this.y = y;
        this.w = width;
        this.h = height;
    }

    /**
     * @return The x coordinate of this rectangle.
     */
    public ScratchObjectAbstractNumber getX()
    {
        return x;
    }

    /**
     * @return The y coordinate of this rectangle.
     */
    public ScratchObjectAbstractNumber getY()
    {
        return y;
    }

    /**
     * @return The width of this rectangle.
     */
    public ScratchObjectAbstractNumber getWidth()
    {
        return w;
    }

    /**
     * @return The height of this rectangle.
     */
    public ScratchObjectAbstractNumber getHeight()
    {
        return h;
    }

    @Override
    public void writeTo(ScratchOutputStream out, ScratchReferenceTable ref,
            ScratchProject project) throws IOException
    {
        super.writeTo(out, ref, project);

        x.writeTo(out, ref, project);
        y.writeTo(out, ref, project);
        w.writeTo(out, ref, project);
        h.writeTo(out, ref, project);
    }

    @Override
    public void readFrom(int id, ScratchInputStream in, ScratchProject project)
            throws IOException
    {
        super.readFrom(id, in, project);

        this.x = (ScratchObjectAbstractNumber) ScratchObjects.read(in, project)
                .get();
        this.y = (ScratchObjectAbstractNumber) ScratchObjects.read(in, project)
                .get();
        this.w = (ScratchObjectAbstractNumber) ScratchObjects.read(in, project)
                .get();
        this.h = (ScratchObjectAbstractNumber) ScratchObjects.read(in, project)
                .get();
    }
}

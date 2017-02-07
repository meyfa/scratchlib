package scratchlib.objects.fixed.dimensions;

import java.io.IOException;
import java.util.Objects;

import scratchlib.objects.IScratchReferenceType;
import scratchlib.objects.ScratchObject;
import scratchlib.objects.ScratchObjects;
import scratchlib.objects.ScratchReferenceTable;
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

    private ScratchObjectSmallInteger16 x, y, w, h;

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
    public ScratchObjectRectangle(ScratchObjectSmallInteger16 x,
            ScratchObjectSmallInteger16 y, ScratchObjectSmallInteger16 width,
            ScratchObjectSmallInteger16 height)
    {
        super(CLASS_ID);

        this.x = x;
        this.y = y;
        this.w = width;
        this.h = height;
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

        this.x = (ScratchObjectSmallInteger16) (ScratchObjects.read(in, project)
                .get());
        this.y = (ScratchObjectSmallInteger16) (ScratchObjects.read(in, project)
                .get());
        this.w = (ScratchObjectSmallInteger16) (ScratchObjects.read(in, project)
                .get());
        this.h = (ScratchObjectSmallInteger16) (ScratchObjects.read(in, project)
                .get());
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + getClassID();
        result = prime * result + ((h == null) ? 0 : h.hashCode());
        result = prime * result + ((w == null) ? 0 : w.hashCode());
        result = prime * result + ((x == null) ? 0 : x.hashCode());
        result = prime * result + ((y == null) ? 0 : y.hashCode());
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
        ScratchObjectRectangle other = (ScratchObjectRectangle) obj;
        return Objects.equals(h, other.h) && Objects.equals(w, other.w)
                && Objects.equals(x, other.x) && Objects.equals(y, other.y);
    }
}

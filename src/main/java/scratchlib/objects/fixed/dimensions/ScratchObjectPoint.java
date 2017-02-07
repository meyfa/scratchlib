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
 * Reference type representing a 2D point, consisting of two 16-bit integers.
 */
public class ScratchObjectPoint extends ScratchObject
        implements IScratchReferenceType
{
    /**
     * Class ID in binary files.
     */
    public static final int CLASS_ID = 32;

    private ScratchObjectSmallInteger16 x, y;

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
    public ScratchObjectPoint(short x, short y)
    {
        this(new ScratchObjectSmallInteger16(x),
                new ScratchObjectSmallInteger16(y));
    }

    /**
     * @param x The x value.
     * @param y The y value.
     */
    public ScratchObjectPoint(ScratchObjectSmallInteger16 x,
            ScratchObjectSmallInteger16 y)
    {
        super(CLASS_ID);

        this.x = x;
        this.y = y;
    }

    /**
     * @return The x value.
     */
    public ScratchObjectSmallInteger16 getX()
    {
        return x;
    }

    /**
     * @return The y value.
     */
    public ScratchObjectSmallInteger16 getY()
    {
        return y;
    }

    @Override
    public void writeTo(ScratchOutputStream out, ScratchReferenceTable ref,
            ScratchProject project) throws IOException
    {
        super.writeTo(out, ref, project);

        x.writeTo(out, ref, project);
        y.writeTo(out, ref, project);
    }

    @Override
    public void readFrom(int id, ScratchInputStream in, ScratchProject project)
            throws IOException
    {
        super.readFrom(id, in, project);

        this.x = (ScratchObjectSmallInteger16) ScratchObjects.read(in, project)
                .get();
        this.y = (ScratchObjectSmallInteger16) ScratchObjects.read(in, project)
                .get();
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + getClassID();
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
        ScratchObjectPoint other = (ScratchObjectPoint) obj;
        return Objects.equals(x, other.x) && Objects.equals(y, other.y);
    }
}

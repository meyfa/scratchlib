package scratchlib.objects.inline;

import java.io.IOException;

import scratchlib.objects.ScratchObject;
import scratchlib.project.ScratchProject;
import scratchlib.reader.ScratchInputStream;


/**
 * The constants TRUE / FALSE as inline {@link ScratchObject} instances.
 */
public class ScratchObjectBoolean extends ScratchObject
{
    /**
     * The singleton object representing a value of {@code true}.
     */
    public static final ScratchObjectBoolean TRUE = new ScratchObjectBoolean(2);

    /**
     * The singleton object representing a value of {@code false}.
     */
    public static final ScratchObjectBoolean FALSE = new ScratchObjectBoolean(3);

    private ScratchObjectBoolean(int classID)
    {
        super(classID);
    }

    /**
     * @return Whether this is the {@code true} or the {@code false} constant.
     */
    public boolean getValue()
    {
        return this == TRUE;
    }

    /**
     * Given a boolean, returns the appropriate Scratch constant {@link #TRUE}
     * or {@link #FALSE}.
     *
     * @param val The boolean under consideration.
     * @return The appropriate constant.
     */
    public static ScratchObjectBoolean valueOf(boolean val)
    {
        return val ? TRUE : FALSE;
    }

    @Override
    public void readFrom(int id, ScratchInputStream in, ScratchProject project) throws IOException
    {
        super.readFrom(id, in, project);
    }
}

package scratchlib.objects;

import java.io.IOException;

import scratchlib.project.ScratchProject;
import scratchlib.reader.ScratchInputStream;
import scratchlib.writer.ScratchOutputStream;


/**
 * Represents a single object inside a Scratch project. Can be any form of data,
 * from simple constants like true/false/nil, to numbers, to collections, and
 * finally to arbitrarily complex user-class types.
 */
public abstract class ScratchObject
{
    /**
     * The singleton object representing a value of {@code null} ("nil").
     */
    public static final ScratchObject NIL = new ScratchObjectNil();

    private final int classID;

    /**
     * @param classID The ID of the class this object belongs to.
     */
    public ScratchObject(int classID)
    {
        this.classID = classID;
    }

    /**
     * @return The ID of the class this object belongs to.
     */
    public int getClassID()
    {
        return classID;
    }

    /**
     * Inserts all reference fields of this object into the given reference
     * table. Only inserts child fields, not this element.
     * 
     * @param ref The reference table the fields shall be inserted into.
     * @param project The project this object belongs to, for version info.
     */
    public void createReferences(ScratchReferenceTable ref,
            ScratchProject project)
    {
    }

    /**
     * Replaces all unresolved reference fields this instance has by looking
     * them up in the given reference table.
     * 
     * @param ref The reference table to use for field lookup.
     */
    public void resolveReferences(ScratchReferenceTable ref)
    {
    }

    /**
     * Writes this object to the given {@link ScratchOutputStream}, using the
     * already populated {@link ScratchReferenceTable} for writing out reference
     * fields.
     * 
     * <p>
     * Subclasses MUST override this if they store any (additional) data.
     * 
     * @param out The stream to write to.
     * @param ref The populated reference table.
     * @param project The project this object belongs to, for version info.
     * @throws IOException
     * 
     * @see #createReferences(ScratchReferenceTable, ScratchProject)
     */
    public void writeTo(ScratchOutputStream out, ScratchReferenceTable ref,
            ScratchProject project) throws IOException
    {
        out.write(classID);
    }

    /**
     * Initializes this object's fields by reading from the given stream.
     * 
     * <p>
     * Subclasses MUST override this if they store any (additional) data.
     * 
     * @param id This object's class ID.
     * @param in The input stream to read from.
     * @param project The project this object belongs to, for version info.
     * @throws IOException
     */
    public void readFrom(int id, ScratchInputStream in, ScratchProject project)
            throws IOException
    {
        if (id != classID) {
            throw new IOException("illegal ID " + id + ", expected " + classID);
        }
    }

    @Override
    public abstract int hashCode();

    @Override
    public abstract boolean equals(Object obj);

    /**
     * Represents the Scratch "nil" constant.
     */
    private static class ScratchObjectNil extends ScratchObject
    {
        public static final int CLASS_ID = 1;

        public ScratchObjectNil()
        {
            super(CLASS_ID);
        }

        @Override
        public int hashCode()
        {
            return Integer.hashCode(31 + getClassID());
        }

        @Override
        public boolean equals(Object obj)
        {
            return this == obj;
        }
    }
}

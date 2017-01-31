package de.jangobrick.scratchlib.objects;

import java.io.IOException;

import de.jangobrick.scratchlib.writer.ScratchOutputStream;


/**
 * Represents a single object inside a Scratch project. Can be any form of data,
 * from simple constants like true/false/nil, to numbers, to collections, and
 * finally to arbitrarily complex user-class types.
 */
public class ScratchObject
{
    /**
     * The singleton object representing a value of {@code null} ("nil").
     */
    public static final ScratchObject NIL = new ScratchObject(1);

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
     */
    public void createReferences(ScratchReferenceTable ref)
    {
    }

    /**
     * Writes this object to the given {@link ScratchOutputStream}, using the
     * already populated {@link ScratchReferenceTable} for writing out reference
     * fields.
     * 
     * @param out The stream to write to.
     * @param ref The populated reference table.
     * @throws IOException
     * 
     * @see #createReferences(ScratchReferenceTable)
     */
    public void writeTo(ScratchOutputStream out, ScratchReferenceTable ref)
            throws IOException
    {
        out.write(classID);
    }
}

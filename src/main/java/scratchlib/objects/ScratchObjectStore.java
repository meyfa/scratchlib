package scratchlib.objects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import scratchlib.project.ScratchProject;
import scratchlib.reader.ScratchInputStream;
import scratchlib.writer.ScratchOutputStream;


/**
 * Main container for project data: both the project's information and its stage
 * are wrapped in this structure.
 *
 * <p>
 * Object stores have one root object, followed by zero or more reference types
 * required by that root object. In other words, they simply wrap one main
 * object, but in a way that flattens its structure.
 *
 * <p>
 * They start with the header: "ObjS" + 0x01 + "Stch" + 0x01. Next, there is a
 * 32-bit integer describing the store's flattened size (number of elements).
 * Finally, the main object and its referenced objects are written out.
 *
 * <p>
 * Due to some undocumented reason, Scratch and BYOB include "orphaned" objects
 * after the main object and its referenced fields for the project's info store.
 * "Orphaned" here means that they are written out, included in the store
 * length, and have logical significance, but are not available by traversing
 * the store root. Examples include the transparent color and a magic byte
 * array.
 */
public class ScratchObjectStore
{
    /**
     * The object store header, consisting of "ObjS" + 0x01 + "Stch" + 0x01.
     */
    public static final String HEADER = "ObjS" + (char) 1 + "Stch" + (char) 1;

    private ScratchObject object;
    private List<ScratchObject> orphanedFields;

    /**
     * @param object The object contained in this store.
     */
    public ScratchObjectStore(ScratchObject object)
    {
        this.object = object;
        this.orphanedFields = new ArrayList<>();
    }

    /**
     * @param object The object contained in this store.
     * @param orphans Non-referenced objects stored in addition to the main one.
     */
    public ScratchObjectStore(ScratchObject object, List<ScratchObject> orphans)
    {
        this.object = object;
        this.orphanedFields = new ArrayList<>(orphans);
    }

    /**
     * @return The object contained in this store.
     */
    public ScratchObject get()
    {
        return object;
    }

    /**
     * Changes the object contained in this store.
     *
     * @param object The new object.
     */
    public void set(ScratchObject object)
    {
        this.object = object;
    }

    /**
     * @return A writable list containing this store's "orphaned" fields (fields
     *         not referenced from the root).
     */
    public List<ScratchObject> getOrphanedFields()
    {
        return orphanedFields;
    }

    /**
     * Sets this store's list of "orphaned" fields (fields not referenced from
     * the root).
     *
     * @param orphans The new list of orphaned fields.
     */
    public void setOrphanedFields(List<ScratchObject> orphans)
    {
        this.orphanedFields = orphans;
    }

    /**
     * Writes this object store to the given {@link ScratchOutputStream}.
     *
     * @param out The stream to write to.
     * @param project The project this store belongs to, for version info.
     * @throws IOException
     */
    public void writeTo(ScratchOutputStream out, ScratchProject project) throws IOException
    {
        // create reference table
        ScratchReferenceTable refTable = new ScratchReferenceTable();
        object.createReferences(refTable, project);
        for (ScratchObject object : orphanedFields) {
            object.createReferences(refTable, project);
        }

        // write header + size
        out.writeString(HEADER);
        out.write32bitUnsignedInt(refTable.size());

        // write objects from reference table
        for (ScratchObject object : refTable) {
            object.writeTo(out, refTable, project);
        }

        out.flush();
    }

    /**
     * Reads an object store from the given input stream. The stream must be
     * positioned <b>before</b> the object store header.
     *
     * @param in The input stream to read from.
     * @param project The project reading for.
     * @return The instance read.
     * @throws IOException
     */
    public static ScratchObjectStore readFrom(ScratchInputStream in, ScratchProject project) throws IOException
    {
        String header = in.readString(10);
        if (!header.equals(HEADER)) {
            throw new IOException("invalid object store header");
        }

        int size = in.read32bitUnsignedInt();

        // read objects
        List<ScratchObject> objectList = new ArrayList<>();
        ScratchReferenceTable refTable = new ScratchReferenceTable();
        for (int i = 0; i < size; ++i) {
            ScratchObject obj = ScratchObjects.read(in, project).get();
            objectList.add(obj);
            refTable.insert(obj);
        }

        // resolve references
        for (ScratchObject obj : objectList) {
            obj.resolveReferences(refTable);
        }

        // find orphaned fields
        ScratchReferenceTable reverseRefTable = new ScratchReferenceTable();
        objectList.get(0).createReferences(reverseRefTable, project);

        int oStart = reverseRefTable.size(), oEnd = objectList.size();
        List<ScratchObject> orphaned = objectList.subList(oStart, oEnd);

        return new ScratchObjectStore(objectList.get(0), orphaned);
    }
}

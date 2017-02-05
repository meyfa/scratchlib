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
 * required by that root object. In other words, they wrap exactly one main
 * object, but in a way that flattens its structure.
 * 
 * <p>
 * They start with the header: "ObjS" + 0x01 + "Stch" + 0x01. Next, there is a
 * 32-bit integer describing the store's flattened size (number of elements).
 * Finally, the main object and its referenced objects are written out.
 */
public class ScratchObjectStore
{
    /**
     * The object store header, consisting of "ObjS" + 0x01 + "Stch" + 0x01.
     */
    public static final String HEADER = "ObjS" + (char) 1 + "Stch" + (char) 1;

    private ScratchObject object;

    /**
     * @param object The object contained in this store.
     */
    public ScratchObjectStore(ScratchObject object)
    {
        this.object = object;
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
     * Writes this object store to the given {@link ScratchOutputStream}.
     * 
     * @param out The stream to write to.
     * @param project The project this store belongs to, for version info.
     * @throws IOException
     */
    public void writeTo(ScratchOutputStream out, ScratchProject project)
            throws IOException
    {
        // create reference table
        ScratchReferenceTable refTable = new ScratchReferenceTable();
        refTable.insert(object);
        object.createReferences(refTable, project);

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
    public static ScratchObjectStore readFrom(ScratchInputStream in,
            ScratchProject project) throws IOException
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

        return new ScratchObjectStore(objectList.get(0));
    }
}

package scratchlib.objects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import scratchlib.project.ScratchProject;
import scratchlib.writer.ScratchOutputStream;


/**
 * Maps instances of {@link ScratchObject} to reference IDs, offering insertion,
 * lookup and iteration methods.
 */
public class ScratchReferenceTable implements Iterable<ScratchObject>
{
    private final List<ScratchObject> references = new ArrayList<>();

    /**
     * Performs a reference lookup for the given {@link ScratchObject}.
     *
     * @param object The object to look up.
     * @return The object's reference ID, or -1 if not found.
     */
    public int lookup(ScratchObject object)
    {
        int index = references.indexOf(object);
        return index == -1 ? index : index + 1;
    }

    /**
     * Performs an object lookup for the given reference ID.
     *
     * @param referenceID The ID to look up.
     * @return The referenced object, or null if not found.
     */
    public ScratchObject lookup(int referenceID)
    {
        if (referenceID < 1 || referenceID > references.size()) {
            return null;
        }
        return references.get(referenceID - 1);
    }

    /**
     * Inserts the given {@link ScratchObject} into this reference table. The ID
     * is determined automatically. True is returned if the object was newly
     * inserted, false otherwise.
     *
     * @param object The object to insert.
     * @return Whether the element was newly inserted (true), or not (false).
     */
    public boolean insert(ScratchObject object)
    {
        if (references.contains(object)) {
            return false;
        }
        references.add(object);
        return true;
    }

    /**
     * @return The number of references stored.
     */
    public int size()
    {
        return references.size();
    }

    @Override
    public Iterator<ScratchObject> iterator()
    {
        return references.iterator();
    }

    /**
     * Convenience method. If the object is inline, it is written out directly
     * via its {@code writeTo(...)} method; otherwise, the reference stored for
     * it in this table is written instead.
     *
     * <p>
     * This is useful for fields of other objects.
     *
     * @param o The object to write.
     * @param out The stream to write to.
     * @param project The project the object is part of.
     * @throws IOException
     */
    public void writeField(ScratchObject o, ScratchOutputStream out,
            ScratchProject project) throws IOException
    {
        int ref = lookup(o);

        if (!(o instanceof IScratchReferenceType) || ref < 1) {
            o.writeTo(out, this, project);
            return;
        }

        out.write(99);
        out.write24bitUnsignedInt(ref);
    }
}

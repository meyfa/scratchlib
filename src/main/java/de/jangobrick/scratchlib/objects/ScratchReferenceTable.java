package de.jangobrick.scratchlib.objects;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


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
        return references.get(referenceID - 1);
    }

    /**
     * Inserts the given {@link ScratchObject} into this reference table. The ID
     * is determined automatically.
     * 
     * @param object The object to insert.
     */
    public void insert(ScratchObject object)
    {
        if (references.contains(object)) {
            throw new IllegalArgumentException("Object already referenced");
        }
        references.add(object);
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
}

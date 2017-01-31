package de.jangobrick.scratchlib.objects;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * Maps instances of {@link ScratchObject} to reference IDs, offering insertion,
 * lookup and iteration methods.
 */
public class ScratchReferenceTable implements Iterable<ScratchObject>
{
    private final Map<ScratchObject, Integer> references = new LinkedHashMap<>();
    private int nextReferenceID = 1;

    /**
     * Performs a reference lookup for the given {@link ScratchObject}.
     * 
     * @param object The object to look up.
     * @return The object's reference ID, or -1 if not found.
     */
    public int lookup(ScratchObject object)
    {
        return references.getOrDefault(object, -1);
    }

    /**
     * Inserts the given {@link ScratchObject} into this reference table. The ID
     * is determined automatically.
     * 
     * @param object The object to insert.
     */
    public void insert(ScratchObject object)
    {
        if (references.containsKey(object)) {
            throw new IllegalArgumentException("Object already referenced");
        }
        references.put(object, nextReferenceID);
        nextReferenceID++;
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
        return references.keySet().iterator();
    }
}

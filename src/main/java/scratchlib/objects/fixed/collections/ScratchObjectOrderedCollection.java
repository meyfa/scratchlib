package scratchlib.objects.fixed.collections;

import java.util.Collection;

import scratchlib.objects.ScratchObject;


/**
 * Fixed-format reference type that behaves roughly like a {@code List},
 * allowing other objects to be stored and accessed via indices.
 * 
 * <p>
 * Functionally equivalent to every other subclass of
 * {@link ScratchObjectAbstractCollection}.
 */
public class ScratchObjectOrderedCollection
        extends ScratchObjectAbstractCollection
{
    /**
     * Class ID in binary files.
     */
    public static final int CLASS_ID = 21;

    /**
     * Constructs a new ordered collection.
     */
    public ScratchObjectOrderedCollection()
    {
        super(CLASS_ID);
    }

    /**
     * @param entries The entries to initialize this collection with.
     */
    public ScratchObjectOrderedCollection(
            Collection<? extends ScratchObject> entries)
    {
        super(CLASS_ID, entries);
    }
}

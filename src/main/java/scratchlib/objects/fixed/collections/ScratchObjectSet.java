package scratchlib.objects.fixed.collections;

/**
 * Fixed-format reference type that behaves roughly like a {@code List},
 * allowing other objects to be stored and accessed via indices.
 * 
 * <p>
 * Functionally equivalent to every other subclass of
 * {@link ScratchObjectAbstractCollection}.
 */
public class ScratchObjectSet extends ScratchObjectAbstractCollection
{
    /**
     * Class ID in binary files.
     */
    public static final int CLASS_ID = 22;

    /**
     * Constructs a new array.
     */
    public ScratchObjectSet()
    {
        super(CLASS_ID);
    }
}

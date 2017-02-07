package scratchlib.objects.fixed.collections;

/**
 * Fixed-format reference type that behaves roughly like a {@code Map}, allowing
 * other objects to be associated many-to-one (key -&gt; value).
 * 
 * <p>
 * Functionally equivalent to {@link ScratchObjectDictionary}.
 */
public class ScratchObjectIdentityDictionary
        extends ScratchObjectAbstractDictionary
{
    /**
     * Class ID in binary files.
     */
    public static final int CLASS_ID = 25;

    /**
     * Constructs a new dictionary.
     */
    public ScratchObjectIdentityDictionary()
    {
        super(CLASS_ID);
    }
}

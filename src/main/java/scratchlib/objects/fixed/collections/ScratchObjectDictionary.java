package scratchlib.objects.fixed.collections;

/**
 * Fixed-format reference type that behaves roughly like a {@code Map}, allowing
 * other objects to be associated many-to-one (key -&gt; value).
 *
 * <p>
 * Functionally equivalent to {@link ScratchObjectIdentityDictionary}.
 */
public class ScratchObjectDictionary extends ScratchObjectAbstractDictionary
{
    /**
     * Class ID in binary files.
     */
    public static final int CLASS_ID = 24;

    /**
     * Constructs a new dictionary.
     */
    public ScratchObjectDictionary()
    {
        super(CLASS_ID);
    }
}

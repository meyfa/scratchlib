package scratchlib.objects;

/**
 * Represents either an object or its reference, if not yet resolved.
 */
public class ScratchOptionalField
{
    private int referenceID = -1;
    private ScratchObject value;

    /**
     * Constructor for when the reference is not yet resolved.
     * 
     * @param referenceID The unresolved reference ID.
     * @throws IllegalArgumentException If referenceID &lt; 1.
     */
    public ScratchOptionalField(int referenceID)
    {
        if (referenceID < 1) {
            throw new IllegalArgumentException("referenceID may not be < 1");
        }
        this.referenceID = referenceID;
    }

    /**
     * Constructor for when there already is a value.
     * 
     * @param value The field value.
     * @throws IllegalArgumentException If value is null.
     */
    public ScratchOptionalField(ScratchObject value)
    {
        if (value == null) {
            throw new IllegalArgumentException("value may not be null");
        }
        this.value = value;
    }

    /**
     * @return The object this field refers to, if it exists.
     * @throws IllegalStateException If not yet resolved.
     */
    public ScratchObject get()
    {
        if (value == null) {
            throw new IllegalStateException("reference field unresolved");
        }
        return value;
    }

    /**
     * @return Whether this field is resolved, i.e. has a value.
     */
    public boolean isResolved()
    {
        return value != null;
    }

    /**
     * @return This field's reference ID, if available.
     */
    public int getReferenceID()
    {
        return referenceID;
    }

    /**
     * If this field has an associated reference ID, looks up the object it
     * points to from the given table and stores it as this field's value.
     * 
     * @param ref The reference lookup table.
     */
    public void resolve(ScratchReferenceTable ref)
    {
        if (referenceID < 1) {
            return;
        }
        this.value = ref.lookup(referenceID);
    }
}

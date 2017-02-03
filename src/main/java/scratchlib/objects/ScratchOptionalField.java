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
     */
    public ScratchOptionalField(int referenceID)
    {
        this.referenceID = referenceID;
    }

    /**
     * Constructor for when there already is a value.
     * 
     * @param value The field value.
     */
    public ScratchOptionalField(ScratchObject value)
    {
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
        if (referenceID < 0) {
            return;
        }
        this.value = ref.lookup(referenceID);
    }
}

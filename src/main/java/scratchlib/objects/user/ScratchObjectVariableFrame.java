package scratchlib.objects.user;

import scratchlib.objects.fixed.collections.ScratchObjectDictionary;


/**
 * BYOB 3.1.1-specific class, use unknown.
 */
public class ScratchObjectVariableFrame extends ScratchUserClassObject
{
    /**
     * Class ID in binary files.
     */
    public static final int CLASS_ID = 205;
    /**
     * Class version in binary files.
     */
    public static final ClassVersion CLASS_VERSION = new ClassVersion(1);

    /**
     * Specifies the single field of this class. Stores an empty dictionary. Its
     * name and use are unknown.
     *
     * @see ScratchObjectDictionary
     */
    public static final String FIELD_UNKNOWN0 = "unknown0";

    /**
     * Constructs an instance with the default values.
     */
    public ScratchObjectVariableFrame()
    {
        this(CLASS_ID, CLASS_VERSION);
    }

    /**
     * Constructs an instance with the default values and with the given classID
     * and version.
     *
     * @param classID The ID of the class this object belongs to.
     * @param version The version of the class this object belongs to.
     */
    public ScratchObjectVariableFrame(int classID, ClassVersion version)
    {
        super(classID, version);

        specifyField(FIELD_UNKNOWN0, new ScratchObjectDictionary());
    }
}

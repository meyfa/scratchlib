package scratchlib.objects.user.morphs.ui;

import scratchlib.objects.inline.ScratchObjectBoolean;
import scratchlib.objects.inline.ScratchObjectSmallInteger16;


/**
 * Represents a slider UI on the stage that responds to variable changes, and as
 * a part of {@link ScratchObjectWatcherMorph}, can be used to modify the
 * variable.
 */
public class ScratchObjectWatcherSliderMorph extends ScratchObjectSimpleSliderMorph
{
    /**
     * Class ID in binary files.
     */
    public static final int CLASS_ID = 174;
    /**
     * Class version in binary files.
     */
    public static final ClassVersion CLASS_VERSION = new ClassVersion(1);

    /**
     * Use unknown. Seems to always be equal to the Boolean 'true'.
     *
     * @see ScratchObjectBoolean
     */
    public static final String FIELD_UNKNOWN0 = "unknown0";
    /**
     * Use unknown. Seems to always be equal to the 16-bit integer 0.
     *
     * @see ScratchObjectSmallInteger16
     */
    public static final String FIELD_UNKNOWN1 = "unknown1";

    /**
     * Constructs an instance with the default values.
     */
    public ScratchObjectWatcherSliderMorph()
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
    public ScratchObjectWatcherSliderMorph(int classID, ClassVersion version)
    {
        super(classID, version);

        specifyField(FIELD_UNKNOWN0, ScratchObjectBoolean.TRUE);
        specifyField(FIELD_UNKNOWN1, new ScratchObjectSmallInteger16((short) 0));
    }
}

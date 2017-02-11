package scratchlib.objects.user.morphs.ui;

import scratchlib.objects.ScratchObject;
import scratchlib.objects.inline.ScratchObjectBoolean;
import scratchlib.objects.inline.ScratchObjectSmallInteger16;


/**
 * Represents a variable display on the stage, showing the variable name and its
 * value, optionally with a slider.
 */
public class ScratchObjectWatcherMorph extends ScratchObjectAlignmentMorph
{
    /**
     * Class ID in binary files.
     */
    public static final int CLASS_ID = 155;
    /**
     * Class version in binary files.
     */
    public static final ClassVersion CLASS_VERSION = new ClassVersion(5);

    /**
     * Specifies the morph used for displaying the title string.
     * 
     * @see ScratchObjectStringMorph
     */
    public static final String FIELD_TITLE_MORPH = "titleMorph";

    /**
     * Specifies the morph used for displaying the watched value.
     * 
     * @see ScratchObjectUpdatingStringMorph
     */
    public static final String FIELD_READOUT = "readout";
    /**
     * Specifies the morph used for displaying the frame around the readout.
     * 
     * @see ScratchObjectWatcherReadoutFrameMorph
     */
    public static final String FIELD_READOUT_FRAME = "readoutFrame";

    /**
     * Specifies the morph used for displaying the slider, if this is a watcher
     * that has one.
     * 
     * @see ScratchObjectWatcherSliderMorph
     */
    public static final String FIELD_SCRATCH_SLIDER = "scratchSlider";

    /**
     * Specifies the alignment morph containing all child elements.
     * 
     * @see ScratchObjectAlignmentMorph
     */
    public static final String FIELD_WATCHER = "watcher";

    /**
     * Specifies whether the watched variable is a sprite-specific variable (as
     * opposed to global).
     * 
     * @see ScratchObjectBoolean
     */
    public static final String FIELD_IS_SPRITE_SPECIFIC = "isSpriteSpecific";

    /**
     * Unused.
     * 
     * @see ScratchObject#NIL
     */
    public static final String FIELD_UNUSED = "unused";

    /**
     * Specifies the minimum value for the slider morph. Default 0.
     * 
     * @see ScratchObjectSmallInteger16
     */
    public static final String FIELD_SLIDER_MIN = "sliderMin";
    /**
     * Specifies the maximum value for the slider morph. Default 100.
     * 
     * @see ScratchObjectSmallInteger16
     */
    public static final String FIELD_SLIDER_MAX = "sliderMax";

    /**
     * Specifies whether this is a large watcher. There are three types of
     * watchers: normal, normal with slider, and large (only value is displayed
     * in big font on a completely orange background).
     * 
     * @see ScratchObjectBoolean
     */
    public static final String FIELD_IS_LARGE = "isLarge";

    /**
     * Constructs an instance with the default values.
     */
    public ScratchObjectWatcherMorph()
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
    public ScratchObjectWatcherMorph(int classID, ClassVersion version)
    {
        super(classID, version);

        specifyField(FIELD_TITLE_MORPH, new ScratchObjectStringMorph());

        specifyField(FIELD_READOUT, new ScratchObjectUpdatingStringMorph());
        specifyField(FIELD_READOUT_FRAME,
                new ScratchObjectWatcherReadoutFrameMorph());

        specifyField(FIELD_SCRATCH_SLIDER,
                new ScratchObjectWatcherSliderMorph());

        specifyField(FIELD_WATCHER, NIL);

        specifyField(FIELD_IS_SPRITE_SPECIFIC, NIL);

        specifyField(FIELD_UNUSED, NIL);

        specifyField(FIELD_SLIDER_MIN,
                new ScratchObjectSmallInteger16((short) 0));
        specifyField(FIELD_SLIDER_MAX,
                new ScratchObjectSmallInteger16((short) 100));

        specifyField(FIELD_IS_LARGE, ScratchObjectBoolean.FALSE);
    }
}

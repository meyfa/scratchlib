package scratchlib.objects.user.morphs.ui;

import scratchlib.objects.ScratchObject;
import scratchlib.objects.fixed.data.ScratchObjectSymbol;
import scratchlib.objects.fixed.data.ScratchObjectUtf8;
import scratchlib.objects.inline.ScratchObjectBoolean;
import scratchlib.objects.inline.ScratchObjectFloat;
import scratchlib.objects.inline.ScratchObjectSmallInteger16;
import scratchlib.objects.user.morphs.ScratchObjectSpriteMorph;
import scratchlib.objects.user.morphs.ScratchObjectStageMorph;


/**
 * Represents a slider UI on the Scratch stage.
 */
public class ScratchObjectSimpleSliderMorph extends ScratchObjectRectangleMorph
{
    /**
     * Class ID in binary files.
     */
    public static final int CLASS_ID = 107;
    /**
     * Class version in binary files.
     */
    public static final ClassVersion CLASS_VERSION = new ClassVersion(1);

    /**
     * Specifies the image morph used as the slider.
     *
     * @see ScratchObjectImageMorph
     */
    public static final String FIELD_SLIDER = "slider";

    /**
     * Specifies the percentage the slider has been moved to (as a float 0 - 1).
     *
     * @see ScratchObjectFloat
     */
    public static final String FIELD_VALUE = "value";
    /**
     * Specifies the selector (i.e. function) called to set the backing value.
     * For variable watcher sliders, this is usually "setVar:to:".
     *
     * @see ScratchObjectSymbol
     */
    public static final String FIELD_SET_VALUE_SELECTOR = "setValueSelector";

    /**
     * Seems to be unused (always nil).
     *
     * @see ScratchObject#NIL
     */
    public static final String FIELD_SLIDER_SHADOW = "sliderShadow";
    /**
     * Seems to be unused (always nil).
     *
     * @see ScratchObject#NIL
     */
    public static final String FIELD_SLIDER_COLOR = "sliderColor";

    /**
     * Specifies whether the slider generates descending values. Usually false.
     *
     * @see ScratchObjectBoolean
     */
    public static final String FIELD_DESCENDING = "descending";

    /**
     * Seems to be unused (always nil).
     *
     * @see ScratchObject#NIL
     */
    public static final String FIELD_MODEL = "model";

    /**
     * Specifies the target this slider calls the setValueSelector on (usually
     * the stage or a sprite). For variable watcher sliders, this is the morph
     * holding the variable.
     *
     * @see ScratchObjectStageMorph
     * @see ScratchObjectSpriteMorph
     */
    public static final String FIELD_TARGET = "target";

    /**
     * Specifies the name of the variable this slider is showing / manipulating.
     *
     * @see ScratchObjectUtf8
     */
    public static final String FIELD_ACTION_SELECTOR = "actionSelector";
    /**
     * Seems to be unused (always 0).
     *
     * @see ScratchObjectSmallInteger16
     */
    public static final String FIELD_ARGUMENTS = "arguments";
    /**
     * Seems to be unused (always 100).
     *
     * @see ScratchObjectSmallInteger16
     */
    public static final String FIELD_ACT_WHEN = "actWhen";

    /**
     * Constructs an instance with the default values.
     */
    public ScratchObjectSimpleSliderMorph()
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
    public ScratchObjectSimpleSliderMorph(int classID, ClassVersion version)
    {
        super(classID, version);

        specifyField(FIELD_SLIDER, NIL);

        specifyField(FIELD_VALUE, new ScratchObjectFloat(0));
        specifyField(FIELD_SET_VALUE_SELECTOR,
                new ScratchObjectSymbol("setVar:to:"));

        specifyField(FIELD_SLIDER_SHADOW, NIL);
        specifyField(FIELD_SLIDER_COLOR, NIL);

        specifyField(FIELD_DESCENDING, ScratchObjectBoolean.FALSE);

        specifyField(FIELD_MODEL, NIL);

        specifyField(FIELD_TARGET, NIL);

        specifyField(FIELD_ACTION_SELECTOR, NIL);
        specifyField(FIELD_ARGUMENTS,
                new ScratchObjectSmallInteger16((short) 0));
        specifyField(FIELD_ACT_WHEN,
                new ScratchObjectSmallInteger16((short) 100));
    }
}

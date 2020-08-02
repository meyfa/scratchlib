package scratchlib.objects.user.morphs.ui;

import scratchlib.objects.fixed.data.ScratchObjectAbstractString;
import scratchlib.objects.fixed.data.ScratchObjectSymbol;
import scratchlib.objects.fixed.data.ScratchObjectUtf8;
import scratchlib.objects.inline.ScratchObjectBoolean;
import scratchlib.objects.inline.ScratchObjectFloat;
import scratchlib.objects.inline.ScratchObjectSmallInteger16;
import scratchlib.objects.user.morphs.ScratchObjectSpriteMorph;
import scratchlib.objects.user.morphs.ScratchObjectStageMorph;


/**
 * Represents a string displayed on the stage. The string is obtained through a
 * selector, and can hence always reflect the current value, for example of a
 * variable.
 */
public class ScratchObjectUpdatingStringMorph extends ScratchObjectStringMorph
{
    /**
     * Class ID in binary files.
     */
    public static final int CLASS_ID = 106;
    /**
     * Class version in binary files.
     */
    public static final ClassVersion CLASS_VERSION = new ClassVersion(1);

    /**
     * Specifies the format to use when displaying the string. Usually
     * "default".
     *
     * @see ScratchObjectSymbol
     */
    public static final String FIELD_FORMAT = "format";

    /**
     * Specifies the target to invoke the get/put selectors on. Usually the
     * stage or the sprite that the displayed variable belongs to.
     *
     * @see ScratchObjectStageMorph
     * @see ScratchObjectSpriteMorph
     */
    public static final String FIELD_TARGET = "target";

    /**
     * Specifies the selector (i.e. function) to invoke for getting the value to
     * display. If this is a variable display, the value of this field is
     * "getVar:".
     *
     * @see ScratchObjectSymbol
     */
    public static final String FIELD_GET_SELECTOR = "getSelector";
    /**
     * Specifies the selector (i.e. function) to invoke for updating the
     * string's source. If this is a variable display, the value of this field
     * is nil.
     *
     * @see ScratchObjectSymbol
     */
    public static final String FIELD_PUT_SELECTOR = "putSelector";
    /**
     * Specifies the parameter to pass to the get/put selectors when invoking
     * them. If this is a variable display, the value of this field is the
     * variable name.
     *
     * @see ScratchObjectUtf8
     */
    public static final String FIELD_PARAMETER = "parameter";

    /**
     * Specifies the precision with which to display decimals returned by the
     * get selector. Default '0.1'.
     *
     * @see ScratchObjectFloat
     */
    public static final String FIELD_FLOAT_PRECISION = "floatPrecision";
    /**
     * Specifies whether this morph may be grown to accommodate longer strings.
     * Default true.
     *
     * @see ScratchObjectBoolean
     */
    public static final String FIELD_GROWABLE = "growable";
    /**
     * Specifies the interval between updates. Default 100.
     *
     * @see ScratchObjectSmallInteger16
     */
    public static final String FIELD_STEP_TIME = "stepTime";

    /**
     * Constructs an instance with the default values.
     */
    public ScratchObjectUpdatingStringMorph()
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
    public ScratchObjectUpdatingStringMorph(int classID, ClassVersion version)
    {
        super(classID, version);

        specifyField(FIELD_FORMAT, new ScratchObjectSymbol("default"));

        specifyField(FIELD_TARGET, NIL);

        specifyField(FIELD_GET_SELECTOR, new ScratchObjectSymbol("getVar:"));
        specifyField(FIELD_PUT_SELECTOR, NIL);
        specifyField(FIELD_PARAMETER, new ScratchObjectUtf8(""));

        specifyField(FIELD_FLOAT_PRECISION, new ScratchObjectFloat(0.1));
        specifyField(FIELD_GROWABLE, ScratchObjectBoolean.TRUE);
        specifyField(FIELD_STEP_TIME, new ScratchObjectSmallInteger16((short) 100));
    }

    /**
     * @return This morph's parameter string.
     *
     * @see #FIELD_PARAMETER
     */
    public String getParameter()
    {
        return ((ScratchObjectAbstractString) getField(FIELD_PARAMETER)).getValue();
    }

    /**
     * Sets this morph's parameter string.
     *
     * @param parameter The new parameter string.
     *
     * @see #FIELD_PARAMETER
     */
    public void setParameter(String parameter)
    {
        setField(FIELD_PARAMETER, new ScratchObjectUtf8(parameter));
    }
}

package scratchlib.objects.user.morphs;

import scratchlib.objects.fixed.collections.ScratchObjectArray;
import scratchlib.objects.fixed.colors.ScratchObjectColor;
import scratchlib.objects.fixed.dimensions.ScratchObjectRectangle;
import scratchlib.objects.inline.ScratchObjectSmallInteger16;
import scratchlib.objects.user.ScratchUserClassObject;


/**
 * A "Morph" is anything shown on the stage, i.e. an object that can be drawn.
 * This is the base class for all such objects.
 */
public class ScratchObjectMorph extends ScratchUserClassObject
{
    /**
     * Class ID in binary files.
     */
    public static final int CLASS_ID = 100;
    /**
     * Class version in binary files.
     */
    public static final ClassVersion CLASS_VERSION = new ClassVersion(1);

    /**
     * Specifies a morph's bounding rectangle.
     * 
     * @see ScratchObjectRectangle
     */
    public static final String FIELD_BOUNDS = "bounds";

    /**
     * Specifies a morph's parent morph.
     * 
     * @see ScratchObjectMorph
     */
    public static final String FIELD_OWNER = "owner";
    /**
     * Specifies a morph's child morphs.
     * 
     * @see ScratchObjectArray
     */
    public static final String FIELD_SUBMORPHS = "submorphs";

    /**
     * Specifies a morph's color.
     * 
     * @see ScratchObjectColor
     */
    public static final String FIELD_COLOR = "color";

    /**
     * Specifies a morph's 16-bit integer flags.
     * 
     * <p>
     * This is relevant for sprites: They store a 0 here if they are visible,
     * but store a 1 if they are invisible (hidden via the "hide" block).
     * 
     * @see ScratchObjectSmallInteger16
     */
    public static final String FIELD_FLAGS = "flags";
    /**
     * Specifies a morph's properties object. (type unknown - ever used?)
     */
    public static final String FIELD_PROPERTIES = "properties";

    /**
     * Constructs an instance with the default values.
     */
    public ScratchObjectMorph()
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
    public ScratchObjectMorph(int classID, ClassVersion version)
    {
        super(classID, version);

        specifyField(FIELD_BOUNDS, new ScratchObjectRectangle(0, 0, 0, 0));

        specifyField(FIELD_OWNER, NIL);
        specifyField(FIELD_SUBMORPHS, new ScratchObjectArray());

        specifyField(FIELD_COLOR, new ScratchObjectColor(1023, 1023, 1023));

        specifyField(FIELD_FLAGS, new ScratchObjectSmallInteger16((short) 0));
        specifyField(FIELD_PROPERTIES, NIL);
    }
}

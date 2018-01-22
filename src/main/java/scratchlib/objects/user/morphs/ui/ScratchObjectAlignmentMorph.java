package scratchlib.objects.user.morphs.ui;

import scratchlib.objects.fixed.data.ScratchObjectSymbol;
import scratchlib.objects.inline.ScratchObjectSmallInteger16;


/**
 * This class is used for laying out other UI morphs on the stage.
 */
public class ScratchObjectAlignmentMorph extends ScratchObjectRectangleMorph
{
    /**
     * Class ID in binary files.
     */
    public static final int CLASS_ID = 104;
    /**
     * Class version in binary files.
     */
    public static final ClassVersion CLASS_VERSION = new ClassVersion(1);

    /**
     * Specifies the morph's orientation, which is either "vertical" or
     * "horizontal" (a symbol).
     *
     * @see ScratchObjectSymbol
     */
    public static final String FIELD_ORIENTATION = "orientation";
    /**
     * Specifies the morph's centering behavior. Usually just "center". Another
     * possible value (there could be, and probably are, more) is "topLeft".
     *
     * @see ScratchObjectSymbol
     */
    public static final String FIELD_CENTERING = "centering";

    /**
     * Specifies the morph's horizontal resize behavior. Usually "shrinkWrap".
     * Other possible values (there could be more) are "rigid" and "spaceFill".
     *
     * @see ScratchObjectSymbol
     */
    public static final String FIELD_H_RESIZING = "hResizing";
    /**
     * Specifies the morph's vertical resize behavior. Usually "shrinkWrap".
     * Other possible values (there could be more) are "rigid" and "spaceFill".
     *
     * @see ScratchObjectSymbol
     */
    public static final String FIELD_V_RESIZING = "vResizing";

    /**
     * Specifies the morph's inset. This is an integer number.
     *
     * @see ScratchObjectSmallInteger16
     */
    public static final String FIELD_INSET = "inset";

    /**
     * Constructs an instance with the default values.
     */
    public ScratchObjectAlignmentMorph()
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
    public ScratchObjectAlignmentMorph(int classID, ClassVersion version)
    {
        super(classID, version);

        specifyField(FIELD_ORIENTATION, new ScratchObjectSymbol("vertical"));
        specifyField(FIELD_CENTERING, new ScratchObjectSymbol("center"));

        specifyField(FIELD_H_RESIZING, new ScratchObjectSymbol("shrinkWrap"));
        specifyField(FIELD_V_RESIZING, new ScratchObjectSymbol("shrinkWrap"));

        specifyField(FIELD_INSET, new ScratchObjectSmallInteger16((short) 2));
    }
}

package scratchlib.objects.user.morphs;

import java.awt.Color;

import scratchlib.objects.fixed.colors.ScratchObjectColor;
import scratchlib.objects.fixed.colors.ScratchObjectTranslucentColor;
import scratchlib.objects.inline.ScratchObjectSmallInteger16;


/**
 * Base class for bordered UI morphs on the Scratch stage.
 */
public class ScratchObjectBorderedMorph extends ScratchObjectMorph
{
    /**
     * Specifies a bordered morph's border width.
     *
     * @see ScratchObjectSmallInteger16
     */
    public static final String FIELD_BORDER_WIDTH = "borderWidth";
    /**
     * Specifies a bordered morph's border color.
     *
     * @see ScratchObjectColor
     * @see ScratchObjectTranslucentColor
     */
    public static final String FIELD_BORDER_COLOR = "borderColor";

    /**
     * Constructs an instance with the default values and with the given classID
     * and version.
     *
     * @param classID The ID of the class this object belongs to.
     * @param version The version of the class this object belongs to.
     */
    public ScratchObjectBorderedMorph(int classID, ClassVersion version)
    {
        super(classID, version);

        specifyField(FIELD_BORDER_WIDTH, new ScratchObjectSmallInteger16((short) 0));
        specifyField(FIELD_BORDER_COLOR, new ScratchObjectColor(new Color(148, 145, 145)));
    }
}

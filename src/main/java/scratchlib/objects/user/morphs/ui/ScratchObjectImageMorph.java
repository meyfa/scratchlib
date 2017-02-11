package scratchlib.objects.user.morphs.ui;

import scratchlib.objects.fixed.forms.ScratchObjectColorForm;
import scratchlib.objects.inline.ScratchObjectFloat;
import scratchlib.objects.user.morphs.ScratchObjectMorph;


/**
 * Class used for displaying images on the stage. This is used inside
 * {@link ScratchObjectWatcherSliderMorph} instances for the knob.
 */
public class ScratchObjectImageMorph extends ScratchObjectMorph
{
    /**
     * Class ID in binary files.
     */
    public static final int CLASS_ID = 110;
    /**
     * Class version in binary files.
     */
    public static final ClassVersion CLASS_VERSION = new ClassVersion(1);

    /**
     * Specifies this image's form.
     * 
     * @see ScratchObjectColorForm
     */
    public static final String FIELD_FORM = "form";
    /**
     * Specifies this image's transparency as a float.
     * 
     * @see ScratchObjectFloat
     */
    public static final String FIELD_TRANSPARENCY = "transparency";

    /**
     * Constructs an instance with the default values.
     */
    public ScratchObjectImageMorph()
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
    public ScratchObjectImageMorph(int classID, ClassVersion version)
    {
        super(classID, version);

        specifyField(FIELD_FORM, NIL);
        specifyField(FIELD_TRANSPARENCY, NIL);
    }
}

package scratchlib.objects.user.media;

import scratchlib.objects.ScratchObject;
import scratchlib.objects.fixed.dimensions.ScratchObjectPoint;
import scratchlib.objects.fixed.forms.ScratchObjectColorForm;
import scratchlib.objects.fixed.forms.ScratchObjectForm;


/**
 * Media reference type for images.
 */
public class ScratchObjectImageMedia extends ScratchObjectMedia
{
    /**
     * Class ID in binary files.
     */
    public static final int CLASS_ID = 162;
    /**
     * Class version in binary files.
     */
    public static final ClassVersion CLASS_VERSION = new ClassVersion(4);

    /**
     * Specifies the media's form containing the actual image data.
     * 
     * @see ScratchObjectForm
     * @see ScratchObjectColorForm
     */
    public static final String FIELD_FORM = "form";

    /**
     * Specifies the media's rotation center.
     * 
     * @see ScratchObjectPoint
     */
    public static final String FIELD_ROTATION_CENTER = "rotationCenter";

    /**
     * Seems to be unused (always nil).
     * 
     * @see ScratchObject#NIL
     */
    public static final String FIELD_TEXT_BOX = "textBox";
    /**
     * Seems to be unused (always nil).
     * 
     * @see ScratchObject#NIL
     */
    public static final String FIELD_JPEG_BYTES = "jpegBytes";
    /**
     * Seems to be unused (always nil).
     * 
     * @see ScratchObject#NIL
     */
    public static final String FIELD_COMPOSITE_FORM = "compositeForm";

    /**
     * Constructs an instance with the default values.
     */
    public ScratchObjectImageMedia()
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
    public ScratchObjectImageMedia(int classID, ClassVersion version)
    {
        super(classID, version);

        specifyField(FIELD_FORM, NIL);
        specifyField(FIELD_ROTATION_CENTER, new ScratchObjectPoint(0, 0));

        specifyField(FIELD_TEXT_BOX, NIL);
        specifyField(FIELD_JPEG_BYTES, NIL);
        specifyField(FIELD_COMPOSITE_FORM, NIL);
    }
}

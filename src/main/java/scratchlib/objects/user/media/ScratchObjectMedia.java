package scratchlib.objects.user.media;

import scratchlib.objects.fixed.data.ScratchObjectUtf8;
import scratchlib.objects.user.ScratchUserClassObject;


/**
 * Base class for the two Scratch media types (image and sound).
 */
public class ScratchObjectMedia extends ScratchUserClassObject
{
    /**
     * Specifies a media's user-assigned name.
     * 
     * @see ScratchObjectUtf8
     */
    public static final String FIELD_MEDIA_NAME = "mediaName";

    /**
     * Constructs an instance with the default values and with the given classID
     * and version.
     * 
     * @param classID The ID of the class this object belongs to.
     * @param version The version of the class this object belongs to.
     */
    public ScratchObjectMedia(int classID, ClassVersion version)
    {
        super(classID, version);

        specifyField(FIELD_MEDIA_NAME, new ScratchObjectUtf8(""));
    }
}

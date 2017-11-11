package scratchlib.objects.user.media;

import scratchlib.objects.fixed.data.ScratchObjectAbstractString;
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

    /**
     * @return The media's name.
     */
    public String getName()
    {
        return ((ScratchObjectAbstractString) getField(FIELD_MEDIA_NAME))
                .getValue();
    }

    /**
     * Updates the media's name. Note that this change is NOT reflected anywhere
     * else (when used in blocks, etc).
     * 
     * @param name The new name.
     */
    public void setName(String name)
    {
        setField(FIELD_MEDIA_NAME, new ScratchObjectUtf8(name));
    }
}

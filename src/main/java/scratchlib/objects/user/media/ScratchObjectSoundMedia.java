package scratchlib.objects.user.media;

import scratchlib.objects.ScratchObject;
import scratchlib.objects.inline.ScratchObjectSmallInteger16;


/**
 * Media reference type for sounds.
 */
public class ScratchObjectSoundMedia extends ScratchObjectMedia
{
    /**
     * Class ID in binary files.
     */
    public static final int CLASS_ID = 164;
    /**
     * Class version in binary files.
     */
    public static final ClassVersion CLASS_VERSION = new ClassVersion(2);

    /**
     * Specifies the sampled sound object this media uses.
     *
     * @see ScratchObjectSampledSound
     */
    public static final String FIELD_ORIGINAL_SOUND = "originalSound";

    /**
     * Specifies the sound's volume. 100 is full volume, 0 is silenced.
     *
     * @see ScratchObjectSmallInteger16
     */
    public static final String FIELD_VOLUME = "volume";
    /**
     * Specifies the sound's balance. 0 is fully left, 100 is fully right, 50
     * (default) is centered.
     *
     * @see ScratchObjectSmallInteger16
     */
    public static final String FIELD_BALANCE = "balance";

    /**
     * Seems to serve no purpose (is always nil).
     *
     * @see ScratchObject#NIL
     */
    public static final String FIELD_COMPRESSED_SAMPLE_RATE = "compressedSampleRate";
    /**
     * Seems to serve no purpose (is always nil).
     *
     * @see ScratchObject#NIL
     */
    public static final String FIELD_COMPRESSED_BITS_PER_SAMPLE = "compressedBitsPerSample";
    /**
     * Seems to serve no purpose (is always nil).
     *
     * @see ScratchObject#NIL
     */
    public static final String FIELD_COMPRESSED_DATA = "compressedData";

    /**
     * Constructs an instance with the default values.
     */
    public ScratchObjectSoundMedia()
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
    public ScratchObjectSoundMedia(int classID, ClassVersion version)
    {
        super(classID, version);

        specifyField(FIELD_ORIGINAL_SOUND, new ScratchObjectSampledSound());

        specifyField(FIELD_VOLUME,
                new ScratchObjectSmallInteger16((short) 100));
        specifyField(FIELD_BALANCE,
                new ScratchObjectSmallInteger16((short) 50));

        specifyField(FIELD_COMPRESSED_SAMPLE_RATE, NIL);
        specifyField(FIELD_COMPRESSED_BITS_PER_SAMPLE, NIL);
        specifyField(FIELD_COMPRESSED_DATA, NIL);
    }
}

package scratchlib.objects.user.media;

import scratchlib.objects.ScratchObject;
import scratchlib.objects.fixed.collections.ScratchObjectArray;
import scratchlib.objects.fixed.data.ScratchObjectSoundBuffer;
import scratchlib.objects.inline.ScratchObjectSmallInteger;
import scratchlib.objects.inline.ScratchObjectSmallInteger16;
import scratchlib.objects.user.ScratchUserClassObject;


/**
 * Represents a sound split into samples. Stores the raw sample data, as well as
 * extra data like the scaled volume, sampling rate or envelopes.
 */
public class ScratchObjectSampledSound extends ScratchUserClassObject
{
    /**
     * Class ID in binary files.
     */
    public static final int CLASS_ID = 109;
    /**
     * Class version in binary files.
     */
    public static final ClassVersion CLASS_VERSION = new ClassVersion(1);

    /**
     * Specifies the sound's envelopes. Usually just an empty array.
     *
     * @see ScratchObjectArray
     */
    public static final String FIELD_ENVELOPES = "envelopes";

    /**
     * Specifies the scaled volume (whatever "scaled" means). Usually 32768
     * (2^15).
     *
     * @see ScratchObjectSmallInteger
     */
    public static final String FIELD_SCALED_VOL = "scaledVol";

    /**
     * Specifies the initial count (whatever that means). Seems to be equal to
     * the "samples" buffer length in bytes if the "original sampling rate"
     * equals 11025, or half the buffer length if the "original sampling rate"
     * equals 22050.
     *
     * @see ScratchObjectSmallInteger
     * @see ScratchObjectSmallInteger16
     */
    public static final String FIELD_INITIAL_COUNT = "initialCount";

    /**
     * Specifies the sound buffer containing the samples.
     *
     * @see ScratchObjectSoundBuffer
     */
    public static final String FIELD_SAMPLES = "samples";
    /**
     * Specifies the original sampling rate (in Hertz). Usually either 11025 or
     * 22050. Apparently has an influence on the "initial count".
     *
     * @see ScratchObjectSmallInteger16
     */
    public static final String FIELD_ORIGINAL_SAMPLING_RATE = "originalSamplingRate";
    /**
     * Specifies the number of samples there are, which is always half the
     * "samples" sound buffer byte length.
     *
     * @see ScratchObjectSmallInteger
     * @see ScratchObjectSmallInteger16
     */
    public static final String FIELD_SAMPLES_SIZE = "samplesSize";

    /**
     * Specifies the scaled increment (whatever that means). Appears to be 32768
     * (2^15) for an "original sampling rate" of 11025, and 65536 (2^16) for an
     * "original sampling rate" of 22050.
     *
     * @see ScratchObjectSmallInteger
     */
    public static final String FIELD_SCALED_INCREMENT = "scaledIncrement";
    /**
     * Specifies the scaled initial index (whatever that means). Usually nil.
     *
     * @see ScratchObject#NIL
     */
    public static final String FIELD_SCALED_INITIAL_INDEX = "scaledInitialIndex";

    /**
     * Constructs an instance with the default values.
     */
    public ScratchObjectSampledSound()
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
    public ScratchObjectSampledSound(int classID, ClassVersion version)
    {
        super(classID, version);

        specifyField(FIELD_ENVELOPES, new ScratchObjectArray());

        specifyField(FIELD_SCALED_VOL, new ScratchObjectSmallInteger(32768));

        specifyField(FIELD_INITIAL_COUNT,
                new ScratchObjectSmallInteger16((short) 0));

        specifyField(FIELD_SAMPLES, new ScratchObjectSoundBuffer(new byte[0]));
        specifyField(FIELD_ORIGINAL_SAMPLING_RATE,
                new ScratchObjectSmallInteger16((short) 22050));
        specifyField(FIELD_SAMPLES_SIZE, new ScratchObjectSmallInteger(37792));

        specifyField(FIELD_SCALED_INCREMENT,
                new ScratchObjectSmallInteger(65536));
        specifyField(FIELD_SCALED_INITIAL_INDEX, NIL);
    }
}

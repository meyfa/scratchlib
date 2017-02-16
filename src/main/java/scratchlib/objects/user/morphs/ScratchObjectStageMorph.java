package scratchlib.objects.user.morphs;

import java.awt.image.BufferedImage;
import java.util.Arrays;

import scratchlib.media.ScratchFormEncoder;
import scratchlib.objects.ScratchObject;
import scratchlib.objects.fixed.collections.ScratchObjectDictionary;
import scratchlib.objects.fixed.collections.ScratchObjectOrderedCollection;
import scratchlib.objects.fixed.data.ScratchObjectUtf8;
import scratchlib.objects.fixed.dimensions.ScratchObjectRectangle;
import scratchlib.objects.fixed.forms.ScratchObjectForm;
import scratchlib.objects.inline.ScratchObjectFloat;
import scratchlib.objects.inline.ScratchObjectSmallInteger16;
import scratchlib.objects.user.media.ScratchObjectImageMedia;


/**
 * Represents the stage morph object, containing the sprites, global variables
 * and global lists, among other things.
 */
public class ScratchObjectStageMorph extends ScratchObjectScriptableMorph
{
    /**
     * Class ID in binary files.
     */
    public static final int CLASS_ID = 125;
    /**
     * Class version in binary files.
     */
    public static final ClassVersion CLASS_VERSION = new ClassVersion(5);

    /**
     * Specifies the zoom. Is always a float of 1.0f.
     * 
     * @see ScratchObjectFloat
     */
    public static final String FIELD_ZOOM = "zoom";
    /**
     * Specifies the horizontal panning. Is always 0.
     * 
     * @see ScratchObjectSmallInteger16
     */
    public static final String FIELD_H_PAN = "hPan";
    /**
     * Specifies the vertical panning. Is always 0.
     * 
     * @see ScratchObjectSmallInteger16
     */
    public static final String FIELD_V_PAN = "vPan";

    /**
     * Seems to serve no purpose (is always nil).
     * 
     * @see ScratchObject#NIL
     */
    public static final String FIELD_OBSOLETE_SAVED_STATE = "obsoleteSavedState";

    /**
     * Specifies an ordered collection of all sprite morphs
     * ({@link ScratchObjectSpriteMorph}).
     * 
     * @see ScratchObjectOrderedCollection
     */
    public static final String FIELD_SPRITES = "sprites";

    /**
     * Specifies the stage's sound volume. 100 (default) is full volume, 0 is
     * silence.
     * 
     * @see ScratchObjectSmallInteger16
     */
    public static final String FIELD_VOLUME = "volume";
    /**
     * Specifies the stage's sound tempo in beats per minute. Default is 60.
     * 
     * @see ScratchObjectSmallInteger16
     */
    public static final String FIELD_TEMPO_BPM = "tempoBPM";

    /**
     * Use unknown. Always an empty dictionary.
     * 
     * @see ScratchObjectDictionary
     */
    public static final String FIELD_SCENE_STATES = "sceneStates";

    /**
     * Specifies the dictionary of global lists (keys:
     * {@link ScratchObjectUtf8}, values: {@link ScratchObjectListMorph}).
     * 
     * @see ScratchObjectDictionary
     */
    public static final String FIELD_LISTS = "lists";

    /**
     * Constructs an instance with the default values.
     */
    public ScratchObjectStageMorph()
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
    public ScratchObjectStageMorph(int classID, ClassVersion version)
    {
        super(classID, version);

        specifyField(FIELD_ZOOM, new ScratchObjectFloat(1.0));
        specifyField(FIELD_H_PAN, new ScratchObjectSmallInteger16((short) 0));
        specifyField(FIELD_V_PAN, new ScratchObjectSmallInteger16((short) 0));

        specifyField(FIELD_OBSOLETE_SAVED_STATE, NIL);

        specifyField(FIELD_SPRITES, new ScratchObjectOrderedCollection());

        specifyField(FIELD_VOLUME,
                new ScratchObjectSmallInteger16((short) 100));
        specifyField(FIELD_TEMPO_BPM,
                new ScratchObjectSmallInteger16((short) 60));

        specifyField(FIELD_SCENE_STATES, new ScratchObjectDictionary());

        specifyField(FIELD_LISTS, new ScratchObjectDictionary());

        // populate fields

        setField(FIELD_OBJ_NAME, new ScratchObjectUtf8("Stage"));

        setField(FIELD_BOUNDS, new ScratchObjectRectangle((short) 0, (short) 0,
                (short) 480, (short) 360));

        ScratchObjectImageMedia background = getEmptyBackground();
        setField(FIELD_MEDIA,
                new ScratchObjectOrderedCollection(Arrays.asList(background)));
        setField(FIELD_COSTUME, background);
    }

    /**
     * @return An empty image that can be used as the default background without
     *         taking up a lot of space.
     */
    private static ScratchObjectImageMedia getEmptyBackground()
    {
        BufferedImage im = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        ScratchObjectForm form = ScratchFormEncoder.encode(im);

        ScratchObjectImageMedia background = new ScratchObjectImageMedia();
        background.setField(ScratchObjectImageMedia.FIELD_MEDIA_NAME,
                new ScratchObjectUtf8("empty"));
        background.setField(ScratchObjectImageMedia.FIELD_FORM, form);

        return background;
    }
}

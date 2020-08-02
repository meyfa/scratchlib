package scratchlib.objects.user.morphs;

import scratchlib.objects.ScratchObject;
import scratchlib.objects.fixed.collections.ScratchObjectDictionary;
import scratchlib.objects.fixed.collections.ScratchObjectOrderedCollection;
import scratchlib.objects.fixed.collections.ScratchObjectSet;
import scratchlib.objects.fixed.data.ScratchObjectSymbol;
import scratchlib.objects.fixed.data.ScratchObjectUtf8;
import scratchlib.objects.fixed.dimensions.ScratchObjectPoint;
import scratchlib.objects.inline.ScratchObjectBoolean;
import scratchlib.objects.inline.ScratchObjectFloat;
import scratchlib.objects.inline.ScratchObjectSmallInteger16;
import scratchlib.project.ScratchVersion;


/**
 * Represents a sprite morph object somewhere on a stage.
 */
public class ScratchObjectSpriteMorph extends ScratchObjectScriptableMorph
{
    /**
     * Class ID in binary files.
     */
    public static final int CLASS_ID = 124;
    /**
     * Class version in binary files.
     */
    public static final ClassVersion CLASS_VERSION = new ClassVersion(3, 5);

    /**
     * Specifies a sprite's visibility. 100 is full visibility, 0 is basically
     * hidden.
     *
     * <p>
     * Note that this is NOT the toggleable visibility (for that, see
     * {@link ScratchObjectMorph#FIELD_FLAGS}), but rather the "ghost" graphic
     * effect.<br>
     * Moreover, note that Scratch fails to store this on its own, and so it is
     * always 100 when read. Yet, loading it is fully supported, so this CAN
     * safely be changed for a given sprite and WILL have an effect.
     *
     * @see ScratchObjectSmallInteger16
     */
    public static final String FIELD_VISIBILITY = "visibility";

    /**
     * Specifies a sprite's scaling point.
     *
     * @see ScratchObjectPoint
     */
    public static final String FIELD_SCALE_POINT = "scalePoint";

    /**
     * Specifies the number of degrees a sprite is rotated. A displayed
     * direction of 90 equals 0 degrees, while 180 equals 90 degrees, and going
     * further in the clockwise direction increases the rotation similarly.
     *
     * @see ScratchObjectSmallInteger16
     */
    public static final String FIELD_ROTATION_DEGREES = "rotationDegrees";
    /**
     * Specifies a sprite's rotation style, as a symbol. One of "normal" (full
     * 360 degrees), "leftRight" (only mirroring allowed), or "none" (no
     * rotation at all).
     *
     * @see ScratchObjectSymbol
     */
    public static final String FIELD_ROTATION_STYLE = "rotationStyle";

    /**
     * Specifies a sprite's sound volume. 100 (default) is full volume, 0 is
     * silence.
     *
     * @see ScratchObjectSmallInteger16
     */
    public static final String FIELD_VOLUME = "volume";
    /**
     * Specifies a sprite's sound tempo in beats per minute. Default is 60.
     *
     * @see ScratchObjectSmallInteger16
     */
    public static final String FIELD_TEMPO_BPM = "tempoBPM";

    /**
     * Specifies whether a sprite can be dragged by the user even in
     * presentation mode (see lock icon next to name input field).
     *
     * @see ScratchObjectBoolean
     */
    public static final String FIELD_DRAGGABLE = "draggable";

    /**
     * Use unknown. Always an empty dictionary.
     *
     * @see ScratchObjectDictionary
     */
    public static final String FIELD_SCENE_STATES = "sceneStates";

    /**
     * Specifies the dictionary of sprite-specific lists (keys:
     * {@link ScratchObjectUtf8}, values: {@link ScratchObjectListMorph}).
     *
     * @see ScratchObjectDictionary
     */
    public static final String FIELD_LISTS = "lists";

    // -- START BYOB FIELDS

    /**
     * BYOB 3.1.1 field. Specifies the amount by which a sprite is scaled, where
     * 100 is no scaling and 200 is double the unscaled size.
     *
     * <p>
     * Non-BYOB versions calculate this dynamically from the bounds field.
     *
     * @see ScratchObjectFloat
     * @see ScratchObjectSmallInteger16
     */
    public static final String FIELD_SCALE = "scale";

    /**
     * BYOB 3.1.1 field. Seems to be unused (always nil).
     *
     * @see ScratchObject#NIL
     */
    public static final String FIELD_UNKNOWN0 = "unknown0";
    /**
     * BYOB 3.1.1 field. Seems to be unused (always an empty collection).
     *
     * @see ScratchObjectOrderedCollection
     */
    public static final String FIELD_UNKNOWN1 = "unknown1";
    /**
     * BYOB 3.1.1 field. Seems to be unused (always the Boolean 'true').
     *
     * @see ScratchObjectBoolean
     */
    public static final String FIELD_UNKNOWN2 = "unknown2";

    /**
     * BYOB 3.1.1 field. Specifies a sprite's midpoint / center coordinates.
     *
     * <p>
     * Non-BYOB versions calculate this dynamically from the bounds field.
     *
     * @see ScratchObjectPoint
     */
    public static final String FIELD_MIDPOINT = "midpoint";

    /**
     * BYOB 3.1.1 field. Specifies a sprite's parent sprite (the one that was
     * cloned to make this sprite).
     *
     * @see ScratchObjectSpriteMorph
     */
    public static final String FIELD_PARENT = "parent";
    /**
     * BYOB 3.1.1 field. Specifies the types of media that a sprite inherits
     * without change from its parent (see {@link #FIELD_PARENT}). This is a set
     * of none, or any number of, the symbols "sounds" and "costumes".
     *
     * <p>
     * Note that even the inherited media items are still contained in a
     * sprite's media collection, so this is probably just an informational
     * field.
     *
     * @see ScratchObjectSet
     */
    public static final String FIELD_INHERITED_MEDIA = "inheritedMedia";

    /**
     * Constructs an instance with the default values.
     */
    public ScratchObjectSpriteMorph()
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
    public ScratchObjectSpriteMorph(int classID, ClassVersion version)
    {
        super(classID, version);

        specifyField(FIELD_VISIBILITY, new ScratchObjectSmallInteger16((short) 100));

        specifyField(FIELD_SCALE_POINT, new ScratchObjectPoint(1, 1));

        specifyField(FIELD_ROTATION_DEGREES, new ScratchObjectFloat(0));
        specifyField(FIELD_ROTATION_STYLE, new ScratchObjectSymbol("normal"));

        specifyField(FIELD_VOLUME, new ScratchObjectSmallInteger16((short) 100));
        specifyField(FIELD_TEMPO_BPM, new ScratchObjectSmallInteger16((short) 60));

        specifyField(FIELD_DRAGGABLE, ScratchObjectBoolean.FALSE);

        specifyField(FIELD_SCENE_STATES, new ScratchObjectDictionary());

        specifyField(FIELD_LISTS, new ScratchObjectDictionary());

        // BYOB fields

        specifyField(FIELD_SCALE, new ScratchObjectSmallInteger16((short) 100), ScratchVersion.BYOB311);

        specifyField(FIELD_UNKNOWN0, NIL, ScratchVersion.BYOB311);
        specifyField(FIELD_UNKNOWN1, new ScratchObjectOrderedCollection(), ScratchVersion.BYOB311);
        specifyField(FIELD_UNKNOWN2, ScratchObjectBoolean.TRUE, ScratchVersion.BYOB311);

        specifyField(FIELD_MIDPOINT, new ScratchObjectPoint(0, 0), ScratchVersion.BYOB311);

        specifyField(FIELD_PARENT, NIL, ScratchVersion.BYOB311);
        specifyField(FIELD_INHERITED_MEDIA, new ScratchObjectSet(), ScratchVersion.BYOB311);

        // populate fields

        setField(FIELD_OBJ_NAME, new ScratchObjectUtf8("Sprite1"));
    }
}

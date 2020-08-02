package scratchlib.objects.user.morphs;

import java.util.stream.Stream;

import scratchlib.objects.ScratchObject;
import scratchlib.objects.fixed.collections.ScratchObjectAbstractCollection;
import scratchlib.objects.fixed.collections.ScratchObjectDictionary;
import scratchlib.objects.fixed.collections.ScratchObjectOrderedCollection;
import scratchlib.objects.fixed.data.ScratchObjectUtf8;
import scratchlib.objects.fixed.dimensions.ScratchObjectRectangle;
import scratchlib.objects.inline.ScratchObjectFloat;
import scratchlib.objects.inline.ScratchObjectSmallInteger16;


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

        specifyField(FIELD_VOLUME, new ScratchObjectSmallInteger16((short) 100));
        specifyField(FIELD_TEMPO_BPM, new ScratchObjectSmallInteger16((short) 60));

        specifyField(FIELD_SCENE_STATES, new ScratchObjectDictionary());

        specifyField(FIELD_LISTS, new ScratchObjectDictionary());

        // populate fields

        setField(FIELD_OBJ_NAME, new ScratchObjectUtf8("Stage"));

        setField(FIELD_BOUNDS, new ScratchObjectRectangle(0, 0, 480, 360));
    }

    /**
     * @return The number of sprites the stage owns.
     */
    public int getSpriteCount()
    {
        // count number of items in 'sprites' collection
        final ScratchObject spritesObj = getField(FIELD_SPRITES);
        return ((ScratchObjectAbstractCollection) spritesObj).size();
    }

    /**
     * Obtains a sprite from the stage. Indexes run from 0 to
     * {@code getSpriteCount()}.
     *
     * @param index The sprite's index.
     * @return The sprite object at the given index.
     */
    public ScratchObjectSpriteMorph getSprite(int index)
    {
        // retrieve item at 'index' in 'sprites' collection
        final ScratchObject spritesObj = getField(FIELD_SPRITES);
        return (ScratchObjectSpriteMorph) (((ScratchObjectAbstractCollection) spritesObj).get(index));
    }

    /**
     * Adds a sprite to the stage. If the sprite was previously owned by any
     * other stage, that stage's {@link #removeSprite(ScratchObjectSpriteMorph)}
     * method is called beforehand.
     *
     * @param sprite The sprite to add.
     */
    public void addSprite(ScratchObjectSpriteMorph sprite)
    {
        // remove from previous owner
        final ScratchObject prevOwner = sprite.getField(FIELD_OWNER);
        if (prevOwner instanceof ScratchObjectStageMorph) {
            ((ScratchObjectStageMorph) prevOwner).removeSprite(sprite);
        }

        // add sprite to 'sprites' collection
        final ScratchObject spritesObj = getField(FIELD_SPRITES);
        ((ScratchObjectAbstractCollection) spritesObj).add(sprite);

        // add sprite to 'submorphs' collection
        final ScratchObject submorphsObj = getField(FIELD_SUBMORPHS);
        ((ScratchObjectAbstractCollection) submorphsObj).add(sprite);

        // update sprite owner
        sprite.setField(FIELD_OWNER, this);
    }

    /**
     * Removes a sprite from the stage. Indexes run from 0 to
     * {@code getSpriteCount()}.
     *
     * @param index The sprite's index.
     */
    public void removeSprite(int index)
    {
        // remove sprite from 'sprites' collection
        final ScratchObject spritesObj = getField(FIELD_SPRITES);
        final ScratchObjectSpriteMorph sprite = (ScratchObjectSpriteMorph) (((ScratchObjectAbstractCollection) spritesObj)
                .remove(index));

        // remove sprite from 'submorphs' collection
        final ScratchObject submorphsObj = getField(FIELD_SUBMORPHS);
        ((ScratchObjectAbstractCollection) submorphsObj).remove(sprite);

        // update sprite owner
        sprite.setField(FIELD_OWNER, ScratchObject.NIL);
    }

    /**
     * Removes a given sprite from the stage.
     *
     * @param sprite The sprite.
     */
    public void removeSprite(ScratchObjectSpriteMorph sprite)
    {
        // remove sprite from 'sprites' collection
        final ScratchObject spritesObj = getField(FIELD_SPRITES);
        ((ScratchObjectAbstractCollection) spritesObj).remove(sprite);

        // remove sprite from 'submorphs' collection
        final ScratchObject submorphsObj = getField(FIELD_SUBMORPHS);
        ((ScratchObjectAbstractCollection) submorphsObj).remove(sprite);

        // update sprite owner
        sprite.setField(FIELD_OWNER, ScratchObject.NIL);
    }

    /**
     * Removes all sprites from the stage.
     */
    public void clearSprites()
    {
        // clear 'sprites' collection
        final ScratchObject spritesObj = getField(FIELD_SPRITES);
        ((ScratchObjectAbstractCollection) spritesObj).clear();

        // filter 'submorphs' collection to only contain non-sprites
        final ScratchObjectAbstractCollection submorphs = (ScratchObjectAbstractCollection) getField(FIELD_SUBMORPHS);
        for (int i = submorphs.size() - 1; i >= 0; --i) {
            final ScratchObject morph = submorphs.get(i);
            if (morph instanceof ScratchObjectSpriteMorph) {
                // remove, then update owner
                submorphs.remove(i);
                ((ScratchObjectSpriteMorph) morph).setField(FIELD_OWNER, ScratchObject.NIL);
            }
        }
    }

    /**
     * @return A stream of all sprites the stage owns.
     */
    public Stream<ScratchObjectSpriteMorph> streamSprites()
    {
        final ScratchObject spritesObj = getField(FIELD_SPRITES);
        return ((ScratchObjectAbstractCollection) spritesObj).stream().map(obj -> (ScratchObjectSpriteMorph) obj);
    }
}

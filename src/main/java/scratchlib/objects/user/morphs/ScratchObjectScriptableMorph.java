package scratchlib.objects.user.morphs;

import scratchlib.objects.fixed.collections.ScratchObjectArray;
import scratchlib.objects.fixed.collections.ScratchObjectDictionary;
import scratchlib.objects.fixed.collections.ScratchObjectOrderedCollection;
import scratchlib.objects.fixed.data.ScratchObjectUtf8;
import scratchlib.objects.inline.ScratchObjectBoolean;
import scratchlib.objects.user.media.ScratchObjectImageMedia;
import scratchlib.project.ScratchVersion;


/**
 * Represents a morph that has a name, media, variables, and - most importantly
 * - can contain scripts.
 * 
 * <p>
 * The only known subclasses are the stage ({@link ScratchObjectStageMorph}) and
 * its sprites ({@link ScratchObjectSpriteMorph}).
 */
public class ScratchObjectScriptableMorph extends ScratchObjectMorph
{
    /**
     * Specifies a morph's name.
     * 
     * @see ScratchObjectUtf8
     */
    public static final String FIELD_OBJ_NAME = "objName";

    /**
     * Specifies a morph's variables (e.g. the stage vars, or a sprite's vars).
     * 
     * @see ScratchObjectDictionary
     */
    public static final String FIELD_VARS = "vars";
    /**
     * Specifies a morph's scripting bin (array of (point, blocksArray) tuples).
     * 
     * @see ScratchObjectArray
     */
    public static final String FIELD_BLOCKS_BIN = "blocksBin";

    /**
     * BYOB 3.1.1 field. Specifies a morph's custom blocks.
     * 
     * @see ScratchObjectOrderedCollection
     */
    public static final String FIELD_CUSTOM_BLOCKS = "customBlocks";

    /**
     * Specifies whether a morph is just a clone of another morph.
     * 
     * @see ScratchObjectBoolean
     */
    public static final String FIELD_IS_CLONE = "isClone";

    /**
     * Specifies all of the morph's media.
     * 
     * @see ScratchObjectOrderedCollection
     */
    public static final String FIELD_MEDIA = "media";
    /**
     * Specifies the morph's current costume.
     * 
     * @see ScratchObjectImageMedia
     */
    public static final String FIELD_COSTUME = "costume";

    /**
     * Constructs an instance with the default values and with the given classID
     * and version.
     * 
     * @param classID The ID of the class this object belongs to.
     * @param version The version of the class this object belongs to.
     */
    public ScratchObjectScriptableMorph(int classID, ClassVersion version)
    {
        super(classID, version);

        specifyField(FIELD_OBJ_NAME, new ScratchObjectUtf8(""));

        specifyField(FIELD_VARS, new ScratchObjectDictionary());
        specifyField(FIELD_BLOCKS_BIN, new ScratchObjectArray());

        specifyField(FIELD_CUSTOM_BLOCKS, new ScratchObjectOrderedCollection(),
                ScratchVersion.BYOB311);

        specifyField(FIELD_IS_CLONE, ScratchObjectBoolean.FALSE);

        specifyField(FIELD_MEDIA, new ScratchObjectOrderedCollection());
        specifyField(FIELD_COSTUME, NIL);
    }
}

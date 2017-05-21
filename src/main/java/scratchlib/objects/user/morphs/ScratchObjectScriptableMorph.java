package scratchlib.objects.user.morphs;

import java.awt.image.BufferedImage;
import java.util.Arrays;

import scratchlib.media.ScratchFormEncoder;
import scratchlib.objects.fixed.collections.ScratchObjectArray;
import scratchlib.objects.fixed.collections.ScratchObjectDictionary;
import scratchlib.objects.fixed.collections.ScratchObjectOrderedCollection;
import scratchlib.objects.fixed.data.ScratchObjectAbstractString;
import scratchlib.objects.fixed.data.ScratchObjectUtf8;
import scratchlib.objects.fixed.forms.ScratchObjectForm;
import scratchlib.objects.inline.ScratchObjectBoolean;
import scratchlib.objects.user.ScratchObjectCustomBlockDefinition;
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
     * Maps their names to their values.
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
     * BYOB 3.1.1 field. Specifies a morph's custom blocks
     * ({@link ScratchObjectCustomBlockDefinition}).
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

        // populate fields

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

    /**
     * @return The morph's name.
     */
    public String getName()
    {
        return ((ScratchObjectAbstractString) getField(FIELD_OBJ_NAME))
                .getValue();
    }

    /**
     * Updates the morph's name. Note that this change is NOT reflected anywhere
     * else (when used in blocks, etc).
     * 
     * @param name The new name.
     */
    public void setName(String name)
    {
        setField(FIELD_OBJ_NAME, new ScratchObjectUtf8(name));
    }
}

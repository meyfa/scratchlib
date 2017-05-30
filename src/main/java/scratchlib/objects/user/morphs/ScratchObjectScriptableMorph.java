package scratchlib.objects.user.morphs;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.stream.Stream;

import scratchlib.media.ScratchFormEncoder;
import scratchlib.objects.ScratchObject;
import scratchlib.objects.fixed.collections.ScratchObjectAbstractCollection;
import scratchlib.objects.fixed.collections.ScratchObjectArray;
import scratchlib.objects.fixed.collections.ScratchObjectDictionary;
import scratchlib.objects.fixed.collections.ScratchObjectOrderedCollection;
import scratchlib.objects.fixed.data.ScratchObjectAbstractString;
import scratchlib.objects.fixed.data.ScratchObjectUtf8;
import scratchlib.objects.fixed.dimensions.ScratchObjectPoint;
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

    // ---- BASIC --------------------------------------------------------------

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

    // ---- CUSTOM BLOCKS ------------------------------------------------------

    /**
     * @return The number of custom blocks the morph owns.
     */
    public int getCustomBlockCount()
    {
        final ScratchObject collectionObj = getField(FIELD_CUSTOM_BLOCKS);
        return ((ScratchObjectAbstractCollection) collectionObj).size();
    }

    /**
     * Obtains a custom block from the morph. Indexes run from 0 to
     * {@code getCustomBlockCount(morph)}.
     * 
     * @param index The custom block's index.
     * @return The custom block object at the given index.
     */
    public ScratchObjectCustomBlockDefinition getCustomBlock(int index)
    {
        final ScratchObjectAbstractCollection collection = (ScratchObjectAbstractCollection) getField(
                FIELD_CUSTOM_BLOCKS);
        return (ScratchObjectCustomBlockDefinition) collection.get(index);
    }

    /**
     * Adds a custom block to the morph. Note that the block's {@code isGlobal}
     * flag is not updated.
     * 
     * @param block The custom block to add.
     */
    public void addCustomBlock(ScratchObjectCustomBlockDefinition block)
    {
        final ScratchObject collectionObj = getField(FIELD_CUSTOM_BLOCKS);
        ((ScratchObjectAbstractCollection) collectionObj).add(block);
    }

    /**
     * Removes a custom block from the morph. Indexes run from 0 to
     * {@code getCustomBlockCount(morph)}.
     * 
     * @param index The custom block's index.
     */
    public void removeCustomBlock(int index)
    {
        final ScratchObject collectionObj = getField(FIELD_CUSTOM_BLOCKS);
        ((ScratchObjectAbstractCollection) collectionObj).remove(index);
    }

    /**
     * Removes a given custom block from the morph.
     * 
     * @param block The custom block.
     */
    public void removeCustomBlock(ScratchObjectCustomBlockDefinition block)
    {
        final ScratchObject collectionObj = getField(FIELD_CUSTOM_BLOCKS);
        ((ScratchObjectAbstractCollection) collectionObj).remove(block);
    }

    /**
     * Removes all custom blocks from this morph.
     */
    public void clearCustomBlocks()
    {
        final ScratchObject collectionObj = getField(FIELD_CUSTOM_BLOCKS);
        ((ScratchObjectAbstractCollection) collectionObj).clear();
    }

    /**
     * @return A stream of all custom blocks the morph owns.
     */
    public Stream<ScratchObjectCustomBlockDefinition> streamCustomBlocks()
    {
        final ScratchObject collectionObj = getField(FIELD_CUSTOM_BLOCKS);
        return ((ScratchObjectAbstractCollection) collectionObj).stream()
                .map(obj -> (ScratchObjectCustomBlockDefinition) obj);
    }

    // ---- SCRIPTS ------------------------------------------------------------

    /**
     * @return The number of scripts the morph owns.
     */
    public int getScriptCount()
    {
        final ScratchObject collectionObj = getField(FIELD_BLOCKS_BIN);
        return ((ScratchObjectAbstractCollection) collectionObj).size();
    }

    /**
     * Obtains a script from the morph. A script is a tuple/collection of its
     * location (a point) and body (a collection of blocks). Indexes run from 0
     * to {@code getScriptCount()}.
     * 
     * @param index The script's index.
     * @return The script at the given index.
     */
    public ScratchObjectAbstractCollection getScript(int index)
    {
        final ScratchObjectAbstractCollection collection = (ScratchObjectAbstractCollection) getField(
                FIELD_BLOCKS_BIN);
        return (ScratchObjectAbstractCollection) collection.get(index);
    }

    /**
     * Obtains the location of one of the morph's scripts. Indexes run from 0 to
     * {@code getScriptCount()}.
     * 
     * @param index The script's index.
     * @return The location of the script at the given index.
     */
    public ScratchObjectPoint getScriptLocation(int index)
    {
        return (ScratchObjectPoint) getScript(index).get(0);
    }

    /**
     * Obtains the body of one of the morph's scripts. The body is a collection
     * of block arrays. Indexes run from 0 to {@code getScriptCount()}.
     * 
     * @param index The script's index.
     * @return The body of the script at the given index.
     */
    public ScratchObjectAbstractCollection getScriptBody(int index)
    {
        return (ScratchObjectAbstractCollection) getScript(index).get(1);
    }

    /**
     * Adds a script to the morph.
     * 
     * @param location The script's location.
     * @param body The script's body (collection of block arrays).
     */
    public void addScript(ScratchObjectPoint location,
            ScratchObjectAbstractCollection body)
    {
        final ScratchObject collectionObj = getField(FIELD_BLOCKS_BIN);
        final ScratchObject script = new ScratchObjectArray(
                Arrays.asList(location, body));
        ((ScratchObjectAbstractCollection) collectionObj).add(script);
    }

    /**
     * Adds a script to the morph. A script is a tuple/collection of its
     * location (a point) and body (a collection of blocks).
     * 
     * @param script The script tuple.
     */
    public void addScript(ScratchObjectAbstractCollection script)
    {
        final ScratchObject collectionObj = getField(FIELD_BLOCKS_BIN);
        ((ScratchObjectAbstractCollection) collectionObj).add(script);
    }

    /**
     * Removes a script from the morph. Indexes run from 0 to
     * {@code getScriptCount()}.
     * 
     * @param index The script's index.
     */
    public void removeScript(int index)
    {
        final ScratchObject collectionObj = getField(FIELD_BLOCKS_BIN);
        ((ScratchObjectAbstractCollection) collectionObj).remove(index);
    }

    /**
     * Removes a given script from the morph.
     * 
     * @param script The script.
     */
    public void removeScript(ScratchObjectAbstractCollection script)
    {
        final ScratchObject collectionObj = getField(FIELD_BLOCKS_BIN);
        ((ScratchObjectAbstractCollection) collectionObj).remove(script);
    }

    /**
     * Removes all scripts from this morph.
     */
    public void clearScripts()
    {
        final ScratchObject collectionObj = getField(FIELD_BLOCKS_BIN);
        ((ScratchObjectAbstractCollection) collectionObj).clear();
    }

    /**
     * @return A stream of all scripts the morph owns.
     */
    public Stream<ScratchObjectAbstractCollection> streamScripts()
    {
        final ScratchObject collectionObj = getField(FIELD_BLOCKS_BIN);
        return ((ScratchObjectAbstractCollection) collectionObj).stream()
                .map(obj -> (ScratchObjectAbstractCollection) obj);
    }

    /**
     * @return A stream of the bodies of all scripts the morph owns.
     */
    public Stream<ScratchObjectAbstractCollection> streamScriptBodies()
    {
        return streamScripts()
                .map(script -> (ScratchObjectAbstractCollection) script.get(1));
    }
}

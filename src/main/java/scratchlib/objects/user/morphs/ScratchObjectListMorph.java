package scratchlib.objects.user.morphs;

import scratchlib.objects.fixed.collections.ScratchObjectArray;
import scratchlib.objects.fixed.data.ScratchObjectUtf8;
import scratchlib.project.ScratchVersion;


/**
 * Represents a user-created list that can be displayed on the stage and be
 * modified with blocks.
 */
public class ScratchObjectListMorph extends ScratchObjectBorderedMorph
{
    /**
     * Class ID in binary files.
     */
    public static final int CLASS_ID = 175;
    /**
     * Class version in binary files.
     */
    public static final ClassVersion CLASS_VERSION = new ClassVersion(2, 3);

    /**
     * Specifies a list's name.
     *
     * @see ScratchObjectUtf8
     */
    public static final String FIELD_LIST_NAME = "listName";
    /**
     * Specifies a list's content array, which stores string entries.
     *
     * @see ScratchObjectArray
     */
    public static final String FIELD_CELL_MORPHS = "cellMorphs";
    /**
     * Specifies a list's so-called "target", which is nothing but its owner
     * morph (either the stage, or a sprite).
     *
     * @see ScratchObjectStageMorph
     * @see ScratchObjectSpriteMorph
     */
    public static final String FIELD_TARGET = "target";
    /**
     * BYOB 3.1.1 field. Specifies an array used in addition to
     * {@link #FIELD_CELL_MORPHS} for storing values; while the other field
     * stores primitive values, this one contains object entries (e.g.
     * references to sprites that are in a list).
     *
     * <p>
     * The sprites are serialized to arrays with two entries ("sprite" followed
     * by the respective sprite's name), making this an array of 2-tuples.
     *
     * @see ScratchObjectArray
     */
    public static final String FIELD_OBJECT_ENTRIES = "objectEntries";

    /**
     * Constructs an instance with the default values.
     */
    public ScratchObjectListMorph()
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
    public ScratchObjectListMorph(int classID, ClassVersion version)
    {
        super(classID, version);

        specifyField(FIELD_LIST_NAME, new ScratchObjectUtf8(""));
        specifyField(FIELD_CELL_MORPHS, new ScratchObjectArray());
        specifyField(FIELD_TARGET, NIL);

        specifyField(FIELD_OBJECT_ENTRIES, new ScratchObjectArray(), ScratchVersion.BYOB311);
    }
}

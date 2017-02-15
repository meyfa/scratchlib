package scratchlib.objects.user;

import scratchlib.objects.fixed.collections.ScratchObjectArray;
import scratchlib.objects.fixed.collections.ScratchObjectDictionary;
import scratchlib.objects.fixed.collections.ScratchObjectSet;
import scratchlib.objects.fixed.data.ScratchObjectString;
import scratchlib.objects.fixed.data.ScratchObjectSymbol;
import scratchlib.objects.fixed.data.ScratchObjectUtf8;
import scratchlib.objects.inline.ScratchObjectBoolean;


/**
 * BYOB 3.1.1-specific class, represents a custom block definition.
 */
public class ScratchObjectCustomBlockDefinition extends ScratchUserClassObject
{
    /**
     * Class ID in binary files.
     */
    public static final int CLASS_ID = 201;
    /**
     * Class version in binary files.
     */
    public static final ClassVersion CLASS_VERSION = new ClassVersion(4);

    /**
     * The block specification. This is a string version of the block header,
     * where parameter names are prefixed with a percentage sign ('%').
     * 
     * <p>
     * Example: The block "letter (1) of [world]" might have the specification
     * "letter %index of %string".
     * 
     * <p>
     * Note that there is sometimes more than one space between labels/params.
     * 
     * @see ScratchObjectUtf8
     */
    public static final String FIELD_USER_SPEC = "userSpec";

    /**
     * Seems to be unused (always an empty set).
     * 
     * @see ScratchObjectSet
     */
    public static final String FIELD_BLOCKS_VAR = "blocksVar";

    /**
     * Specifies whether a block is atomic, i.e. runs in an instant without
     * intermediate graphics updates.
     * 
     * @see ScratchObjectBoolean
     */
    public static final String FIELD_IS_ATOMIC = "isAtomic";

    /**
     * Seems to be unused (always false). Use {@link #FIELD_TYPE} instead!
     * 
     * @see ScratchObjectBoolean
     */
    public static final String FIELD_IS_REPORTER = "isReporter";
    /**
     * Seems to be unused (always false). Use {@link #FIELD_TYPE} instead!
     * 
     * @see ScratchObjectBoolean
     */
    public static final String FIELD_IS_BOOLEAN = "isBoolean";

    /**
     * Specifies a block's script. This follows the usual script standards,
     * which means that it is an array of block arrays.
     * 
     * @see ScratchObjectArray
     */
    public static final String FIELD_BODY = "body";
    /**
     * Specifies a reporter block's default answer, i.e. the value inside the
     * field at the dialog's bottom.
     * 
     * @see ScratchObjectUtf8
     * @see ScratchObjectArray
     */
    public static final String FIELD_ANSWER = "answer";

    /**
     * Specifies a block's return value type. This is "none" for command blocks,
     * "any" for normal reporter blocks, and "boolean" for predicate reporter
     * blocks.
     * 
     * <p>
     * It might be possible to achieve strictly type-compatible blocks by
     * inserting values like "number", but this is untested (and would be
     * useless, since "any" does just fine).
     * 
     * @see ScratchObjectSymbol
     */
    public static final String FIELD_TYPE = "type";
    /**
     * Specifies a block's block category. This is a symbol and one of the
     * following:
     * 
     * <ul>
     * <li>"motion" (dark blue)
     * <li>"control" (yellow)
     * <li>"looks" (purple)
     * <li>"sensing" (light blue)
     * <li>"sound" (pink)
     * <li>"operators" (light green)
     * <li>"pen" (dark green)
     * <li>"variables" (light orange)
     * <li>"list" (dark orange)
     * <li>"none" (gray)
     * </ul>
     * 
     * @see ScratchObjectSymbol
     */
    public static final String FIELD_CATEGORY = "category";

    /**
     * Specifies a block's parameter types. This is a dictionary where the keys
     * are the parameter names ({@link ScratchObjectUtf8}), and the values are
     * symbols ({@link ScratchObjectSymbol}) describing their types.
     * 
     * <p>
     * The default is "any". All possible values are:
     * 
     * <ul>
     * <li>"object" (sprites, stage, etc.)
     * <li>"number"
     * <li>"command" (inline)
     * <li>"loop" (C-Shape)
     * <li>"text"
     * <li>"any"
     * <li>"reporter"
     * <li>"unevaluated"
     * <li>"list"
     * <li>"boolean"
     * <li>"predicate"
     * <li>"unevaluatedBoolean"
     * </ul>
     * 
     * <p>
     * Moreover, every value above can be suffixed with "List" (i.e. "anyList"
     * or "unevaluatedBooleanList") to form a multiple input parameter.<br>
     * The special value "template" corresponds to an "internal variable visible
     * to the caller".
     * 
     * @see ScratchObjectDictionary
     */
    public static final String FIELD_DECLARATIONS = "declarations";
    /**
     * Specifies a block's parameter defaults. This is a dictionary where the
     * keys are the parameter names ({@link ScratchObjectUtf8}), and the values
     * are some sort of string (either {@link ScratchObjectUtf8} or
     * {@link ScratchObjectString}) containing their respective default values.
     * 
     * @see ScratchObjectDictionary
     */
    public static final String FIELD_DEFAULTS = "defaults";

    /**
     * Specifies whether a block is global (stage-owned) instead of
     * sprite-owned.
     * 
     * @see ScratchObjectBoolean
     */
    public static final String FIELD_IS_GLOBAL = "isGlobal";

    /**
     * Constructs an instance with the default values.
     */
    public ScratchObjectCustomBlockDefinition()
    {
        super(CLASS_ID, CLASS_VERSION);

        specifyField(FIELD_USER_SPEC, new ScratchObjectUtf8(""));

        specifyField(FIELD_BLOCKS_VAR, new ScratchObjectSet());

        specifyField(FIELD_IS_ATOMIC, ScratchObjectBoolean.FALSE);

        specifyField(FIELD_IS_REPORTER, ScratchObjectBoolean.FALSE);
        specifyField(FIELD_IS_BOOLEAN, ScratchObjectBoolean.FALSE);

        specifyField(FIELD_BODY, new ScratchObjectArray());
        specifyField(FIELD_ANSWER, NIL);

        specifyField(FIELD_TYPE, new ScratchObjectSymbol("none"));
        specifyField(FIELD_CATEGORY, new ScratchObjectSymbol("none"));

        specifyField(FIELD_DECLARATIONS, new ScratchObjectDictionary());
        specifyField(FIELD_DEFAULTS, new ScratchObjectDictionary());

        specifyField(FIELD_IS_GLOBAL, ScratchObjectBoolean.TRUE);
    }
}

package scratchlib.objects.user.morphs.ui;

import scratchlib.objects.fixed.collections.ScratchObjectArray;
import scratchlib.objects.fixed.data.ScratchObjectAbstractString;
import scratchlib.objects.fixed.data.ScratchObjectSymbol;
import scratchlib.objects.fixed.data.ScratchObjectUtf8;
import scratchlib.objects.inline.ScratchObjectSmallInteger16;
import scratchlib.objects.user.morphs.ScratchObjectMorph;


/**
 * Represents a string displayed on the stage, for example a variable or list
 * name.
 */
public class ScratchObjectStringMorph extends ScratchObjectMorph
{
    /**
     * Class ID in binary files.
     */
    public static final int CLASS_ID = 105;
    /**
     * Class version in binary files.
     */
    public static final ClassVersion CLASS_VERSION = new ClassVersion(1);

    /**
     * Specifies the font and font size to use when displaying this string. This
     * is an array where the first element is a symbol (default "VerdanaBold")
     * and the second is a 16-bit integer (default 10).
     * 
     * @see ScratchObjectArray
     */
    public static final String FIELD_FONT = "font";
    /**
     * Specifies the amount of "emphasis" to put on the displayed string.
     * 
     * @see ScratchObjectSmallInteger16
     */
    public static final String FIELD_EMPHASIS = "emphasis";

    /**
     * Specifies the actual string value to display.
     * 
     * @see ScratchObjectUtf8
     */
    public static final String FIELD_CONTENTS = "contents";

    /**
     * Constructs an instance with the default values.
     */
    public ScratchObjectStringMorph()
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
    public ScratchObjectStringMorph(int classID, ClassVersion version)
    {
        super(classID, version);

        ScratchObjectArray font = new ScratchObjectArray();
        font.add(new ScratchObjectSymbol("VerdanaBold"));
        font.add(new ScratchObjectSmallInteger16((short) 10));

        specifyField(FIELD_FONT, font);
        specifyField(FIELD_EMPHASIS,
                new ScratchObjectSmallInteger16((short) 0));

        specifyField(FIELD_CONTENTS, new ScratchObjectUtf8(""));
    }

    /**
     * @return This morph's text contents (the displayed string).
     */
    public String getContents()
    {
        return ((ScratchObjectAbstractString) getField(FIELD_CONTENTS))
                .getValue();
    }

    /**
     * Sets this morph's text contents (the displayed string).
     * 
     * @param contents The new contents.
     */
    public void setContents(String contents)
    {
        setField(FIELD_CONTENTS, new ScratchObjectUtf8(contents));
    }
}

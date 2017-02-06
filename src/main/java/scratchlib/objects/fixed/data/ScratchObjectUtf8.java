package scratchlib.objects.fixed.data;

import java.nio.charset.StandardCharsets;


/**
 * String reference type with the UTF-8 charset, used for most user-defined
 * values (since they are "unsafe").
 */
public class ScratchObjectUtf8 extends ScratchObjectAbstractString
{
    /**
     * Class ID in binary files.
     */
    public static final int CLASS_ID = 14;

    /**
     * Empty constructor for yet-to-be-read instances.
     */
    public ScratchObjectUtf8()
    {
        super(CLASS_ID, StandardCharsets.UTF_8);
    }

    /**
     * @param value The String value.
     */
    public ScratchObjectUtf8(String value)
    {
        super(CLASS_ID, StandardCharsets.UTF_8, value);
    }
}

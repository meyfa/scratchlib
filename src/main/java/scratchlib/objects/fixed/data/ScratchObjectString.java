package scratchlib.objects.fixed.data;

import java.nio.charset.StandardCharsets;


/**
 * Standard string reference type. Uses the US-ASCII charset.
 * 
 * <p>
 * This is the most widely used string class.
 */
public class ScratchObjectString extends ScratchObjectAbstractString
{
    /**
     * Class ID in binary files.
     */
    public static final int CLASS_ID = 9;

    /**
     * Empty constructor for yet-to-be-read instances.
     */
    public ScratchObjectString()
    {
        super(CLASS_ID, StandardCharsets.US_ASCII);
    }

    /**
     * @param value The String value.
     */
    public ScratchObjectString(String value)
    {
        super(CLASS_ID, StandardCharsets.US_ASCII, value);
    }
}

package scratchlib.objects.fixed.data;

import java.nio.charset.StandardCharsets;


/**
 * String reference type used for referencing Scratch-internal values that are
 * guaranteed "safe". As such, uses the US-ASCII charset.
 */
public class ScratchObjectSymbol extends ScratchObjectAbstractString
{
    /**
     * Class ID in binary files.
     */
    public static final int CLASS_ID = 10;

    /**
     * Empty constructor for yet-to-be-read instances.
     */
    public ScratchObjectSymbol()
    {
        super(CLASS_ID, StandardCharsets.US_ASCII);
    }

    /**
     * @param value The String value.
     */
    public ScratchObjectSymbol(String value)
    {
        super(CLASS_ID, StandardCharsets.US_ASCII, value);
    }
}

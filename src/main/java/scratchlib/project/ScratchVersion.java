package scratchlib.project;

/**
 * Enumerates the supported Scratch versions / dialects.
 */
public enum ScratchVersion
{
    /**
     * The binary Scratch 1.4 file format (*.sb).
     * <p>
     * Header: "ScratchV02"<br>
     * Version: "1.4 of 30-Jun-09"
     */
    SCRATCH14("ScratchV02", "1.4 of 30-Jun-09"),

    /**
     * The binary BYOB 3.1.1 file format (*.ypr).
     * <p>
     * Header: "BloxExpV01"<br>
     * Version: "3.1.1 (19-May-11)"
     */
    BYOB311("BloxExpV01", "3.1.1 (19-May-11)");

    private final String header, version;

    private ScratchVersion(String header, String version)
    {
        this.header = header;
        this.version = version;
    }

    /**
     * @return This format's header.
     */
    public String getHeader()
    {
        return header;
    }

    /**
     * @return This format's version string.
     */
    public String getVersionString()
    {
        return version;
    }

    /**
     * Performs a header lookup and returns the matching {@code ScratchVersion}.
     * 
     * @param header The header string to lookup.
     * @return The matching version.
     */
    public static ScratchVersion lookupHeader(String header)
    {
        for (ScratchVersion v : values()) {
            if (v.header.equals(header)) {
                return v;
            }
        }
        return null;
    }
}

package de.jangobrick.scratchlib.project;

/**
 * Represents a Scratch project file that has version information, metadata, a
 * stage with sprites, etc.
 */
public class ScratchProject
{
    private final ScratchVersion version;

    /**
     * @param version This project's version.
     */
    public ScratchProject(ScratchVersion version)
    {
        this.version = version;
    }

    /**
     * @return This project's version.
     */
    public ScratchVersion getVersion()
    {
        return version;
    }
}

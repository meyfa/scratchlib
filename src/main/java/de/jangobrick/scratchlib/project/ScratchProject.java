package de.jangobrick.scratchlib.project;

import java.io.IOException;

import de.jangobrick.scratchlib.objects.ScratchObject;
import de.jangobrick.scratchlib.objects.ScratchObjectStore;
import de.jangobrick.scratchlib.writer.ScratchOutputStream;


/**
 * Represents a Scratch project file that has version information, metadata, a
 * stage with sprites, etc.
 */
public class ScratchProject
{
    private final ScratchVersion version;
    private ScratchObject info = ScratchObject.NIL;
    private ScratchObject stage = ScratchObject.NIL;

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

    /**
     * Writes this project to the given output stream.
     * 
     * @param out The stream to write to.
     * @throws IOException
     */
    public void writeTo(ScratchOutputStream out) throws IOException
    {
        ScratchObjectStore sectionInfo = new ScratchObjectStore(info);
        ScratchObjectStore sectionStage = new ScratchObjectStore(stage);

        // write header
        out.writeString(version.getHeader());

        // write info section
        ScratchOutputStream tempInfoOut = new ScratchOutputStream();
        sectionInfo.writeTo(tempInfoOut);
        // copy to out
        out.write32bitUnsignedInt(tempInfoOut.size());
        tempInfoOut.writeTo(out);

        // write stage section
        sectionStage.writeTo(out);
    }
}

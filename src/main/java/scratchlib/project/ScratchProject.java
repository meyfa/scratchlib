package scratchlib.project;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import scratchlib.objects.ScratchObject;
import scratchlib.objects.ScratchObjectStore;
import scratchlib.writer.ScratchOutputStream;


/**
 * Represents a Scratch project file that has version information, metadata, a
 * stage with sprites, etc.
 */
public class ScratchProject
{
    private final ScratchVersion version;
    private ScratchObjectStore info = new ScratchObjectStore(ScratchObject.NIL);
    private ScratchObjectStore stage = new ScratchObjectStore(
            ScratchObject.NIL);

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
     * @return The object that is the project's info section.
     */
    public ScratchObjectStore getInfoSection()
    {
        return info;
    }

    /**
     * Sets the project's info section.
     * 
     * @param info The new info section.
     */
    public void setInfoSection(ScratchObjectStore info)
    {
        this.info = info;
    }

    /**
     * @return The object that is the project's stage / content section.
     */
    public ScratchObjectStore getStageSection()
    {
        return stage;
    }

    /**
     * Sets the project's stage / content section.
     * 
     * @param stage The new stage section.
     */
    public void setStageSection(ScratchObjectStore stage)
    {
        this.stage = stage;
    }

    /**
     * Writes this project to the given output stream.
     * 
     * @param out The stream to write to.
     * @throws IOException
     */
    public void writeTo(ScratchOutputStream out) throws IOException
    {
        // write header
        out.writeString(version.getHeader());

        // write info section
        ByteArrayOutputStream infoBytes = getSectionBytes(info);
        out.write32bitUnsignedInt(infoBytes.size());
        infoBytes.writeTo(out);

        // write stage section
        stage.writeTo(out, this);
    }

    /**
     * Writes the given object store into a byte array output stream.
     * 
     * @param section The section to write.
     * @return A {@code ByteArrayOutputStream} containing the written bytes.
     * @throws IOException
     */
    private ByteArrayOutputStream getSectionBytes(ScratchObjectStore section)
            throws IOException
    {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ScratchOutputStream out = new ScratchOutputStream(bout);

        section.writeTo(out, this);

        return bout;
    }
}

package de.jangobrick.scratchlib.writer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import de.jangobrick.scratchlib.project.ScratchProject;


/**
 * Allows writing {@link ScratchProject} instances to {@code File}.
 */
public class ScratchWriter
{
    private final File file;

    /**
     * @param dest The file to write to.
     */
    public ScratchWriter(File dest)
    {
        this.file = dest;
    }

    /**
     * Writes the given {@link ScratchProject} to the file.
     * 
     * @param project The project to write.
     * @throws IOException
     */
    public void write(ScratchProject project) throws IOException
    {
        // write to ScratchOutputStream
        ScratchOutputStream out = new ScratchOutputStream();
        project.writeTo(out);

        // write to file
        FileOutputStream fout = new FileOutputStream(file);
        out.writeTo(fout);
        fout.flush();

        // close streams
        out.close();
        fout.close();
    }
}

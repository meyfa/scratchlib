package scratchlib.writer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import scratchlib.project.ScratchProject;


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
        ScratchOutputStream out = new ScratchOutputStream(new FileOutputStream(file));
        project.writeTo(out);
        out.close();
    }
}

package scratchlib.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import scratchlib.project.ScratchProject;
import scratchlib.project.ScratchVersion;


/**
 * Allows reading {@link ScratchProject} instances from {@code File} and
 * {@code InputStream}.
 */
public class ScratchReader
{
    /**
     * Reads a {@link ScratchProject} from the given project file. The version
     * is determined automatically.
     * 
     * @param file The project file to read.
     * @return The project that was read.
     * @throws IOException If an I/O error occurs.
     */
    public ScratchProject read(File file) throws IOException
    {
        try (FileInputStream in = new FileInputStream(file)) {
            return read(in);
        }
    }

    /**
     * Reads a {@link ScratchProject} from the given input stream. The version
     * is determined automatically.
     * 
     * @param in The input stream to read from.
     * @return The project that was read.
     * @throws IOException If an I/O error occurs.
     */
    public ScratchProject read(InputStream in) throws IOException
    {
        try (ScratchInputStream sin = new ScratchInputStream(in)) {

            ScratchVersion version = ScratchVersion
                    .lookupHeader(sin.readString(10));
            if (version == null) {
                throw new IOException("Scratch version unknown");
            }

            return new ScratchProject(version);
        }
    }
}

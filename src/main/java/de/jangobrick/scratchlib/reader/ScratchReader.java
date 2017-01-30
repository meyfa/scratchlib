package de.jangobrick.scratchlib.reader;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import de.jangobrick.scratchlib.project.ScratchProject;
import de.jangobrick.scratchlib.project.ScratchVersion;


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
        DataInputStream din = toDataInputStream(in);

        ScratchVersion version = readHeader(din);
        if (version == null) {
            throw new IOException("Scratch version unknown");
        }

        return new ScratchProject(version);
    }

    /**
     * Casts the given stream into a {@code DataInputStream} if possible,
     * otherwise instantiates a new one.
     * 
     * @param in The input stream to convert.
     * @return A {@code DataInputStream} for the given input stream.
     */
    private static DataInputStream toDataInputStream(InputStream in)
    {
        if (in instanceof DataInputStream) {
            return (DataInputStream) in;
        }
        return new DataInputStream(in);
    }

    /**
     * Reads the header string from the given stream and returns the matching
     * {@link ScratchVersion}.
     * 
     * @param din The input stream.
     * @return The matching {@link ScratchVersion}.
     * @throws IOException
     */
    private ScratchVersion readHeader(DataInputStream din) throws IOException
    {
        byte[] headerBytes = new byte[10];
        din.readFully(headerBytes);
        String header = new String(headerBytes, StandardCharsets.US_ASCII);

        return ScratchVersion.lookupHeader(header);
    }
}

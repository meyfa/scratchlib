package scratchlib.objects.fixed.colors;

import java.awt.Color;
import java.io.IOException;

import scratchlib.objects.IScratchReferenceType;
import scratchlib.objects.ScratchObject;
import scratchlib.objects.ScratchReferenceTable;
import scratchlib.project.ScratchProject;
import scratchlib.reader.ScratchInputStream;
import scratchlib.writer.ScratchOutputStream;


/**
 * Reference type for 32-bit RGB colors. The 32 bits are achieved by allocating
 * 10 bits to each component, and padding with two leading zeros.
 */
public class ScratchObjectColor extends ScratchObject implements IScratchReferenceType
{
    /**
     * Class ID in binary files.
     */
    public static final int CLASS_ID = 30;

    private int r, g, b;

    /**
     * Empty constructor for yet-to-be-read instances.
     */
    public ScratchObjectColor()
    {
        super(CLASS_ID);
    }

    /**
     * @param r The red value, ranging from 0 to 1023.
     * @param g The green value, ranging from 0 to 1023.
     * @param b The blue value, ranging from 0 to 1023.
     */
    public ScratchObjectColor(int r, int g, int b)
    {
        super(CLASS_ID);

        this.r = r;
        this.g = g;
        this.b = b;
    }

    /**
     * @param source The color object to convert to a Scratch RGB color.
     */
    public ScratchObjectColor(Color source)
    {
        super(CLASS_ID);

        this.r = source.getRed() * 1023 / 255;
        this.g = source.getGreen() * 1023 / 255;
        this.b = source.getBlue() * 1023 / 255;
    }

    /**
     * @return The red value, ranging from 0 to 1023 (4x the Java range).
     */
    public int getRed()
    {
        return r;
    }

    /**
     * @return The green value, ranging from 0 to 1023 (4x the Java range).
     */
    public int getGreen()
    {
        return g;
    }

    /**
     * @return The blue value, ranging from 0 to 1023 (4x the Java range).
     */
    public int getBlue()
    {
        return b;
    }

    /**
     * @return This color converted to {@link java.awt.Color}.
     */
    public Color toAwtColor()
    {
        return new Color(r * 255 / 1023, g * 255 / 1023, b * 255 / 1023);
    }

    @Override
    public void writeTo(ScratchOutputStream out, ScratchReferenceTable ref, ScratchProject project) throws IOException
    {
        super.writeTo(out, ref, project);

        final int tenBits = (1 << 10) - 1;

        int result = (r & tenBits) << 20 | (g & tenBits) << 10 | (b & tenBits);
        out.write32bitUnsignedInt(result);
    }

    @Override
    public void readFrom(int id, ScratchInputStream in, ScratchProject project) throws IOException
    {
        super.readFrom(id, in, project);

        final int tenBits = (1 << 10) - 1;

        int num = in.read32bitUnsignedInt();
        this.r = (num >> 20) & tenBits;
        this.g = (num >> 10) & tenBits;
        this.b = num & tenBits;
    }
}

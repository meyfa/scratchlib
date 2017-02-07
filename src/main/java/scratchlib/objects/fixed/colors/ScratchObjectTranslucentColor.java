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
 * Reference type for 40-bit RGBA colors. The format is the same as for
 * {@link ScratchObjectColor}, but with an additional byte for alpha. In other
 * words, there are two bits of padding, then 10 bits per RGB component, and
 * another 8 bits for the A component.
 */
public class ScratchObjectTranslucentColor extends ScratchObject
        implements IScratchReferenceType
{
    /**
     * Class ID in binary files.
     */
    public static final int CLASS_ID = 31;

    private int r, g, b, a;

    /**
     * Empty constructor for yet-to-be-read instances.
     */
    public ScratchObjectTranslucentColor()
    {
        super(CLASS_ID);
    }

    /**
     * @param r The red value, ranging from 0 to 1023.
     * @param g The green value, ranging from 0 to 1023.
     * @param b The blue value, ranging from 0 to 1023.
     * @param a The alpha value, ranging from 0 to 255 (!).
     */
    public ScratchObjectTranslucentColor(int r, int g, int b, int a)
    {
        super(CLASS_ID);

        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    /**
     * @param source The color object to convert to a Scratch RGBA color.
     */
    public ScratchObjectTranslucentColor(Color source)
    {
        super(CLASS_ID);

        this.r = source.getRed() * 4;
        this.g = source.getGreen() * 4;
        this.b = source.getBlue() * 4;
        this.a = source.getAlpha();
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
     * @return The alpha value, ranging from 0 to 255 (normal Java range).
     */
    public int getAlpha()
    {
        return a;
    }

    /**
     * @return This color converted to {@link java.awt.Color}.
     */
    public Color toAwtColor()
    {
        return new Color(r / 4, g / 4, b / 4, a);
    }

    @Override
    public void writeTo(ScratchOutputStream out, ScratchReferenceTable ref,
            ScratchProject project) throws IOException
    {
        super.writeTo(out, ref, project);

        final int tenBits = (1 << 10) - 1;

        int result = (r & tenBits) << 20 | (g & tenBits) << 10 | (b & tenBits);
        out.write32bitUnsignedInt(result);
        out.write(a);
    }

    @Override
    public void readFrom(int id, ScratchInputStream in, ScratchProject project)
            throws IOException
    {
        super.readFrom(id, in, project);

        final int tenBits = (1 << 10) - 1;

        int num = in.read32bitUnsignedInt();
        this.r = (num >> 20) & tenBits;
        this.g = (num >> 10) & tenBits;
        this.b = num & tenBits;

        this.a = in.read();
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + getClassID();
        result = prime * result + a;
        result = prime * result + b;
        result = prime * result + g;
        result = prime * result + r;
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ScratchObjectTranslucentColor other = (ScratchObjectTranslucentColor) obj;
        return a == other.a && b == other.b && g == other.g && r == other.r;
    }
}

package scratchlib.objects.fixed.colors;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.Test;

import scratchlib.objects.ScratchReferenceTable;
import scratchlib.project.ScratchProject;
import scratchlib.project.ScratchVersion;
import scratchlib.reader.ScratchInputStream;
import scratchlib.writer.ScratchOutputStream;


public class ScratchObjectTranslucentColorTest
{
    @Test
    public void convertsFromAwtColor()
    {
        ScratchObjectTranslucentColor col0 = new ScratchObjectTranslucentColor(
                new Color(0, 0, 0, 255));

        assertEquals(0, col0.getRed());
        assertEquals(0, col0.getGreen());
        assertEquals(0, col0.getBlue());
        assertEquals(255, col0.getAlpha());

        ScratchObjectTranslucentColor col1 = new ScratchObjectTranslucentColor(
                new Color(255, 255, 255, 127));

        assertEquals(1023, col1.getRed());
        assertEquals(1023, col1.getGreen());
        assertEquals(1023, col1.getBlue());
        assertEquals(127, col1.getAlpha());
    }

    @Test
    public void convertsToAwtColor()
    {
        ScratchObjectTranslucentColor col0 = new ScratchObjectTranslucentColor(
                0, 0, 0, 255);
        Color awtCol0 = col0.toAwtColor();

        assertEquals(0, awtCol0.getRed());
        assertEquals(0, awtCol0.getGreen());
        assertEquals(0, awtCol0.getBlue());
        assertEquals(255, awtCol0.getAlpha());

        ScratchObjectTranslucentColor col1 = new ScratchObjectTranslucentColor(
                1023, 1023, 1023, 127);
        Color awtCol1 = col1.toAwtColor();

        assertEquals(255, awtCol1.getRed());
        assertEquals(255, awtCol1.getGreen());
        assertEquals(255, awtCol1.getBlue());
        assertEquals(127, awtCol1.getAlpha());
    }

    @Test
    public void writesCorrectly() throws IOException
    {
        ScratchProject project = new ScratchProject(ScratchVersion.SCRATCH14);

        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ScratchOutputStream sout = new ScratchOutputStream(bout);
        ScratchReferenceTable ref = new ScratchReferenceTable();

        new ScratchObjectTranslucentColor(0, 0, 0, 0).writeTo(sout, ref,
                project);
        new ScratchObjectTranslucentColor(500, 600, 700, 127).writeTo(sout, ref,
                project);
        new ScratchObjectTranslucentColor(1023, 1023, 1023, 255).writeTo(sout,
                ref, project);

        assertArrayEquals(new byte[] {
                // (0, 0, 0, 0)
                31, //
                0x00, 0x00, 0x00, 0x00, 0x00,
                // (500, 600, 700, 127)
                31, //
                0x1F, 0x49, 0x62, (byte) 0xBC, 0x7F,
                // (1023, 1023, 1023, 255)
                31, //
                0x3F, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
                // end
        }, bout.toByteArray());
    }

    @Test
    public void readsCorrectly() throws IOException
    {
        ScratchProject project = new ScratchProject(ScratchVersion.SCRATCH14);

        ByteArrayInputStream bin = new ByteArrayInputStream(new byte[] {
                // (0, 0, 0, 0)
                0x00, 0x00, 0x00, 0x00, 0x00,
                // (500, 600, 700, 127)
                0x1F, 0x49, 0x62, (byte) 0xBC, 0x7F,
                // (1023, 1023, 1023, 255)
                0x3F, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
                // end
        });
        ScratchInputStream sin = new ScratchInputStream(bin);

        ScratchObjectTranslucentColor col0 = new ScratchObjectTranslucentColor();
        col0.readFrom(31, sin, project);
        ScratchObjectTranslucentColor col1 = new ScratchObjectTranslucentColor();
        col1.readFrom(31, sin, project);
        ScratchObjectTranslucentColor col2 = new ScratchObjectTranslucentColor();
        col2.readFrom(31, sin, project);

        assertEquals(0, col0.getRed());
        assertEquals(0, col0.getGreen());
        assertEquals(0, col0.getBlue());
        assertEquals(0, col0.getAlpha());

        assertEquals(500, col1.getRed());
        assertEquals(600, col1.getGreen());
        assertEquals(700, col1.getBlue());
        assertEquals(127, col1.getAlpha());

        assertEquals(1023, col2.getRed());
        assertEquals(1023, col2.getGreen());
        assertEquals(1023, col2.getBlue());
        assertEquals(255, col2.getAlpha());
    }
}

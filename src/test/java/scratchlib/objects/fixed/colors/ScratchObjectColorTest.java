package scratchlib.objects.fixed.colors;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import scratchlib.objects.ScratchReferenceTable;
import scratchlib.project.ScratchProject;
import scratchlib.project.ScratchVersion;
import scratchlib.reader.ScratchInputStream;
import scratchlib.writer.ScratchOutputStream;

import static org.junit.jupiter.api.Assertions.*;


public class ScratchObjectColorTest
{
    @Test
    public void convertsFromAwtColor()
    {
        ScratchObjectColor col0 = new ScratchObjectColor(new Color(0, 0, 0));

        assertEquals(0, col0.getRed());
        assertEquals(0, col0.getGreen());
        assertEquals(0, col0.getBlue());

        ScratchObjectColor col1 = new ScratchObjectColor(
                new Color(255, 255, 255));

        assertEquals(1023, col1.getRed());
        assertEquals(1023, col1.getGreen());
        assertEquals(1023, col1.getBlue());
    }

    @Test
    public void convertsToAwtColor()
    {
        ScratchObjectColor col0 = new ScratchObjectColor(0, 0, 0);
        Color awtCol0 = col0.toAwtColor();

        assertEquals(0, awtCol0.getRed());
        assertEquals(0, awtCol0.getGreen());
        assertEquals(0, awtCol0.getBlue());
        assertEquals(255, awtCol0.getAlpha());

        ScratchObjectColor col1 = new ScratchObjectColor(1023, 1023, 1023);
        Color awtCol1 = col1.toAwtColor();

        assertEquals(255, awtCol1.getRed());
        assertEquals(255, awtCol1.getGreen());
        assertEquals(255, awtCol1.getBlue());
        assertEquals(255, awtCol1.getAlpha());
    }

    @Test
    public void writesCorrectly() throws IOException
    {
        ScratchProject project = new ScratchProject(ScratchVersion.SCRATCH14);

        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ScratchOutputStream sout = new ScratchOutputStream(bout);
        ScratchReferenceTable ref = new ScratchReferenceTable();

        new ScratchObjectColor(0, 0, 0).writeTo(sout, ref, project);
        new ScratchObjectColor(500, 600, 700).writeTo(sout, ref, project);
        new ScratchObjectColor(1023, 1023, 1023).writeTo(sout, ref, project);

        assertArrayEquals(new byte[] {
                // (0, 0, 0)
                30, //
                0x00, 0x00, 0x00, 0x00,
                // (500, 600, 700)
                30, //
                0x1F, 0x49, 0x62, (byte) 0xBC,
                // (1023, 1023, 1023)
                30, //
                0x3F, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
                // end
        }, bout.toByteArray());
    }

    @Test
    public void readsCorrectly() throws IOException
    {
        ScratchProject project = new ScratchProject(ScratchVersion.SCRATCH14);

        ByteArrayInputStream bin = new ByteArrayInputStream(new byte[] {
                // (0, 0, 0)
                0x00, 0x00, 0x00, 0x00,
                // (500, 600, 700)
                0x1F, 0x49, 0x62, (byte) 0xBC,
                // (1023, 1023, 1023)
                0x3F, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
                // end
        });
        ScratchInputStream sin = new ScratchInputStream(bin);

        ScratchObjectColor col0 = new ScratchObjectColor();
        col0.readFrom(30, sin, project);
        ScratchObjectColor col1 = new ScratchObjectColor();
        col1.readFrom(30, sin, project);
        ScratchObjectColor col2 = new ScratchObjectColor();
        col2.readFrom(30, sin, project);

        assertEquals(0, col0.getRed());
        assertEquals(0, col0.getGreen());
        assertEquals(0, col0.getBlue());

        assertEquals(500, col1.getRed());
        assertEquals(600, col1.getGreen());
        assertEquals(700, col1.getBlue());

        assertEquals(1023, col2.getRed());
        assertEquals(1023, col2.getGreen());
        assertEquals(1023, col2.getBlue());
    }
}

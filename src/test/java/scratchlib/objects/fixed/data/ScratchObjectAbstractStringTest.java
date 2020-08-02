package scratchlib.objects.fixed.data;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;
import scratchlib.objects.ScratchReferenceTable;
import scratchlib.project.ScratchProject;
import scratchlib.project.ScratchVersion;
import scratchlib.reader.ScratchInputStream;
import scratchlib.writer.ScratchOutputStream;

import static org.junit.jupiter.api.Assertions.*;


public class ScratchObjectAbstractStringTest
{
    @Test
    public void writesCorrectly() throws IOException
    {
        ScratchProject project = new ScratchProject(ScratchVersion.SCRATCH14);

        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ScratchOutputStream sout = new ScratchOutputStream(bout);
        ScratchReferenceTable ref = new ScratchReferenceTable();

        new ScratchObjectAbstractString(42, StandardCharsets.US_ASCII, "foo") {
        }.writeTo(sout, ref, project);
        new ScratchObjectAbstractString(42, StandardCharsets.UTF_8, "hêllo") {
        }.writeTo(sout, ref, project);
        new ScratchObjectAbstractString(42, StandardCharsets.US_ASCII, "hê") {
        }.writeTo(sout, ref, project);

        assertArrayEquals(new byte[] {
                // ascii - foo
                42, //
                0, 0, 0, 3, //
                'f', 'o', 'o',
                // utf-8 - hêllo
                42, //
                0, 0, 0, 6, //
                'h', (byte) 0xC3, (byte) 0xAA, 'l', 'l', 'o',
                // ascii - hê
                42, //
                0, 0, 0, 2, //
                'h', '?',
                // end
        }, bout.toByteArray());
    }

    @Test
    public void readsCorrectly() throws IOException
    {
        ScratchProject project = new ScratchProject(ScratchVersion.SCRATCH14);

        ByteArrayInputStream bin = new ByteArrayInputStream(new byte[] {
                // ascii - foo
                0, 0, 0, 3, //
                'f', 'o', 'o',
                // utf-8 - hêllo
                0, 0, 0, 6, //
                'h', (byte) 0xC3, (byte) 0xAA, 'l', 'l', 'o',
                // ascii - hê
                0, 0, 0, 2, //
                'h', '?',
                // end
        });
        ScratchInputStream sin = new ScratchInputStream(bin);

        ScratchObjectAbstractString str0 = new ScratchObjectAbstractString(42, StandardCharsets.US_ASCII) {
        };
        str0.readFrom(42, sin, project);

        ScratchObjectAbstractString str1 = new ScratchObjectAbstractString(42, StandardCharsets.UTF_8) {
        };
        str1.readFrom(42, sin, project);

        ScratchObjectAbstractString str2 = new ScratchObjectAbstractString(42, StandardCharsets.US_ASCII) {
        };
        str2.readFrom(42, sin, project);

        assertEquals("foo", str0.getValue());
        assertEquals("hêllo", str1.getValue());
        assertEquals("h?", str2.getValue());
    }
}

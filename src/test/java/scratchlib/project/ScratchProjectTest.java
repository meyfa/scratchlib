package scratchlib.project;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import org.junit.Test;

import scratchlib.objects.ScratchObjectStore;
import scratchlib.objects.fixed.data.ScratchObjectAbstractString;
import scratchlib.objects.fixed.data.ScratchObjectUtf8;
import scratchlib.objects.user.morphs.ScratchObjectStageMorph;
import scratchlib.writer.ScratchOutputStream;


public class ScratchProjectTest
{
    @Test
    public void returnsVersion()
    {
        ScratchProject obj = new ScratchProject(ScratchVersion.SCRATCH14);
        assertSame(ScratchVersion.SCRATCH14, obj.getVersion());
    }

    @Test
    public void hasDefaultInfo()
    {
        ScratchProject obj = new ScratchProject(ScratchVersion.SCRATCH14);

        assertThat(obj.getInfoProperty(ScratchProject.INFO_OS_VERSION),
                instanceOf(ScratchObjectAbstractString.class));
        assertThat(obj.getInfoProperty(ScratchProject.INFO_PLATFORM),
                instanceOf(ScratchObjectAbstractString.class));
        assertThat(obj.getInfoProperty(ScratchProject.INFO_LANGUAGE),
                instanceOf(ScratchObjectAbstractString.class));
        assertThat(obj.getInfoProperty(ScratchProject.INFO_HISTORY),
                instanceOf(ScratchObjectAbstractString.class));
        assertThat(obj.getInfoProperty(ScratchProject.INFO_SCRATCH_VERSION),
                instanceOf(ScratchObjectAbstractString.class));
        assertThat(obj.getInfoProperty(ScratchProject.INFO_COMMENT),
                instanceOf(ScratchObjectAbstractString.class));
        assertThat(obj.getInfoProperty(ScratchProject.INFO_AUTHOR),
                instanceOf(ScratchObjectAbstractString.class));
    }

    @Test
    public void getsStage()
    {
        ScratchProject obj = new ScratchProject(ScratchVersion.SCRATCH14);

        assertThat(obj.getStage(), instanceOf(ScratchObjectStageMorph.class));
        assertSame(obj.getStage(), obj.getStageSection().get());
    }

    @Test
    public void returnsNullForUnknownInfoKey()
    {
        ScratchProject obj = new ScratchProject(ScratchVersion.SCRATCH14);

        assertNull(obj.getInfoProperty("unknownproperty"));
    }

    @Test
    public void setsInfoProperty()
    {
        ScratchProject obj = new ScratchProject(ScratchVersion.SCRATCH14);

        obj.setInfoProperty(ScratchProject.INFO_COMMENT,
                new ScratchObjectUtf8("unit-test-comment"));
        assertEquals("unit-test-comment",
                ((ScratchObjectUtf8) obj
                        .getInfoProperty(ScratchProject.INFO_COMMENT))
                                .getValue());

        obj.setInfoProperty("unknownproperty",
                new ScratchObjectUtf8("unit-test-comment"));
        assertEquals("unit-test-comment",
                ((ScratchObjectUtf8) obj.getInfoProperty("unknownproperty"))
                        .getValue());
    }

    @Test
    public void writesCorrectFormat() throws IOException
    {
        ScratchProject obj = new ScratchProject(ScratchVersion.SCRATCH14);

        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ScratchOutputStream sout = new ScratchOutputStream(bout);

        obj.writeTo(sout);

        byte[] actual = bout.toByteArray();

        // starts with header
        byte[] header = ScratchVersion.SCRATCH14.getHeader()
                .getBytes(StandardCharsets.UTF_8);
        assertArrayRange(header, actual, 0);

        // follows with info section length
        byte[] info = getSectionBytes(obj, obj.getInfoSection());
        byte[] infoLength = ByteBuffer.allocate(4).putInt(info.length).array();
        assertArrayRange(infoLength, actual, header.length);

        // follows with info section bytes
        assertArrayRange(info, actual, header.length + infoLength.length);

        // ends with stage section bytes
        byte[] stage = getSectionBytes(obj, obj.getStageSection());
        assertArrayRange(stage, actual,
                header.length + infoLength.length + info.length);

        // nothing else follows
        assertEquals(bout.size(),
                header.length + infoLength.length + info.length + stage.length);
    }

    private static void assertArrayRange(byte[] exp, byte[] act, int start)
    {
        for (int i = 0; i < exp.length; ++i) {
            if (act[start + i] != exp[i]) {
                fail("byte at index " + i + " not equal to expected");
            }
        }
    }

    private byte[] getSectionBytes(ScratchProject project,
            ScratchObjectStore section) throws IOException
    {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ScratchOutputStream out = new ScratchOutputStream(bout);

        section.writeTo(out, project);

        return bout.toByteArray();
    }
}

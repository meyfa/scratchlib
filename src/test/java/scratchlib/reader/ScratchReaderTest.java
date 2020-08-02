package scratchlib.reader;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import scratchlib.objects.ScratchObject;
import scratchlib.objects.fixed.data.ScratchObjectAbstractString;
import scratchlib.project.ScratchProject;
import scratchlib.project.ScratchVersion;

import static org.junit.jupiter.api.Assertions.*;


public class ScratchReaderTest
{
    @Test
    public void readsFile() throws IOException
    {
        ScratchReader obj = new ScratchReader();

        final File file = new File("./src/test/resources/empty.sb");
        ScratchProject proj = obj.read(file);

        assertNotNull(proj);
        assertEquals(ScratchVersion.SCRATCH14, proj.getVersion());

        ScratchObject c = proj.getInfoProperty(ScratchProject.INFO_COMMENT);
        assertEquals("test project", ((ScratchObjectAbstractString) c).getValue());
    }

    @Test
    public void readsInputStream() throws IOException
    {
        ScratchReader obj = new ScratchReader();

        ScratchProject proj = obj.read(getClass().getClassLoader().getResourceAsStream("empty.sb"));

        assertNotNull(proj);
        assertEquals(ScratchVersion.SCRATCH14, proj.getVersion());

        ScratchObject c = proj.getInfoProperty(ScratchProject.INFO_COMMENT);
        assertEquals("test project", ((ScratchObjectAbstractString) c).getValue());
    }
}

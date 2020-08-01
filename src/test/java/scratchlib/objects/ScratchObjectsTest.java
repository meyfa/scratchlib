package scratchlib.objects;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import scratchlib.objects.fixed.data.ScratchObjectString;
import scratchlib.objects.inline.ScratchObjectBoolean;
import scratchlib.project.ScratchProject;
import scratchlib.project.ScratchVersion;
import scratchlib.reader.ScratchInputStream;

import static org.junit.jupiter.api.Assertions.*;


public class ScratchObjectsTest
{
    @Test
    public void looksUpConstructor()
    {
        assertSame(ScratchObjectBoolean.NIL,
                ScratchObjects.lookupConstructor(1).get());
        assertSame(ScratchObjectBoolean.FALSE,
                ScratchObjects.lookupConstructor(3).get());

        assertTrue(ScratchObjects.lookupConstructor(9).get() instanceof ScratchObjectString);
    }

    @Test
    public void readsReferences() throws IOException
    {
        ScratchProject project = new ScratchProject(ScratchVersion.SCRATCH14);

        ByteArrayInputStream bin = new ByteArrayInputStream(
                new byte[] { 99, 0, 1, 37 });

        ScratchOptionalField result = ScratchObjects
                .read(new ScratchInputStream(bin), project);

        assertFalse(result.isResolved());
        assertEquals(293, result.getReferenceID());
    }

    @Test
    public void readsObjects() throws IOException
    {
        ScratchProject project = new ScratchProject(ScratchVersion.SCRATCH14);

        ByteArrayInputStream bin = new ByteArrayInputStream(
                new byte[] { 9, 0, 0, 0, 0 });

        ScratchOptionalField result = ScratchObjects
                .read(new ScratchInputStream(bin), project);

        assertTrue(result.isResolved());
        assertTrue(result.get() instanceof ScratchObjectString);
    }

    @Test
    public void throwsForUnknownClassID()
    {
        ScratchProject project = new ScratchProject(ScratchVersion.SCRATCH14);

        ByteArrayInputStream bin = new ByteArrayInputStream(
                new byte[] { (byte) 255, 0, 0, 0, 0 });

        assertThrows(IOException.class, () -> {
            ScratchObjects.read(new ScratchInputStream(bin), project);
        });
    }
}

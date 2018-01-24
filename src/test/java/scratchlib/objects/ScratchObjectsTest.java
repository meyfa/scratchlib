package scratchlib.objects;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.Test;

import scratchlib.objects.fixed.data.ScratchObjectString;
import scratchlib.objects.inline.ScratchObjectBoolean;
import scratchlib.project.ScratchProject;
import scratchlib.project.ScratchVersion;
import scratchlib.reader.ScratchInputStream;


public class ScratchObjectsTest
{
    @Test
    public void looksUpConstructor()
    {
        assertSame(ScratchObjectBoolean.NIL,
                ScratchObjects.lookupConstructor(1).get());
        assertSame(ScratchObjectBoolean.FALSE,
                ScratchObjects.lookupConstructor(3).get());

        assertThat(ScratchObjects.lookupConstructor(9).get(),
                instanceOf(ScratchObjectString.class));
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
        assertThat(result.get(), instanceOf(ScratchObjectString.class));
    }

    @Test(expected = IOException.class)
    public void throwsForUnknownClassID() throws IOException
    {
        ScratchProject project = new ScratchProject(ScratchVersion.SCRATCH14);

        ByteArrayInputStream bin = new ByteArrayInputStream(
                new byte[] { (byte) 255, 0, 0, 0, 0 });

        ScratchObjects.read(new ScratchInputStream(bin), project);
    }
}

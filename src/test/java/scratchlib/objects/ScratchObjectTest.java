package scratchlib.objects;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import scratchlib.project.ScratchProject;
import scratchlib.project.ScratchVersion;
import scratchlib.reader.ScratchInputStream;
import scratchlib.writer.ScratchOutputStream;

import static org.junit.jupiter.api.Assertions.*;


public class ScratchObjectTest
{
    private static class NonReferenceType extends ScratchObject
    {
        public NonReferenceType(int classID)
        {
            super(classID);
        }
    }

    private static class ReferenceType extends ScratchObject
            implements IScratchReferenceType
    {
        public ReferenceType(int classID)
        {
            super(classID);
        }
    }

    @Test
    public void returnsClassID()
    {
        assertEquals(42, new NonReferenceType(42).getClassID());
    }

    @Test
    public void createsSelfReferenceWhenReferenceType()
    {
        ScratchProject project = new ScratchProject(ScratchVersion.SCRATCH14);

        ScratchReferenceTable table = new ScratchReferenceTable();
        assertEquals(0, table.size());

        assertFalse(new NonReferenceType(42).createReferences(table, project));
        assertEquals(0, table.size());

        assertTrue(new ReferenceType(42).createReferences(table, project));
        assertEquals(1, table.size());
    }

    @Test
    public void writesNothingButClassID() throws IOException
    {
        ScratchProject project = new ScratchProject(ScratchVersion.SCRATCH14);
        ScratchReferenceTable ref = new ScratchReferenceTable();

        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ScratchOutputStream sout = new ScratchOutputStream(bout);

        new ReferenceType(42).writeTo(sout, ref, project);

        assertArrayEquals(new byte[] { 42 }, bout.toByteArray());
    }

    @Test
    public void readThrowsForWrongClassID()
    {
        ScratchProject project = new ScratchProject(ScratchVersion.SCRATCH14);
        ScratchInputStream in = new ScratchInputStream(new ByteArrayInputStream(new byte[0]));

        assertThrows(IOException.class, () -> {
            new ReferenceType(42).readFrom(37, in, project);
        });
    }
}

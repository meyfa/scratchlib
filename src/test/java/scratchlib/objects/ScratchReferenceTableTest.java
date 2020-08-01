package scratchlib.objects;

import org.junit.jupiter.api.Test;
import scratchlib.objects.fixed.collections.ScratchObjectArray;

import static org.junit.jupiter.api.Assertions.*;


public class ScratchReferenceTableTest
{
    @Test
    public void returnsNegativeOneWhenObjectNotFound()
    {
        ScratchReferenceTable obj = new ScratchReferenceTable();

        assertEquals(-1, obj.lookup(new ScratchObjectArray()));
    }

    @Test
    public void returnsNullWhenIDNotFound()
    {
        ScratchReferenceTable obj = new ScratchReferenceTable();

        assertNull(obj.lookup(37));
    }

    @Test
    public void insertsOnce()
    {
        ScratchReferenceTable obj = new ScratchReferenceTable();

        assertEquals(0, obj.size());

        ScratchObjectArray arr = new ScratchObjectArray();

        assertTrue(obj.insert(arr));
        assertEquals(1, obj.size());
        assertSame(arr, obj.lookup(1));

        assertFalse(obj.insert(arr));
        assertEquals(1, obj.size());
    }
}

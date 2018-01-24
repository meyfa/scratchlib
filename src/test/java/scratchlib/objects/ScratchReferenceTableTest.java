package scratchlib.objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import scratchlib.objects.fixed.collections.ScratchObjectArray;


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

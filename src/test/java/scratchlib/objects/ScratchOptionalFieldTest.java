package scratchlib.objects;

import org.junit.jupiter.api.Test;
import scratchlib.objects.fixed.collections.ScratchObjectArray;
import scratchlib.objects.inline.ScratchObjectBoolean;

import static org.junit.jupiter.api.Assertions.*;


public class ScratchOptionalFieldTest
{
    @Test
    public void throwsForInvalidReferenceID()
    {
        assertThrows(IllegalArgumentException.class, () -> new ScratchOptionalField(0));
    }

    @Test
    public void throwsForNullValue()
    {
        assertThrows(NullPointerException.class, () -> new ScratchOptionalField(null));
    }

    @Test
    public void getThrowsWhenStillUnresolved()
    {
        ScratchOptionalField obj = new ScratchOptionalField(1);

        assertFalse(obj.isResolved());
        assertThrows(IllegalStateException.class, obj::get);
    }

    @Test
    public void getReturnsValueWhenResolved()
    {
        ScratchObjectArray val = new ScratchObjectArray();

        ScratchOptionalField obj = new ScratchOptionalField(val);
        assertTrue(obj.isResolved());
        assertSame(val, obj.get());

        ScratchOptionalField obj2 = new ScratchOptionalField(1);
        ScratchReferenceTable ref = new ScratchReferenceTable();
        ref.insert(val);
        obj2.resolve(ref);
        assertTrue(obj2.isResolved());
        assertSame(val, obj2.get());
    }

    @Test
    public void calculatesSymmetricHashCode()
    {
        ScratchOptionalField obj0 = new ScratchOptionalField(1);
        ScratchOptionalField obj1 = new ScratchOptionalField(1);

        assertEquals(obj1.hashCode(), obj0.hashCode());

        ScratchObjectArray val = new ScratchObjectArray();
        ScratchOptionalField obj2 = new ScratchOptionalField(val);
        ScratchOptionalField obj3 = new ScratchOptionalField(val);

        assertEquals(obj3.hashCode(), obj2.hashCode());
    }

    @Test
    public void performsValidEqualityTest()
    {
        ScratchOptionalField obj0 = new ScratchOptionalField(1);
        ScratchOptionalField obj1 = new ScratchOptionalField(1);

        assertEquals(obj0, obj0, "obj0 not considered equal to itself");

        assertEquals(obj1, obj0, "obj1 not considered equal to obj0");
        assertEquals(obj0, obj1, "obj0 not considered equal to obj1");

        assertNotEquals(new Object(), obj0, "another type considered equal to obj0");
        assertNotEquals(obj0, null, "null considered equal to obj0");

        ScratchObjectArray val = new ScratchObjectArray();
        ScratchOptionalField obj2 = new ScratchOptionalField(val);
        ScratchOptionalField obj3 = new ScratchOptionalField(val);

        assertNotEquals(obj2, obj0, "obj2 considered equal to obj0");
        assertNotEquals(obj0, obj2, "obj0 considered equal to obj2");

        assertEquals(obj2, obj3, "obj2 not considered equal to itself");

        assertEquals(obj3, obj2, "obj3 not considered equal to obj2");
        assertEquals(obj2, obj3, "obj2 not considered equal to obj3");

        ScratchOptionalField obj4 = new ScratchOptionalField(ScratchObjectBoolean.TRUE);
        assertNotEquals(obj4, obj2, "obj4 considered equal to obj2");
    }
}

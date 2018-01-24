package scratchlib.objects;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import scratchlib.objects.fixed.collections.ScratchObjectArray;
import scratchlib.objects.inline.ScratchObjectBoolean;


public class ScratchOptionalFieldTest
{
    @Test(expected = IllegalArgumentException.class)
    public void throwsForInvalidReferenceID()
    {
        new ScratchOptionalField(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsForNullValue()
    {
        new ScratchOptionalField(null);
    }

    @Test(expected = IllegalStateException.class)
    public void getThrowsWhenStillUnresolved()
    {
        ScratchOptionalField obj = new ScratchOptionalField(1);

        assertFalse(obj.isResolved());
        obj.get();
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

        assertTrue(obj0.hashCode() == obj1.hashCode());

        ScratchObjectArray val = new ScratchObjectArray();
        ScratchOptionalField obj2 = new ScratchOptionalField(val);
        ScratchOptionalField obj3 = new ScratchOptionalField(val);

        assertTrue(obj2.hashCode() == obj3.hashCode());
    }

    @Test
    public void performsValidEqualityTest()
    {
        ScratchOptionalField obj0 = new ScratchOptionalField(1);
        ScratchOptionalField obj1 = new ScratchOptionalField(1);

        assertTrue("obj0 not considered equal to itself", obj0.equals(obj0));

        assertTrue("obj1 not considered equal to obj0", obj0.equals(obj1));
        assertTrue("obj0 not considered equal to obj1", obj1.equals(obj0));

        assertFalse("another type considered equal to obj0",
                obj0.equals(new Object()));
        assertFalse("null considered equal to obj0", obj0.equals(null));

        ScratchObjectArray val = new ScratchObjectArray();
        ScratchOptionalField obj2 = new ScratchOptionalField(val);
        ScratchOptionalField obj3 = new ScratchOptionalField(val);

        assertFalse("obj2 considered equal to obj0", obj0.equals(obj2));
        assertFalse("obj0 considered equal to obj2", obj2.equals(obj0));

        assertTrue("obj2 not considered equal to itself", obj3.equals(obj2));

        assertTrue("obj3 not considered equal to obj2", obj2.equals(obj3));
        assertTrue("obj2 not considered equal to obj3", obj3.equals(obj2));

        ScratchOptionalField obj4 = new ScratchOptionalField(
                ScratchObjectBoolean.TRUE);
        assertFalse("obj4 considered equal to obj2", obj2.equals(obj4));
    }
}

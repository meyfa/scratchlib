package scratchlib.objects.user;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;

import org.junit.jupiter.api.Test;
import scratchlib.objects.ScratchObject;
import scratchlib.objects.ScratchReferenceTable;
import scratchlib.objects.fixed.collections.ScratchObjectArray;
import scratchlib.objects.inline.ScratchObjectBoolean;
import scratchlib.objects.user.ScratchUserClassObject.ClassVersion;
import scratchlib.project.ScratchProject;
import scratchlib.project.ScratchVersion;
import scratchlib.reader.ScratchInputStream;
import scratchlib.writer.ScratchOutputStream;

import static org.junit.jupiter.api.Assertions.*;


public class ScratchUserClassObjectTest
{
    @Test
    public void returnsCorrectClassVersion()
    {
        ScratchUserClassObject obj = new ScratchUserClassObject(42,
                new ClassVersion(3, 5)) {
        };

        assertEquals(3, obj.getClassVersion(ScratchVersion.SCRATCH14));
        assertEquals(5, obj.getClassVersion(ScratchVersion.BYOB311));
    }

    @Test
    public void getsFields()
    {
        ScratchUserClassObject obj = new ScratchUserClassObject(42,
                new ClassVersion(3, 5)) {
        };
        obj.specifyField("testfield", ScratchObjectBoolean.TRUE);
        obj.specifyField("testfield2", ScratchObjectBoolean.FALSE);

        assertSame(ScratchObjectBoolean.TRUE, obj.getField("testfield"));
        assertSame(ScratchObjectBoolean.FALSE, obj.getField("testfield2"));
    }

    @Test
    public void setsFields()
    {
        ScratchUserClassObject obj = new ScratchUserClassObject(42,
                new ClassVersion(3, 5)) {
        };
        obj.specifyField("testfield", ScratchObjectBoolean.TRUE);
        obj.specifyField("testfield2", ScratchObjectBoolean.FALSE);

        obj.setField("testfield", ScratchObject.NIL);

        assertSame(ScratchObject.NIL, obj.getField("testfield"));
        assertSame(ScratchObjectBoolean.FALSE, obj.getField("testfield2"));
    }

    @Test
    public void throwsForIllegalFieldSpecifications()
    {
        ScratchUserClassObject obj = new ScratchUserClassObject(42,
                new ClassVersion(3, 5)) {
        };

        // null name
        try {
            obj.specifyField(null, ScratchObject.NIL);
            fail("not thrown");
        } catch (IllegalArgumentException e) {
        }

        // duplicate name
        obj.specifyField("testfield", ScratchObject.NIL);
        try {
            obj.specifyField("testfield", ScratchObject.NIL);
            fail("not thrown");
        } catch (IllegalArgumentException e) {
        }

        // null value
        try {
            obj.specifyField("otherfield", null);
            fail("not thrown");
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void throwsForTooManyFieldSpecifications()
    {
        ScratchUserClassObject obj = new ScratchUserClassObject(42,
                new ClassVersion(3, 5)) {
        };

        for (int i = 0; i < 255; ++i) {
            obj.specifyField("testfield" + i, ScratchObject.NIL);
        }
        try {
            obj.specifyField("testfield255", ScratchObject.NIL);
            fail("not thrown");
        } catch (UnsupportedOperationException e) {
        }
    }

    @Test
    public void returnsFieldNames()
    {
        ScratchUserClassObject obj = new ScratchUserClassObject(42,
                new ClassVersion(3, 5)) {
        };
        obj.specifyField("testfield", ScratchObjectBoolean.TRUE);
        obj.specifyField("testfield2", ScratchObjectBoolean.FALSE);

        assertEquals(new HashSet<>(Arrays.asList("testfield", "testfield2")),
                obj.getFieldNames());
    }

    @Test
    public void createsReferences()
    {
        ScratchObject ref0 = new ScratchObjectArray();
        ScratchObject ref1 = new ScratchObjectArray();
        ScratchObject ref2 = new ScratchObjectArray();

        ScratchUserClassObject obj = new ScratchUserClassObject(42,
                new ClassVersion(3, 5)) {
        };
        obj.specifyField("foo", ref0);
        obj.specifyField("bar", ref1, ScratchVersion.SCRATCH14);
        obj.specifyField("baz", ref2, ScratchVersion.BYOB311);
        obj.specifyField("qux", ScratchObject.NIL);

        // scratch
        ScratchProject scratch = new ScratchProject(ScratchVersion.SCRATCH14);
        ScratchReferenceTable scratchRef = new ScratchReferenceTable();
        obj.createReferences(scratchRef, scratch);

        assertEquals(3, scratchRef.size());
        assertSame(obj, scratchRef.lookup(1));
        assertSame(ref0, scratchRef.lookup(2));
        assertSame(ref1, scratchRef.lookup(3));

        // byob
        ScratchProject byob = new ScratchProject(ScratchVersion.BYOB311);
        ScratchReferenceTable byobRef = new ScratchReferenceTable();
        obj.createReferences(byobRef, byob);

        assertEquals(3, byobRef.size());
        assertSame(obj, byobRef.lookup(1));
        assertSame(ref0, byobRef.lookup(2));
        assertSame(ref2, byobRef.lookup(3));
    }

    @Test
    public void writesCorrectly() throws IOException
    {
        ScratchProject project = new ScratchProject(ScratchVersion.SCRATCH14);

        ScratchUserClassObject obj = new ScratchUserClassObject(42,
                new ClassVersion(3, 5)) {
        };
        obj.specifyField("foo", ScratchObject.NIL);
        obj.specifyField("bar", ScratchObjectBoolean.TRUE,
                ScratchVersion.SCRATCH14);
        obj.specifyField("baz", ScratchObjectBoolean.FALSE,
                ScratchVersion.BYOB311);

        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ScratchReferenceTable ref = new ScratchReferenceTable();

        obj.writeTo(new ScratchOutputStream(bout), ref, project);

        assertArrayEquals(new byte[] {
                // class id
                42,
                // class version
                3,
                // length
                2,
                // field "foo"
                1,
                // field "bar"
                2,
                // end
        }, bout.toByteArray());
    }

    @Test
    public void readsCorrectly() throws IOException
    {
        ScratchProject project = new ScratchProject(ScratchVersion.SCRATCH14);

        ScratchUserClassObject obj = new ScratchUserClassObject(42,
                new ClassVersion(3, 5)) {
        };
        obj.specifyField("foo", ScratchObject.NIL);
        obj.specifyField("bar", ScratchObject.NIL, ScratchVersion.SCRATCH14);
        obj.specifyField("baz", ScratchObject.NIL, ScratchVersion.BYOB311);

        ByteArrayInputStream bin = new ByteArrayInputStream(new byte[] {
                // class version
                3,
                // length
                2,
                // field "foo"
                2,
                // field "bar"
                3,
                // end
        });
        obj.readFrom(42, new ScratchInputStream(bin), project);

        assertSame(ScratchObjectBoolean.TRUE, obj.getField("foo"));
        assertSame(ScratchObjectBoolean.FALSE, obj.getField("bar"));
    }
}

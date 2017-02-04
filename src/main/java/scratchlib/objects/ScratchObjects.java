package scratchlib.objects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

import scratchlib.objects.inline.ScratchObjectBoolean;
import scratchlib.objects.inline.ScratchObjectFloat;
import scratchlib.objects.inline.ScratchObjectLargeNegativeInteger;
import scratchlib.objects.inline.ScratchObjectLargePositiveInteger;
import scratchlib.objects.inline.ScratchObjectSmallInteger;
import scratchlib.objects.inline.ScratchObjectSmallInteger16;
import scratchlib.project.ScratchProject;
import scratchlib.reader.ScratchInputStream;


/**
 * Allows class lookup by ID.
 */
public class ScratchObjects
{
    private static final List<Supplier<ScratchObject>> cons = new ArrayList<>(
            Collections.nCopies(255, null));

    /**
     * Stores the given constructor for the given class ID.
     * 
     * @param id The class ID.
     * @param con The class constructor.
     */
    private static void storeConstructor(int id, Supplier<ScratchObject> con)
    {
        cons.set(id - 1, con);
    }

    /**
     * Finds the constructor for the given class ID.
     * 
     * @param id The class ID.
     * @return The respective class constructor.
     */
    public static Supplier<ScratchObject> lookupConstructor(int id)
    {
        return cons.get(id - 1);
    }

    /**
     * Convenience method for reading an object or reference from the given
     * input stream.
     * 
     * @param in The stream to read from.
     * @param project The project reading for.
     * @return The object that was read.
     * @throws IOException
     */
    public static ScratchOptionalField read(ScratchInputStream in,
            ScratchProject project) throws IOException
    {
        int id = in.read();

        if (id == 99) {
            return new ScratchOptionalField(in.read24bitUnsignedInt());
        }

        Supplier<ScratchObject> con = lookupConstructor(id);
        if (con == null) {
            throw new IOException("unknown class id: " + id);
        }

        ScratchObject obj = con.get();
        obj.readFrom(id, in, project);

        return new ScratchOptionalField(obj);
    }

    static {
        //@formatter:off

        // inline: constants (1, 2, 3)
        storeConstructor(ScratchObject.NIL.getClassID(), () -> ScratchObject.NIL);
        storeConstructor(ScratchObjectBoolean.TRUE.getClassID(), () -> ScratchObjectBoolean.TRUE);
        storeConstructor(ScratchObjectBoolean.FALSE.getClassID(), () -> ScratchObjectBoolean.FALSE);

        // inline: numbers (4 ... 8)
        storeConstructor(ScratchObjectSmallInteger.CLASS_ID, ScratchObjectSmallInteger::new);
        storeConstructor(ScratchObjectSmallInteger16.CLASS_ID, ScratchObjectSmallInteger16::new);
        storeConstructor(ScratchObjectLargePositiveInteger.CLASS_ID, ScratchObjectLargePositiveInteger::new);
        storeConstructor(ScratchObjectLargeNegativeInteger.CLASS_ID, ScratchObjectLargeNegativeInteger::new);
        storeConstructor(ScratchObjectFloat.CLASS_ID, ScratchObjectFloat::new);

        //@formatter:on
    }
}

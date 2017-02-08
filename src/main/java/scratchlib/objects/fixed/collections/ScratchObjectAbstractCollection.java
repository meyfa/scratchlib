package scratchlib.objects.fixed.collections;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import scratchlib.objects.IScratchReferenceType;
import scratchlib.objects.ScratchObject;
import scratchlib.objects.ScratchObjects;
import scratchlib.objects.ScratchOptionalField;
import scratchlib.objects.ScratchReferenceTable;
import scratchlib.project.ScratchProject;
import scratchlib.reader.ScratchInputStream;
import scratchlib.writer.ScratchOutputStream;


/**
 * Base class for all {@code List}-like Scratch collection classes, e.g.
 * {@link ScratchObjectArray} or {@link ScratchObjectOrderedCollection}.
 * 
 * <p>
 * Allows objects to be stored, retrieved, replaced, inserted and removed via
 * indices.
 */
public abstract class ScratchObjectAbstractCollection extends ScratchObject
        implements IScratchReferenceType
{
    private final List<ScratchOptionalField> entries = new ArrayList<>();

    /**
     * @param classID The ID of the class this object belongs to.
     */
    public ScratchObjectAbstractCollection(int classID)
    {
        super(classID);
    }

    /**
     * @return The number of elements in this collection.
     */
    public int size()
    {
        return entries.size();
    }

    /**
     * Retrieves an element from this collection.
     * 
     * @param index The object's index.
     * @return The object at the given index.
     */
    public ScratchObject get(int index)
    {
        return entries.get(index).get();
    }

    /**
     * Replaces an element in this collection with a different object.
     * 
     * @param index The object's index.
     * @param object The object to replace the current one with.
     */
    public void set(int index, ScratchObject object)
    {
        if (object == null) {
            throw new IllegalArgumentException("object may not be null");
        }
        entries.set(index, new ScratchOptionalField(object));
    }

    /**
     * Adds an element to this collection.
     * 
     * @param object The element to add.
     * @throws IllegalArgumentException If object is null.
     */
    public void add(ScratchObject object)
    {
        if (object == null) {
            throw new IllegalArgumentException("object may not be null");
        }
        entries.add(new ScratchOptionalField(object));
    }

    /**
     * Inserts an element into this collection, shifting the current and all
     * subsequent elements to the right (adding one to their indices).
     * 
     * @param index The position at which to insert.
     * @param object The element to add.
     * @throws IllegalArgumentException If object is null.
     */
    public void add(int index, ScratchObject object)
    {
        if (object == null) {
            throw new IllegalArgumentException("object may not be null");
        }
        entries.add(index, new ScratchOptionalField(object));
    }

    /**
     * Removes an element from this collection.
     * 
     * @param index The index of the element to remove.
     */
    public void remove(int index)
    {
        entries.remove(index);
    }

    @Override
    public boolean createReferences(ScratchReferenceTable ref,
            ScratchProject project)
    {
        if (!super.createReferences(ref, project)) {
            return false;
        }

        for (ScratchOptionalField entry : entries) {
            entry.get().createReferences(ref, project);
        }

        return true;
    }

    @Override
    public void resolveReferences(ScratchReferenceTable ref)
    {
        super.resolveReferences(ref);

        for (ScratchOptionalField entry : entries) {
            entry.resolve(ref);
        }
    }

    @Override
    public void writeTo(ScratchOutputStream out, ScratchReferenceTable ref,
            ScratchProject project) throws IOException
    {
        super.writeTo(out, ref, project);

        out.write32bitUnsignedInt(entries.size());
        for (ScratchOptionalField entry : entries) {
            ref.writeField(entry.get(), out, project);
        }
    }

    @Override
    public void readFrom(int id, ScratchInputStream in, ScratchProject project)
            throws IOException
    {
        super.readFrom(id, in, project);

        int size = in.read32bitUnsignedInt();
        for (int i = 0; i < size; ++i) {
            entries.add(ScratchObjects.read(in, project));
        }
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + getClassID();
        result = prime * result + ((entries == null) ? 0 : entries.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ScratchObjectAbstractCollection other = (ScratchObjectAbstractCollection) obj;
        return Objects.equals(entries, other.entries);
    }
}

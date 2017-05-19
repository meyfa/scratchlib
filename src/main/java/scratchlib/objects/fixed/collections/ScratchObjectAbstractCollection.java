package scratchlib.objects.fixed.collections;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        implements IScratchReferenceType, Iterable<ScratchObject>
{
    private final List<ScratchOptionalField> entries;

    /**
     * @param classID The ID of the class this object belongs to.
     */
    public ScratchObjectAbstractCollection(int classID)
    {
        super(classID);

        this.entries = new ArrayList<>();
    }

    /**
     * @param classID The ID of the class this object belongs to.
     * @param entries The entries to initialize this collection with.
     */
    public ScratchObjectAbstractCollection(int classID,
            Collection<? extends ScratchObject> entries)
    {
        super(classID);

        this.entries = entries.stream().map(ScratchOptionalField::new)
                .collect(Collectors.toCollection(ArrayList::new));
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
    public Iterator<ScratchObject> iterator()
    {
        return entries.stream().map(ScratchOptionalField::get).iterator();
    }

    /**
     * @return A sequential stream of all objects in this collection.
     */
    public Stream<ScratchObject> stream()
    {
        return entries.stream().map(ScratchOptionalField::get);
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
}

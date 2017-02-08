package scratchlib.objects.fixed.collections;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import scratchlib.objects.IScratchReferenceType;
import scratchlib.objects.ScratchObject;
import scratchlib.objects.ScratchObjects;
import scratchlib.objects.ScratchOptionalField;
import scratchlib.objects.ScratchReferenceTable;
import scratchlib.project.ScratchProject;
import scratchlib.reader.ScratchInputStream;
import scratchlib.writer.ScratchOutputStream;


/**
 * Base class for all {@code Map}-like Scratch collection classes, that is,
 * {@link ScratchObjectDictionary} and {@link ScratchObjectIdentityDictionary}.
 * 
 * <p>
 * Allows elements (values) to be associated with other elements (keys), and
 * later retrieved, updated or removed through the same keys again.
 */
public abstract class ScratchObjectAbstractDictionary extends ScratchObject
        implements IScratchReferenceType
{
    private final LinkedHashMap<ScratchOptionalField, ScratchOptionalField> entries = new LinkedHashMap<>();

    /**
     * @param classID The ID of the class this object belongs to.
     */
    public ScratchObjectAbstractDictionary(int classID)
    {
        super(classID);
    }

    /**
     * Finds the object associated with the given key.
     * 
     * @param key The key whose associated value shall be found.
     * @return The associated value.
     */
    public ScratchObject get(ScratchObject key)
    {
        // TODO implement without instantiation
        return entries.get(new ScratchOptionalField(key)).get();
    }

    /**
     * Associates the given key with the given value. Behaves exactly like the
     * equivalent Java Map operation.
     * 
     * @param key The entry's key.
     * @param value The entry's value.
     */
    public void put(ScratchObject key, ScratchObject value)
    {
        if (key == null) {
            throw new IllegalArgumentException("key may not be null");
        }
        if (value == null) {
            throw new IllegalArgumentException("value may not be null");
        }

        entries.put(new ScratchOptionalField(key),
                new ScratchOptionalField(value));
    }

    /**
     * Removes the entry with the given key from this dictionary.
     * 
     * @param key The entry's key.
     */
    public void remove(ScratchObject key)
    {
        // TODO implement without instantiation
        entries.remove(new ScratchOptionalField(key));
    }

    /**
     * @return A set of all keys in this dictionary.
     */
    public Set<ScratchObject> keySet()
    {
        return entries.keySet().stream().map(ScratchOptionalField::get)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    /**
     * @return A collection of all values in this dictionary.
     */
    public Collection<ScratchObject> values()
    {
        return entries.values().stream().map(ScratchOptionalField::get)
                .collect(Collectors.toList());
    }

    /**
     * @return A set of all mappings in this dictionary.
     */
    public Set<Entry<ScratchObject, ScratchObject>> entrySet()
    {
        return entries.entrySet().stream()
                .map(e -> new AbstractMap.SimpleImmutableEntry<>(
                        e.getKey().get(), e.getValue().get()))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public void createReferences(ScratchReferenceTable ref,
            ScratchProject project)
    {
        super.createReferences(ref, project);

        for (Entry<ScratchOptionalField, ScratchOptionalField> entry : entries
                .entrySet()) {
            entry.getKey().get().createReferences(ref, project);
            entry.getValue().get().createReferences(ref, project);
        }
    }

    @Override
    public void resolveReferences(ScratchReferenceTable ref)
    {
        super.resolveReferences(ref);

        for (Entry<ScratchOptionalField, ScratchOptionalField> entry : entries
                .entrySet()) {
            entry.getKey().resolve(ref);
            entry.getValue().resolve(ref);
        }
    }

    @Override
    public void writeTo(ScratchOutputStream out, ScratchReferenceTable ref,
            ScratchProject project) throws IOException
    {
        super.writeTo(out, ref, project);

        out.write32bitUnsignedInt(entries.size());
        for (Entry<ScratchOptionalField, ScratchOptionalField> entry : entries
                .entrySet()) {
            ref.writeField(entry.getKey().get(), out, project);
            ref.writeField(entry.getValue().get(), out, project);
        }
    }

    @Override
    public void readFrom(int id, ScratchInputStream in, ScratchProject project)
            throws IOException
    {
        super.readFrom(id, in, project);

        int size = in.read32bitUnsignedInt();
        for (int i = 0; i < size; ++i) {
            entries.put(ScratchObjects.read(in, project),
                    ScratchObjects.read(in, project));
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
        ScratchObjectAbstractDictionary other = (ScratchObjectAbstractDictionary) obj;
        return Objects.equals(entries, other.entries);
    }
}

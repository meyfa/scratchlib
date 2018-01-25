package scratchlib.objects.fixed.collections;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;

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
    private final LinkedHashMap<ScratchOptionalField, ScratchOptionalField> entriesBeforeResolve = new LinkedHashMap<>();
    private final LinkedHashMap<ScratchObject, ScratchObject> entries = new LinkedHashMap<>();

    /**
     * @param classID The ID of the class this object belongs to.
     */
    public ScratchObjectAbstractDictionary(int classID)
    {
        super(classID);
    }

    /**
     * @return The number of elements in this dictionary.
     */
    public int size()
    {
        return entries.size() + entriesBeforeResolve.size();
    }

    /**
     * Finds the object associated with the given key.
     *
     * @param key The key whose associated value shall be found.
     * @return The associated value.
     */
    public ScratchObject get(ScratchObject key)
    {
        return entries.get(key);
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

        entries.put(key, value);
    }

    /**
     * Removes the entry with the given key from this dictionary.
     *
     * @param key The entry's key.
     */
    public void remove(ScratchObject key)
    {
        entries.remove(key);
    }

    /**
     * @return A writable set of all keys in this dictionary.
     */
    public Set<ScratchObject> keySet()
    {
        return entries.keySet();
    }

    /**
     * @return A writable collection of all values in this dictionary.
     */
    public Collection<ScratchObject> values()
    {
        return entries.values();
    }

    /**
     * @return A writable set of all mappings in this dictionary.
     */
    public Set<Entry<ScratchObject, ScratchObject>> entrySet()
    {
        return entries.entrySet();
    }

    @Override
    public boolean createReferences(ScratchReferenceTable ref,
            ScratchProject project)
    {
        if (!super.createReferences(ref, project)) {
            return false;
        }

        for (Entry<ScratchObject, ScratchObject> entry : entries.entrySet()) {
            entry.getKey().createReferences(ref, project);
            entry.getValue().createReferences(ref, project);
        }

        return true;
    }

    @Override
    public void resolveReferences(ScratchReferenceTable ref)
    {
        super.resolveReferences(ref);

        entries.clear();
        for (Entry<ScratchOptionalField, ScratchOptionalField> entry : entriesBeforeResolve
                .entrySet()) {
            entry.getKey().resolve(ref);
            entry.getValue().resolve(ref);

            entries.put(entry.getKey().get(), entry.getValue().get());
        }
        entriesBeforeResolve.clear();
    }

    @Override
    public void writeTo(ScratchOutputStream out, ScratchReferenceTable ref,
            ScratchProject project) throws IOException
    {
        super.writeTo(out, ref, project);

        out.write32bitUnsignedInt(entries.size());
        for (Entry<ScratchObject, ScratchObject> entry : entries.entrySet()) {
            ref.writeField(entry.getKey(), out, project);
            ref.writeField(entry.getValue(), out, project);
        }
    }

    @Override
    public void readFrom(int id, ScratchInputStream in, ScratchProject project)
            throws IOException
    {
        super.readFrom(id, in, project);

        int size = in.read32bitUnsignedInt();
        for (int i = 0; i < size; ++i) {
            entriesBeforeResolve.put(ScratchObjects.read(in, project),
                    ScratchObjects.read(in, project));
        }
    }
}

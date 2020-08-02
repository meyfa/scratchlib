package scratchlib.objects.user;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import scratchlib.objects.IScratchReferenceType;
import scratchlib.objects.ScratchObject;
import scratchlib.objects.ScratchObjects;
import scratchlib.objects.ScratchOptionalField;
import scratchlib.objects.ScratchReferenceTable;
import scratchlib.project.ScratchProject;
import scratchlib.project.ScratchVersion;
import scratchlib.reader.ScratchInputStream;
import scratchlib.writer.ScratchOutputStream;


/**
 * Base class for all Scratch objects that are neither inline nor fixed-format,
 * i.e. the objects with class IDs 100 to 255 that can change their attributes
 * between Scratch versions.
 *
 * <p>
 * Fields are stored in a map and can be accessed with their string keys, which
 * subclasses should make available as {@code public static final} properties
 * (e.g. {@code FIELD_BOUNDS} for a field called "bounds").
 */
public abstract class ScratchUserClassObject extends ScratchObject implements IScratchReferenceType
{
    private final ClassVersion version;
    private final Map<String, FieldDescriptor> fields = new LinkedHashMap<>();

    /**
     * @param classID The ID of the class this object belongs to.
     * @param version The version of the class this object belongs to.
     */
    public ScratchUserClassObject(int classID, ClassVersion version)
    {
        super(classID);
        this.version = version;
    }

    /**
     * Gets the class version in relation to a given Scratch version, as the
     * class version may change between those.
     *
     * @param projectVersion The Scratch version for which to get the class version.
     * @return The version of the class this object belongs to.
     */
    public int getClassVersion(ScratchVersion projectVersion)
    {
        return version.get(projectVersion);
    }

    /**
     * Specifies a new field on this class. The field has the given name, and is
     * initialized to the given value.
     *
     * @param name The field's name.
     * @param defaultValue The field's initial value.
     * @throws NullPointerException If name or defaultValue are null.
     * @throws IllegalArgumentException If the name is already taken.
     * @throws IndexOutOfBoundsException If the class would have too many (more than 255) fields
     *          after specifying this one.
     */
    protected void specifyField(String name, ScratchObject defaultValue)
    {
        this.specifyField(name, defaultValue, null);
    }

    /**
     * Specifies a new field on this class. The field has the given name, and is
     * initialized to the given value.
     *
     * <p>
     * As opposed to {@link #specifyField(String, ScratchObject)}, this
     * specifies a field that is version-dependent. In other words, the field is
     * only written or read if the project has a matching version.
     *
     * @param name The field's name.
     * @param defaultValue The field's initial value.
     * @param version The Scratch version this field applies to.
     * @throws NullPointerException If name or defaultValue are null.
     * @throws IllegalArgumentException If the name is already taken.
     * @throws IndexOutOfBoundsException If the class would have too many (more than 255) fields
     *          after specifying this one.
     */
    protected void specifyField(String name, ScratchObject defaultValue, ScratchVersion version)
    {
        Objects.requireNonNull(name);
        if (fields.containsKey(name)) {
            throw new IllegalArgumentException(String.format("field %s already specified", name));
        }
        Objects.requireNonNull(defaultValue);

        if (fields.size() >= 255) {
            throw new IndexOutOfBoundsException("too many fields (max 255)");
        }

        fields.put(name, new FieldDescriptor(defaultValue, version));
    }

    /**
     * Retrieves the field's value.
     *
     * @param name The field's name.
     * @return The field's value.
     */
    public ScratchObject getField(String name)
    {
        return fields.get(name).field.get();
    }

    /**
     * Sets the field's value to the given object.
     *
     * @param name The field's name.
     * @param value The field's new value.
     * @throws NullPointerException If the value is null.
     */
    public void setField(String name, ScratchObject value)
    {
        Objects.requireNonNull(value);
        fields.get(name).field = new ScratchOptionalField(value);
    }

    /**
     * @return A set containing all field names. No version filtering is done.
     */
    public Set<String> getFieldNames()
    {
        return fields.keySet();
    }

    @Override
    public boolean createReferences(ScratchReferenceTable ref, ScratchProject project)
    {
        if (!super.createReferences(ref, project)) {
            return false;
        }

        for (FieldDescriptor fd : fields.values()) {
            if (fd.isApplicable(project)) {
                fd.field.get().createReferences(ref, project);
            }
        }

        return true;
    }

    @Override
    public void resolveReferences(ScratchReferenceTable ref)
    {
        super.resolveReferences(ref);

        for (FieldDescriptor fd : fields.values()) {
            fd.field.resolve(ref);
        }
    }

    @Override
    public void writeTo(ScratchOutputStream out, ScratchReferenceTable ref, ScratchProject project) throws IOException
    {
        super.writeTo(out, ref, project);

        out.write(getClassVersion(project.getVersion()));

        int length = (int) fields.values().stream().filter(fd -> fd.isApplicable(project)).count();
        out.write(length);
        for (FieldDescriptor fd : fields.values()) {
            if (fd.isApplicable(project)) {
                ref.writeField(fd.field.get(), out, project);
            }
        }
    }

    @Override
    public void readFrom(int id, ScratchInputStream in, ScratchProject project) throws IOException
    {
        super.readFrom(id, in, project);

        int version = in.read();
        int expectedVersion = getClassVersion(project.getVersion());
        if (version != expectedVersion) {
            throw new IOException(String.format("illegal version %d, expected %d (class ID %d",
                    version, expectedVersion, getClassID()));
        }

        int length = in.read();
        int expectedLength = (int) fields.values().stream().filter(fd -> fd.isApplicable(project)).count();
        if (length != expectedLength) {
            throw new IOException(String.format("illegal length %d, expected %d (class ID %d",
                    length, expectedLength, getClassID()));
        }

        for (FieldDescriptor fd : fields.values()) {
            if (fd.isApplicable(project)) {
                fd.field = ScratchObjects.read(in, project);
            }
        }
    }

    /**
     * Describes a class field, storing its value and optionally the required
     * Scratch version.
     */
    private static final class FieldDescriptor
    {
        private final ScratchVersion version;
        private ScratchOptionalField field;

        /**
         * @param value This field's initial value.
         * @param version The Scratch version required. Can be null.
         */
        public FieldDescriptor(ScratchObject value, ScratchVersion version)
        {
            this.version = version;
            this.field = new ScratchOptionalField(value);
        }

        /**
         * Checks whether this field is applicable for the given project by
         * comparing its version with this field's constructor version.
         *
         * @param project The project.
         * @return Whether the project version matches.
         */
        public boolean isApplicable(ScratchProject project)
        {
            return version == null || project.getVersion() == version;
        }
    }

    /**
     * Stores a <b>class</b> version for each existing <b>project</b> version.
     */
    public static final class ClassVersion
    {
        private final int versionScratch, versionByob;

        /**
         * One-size-fits-all constructor.
         *
         * @param version The class version to use for all Scratch versions.
         */
        public ClassVersion(int version)
        {
            this(version, version);
        }

        /**
         * Constructor with separate versions for each Scratch version.
         *
         * @param versionScratch The class version for Scratch 1.4.
         * @param versionByob The class version for BYOB 3.1.1.
         */
        public ClassVersion(int versionScratch, int versionByob)
        {
            this.versionScratch = versionScratch;
            this.versionByob = versionByob;
        }

        /**
         * Returns the class version as an int for a given project version.
         *
         * @param projectVersion The project's Scratch version.
         * @return The associated class version.
         */
        public int get(ScratchVersion projectVersion)
        {
            switch (projectVersion) {
                case BYOB311:
                    return versionByob;
                case SCRATCH14:
                    return versionScratch;
                default:
                    throw new RuntimeException(String.format("no case for project version: %s", projectVersion.name()));
            }
        }
    }
}

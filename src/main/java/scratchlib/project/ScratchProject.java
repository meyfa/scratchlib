package scratchlib.project;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map.Entry;

import scratchlib.objects.ScratchObject;
import scratchlib.objects.ScratchObjectStore;
import scratchlib.objects.fixed.collections.ScratchObjectAbstractDictionary;
import scratchlib.objects.fixed.collections.ScratchObjectDictionary;
import scratchlib.objects.fixed.data.ScratchObjectAbstractString;
import scratchlib.objects.fixed.data.ScratchObjectString;
import scratchlib.objects.fixed.data.ScratchObjectUtf8;
import scratchlib.objects.fixed.forms.ScratchObjectColorForm;
import scratchlib.objects.fixed.forms.ScratchObjectForm;
import scratchlib.objects.inline.ScratchObjectBoolean;
import scratchlib.objects.user.morphs.ScratchObjectStageMorph;
import scratchlib.writer.ScratchOutputStream;


/**
 * Represents a Scratch project file that has version information, metadata, a
 * stage with sprites, etc.
 */
public class ScratchProject
{
    /**
     * Specifies the operating system (OS) version the project was created on.
     * The default is "NT". Has no influence on the project.
     */
    public static final String INFO_OS_VERSION = "os-version";
    /**
     * Specifies the operating system (OS) platform the project was created on.
     * The default is "Win32". Has no influence on the project.
     */
    public static final String INFO_PLATFORM = "platform";
    /**
     * Specifies the language of the project. The default is "en".
     */
    public static final String INFO_LANGUAGE = "language";
    /**
     * Specifies the project's save history. The default is "\r" (empty
     * history). Has no influence on the project.
     */
    public static final String INFO_HISTORY = "history";
    /**
     * Specifies the program version the project was created with. This can be
     * ANY STRING and has no influence on the project. The default is set
     * according to {@link ScratchVersion#getVersionString()}.
     */
    public static final String INFO_SCRATCH_VERSION = "scratch-version";
    /**
     * Specifies the project's thumbnail. This is a
     * {@link ScratchObjectColorForm}. Passing NIL is not recommended, since it
     * crashes the Scratch file browser; the default is to omit it entirely.
     */
    public static final String INFO_THUMBNAIL = "thumbnail";
    /**
     * Specifies the project's comment. This is visible in the Scratch file
     * browser, and through "File" -> "Project Notes". The default is the URL to
     * this library's GitHub repository.
     */
    public static final String INFO_COMMENT = "comment";
    /**
     * Specifies the project's author. This is visible in the Scratch file
     * browser. The default is "".
     */
    public static final String INFO_AUTHOR = "author";
    /**
     * Specifies the {@link ScratchObjectForm} storing the pen trails and stamps
     * drawn by sprites. The default is NIL.
     */
    public static final String INFO_PEN_TRAILS = "penTrails";
    /**
     * BYOB 3.1.1 only. Specifies whether sprites have to stay on the stage
     * (true; the default) or are allowed off stage (false). For Scratch, this
     * is always true.
     */
    public static final String INFO_KEEP_ON_STAGE = "keepOnStage";

    private final ScratchVersion version;
    private ScratchObjectStore info = new ScratchObjectStore(ScratchObject.NIL);
    private ScratchObjectStore stage = new ScratchObjectStore(
            ScratchObject.NIL);

    /**
     * @param version This project's version.
     */
    public ScratchProject(ScratchVersion version)
    {
        this.version = version;

        // populate info section

        ScratchObjectDictionary dict = new ScratchObjectDictionary();
        {
            dict.put(new ScratchObjectString(INFO_OS_VERSION),
                    new ScratchObjectString("NT"));
            dict.put(new ScratchObjectString(INFO_PLATFORM),
                    new ScratchObjectString("Win32"));
            dict.put(new ScratchObjectString(INFO_LANGUAGE),
                    new ScratchObjectString("en"));
            dict.put(new ScratchObjectString(INFO_HISTORY),
                    new ScratchObjectUtf8("\r"));
            dict.put(new ScratchObjectString(INFO_SCRATCH_VERSION),
                    new ScratchObjectString(version.getVersionString()));
            // thumbnail omitted (-> ColorForm)
            dict.put(new ScratchObjectString(INFO_COMMENT),
                    new ScratchObjectUtf8(
                            "https://github.com/JangoBrick/scratchlib"));
            dict.put(new ScratchObjectString(INFO_AUTHOR),
                    new ScratchObjectUtf8(""));
            dict.put(new ScratchObjectString(INFO_PEN_TRAILS),
                    ScratchObject.NIL);
            dict.put(new ScratchObjectString(INFO_KEEP_ON_STAGE),
                    ScratchObjectBoolean.TRUE);
        }
        info.set(dict);

        // populate stage section

        ScratchObjectStageMorph stageMorph = new ScratchObjectStageMorph();

        stage.set(stageMorph);
    }

    /**
     * @return This project's version.
     */
    public ScratchVersion getVersion()
    {
        return version;
    }

    /**
     * @return The object that is the project's info section.
     */
    public ScratchObjectStore getInfoSection()
    {
        return info;
    }

    /**
     * Sets the project's info section.
     * 
     * @param info The new info section.
     */
    public void setInfoSection(ScratchObjectStore info)
    {
        this.info = info;
    }

    /**
     * @return The object that is the project's stage / content section.
     */
    public ScratchObjectStore getStageSection()
    {
        return stage;
    }

    /**
     * Sets the project's stage / content section.
     * 
     * @param stage The new stage section.
     */
    public void setStageSection(ScratchObjectStore stage)
    {
        this.stage = stage;
    }

    /**
     * Convenience method for retrieving the actual stage morph stored in the
     * stage section.
     * 
     * @return The stage morph.
     * 
     * @see #getStageSection()
     */
    public ScratchObjectStageMorph getStage()
    {
        return (ScratchObjectStageMorph) stage.get();
    }

    /**
     * Convenience method for changing the stage morph stored in the stage
     * section.
     * 
     * @param stage The new stage morph.
     * 
     * @see #setStageSection(ScratchObjectStore)
     */
    public void setStage(ScratchObjectStageMorph stage)
    {
        this.stage.set(stage);
    }

    /**
     * Finds the given property in this project's info dictionary.
     * 
     * @param key The property's key.
     * @return The associated value.
     */
    public ScratchObject getInfoProperty(String key)
    {
        Entry<ScratchObject, ScratchObject> e = findInfoEntry(key);
        return e != null ? e.getValue() : null;
    }

    /**
     * Associates the given value with the given property name in this project's
     * info dictionary.
     * 
     * @param key The property's key.
     * @param value The value to associate with the key.
     */
    public void setInfoProperty(String key, ScratchObject value)
    {
        Entry<ScratchObject, ScratchObject> e = findInfoEntry(key);

        ScratchObject k = e != null ? e.getKey() : new ScratchObjectString(key);
        ((ScratchObjectAbstractDictionary) info.get()).put(k, value);
    }

    /**
     * Finds the info dictionary entry for the given key string. If no such
     * entry exists, returns {@code null}.
     * 
     * @param key The entry's key.
     * @return The entry.
     */
    private Entry<ScratchObject, ScratchObject> findInfoEntry(String key)
    {
        ScratchObject i = this.info.get();
        ScratchObjectAbstractDictionary d = (ScratchObjectAbstractDictionary) i;

        for (Entry<ScratchObject, ScratchObject> e : d.entrySet()) {
            ScratchObject k = e.getKey();
            if (!(k instanceof ScratchObjectAbstractString)) {
                continue;
            }
            ScratchObjectAbstractString ks = (ScratchObjectAbstractString) k;
            if (!ks.getValue().equals(key)) {
                continue;
            }
            return e;
        }

        return null;
    }

    /**
     * Writes this project to the given output stream.
     * 
     * @param out The stream to write to.
     * @throws IOException
     */
    public void writeTo(ScratchOutputStream out) throws IOException
    {
        // write header
        out.writeString(version.getHeader());

        // write info section
        ByteArrayOutputStream infoBytes = getSectionBytes(info);
        out.write32bitUnsignedInt(infoBytes.size());
        infoBytes.writeTo(out);

        // write stage section
        stage.writeTo(out, this);
    }

    /**
     * Writes the given object store into a byte array output stream.
     * 
     * @param section The section to write.
     * @return A {@code ByteArrayOutputStream} containing the written bytes.
     * @throws IOException
     */
    private ByteArrayOutputStream getSectionBytes(ScratchObjectStore section)
            throws IOException
    {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ScratchOutputStream out = new ScratchOutputStream(bout);

        section.writeTo(out, this);

        return bout;
    }
}

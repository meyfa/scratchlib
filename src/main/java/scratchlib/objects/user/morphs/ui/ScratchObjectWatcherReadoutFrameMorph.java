package scratchlib.objects.user.morphs.ui;

import scratchlib.objects.user.morphs.ScratchObjectBorderedMorph;


/**
 * Represents the border of a variable watcher readout morph.
 */
public class ScratchObjectWatcherReadoutFrameMorph extends ScratchObjectBorderedMorph
{
    /**
     * Class ID in binary files.
     */
    public static final int CLASS_ID = 173;
    /**
     * Class version in binary files.
     */
    public static final ClassVersion CLASS_VERSION = new ClassVersion(1);

    /**
     * Constructs an instance with the default values.
     */
    public ScratchObjectWatcherReadoutFrameMorph()
    {
        this(CLASS_ID, CLASS_VERSION);
    }

    /**
     * Constructs an instance with the default values and with the given classID
     * and version.
     *
     * @param classID The ID of the class this object belongs to.
     * @param version The version of the class this object belongs to.
     */
    public ScratchObjectWatcherReadoutFrameMorph(int classID, ClassVersion version)
    {
        super(classID, version);
    }
}

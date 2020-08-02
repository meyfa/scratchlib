package scratchlib.objects.user.morphs;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import scratchlib.objects.ScratchObject;
import scratchlib.objects.fixed.collections.ScratchObjectAbstractCollection;
import scratchlib.objects.fixed.collections.ScratchObjectOrderedCollection;
import scratchlib.objects.user.morphs.ui.ScratchObjectStringMorph;

import static org.junit.jupiter.api.Assertions.*;


public class ScratchObjectStageMorphTest
{
    @Test
    public void getsSpriteCount()
    {
        final String field = ScratchObjectStageMorph.FIELD_SPRITES;

        ScratchObjectStageMorph obj = new ScratchObjectStageMorph();

        assertEquals(0, obj.getSpriteCount());

        ScratchObjectSpriteMorph s0 = new ScratchObjectSpriteMorph();
        ScratchObjectSpriteMorph s1 = new ScratchObjectSpriteMorph();
        obj.setField(field, new ScratchObjectOrderedCollection(Arrays.asList(s0, s1)));

        assertEquals(2, obj.getSpriteCount());
    }

    @Test
    public void getsSprite()
    {
        final String field = ScratchObjectStageMorph.FIELD_SPRITES;

        ScratchObjectStageMorph obj = new ScratchObjectStageMorph();

        ScratchObjectSpriteMorph s0 = new ScratchObjectSpriteMorph();
        ScratchObjectSpriteMorph s1 = new ScratchObjectSpriteMorph();
        obj.setField(field, new ScratchObjectOrderedCollection(Arrays.asList(s0, s1)));

        assertSame(s0, obj.getSprite(0));
        assertSame(s1, obj.getSprite(1));
    }

    @Test
    public void addsSprite()
    {
        ScratchObjectStageMorph obj = new ScratchObjectStageMorph();

        ScratchObjectSpriteMorph s0 = new ScratchObjectSpriteMorph();
        obj.addSprite(s0);

        // adds to sprites
        ScratchObjectAbstractCollection spr = (ScratchObjectAbstractCollection) obj
                .getField(ScratchObjectStageMorph.FIELD_SPRITES);
        assertEquals(1, spr.size());
        assertSame(s0, spr.get(0));

        // adds to submorphs
        ScratchObjectAbstractCollection sum = (ScratchObjectAbstractCollection) obj
                .getField(ScratchObjectMorph.FIELD_SUBMORPHS);
        assertEquals(1, sum.size());
        assertSame(s0, sum.get(0));

        // sets owner
        assertSame(obj, s0.getField(ScratchObjectMorph.FIELD_OWNER));

        // removes from previous owner
        ScratchObjectStageMorph obj2 = new ScratchObjectStageMorph();
        obj2.addSprite(s0);
        assertEquals(1, obj2.getSpriteCount());
        assertEquals(0, obj.getSpriteCount());
        assertSame(obj2, s0.getField(ScratchObjectMorph.FIELD_OWNER));
    }

    @Test
    public void removesSprite()
    {
        final String field = ScratchObjectStageMorph.FIELD_SPRITES;

        ScratchObjectStageMorph obj = new ScratchObjectStageMorph();

        ScratchObjectSpriteMorph s0 = new ScratchObjectSpriteMorph();
        ScratchObjectSpriteMorph s1 = new ScratchObjectSpriteMorph();
        obj.setField(field, new ScratchObjectOrderedCollection(Arrays.asList(s0, s1)));

        obj.removeSprite(1);
        assertEquals(1, obj.getSpriteCount());
        assertSame(s0, obj.getSprite(0));
        assertSame(ScratchObject.NIL, s1.getField(ScratchObjectMorph.FIELD_OWNER));

        obj.removeSprite(s0);
        assertEquals(0, obj.getSpriteCount());
        assertSame(ScratchObject.NIL, s0.getField(ScratchObjectMorph.FIELD_OWNER));
    }

    @Test
    public void clearsSprites()
    {
        final String field = ScratchObjectStageMorph.FIELD_SPRITES;

        ScratchObjectStageMorph obj = new ScratchObjectStageMorph();

        ScratchObjectSpriteMorph s0 = new ScratchObjectSpriteMorph();
        ScratchObjectSpriteMorph s1 = new ScratchObjectSpriteMorph();
        obj.setField(field, new ScratchObjectOrderedCollection(Arrays.asList(s0, s1)));

        ScratchObjectStringMorph sm = new ScratchObjectStringMorph();
        obj.setField(ScratchObjectMorph.FIELD_SUBMORPHS, new ScratchObjectOrderedCollection(Arrays.asList(sm, s0, s1)));

        obj.clearSprites();
        assertEquals(0, obj.getSpriteCount());

        // resets owners
        assertSame(ScratchObject.NIL, s0.getField(ScratchObjectMorph.FIELD_OWNER));
        assertSame(ScratchObject.NIL, s1.getField(ScratchObjectMorph.FIELD_OWNER));

        // filters submorphs
        ScratchObjectOrderedCollection sum = (ScratchObjectOrderedCollection) obj
                .getField(ScratchObjectMorph.FIELD_SUBMORPHS);
        assertEquals(1, sum.size());
        assertSame(sm, sum.get(0));
    }

    @Test
    public void streamsSprites()
    {
        final String field = ScratchObjectStageMorph.FIELD_SPRITES;

        ScratchObjectStageMorph obj = new ScratchObjectStageMorph();

        ScratchObjectSpriteMorph s0 = new ScratchObjectSpriteMorph();
        ScratchObjectSpriteMorph s1 = new ScratchObjectSpriteMorph();
        obj.setField(field, new ScratchObjectOrderedCollection(Arrays.asList(s0, s1)));

        assertEquals(Arrays.asList(s0, s1), obj.streamSprites().collect(Collectors.toList()));
    }
}

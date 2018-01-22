# scratchlib

[![Build Status](https://travis-ci.org/meyfa/scratchlib.svg?branch=master)](https://travis-ci.org/meyfa/scratchlib)

This is a Java library for working with
[Scratch](https://scratch.mit.edu/scratch_1.4/) (up to v1.4) and
[BYOB](http://snap.berkeley.edu/old-byob.html) project files. You can load
projects created in those applications or create them with code, change all
objects they contain, and also write them out to files.



## Loading a project

```java
ScratchReader r = new ScratchReader();
try {
    ScratchProject scratchProject = r.read(new File("project.sb"));
    ScratchProject byobProject = r.read(new File("project.ypr"));
} catch (IOException e) {
    e.printStackTrace();
}
```



## Creating a project

```java
ScratchProject project = new ScratchProject(ScratchVersion.SCRATCH14);
// project is a fully working, albeit empty, Scratch project
// the following actions are examples of what's possible

// set metadata
project.setInfoProperty(ScratchProject.INFO_COMMENT, new ScratchObjectUtf8("My awesome project!"));

// get the stage
ScratchObjectStageMorph stage = project.getStage();

// add a sprite
ScratchObjectSpriteMorph sprite = new ScratchObjectSpriteMorph();
stage.addSprite(sprite);

// ...
```



## Saving a project

```java
ScratchProject project = new ScratchProject(ScratchVersion.SCRATCH14);
// ... or load it from somewhere

ScratchWriter w = new ScratchWriter(new File("project.sb"));
try {
    w.write(project);
} catch (IOException e) {
    e.printStackTrace();
}
```



## Image to form conversion

Sprite costumes and stage backgrounds require images to be given as "forms"
(either `ScratchObjectForm` or `ScratchObjectColorForm`). Conversion to those
formats is rather involved, which is why `ScratchFormEncoder` exists:

```java
BufferedImage img = /* obtain source image here */;
// convert to form
ScratchObjectForm form = ScratchFormEncoder.encode(img);

// create media object
ScratchObjectImageMedia cos = new ScratchObjectImageMedia();
cos.setField(ScratchObjectMedia.FIELD_MEDIA_NAME, new ScratchObjectUtf8("costume name"));
cos.setField(ScratchObjectImageMedia.FIELD_FORM, form);

// use media object - add to sprite, for example
ScratchObjectSpriteMorph sprite = new ScratchObjectSpriteMorph();
sprite.setField(ScratchObjectSpriteMorph.FIELD_MEDIA, new ScratchObjectOrderedCollection(Arrays.asList(cos)));
sprite.setField(ScratchObjectSpriteMorph.FIELD_COSTUME, cos);
```



## Classes

### Inline: Constants

|  ID | Package                   | Constant Name              |
| --: | ------------------------- | -------------------------- |
|   1 | scratchlib.objects        | ScratchObject.NIL          |
|   2 | scratchlib.objects.inline | ScratchObjectBoolean.TRUE  |
|   3 | scratchlib.objects.inline | ScratchObjectBoolean.FALSE |

### Inline: Numbers

Package: `scratchlib.objects.inline`

Use the general `ScratchObjectAbstractNumber` for access, the other classes for
creation.

|  ID | Class Name                          | Use for                      |
| --: | ----------------------------------- | ---------------------------- |
|   4 | `ScratchObjectSmallInteger`         | `int` values                 |
|   5 | `ScratchObjectSmallInteger16`       | `short` values               |
|   6 | `ScratchObjectLargePositiveInteger` | positive `BigDecimal` values |
|   7 | `ScratchObjectLargeNegativeInteger` | negative `BigDecimal` values |
|   8 | `ScratchObjectFloat`                | `double` values              |

----

### Fixed format: data

Package: `scratchlib.objects.fixed.data`

For the string classes below, use the general `ScratchObjectAbstractString` for
access, the other classes for creation.

|  ID | Class Name                 | Use for                        |
| --: | -------------------------- | ------------------------------ |
|   9 | `ScratchObjectString`      | ASCII strings                  |
|  10 | `ScratchObjectSymbol`      | ASCII strings fixed by Scratch |
|  11 | `ScratchObjectByteArray`   | `byte[]` data                  |
|  12 | `ScratchObjectSoundBuffer` | sound data                     |
|  13 | `ScratchObjectBitmap`      | image data                     |
|  14 | `ScratchObjectUtf8`        | UTF-8 strings                  |

### Fixed format: collections

Package: `scratchlib.objects.fixed.collections`

For array, ordered collection, set and identity set, use the general
`ScratchObjectAbstractCollection` for access, the other classes for creation.
For dictionary and identity dictionary, use `ScratchObjectAbstractDictionary`.

|  ID | Class Name                        | Use for                          |
| --: | --------------------------------- | -------------------------------- |
|  20 | `ScratchObjectArray`              | lists of objects                 |
|  21 | `ScratchObjectOrderedCollection`  | lists of objects                 |
|  22 | `ScratchObjectSet`                | lists of objects                 |
|  23 | `ScratchObjectIdentitySet`        | lists of objects                 |
|  24 | `ScratchObjectDictionary`         | maps of objects to other objects |
|  25 | `ScratchObjectIdentityDictionary` | maps of objects to other objects |

### Fixed format: colors

Package: `scratchlib.objects.fixed.colors`

|  ID | Class Name                      | Use for                          |
| --: | ------------------------------- | -------------------------------- |
|  30 | `ScratchObjectColor`            | colors without transparency      |
|  31 | `ScratchObjectTranslucentColor` | colors with transparency (alpha) |

### Fixed format: dimensions

Package: `scratchlib.objects.fixed.dimensions`

|  ID | Class Name               | Use for                             |
| --: | ------------------------ | ----------------------------------- |
|  32 | `ScratchObjectPoint`     | 2D points (x, y)                    |
|  33 | `ScratchObjectRectangle` | 2D rectangles (x, y, width, height) |

### Fixed format: forms

Package: `scratchlib.objects.fixed.forms`

|  ID | Class Name               | Use for                  |
| --: | ------------------------ | ------------------------ |
|  34 | `ScratchObjectForm`      | images                   |
|  35 | `ScratchObjectColorForm` | images with lookup table |

----

### User-class: morphs

Package: `scratchlib.objects.user.morphs`

|  ID | Class Name                 | Description                            |
| --: | -------------------------- | -------------------------------------- |
| 100 | `ScratchObjectMorph`       | base class for all morphs              |
| 124 | `ScratchObjectSpriteMorph` | sprites                                |
| 125 | `ScratchObjectStageMorph`  | the stage                              |
| 125 | `ScratchObjectListMorph`   | user-created list displayable on stage |

### User-class: media

Package: `scratchlib.objects.user.media`

|  ID | Class Name                  | Description                |
| --: | --------------------------- | -------------------------- |
| 109 | `ScratchObjectSampledSound` | a sound split into samples |
| 162 | `ScratchObjectImageMedia`   | media container for images |
| 164 | `ScratchObjectSoundMedia`   | media container for sounds |

### User-class: UI

Package: `scratchlib.objects.user.morphs.ui`

|  ID | Class Name                              | Description                  |
| --: | --------------------------------------- | ---------------------------- |
| 104 | `ScratchObjectAlignmentMorph`           | lays out other morphs stage  |
| 105 | `ScratchObjectStringMorph`              | fixed string display         |
| 106 | `ScratchObjectUpdatingStringMorph`      | dynamic string display       |
| 107 | `ScratchObjectSimpleSliderMorph`        | slider                       |
| 110 | `ScratchObjectImageMorph`               | slider knob                  |
| 155 | `ScratchObjectWatcherMorph`             | variable watcher             |
| 173 | `ScratchObjectWatcherReadoutFrameMorph` | variable watcher readout     |
| 174 | `ScratchObjectWatcherSliderMorph`       | slider extension for watcher |

### User-class: BYOB objects

Package: `scratchlib.objects.user`

|  ID | Class Name                           | Description                   |
| --: | ------------------------------------ | ----------------------------- |
| 201 | `ScratchObjectCustomBlockDefinition` | BYOB's custom blocks          |
| 205 | `ScratchObjectVariableFrame`         | required for script variables |

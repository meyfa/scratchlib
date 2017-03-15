# scratchlib

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
// the following actions are only examples of what's possible

// set metadata
project.setInfoProperty(ScratchProject.INFO_COMMENT, new ScratchObjectUtf8("My awesome project!"));

// get the stage
ScratchObjectStageMorph stage = (ScratchObjectStageMorph) (project.getStageSection().get());
// get its collections of children
ScratchObjectAbstractCollection sprites = (ScratchObjectAbstractCollection) stage.getField(ScratchObjectStageMorph.FIELD_SPRITES);
ScratchObjectAbstractCollection submorphs = (ScratchObjectAbstractCollection) stage.getField(ScratchObjectStageMorph.FIELD_SUBMORPHS);

// add a sprite
ScratchObjectSpriteMorph sprite = new ScratchObjectSpriteMorph();
sprites.add(sprite);
submorphs.add(sprite);
sprite.setField(ScratchObjectSpriteMorph.FIELD_OWNER, stage);

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

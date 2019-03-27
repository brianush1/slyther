# Slyther

Dick Balaska's fork of [gegy1000](https://github.com/gegy1000)'s [Slyther](https://github.com/gegy1000/Slyther)
#### Features
* Fullscreen mode
* Database of previous game scores

---
---
Original Readme:

## Prerequisites

* JDK 8 or higher
* [Gradle](https://gradle.org/gradle-download/)

## Building

Slyther builds with Gradle, to build the standalone executable client jar run the following in the root directory:

    gradle uberjar

## Opening in your IDE

### IntelliJ IDEA

In order to open the Slyther project in IDEA, run the following task from the root:

    gradle idea

This generates the appropriate metadata so that the project can be opened from IDEA.

### Eclipse

  1. Run `gradle eclipse` from the root directory
  2. Import all projects using the "Import Existing Projects into Workspace" wizard

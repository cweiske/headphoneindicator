*******************
Headphone indicator
*******************
Android app that shows a notification icon in the status bar when
headphones are plugged into the phone.

Shows a different icon when a headset is attached.
Both 3.5mm audio jack devices and USB headphones/headsets are supported.

The app aims to be a tiny as possible.

Runs on Android 4.4+.


=====
Usage
=====
After installation_, you have to start the application `at least once`__.
Then it will be running as a background service that shows the headset
status icon whenever the headphones are plugged in.

You can close the app, and the status icon will still work - even after
rebooting your phone.

__ http://stackoverflow.com/a/8535062/282601


===================
Why was it written?
===================
I wrote the headphone indicator app because I was immensely unsatisfied
with apps like `susomena's Headphones Indicator`__ which are over
2 MiB in size, but 99% of their code is only related to advertisement.

My task was to show that you can make it much, much better.

__ https://play.google.com/store/apps/details?id=com.susomena.headphonesindicator


============
Installation
============
You have several installation options:

#. Install from `F-Droid`__
#. Pay on `Google Play`__
#. Download ``.apk`` from `Github`__
#. `Build it yourself <#building>`_

__ https://f-droid.org/repository/browse/?fdid=de.cweiske.headphoneindicator
__ https://play.google.com/store/apps/details?id=de.cweiske.headphoneindicator
__ https://github.com/cweiske/headphoneindicator/releases


========
Building
========
::

    $ ./gradlew build


Releasing
=========
Modify the version in ``AndroidManifest.xml```.

Disable resource file optimization in ``gradle.properties``:

    android.enableResourceOptimizations=false

Build it normally, then sign the generated package file::

    $ gradle assembleRelease

To sign the release with your key, put the path to the signing configuration
file into ``gradle.properties``::

    signingconfigfile=/path/to/signing-config.gradle

The signing configuration file should look like shown in
`Handling signing configs with Gradle`__

__ https://www.timroes.de/2013/09/22/handling-signing-configs-with-gradle/


Dependencies
============
* gradle 8.5.2 (earlier will probably work, too)
* Android SDK 34 (compiles for SDK 19 - Android 4.4)


Meta data
=========
The code repository contains the meta data needed for the Google Play store:
Text, icons, screenshots.

They follow the `Triple-T gradle publisher`__, which are also
`ingested by F-Droid`__.

The meta data directory is ``src/main/play/`` - with ``main`` even though
this app has no flavors, but it's `required by f-droid`__.

__ https://github.com/Triple-T/gradle-play-publisher
__ https://f-droid.org/en/docs/FAQ_-_App_Developers/#how-do-i-change-the-description-and-add-meta-information-like-screenshots
__ https://gitlab.com/fdroid/fdroiddata/-/issues/3447


=========================
About headphone indicator
=========================

Source code
===========
Headphone indicators's source code is available from
http://git.cweiske.de/headphoneindicator.git
or the `mirror on github`__.

__ https://github.com/cweiske/headphoneindicator


License
=======
Headphone indicator is licensed under the `GPL v3 or later`__.

__ http://www.gnu.org/licenses/gpl.html


Author
======
Headphone indicator was written by `Christian Weiske`__.

__ http://cweiske.de/

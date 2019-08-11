# slyther

Dick Balaska's fork of [gegy1000](https://github.com/gegy1000)'s [Slyther](https://github.com/gegy1000/Slyther)
#### Features
* Fullscreen mode
* Database of previous game scores
* LWJGL 3 (on the slyther3 branch)
* Tool for monitoring/configuring/debugging the server
* Custom snakes (protocol 11)

## Installing

You can fetch a prebuilt jar from [the releases](https://github.com/dickbalaska/slyther/releases) area.

Debian systems can install from [launchpad](https://launchpad.net/~dickbalaska/+archive/ubuntu/slyther).

	sudo add-apt-repository ppa:dickbalaska/slyther
	sudo apt update
	sudo apt install slyther
	or
	sudo apt install slyther3

slyther and slyther3 differ only slightly.  
* slyther3 uses a manual cursor instead of the system cursor.
* slyther3 needs a better video card (even though the graphics are the same).

Note the built-in server is really primitive and probably best avoided.

## Building

Slyther builds with Gradle, to build the standalone executable client and server jars run the following in the project root directory:

    gradle jar

Run with

	java -jar server/build/libs/slyther-server-2.0.0.jar

and

	java -jar build/libs/slyther-2.0.0.jar

## Branches
* master contains the LWJGL 2 client and the server
* slyther3 contains the LWJGL 3 client
* slythermon contains the server monitoring tool

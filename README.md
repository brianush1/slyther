# slyther

Dick Balaska's fork of [gegy1000](https://github.com/gegy1000)'s [Slyther](https://github.com/gegy1000/Slyther)
#### Features
* Fullscreen mode
* Database of previous game scores
* LWJGL 3 (on the slyther3 branch)
* Tool for monitoring/configuring/debugging the server
* Custom snakes (protocol 11)


## Building

Slyther builds with Gradle, to build the standalone executable client and server jars run the following in the project root directory:

    gradle uberjar

Run with

	java -jar server/build/libs/slyther-server-0.2.jar

and

	java -jar build/libs/slyther-0.2.jar

## Branches
* master contains the LWJGL 2 client and the server
* slyther3 contains the LWJGL 3 client
* slythermon contains the server monitoring tool

# The Github continuous integration actions use this Makefile, so do not
# change it unless you really know what you are doing. If the CI tests
# fail because you changed this file, it is on you.

tests: compile
	java -cp resources/junit-3.8.2.jar;resources/kalah20200717.jar;bin junit.textui.TestRunner kalah.test.TestKalah

play: compile
	java -cp resources/junit-3.8.2.jar;resources/kalah20200717.jar;bin kalah.Kalah

compile:
	javac -d bin -cp resources/junit-3.8.2.jar;resources/kalah20200717.jar;bin;src src/kalah/Kalah.java
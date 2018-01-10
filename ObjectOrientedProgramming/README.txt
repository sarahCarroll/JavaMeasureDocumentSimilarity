
README.TXT for A Java API for Measuring Document Similarity
===========================================================

Author:		Sarah Carroll
Student ID:	G00330821

This project consists of 6 source files:

Runner.java    - contains main() method which controls the flow of the application. It calls the menu classes show method.

menu.java      - User Interface, prompts user to enter the two file/url names. It invokes the Launcher class.

Launcher.java  - Starts the Threads to documentParser each input file and the Consumer threads.It performs the jackard calculation on the resulting mini hash arrayLists(see Consumer below).

documentParser - Implements Runnable. It calls shingle to create 4-word shingles and stores them on a blocking queue.

Consumer       - Implements Runnable. It creats mini hashes from the shingles on the relevant blocking queue and writes them to an integer arrayList.

Shingle        - Getters and setters and constructors


Sample Execution of application using War and Peace input text file
-------------------------------------------------------------------

C:\Users\sarahc\eclipse-workspace\ObjectOrientedProgramming\bin>java -cp ./oop.jar ie.gmit.sw.Runner
*** Document Comparison Service ***

Enter File Name / URL 1: war.txt

Enter File Name / URL 2: war.txt
------------------------------------------------------
Similarity: 100.0%
------------------------------------------------------


Program Complete!
 
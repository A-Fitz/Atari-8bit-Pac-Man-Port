
## SE 3860 - Software Maintenance and Reengineering

Semester Project - Group 8 - Team Pac-Men 

Austin F, Myles H, Nate B, Nate S

---

This project is a port of the 1982 Atari 8bit version of Pac-Man. Specifically the Roklan version.

---
**Folder Structure:**

 - /assignments
	 - Organize folders in this directory as assignment_#
	 - Assignment folders should contain the original rubric and submissions
 - /source_files
	 - Contains the source files and assets for the project
	 - .gitignore will stop most everything we don't want committed, if you find something new to be added to .gitignore then please do so

---
**Requirements:**
 - Java 11 was used for development
 - IntelliJ was used as an IDE, but others should work

---
**Instructions to Run:**

FROM EXECUTABLE:
 1.  Run the jar file like normal. (double click, or in cmd “java -jar “/file/to/path”

FROM SOURCE:
 1.  In your IDE, create a new project from existing sources and choose the “build.gradle” file in the source_files directory.
 2.  Navigate to the gradle window in Intellij. Run the task at /desktop/Tasks/other/run

---
**Instructions to Build Executable Jar:**

FROM EXECUTABLE:
 1.  Navigate to the gradle window in Intellij. Run the task at /desktop/Tasks/other/dist

# print-script

## Git Hooks

Unhide the .git folder in the project directory by going into File - Settings - Editor - File Types and removing the .git entry from the Ignore files and folders text box.

Create a file named 'pre-commit' without any extension, inside the .git/hooks folder and add the following code:
    
    #!/bin/sh
    ./gradlew build

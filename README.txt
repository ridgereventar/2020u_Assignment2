csci2020u Assignment 2:

By: Martin Reventar, 100623912
    Dominic Cabitac, 100547918


https://github.com/ridgereventar/2020u_Assignment2.git

Steps on downloading:
1) Click link to go to GitHub assignment repository.
2) Open terminal/cmd
3) Change directory into folder you wish to clone this repo: cd <dir_path>
4) Clone repo with: git clone https://github.com/ridgereventar/2020u_Assignment2.git
5) Open Intellij IDEA
6) Choose the repo recently cloned: <dir_path>/2020u_Assignment2

Steps on Running the code:
1) Run the Server.java file first to create the server.
   A msg will print out on the system to indicate that the server is up and listening.
2) Run the Client.java file to create a client.
3) The system will then wait for a command to be entered, use either of the following:
   NOTE: the commands are entered in the 'Run' panel on intellij.
   i) DIR (return file list of the server)
  ii) UPLOAD <filename> (writes a newFile onto the server)
      -> this command is followed by the user inputted contents of the file
      -> the file will end when the user types "DONE" on a newline.
 iii) DOWNLOAD <filename> (displays contents of this file from the server)

4) After a command is sent, the connection ends and the client UI is displayed.
5) Important note:
   When creating these clients for the first time, the local folders for these clients will be empty.
   We have provided test files for clientFolder1 to allow for testing the download and upload buttons.
   However the user can create more clients by running the Client.java multiple times. This will create new
   client folders each with a number indicating which client it belongs to.
   (when the server is stopped and run again, the client count will start from zero, but will still link to clientFolders if they exist)
6) When the UI is displayed, the user can:
   i) select any file on the left (ClientsFiles) and hit the Upload button to send to the serverFolder.
  ii) select any file on the right(ServersFiles) and hit the Download button to send to the clientsFolder.
 iii) hit the refresh button to show any changes made between other clients and the server.

NOTES:
- Null pointer Exception messages may appear when clicking on buttons. Was unable to figure out the cause.
However, buttons functionality works fine. The files are transferred on command and Trees refreshed.
- Program runs on Java 1.8
csci2020u Assignment 2:

By: Martin Reventar, 100623912
    Dominic Cabitac,


Steps on downloading:
1) Open the link given above, which will redirect you to my public github repository.
2) Click on "Clone or download" button, and select download ZIP.
3) Locate the ZIP file in file browser and open it to create a folder.
4) Open IntelliJ IDEA.
5) Open the folder.
6) the 'serverFolder' included contains the contents of the files in the server.

Steps on Running the code:
1) Run the Server.java file first to create the server.
   A msg will print out on the system to indicate that the server is up and listening.
2) Run the Client.java file to create a client.
3) The system will then wait for a command to be entered, use either of the following:
   i) DIR (return filelist of the server)
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

NOTE: Null pointer Exception messages may appear when clicking on buttons. Was unable to figure out the cause.
      However, buttons functionality works fine. The files are transferred on command and Trees refreshed.
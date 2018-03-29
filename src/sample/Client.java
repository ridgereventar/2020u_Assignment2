package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class Client extends Application {

    private static String[] arguments;
    private File clientDir;
    private File serverDir;

    private Socket socket;
    private PrintWriter out = null;
    private InputStreamReader inSystem = null;
    private BufferedReader brSystem = null;
    private InputStreamReader in = null;
    private BufferedReader br = null;

    @Override
    public void start(Stage primaryStage) throws Exception{
        try {
            // Set up client socket:
            socket = new Socket("localhost", 1521);
            inSystem = new InputStreamReader(System.in);
            brSystem = new BufferedReader(inSystem);
            in = new InputStreamReader(socket.getInputStream());
            br = new BufferedReader(in);
            out = new PrintWriter(socket.getOutputStream(), true);

            // reads the first line sent from Server, which is the clientCount
            // uses this to generate this clients shared folder.
            int clientCount = Integer.parseInt(br.readLine());

            // Set up client and server Directories
            serverDir = new File("/Users/RidgeReventar/IdeaProjects/Assignment2/serverFolder");
            clientDir = new File("/Users/RidgeReventar/IdeaProjects/Assignment2/" + "clientFolder" + Integer.toString(clientCount));
            clientDir.mkdir();

            System.out.println("Enter a command to send to the server: (DIR, UPLOAD <filename>, DOWNLOAD <filename>)");
            // Store the user input command in a String variable.
            String command = brSystem.readLine();
            if(command.matches("DIR")) {
                handle_DIR_response(command, br);
            } else if(command.startsWith("UPLOAD")) {
                send_UPLOAD_request(command,brSystem,out);
            } else if(command.startsWith("DOWNLOAD")) {
                handle_DOWNLOAD_response(command,br,out);
            }

            closeALL();

        } catch(Exception e) {
            e.printStackTrace();
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();

        Controller cont = loader.<Controller>getController();
        // send the clientDir and serverDir to the controller and populate the trees.
        cont.setDirectories(clientDir, serverDir);
        cont.populateClientTree();
        cont.populateServerTree();

        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();

    }

    public void handle_DIR_response(String command, BufferedReader br) throws IOException {
        // send the command to the server and read in its response (file names) to print out to the user.
        out.println(command);
        String line;
        while((line = br.readLine()) != null) {
            System.out.println(line);
        }
    }

    public void send_UPLOAD_request(String command, BufferedReader brSystem, PrintWriter out) throws IOException {
        // send the command to the server, followed by user inputted lines for a text file.
        // when done entering the file contents, type "DONE" on a newline to end the request.
        out.println(command);
        System.out.println("Enter the contents of the file: (Type 'DONE' on newline when finished)" );
        while(true) {
            String line = brSystem.readLine();
            if(line.matches("DONE")) {
                break;
            } else {
                out.println(line);
            }
        }
    }

    public void handle_DOWNLOAD_response(String command, BufferedReader br, PrintWriter out) throws IOException {
        // send the command to the server and read in its response (file contents) to print to the user.
        out.println(command);
        String line;
        while((line = br.readLine()) != null) {
            System.out.println(line);
        }
    }

    public void closeALL() throws IOException {
        inSystem.close();
        brSystem.close();
        in.close();
        br.close();
        out.close();
        socket.close();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

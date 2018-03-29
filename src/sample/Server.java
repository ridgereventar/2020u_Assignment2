package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Server extends Application {

    private ServerSocket serverSocket;
    public File serverDir = new File(System.getProperty("user.dir") + "/serverFolder");
    public ClientCounter counter = new ClientCounter(0);

    @Override
    public void start (Stage primaryStage) throws Exception {

        System.out.println("Server up & listening...");
        serverSocket = new ServerSocket(1521);

        while(true) {
            Socket clientSocket = serverSocket.accept();
            ClientConnectionHandler handler = new ClientConnectionHandler(clientSocket);

            // for every thread client connected, increase the client count
            // and send to ThreadClient instance to print it out back to the client.
            counter.increment();
            handler.setCount(counter.getCount());

            Thread handlerThread = new Thread(handler);
            handlerThread.start();
        }
    }

    class ClientConnectionHandler implements Runnable {

        private Socket socket;
        private InputStreamReader in = null;
        private PrintWriter out = null;
        private BufferedReader br = null;
        private int count;
        
        private ClientConnectionHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                in = new InputStreamReader(socket.getInputStream());
                out = new PrintWriter(socket.getOutputStream(),true);
                br = new BufferedReader(in);

                // Send the current count to the client. (to allow the client to create its shared folder)
                out.println(Integer.toString(count));

                // Handle each command request:
                String commandFromClient = br.readLine();
                if(commandFromClient.matches("DIR")) {
                    handle_DIR_request();
                } else if(commandFromClient.startsWith("UPLOAD")) {
                    handle_UPLOAD_request(commandFromClient);
                } else if(commandFromClient.startsWith("DOWNLOAD")) {
                    handle_DOWNLOAD_request(commandFromClient);
                }
                closeALL();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }

        private void setCount(int c) {
            count = c;
        }

        private void handle_DIR_request() throws IOException {
            // Print the file list to the client.
            for(File f: serverDir.listFiles()) {
                out.println(f.getName());
            }
        }

        private void handle_UPLOAD_request(String command) throws IOException {
            // to upload the file to the serversFolder,
            // the following code generates the appropriate path to do so:
            String[] token = command.split(" ");
            String filename = token[1];
            File newFile = new File(serverDir.toString() + "/" + filename);

            // using this path, write the contents of the new file.
            PrintWriter writer = new PrintWriter(newFile);
            String line;
            while((line = br.readLine()) != null) {
                writer.println(line);
            }
            writer.close();
        }

        private void handle_DOWNLOAD_request(String command) throws IOException {
            // To download a files contents,
            // the following code acquires the correct path location of the file in order to read it.
            String[] token = command.split(" ");
            String filename = token[1];
            File newFile = new File(serverDir.toString() + "/" + filename);

            // using this path, read the contents of the file and print to the client.
            BufferedReader br = new BufferedReader(new FileReader(newFile));
            String line;
            while((line = br.readLine()) != null) {
                out.println(line);
            }
            br.close();
        }

        private void closeALL() throws IOException {
            in.close();
            br.close();
            out.close();
            socket.close();
        }
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}

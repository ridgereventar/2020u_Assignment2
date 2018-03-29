package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.event.ActionEvent;
import java.io.*;
import java.net.Socket;

public class Controller {
    @FXML private TreeView<String> clientTreeView;
    @FXML private TreeView<String> serverTreeView;
    @FXML private Button uploadB;
    @FXML private Button downloadB;

    private File clientDir;
    private File serverDir;
    private TreeItem<String> clientFileSelected = new TreeItem<>();
    private TreeItem<String> serverFileSelected = new TreeItem<>();

    public void setDirectories(File cliDir, File servDir) {
        this.clientDir = cliDir;
        this.serverDir = servDir;
    }

    public void initialize() {}

    public void populateClientTree() {
        clientTreeView.setRoot(generateNodes(clientDir));
        clientTreeView.setShowRoot(false);
        clientTreeView.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            if(newValue.isLeaf()) {
                // keeps track of the current file selected.
                clientFileSelected = newValue;
            }
        });
    }

    public void populateServerTree() {
        serverTreeView.setRoot(generateNodes(serverDir));
        serverTreeView.setShowRoot(false);
        serverTreeView.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            if(newValue.isLeaf()) {
                // keeps track of the current file selected.
                serverFileSelected = newValue;
            }
        });

    }

    public TreeItem<String> generateNodes(File dir) {
        TreeItem<String> root = new TreeItem<String>(dir.getName());

        for(File f: dir.listFiles()) {
            if(f.isDirectory()) {
                root.getChildren().add(generateNodes(f));
            } else {
                root.getChildren().add(new TreeItem<>(f.getName()));
            }
        }
        return root;
    }

    @FXML private void uploadFile(ActionEvent event) throws IOException {

        // obtain the required paths for file transfer.
        String DestinationPath = serverDir.toString() + "/" + clientFileSelected.getValue();
        String currentPath = clientDir.toString() + "/" + clientFileSelected.getValue();

        BufferedReader br = new BufferedReader(new FileReader(currentPath));
        PrintWriter out = new PrintWriter(DestinationPath);

        // write the file in the appropriate location.
        String line;
        while((line = br.readLine()) != null) {
            out.write(line);
            out.write("\n");
        }

        refreshTrees();

        br.close();
        out.close();

    }

    @FXML private void downloadFile() throws IOException {

        // obtain the required paths for file transfer.
        String DestinationPath = clientDir.toString() + "/" + serverFileSelected.getValue();
        String currentPath = serverDir.toString() + "/" + serverFileSelected.getValue();

        BufferedReader br = new BufferedReader(new FileReader(currentPath));
        PrintWriter out = new PrintWriter(DestinationPath);

        // write the file in the appropriate location.
        String line;
        while((line = br.readLine()) != null) {
            out.write(line);
            out.write("\n");
        }

        refreshTrees();

        br.close();
        out.close();

    }

    @FXML private void refreshTrees() {
        populateClientTree();
        populateServerTree();
    }
}

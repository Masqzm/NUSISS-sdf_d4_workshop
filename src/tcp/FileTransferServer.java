package tcp;

import java.io.*;
import java.net.*;

// Server program to copy (receive) file from client (stores in working dir root)
public class FileTransferServer {

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(Constants.PORT);

        System.out.printf("File transfer server started on port %d\n", Constants.PORT);

        while (true) {
            Socket sock = server.accept();
            System.out.printf("Receiving new file...\n");

            ReceiveFile rcvFile = new ReceiveFile(sock);
            rcvFile.receive();
        }
    }
}
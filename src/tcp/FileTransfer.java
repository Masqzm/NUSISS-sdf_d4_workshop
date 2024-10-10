package tcp;

import java.io.*;

// Client program to transfer file to server (stores in working dir root)
// cmd example: java -cp classes tcp.FileTransfer resource/workshop_summary.jpg
public class FileTransfer {

    public static void main(String[] args) throws IOException {
        if (args.length <= 0) {
            System.err.println("Missing file name");
            System.exit(-1);
        }

        // Create a file object with the input argument
        File file = new File(args[0]);

        // Get file properties (metadata)
        String fileName = file.getName();
        long fileSize = file.length();

        System.out.printf("Transfering file %s\n", fileName);

        // Use SendFile class to encapsulate (perform) send operations
        SendFile sendFile = new SendFile("localhost", Constants.PORT, file);
        sendFile.send();
    }
}
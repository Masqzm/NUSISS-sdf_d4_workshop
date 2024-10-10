package tcp;

import java.io.*;
import java.net.*;

public class ReceiveFile {

    private final Socket sock;

    public ReceiveFile(Socket sock) {
        this.sock = sock;
    }

    public void receive() throws IOException {
        // Open input stream to receive file
        InputStream is = sock.getInputStream();
        BufferedInputStream bis = new BufferedInputStream(is);
        DataInputStream dis = new DataInputStream(bis);

        // Read the metadata
        String fileName = dis.readUTF();
        long fileSize = dis.readLong();

        // Create a file based on the file name
        FileOutputStream fos = new FileOutputStream(fileName);
        BufferedOutputStream bos = new BufferedOutputStream(fos);

        // Copy file received to server
        int bytesRead = 0;      // current byte size being read
        int bytesRcv = 0;       // total bytes received
        byte[] buffer = new byte[4 * 1024];

        // Read in the file
        while (bytesRcv < fileSize) {
            bytesRead = dis.read(buffer);
            bos.write(buffer, 0, bytesRead);
            bytesRcv += bytesRead;
            System.out.printf("Received %d of %d\n", bytesRcv, fileSize);
        }

        // Close the new file
        bos.flush();
        fos.close();

        // Close the socket
        dis.close();
        bis.close();
        is.close();
        sock.close();
    }
}
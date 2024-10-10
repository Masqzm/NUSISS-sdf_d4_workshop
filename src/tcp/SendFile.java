package tcp;

import java.io.*;
import java.net.*;

public class SendFile {

    private String host;
    private int port;
    private File file;      // file to be sent/copied over to server

    public SendFile(String host, int port, File file) {
        this.host = host;
        this.port = port;
        this.file = file;
    }

    // Mtd to perform the send
    public void send() throws IOException {
        Socket sock = new Socket(host, port);

        // Open a OutputStream to write into socket (server)
        OutputStream os = sock.getOutputStream();
        BufferedOutputStream bos = new BufferedOutputStream(os);
        DataOutputStream dos = new DataOutputStream(bos);

        // Open the file for reading (so we can write into socket)
        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bis = new BufferedInputStream(fis);

        // Get the file information/metadata
        String fileName = file.getName();
        long fileSize = file.length();

        // Write the file metadata 
        dos.writeUTF(fileName);
        dos.writeLong(fileSize);

        // Write file to server
        int bytesRead = 0;      // current byte size being read
        int bytesSent = 0;      // total byte size sent 
        // Create buffer to store bytes being read from bis (file)
        byte[] buffer = new byte[4 * 1024];     

        // bis.read(buffer) -> reads bis and stores it into buffer 
        //                  -> returns total bytes read into buffer (-1 if at end of file)
        while ((bytesRead = bis.read(buffer)) > 0) {
            // Write the amount already read
            bytesSent += bytesRead;
            dos.write(buffer, 0, bytesRead);
            System.out.printf("Send %d of %d\n", bytesSent, fileSize);
        }

        // Close streams (NOTE: always close reverse order to how it was opened)
        bis.close();
        fis.close();

        // Flush streams (to ensure all data written to stream is sent to its destination)
        dos.flush();
        bos.flush();
        os.flush();

        // Close streams & connection
        dos.close();
        bos.close();
        os.close();
        sock.close();
    }
}
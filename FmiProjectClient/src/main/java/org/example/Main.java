package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Main {

    private static final int REQUEST_TOKEN = 8;
    public static final int EXIT_RESPONSE = 1;

    public static void main(String[] args) throws IOException {

        Socket clientSocket = new Socket("localhost",1423);

        InputStream response = clientSocket.getInputStream();
        OutputStream request = clientSocket.getOutputStream();


        while(clientSocket.isConnected()) {

            //Изпращаме заявка до сървъра
            request.write(REQUEST_TOKEN);

            int responseToken = response.read();

            //Изчакваме резултат от сървъра
            System.out.println("Server responded with " + responseToken);

            if(responseToken == EXIT_RESPONSE) {
               break;
            }
            try {
                Thread.sleep(1000);
            }
            catch(InterruptedException e) {
                throw new IOException(e);
            }
        }

        response.close();
        request.close();
        clientSocket.close();
    }
}
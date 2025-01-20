package org.example;

import org.example.controllers.CustomerController;
import org.example.controllers.HomeController;
import org.example.system.ApplicationLoader;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.Buffer;

public class Main {

    private static final String NEW_LINE = "\n\r";

    private static String buildHTTPResponse(String body){

        return "HTTP/1.1 200 OK" + NEW_LINE +
                "Access-Control-Allow-Origin: *" + NEW_LINE +
                "Content-Lenght: " + body.getBytes().length + NEW_LINE +
                "Content-Type: text/html" + NEW_LINE + NEW_LINE +
                body + NEW_LINE + NEW_LINE;
    }

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(1423);

        while(serverSocket.isBound()){

            //Извикване на метод кайто претърсва всички АНОТИРАНИ класове,
            // от приложението.
            ApplicationLoader loader = new ApplicationLoader();
            loader.findAllClasses("org.example");


            //Сървъра който парсва HTTP заявките
            Socket clientSocket = serverSocket.accept();

            InputStream request   = clientSocket.getInputStream();
            OutputStream response = clientSocket.getOutputStream();

            //1.Обработваме входните данни, които идват от заявката
            InputStreamReader reader = new InputStreamReader(request);

            //2.Опаковаме нашият четец в допълнителен буферен четец
            BufferedReader httpRequestReader = new BufferedReader(reader);

            //3.Изчитане на заявката, ред по ред
            String currentLine;

            //създаване на 2 промевливи за метоз и endpoint
            String httpMethod = "";
            String httpEndPoint = "";

            while ((currentLine = httpRequestReader.readLine()) != null) {

                String[] httpHeaderTitleCollection =  currentLine.split(" ");
                httpMethod   = httpHeaderTitleCollection[0];
                httpEndPoint = httpHeaderTitleCollection[1];
                break;
            }

            //4.Създаване на междинен интерпретатор на заявките
            String controllerMessage = "Controller not found";

            if (httpMethod.equals("GET") && httpEndPoint.equals("/home")) {

                HomeController homeController = new HomeController();
                controllerMessage = homeController.Index();
            }

            if (httpMethod.equals("GET") && httpEndPoint.equals("/customer")) {

                CustomerController customerController = new CustomerController();
                controllerMessage = customerController.Index();
            }

            String message = buildHTTPResponse(controllerMessage);
            response.write(message.getBytes());



            response.close();
            request.close();
            clientSocket.close();
        }
    }
}
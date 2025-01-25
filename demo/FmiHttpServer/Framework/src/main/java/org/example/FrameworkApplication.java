package org.example;

import org.example.system.ApplicationLoader;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;

public class FrameworkApplication {

    private static final String NEW_LINE = "\n\r";
    private static ApplicationLoader loader = new ApplicationLoader();



    public static void run(Class mainClass){
        try {
            bootstrap(mainClass);
            startWebServer();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private static String buildHTTPResponse(String body) {

        return "HTTP/1.1 200 OK" + NEW_LINE +
                "Access-Control-Allow-Origin: *" + NEW_LINE +
                "Content-Lenght: " + body.getBytes().length + NEW_LINE +
                "Content-Type: text/html" + NEW_LINE + NEW_LINE +
                body + NEW_LINE + NEW_LINE;
    }

    // Трябва да получаваме MAIN класа и от него
    // да извлечем ивфо за всички паети в клиентското приложение
    private static void bootstrap(Class mainClass) throws IOException, ClassNotFoundException {

        //Извикване на метод кайто претърсва всички АНОТИРАНИ класове,
        // от приложението.
            loader.findAllClasses(mainClass.getPackageName());
    }

    //Стартираме сървъра и чакаме заявки
    private static void startWebServer() throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        ServerSocket serverSocket = new ServerSocket(1423);

        while (serverSocket.isBound()) {


            //Сървъра който парсва HTTP заявките
            Socket clientSocket = serverSocket.accept();

            InputStream request = clientSocket.getInputStream();
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

                String[] httpHeaderTitleCollection = currentLine.split(" ");
                httpMethod = httpHeaderTitleCollection[0];
                httpEndPoint = httpHeaderTitleCollection[1];
                break;
            }

            //4.Създаване на междинен интерпретатор на заявките
            String controllerMessage = loader.executeController(httpMethod, httpEndPoint);

            String message = buildHTTPResponse(controllerMessage);
            response.write(message.getBytes());


            response.close();
            request.close();
            clientSocket.close();
        }
    }
}

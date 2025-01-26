package org.example.system;

import org.example.steriotypes.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import org.example.entities.RequestInfo;

public class ApplicationLoader {

    private ClassLoader classLoader = ClassLoader.getSystemClassLoader();

    //Създаване на речник, в който да се пази комбинацията от HTTP method и HTTP endpoint
    // и класа който трябва да го обработи
    private HashMap<RequestInfo, Class> controllerLookupTable = new HashMap<RequestInfo, Class>();

    //Изпълнение на контролера
    public String executeController(String httpMethod, String httpEndPoint) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {

        Class clazz = controllerLookupTable.get(new RequestInfo(httpMethod ,httpEndPoint));

        if(clazz == null){
            return "";
        }
        //Тук имаме инстанция
        var controllerInstance =  clazz.getDeclaredConstructor().newInstance();

        return  (String) clazz.getMethod("Index").invoke(controllerInstance);
    }


    //1.Проверява и търси всички анотации

    //От тук почваме да търсим класове,
    // които се намират в пакета и свързани с него пакети
    public  void findAllClasses(String packageName) throws IOException, ClassNotFoundException {

        InputStream classLoaderStream = classLoader.getResourceAsStream(packageName.replace("." , "/"));
        BufferedReader classReader         = new BufferedReader(new InputStreamReader(classLoaderStream));

        String packageReference = "";
        while ((packageReference = classReader.readLine()) != null){


            if(!packageReference.contains(".class")){

                findAllClasses(packageName + "." + packageReference);
                continue;
            }

            if(packageReference.contains(".class")){

                //Тук се парсват класове
            }
        }
    }

    private  void  classParser(String packageReference, String packageName) throws ClassNotFoundException {
        String className = packageReference.replace(".class", "");
        String fullName = packageName + "." + className;

        Class clazz = Class.forName(fullName);

        if(clazz.isAnnotationPresent(Controller.class)){


        }
    }

    private  void parseController(Class clazz){

        Controller annotation = (Controller) clazz.getAnnotation(Controller.class);
        String httpMethod = annotation.method();
        String httpEndPoint = annotation.endpoint();

        controllerLookupTable.put(
                new RequestInfo(httpMethod, httpEndPoint), clazz
        );
    }
}

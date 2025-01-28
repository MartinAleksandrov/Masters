package org.example.system;

import org.example.entities.ControllerMeta;
import org.example.steriotypes.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import org.example.entities.RequestInfo;

public class ApplicationLoader {

    private ClassLoader classLoader = ClassLoader.getSystemClassLoader();

    //Създаване на речник, в който да се пази комбинацията от HTTP method и HTTP endpoint
    // и класа който трябва да го обработи
    private HashMap<RequestInfo, ControllerMeta> controllerLookupTable = new HashMap<>();

    //Изпълнение на контролера
    public String executeController(String httpMethod, String httpEndPoint) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {

        ControllerMeta controllerMethodReference = controllerLookupTable.get(new RequestInfo(httpMethod ,httpEndPoint));

        if (controllerMethodReference == null) {
            return "";
        }

        Class clazz = controllerMethodReference.getClassReference();
        String methodName = controllerMethodReference.getMethodName();

        //Тук имаме инстанция
        var controllerInstance =  clazz.getDeclaredConstructor().newInstance();
        return  (String) clazz.getMethod(methodName).invoke(controllerInstance);
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
                classParser(packageReference, packageName);
            }
        }
    }

    private  void  classParser(String packageReference, String packageName) throws ClassNotFoundException {
        String className = packageReference.replace(".class", "");
        String fullName = packageName + "." + className;

        Class clazz = Class.forName(fullName);

        if(clazz.isAnnotationPresent(Controller.class)){

            parseController(clazz);

        }
    }

    private  void parseController(Class clazz){

        //Взимане на всички методи на този контролер, но само ако са публични

        Method[] controllerClassMethodCollection =  clazz.getMethods();

        for (Method method : controllerClassMethodCollection) {

            if(method.isAnnotationPresent(org.example.steriotypes.GetMapping.class)){

                GetMapping annotation = method.getAnnotation(org.example.steriotypes.GetMapping.class);
                String methodEndPoint = annotation.value();

                controllerLookupTable.put(new RequestInfo("GET", methodEndPoint),
                        new ControllerMeta(clazz, method.getName()));
            }

            if(method.isAnnotationPresent(org.example.steriotypes.PostMapping.class)){

                PostMapping annotation = method.getAnnotation(org.example.steriotypes.PostMapping.class);
                String methodEndPoint = annotation.value();

                controllerLookupTable.put(new RequestInfo("POST", methodEndPoint),
                        new ControllerMeta(clazz, method.getName()));
            }

            if(method.isAnnotationPresent(org.example.steriotypes.PutMapping.class)){

                PutMapping annotation = method.getAnnotation(org.example.steriotypes.PutMapping.class);
                String methodEndPoint = annotation.value();

                controllerLookupTable.put(new RequestInfo("PUT", methodEndPoint),
                        new ControllerMeta(clazz, method.getName()));
            }

            if(method.isAnnotationPresent(org.example.steriotypes.DeleteMapping.class)){

                DeleteMapping annotation = method.getAnnotation(org.example.steriotypes.DeleteMapping.class);
                String methodEndPoint = annotation.value();

                controllerLookupTable.put(new RequestInfo("DELETE", methodEndPoint),
                        new ControllerMeta(clazz, method.getName()));
            }
        }
    }
}

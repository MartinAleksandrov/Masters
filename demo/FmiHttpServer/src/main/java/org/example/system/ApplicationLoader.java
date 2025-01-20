package org.example.system;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ApplicationLoader {

    private ClassLoader classLoader = ClassLoader.getSystemClassLoader();

    //1.Проверява и търси всички анотации

    //От тук почваме да търсим класове,
    // които се намират в пакета и свързани с него пакети
    public  void findAllClasses(String packageName) throws IOException, ClassNotFoundException {

        InputStream classLoaderStream = classLoader.getResourceAsStream(packageName.replace("." , "/"));
        BufferedReader classReader         = new BufferedReader(new InputStreamReader(classLoaderStream));

        String packageReference = "";
        while ((packageReference = classReader.readLine()) != null){


            if(!packageReference.contains(".class")){

                findAllClasses(packageName + "." + packageName);
                continue;
            }

            if(packageName.contains(".class")){

                String className = packageReference.replace(".class", "");
                String fullName = packageReference + "." + className;

                Class clazz = Class.forName(fullName);

                if(clazz.isAnnotationPresent(org.example.steriotypes.Controller.class)){

                }
            }
        }
    }
}

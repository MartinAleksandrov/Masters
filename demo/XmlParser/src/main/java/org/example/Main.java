package org.example;

import netscape.javascript.JSObject;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

public class Main {

    public static String parseObject(Object parsableObject) throws IllegalAccessException {

        //Взимам си обекта с който ще работя
        //и получавам данни за класа
        Class parsableObjectClass = parsableObject.getClass();

        Field[] fieldCollection =  parsableObjectClass.getDeclaredFields();

        //Създаваме Stringbuilder който да ни пази всички стойности,за XML документа
        StringBuilder xmlbuilder = new StringBuilder();

        for (Field field : fieldCollection) {
            field.setAccessible(true);

            if(field.isAnnotationPresent(org.example.Documentable.class)) {

                Documentable documentableAnnotattion = field.getAnnotation(org.example.Documentable.class);


                String fieldName = documentableAnnotattion.title().equals("_")
                        ? field.getName()
                        : documentableAnnotattion.title();

                xmlbuilder.append("<")
                        .append(fieldName)
                        .append(">")
                        .append(field.get(parsableObject))
                        .append("</").
                        append(fieldName)
                        .append(">");
            };
        }

        return xmlbuilder.toString();
    }

    //Създаване на JSON парсър ,който получава обект и връща произволен JSON обект
    //можем да връщаме JSON стойности само ако класът е анотиран с JsonEntity
    public static String parseObjectToJson(Object parsableObject) throws IllegalAccessException {

       Class clazz = parsableObject.getClass();

       if(!clazz.isAnnotationPresent(org.example.Jsonable.class)){
           return "{}";
       };

        StringBuilder jsonbuilder = new StringBuilder();

        for(Field field : clazz.getDeclaredFields()){
           field.setAccessible(true);

           if(!field.isAnnotationPresent(org.example.JsonField.class)) {
            continue;
           }

           JsonField jsonFieldAnnotattion = field.getAnnotation(org.example.JsonField.class);

           String fieldName = jsonFieldAnnotattion.title().equals("_")
                   ? field.getName()
                   : jsonFieldAnnotattion.title();

           JsonFieldType fieldType = jsonFieldAnnotattion.expectedType();

            jsonbuilder.append("/")
                    .append(fieldName)
                    .append("/")
                    .append(":");

            if(fieldType == JsonFieldType.STRING){
                jsonbuilder.append("/")
                        .append(field.get(parsableObject))
                        .append("/");
            }


            if(fieldType == JsonFieldType.PLAIN){
                jsonbuilder.append("")
                        .append(field.get(parsableObject))
                        .append("");
            }

            jsonbuilder.append(",");
        }

        return jsonbuilder.toString();
    }

    public static void main(String[] args) throws IllegalAccessException {

       System.out.println(parseObjectToJson(new ItemJson()));
    }
}
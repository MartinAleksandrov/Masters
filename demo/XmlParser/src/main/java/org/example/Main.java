package org.example;

import java.lang.reflect.Field;

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

    public static void main(String[] args) throws IllegalAccessException {
       System.out.println(parseObject(new Item()));
    }
}
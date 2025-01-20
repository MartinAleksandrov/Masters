package org.example;

import javax.swing.plaf.PanelUI;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonField {
      String title() default "_";
      JsonFieldType expectedType() default JsonFieldType.STRING;
}


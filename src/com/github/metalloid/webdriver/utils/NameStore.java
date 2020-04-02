package com.github.metalloid.webdriver.utils;

import com.github.metalloid.pagefactory.controls.Control;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Field;
import java.util.HashMap;

public class NameStore {
    public static HashMap<WebElement, String> map = new HashMap<>();

    public static void store(Object pageObject) {
        Field[] fields = pageObject.getClass().getDeclaredFields();
        for (Field field : fields) {
            Name annotation = field.getAnnotation(Name.class);
            if (annotation != null) {
                try {
                    Object object = field.get(pageObject);
                    if (object.getClass().isAssignableFrom(WebElement.class)) {
                        map.put((WebElement) object, annotation.Description());
                    } else if (object.getClass().isAssignableFrom(Control.class)) {
                        map.put(((Control) object).element(), annotation.Description());
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static String getDescription(WebElement element) {
        return map.get(element);
    }

    public static String getDescription(Control control) {
        return getDescription(control.element());
    }
}
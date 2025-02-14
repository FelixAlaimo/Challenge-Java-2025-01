package utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.jupiter.api.Assertions;

public abstract class BasePojoTests {
	
	public void testGettersAndSettersByReflection(Class<?> clazz) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.getName().startsWith("get")) {
            	try {
            		String property = method.getName().substring(3);
            		String setterMethodName = "set" + property;
            		Method setterMethod = clazz.getDeclaredMethod(setterMethodName, method.getReturnType());
            		Object instance = clazz.getDeclaredConstructor().newInstance();
            		
            		// Test the setter
            		Object value = getDefaultValue(method.getReturnType());
            		setterMethod.invoke(instance, value);
            		
            		// Test the getter
            		Object result = method.invoke(instance);
            		Assertions.assertEquals(value, result, "Getter for " + property + " did not return the expected value");            		
            	}
            	catch (NoSuchMethodException e) {
            		Assertions.fail("No corresponding setter method for " + method.getName());
            	}
            	catch (InvocationTargetException | IllegalAccessException | InstantiationException e) {
            		Assertions.fail("Another error ocurred while doing reflection for " + method.getName());
            	}
            }
        }
    }

    private Object getDefaultValue(Class<?> clazz) {
        if (clazz == boolean.class || clazz == Boolean.class) {
        	return true;
        }
        if (clazz == int.class || clazz == Integer.class) {
        	return 831;
        }
        if (clazz == double.class || clazz == Double.class) {
        	return 562.33;
        }
        if (clazz == String.class) {
        	return "defaultString";
        }
        return null;
    }

}

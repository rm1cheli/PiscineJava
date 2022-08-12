package edu.school21.app;

import edu.school21.Product.Product;
import edu.school21.User.User;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)){

            List<Object> objects = new ArrayList<>();
            objects.add(new User());
            objects.add(new Product());
            System.out.println("Classes:");
            printClasses(objects);
            System.out.println("---------------------\n" + "Enter class name:");

            String nameClass = scanner.next();
            if (getObject(objects, nameClass) == null) {
                System.err.println("no such class exists");
                System.exit(-1);
            }
            printFields(objects, nameClass);
            printMethods(objects, nameClass);
            Object object = getObject(objects, nameClass);
            System.out.println("---------------------\n" + "Let's create an object.");
            Object obj = createClass(scanner, object);
            System.out.println("---------------------\n" + "Enter name of the field for changing:");
            changingField(obj, scanner);
            System.out.println("---------------------\n" + "Enter name of the method for call:");
            callMethod(obj, scanner);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | NoSuchFieldException | IllegalAccessException e) {
            System.err.println("Illegal value");
            System.exit(-1);
        }
    }

    public static void callMethod(Object object, Scanner scanner) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        String line = scanner.next();
        String str = null;
        for (Method method: object.getClass().getDeclaredMethods()){
            str = method.getName() + '(' + getParameters(method) + ')';
            if (str.equals(line)) {
                Field[]     fields = object.getClass().getDeclaredFields();
                Class<?>[] classes = method.getParameterTypes();
                Object[] objects1 = new Object[classes.length];
                int index = 0;
                while (classes.length > index){
                    System.out.print("Enter " + classes[index].getSimpleName() + " value:\n");
                    line = scanner.next();
                    objects1[index] = classes[index].getConstructor(String.class).newInstance(line);
                    ++index;
                }
                Object invoke = method.invoke(object, objects1);
                if(invoke != null) {
                    System.out.println("Method returned:");
                    System.out.println(invoke.toString());
                    return;
                }
            }
        }
        System.err.println("Method not found");
    }

    public static void changingField(Object e, Scanner scanner) throws NoSuchFieldException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String line;
        line = scanner.next();
        Field field = e.getClass().getDeclaredField(line);
        System.out.printf("Enter %s value:\n", field.getType().getSimpleName());
        line = scanner.next();
        field.setAccessible(true);
        field.set(e, field.getType().getConstructor(String.class).newInstance(line));
        field.setAccessible(false);
        System.out.printf("Object updated: %s\n", e.toString());
    }

    public static Object createClass(Scanner scanner, Object o) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?>[] classNew;
        Object[] objects1;
        int i = 0;
        Object result = null;
        String str = null;
        classNew = new Class[o.getClass().getDeclaredFields().length];
        objects1 = new Object[o.getClass().getDeclaredFields().length];
        for(Field field: o.getClass().getDeclaredFields()){
            classNew[i] = field.getType();
            System.out.printf("%s:\n", field.getName());
            str = scanner.next();
            objects1[i] = classNew[i].getConstructor(String.class).newInstance(str);
            ++i;
        }
        result = o.getClass().getConstructor(classNew).newInstance(objects1);
        System.out.printf("Object created: %s\n", result.toString());
        return result;
    }

    public static void printClasses(List<Object> objects){
        for(Object o: objects){
            System.out.println("  - " + o.getClass().getSimpleName());
        }
    }

    public static void printFields(List<Object> objects, String name){
        System.out.println("fields:");
        for(Object o: objects){
            if(o.getClass().getSimpleName().equals(name)){
                for(Field field: o.getClass().getDeclaredFields()){
                    System.out.printf("  %s %s\n", field.getType().getSimpleName(), field.getName());
                }
            }
        }
    }

    public static void printMethods(List<Object> objects, String name){
        System.out.println("methods:");
        for (Object o: objects){
            if (o.getClass().getSimpleName().equals(name)){
                for(Method method: o.getClass().getDeclaredMethods()){
                    System.out.printf("  %s %s(%s)\n", method.getReturnType().getSimpleName()
                            , method.getName(), getParameters(method));
                }
            }
        }
    }
    public static String getParameters(Method method){
        String str = null;
        Class<?>[] parameters = method.getParameterTypes();
        for (int i = 0; i < parameters.length; i++){
            if(str == null)
                str = parameters[i].getSimpleName();
            else
                str = str + ", " + parameters[i].getSimpleName();
        }
        if (str == null)
            str = "";
        return str;
    }
    public static Object getObject(List<Object> objects, String name){
        for(Object o: objects){
            if(o.getClass().getSimpleName().equals(name))
                return o;
        }
        return null;
    }
}

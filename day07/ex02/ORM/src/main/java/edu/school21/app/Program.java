package edu.school21.app;

import edu.school21.models.Car;
import edu.school21.models.User;

import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

public class Program {
    private static final String PACKAGE_NAME = "edu.school21.models.";
    private EmbeddedDatabase dataSourse;
    private OrmManager manager;


    public static void main(String[] args) {
        Program program = new Program();
        program.dataSourse = new EmbeddedDatabaseBuilder().build();
        program.manager = new OrmManager(PACKAGE_NAME, program.dataSourse);
        program.manager.init();
        program.testSave();
        program.testFind();
        program.testUpdate();;
        program.dataSourse.shutdown();
    }


    private void testSave(){
        System.out.println("--SAVE TEST--");
        User user = new User(1L, "Ivan", "Ivanov", 35);
        User user2 = new User(2L, "Peter", "Petrov", 40);
        manager.save(user);
        manager.save(user2);

        Car car = new Car(1L, "Audi");
        Car car2 = new Car(2L, "Mersedes");
        manager.save(car);
        manager.save(car2);
    }

    private void testFind() {
        System.out.println("--FIND TEST--");
        User user;
        if ((user = manager.findById(1L, User.class)) != null) {
            System.out.println(user);
        }
        Car car;
        if ((car = manager.findById(1L, Car.class)) != null) {
            System.out.println(car);
        }
    }

    private void testUpdate() {
        System.out.println("--UPDATE TEST--");
        User user;
        if ((user = manager.findById(1L, User.class)) != null) {
            System.out.println("Before update:" + user);
        }
        user = new User(1L, "Peter", "Petrov", 40);
        manager.update(user);

        if ((user = manager.findById(1L, User.class)) != null) {
            System.out.println("After update:" + user);
        }

        Car car;
        if ((car = manager.findById(2L, Car.class)) != null) {
            System.out.println("Before update:" + car);
        }
        car = new Car(2L, "Mersedes");
        manager.update(car);

        if ((car = manager.findById(2L, Car.class)) != null) {
            System.out.println("After update:" + car);
        }
    }
}
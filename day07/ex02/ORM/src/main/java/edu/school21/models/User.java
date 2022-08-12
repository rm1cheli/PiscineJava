package edu.school21.models;

import edu.school21.annotations.OrmColumn;
import edu.school21.annotations.OrmColumnId;
import edu.school21.annotations.OrmEntity;

@OrmEntity(table = "users")
public class User {
    @OrmColumnId
    private Long id;
    @OrmColumn(name = "first_name")
    private String first_name;
    @OrmColumn(name = "last_name")
    private String last_name;
    @OrmColumn(name = "age")
    private Integer age;

    public User(){

    }

    public User(Long id, String first_name, String last_name, Integer age) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public Integer getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", age=" + age +
                '}';
    }
}

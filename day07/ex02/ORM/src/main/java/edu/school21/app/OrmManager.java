package edu.school21.app;

import edu.school21.annotations.OrmColumn;
import edu.school21.annotations.OrmColumnId;
import edu.school21.annotations.OrmEntity;

import javax.sql.DataSource;
import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.sql.*;
import java.util.HashMap;

public class OrmManager{
    private final String name;
    private Connection connection;

    public OrmManager(String name, DataSource dataSource) {
        this.name = name;
        try {
            this.connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void init(){
        String path = name.replace('.', '/');
        URL url = getClass().getClassLoader().getResource(path);
        File dir = new File(url.getFile());
        File[] filesList = dir.listFiles();
        try{
            PreparedStatement statement;
            for (File file : filesList){
                System.out.println(file.getName());
                String className = file.getName().replace(".class", "");
                Class<?> aClass = Class.forName(name + className);
                if(aClass.isAnnotationPresent(OrmEntity.class)) {
                    OrmEntity entity = aClass.getAnnotation(OrmEntity.class);
                    String dropTable = "DROP TABLE " + entity.table() + " IF EXISTS;";
                    statement = connection.prepareStatement(dropTable);
                    statement.execute();
                    System.out.println(dropTable);
                    String createTable = "CREATE TABLE " + entity.table() + "(\n";
                    for (Field f : aClass.getDeclaredFields()) {
                        if (f.isAnnotationPresent(OrmColumnId.class)) {
                            String typeName = f.getType().getSimpleName();
                            if(typeName.equals("Long")){
                                createTable += "\t" + f.getName() + " BIGINT PRIMARY KEY,\n";
                            } else {
                                throw new IllegalArgumentException(aClass.getName() + " PRIMARY KEY field \"" + f.getName() + "\" must be a Long type!");
                            }
                        } else if (f.isAnnotationPresent(OrmColumn.class)){
                            OrmColumn column = f.getAnnotation(OrmColumn.class);
                            createTable += "\t" + column.name() + " " +
                                            sqlType(f.getType().getSimpleName(), 10, aClass.getName() + "."
                                            + f.getName()) + ",\n";
                        }
                    }
                    createTable += ");";
                    statement = connection.prepareStatement(createTable);
                    statement.execute();
                    System.out.println(createTable);
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error create schema");
            System.exit(-1);
        }
    }

    private String sqlType(String typeName, int length, String classWithField) {
        if (typeName.equals("Integer")) { return "INT"; }
        else if (typeName.equals("Long")) {	return "BIGINT"; }
        else if (typeName.equals("String")) { return "VARCHAR(" + length + ")"; }
        else if (typeName.equals("Double")) { return "DOUBLE"; }
        else if (typeName.equals("Boolean")) { return "BOOLEAN"; }
        else {
            throw new IllegalArgumentException(classWithField + " contains not valid type \"" + typeName+"\"!");
        }
    }

    public void save(Object obj) {
        Class<?> c = obj.getClass();
        if (c.isAnnotationPresent(OrmEntity.class)) {
            OrmEntity entity = c.getAnnotation(OrmEntity.class);
            StringBuilder attributes = new StringBuilder("(");
            StringBuilder entities = new StringBuilder("(");
            Field[] fields = c.getDeclaredFields();
            try {
                for (Field f : fields) {
                    f.setAccessible(true);
                    if (f.isAnnotationPresent(OrmColumnId.class)) {
                        String tt = f.getName();
                        attributes.append(tt).append(", ");
                        Object gg = f.get(obj);
                        entities.append(gg).append(", ");
                    }
                    if (f.isAnnotationPresent(OrmColumn.class)) {
                        OrmColumn column = f.getAnnotation(OrmColumn.class);
                        String attr = column.name();
                        attributes.append(attr).append(", ");
                        Object value = f.get(obj);
                        if (f.getType() == int.class) {
                            entities.append(value).append(", ");
                        } else {
                            entities.append("'").append(value).append("', ");
                        }
                    }
                }
                attributes = new StringBuilder(attributes.substring(0, attributes.lastIndexOf(",")));
                attributes.append(")");
                entities = new StringBuilder(entities.substring(0, entities.lastIndexOf(",")));
                entities.append(")");
                String sql = "INSERT INTO " + entity.table() + " " + attributes + " VALUES " + entities;
                System.out.println(sql);
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.execute();
            } catch (SQLException | IllegalAccessException e) {
                System.err.println("Error save");
                System.exit(-1);
            }
        }
    }

    public void update(Object entity){
        Class<?> aClass = entity.getClass();
        if(aClass.isAnnotationPresent(OrmEntity.class)){
            try {
                OrmEntity entityAnn = aClass.getAnnotation(OrmEntity.class);
                StringBuilder sqlUpdate = new StringBuilder("UPDATE " + entityAnn.table() + " SET ");
                String id = "1";
                for(Field f : aClass.getDeclaredFields()){
                    f.setAccessible(true);
                    if(f.isAnnotationPresent(OrmColumnId.class))
                        id = f.get(entity).toString();
                    else if(f.isAnnotationPresent(OrmColumn.class)){
                        OrmColumn column = f.getAnnotation(OrmColumn.class);
                        sqlUpdate.append(column.name()).append(" = ");
                        if(f.getType().getSimpleName().equals("String")){
                            sqlUpdate.append("'").append(f.get(entity)).append("', ");
                        } else {
                            sqlUpdate.append(f.get(entity)).append(", ");
                        }
                    }
                }
                sqlUpdate = new StringBuilder(sqlUpdate.substring(0, sqlUpdate.length() - 2));
                sqlUpdate.append(" WHERE id = ").append(id).append(";");
                PreparedStatement statement = connection.prepareStatement(sqlUpdate.toString());
                statement.execute();
                System.out.println(sqlUpdate);
            } catch (IllegalAccessException | SQLException e) {
                System.err.println("Error update");
                System.exit(-1);
            }
        }
    }

    public <T> T findById(Long id, Class<T> aClass){
        if (aClass.isAnnotationPresent(OrmEntity.class)) {
            try {
                OrmEntity entity = aClass.getAnnotation(OrmEntity.class);
                String sql_find_by_id = "SELECT * FROM " + entity.table() + " WHERE id = " + id + ";";
                PreparedStatement statement = connection.prepareStatement(sql_find_by_id);
                statement.execute();
                System.out.println(sql_find_by_id);
                ResultSet resultSet = statement.getResultSet();
                if(resultSet.next()) {
                    T object = aClass.newInstance();
                    for (Field f : aClass.getDeclaredFields()) {
                        f.setAccessible(true);
                        if (f.isAnnotationPresent(OrmColumnId.class)) {
                            f.set(object, resultSet.getLong("id"));
                        } else if (f.isAnnotationPresent(OrmColumn.class)) {
                            OrmColumn column = f.getAnnotation(OrmColumn.class);
                            f.set(object, resultSet.getObject(column.name()));
                        }
                    }
                    return object;
                }
                System.out.println("The record was not found!");
                return null;
            } catch (IllegalAccessException | SQLException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                System.out.println("Not found constructor without parameters!");
                return null;
            }
        }
        System.out.println("The class is not associated with a database!");
        return null;
    }

    private <T extends HashMap<String, Object>> String getTableName(T obj) {
        String packageName = obj.getClass().getPackage().getName();
        String className = obj.getClass().getName();
        return className.substring(packageName.length() + 1, className.length()).toLowerCase();
    }
}
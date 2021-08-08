package com.zjj2020.jdbc;

import java.sql.*;
import java.util.Scanner;

public class jdbc {
    static DBLink db = new DBLink();
    public static void main(String[] args) {
        System.out.println("*********************************");
        System.out.println("*\t\t\t\t*");
        System.out.println("*\t欢迎使用学生信息管理系统\t*");
        System.out.println("*\t\t\t\t*");
        System.out.println("*********************************");
        while (true) {
            menu();

        }
    }

    public static void menu() {
        System.out.println("1、添加学生信息");
        System.out.println("2、删除学生信息");
        System.out.println("3、修改学生信息");
        System.out.println("4、查询学生信息");
        System.out.println("请输入操作，以Enter键结束:");
        Scanner scanner = new Scanner(System.in);
        int option  = scanner.nextInt();
        switch (option) {
            case 1:{
                System.out.println("请输入学号：");
                String id = scanner.next();
                String sql = "select id from student where id='"+id+"'";
                if(db.exist(sql)) {
                    System.out.println("该学号已存在，无法添加！");
                    return;
                }
                System.out.println("请输入姓名：");
                String name = scanner.next();
                System.out.println("请输入手机号：");
                String mobile = scanner.next();
                System.out.println("请输入地址：");
                String address = scanner.next();
                sql = "insert into student(id,name,mobile,address) values ('"+id+"','"+name+"','"+mobile+"','"+address+"')";
                if(db.update(sql)) {
                    System.out.println("添加成功！");
                    return;
                }
                System.out.println("系统异常，添加失败");
                break;
            }
            case 2:{
                System.out.println("请输入要删除学生的学号：");
                String id = scanner.next();
                String sql = "select id from student where id='"+id+"'";
                if(!db.exist(sql)) {
                    System.out.println("该学号不存在，无法删除！");
                    return;
                }
                sql = "delete from student where id='"+id+"'";
                if(db.update(sql)) {
                    System.out.println("删除成功！");
                    return;
                }
                System.out.println("系统异常，删除失败！");
                break;
            }
            case 3:{
                System.out.println("请输入要修改学生的学号：");
                String id = scanner.next();
                String sql = "select id from student where id='"+id+"'";
                if(!db.exist(sql)) {
                    System.out.println("该学号不存在，无法修改！");
                    return;
                }
                System.out.println("请输入新的姓名：");
                String name = scanner.next();
                System.out.println("请输入新的手机号：");
                String mobile = scanner.next();
                System.out.println("请输入新的地址：");
                String address = scanner.next();
                sql = "update student set name='"+name+"',mobile='"+mobile+"',address='"+address+"' where id='"+id+"'";
                if(db.update(sql)) {
                    System.out.println("修改成功！");
                }
                System.out.println("系统异常，修改失败！");
                break;
            }
            case 4:{
                System.out.println("请输入要查询学生的学号：");
                String id = scanner.next();
                String sql = "select id from student where id='"+id+"'";
                if(!db.exist(sql)) {
                    System.out.println("该学号不存在，查询无果！");
                    return;
                }
                sql = "select id,name,mobile,address from student where id='"+id+"'";
                db.select(sql);
                break;
            }
            default:
                System.out.println("I'm Sorry,there is not the "+option+" option,please try again.");
        }

    }
}
class DBLink {

    /**
     * 因为一下三个方法都要连接，所以直接定义一个连接的方法，为了简化代码
     * @return
     */
    private Statement createStatement(){
        try {
            //加载驱动
            Class.forName("com.mysql.jdbc.Driver");

            //连接MySQL数据库，并返回Connection类型的对象
            String url = "jdbc:mysql://localhost:3306/db1";
            Connection connection = DriverManager.getConnection(url, "root", "c3zjj");

            //创建Statement对象，相当于 Navicat Premium 中 New Query，并返回
            return connection.createStatement();

        } catch (Exception e) {
            e.printStackTrace();
        }

        //若连接异常，则返回null
        return null;

    }

    /**
     * 添加、删除、修改学生信息
     * @param sql 要执行的SQL语句
     */
    public boolean update(String sql) {
        Connection connection=null;
        Statement statement = null;
        try {
            //调用createStatement()方法
            statement = createStatement();
            //执行SQL语句，并返回影响的行数
            int affect = statement.executeUpdate(sql);
            //无论怎样都要先执行finally里的语句之后，再执行return;
            return affect>0;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //释放资源
            close(connection,statement);
        }
        return false;
    }

    /**
     * 查询学生信息
     * @param sql
     */
    public void select(String sql) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultset = null;
        try {

            statement = createStatement();
            //执行sql语句，并把学生信息存储在resultset资源中
            resultset = statement.executeQuery(sql);
            if(resultset.next()) {//next指向一行数据，执行完后跳到下一行，近似把它看C语言中的“指针”
                String id = resultset.getString("id");
                String name = resultset.getString("name");
                String mobile = resultset.getString("mobile");
                String address = resultset.getString("address");
                System.out.println("学号："+id+",姓名："+name+",手机号："+mobile+",地址："+address);
            } else {
                System.out.println("系统异常，无法查询！");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(resultset,connection,statement);
        }
    }

    /**
     * 判断一个学生是否存在
     * 本质上还是查询学生信息，只是返回值不同，有则返回true，无则返回false
     * @param sql
     * @return
     */
    public boolean exist(String sql) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultset = null;
        try {
            statement = createStatement();
            resultset = statement.executeQuery(sql);
            return resultset.next();//若存在则返回学生信息
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //要及时释放资源
            close(resultset,connection,statement);
        }
        return false;//若不存在则返回false
    }

    /**
     * 定义一个close方法，主要是为了简化代码，
     * 因为上面三个都要释放资源
     * @param connection
     * @param statement
     */
    private static void close(Connection connection,Statement statement) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 与上面close()方法属于重载
     * @param resultset
     * @param connection
     * @param statement
     */
    private static void close(ResultSet resultset,Connection connection,Statement statement) {
        try {
            if (resultset != null) {
                resultset.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        close(connection,statement);
    }
}
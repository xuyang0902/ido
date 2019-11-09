package com.ido.jdbc;

import java.sql.*;

/**
 * @author xu.qiang
 * @date 19/8/5
 */
public class Jdbc {


    public static void main(String[] args) throws ClassNotFoundException, SQLException, IllegalAccessException, InstantiationException {


        Class.forName("com.mysql.jdbc.Driver");

        Connection connection = DriverManager.getConnection("jdbc:mysql://192.168.1.13:3306/test?useUnicode=true&characterEncoding=UTF-8",
                "你不知道的事", "你不知道的事");

        //不要自动提交
        connection.setAutoCommit(false);
        //事务的隔离级别
        connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);

        //执行sql
        PreparedStatement preparedStatement = connection.prepareStatement("select * from test.t where id > 10 and id  <= 15 for update");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            System.out.println(id);
        }

        //提交事务
        connection.commit();

    }
}

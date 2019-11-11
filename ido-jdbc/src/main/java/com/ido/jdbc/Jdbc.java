package com.ido.jdbc;

import java.sql.*;

/**
 * 事务是基于session（connection）
 * <p>
 * mysql默认执行一条sql都是自动提交的
 *
 * @author xu.qiang
 * @date 19/8/5
 */
public class Jdbc {


    public static void main(String[] args) throws ClassNotFoundException, SQLException, IllegalAccessException, InstantiationException {


        Class.forName("com.mysql.jdbc.Driver");

        Connection connection = DriverManager.getConnection("jdbc:mysql://192.168.1.16:3306/test?useUnicode=true&characterEncoding=UTF-8",
                "tbj", "xxx");

        //不要自动提交
        connection.setAutoCommit(false);
        //事务的隔离级别
        connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);

        //执行sql
        PreparedStatement preparedStatement = connection.prepareStatement(" select * from user where id = ?");
        preparedStatement.setInt(1, 1);

        ResultSet resultSet = preparedStatement.executeQuery();
        ResultSetMetaData metaData = resultSet.getMetaData();

        int columnCount = metaData.getColumnCount();

        String[] columnLabels = new String[columnCount];
        String[] columnClassNames = new String[columnCount];
        for (int index = 1; index <= columnCount; index++) {
            columnLabels[index-1] = metaData.getColumnLabel(index);
            columnClassNames[index-1] = metaData.getColumnClassName(index);
        }


        while (resultSet.next()) {

            for (String columnLabel : columnLabels) {
                Object object = resultSet.getObject(columnLabel);

                if(object != null){
                    System.out.println(columnLabel + " --> " +  object.toString());
                }
            }
        }

        //提交事务
        connection.commit();

    }
}

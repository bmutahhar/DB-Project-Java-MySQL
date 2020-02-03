package dbproject;

import com.mysql.cj.jdbc.Driver;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class ConnectionToDB {

    // Connect to database
    private static String user = "Mutahhar";
    private static String password = "Mutahhar@123";
    private static String url = "jdbc:mysql://localhost:3307/classroom";
    private static String selectSql = "select course.title, prereq.prereq_id, prereq.course_id from prereq join course on prereq.course_id =  course.course_id";


    private static Connection connection = null;

    public static void main(String[] args) {

        getConnection();

    }


    public static void getConnection() {
        // TODO Auto-generated constructor stub

        try {
            Driver driver = new Driver();
            DriverManager.registerDriver(driver);

            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connection Successful!");

/*
            //String schema = connection.getSchema();
            //System.out.println("Successful connection - Schema: " + schema);

            System.out.println("Query data example:");
            System.out.println("=========================================");

            // Create and execute a SELECT SQL statement.
            //selectSql = "select course.title, prereq.prereq_id, prereq.course_id from prereq join course on prereq.course_id =  course.course_id;";

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectSql);
            ResultSetMetaData metaData = resultSet.getMetaData();

//            int numberOfColoumns = metaData.getColumnCount();
//            System.out.println(numberOfColoumns);

//            for (int i = 1; i <= numberOfColoumns; i++) {
//                System.out.printf("%-8s\t", resultSet.getObject(i));
//                System.out.println();
//            }

            while (resultSet.next()){
                System.out.print(resultSet.getString(1) + " ");
                System.out.print(resultSet.getString(2) + " ");
                System.out.println(resultSet.getString(3) );
            }

            */


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}

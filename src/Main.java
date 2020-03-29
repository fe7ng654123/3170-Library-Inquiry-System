import java.sql.*;

public class Main {

    public static void main(String[] args) {
        String dbAddress = "jdbc:mysql://projgw.cse.cuhk.edu.hk:2633/db17";
        String dbUsername = "Group17";
        String dbPassword = "CSCI3170";
        Connection con = null;

        //checking if the connection is successful
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(dbAddress, dbUsername, dbPassword);
            Statement stmt = con.createStatement();
        }catch(ClassNotFoundException e){
            System.out.println("Java MySQL DB Driver not found!");
            System.exit(0);
        }catch (SQLException e){
            System.out.println(e);
        }


        try {
            Statement stmt = con.createStatement();
//            String sqlCreate = "CREATE table Student(sid integer primary key, name varchar(30) not null, year integer, age integer)";
//            stmt.execute(sqlCreate);  //Already created table Student

            String sqlInsert = "INSERT INTO Student values(?,?,?,?)";
            int sid = 1155;
            String name = "Potato";
            int year = 2020;
            int age = 99;
            PreparedStatement pstmt = con.prepareStatement(sqlInsert);
            pstmt.setInt(1,sid);
            pstmt.setString(2,name);
            pstmt.setInt(3,year);
            pstmt.setInt(4,age);
//            pstmt.executeUpdate();

            String sqlSelect = "SELECT * from Student;";
            ResultSet resultSet = stmt.executeQuery(sqlSelect);
            if(!resultSet.isBeforeFirst()){
                System.out.println("table is empty");
            }else {
                while (resultSet.next()){
                    System.out.println(resultSet.getString(1));
                    System.out.println(resultSet.getString("name"));
                    System.out.println(resultSet.getString(3));
                    System.out.println(resultSet.getString(4));
                }
            }

        } catch (SQLException e) {
            System.out.println(e);
        }


    }

}

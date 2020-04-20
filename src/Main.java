/*
Group 17:
LIN Chuanfeng	1155110077
Lam Kwong Wai	1155079229
Or Cheuk Yin	1155078796
*/

import java.sql.*;
import java.util.Scanner;

public class Main {

    public static Scanner scn = new Scanner(System.in);
    public static Connection con = null;
    public static Statement stmt = null;

    public static void main(String[] args) {
        String dbAddress = "jdbc:mysql://projgw.cse.cuhk.edu.hk:2633/db17";
        String dbUsername = "Group17";
        String dbPassword = "CSCI317017";

        //checking if the connection is successful
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(dbAddress, dbUsername, dbPassword);
            stmt = con.createStatement();
        }catch(ClassNotFoundException e){
            System.out.println("Java MySQL DB Driver not found!");
            System.exit(0);
        }catch (SQLException e){
            System.out.println(e);
        }

        System.out.println("Welcome to library inquery system!\n");
        displayMainMenu();


    }

    static void displayMainMenu() {
        System.out.println("-----Main Menu-----\n" +
                "What kinds of operation do you want to perform?\n" +
                "1.Operations for administrator\n" +
                "2.Operations for library user\n" +
                "3.Operations for librarian\n" +
                "4.Operations for library director\n" +
                "5.Exit the program");
        System.out.print("Enter your choice: ");
        boolean flag = false;
        int choice = 0;
        while (!flag) {
            try {
                choice = Integer.valueOf(scn.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid input");
                continue;
            }
            if (!(choice >= 1 && choice <= 5)) {
                System.out.println("Invalid input");
                continue;
            } else flag = true;

        }

        switch(choice) {
            case 1:
                new Admin();
                break;
            case 2:
                new LibUser();
                break;
            case 3:
                new Librarian();
                break;
            case 4:
                new LibDirector();
                break;
            case 5:
                return;

        }
    }


}

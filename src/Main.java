import jdk.nashorn.internal.ir.LexicalContext;
import jdk.nashorn.internal.ir.LexicalContextNode;

import java.sql.*;
import java.util.Scanner;

public class Main {

    static Scanner scn = new Scanner(System.in);

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
//            String sqlCreate = "CREATE table Category(cid tinyint primary key, max tinyint not null, period tinyint not null);";
//            String sqlCreate = "";
//            stmt.execute(sqlCreate);
        } catch (SQLException e) {
            System.out.println(e);
        }

        System.out.println("Welcome to library inquery system!\n");
        displayMainMenu();



    }

    static void displayMainMenu(){
        System.out.println("-----Main Menu-----\n"+
                "What kinds of operation do you want to perform?\n" +
                "1.Operations for administrator\n"+
                "2.Operations for library user\n"+
                "3.Operations for librarian\n"+
                "4.Operations for library director\n"+
                "5.Exit the program");
        boolean flag = false;
        int choice = 0;
        while(!(flag)) {
            try{
                choice = Integer.valueOf(scn.nextLine());
            } catch (Exception e){
                System.out.println("Invalid input");
                continue;
            }
            if (!(choice >=1 && choice<=5)) {
                System.out.println("Invalid input");
                continue;
            }else flag = true;

        }

        switch(choice) {
            case 1:
                admin();
                break;
            case 2:
                libUser();
                break;
            case 3:
                librarian();
                break;
            case 4:
                libDirector();
                break;
            case 5:
                return;

        }
    }

    private static void libDirector() {
    }

    private static void librarian() {
    }

    private static void libUser() {
    }

    private static void admin() {
        System.out.println("-----Operations for administrator menu-----\n"+
                "What kinds of operation do you want to perform?\n" +
                "1.Create all tables\n"+
                "2.Delete all tables\n"+
                "3.Load from datafile\n"+
                "4.Show number of records in each table\n"+
                "5.Return to the main menu");
        boolean flag = false;
        int choice = 0;
        while(!(flag)) {
            try{
                choice = Integer.valueOf(scn.nextLine());
            } catch (Exception e){
                System.out.println("Invalid input");
                continue;
            }
            if (!(choice >=1 && choice<=5)) {
                System.out.println("Invalid input");
                continue;
            }else flag = true;

        }

        switch(choice) {
            case 1:

                break;
            case 2:

                break;
            case 3:

                break;
            case 4:

                break;
            case 5:
                displayMainMenu();

        }
    }



}

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class LibUser {
    public LibUser() {
        boolean finish = false;
        while (!finish) {
            System.out.println("-----Operations for library user menu-----\n" +
                    "What kinds of operation do you want to perform?\n" +
                    "1.Search for books\n" +
                    "2.Show loan record of a user\n" +
                    "3.Return to the main menu");
            System.out.print("Enter your choice: ");
            boolean flag = false;
            int choice = 0;
            while (!flag) {
                try {
                    choice = Integer.valueOf(Main.scn.nextLine());
                } catch (Exception e) {
                    System.out.println("Invalid input");
                    continue;
                }
                if (!(choice >= 1 && choice <= 3)) {
                    System.out.println("Invalid input");
                    continue;
                } else flag = true;

            }

            switch (choice) {
                case 1:
                    System.out.print("Choose the search criterion:\n" +
                            "1. call number\n" +
                            "2. title\n" +
                            "3. author\n" +
                            "Choose the search criterion:");
                    int choice2 = Integer.valueOf(Main.scn.nextLine());
                    while (choice2 > 3 || choice2 < 1) {
                        System.out.println("Invalid input");
                        System.out.print("Choose the search criterion:");
                        choice2 = Integer.valueOf(Main.scn.nextLine());
                    }
                    System.out.print("Type in the Search Keyword: ");
                    String keyword = Main.scn.nextLine();
                    String sqlSearch = "SELECT b.callnum, b.title, a.aname,(copy.copynum- IFNULL(tmp.NR, 0)) AS avai FROM book AS b LEFT JOIN authorship AS a ON b.callnum=a.callnum left join copy on copy.callnum = b.callnum left join (select callnum, count(*) AS NR from borrow where `return` is NULL group by callnum) as tmp on tmp.callnum=b.callnum ";
                    if (choice2 == 1) {
                        sqlSearch = sqlSearch.concat(" WHERE b.callnum REGEXP '[[:<:]]" + keyword + "[[:>:]]' ;");
                    } else if (choice2 == 2) {
                        sqlSearch = sqlSearch.concat(" WHERE b.title REGEXP '[[:<:]]" + keyword + "[[:>:]]' ;");
                    } else if (choice2 == 3) {
                        sqlSearch = sqlSearch.concat(" WHERE a.aname REGEXP '[[:<:]]" + keyword + "[[:>:]]' ;");
                    }
                    try {
                        ResultSet resultSet = Main.stmt.executeQuery(sqlSearch);
                        ResultSetMetaData rsmd = resultSet.getMetaData();
                        int columnsNumber = rsmd.getColumnCount();
                        System.out.println("|Call Num|Title|Author|Available NO. of Copy|");
                        while (resultSet.next()) {
                            for (int i = 1; i <= columnsNumber; i++) {
                                System.out.print("|" + resultSet.getString(i));
                            }
                            System.out.println("|");
                        }
                        System.out.println("End of Query");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    System.out.print("Enter The User ID: ");
                    String uid = Main.scn.nextLine();
                    String sqlQuery = String.format("SELECT bw.callnum as CallNum, bw.copynum AS CopyNum, " +
                            "bk.title AS Title, a.aname AS Author, bw.checkout AS 'Check-out', " +
                            "IF(bw.`return` is not NULL, 'YES','NO') as 'Return?' FROM borrow as bw " +
                            "left join book as bk on bw.callnum = bk.callnum " +
                            "left join authorship as a on bw.callnum = a.callnum " +
                            "WHERE libuid like '%s' ORDER BY bw.checkout DESC;", uid);
                    try {
                        ResultSet resultSet = Main.stmt.executeQuery(sqlQuery);
                        ResultSetMetaData rsmd = resultSet.getMetaData();
                        int columnsNumber = rsmd.getColumnCount();
                        System.out.println("Loan Record:");
                        for (int i = 1; i <= columnsNumber; i++) {
                            System.out.print("|" + rsmd.getColumnLabel(i));
                        }
                        System.out.println("|");
                        while (resultSet.next()) {
                            for (int i = 1; i <= columnsNumber; i++) {
                                System.out.print("|" + resultSet.getString(i));
                            }
                            System.out.println("|");
                        }
                        System.out.println("End of Query");

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    break;
                case 3:
                    Main.displayMainMenu();
                    finish = true;
                    return;

            }
        }

    }
}

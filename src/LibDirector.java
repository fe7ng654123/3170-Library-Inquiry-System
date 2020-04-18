import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class LibDirector {
    public LibDirector() {
        boolean finish = false;
        while (!finish) {
            System.out.println("-----Operations for library director menu-----\n" +
                    "What kinds of operation do you want to perform?\n" +
                    "1.List all un-returned book copies which are checked-out within a period\n" +
                    "2.Return to the main menu");
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
                if (!(choice >= 1 && choice <= 2)) {
                    System.out.println("Invalid input");
                    continue;
                } else flag = true;

            }

            switch (choice) {
                case 1:
                    System.out.print("Type in the starting date [dd/mm/yyyy]: ");
                    String stDate = Main.scn.nextLine();
                    System.out.print("Type in the ending date [dd/mm/yyyy]: ");
                    String endDate = Main.scn.nextLine();
                    String sqlQuery = String.format("Select * from borrow WHERE `return` is null AND checkout BETWEEN STR_TO_DATE('%s', '%%d/%%m/%%Y') AND STR_TO_DATE('%s' , '%%d/%%m/%%Y');", stDate, endDate);
                    try {
                        ResultSet resultSet = Main.stmt.executeQuery(sqlQuery);
                        ResultSetMetaData rsmd = resultSet.getMetaData();
                        int columnsNumber = rsmd.getColumnCount();
                        System.out.println("List of UnReturned Book:");
                        for (int i = 1; i <= columnsNumber - 1; i++) {
                            System.out.print("|" + rsmd.getColumnName(i));
                        }
                        System.out.println("|");
                        while (resultSet.next()) {
                            for (int i = 1; i <= columnsNumber - 1; i++) {
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
                    Main.displayMainMenu();
                    finish = true;
                    return;

            }
        }
    }
}

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Librarian {
    public Librarian() {
        boolean finish = false;
        while (!finish) {
            System.out.println("-----Operations for librarian menu-----\n" +
                    "What kinds of operation do you want to perform?\n" +
                    "1.Book borrowing\n" +
                    "2.Book returning\n" +
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
                    System.out.print("Enter The User ID: ");
                    String uid = Main.scn.nextLine();
                    System.out.print("Enter The Call Number: ");
                    String callnum = Main.scn.nextLine();
                    System.out.print("Enter The Copy Number: ");
                    String copynum = Main.scn.nextLine();
                    String now = LocalDate.now().toString();
//                    System.out.println(now);
                    String sqlInsert = String.format("INSERT into borrow(libuid, callnum, copynum, checkout) VALUES ('%s','%s','%s','%s');", uid, callnum, copynum, now);
                    try {
                        Main.stmt.execute(sqlInsert);
                        System.out.println("Book borrowing performed successfully!!!");
                    } catch (SQLException e) {
                        e.printStackTrace();
                        System.out.println("Cannot borrow this book!");
                    }

                    break;
                case 2:
                    System.out.print("Enter The User ID: ");
                    uid = Main.scn.nextLine();
                    System.out.print("Enter The Call Number: ");
                    callnum = Main.scn.nextLine();
                    System.out.print("Enter The Copy Number: ");
                    copynum = Main.scn.nextLine();
                    now = LocalDate.now().toString();
//                    System.out.println(now);
                    sqlInsert = String.format("Update borrow SET `return` = '2020-04-18' where libuid like '%s' AND callnum like '%s' AND copynum like '%s' AND `return` is NULL;", uid, callnum, copynum);
                    try {
                        Main.stmt.execute(sqlInsert);
                        System.out.println("Book returning performed successfully!!!");
                    } catch (SQLException e) {
                        e.printStackTrace();
                        System.out.println("Cannot return this book!");
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

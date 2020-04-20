import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

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
                    try {
                        if (checkInput(uid, callnum, copynum)) {
                            String sqlInsert = String.format("INSERT into borrow(libuid, callnum, copynum, checkout) VALUES ('%s','%s','%s','%s');", uid, callnum, copynum, now);
                            Main.stmt.execute(sqlInsert);
                            System.out.println("Book borrowing performed successfully!!!");
                        } else {
                            System.out.println("This book is not available!");
                        }
                    } catch (SQLException e) {
                        System.out.println(e);
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
                    try {
                        if (!checkInput(uid, callnum, copynum)) {
                            String sqlUpdate = String.format("Update borrow SET `return` = '%s' where libuid like '%s' AND callnum like '%s' AND copynum like '%s' AND `return` is NULL;", now, uid, callnum, copynum);
                            Main.stmt.execute(sqlUpdate);
                            System.out.println("Book returning performed successfully!!!");
                        } else {
                            System.out.println("A matching borrow record is not found.");
                        }
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

    private boolean checkInput(String uid, String callnum, String copynum) {
        String sqlCheck = String.format("select count(*) from borrow where libuid like '%s' AND callnum like '%s' and copynum like '%s' AND `return` is NULL;", uid, callnum, copynum);
        try {
            ResultSet resultSet = Main.stmt.executeQuery(sqlCheck);
            resultSet.next();
            int count = Integer.valueOf(resultSet.getString(1));
//            System.out.println("count = " +resultSet.getString(1));
            if (count > 0) return false;
            else return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

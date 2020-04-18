import java.sql.ResultSet;
import java.sql.SQLException;


public class Admin {


    public Admin() {
        boolean finish = false;
        while (!finish) {
            System.out.println("-----Operations for administrator menu-----\n" +
                    "What kinds of operation do you want to perform?\n" +
                    "1.Create all tables\n" +
                    "2.Delete all tables\n" +
                    "3.Load from datafile\n" +
                    "4.Show number of records in each table\n" +
                    "5.Return to the main menu");
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
                if (!(choice >= 1 && choice <= 5)) {
                    System.out.println("Invalid input");
                    continue;
                } else flag = true;

            }

            switch (choice) {
                case 1:
                    String sqlCreate[] = new String[6];
                    sqlCreate[0] = "CREATE TABLE category(cid TINYINT NOT NULL, max TINYINT NOT NULL, period TINYINT NOT NULL, PRIMARY KEY (cid));";
                    sqlCreate[1] = "CREATE TABLE libuser(libuid CHAR(10) NOT NULL, name VARCHAR(25) NOT NULL, address VARCHAR(100) NOT NULL, cid TINYINT NOT NULL, PRIMARY KEY(libuid));";
                    sqlCreate[2] = "CREATE TABLE book(callnum CHAR(8) NOT NULL, title VARCHAR(30) NOT NULL, publish DATE NOT NULL, PRIMARY KEY(callnum));";
                    sqlCreate[3] = "CREATE TABLE copy(callnum CHAR(8) NOT NULL, copynum TINYINT NOT NULL, PRIMARY KEY(callnum, copynum), FOREIGN KEY(callnum) REFERENCES book(callnum));";
                    sqlCreate[4] = "CREATE TABLE borrow(libuid CHAR(10) NOT NULL, callnum CHAR(8) NOT NULL, copynum TINYINT NOT NULL, checkout DATE NOT NULL, `return` DATE, PRIMARY KEY(libuid, callnum, copynum, checkout), FOREIGN KEY(libuid) REFERENCES libuser(libuid), FOREIGN KEY(callnum) REFERENCES book(callnum));";
                    sqlCreate[5] = "CREATE TABLE authorship(aname VARCHAR(255) NOT NULL, callnum CHAR(8) NOT NULL, PRIMARY KEY(aname, callnum), FOREIGN KEY(callnum) REFERENCES book(callnum));";
                    try {
                        for (int i = 0; i < 6; i++) {
                            Main.stmt.execute(sqlCreate[i]);
                        }
                    } catch (SQLException e) {
                        System.out.println(e);
                    }
                    System.out.println("Created all tables!");
                    break;

                case 2:
                    String sqlDelete = "DROP TABLE IF EXISTS authorship, borrow, category, copy, book, libuser;";
                    try {
                        Main.stmt.execute(sqlDelete);
                    } catch (SQLException e) {
                        System.out.println(e);
                    }
                    System.out.println("Deleted all tables!");
                    break;
                case 3:
                    try {
                        String fileNames[] = new String[]{"category.txt", "book.txt", "book.txt", "book.txt", "user.txt", "check_out.txt"};
                        String tables[] = new String[]{"category", "book", "authorship", "copy", "libuser", "borrow"};
                        String attributes[] = new String[]{"cid, max, period", //category
                                "callnum, @dummy,title, @dummy, @publish",    //book
                                "callnum, @dummy, @dummy, aname, @dummy",   //authorship
                                "callnum, copynum, @dummy, @dummy, @dummy", //copy
                                "libuid, name, address, cid",    //libuser
                                "callnum, copynum, libuid, @checkout, @ret",  //borrow
                        };
                        for (int i = 0; i < 6; i++) {
                            String filePath = String.format("'%s/%s'", System.getProperty("user.dir"), fileNames[i]);
                            filePath = filePath.replace('\\', '/');
                            String sqlLoad = String.format("LOAD DATA LOCAL INFILE %s INTO TABLE %s FIELDS TERMINATED BY '\t' LINES TERMINATED BY '\n' (%s)",
                                    filePath, tables[i], attributes[i]);
                            if (i == 1) sqlLoad = sqlLoad.concat(" SET publish = STR_TO_DATE(@publish,'%d/%m/%Y')");
                            if (i == 5)
                                sqlLoad = sqlLoad.concat(" SET checkout = STR_TO_DATE(@checkout,'%d/%m/%Y') , `return` = STR_TO_DATE(@ret,'%d/%m/%Y')");
//                            System.out.println(sqlLoad);
                            Main.stmt.execute(sqlLoad);
                        }


                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    break;

                case 4:
                    String sqlRecordNum = "SELECT \"authorship\" AS table_name, COUNT(*) AS exact_row_count FROM `db17`.`authorship` UNION SELECT \"book\" AS table_name, COUNT(*) AS exact_row_count FROM `db17`.`book` UNION SELECT \"borrow\" AS table_name, COUNT(*) AS exact_row_count FROM `db17`.`borrow` UNION SELECT \"category\" AS table_name, COUNT(*) AS exact_row_count FROM `db17`.`category` UNION SELECT \"copy\" AS table_name, COUNT(*) AS exact_row_count FROM `db17`.`copy` UNION SELECT \"libuser\" AS table_name, COUNT(*) AS exact_row_count FROM `db17`.`libuser`;";
                    try {
                        ResultSet resultSet = Main.stmt.executeQuery(sqlRecordNum);
                        if (!resultSet.isBeforeFirst()) {
                            System.out.println("table is empty");
                        } else {
                            while (resultSet.next()) {
                                System.out.println(resultSet.getString(1) + ": " + resultSet.getString(2));
                            }
                        }
                    } catch (SQLException e) {
//                        System.out.println(e);
                        System.out.println("You have not yet created the tables");
                    }

                    break;

                case 5:
                    Main.displayMainMenu();
                    finish = true;
                    return;
            }
        }
    }
}

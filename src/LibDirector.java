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
                    break;

                case 2:
                    Main.displayMainMenu();
                    finish = true;
                    return;

            }
        }
    }
}

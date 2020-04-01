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
                    break;
                case 2:
                    break;
                case 3:
                    Main.displayMainMenu();
                    finish = true;
                    return;

            }
        }

    }
}

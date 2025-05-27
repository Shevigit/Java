
import Business.InquiryManager;
import Business.RepresentativeManagement;
import ClientServer.InquiryManagerServer;
import java.net.ServerSocket;
import java.util.Scanner;



public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        int key = 0;

        while (key != 3) {
            System.out.println("To add/delete a representative, press 1.");
            System.out.println("To activate the system, press 2.");
            System.out.println("To exit, press 3.");
            System.out.print("Your choice: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input! Please enter a number.");
                scanner.next();
                continue;
            }

            key = scanner.nextInt();

            switch (key) {
                case 1: {
                    RepresentativeManagement r = new RepresentativeManagement();
                    r.start();
                    r.join(); // נמתין עד לסיום
                    break;
                }

                case 2: {
                    ServerSocket serverSocket = new ServerSocket(9000);
                    InquiryManagerServer inquiryManagerServer = new InquiryManagerServer(serverSocket);
                    Thread t1 = new Thread(inquiryManagerServer);
                    t1.start();

                    InquiryManager inquiryManager = InquiryManager.getInstance();
                    Thread t2 = new Thread(inquiryManager);
                    t2.start();

                    // המתנה עד ששני התהליכים יסתיימו
                    try {
                        t1.join();
                        t2.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    break;
                }

                case 3: {
                    System.out.println("System shutting down...");
                    break;
                }

                default: {
                    System.out.println("Wrong keystroke! Please press 1, 2 or 3.");
                }
            }
        }

        scanner.close();
    }
}

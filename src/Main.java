
import Business.InquiryManager;
import Business.RepresentativeManagement;
import ClientServer.InquiryManagerServer;
import java.net.ServerSocket;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner=new Scanner(System.in);
        System.out.println("To add/delete a representative, press 1. To activate the system, press 2");
        int key=scanner.nextInt();
            while (key!=3){
            switch (key) {
                case 1: {
                    RepresentativeManagement r = new RepresentativeManagement();
                    r.start();
                    r.join();
                    break;
                }
                case 2: {

                    ServerSocket serverSocket = new ServerSocket(9000);
                    InquiryManagerServer inquiryManagerServer = new InquiryManagerServer(serverSocket);
                    Thread t1=new Thread(inquiryManagerServer);
                    t1.start();
                    InquiryManager inquiryManager=InquiryManager.getInstance();
                    Thread t2=new Thread(inquiryManager);
                    inquiryManagerServer.join();
                    break;
                }
                default:{
                    System.out.println("Wrong keystroke!!To add/delete a representative, press 1. To activate the system, press 2");
                    key=scanner.nextInt();
                    break;
                }
            }
                System.out.println("To add/delete a representative, press 1. To activate the system, press 2");
                key=scanner.nextInt();
            }

    }
}

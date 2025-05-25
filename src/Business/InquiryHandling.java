package Business;

import Data.Complaint;
import Data.Inquiry;
import Data.Question;
import Data.Request;

import java.util.Scanner;

public class InquiryHandling extends Thread{
    private Inquiry currentInquiry;

@Deprecated
    public  void createInquiry()
    {
        System.out.println("enter:");
        System.out.println("\t 1 to question");
        System.out.println("\t 2 to request");
        System.out.println("\t 3 to complaint");
        int digit=0;

            Scanner scan=new Scanner(System.in);
            digit=scan.nextInt();
            switch (digit){
                case 1:
                    currentInquiry=new Question();

                    break;
                case 2:
                    currentInquiry=new Request();

                    break;
                case 3:
                    currentInquiry=new Complaint();

                    break;
                default:

                    break;
            }

        currentInquiry.fillDataByUser();

    }
    @Override
    public void run() {
//try{
    currentInquiry.handling();
//    sleep(5*1000);
//}
//    catch (InterruptedException e){
//    e.printStackTrace();
//    }

    }
}

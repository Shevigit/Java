package Business;

import Data.Complaint;
import Data.Inquiry;
import Data.Question;
import Data.Request;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class InquiryManager {
    Queue<Inquiry> q=new LinkedList<Inquiry>();
    private Inquiry currentInquiry;

    public  void createInquiry(int digit)
    {

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

    }

    public void inquiryCreation (){

        int digit=0;
        System.out.println("enter:");
        System.out.println("\t 1 to question");
        System.out.println("\t 2 to request");
        System.out.println("\t 3 to complaint");
        System.out.println("\t 4 to exit");
        Scanner scan=new Scanner(System.in);
        digit=scan.nextInt();
        while (digit>0&&digit<4)
        {
        createInquiry(digit);
       currentInquiry.fillDataByUser();
        q.add(currentInquiry);
            System.out.println("enter:");
            System.out.println("\t 1 to question");
            System.out.println("\t 2 to request");
            System.out.println("\t 3 to complaint");
            System.out.println("\t 4 to exit");
             scan=new Scanner(System.in);
            digit=scan.nextInt();

        }

    }
        public  void  processInquiryManager() {
            if (q != null) {
                while (!q.isEmpty()) {
                 currentInquiry= q.poll();
                 currentInquiry.handling();
                }
            }
        }


}

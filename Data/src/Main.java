import Business.InquiryHandling;
import Business.InquiryManager;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

//        InquiryHandling handling1 = new InquiryHandling();
//        Thread handling2 = new InquiryHandling();
//        InquiryHandling handling3 = new InquiryHandling();
//        InquiryHandling handling4 = new InquiryHandling();
//        handling1. createInquiry();
//        ((InquiryHandling) handling2).createInquiry();
//        handling3.createInquiry();
//        handling4.createInquiry();
//	    handling1.start();
//		handling2.start();
//	    handling3.start();
//         handling4.start();


//        handling1.run();
//        handling2.run();
//        handling3.run();
//        handling4.run();


        InquiryManager inquiryManager = new InquiryManager();
        inquiryManager.inquiryCreation();
        inquiryManager.processInquiryManager();


    }
}
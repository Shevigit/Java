package Business;

import Data.Complaint;
import Data.Inquiry;
import Data.Question;
import Data.Request;
import HandleStoreFiles.HandleFiles;

import java.awt.print.PrinterIOException;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class InquiryManager {
    private final  static  Queue<Inquiry> q;
     private  static Integer nextCodeVal = 0;

    private InquiryManager() {}

    private  static  InquiryManager instance;
    public static InquiryManager getInstance() {
        if(instance==null)
        {
            instance=new InquiryManager();
        }
        return instance;
    }
    public static void setNextCodeVal(Integer nextCodeVal) {
        InquiryManager.nextCodeVal = nextCodeVal;
    }

    public static Integer getNextCodeVal() {
        return nextCodeVal;
    }

    private Inquiry currentInquiry;
    HandleFiles handleFiles=new HandleFiles();

    public void loadInquiriesFromDirectory(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.exists() || !directory.isDirectory()) {
            throw new IllegalArgumentException("The specified path is not a directory: " + directoryPath);
        }

        File[] files = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));
        if (files != null) {

            for (File file : files) {
                try {
                    setNextCodeVal(nextCodeVal+1);
                   Inquiry inquiry = (Inquiry) HandleFiles.readFromFile(file.getAbsolutePath());
                    System.out.println(nextCodeVal);
                    if (inquiry != null) {
                        q.add(inquiry);
                    }
                } catch (Exception e) {
                    System.err.println("Error reading file: " + file.getName() + " -> " + e.getMessage());
                }
            }
        }
    }

        static {
        q = new LinkedList<Inquiry>();

        try {
            InquiryManager inquiryManager = new InquiryManager();
            File directory = new File("M:\\practicum_properation\\inquirymanagement_bsg\\Question");
            if (directory.exists() || directory.isDirectory()) {
                inquiryManager.loadInquiriesFromDirectory("M:\\practicum_properation\\inquirymanagement_bsg\\Question");
            }
            directory = new File("M:\\practicum_properation\\inquirymanagement_bsg\\Request");
            if (directory.exists() || directory.isDirectory()) {
                inquiryManager.loadInquiriesFromDirectory("M:\\practicum_properation\\inquirymanagement_bsg\\Request");
            }
            directory = new File("M:\\practicum_properation\\inquirymanagement_bsg\\Complaint");
            if (directory.exists() || directory.isDirectory()) {
                inquiryManager.loadInquiriesFromDirectory("M:\\practicum_properation\\inquirymanagement_bsg\\Complaint");
            }
            System.out.printf("succeeded: " + q.isEmpty()+"\n");
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while reading the files", e);
        }
    }


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

    public void inquiryCreation () throws IOException {

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

            setNextCodeVal(nextCodeVal+1);
            currentInquiry.fillDataByUser();
            System.out.println(nextCodeVal);
            q.add(currentInquiry);
            handleFiles.saveFile(currentInquiry);
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

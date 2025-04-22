package Business;

import Data.*;
import HandleStoreFiles.HandleFiles;
import java.io.File;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class InquiryManager {
    private final  static  Queue<Inquiry> q;
    private  static Integer nextCodeVal = 0;
    private final static Queue<Representative> representativeList;
    private  static Integer nextCodeWorker = 100;

    private InquiryManager() {}

    public static Integer getNextCodeWorker() {
        return nextCodeWorker;
    }

    public static void setNextCodeWorker(Integer nextCodeWorker) {
        InquiryManager.nextCodeWorker = nextCodeWorker;
    }

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

    static {
        q = new LinkedList<Inquiry>();
        representativeList = new LinkedList<Representative>();
        String[] folders = {"Question", "Request", "Complaint"};
        InquiryManager inquiryManager = new InquiryManager();
        try {
            for (String folderName : folders) {

                File directory = new File(folderName);
                if (directory.exists() || directory.isDirectory()) {
                    inquiryManager.loadInquiriesFromDirectory(folderName);
                }
            }
            File directory = new File("Representative");
            if (directory.exists() || directory.isDirectory()) {
                inquiryManager.loadRepresentativeFromDirectory("Representative");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.printf("succeeded: " + q.isEmpty() + "\n");

    }


    public  void defineRepresentative(){
        System.out.println("To Add representative enter 1! To exit enter 0!");
        Scanner scan = new Scanner(System.in);
        int key=scan.nextInt();
        if(key==1) {
            System.out.println("Enter representative name");
            Scanner scan2 = new Scanner(System.in);
            String name = scan2.nextLine();
            System.out.println("Enter representative identity");
            String identity=scan2.nextLine();
            while (key == 1) {
                try{
                    define(name,identity);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                System.out.println("To Add representative enter 1! To exit enter 0!");
                key = scan.nextInt();
                if (key == 1) {
                    System.out.println("Enter representative name");
                    name = scan2.nextLine();
                    System.out.println("Enter representative identity");
                    identity=scan2.nextLine();
                }

            }
        }
    }

    public void define(String representativeName,String identity){
        try{
            setNextCodeWorker(nextCodeWorker+1);
            Representative representative=new Representative(representativeName,identity);
            handleFiles.saveCSV(representative,representative.getFolderName()+"\\"+representative.getFileName());
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void loadInquiriesFromDirectory(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.exists() || !directory.isDirectory()) {
            throw new IllegalArgumentException("The specified path is not a directory: " + directoryPath);
        }
        File[] files = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".csv"));
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

    public void loadRepresentativeFromDirectory(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.exists() || !directory.isDirectory()) {
            throw new IllegalArgumentException("The specified path is not a directory: " + directoryPath);
        }
        File[] files = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".csv"));
        if (files != null) {

            for (File file : files) {
                try {
                    setNextCodeWorker(nextCodeWorker+1);
                    Representative representative = (Representative) HandleFiles.readFromFile(file.getAbsolutePath());
                    System.out.println("Representative" + nextCodeWorker);
                    if (representative != null) {
                        representativeList.add(representative);
                    }
                } catch (Exception e) {
                    System.err.println("Error reading file: " + file.getName() + " -> " + e.getMessage());
                }
            }
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

    public void inquiryCreation ()   {

        int digit=0;
        System.out.println("enter:");
        System.out.println("\t 1 to question");
        System.out.println("\t 2 to request");
        System.out.println("\t 3 to complaint");
        System.out.println("\t 4 to exit");
        Scanner scan=new Scanner(System.in);
        digit=scan.nextInt();
        try{
            while (digit>0&&digit<4)
            {
                createInquiry(digit);

                setNextCodeVal(nextCodeVal+1);
                currentInquiry.fillDataByUser();
                System.out.println(nextCodeVal);
                q.add(currentInquiry);
                handleFiles.saveCSV(currentInquiry,currentInquiry.getFolderName()+"\\"+currentInquiry.getFileName());
                System.out.println("enter:");
                System.out.println("\t 1 to question");
                System.out.println("\t 2 to request");
                System.out.println("\t 3 to complaint");
                System.out.println("\t 4 to exit");
                scan=new Scanner(System.in);
                digit=scan.nextInt();

            }
        }
        catch (Exception e){
            e.printStackTrace();
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

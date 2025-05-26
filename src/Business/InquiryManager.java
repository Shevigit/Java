package Business;

import Data.*;
import HandleStoreFiles.HandleFiles;
import java.io.File;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import Exception.InquiryException;
import Exception.InquiryRunTimeException;

public class InquiryManager {
    public final  static  Queue<Inquiry> q;
    private  static Integer nextCodeVal = 0;
    public   static Queue<Representative> representativeList;
    private  static Integer nextCodeWorker = 100;

    private InquiryManager() {}
    public  static Queue<Representative>getRepresentativeList(){
        return representativeList;
    }
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
            System.out.printf("succeeded: " + q.isEmpty() + "\n");
        }
        catch (InquiryRunTimeException e) {
            throw new InquiryRunTimeException(nextCodeVal);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void loadInquiriesFromDirectory(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.exists() || !directory.isDirectory()) {
            throw new IllegalArgumentException("The specified path is not a directory: " + directoryPath);
        }
        File [] files= directory.listFiles();
        if (files != null) {

            for (File file : files) {
                try {
                    setNextCodeVal(nextCodeVal+1);
                    Inquiry inquiry = (Inquiry) HandleFiles.readFromFile(file.getAbsolutePath());
                    //   System.out.println(nextCodeVal);
                    if (inquiry != null) {
                        q.add(inquiry);
                    }
                    if(file.getName().toLowerCase().endsWith(".txt")){
                        throw new InquiryException(Integer.parseInt(file.getName().substring(1, file.getName().indexOf('.'))));
                    }

                }

                catch (Exception e) {
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
                    //  System.out.println("Representative" + nextCodeWorker);
                    if (representative != null) {
                        representativeList.add(representative);
                    }
                } catch (InquiryRunTimeException e) {
                    e.printStackTrace();
                }
                catch (Exception e){
                    System.err.println("Error reading file: " + file.getName() + " -> " + e.getMessage());
                }
            }
        }
    }

//
//    public  void createInquiry(int digit)
//    {
//
//        switch (digit){
//            case 1:
//                currentInquiry=new Question();
//                break;
//            case 2:
//                currentInquiry=new Request();
//                break;
//            case 3:
//                currentInquiry=new Complaint();
//                break;
//            default:
//                break;
//        }
//
//    }
//
//    public void inquiryCreation ()   {
//
//        int digit=0;
//        System.out.println("enter:");
//        System.out.println("\t 1 to question");
//        System.out.println("\t 2 to request");
//        System.out.println("\t 3 to complaint");
//        System.out.println("\t 4 to exit");
//        Scanner scan=new Scanner(System.in);
//        digit=scan.nextInt();
//        try{
//            while (digit>0&&digit<4)
//            {
//                createInquiry(digit);
//
//                setNextCodeVal(nextCodeVal+1);
//                currentInquiry.fillDataByUser();
//                System.out.println(nextCodeVal);
//                q.add(currentInquiry);

    //                handleFiles.saveCSV(currentInquiry,currentInquiry.getFolderName()+"\\"+currentInquiry.getFileName());
//                System.out.println("enter:");
//                System.out.println("\t 1 to question");
//                System.out.println("\t 2 to request");
//                System.out.println("\t 3 to complaint");
//                System.out.println("\t 4 to exit");
//                scan=new Scanner(System.in);
//                digit=scan.nextInt();
//
//            }
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
//
//
//    }
    public  void  processInquiryManager() {
        if (q != null) {
            while (!q.isEmpty()) {
                currentInquiry= q.poll();
                currentInquiry.handling();
            }
        }  
        
    }


// Saves inquiry to CSV with assigned representative and initial "open" status.
//public Inquiry registerInquiry(List<Object> params) throws IOException {
  //  if (params != null && !params.isEmpty() && params.get(0) instanceof Inquiry) {
    //    Inquiry inquiry = (Inquiry) params.get(0);
    //    setNextCodeVal(nextCodeVal + 1);
    //    inquiry.setCode(getNextCodeVal());
    //    allInquiry.add(inquiry);
    //    System.out.println(InquiryManager.allInquiry);
     //   String pathFile = handleFiles.getDirectoryPath(inquiry);
     //   String representativeCode = inquiryToRepresetative(inquiry);
      //  inquiry.setRepresentativeCode(representativeCode);
      //  inquiry.setStatusInquiry(StatusInquiry.OPEN);
    //    handleFiles.saveCSV(inquiry, pathFile);
    //    return inquiry;
   // }
  //  return null;
//}

// connect inquiry to an available representative.
//public String inquiryToRepresetative(Inquiry inquiry) {
 //   Representative representative = avilableRepresentative.poll();
 //   if (representative != null) {
 //       requestToAgentMap.put(representative, inquiry);
  //      return representative.getCode();
  //  } else {
  //      System.out.println("No representatives are currently available.");
   //     return null;
   // }
//}

}

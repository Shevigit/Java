package Business;

import Data.Representative;
import HandleStoreFiles.HandleFiles;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class RepresentativeManagement extends Thread{
    HandleFiles handleFiles=new HandleFiles();

@Override
    public void run(){
    int key=1,codeWorker;
    Scanner scan = new Scanner(System.in);
    while (key==1||key==2) {
        System.out.println("Press 1 to add a representative, press 2 to delete a representative and press 3 to exit.");
        key = scan.nextInt();
        switch (key){
            case 1:
                addRepresentative();
                break;
            case 2:{
                System.out.println("enter kodWorker to delete");
                codeWorker=scan.nextInt();
                deleteRepresentative(codeWorker);
            }
            case 3:return;
            default:
                System.out.println("Wrong keystroke.");
        }
    }
}
    public  void addRepresentative(){
             Scanner scan2 = new Scanner(System.in);
            System.out.println("Enter representative name");

            String name = scan2.nextLine();
            System.out.println("Enter representative identity");
            String identity=scan2.nextLine();
             System.out.println("Enter if you Associated with inquiries");
              boolean isAssociated=scan2.nextBoolean();
                try{
                    define(name,identity,isAssociated);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
    }

    public void define(String representativeName,String identity,boolean isAssociated){
        try{
           InquiryManager.setNextCodeWorker(InquiryManager.getNextCodeWorker()+1);
            Representative representative=new Representative(representativeName,identity,isAssociated);
            InquiryManager.representativeList.add(representative);//הוספה לתור הנציגים
            if(isAssociated==true){
                InquiryManager.availableRepresntative.add(representative);//במידה והוא מטפל בפניות הוספה לתור הנציגים הפנויים
            }
            handleFiles.saveCSV(representative,representative.getFolderName()+"\\"+representative.getFileName());
            System.out.println("The representative was successfully added!!");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    public void deleteRepresentative(Integer codeWorker) {
        Queue<Representative> queue = InquiryManager.getRepresentativeList();
        Queue<Representative>temp=new LinkedList<Representative>();
        while (!queue.isEmpty()) {
            Representative r=queue.remove();
            if (r.getCodeWorker()==codeWorker) {
                try {
                    boolean isDelete=handleFiles.deleteFile(r);
                    System.out.println("the Representetive deleted: "+isDelete);
                }
                catch (IOException ex){
                    ex.printStackTrace();
                }
                break;
            }
            else {
                temp.add(r);
            }
        }
        InquiryManager.representativeList=temp;
    }

}

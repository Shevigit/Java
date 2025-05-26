package Data;

import HandleStoreFiles.IForSaving;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.Scanner;

public class Complaint extends Inquiry  {
    private  String assignedBranch;

    public String getAssignedBranch() {
        return assignedBranch;
    }



    public void setAssignedBranch(String assignedBranch) {
        this.assignedBranch = assignedBranch;
    }

    @Override
    public String getFolderName() {
        return "Complaint";
    }

    @Override
    public  void fillDataByUser() {
        super.fillDataByUser();
        System.out.println("enter AssignedBranch:");
        Scanner scan=new Scanner(System.in);
        setAssignedBranch(scan.nextLine());
    }

    @Override
    public void handling()
    {
        Random rand = new Random();
        int estimationTime = rand.nextInt(21)+20;


        System.out.print("The system treat in the complaint inquiry number "+getCode()+" "+ estimationTime+"is the estimationTime " + "\n");
        try{
            if(Thread.currentThread().activeCount()>=10)
                Thread.currentThread().yield();
            Thread.currentThread().sleep(estimationTime*1000);
        }
        catch (InterruptedException e)
        {e.printStackTrace();}

    }
}

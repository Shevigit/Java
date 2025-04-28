package Data;

import java.util.Random;

public class Question extends  Inquiry{
    @Override
    public String getFolderName() {
        return "Question";
    }

    @Override
    public void handling()
    {
        Random rand = new Random();
        int estimationTime = rand.nextInt(6);
        System.out.print("The system treat in the question inquiry number "+getCode()+" "+ estimationTime+"is the estimationTime " + "\n");
        try{

            Thread.currentThread().setPriority(1);
            Thread.currentThread().sleep(estimationTime*1000);
        }
        catch (InterruptedException e)
        {e.printStackTrace();}
    }
}


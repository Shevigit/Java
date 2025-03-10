package Data;


import java.util.Random;

public class Request extends Inquiry{

    @Override
    public void handling()
    {
        Random rand = new Random();
        int estimationTime = rand.nextInt(6)+10;
        System.out.print("The system treat in the request inquiry number "+getCode()+" "+ estimationTime+"is the estimationTime " + "\n");
        try{
            if(Thread.currentThread().activeCount()>=10)
                Thread.currentThread().yield();

            Thread.currentThread().sleep(estimationTime*1000);
        }
        catch (InterruptedException e)
        {e.printStackTrace();}
    }
}

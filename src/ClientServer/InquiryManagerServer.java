package ClientServer;

import java.io.IOException;
import java.net.ServerSocket;

public class InquiryManagerServer extends Thread{
    ServerSocket myServer;

    public InquiryManagerServer(ServerSocket myServer) {
        this.myServer = myServer;
    }

    public  void stopServer(){
        try{
            this.myServer.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void run() {
        try{
            HandleClient handleClient;
            while (true){
               handleClient=new HandleClient(myServer.accept());                System.out.println("connected...");
                handleClient.start();
            }

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}

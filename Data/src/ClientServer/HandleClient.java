package ClientServer;

import Business.InquiryManager;
import HandleStoreFiles.HandleFiles;
import Data.Inquiry;
//import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class HandleClient extends Thread  {
    Socket clientSocket;
    HandleFiles handleFiles = new HandleFiles();

    /// /???
    public HandleClient(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public synchronized void run() {
        try {
            while(true){
                handleClientRequest();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleClientRequest() {
        try {
      

            ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
            RequestData requestData = (RequestData) objectInputStream.readObject();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            if (requestData.action == InquiryManagerActions.ALL_INQUIRY) {
                List list = new ArrayList<Inquiry>();
                Queue<Inquiry> tempQueue = new LinkedList<>(InquiryManager.q);
                while (!tempQueue.isEmpty()) {
                    Inquiry data = tempQueue.poll();
                    list.add(data);
                    InquiryManager.q.add(data);
                }
                objectOutputStream.writeObject(new ResponseData("The inquiries received succesfully", ResponseStatus.SCCESS, list));
            } else {
                if (requestData.action == InquiryManagerActions.ADD_INQUIRY) {
                    if (requestData.parameters != null) {
                        ArrayList<Inquiry> listInq=(ArrayList<Inquiry>) requestData.parameters;
                        for (Object parameter :listInq) {
                            Inquiry inq = (Inquiry) parameter;
                            InquiryManager.q.add(inq);
                            InquiryManager.setNextCodeVal(InquiryManager.getNextCodeVal() + 1);
                           inq.setCode(InquiryManager.getNextCodeVal());
         //                   InquiryManager.setNextCodeVal(InquiryManager.getNextCodeVal() + 1);
                            try {
//                                registerInquiry();
                                handleFiles.saveCSV((Inquiry) parameter, inq.getFolderName() + "\\" + inq.getFileName());

                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        }
                        objectOutputStream.writeObject(new ResponseData("The inquiry received succesfully", ResponseStatus.SCCESS));
                        objectOutputStream.flush();
                    }
                }
                else{
                    if(requestData.action == InquiryManagerActions.GET_COUNTINQUIRY){
                        int digit=GetCountInquiriesOfMonth((int)requestData.parameters);
                        objectOutputStream.writeObject(new ResponseData("The inquiries received succesfully", ResponseStatus.SCCESS, digit));
                    }
                }
            }
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public int GetCountInquiriesOfMonth(int numOfMonth){
        int count=0;
//        List list = new ArrayList<Inquiry>();
        Queue<Inquiry> tempQueue = new LinkedList<>(InquiryManager.q);
        while(!tempQueue.isEmpty()){
            Inquiry inquiry=  tempQueue.poll();
            if(inquiry.getCreationDate().getMonthValue()==numOfMonth){
                count++;
            }
//                list.add(inquiry);
            InquiryManager.q.add(inquiry);
        }
        return count;
    }
}

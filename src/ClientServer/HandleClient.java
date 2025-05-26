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

public class HandleClient extends Thread{
private Socket clientSocket ;
HandleFiles handleFiles = new HandleFiles();
ResponseData response = null ;
Inquiry inquiry = null ;
InquiryManager   inquiryManager = InquiryManager.getInstance ();

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
        switch (requestData.action) {
            case InquiryManagerActions.ALL_INQUIRY: {
                List list = new ArrayList<Inquiry>();
                Queue<Inquiry> tempQueue = new LinkedList<>(InquiryManager.q);
                while (!tempQueue.isEmpty()) {
                    Inquiry data = tempQueue.poll();
                    list.add(data);
                    InquiryManager.q.add(data);
                }
                objectOutputStream.writeObject(new ResponseData("The inquiries received succesfully", ResponseStatus.SCCESS, list));
                break;
            }
            case InquiryManagerActions.ADD_INQUIRY: {
                ArrayList<Inquiry> listInq = (ArrayList<Inquiry>) requestData.parameters;
                for (Object parameter : listInq) {
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
            case InquiryManagerActions.GET_COUNTINQUIRY: {
                int digit = GetCountInquiriesOfMonth((int) requestData.getDigit());
                objectOutputStream.writeObject(new ResponseData("The inquiries received succesfully", ResponseStatus.SCCESS, digit));
                objectOutputStream.flush();
            }
            case InquiryManagerActions.DELETE_INQUIRY: {
                int digit=(int)requestData.getDigit();
                boolean flag = false;
                Queue<Inquiry> temp = new LinkedList<Inquiry>();
                for (Inquiry inquiry : InquiryManager.q) {
                    if (inquiry.getCode() == digit) {
                        try {
                            handleFiles.deleteFile(inquiry);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        flag = true;
                        inquiry.setStatus(StatusInquiry.CANCEL);
                    } else {
                        temp.add(inquiry);
                    }
                }
                inquiryManager.q = temp;
                if (flag)
                    objectOutputStream.writeObject(new ResponseData(" Inquirie code  deleted succesfully", ResponseStatus.SCCESS));
                else
                    objectOutputStream.writeObject(new ResponseData("The Inquirie is not exist", ResponseStatus.FAIL));
                objectOutputStream.flush();
                break;
            }
//            case GET_REPRESENTATIVE: {
//
//                if (requestData.parameters != null && !requestData.parameters.isEmpty()) {
//                    Object params = requestData.parameters.get(0);
//
//                    String name = InquiryManager.getNameRepresentativeByInquiry((Integer) params);
//                    ResponseData response = new ResponseData("שליפת הנציג הצליחה ", ResponseStatus.SCCESS);
//                } else {
//                    ResponseData response = new ResponseData(ResponseStatus.FAIL, "שליפת הנציג נכשלה", null);
//                }
//            }

            case GET_STATUS:
                break;
            case GET_REPRESENTATIVE:
                break;
            default:
                break;
        }
    }

//                case GET_STATUS:{
//                    StatusInquiry status = null;
//                    for (Inquiry inquiry : InquiryManager.q) {
//
//                        if (inquiry.getCode() == requestData.getDigit()) {
//                            try {
//                                status = inquiry.getStatus();
//
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                            flag = true;
//                            inquiry.setStatus(StatusInquiry.CANCEL);
//
//                        }
//                    }
//                }
//
//            }


    catch (IOException e) {
        e.printStackTrace();

    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    }
}

    public int GetCountInquiriesOfMonth(int numOfMonth){
        int count=0;
        Queue<Inquiry> tempQueue = new LinkedList<>(InquiryManager.q);
        while(!tempQueue.isEmpty()){
            Inquiry inquiry=  tempQueue.poll();
            if(inquiry.getCreationDate().getMonthValue()==numOfMonth){
                count++;
            }
            InquiryManager.q.add(inquiry);
        }
        return count;
    }
}


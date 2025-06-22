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



public class HandleClient extends Thread {
    private Socket clientSocket;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;

    HandleFiles handleFiles = new HandleFiles();
    InquiryManager inquiryManager = InquiryManager.getInstance();

    public HandleClient(Socket clientSocket) {
        this.clientSocket = clientSocket;
        try {
            this.objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            this.objectOutputStream.flush(); // חובה לפני פתיחת input
            this.objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                handleClientRequest();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                objectInputStream.close();
                objectOutputStream.close();
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void handleClientRequest() throws IOException {
        try {
            RequestData requestData = (RequestData) objectInputStream.readObject();
            switch (requestData.action) {
                case ALL_INQUIRY: {
                    List<Inquiry> list = new ArrayList<>();
                    Queue<Inquiry> tempQueue = new LinkedList<>(InquiryManager.q);
                    while (!tempQueue.isEmpty()) {
                        Inquiry data = tempQueue.poll();
                        list.add(data);
                        InquiryManager.q.add(data);
                    }
                    objectOutputStream.writeObject(new ResponseData("The inquiries received succesfully", ResponseStatus.SCCESS, list));
                    objectOutputStream.flush();
                    break;
                }

                case ADD_INQUIRY: {
                    ArrayList<Inquiry> listInq = (ArrayList<Inquiry>) requestData.parameters;
                    for (Object parameter : listInq) {
                        Inquiry inq = (Inquiry) parameter;
                        InquiryManager.q.add(inq);
                        InquiryManager.setNextCodeVal(InquiryManager.getNextCodeVal() + 1);
                        inq.setCode(InquiryManager.getNextCodeVal());

                        try {
                            handleFiles.saveCSV(inq, inq.getFolderName() + "\\" + inq.getFileName());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    objectOutputStream.writeObject(new ResponseData("The inquiry received succesfully", ResponseStatus.SCCESS));
                    objectOutputStream.flush();
                    break;
                }

                case GET_COUNTINQUIRY: {
                    int digit = GetCountInquiriesOfMonth((int) requestData.getDigit());
                    objectOutputStream.writeObject(new ResponseData("The inquiries received succesfully", ResponseStatus.SCCESS, digit));
                    objectOutputStream.flush();
                    break;
                }

                case DELETE_INQUIRY: {
                    int digit = (int) requestData.getDigit();
                    boolean flag = false;
                    Queue<Inquiry> temp = new LinkedList<>();

                    for (Inquiry inquiry : InquiryManager.q) {
                        if (inquiry.getCode() == digit) {
                            try {
                                handleFiles.deleteFile(inquiry);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            flag = true;
                            inquiry.setStatus(StatusInquiry.CANCEL);
                            try{
                                handleFiles.saveCSV(inquiry, "History" + "\\" + inquiry.getFileName());
                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }
                        } else {
                            temp.add(inquiry);
                        }
                    }
                    inquiryManager.q = temp;

                    if (flag) {
                        objectOutputStream.writeObject(new ResponseData("Inquiry deleted successfully", ResponseStatus.SCCESS));
                    } else {
                        objectOutputStream.writeObject(new ResponseData("Inquiry does not exist", ResponseStatus.FAIL));
                    }
                    objectOutputStream.flush();
                    break;
                }

                case GET_STATUS:
                case GET_REPRESENTATIVE:
                default:
                    break;
            }

        } catch (IOException | ClassNotFoundException e) {
            objectInputStream.close();
            objectOutputStream.close();
            clientSocket.close();
            return;
        }
    }

    public int GetCountInquiriesOfMonth(int numOfMonth) {
        int count = 0;
        Queue<Inquiry> tempQueue = new LinkedList<>(InquiryManager.q);
        while (!tempQueue.isEmpty()) {
            Inquiry inquiry = tempQueue.poll();
            if (inquiry.getCreationDate().getMonthValue() == numOfMonth) {
                count++;
            }
            InquiryManager.q.add(inquiry);
        }
        return count;
    }
}

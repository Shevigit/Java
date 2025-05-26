package Business;

import ClientServer.InquiryManagerActions;
import ClientServer.RequestData;
import ClientServer.ResponseData;
import ClientServer.ResponseStatus;
import Data.Complaint;
import Data.Inquiry;
import Data.Question;
import Data.Request;
import HandleStoreFiles.IForSaving;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InquiryManagerClient  {
    Socket connectToServer;
    Inquiry currentInquiry;
    List inquiryList=new ArrayList<Inquiry>();

    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;

    public InquiryManagerClient(String address,int port) {
        try{
            connectToServer=new Socket(address,port);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public  void execut(){
        Scanner scanner=new Scanner(System.in);
        InquiryManagerActions inquiryManagerActions;
        System.out.println("Enter 1 to get all Inquiries\nEnter 2 to add Inquiry\nEnter 3 to delet Inquiry" +
                "\nEnter 4 to get count of the Inquiries in the specific month\nEnter 5 to exit");

        int num=scanner.nextInt();
        int digit=0;
        RequestData requestData;
        while (num>0&&num<5){
            switch (num){
                case 1:
                    inquiryManagerActions=InquiryManagerActions.ALL_INQUIRY;
                    requestData=new RequestData(inquiryManagerActions);
                    sendRequestToServer(requestData);
                    break;
                case  2:
                    inquiryManagerActions=InquiryManagerActions.ADD_INQUIRY;
                    System.out.println("Enter 1 to question\nEnter 2 to request\nEnter 3 to complaint\nEnter 4 to exit");
                    digit=scanner.nextInt();
                    while(digit>0&&digit<4)
                    {
                        createInquiry(digit);
                        //  InquiryManager.setNextCodeVal(InquiryManager.getNextCodeVal() + 1);
                        currentInquiry.fillDataByUser();
                        inquiryList.add(currentInquiry);
                        System.out.println("Enter 1 to question\n Enter 2 to request\nEnter 3 to complaint\n Enter 4 to exit");
                        digit=scanner.nextInt();

                    }
                    requestData=new RequestData(inquiryManagerActions,inquiryList);
                    sendRequestToServer(requestData);
                    break;
                case 3:{
                    inquiryManagerActions=InquiryManagerActions.DELETE_INQUIRY;
                    System.out.println("enter code inquiry");
                    digit=scanner.nextInt();
                    requestData=new RequestData(inquiryManagerActions,digit);
                    sendRequestToServer(requestData);
                    break;
                }
                case 4:{
                    inquiryManagerActions=InquiryManagerActions.GET_COUNTINQUIRY;
                    System.out.println("Enter number of month");
                    digit=scanner.nextInt();
                    requestData=new RequestData(inquiryManagerActions,digit);
                    sendRequestToServer(requestData);
                    break;
                }

                default:
                    break;

            }
            System.out.println("Enter 1 to get all Inquiries\nEnter 2 to add Inquiry\nEnter 3 to delet Inquiry" +
                    "\nEnter 4 to get count of the Inquiries in the specific month\nEnter 5 to exit");

            num=scanner.nextInt();
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

    public  void sendRequestToServer(RequestData requestData){
        try{
            objectOutputStream=new ObjectOutputStream(connectToServer.getOutputStream());
            objectOutputStream.writeObject(requestData);
            objectOutputStream.flush();
            objectInputStream=new ObjectInputStream(connectToServer.getInputStream());
            ResponseData responseData=(ResponseData) objectInputStream.readObject();
            System.out.println("responeData: status: " + responseData.getStatus() +" message: " + responseData.getMessage()
                    + " resualt: " + responseData.getResult());
        }
        catch (IOException e){
            ResponseData responseData=new ResponseData(ResponseStatus.FAIL,"The response faailed!");
            System.out.println("responeData: status: " + responseData.getStatus() +" message: " + responseData.getMessage());

        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

}

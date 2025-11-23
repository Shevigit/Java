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




public class InquiryManagerClient {
    private Socket connectToServer;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;

    private Inquiry currentInquiry;
    private List<Inquiry> inquiryList = new ArrayList<>();

    public InquiryManagerClient(String address, int port) {
        try {
            connectToServer = new Socket(address, port);
            objectOutputStream = new ObjectOutputStream(connectToServer.getOutputStream());
            objectOutputStream.flush(); // חובה לפני פתיחת input
            objectInputStream = new ObjectInputStream(connectToServer.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void execut() {
        Scanner scanner = new Scanner(System.in);
        InquiryManagerActions inquiryManagerActions;
        System.out.println("Enter 1 to get all Inquiries\nEnter 2 to add Inquiry\nEnter 3 to delete Inquiry" +
                "\nEnter 4 to get count of the Inquiries in the specific month\nEnter 5 to exit");

        int num = scanner.nextInt();
        int digit = 0;
        RequestData requestData;

        while (num > 0 && num < 5) {
            switch (num) {
                case 1:
                    inquiryManagerActions = InquiryManagerActions.ALL_INQUIRY;
                    requestData = new RequestData(inquiryManagerActions);
                    sendRequestToServer(requestData);
                    break;

                case 2:
                    inquiryManagerActions = InquiryManagerActions.ADD_INQUIRY;
                    inquiryList.clear(); // חשוב - לרוקן את הרשימה לפני הכנסת חדשים
                    System.out.println("Enter 1 to question\nEnter 2 to request\nEnter 3 to complaint\nEnter 4 to exit");
                    digit = scanner.nextInt();

                    while (digit > 0 && digit < 4) {
                        createInquiry(digit);
                        currentInquiry.fillDataByUser();
                        inquiryList.add(currentInquiry);
                        System.out.println("Enter 1 to question\nEnter 2 to request\nEnter 3 to complaint\nEnter 4 to exit");
                        digit = scanner.nextInt();
                    }

                    requestData = new RequestData(inquiryManagerActions, inquiryList);
                    sendRequestToServer(requestData);
                    break;

                case 3:
                    inquiryManagerActions=InquiryManagerActions.ALL_INQUIRY;
                    requestData=new RequestData(inquiryManagerActions);
                    sendRequestToServer(requestData);

                    inquiryManagerActions = InquiryManagerActions.DELETE_INQUIRY;
                    System.out.println("Enter code of inquiry to delete:");
                    digit = scanner.nextInt();
                    requestData = new RequestData(inquiryManagerActions, digit);
                    sendRequestToServer(requestData);
                    break;
                case 4:
                    inquiryManagerActions = InquiryManagerActions.GET_COUNTINQUIRY;
                    System.out.println("Enter number of month:");
                    digit = scanner.nextInt();
                    requestData = new RequestData(inquiryManagerActions, digit);
                    sendRequestToServer(requestData);
                    break;
                default:
                    break;
            }

            System.out.println("\nEnter 1 to get all Inquiries\nEnter 2 to add Inquiry\nEnter 3 to delete Inquiry" +
                    "\nEnter 4 to get count of the Inquiries in the specific month\nEnter 5 to exit");
            num = scanner.nextInt();
        }

        try {
            objectInputStream.close();
            objectOutputStream.close();
            connectToServer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createInquiry(int digit) {
        switch (digit) {
            case 1:
                currentInquiry = new Question();
                break;
            case 2:
                currentInquiry = new Request();
                break;
            case 3:
                currentInquiry = new Complaint();
                break;
            default:
                break;
        }
    }

    public void sendRequestToServer(RequestData requestData) {
        try {
            objectOutputStream.writeObject(requestData);
            objectOutputStream.flush();

            ResponseData responseData = (ResponseData) objectInputStream.readObject();
            System.out.println("ResponseData:\nStatus: " + responseData.getStatus() +
                    "\nMessage: " + responseData.getMessage() +
                    "\nResult: "  +responseData.getResult());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            ResponseData responseData = new ResponseData("The response failed!", ResponseStatus.FAIL);
            System.out.println("ResponseData:\nStatus: " + responseData.getStatus() +
                    "\nMessage: " + responseData.getMessage());
        }
    }
}

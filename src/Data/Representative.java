package Data;

import Business.InquiryManager;
import Business.RepresentativeManagement;
import HandleStoreFiles.IForSaving;

import java.io.IOException;

public class Representative  implements IForSaving{
    private  String className;//ניתן לבטל
    private  String firstName;
    private String identity;
    private  int codeWorker;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setCodeWorker(int codeWorker) {
        this.codeWorker = codeWorker;
    }
    public int getCodeWorker() {
        return  codeWorker;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public Representative() {
    }
    public Representative(String firstName,String identity) {
        this.firstName = firstName;
        this.identity=identity;
        codeWorker= InquiryManager.getNextCodeWorker();
    }


    @Override
    public String getFolderName() {
        return "Representative";
    }

    @Override
    public String getFileName() {
        return codeWorker+"";
    }

    @Override
    public String getData() {
        return this.firstName + "," + this.identity + "," + codeWorker + "," ;
    }

    @Override
    public void parse(String[] fileText) throws IOException {
        this.className=fileText[0];
        this.firstName = fileText[1];
        this.identity = fileText[2];
        this.codeWorker = Integer.parseInt(fileText[3]);
    }
}

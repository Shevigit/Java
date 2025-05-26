package Data;

import Business.InquiryManager;
import ClientServer.StatusInquiry;
import HandleStoreFiles.IForSaving;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Scanner;

public abstract class Inquiry implements IForSaving ,Serializable{
    public String getClassName() {
        return className;
    }
    protected String description;
    protected LocalDateTime creationDate;
    protected Integer code;

    public StatusInquiry getStatus() {
        return status;
    }

    public void setStatus(StatusInquiry status) {
        this.status = status;
    }

    StatusInquiry status;
    Representative representative;

    public Representative getRepresentative() {
        return representative;
    }

    public void setRepresentative(Representative representative) {
        this.representative = representative;
    }

    @Override
    public void parse(String[] fileText) throws IOException {
        this.className = fileText[0];
        this.code = Integer.parseInt(fileText[1]);
        this.creationDate = LocalDateTime.parse(fileText[2]);
        this.description = fileText[3];

    }

    public void setClassName(String className) {
        this.className = className;
    }

    protected String className;

    public Inquiry() {

    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }


    public void fillDataByUser() {
        className = this.getClass().getSimpleName();
        // setCode(InquiryManager.getNextCodeVal());
        setCreationDate(LocalDateTime.now());
        System.out.println("enter describtion:");
        Scanner scan = new Scanner(System.in);
        setDescription(scan.nextLine());
    }

    public abstract void handling();

    public abstract String getFolderName();

    public String getFileName() {
        return getClassName().charAt(0)+""+ getCode() + "";
    }

    public String getData() {
        return this.className + "," + this.getCode() + "," + getCreationDate() + "," + getDescription() + ",";
    }

//    @Override
//    public String toString() {
//        return "Inquiry{" +
//                "description='" + description + '\'' +
//                ", creationDate=" + creationDate +
//                ", code=" + code +
//                ", className='" + className + '\'' +
//                '}';
//    }
}

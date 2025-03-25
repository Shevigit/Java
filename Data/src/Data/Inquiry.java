package Data;

import Business.InquiryManager;
import HandleStoreFiles.IForSaving;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Scanner;

public abstract class Inquiry implements IForSaving {

    protected Integer code;
    protected String description;
    protected LocalDateTime creationDate;

    public String getClassName() {
        return className;
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
        setCode(InquiryManager.getNextCodeVal());
        setCreationDate(LocalDateTime.now());
        System.out.println("enter describtion:");
        Scanner scan = new Scanner(System.in);
        setDescription(scan.nextLine());
    }

    public abstract void handling();

    public abstract String getFolderName();

    public String getFileName() {
        return getCode() + "";
    }

    public String getData() {
        return this.className + "," + this.getCode() + "," + getCreationDate() + "," + getDescription() + ",";
    }


}

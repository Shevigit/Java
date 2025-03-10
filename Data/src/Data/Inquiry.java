package Data;

import java.time.LocalDateTime;
import java.util.Scanner;

public abstract class Inquiry {
    private  static Integer nextCodeVal = 0;
    protected Integer code;
    protected String description;
    protected LocalDateTime creationDate;

    public Inquiry() {
        nextCodeVal++;
    }

    public static void setNextCodeVal(Integer nextCodeVal) {
        Inquiry.nextCodeVal = nextCodeVal;
    }

    public static Integer getNextCodeVal() {
        return nextCodeVal;
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


    public  void fillDataByUser() {
    setCode(nextCodeVal);
        setCreationDate(LocalDateTime.now());
        System.out.println("enter describtion:");
        Scanner scan=new Scanner(System.in);
        setDescription(scan.nextLine());
    }
public  abstract void handling();

}

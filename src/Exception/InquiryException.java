package Exception;

public class InquiryException extends  Exception{
    public Integer getCodeinquiry() {
        return codeinquiry;
    }

    public void setCodeinquiry(Integer codeinquiry) {
        this.codeinquiry = codeinquiry;
    }

    private Integer codeinquiry;

    public InquiryException(Integer code) {
        this.codeinquiry = code;
    }

    @Override
    public String getMessage() {
        return "The inquiryCode " + codeinquiry+ " " + super.getMessage();
    }
}

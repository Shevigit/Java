package Exception;

public class InquiryRunTimeException extends  RuntimeException{
    public Integer getCodeinquiry() {
        return codeinquiry;
    }

    public void setCodeinquiry(Integer codeinquiry) {
        this.codeinquiry = codeinquiry;
    }

    private Integer codeinquiry;

    public InquiryRunTimeException(Integer code) {
        this.codeinquiry = code;
    }

    @Override
    public String getMessage() {
        return codeinquiry+ " " +super.getMessage();
    }
}

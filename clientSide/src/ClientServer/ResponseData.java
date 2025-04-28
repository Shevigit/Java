package ClientServer;

public class ResponseData {
    ResponseStatus status;
    String message;
    Object result;
    public ResponseStatus getStatus() {
        return status;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }

    public ResponseData(ResponseStatus status, String message, Object result) {
        this.status = status;
        this.message = message;
        this.result = result;
    }
    public ResponseData() {
    }

    public String getMessage() {
        return message;
    }



    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }


}

package ClientServer;

import java.io.Serializable;

public class ResponseData implements  Serializable  {
    private static final long serialVersionUID = 1L;
    ResponseStatus status;
    String message;
    Object result;
    public ResponseStatus getStatus() {
        return status;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }

    public ResponseData( String message,ResponseStatus status, Object result) {
        this.status = status;
        this.message = message;
        this.result = result;
    }
    public ResponseData() {
    }

    public String getMessage() {
        return message;
    }


    public ResponseData(ResponseStatus status,String message) {

        this.message = message;
        this.status=status;
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






//    @Override
//    public String toString() {
//        String resultString;
//        if (result instanceof List) {
//            // If result is a list, you might want to join the toString results of each element
//            StringBuilder sb = new StringBuilder("[");
//            List<?> resultList = (List<?>) result; // Cast to List for processing
//            for (Object obj : resultList) {
//                sb.append(obj.toString()).append(", ");
//            }
//            // Remove the trailing comma and space, if there are any results
//            if (sb.length() > 1) {
//                sb.setLength(sb.length() - 2);
//            }
//            sb.append("]");
//            resultString = sb.toString();
//        } else {
//            resultString = (result != null) ? result.toString() : "null";
//        }
//
//        return "ResponseData{" +
//                "status=" + status +
//                ", message='" + message + '\'' +
//                ", result=" + resultString +
//                '}';
//    }



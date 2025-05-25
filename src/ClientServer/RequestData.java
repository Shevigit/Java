package ClientServer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RequestData implements Serializable
{
    private static final long serialVersionUID = 1L;
    InquiryManagerActions action;
    List parameters=new ArrayList<>();

    public RequestData(InquiryManagerActions action) {
        this.action = action;
    }

    public InquiryManagerActions getAction() {
        return action;
    }

    public void setAction(InquiryManagerActions action) {
        this.action = action;
    }


    public RequestData(InquiryManagerActions action, List parameters) {
        this.action = action;
        this.parameters = parameters;
    }
}

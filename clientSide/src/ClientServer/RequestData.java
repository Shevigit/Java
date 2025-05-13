package ClientServer;

import Data.Inquiry;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RequestData implements Serializable {
    private static final long serialVersionUID = 1L;
    InquiryManagerActions action;
    List parameters=new ArrayList<Inquiry>();

    public RequestData(InquiryManagerActions action) {
        this.action = action;
    }

    public RequestData() {
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

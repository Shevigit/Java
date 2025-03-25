package HandleStoreFiles;

import Data.Inquiry;

import java.io.IOException;

public interface IForSaving {
    public  String 	getFolderName();
    public  String 	getFileName();
    public  String 	getData();
    public void parse(String[] fileText) throws IOException;
}

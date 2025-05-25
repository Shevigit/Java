package TestSaveFile;

import Data.Inquiry;
import HandleStoreFiles.IForSaving;

public class SheviForTestSaving implements IForSaving {
    String id;
    String name;

    public SheviForTestSaving(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String getFolderName() {
        return getClass().getPackage().getName();
    }

    @Override
    public String getFileName() {
        return getClass().getSimpleName()+id;
    }

    @Override
    public String getData() {
        return id + "," + name;
    }

    @Override
    public void parse(String[] fileText) {

    }
}

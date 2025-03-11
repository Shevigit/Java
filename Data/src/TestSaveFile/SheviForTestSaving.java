package TestSaveFile;

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
        return getClass().getPackageName();
    }

    @Override
    public String getFileName() {
        return getClass().getSimpleName()+id;
    }

    @Override
    public String getData() {
        return id + "," + name;
    }
}

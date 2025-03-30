package TestSaveFile;

import HandleStoreFiles.HandleFiles;
import java.io.File;
import java.util.Arrays;

public class TestingHandleFiles {
    public static void main(String[] args) throws Exception {

        SheviForTestSaving p1 = new SheviForTestSaving("1234","aaa");
        SheviForTestSaving p2 = new SheviForTestSaving("5432","bbb");
        SheviForTestSaving p3 = new SheviForTestSaving("9999","ccc");
        SheviForTestSaving p4 = new SheviForTestSaving("0090","ccdc");

        HandleFiles handleFiles = new HandleFiles();
        handleFiles.saveFile(p3);
        handleFiles.saveFiles(Arrays.asList(p1,p2,p3,p4));
        handleFiles.deleteFile(p2);
        SheviForTestSaving p5 = new SheviForTestSaving("123456789","sucess BH!");
       boolean x= handleFiles.saveCSV(p5, "id");
        System.out.println(x);
        SheviForTestSaving readP5 = new SheviForTestSaving("123456789","sucess BH!");
        handleFiles.saveCSV(p5, "id");
        File dir = new File("M:\\practicum_properation\\inquirymanagement_bsg");
        dir.mkdir();
        File file = new File("M:\\practicum_properation\\inquirymanagement_bsg\\13");
        file.createNewFile();


}}

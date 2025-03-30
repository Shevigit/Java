package HandleStoreFiles;

import Data.Complaint;
import Data.Inquiry;
import Data.Question;
import Data.Request;

import java.io.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.OutputStream;
//import java.io.OutputStreamWriter;
import java.lang.reflect.Constructor;

public class HandleFiles {


    public void saveFile(IForSaving forSaving) throws IOException {
        File dir = new File(forSaving.getFolderName());
        dir.mkdir();
        File file = new File(getDirectoryPath(forSaving));
        file.createNewFile();
        OutputStream outputStream = new FileOutputStream(file);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
        outputStreamWriter.write(forSaving.getData());
        outputStreamWriter.flush();
    }

    public void deleteFile(IForSaving forSaving) throws IOException {

        File f = new File(forSaving.getFileName());
        f.delete();
    }

    public void updateFile(IForSaving forSaving) throws IOException {
        saveFile(forSaving);
    }

    private String getFileName(IForSaving forSaving) throws IOException {
        return forSaving.getFileName();
    }

    private String getDirectoryPath(IForSaving forSaving) throws IOException {
        return forSaving.getFolderName() + "\\" +forSaving.getFileName() + ".csv";
    }

    public void saveFiles(List<IForSaving> forSavingList) throws IOException {
        for (IForSaving i : forSavingList) {
            saveFile(i);
        }
    }

    public static Object readFromFile(String filePath) throws Exception {
        String[] data = new String[4];
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line = br.readLine();
        if (line != null) {
            data = line.split(",");
            Inquiry inquiry;
            String fullClassName = data[0];
            try {
                Class clazz = Class.forName("Data." + fullClassName);
                if (Inquiry.class.isAssignableFrom(clazz)) {
                    Constructor<?> constructor = clazz.getDeclaredConstructor();
                    inquiry = (Inquiry) constructor.newInstance();
                    inquiry.parse(data);
                    return inquiry;
                } else {
                    throw new IllegalArgumentException("Class does not extend Inquiry");
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public String getCSVDataRecursive(Object obj) throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        String className = obj.getClass().getSimpleName();
        stringBuilder.append(className).append(",");
        Class<?> clazz = obj.getClass();
        while (clazz.getSuperclass() != null) {
            clazz = clazz.getSuperclass();
            Field[] fields = clazz.getDeclaredFields();
            Arrays.sort(fields, Comparator.comparing(Field::getName));
            for (Field field : fields) {
                field.setAccessible(true);
                Object fieldValue = field.get(obj);
                if (!field.getName().equals("className")) {
                    stringBuilder.append(fieldValue != null ? fieldValue.toString() : "null").append(",");
                }
            }
        }
        clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        Arrays.sort(fields, Comparator.comparing(Field::getName));
        for (Field field : fields) {
            field.setAccessible(true);
            Object fieldValue = field.get(obj);
            if (!field.getName().equals("className")) {
                stringBuilder.append(fieldValue != null ? fieldValue.toString() : "null").append(",");
            }
        }
        if (stringBuilder.length() > 0) {
            stringBuilder.setLength(stringBuilder.length() - 1);
        }
        return stringBuilder.toString();
    }


    public boolean saveCSV(Object obj, String filePath) throws Exception {

        int index = filePath.indexOf("\\");
        String dPath=filePath.substring(0, index);
        File dir = new File(dPath);
        dir.mkdir();
        String fPath=filePath.substring(index+1);
        File file = new File(dPath+"\\"+filePath.substring(index+1));
            try (BufferedWriter br = new BufferedWriter(new FileWriter(filePath + ".csv"))) {
                br.write(getCSVDataRecursive(obj));
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }


    }



}








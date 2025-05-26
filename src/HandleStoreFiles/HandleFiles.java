package HandleStoreFiles;

import java.io.*;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import  Exception.InquiryRunTimeException;
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

    public boolean deleteFile(IForSaving forSaving) throws IOException {
        File f = new File(forSaving.getFolderName(), forSaving.getFileName() + ".csv");
        boolean success = f.delete();
       return success;
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

    public static IForSaving readFromFile(String filePath) throws Exception {
        int commaCount = filePath.length() - filePath.replace(",", "").length();
        String[] data = new String[commaCount + 1];

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();
            if (line != null) {
                data = line.split(",");
                String fullClassName = data[0];

                Class<?> clazz = Class.forName("Data." + fullClassName);
                if (IForSaving.class.isAssignableFrom(clazz)) {
                    Constructor<?> constructor = clazz.getDeclaredConstructor();
                    IForSaving iforsaving = (IForSaving) constructor.newInstance();
                    iforsaving.parse(data);
                    return iforsaving;
                } else {
                    throw new IllegalArgumentException("Class does not implement IForSaving");
                }
            }
        } catch (IOException | ReflectiveOperationException e) {
            e.printStackTrace();
            throw e;
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
        } catch (InquiryRunTimeException e) {
            throw new InquiryRunTimeException(Integer.parseInt(file.getName().substring(1, file.getName().indexOf('.'))));

        }
    }



}








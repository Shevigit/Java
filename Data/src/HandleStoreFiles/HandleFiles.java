package HandleStoreFiles;

import Data.Complaint;
import Data.Inquiry;
import Data.Question;
import Data.Request;

import java.io.*;
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
        return forSaving.getFolderName() + "\\" + forSaving.getFileName() + ".txt";
    }

    public void saveFiles(List<IForSaving> forSavingList) throws IOException {
        for (IForSaving i : forSavingList) {
            saveFile(i);
        }
    }

    public static Object readFromFile( String filePath) throws Exception {
        String[] data = new String[3];
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line = br.readLine();
        if (line != null) {
            data = line.split(",");
            Inquiry inquiry;
            String fullClassName = data[0];
            Class<?> clazz = Class.forName(fullClassName);
            if (Inquiry.class.isAssignableFrom(clazz))
            {
                Constructor<?> constructor = clazz.getDeclaredConstructor();
                inquiry= (Inquiry) constructor.newInstance();
                inquiry.parse(data);
                return inquiry;
            } else {
                throw new IllegalArgumentException("Class does not extend Inquiry");
            }
        }
        return null;
    }
}


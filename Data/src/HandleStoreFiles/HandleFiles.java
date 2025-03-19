package HandleStoreFiles;
import Data.Inquiry;

import java.io.*;
import java.util.List;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.OutputStream;
//import java.io.OutputStreamWriter;

public class HandleFiles {


    public void saveFile(IForSaving forSaving) throws  IOException{
        File dir=new File(forSaving.getFolderName());
        dir.mkdir();
        File file = new File(getDirectoryPath(forSaving));
        file.createNewFile();
        OutputStream  outputStream=new FileOutputStream(file) ;
        OutputStreamWriter outputStreamWriter=new OutputStreamWriter(outputStream);
        outputStreamWriter.write(forSaving.getData());
        outputStreamWriter.flush();
    }


    public void deleteFile(IForSaving forSaving) throws  IOException{

        File f=new File(forSaving.getFileName());
        f.delete();
    }

    public void updateFile(IForSaving forSaving)throws  IOException{
        saveFile(forSaving);
    }

    private String getFileName(IForSaving forSaving)throws  IOException{
        return forSaving.getFileName();
    }
    private String getDirectoryPath(IForSaving forSaving)throws IOException{
        return  forSaving.getFolderName()+"\\"+forSaving.getFileName()+".txt";
    }
    public void saveFiles(List<IForSaving> forSavingList)throws IOException{
        for(IForSaving i:forSavingList)
        {
            saveFile(i);
        }
    }


//    private static Inquiry parseInquiry(String line) {
//
//        return new Inquiry(line);
//    }
  public  Object readFromFile(IForSaving iForSaving, String filePath)throws  IOException{
        StringBuilder str=new StringBuilder();
      BufferedReader br = new BufferedReader(new FileReader(filePath));
      String [] data;
      String line=br.readLine();
      if(line!=null)
      {
      data=line.split(",");
      }

//      Inquiry inquiry=parseInquiry(data[i]);
//      inquiry.parse(data);

      return null;
  }
}

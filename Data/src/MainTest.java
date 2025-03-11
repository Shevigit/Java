import java.io.*;

public class MainTest {
    public static void main(String[] args)throws IOException {
                    File myObj = new File("Shevi.txt");
                    if(myObj.createNewFile())
                        System.out.println("File created: " + myObj.getName());
                         else
                      System.out.println("File already exists.");
                        OutputStream    outputStream=new FileOutputStream(myObj) ;
                           outputStream.write("hello Shevi".getBytes());
                           OutputStreamWriter outputStreamWriter=new OutputStreamWriter(outputStream);
                           outputStreamWriter.write(" how are you?");
                            outputStreamWriter.flush();


    }
}

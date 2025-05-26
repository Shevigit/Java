package proccessNight;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

public class  NightlyFileDeletation extends Thread {
    String folderPath; int days;
    public NightlyFileDeletation(String f, int d){
        folderPath=f; days=d;
    }

    @Override
    public void run() {
        deleteOldFiles();
    }

    public  void deleteOldFiles() {
        File folder = new File(folderPath);
        if (!folder.exists() || !folder.isDirectory()) {
            System.err.println("Invalid folder path.");
            return;
        }
        File[] files = folder.listFiles();
        if (files == null) {
            System.err.println("Could not list files in folder.");
            return;
        }
        LocalDateTime cutoffDate = LocalDateTime.now().minus(days, ChronoUnit.DAYS);
        for (File file : files) {
            try {
                BasicFileAttributes attributes = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
                Instant creationTime = attributes.creationTime().toInstant();
                LocalDateTime fileCreationTime = LocalDateTime.ofInstant(creationTime, ZoneId.systemDefault());
                if (fileCreationTime.isBefore(cutoffDate)) {
                    if (file.delete()) {
                        System.out.println("Deleted file: " + file.getName());
                    } else {
                        System.err.println("Failed to delete file: " + file.getName());
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}

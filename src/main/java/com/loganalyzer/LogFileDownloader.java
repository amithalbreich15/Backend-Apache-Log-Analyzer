package com.loganalyzer;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

//Add the log.all file to src/main/resources/all.log in README and add also the file LogFileDownloader to the java com.loganalyzer library

class LogFileDownloader {

    /**
     * Downloads a file from the given URL and saves it to the specified output path.
     *
     * @param fileUrl    URL of the file to be downloaded.
     * @param outputPath Local path where the file will be saved.
     */
    public void downloadFile(String fileUrl, String outputPath) {
        try (BufferedInputStream in = new BufferedInputStream(new URL(fileUrl).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(outputPath)) {

            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }

            System.out.println("File downloaded successfully!");
        } catch (IOException e) {
            System.err.println("Failed to download file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
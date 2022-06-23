package com.max.databases;

import java.io.File;
import java.util.Objects;

public class ReadFolder {
    public static void ReadFiles(File folder){
        //reading folder, processing folder info
        for (File file : Objects.requireNonNull(folder.listFiles())) {
            if (!file.isDirectory()) {
                String fileName = file.getName();
                String filePath = file.getPath();
                String extension = "";
                int i = fileName.lastIndexOf('.');
                if (i > 0) {
                    extension = fileName.substring(i+1);
                }
                long fileSize = file.length();
                Main.addFiles(fileName, filePath, extension, Long.toString(fileSize));

            }else{
                ReadFiles(file);
            }
        }
    }

}

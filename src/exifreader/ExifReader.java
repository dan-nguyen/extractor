/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exifreader;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Danbot
 */
public class ExifReader {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        File theImage = null;
        ArrayList<String> focalLengthCountList = new ArrayList<>();
        String theDirectoryPath = "/Path/To/Images/";
        File folder = new File(theDirectoryPath);
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                theImage = new File(theDirectoryPath + listOfFiles[i].getName());
                System.out.println("Path: " + theImage.toPath().toString());
                Metadata metadata = null;
                try {
                    metadata = ImageMetadataReader.readMetadata(theImage);
                } catch (ImageProcessingException | IOException ex) {
                    Logger.getLogger(ExifReader.class.getName()).log(Level.SEVERE, null, ex);
                }

                for (Directory directory : metadata.getDirectories()) {
                    for (Tag tag : directory.getTags()) {
                        if (tag.getTagName().equals("Focal Length")) {
                            focalLengthCountList.add(tag.getDescription());
                            System.out.println("Focal Length: " + tag.getDescription());
                        }
                    }

                    if (directory.hasErrors()) {
                        for (String error : directory.getErrors()) {
                            System.err.format("ERROR: %s", error);
                        }
                    }
                }
            } else if (listOfFiles[i].isDirectory()) {

            }
        }
        
        Collections.sort(focalLengthCountList);
        System.out.println("\nFrequency Counter:");
        Map<String, Integer> focalCount = new HashMap<>();
        for (String t : focalLengthCountList) {
            Integer i = focalCount.get(t);
            if (i == null) {
                i = 0;
            }
            focalCount.put(t, i + 1);
        }
        
        System.out.println(focalCount);

    }

}

package de.uni_heidelberg.cos.agw.stackwalker;

import de.uni_heidelberg.cos.agw.stackwalker.ui.LogPanel;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;


public class Mover {

    private final String destinationDir;


    public Mover(final String destinationDir) {
        this.destinationDir = destinationDir;
    }


    public boolean move(final DataFile dataFile) {
        if (!dataFile.file.exists()) {
            LogPanel.log("ERROR! " + dataFile.getFilePath() + " can not be moved because it doesn't exist.");
            return false;
        } else if (!dataFile.file.isFile()) {
            LogPanel.log("ERROR! " + dataFile.getFilePath() + " can not be moved because it a file.");
            return false;
        } else if (!dataFile.file.canRead()) {
            LogPanel.log("ERROR! " + dataFile.getFilePath() + " can not be read from.");
            return false;
        } else if (!dataFile.file.canWrite()) {
            LogPanel.log("ERROR! " + dataFile.getFilePath() + "can not be written to.");
            return false;
        }

        String destination = destinationDir + File.separator + dataFile.getTargetSubFolder();
        File destinationFile = new File(destination);

        if (destinationFile.exists()) {
            LogPanel.log("ERROR! " + dataFile.getFileName() + " can not be moved because target " + destination + " exists.");
            return false;
        }


        boolean logOnly = true;


        String source = dataFile.getFilePath();
        LogPanel.log(String.format("%s -> %s\n", source, destination));

        if (logOnly)
            return true;


        try {
            destinationFile.mkdirs();
            FileUtils.moveFile(dataFile.file, destinationFile);
        } catch (IOException e) {
            LogPanel.log(e.getStackTrace().toString() + "\n\n\n");
            return false;
        }

        return true;

    }

}

package de.uni_heidelberg.cos.agw.stackwalker;

import java.io.File;
import java.io.FileFilter;

public class DataDir {

    private String dirPath;
    private boolean isRecursive;
    private FileFilter filter;

    public DataDir(final String dirPath, final boolean isRecursive) {
        this.dirPath = dirPath;
        this.isRecursive = isRecursive;

        filter = new FileFilter() {
            @Override
            public boolean accept(final File file) {
                return true;
            }
        };
    }

    public String getDirPath() {
        return dirPath;
    }

    public void setDirPath(final String dirPath) {
        this.dirPath = dirPath;
    }

    public boolean isRecursive() {
        return isRecursive;
    }

    public void setIsRecursive(final boolean isRecursive) {
        this.isRecursive = isRecursive;
    }

    public FileFilter getFilter() {
        return filter;
    }

    public void setFilter(final FileFilter filter) {
        this.filter = filter;
    }
}

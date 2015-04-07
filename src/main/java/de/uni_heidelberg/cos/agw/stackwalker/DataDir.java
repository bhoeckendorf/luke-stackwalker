package de.uni_heidelberg.cos.agw.stackwalker;

public class DataDir {

    private String dirPath;
    private boolean isRecursive;

    public DataDir(final String dirPath, final boolean isRecursive) {
        this.dirPath = dirPath;
        this.isRecursive = isRecursive;
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
}

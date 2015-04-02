package de.uni_heidelberg.cos.agw.stackwalker;

import de.uni_heidelberg.cos.agw.stackwalker.ui.DataTypeTableModel;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Holds the information associated with a data file. Its location,
 * DataTypes and values.
 */
public class DataFile {

    /**
     * the {@code File} instance of the DataFile
     */
    public final File file;

    /**
     * the {@link DataType} values, as key-value pairs, with
     * {@link DataType #getName()} as key
     */
    private final Map<String, Integer> fileNameTagValues;

    /**
     * Constructs a new DataFile. Is private and to be used via {@link DataFile#make(String, boolean, File)}.
     *
     * @param dataDir the data folder
     * @param file    the data file
     * @see #make(String, boolean, File)
     */
    private DataFile(final File file, final Map<String, Integer> fileNameTagValues) {
        this.file = file;
        this.fileNameTagValues = fileNameTagValues;
    }

    /**
     * Returns a new {@code DataFile} instance or {@code null} if the
     * location can't be read from or not all file name tags are present
     * in the file name.
     *
     * @param dataDir the data folder
     * @param file    the data file
     * @return a new DataFile instance, or {@code null} file is non-existent or doesn't contain all file name tags the activated {@link DataType}s in {@link DataTypeTableModel}).
     */
    public static DataFile create(final File file) {
        final String template = file.getAbsolutePath();
        final Map<String, Integer> values = new HashMap<String, Integer>();
        for (final DataType type : DataType.LIST) {
            if (!type.isActive()) {
                continue;
            }
            final int value = type.getValue(template);
            if (value == -1) {
                return null;
            }
            values.put(type.getName(), value);
        }

//        for (final String key : values.keySet()) {
//            System.out.println(key + " " + values.get(key));
//        }

        return new DataFile(file, values);
    }

    /**
     * Returns the file's path.
     *
     * @return the file's path
     * @see #getFileName()
     */
    public String getFilePath() {
        return file.getAbsolutePath();
    }

    /**
     * Returns the file name.
     *
     * @return the file name
     * @see #getFilePath()
     */
    public String getFileName() {
        return file.getName();
    }

    public int getValue(final DataType type) {
        return fileNameTagValues.get(type.getName());
    }

    /**
     * Returns the value of a {@link DataType} (by name) in a DataFile's file name, or {@code null}.
     * Example: file name = footag123bar, {@link DataType#getFileNameTag()} = tag, return = 123
     *
     * @param name a DataType's file name tag ({@link DataType#getFileNameTag()})
     * @return the value of a DataType (by name) in a DataFile's file name, or {@code null}
     * @see #getValue(int)
     * @see DataType
     * @see DataTypeTableModel
     */
    public int getValue(final String name) {
        return fileNameTagValues.get(name);
    }
}

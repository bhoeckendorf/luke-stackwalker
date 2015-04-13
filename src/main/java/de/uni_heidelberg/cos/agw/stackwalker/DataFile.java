package de.uni_heidelberg.cos.agw.stackwalker;

import com.sun.istack.internal.Nullable;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Holds the information associated with a data file.
 * Its location, DataTypes and values.
 */
public class DataFile {

    /**
     * file system path to data file
     */
    public final String filePath;

    /**
     * the {@link DataType} values, as key-value pairs,
     * with {@link DataType#getName()} as key
     */
    private final Map<String, Integer> fileNameTagValues;

    /**
     * Constructs a new DataFile.
     * To be used via {@link #create(File)}.
     *
     * @param filePath          file system path to data file
     * @param fileNameTagValues data type values
     * @see #create(File)
     */
    private DataFile(final String filePath, final Map<String, Integer> fileNameTagValues) {
        this.filePath = filePath;
        this.fileNameTagValues = fileNameTagValues;
    }

    /**
     * Returns a new {@code DataFile} instance or {@code null} if not
     * all file name tags are present in the file name.
     *
     * @param file file system path
     * @return {@code DataFile} instance or {@code null}
     */
    @Nullable
    public static DataFile create(final File file) {
        return create(file.getAbsolutePath());
    }

    /**
     * Returns a new {@code DataFile} instance or {@code null} if not
     * all file name tags are present in the file name.
     *
     * @param filePath file system path
     * @return {@code DataFile} instance or {@code null}
     */
    @Nullable
    public static DataFile create(final String filePath) {
        final Map<String, Integer> values = new HashMap<String, Integer>();
        for (final DataType type : DataType.LIST) {
            final int value = type.getValue(filePath);
            if (value == -1) {
                return null;
            }
            values.put(type.getName(), value);
        }
        return new DataFile(filePath, values);
    }

    /**
     * Returns the file system path of the data file.
     *
     * @return file system path
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * Returns the value of the given {@link DataType}
     *
     * @param type data type
     * @return data type value
     */
    public int getValue(final DataType type) {
        return fileNameTagValues.get(type.getName());
    }

    @Override
    public String toString() {
        return filePath;
    }
}

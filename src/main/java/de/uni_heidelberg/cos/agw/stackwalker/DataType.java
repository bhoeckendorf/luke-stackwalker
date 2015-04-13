package de.uni_heidelberg.cos.agw.stackwalker;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A data parameter that is represented in file system path and/or
 * file name via a tag followed by a numeric value.
 * <p/>
 * <p>Example: A measurement over time of multiple channels, the data is split into
 * single files for each combination of DataTypes.</p>
 * <ul>
 * <li>{@code Experiment1Time001Chn1.csv}</li>
 * <li>{@code Experiment1Time001Chn2.csv}</li>
 * <li>{@code Experiment1Time002Chn1.csv}</li>
 * <li>{@code Experiment1Time002Chn2.csv}</li>
 * <li>...
 * </ul>
 * <p/>
 * <p>In this example, DataTypes are Time and Channel, their file name
 * tags are "Time" and "Chn", respectively. Their value is the value of
 * all digits directly following the file name tags.</p>
 */
public class DataType {

    public static final List<DataType> LIST = new ArrayList<DataType>();
    private String name = "";
    private String fileNameTag = "";
    private Pattern pattern;

    public DataType() {
    }

    public DataType(final String name, final String fileNameTag) {
        setName(name);
        setFileNameTag(fileNameTag);
    }

    public int getLevel() {
        return LIST.indexOf(this);
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getFileNameTag() {
        return fileNameTag;
    }

    public void setFileNameTag(final String tag) {
        fileNameTag = tag;
        pattern = Pattern.compile(String.format("%s\\d+", fileNameTag));
    }

    public int getValue(final String template) {
//        System.out.format("Matching %s in %s\n", fileNameTag, template);
        final Matcher matcher = pattern.matcher(template);
        if (matcher.find()) {
            final String subst = template.substring(matcher.start() + fileNameTag.length(), matcher.end());
//            System.out.println("    " + subst);
            return Integer.parseInt(subst);
        }
        return -1;
    }

//    public int getValue(final String template) {
//        int start;
//        if (hasFixedBlockStart && fileNameTag.equals(template.substring(blockStart, blockStart + fileNameTag.length()))) {
//            start = blockStart;
//        } else {
//            start = template.lastIndexOf(fileNameTag);
//        }
//
//        while (start != -1) {
//            final int numBlockStart = start + fileNameTag.length();
//
//            if (hasFixedBlockSize) {
//                try {
//                    return Integer.parseInt(template.substring(numBlockStart, start + blockSize));
//                } catch (NumberFormatException e) {
//                }
//            }
//
//            int size = 0;
//            for (final char c : template.substring(numBlockStart).toCharArray()) {
//                if (Character.isDigit(c)) {
//                    size++;
//                } else if (size > 0) {
//                    return Integer.parseInt(template.substring(numBlockStart, numBlockStart + size));
//                }
//            }
//
//            start = template.lastIndexOf(fileNameTag, start);
//        }
//
//        return -1;
//    }
}

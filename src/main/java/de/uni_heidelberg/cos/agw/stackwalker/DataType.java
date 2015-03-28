/*
 * This file is part of Luke Stackwalker.
 * https://github.com/bhoeckendorf/luke-stackwalker
 * 
 * Copyright 2012 Burkhard Hoeckendorf <b.hoeckendorf at web dot de>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.uni_heidelberg.cos.agw.stackwalker;


/**
 * A data parameter that is represented in the file names via a tag
 * followed by a value.
 * <p>
 * <h4>Example</h4>
 * <p>A measurement over time of multiple channels, the data is split into
 * single files for each combination of DataTypes.</p>
 * <ul>
 * <li>Experiment1Time001Chn1.csv</li>
 * <li>Experiment1Time001Chn2.csv</li>
 * <li>Experimene1Time002Chn1.csv</li>
 * <li>Experiment1Time002Chn2.csv</li>
 * <li>...
 * </ul>
 * <p>
 * <p>In this example, DataTypes are Timepoint and Channel, their file name
 * tags are "Time" and "Chn", respectively. Their value is the value of
 * all digits directly following the file name tags.</p>
 */
public class DataType {

    /**
     * the name of the DataType.
     */
    private String name = "";

    /**
     * the file name tag
     */
    private String fileNameTag = "";

    /**
     * whether or not the DataType is activated and will be used to compute
     * the data file hierarchy
     */
    private boolean active = true;

    /**
     * whether or not only a range of DataType values will be considered
     */
    private boolean rangeActive = false;

    // TODO: add JavaDoc for all params and clarify it a bit more.
    private int rangeStart = 0;
    private int rangeEnd = 0;
    private int interval = 1;


    public DataType() {
    }


    public DataType(final String name) {
        setName(name);
    }

    /**
     * Returns the file name tag of this DataType.
     *
     * @return the file name tag of this DataType
     */
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Returns whether or not this DataType is used for processing.
     *
     * @return whether or not this DataType is used for processing
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Sets whether or not this DataType is used for processing.
     *
     * @param state whether or not this DataType is used for processing
     */
    public void setActive(final boolean state) {
        active = state;
    }

    /**
     * Returns whether or not the processing is limited by a set index range.
     *
     * @return whether or not the processing is limited by a set index range
     */
    public boolean isRangeActive() {
        return rangeActive;
    }

    /**
     * Sets whether or not the processing is limited by a set index range.
     *
     * @param state whether or not the processing is limited by a set index range
     */
    public void setRangeActive(final boolean state) {
        rangeActive = state;
    }

    /**
     * Returns the file name tag.
     *
     * @return the file name tag
     */
    public String getFileNameTag() {
        return fileNameTag;
    }

    /**
     * Sets the file name tag.
     *
     * @param tag the file name tag
     */
    public void setFileNameTag(final String tag) {
        fileNameTag = tag;
    }

    /**
     * Returns the first index that will be processed.
     *
     * @return the first index thet will be processed
     */
    public int getRangeStart() {
        return rangeStart;
    }

    /**
     * Sets the first index that will be processed.
     *
     * @param start the first index that will be processed
     */
    public void setRangeStart(final int start) {
        rangeStart = start;
        if (rangeStart > rangeEnd)
            rangeEnd = rangeStart;
    }

    /**
     * Returns the final index that will be processed.
     *
     * @return the end of the range
     */
    public int getRangeEnd() {
        return rangeEnd;
    }

    /**
     * Sets the final index that will be processed.
     *
     * @param end the final index that will be processed
     */
    public void setRangeEnd(final int end) {
        rangeEnd = end;
        if (rangeEnd < rangeStart)
            rangeStart = rangeEnd;
    }

    /**
     * Returns the interval for traversing over this {@code DataType}'s indices.
     *
     * @return the interval for traversing over this {@code DataType}'s indices
     */
    public int getInterval() {
        return interval;
    }

    /**
     * Sets the interval for traversing over this {@code DataType}'s indices.
     *
     * @param interval the interval for traversing over this {@code DataType}'s indices
     */
    public void setInterval(final int interval) {
        this.interval = interval;
    }

}

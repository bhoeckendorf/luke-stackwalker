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

package de.uni_heidelberg.cos.lukestackwalker;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Holds the information associated with a data file. Its location,
 * DataTypes and values.
 */
public class DataFile {
	
//	/** the {@link DataDir} that the file is located in */
//	// Currently not used, hence commented out.
//	private final DataDir dataDir;

	/**
	 * the {@link DataType} values, as key-value pairs, with
	 * {@link DataType #getName()} as key
	 */
	private final Map<String, Integer> fileNameTagValues;
	
	/** the absolute location of the file */
	private final String absoluteFilePath;
	
	/** the name of the data set the file belongs to */
	private final String dataSetName;
	
	/**
	 * Whether the file is valid or not. A DataFile is invalid if its
	 * location can't be read from or if not all DataType fileNameTags
	 * are present in its file name.
	 */
	// TODO: replace by something more elegant, if possible
	private boolean isValid = true;
	
	/** the {@code File} instance of the DataFile */
	private final File file;
	

	/**
	 * Returns a new {@code DataFile} instance or {@code null} if the
	 * location can't be read from or not all file name tags are present
	 * in the file name.
	 *  
	 * @param dataDir the data folder
	 * @param file the data file
	 * @return a new DataFile instance, or {@code null} file is non-existent or doesn't contain all file name tags the activated {@link DataType}s in {@link DataTypeTableModel}).
	 */
	public static DataFile make(final DataDir dataDir, final File file) {
		DataFile dataFile = new DataFile(dataDir, file);
		if (!dataFile.isValid)
			return null;
		return dataFile;
	}
	

	/**
	 * Constructs a new DataFile. Is private and to be used via {@link DataFile#make(String, boolean, File)}.
	 * @param dataDir the data folder
	 * @param file the data file
	 * @see #make(String, boolean, File)
	 */
	private DataFile(final DataDir dataDir, final File file) {
//		this.dataDir = dataDir;  // see above
		this.file = file;
		
		String filePath = "";
		try {
			filePath = this.file.getCanonicalPath();
		} catch (IOException e) {
			this.isValid = false;
			// TODO: generate some warning in the UI
		}
		absoluteFilePath = filePath;
		
		String comparableFileName = absoluteFilePath.replace(dataDir.getPath().toString(), "").substring(1);//.replace(File.separator, "");
		DataType firstDataType = DataTypeTableModel.getDataTypeOfLevel(true, 0);
		String firstFileNameTag = firstDataType.getFileNameTag();
		//dataSetName = comparableFileName.split(firstFileNameTag)[0];
		dataSetName = getDataSetName(comparableFileName);

		fileNameTagValues = getFileNameTagValues(comparableFileName);
		if (fileNameTagValues.isEmpty())
			isValid = false;
	}

	
	/**
	 * Returns the file's path.
	 * @return the file's path
	 * @see #getFileName()
	 */
	public String getFilePath() {
		return absoluteFilePath;
	}
	
	
	/**
	 * Returns the file name.
	 * @return the file name
	 * @see #getFilePath()
	 */
	public String getFileName() {
		return file.getName();
	}
	

	/**
	 * Returns the name of the data set that a DataFile instance belongs to.
	 * @return the name of the data set that a DataFile instance belongs to
	 */
	public String getDataSetName() {
		return dataSetName;
	}
	
	
	/**
	 * Returns the value of a {@link DataType} (by name) in a DataFile's file name, or {@code null}.
	 * Example: file name = footag123bar, {@link DataType#getFileNameTag()} = tag, return = 123 
	 * @param dataTypeName a DataType's file name tag ({@link DataType#getFileNameTag()})
	 * @return the value of a DataType (by name) in a DataFile's file name, or {@code null}
	 * @see #getDataTypeValue(int)
	 * @see DataType
	 * @see DataTypeTableModel
	 */
	public int getDataTypeValue(final String dataTypeName) {
		return fileNameTagValues.get(dataTypeName);
	}
	

	/**
	 * Returns the value of the nth {@link DataType} from {@link DataTypeTableModel} in this DataFile's file name, or {@code null}.
	 * Example: file name = footag123bar, {@link DataType#getFileNameTag()} = tag, return = 123 
	 * The index n (= dataTypeLevel) considers only activated data types.
	 * @param dataTypeLevel nth activated DataType in DataTypeTableModel (starting at 0)
	 * @return the value of the nth DataType from DataTypeTableModel in this DataFile's file name, or {@code null}
	 * @see #getDataTypeValue(String)
	 * @see DataType
	 * @see DataTypeTableModel
	 */
	public int getDataTypeValue(final int dataTypeLevel) {
		final String dataTypeName = DataTypeTableModel.getDataTypeName(true, dataTypeLevel); 
		return getDataTypeValue(dataTypeName);
	}
	

	/**
	 * Returns a key-value Map of {@link DataType}s and their respective values in comparableFileName.
	 * If this method runs into trouble, it returns an empty Map. 
	 * @param comparableFileName the subject
	 * @return key-value Map, keys: {@link DataType#getName()}, values: {@link #getValueOfTag(String, String)}
	 * @see DataType
	 */
	private Map<String, Integer> getFileNameTagValues(final String comparableFileName) {
		Map<String, Integer> values = new HashMap<String, Integer>();
		List<DataType> dataTypes = DataTypeTableModel.getDataTypes(true);
		for (DataType dataType : dataTypes) {
			String dataTypeName = dataType.getName();
			String fileNameTag = dataType.getFileNameTag();
			final int value = getValueOfTag(comparableFileName, fileNameTag);
			values.put(dataTypeName, value);
			if (value == -1) {
				values.clear();
				break;
			}
		}
		return values;
	}

	
	/**
	 * Returns the name of the dataset that this DataFile instance belongs to.
	 * @param comparableFileName the subject
	 * @return the name of the dataset that this DataFile instance belongs to.
	 */
	// TODO: this can probably somehow be integrated in all the iterations that
	// are done to get the data type values.
	private String getDataSetName(final String comparableFileName) {
		int index = comparableFileName.length() - 1;
		for (DataType dataType : DataTypeTableModel.getDataTypes(true)) {
			final String fileNameTag = dataType.getFileNameTag();
			final int currentIndex = comparableFileName.indexOf(fileNameTag);
			if (currentIndex < index)
				index = currentIndex;
		}
		System.out.println("0-" + index + " of " + )
		return comparableFileName.substring(0, index);
	}
	

	/**
	 * Finds fileNameTag in comparableFileName, and returns any number of digits downstream as {@code int}, which it returns.
	 * Example: comparableFileName = footag123bar, fileNameTag = tag, return = 123 
	 * @param comparableFileName the subject
	 * @param fileNameTag the query
	 * @return the {@code int} in comparableFileNameTag downstream of fileNameTag, or -1 if there is no such thing
	 */
	private int getValueOfTag(final String comparableFileName, final String fileNameTag) {
		String[] parts = comparableFileName.split(fileNameTag);
		if(parts.length != 2)
			return -1;

		final String string = parts[1];
		char[] chars = string.toCharArray();
		int i = 0;
		for (char c : chars) {
			if (Character.isDigit(c))
				++i;
			else
				break;
		}
		
		if (i == 0)
			return -1;
		
		int value = -1;
		try {
			value = Integer.parseInt(string.substring(0, i));
		}
		catch(NumberFormatException e) {
			return value;
		}
		return value;
	}

}

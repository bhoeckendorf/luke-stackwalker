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


public class DataFile {
	
	private final Map<String, Integer> fileNameTagValues;
	private final String
		absoluteFilePath,
		dataDir,
		dataSetName;
	private final boolean
		isRecursive;
	private boolean isValid = true;
	private final File file;
	

	public static DataFile make(String dataDir, boolean isRecursive, File file) {
		DataFile dataFile = new DataFile(dataDir, isRecursive, file);
		if (!dataFile.isValid)
			return null;
		return dataFile;
	}
	
	
	private DataFile(String dataDir, boolean isRecursive, File file) {
		this.dataDir = dataDir;
		this.isRecursive = isRecursive;
		this.file = file;
		
		String filePath = new String();
		try {
			filePath = this.file.getCanonicalPath();
		} catch (IOException e) {
			System.out.println("In constructor for DataFile:");
			e.printStackTrace();
			System.exit(1);
		}
		absoluteFilePath = filePath;
		
		String comparableFileName = absoluteFilePath.replace(dataDir, "").substring(1);//.replace(File.separator, "");
		DataType firstDataType = DataTypeTableModel.getDataTypeOfLevel(true, 0);
		String firstFileNameTag = firstDataType.getFileNameTag();
		dataSetName = comparableFileName.split(firstFileNameTag)[0];

		fileNameTagValues = getFileNameTagValues(comparableFileName);
	}

	
	public boolean isValid() {
		return isValid;
	}
	
	
	public String getFilePath() {
		return absoluteFilePath;
	}
	
	
	public String getFileName() {
		return file.getName();
	}
	
	
	public String getDataSetName() {
		return dataSetName;
	}
	
	
	public int getDataTypeValue(String dataTypeName) {
		return fileNameTagValues.get(dataTypeName);
	}
	
	
	public int getDataTypeValue(int dataTypeLevel) {
		String dataTypeName = DataTypeTableModel.getDataTypeName(true, dataTypeLevel); 
		return getDataTypeValue(dataTypeName);
	}
	

	private Map<String, Integer> getFileNameTagValues(String comparableFileName) {
		Map<String, Integer> values = new HashMap<String, Integer>();
		final List<DataType> dataTypes = DataTypeTableModel.getDataTypes(true);
		for (DataType dataType : dataTypes) {
			String dataTypeName = dataType.getName();
			String fileNameTag = dataType.getFileNameTag();
			int value = getValueOfTag(comparableFileName, fileNameTag);
			values.put(dataTypeName, value);
			if (value == -1) {
				isValid = false;
				break;
			}
		}
		return values;
	}


	private int getValueOfTag(String comparableFileName, String fileNameTag) {
		String[] parts = comparableFileName.split(fileNameTag);
		if(parts.length != 2)
			return -1;
		String string = parts[1];

//		first implementation. less code, but probably slower than the implementation below?
//		int value = -1;
//		for(int i=1; i<string.length(); i++) {
//			try {
//				value = Integer.parseInt(string.substring(0, i));
//			}
//			catch(NumberFormatException e) {
//				return value;
//			}
//		}
		
		char[] chars = string.toCharArray();
		int i = 0;
		for (char c : chars) {
			if (Character.isDigit(c))
				i++;
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

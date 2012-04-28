/*
 * This file is part of Luke Stackwalker
 * https://github.com/bhoeckendorf/luke-stackwalker
 * 
 * Copyright 2012 Burkhard Höckendorf <b.hoeckendorf at web dot de>
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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class DataTypeModelPreset {

	private static final String delimiter = "=";
	private static final String comment = "//";
	public final String name;
	public final List<DataType> dataTypes;
	
	
	public DataTypeModelPreset(final String name, final List<DataType> dataTypes) {
		this.name = name;
		this.dataTypes = dataTypes;
	}
	
	
	public static DataTypeModelPreset read(final String name, final File file) {
		System.out.println("reading");
		Map<String, String> keyvalMap = new HashMap<String, String>();

		try {
			Scanner reader = new Scanner(new FileReader(file));
			while (reader.hasNext()) {
				String line = reader.nextLine().trim();
				if (line.isEmpty() || line.startsWith(comment) || !line.contains(delimiter))
					continue;
				String[] keyval = getKeyValue(line);
				keyvalMap.put(keyval[0], keyval[1]);
			}
			reader.close();
		}
		catch (FileNotFoundException e) {
			return null;
		}
		catch (IOException e) {
			return null;
		}
		
		List<DataType> dataTypes = new ArrayList<DataType>();
		for (final String dataTypeName : keyvalMap.keySet()) {
			DataType dataType = getDataType(dataTypeName, keyvalMap.get(dataTypeName));
			if (dataType != null)
				dataTypes.add(dataType);
		}
		
		System.out.println("done reading");
		return new DataTypeModelPreset(name, dataTypes);
	}
	
	
	private static String[] getKeyValue(final String input) {
		String[] parts = input.trim().split(delimiter);
		if (parts.length != 2)
			return new String[0];
		for (int i = 0; i < parts.length; ++i) {
			parts[i] = parts[i].trim();
			if (parts[i].isEmpty())
				return new String[0];
		}
		return parts;
	}
	
	
	private static DataType getDataType(final String name, final String input) {
		String[] parts = input.trim().split(" ");
		if (parts.length != 3)
			return null;
		String nameTag = parts[0].trim();
		boolean optional = Boolean.parseBoolean(parts[1].trim());
		boolean active = Boolean.parseBoolean(parts[2].trim());
		
		DataType dataType = new DataType();
		dataType.setName(name);
		dataType.setFileNameTag(nameTag);
		dataType.setOptional(optional);
		dataType.setActive(active);
		return dataType;
	}

}

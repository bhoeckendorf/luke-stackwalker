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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class DataTypeModelPreset {

	private static final File presetsDir = new File("presets");
	private static final String delimiter = " ";
	private static final String comment = "//";
	public final String name;
	public final List<DataType> dataTypes;
	

	public static List<DataTypeModelPreset> read() {
		List<DataTypeModelPreset> presets = new ArrayList<DataTypeModelPreset>();
		for (File file : presetsDir.listFiles()) {
			if (!file.isFile() || !file.getName().endsWith(".txt"))
				continue;
			String name = file.getName().replace(".txt", "");
			DataTypeModelPreset preset = DataTypeModelPreset.read(name, file);
			if (preset != null)
				presets.add(preset);
		}
		return presets;
	}
	
	
	private static DataTypeModelPreset read(final String name, final File file) {
		List<DataType> dataTypes = new ArrayList<DataType>();
		try {
			Scanner reader = new Scanner(new FileReader(file));
			while (reader.hasNext()) {
				String line = reader.nextLine().trim();
				if (line.isEmpty() || line.startsWith(comment) || !line.contains(delimiter))
					continue;
				DataType dataType = getDataType(line);
				if (dataType != null)
					dataTypes.add(dataType);
			}
			reader.close();
		}
		catch (FileNotFoundException e) {
			return null;
		}
		return new DataTypeModelPreset(name, dataTypes);
	}
	
	
	public DataTypeModelPreset(final String name, final List<DataType> dataTypes) {
		this.name = name;
		this.dataTypes = dataTypes;
	}
	
	
	private static DataType getDataType(final String input) {
		String[] parts = input.split(delimiter);
		if (parts.length != 4)
			return null;
		String name = parts[0].trim();
		String tag = parts[1].trim();
		boolean optional = Boolean.parseBoolean(parts[2].trim());
		boolean active = Boolean.parseBoolean(parts[3].trim());
		
		DataType dataType = new DataType();
		dataType.setName(name);
		dataType.setFileNameTag(tag);
		dataType.setOptional(optional);
		dataType.setActive(active);
		return dataType;
	}

}

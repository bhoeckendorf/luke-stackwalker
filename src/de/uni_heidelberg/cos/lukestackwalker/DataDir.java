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


/**
 * This class holds the path to a data folder and the information whether or not its subfolders should be included recursively.
 * {@see DataDirTableModel}
 * {@see DataDirPanel}
 */
public class DataDir {

	/** the path to a data folder */
	private File path;
	
	/** whether or not to include subfolders recursively */
	private boolean isRecursive;
	

	/**
	 * Returns a new DataDir or {@code null} if path is not a folder or an unreadable location.
	 * @param path the path to a data folder
	 * @param isRecursive whether or not to include subfolders of path
	 * @return a new DataDir
	 */
	public static DataDir make(File path, boolean isRecursive) {
		if (!path.isDirectory() || !path.canRead())
			return null;
		return new DataDir(path, isRecursive);
	}
	
	
	/** 
	 * Creates a new DataDir. Is used via {@link #make(File, boolean)}.
	 * @param path the path to a data folder
	 * @param isRecursive whether or not to include subfolders of path
	 */
	private DataDir(File path, boolean isRecursive) {
		setPath(path);
		setRecursive(isRecursive);
	}
	
	
	/**
	 * Returns the path to the data folder.
	 * @return the path to the data folder
	 */
	public File getPath() {
		return path;
	}
	
	
	/**
	 * Sets the path to the data folder.
	 * @param path the path to the data folder
	 */
	public void setPath(File path) {
		this.path = path;
	}
	
	
	/**
	 * Whether or not subfolders of the data folder are included.
	 * @return whether or not subfolders of the data folder are included
	 */
	public boolean isRecursive() {
		return isRecursive;
	}
	
	
	/**
	 * Sets whether or not to include subfolders of the data folder.
	 * @param isRecursive whether or not to include subfolders of the data folder
	 */
	public void setRecursive(boolean isRecursive) {
		this.isRecursive = isRecursive;
	}
	
}

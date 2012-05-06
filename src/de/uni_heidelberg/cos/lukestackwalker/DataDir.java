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
import java.io.FileFilter;


/**
 * Holds the path to a data folder and retrieves the {@link DataFile}s
 * contained within, recursively if desired.
 */
public class DataDir {

	/** the path to a data folder */
	private File path;
	
	/** whether or not to include subfolders recursively */
	private boolean isRecursive;
	

	/**
	 * Returns a new DataDir or {@code null} if path is not a folder
	 * or an unreadable location.
	 * 
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
	 * 
	 * @param path the path to a data folder
	 * @param isRecursive whether or not to include subfolders of path
	 */
	private DataDir(File path, boolean isRecursive) {
		setPath(path);
		setRecursive(isRecursive);
	}
	
	
	/**
	 * Returns the path to the data folder.
	 * 
	 * @return the path to the data folder
	 */
	public File getPath() {
		return path;
	}
	
	
	/**
	 * Sets the path to the data folder.
	 * 
	 * @param path the path to the data folder
	 */
	public void setPath(File path) {
		this.path = path;
	}
	
	
	/**
	 * Whether or not to include subfolders recursively.
	 * 
	 * @return whether or not to include subfolders recursively
	 */
	public boolean isRecursive() {
		return isRecursive;
	}
	
	
	/**
	 * Sets whether or not to include subfolders recursively.
	 * 
	 * @param isRecursive whether or not to include subfolders recursively
	 */
	public void setRecursive(boolean isRecursive) {
		this.isRecursive = isRecursive;
	}
	

	/**
	 * Inserts the valid {@link DataFile}s contained in this DataDir (and its
	 * subfolders, if {@link #isRecursive()}) into the {@link DataSetTreeModel}
	 * {@code model}.
	 * 
	 * @param model the target {@code DataSetTreeModel}
	 */
	/*
	 * There is no getDataFiles() method, because that would generate a long list
	 * of DataFiles, just to insert them to the long list of DataFiles in the
	 * DataSetTreeModel. Instead, we're going to add to the latter list right away.
	 */
	public void insertDataFilesIntoModel(final DataSetTreeModel model) {
		System.out.println("Populating data file hierarchy ...");
		if (isRecursive)
			insertDataFilesIntoModelRecursively(model, path);
		else
			insertDataFilesIntoModelNonRecursively(model);
		System.out.println("Done populating data file hierarchy.");
	}
	

	/**
	 * Inserts the valid {@link DataFile}s contained in this DataDir (and its
	 * subfolders, if {@link #isRecursive()}) into the {@link DataSetTreeModel}
	 * {@code model}.
	 * Non-recursive version, called by {@link #insertDataFilesIntoModel(DataSetTreeModel)}. 
	 * 
	 * @param model the target {@code DataSetTreeModel}
	 */
	private void insertDataFilesIntoModelNonRecursively(final DataSetTreeModel model) {
		DataFileFilter filter = new DataFileFilter();
		for(File file : path.listFiles(filter)) {
			DataFile dataFile = DataFile.make(this, file);
			if (dataFile != null)
				model.add(dataFile);
		}
	}

	
	/**
	 * Inserts the valid {@link DataFile}s contained in this DataDir (and its
	 * subfolders, if {@link #isRecursive()}) into the {@link DataSetTreeModel}
	 * {@code model}.
	 * Recursive version, called by {@link #insertDataFilesIntoModel(DataSetTreeModel)}. 
	 * 
	 * @param model the target {@code DataSetTreeModel}
	 * @param currentSubDir the current subfolder, is called for every subfolder
	 * to find all files
	 */
	private void insertDataFilesIntoModelRecursively(final DataSetTreeModel model, final File currentSubDir) {
//		System.out.println("Looking for TIFF files in " + currentSubDir.toString());
		DataFileFilter filter = new DataFileFilter();
		File[] files = currentSubDir.listFiles();
		if (files == null)
			return;
		for (File file : files) {
			if(file.isDirectory())
				insertDataFilesIntoModelRecursively(model, file);
			else {
				if(filter.acceptFileName(file.getName())) {
					DataFile dataFile = DataFile.make(this, file);
					if (dataFile != null)
						model.add(dataFile);
				}
			}
		}
	}

}


/**
 * Provides initial filtering of files to find candidates for {@link DataFile}s.
 */
/*
 * Checking for isDirectory() is separated from looking at the file name because
 * the recursive version of insertDataFilesIntoModel... needs to deal with folders
 * in a different way than with files, and we don't want to iterate twice.
 * The non-recursive version on the other hand doesn't care for folders.
 * In this implementation, the recursive method deals with folders itself, and
 * then calls acceptFileName(), while the non-recursive method can call accept().
 * 
 * TODO: Still seems not overly elegant. Also unsure about performance.
 */
class DataFileFilter implements FileFilter {


	/**
	 * If {@code file} is not a folder, calls {@link #acceptFileName(String)}
	 * to assess its file name and extension.
	 * 
	 * @param file the path to assess
	 * @return {@code true} if {@code file} is not a folder and
	 * {@link #acceptFileName(String)} returns {@code true} as well, else returns
	 * {@code false}
	 */
	@Override
	public boolean accept(final File file) {
		if (file.isDirectory())
			return false;
		final String fileName = file.getName();
		return acceptFileName(fileName);
	}
	

	/**
	 * Checks a file name and extension for certain criteria.
	 * 
	 * @param fileName the file name to assess
	 * @return {@code true} if criteria are met, else {@code false}
	 */
	public boolean acceptFileName(final String fileName) {
		return fileName.endsWith(".tif") || fileName.endsWith(".tiff") || fileName.endsWith(".TIF") || fileName.endsWith(".TIFF");		
	}
	
}
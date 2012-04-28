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


public class DataDir {

	private File path;
	private boolean isRecursive;
	
	
	public DataDir(File path, boolean isRecursive) {
		setPath(path);
		setRecursive(isRecursive);
	}
	
	
	public File getPath() {
		return path;
	}
	
	
	public void setPath(File path) {
		this.path = path;
	}
	
	
	public boolean isRecursive() {
		return isRecursive;
	}
	
	
	public void setRecursive(boolean isRecursive) {
		this.isRecursive = isRecursive;
	}
	
}

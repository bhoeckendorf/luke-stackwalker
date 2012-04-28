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
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.table.AbstractTableModel;


/**
 * Model underlying the {@link DataDirTable}.
 */
public class DataDirTableModel extends AbstractTableModel {

	/**
	 * List of {@link DataDir} that are contained in the model,
	 * also serves as the model's data structure.
	 */
	private static final List<DataDir> dataDirs = new ArrayList<DataDir>();

	private final String[] columnHeaders = {"Path", "Recursive?"};
	

	/**
	 * Returns a list of {@link DataDir}s currently held by the model.
	 * @return a list of {@link DataDir}s currently held by the model
	 */
	public static List<DataDir> getDataDirs() {
		return dataDirs;
	}
	

	/**
	 * Adds a new row (a {@link DataDir}) to the table model.
	 * 
	 * Always appends the new row to the end of the table model (the bottom of the table).
	 * Called from the UI's {@link DataDirPanel}.
	 */
	public void addRow(DataDir dataDir) {
		dataDirs.add(dataDir);
		final int lastRowIdx = getRowCount() - 1;
		fireTableRowsInserted(lastRowIdx, lastRowIdx);
	}
	

	/**
	 * Removes a row (a {@link DataDir}) from the table model.
	 * 
	 * Called from the UI's {@link DataDirPanel}.
	 * @param rowIdx the {@link DataDir}'s row index
	 */
	public void removeRow(final int rowIdx) {
		dataDirs.remove(rowIdx);
		fireTableRowsDeleted(rowIdx, rowIdx);
	}
	
	
	@Override
	public int getColumnCount() {
		return 2;
	}
	
	
	@Override
	public String getColumnName(final int colIdx) {
		return columnHeaders[colIdx];
	}
	
	
	@Override
	public Class<?> getColumnClass(final int colIdx) {
		return getValueAt(0, colIdx).getClass();
	}
	
	
	@Override
	public int getRowCount() {
		return dataDirs.size();
	}


	@Override
	public Object getValueAt(final int rowIdx, final int colIdx) {
		DataDir dir = dataDirs.get(rowIdx);
		switch (colIdx) {
		case 0:
			return dir.getPath();
		case 1:
			return dir.isRecursive();
		default:
			assert false;
		}
		return null;
	}
	

	@Override
	public boolean isCellEditable(final int rowIdx, final int colIdx) {
		return true;
	}
	
	
	@Override
	public void setValueAt(Object value, final int rowIdx, final int colIdx) {
		DataDir dir = dataDirs.get(rowIdx);
		switch (colIdx) {
		case 0:
			dir.setPath(new File(value.toString()));
		case 1:
			dir.setRecursive(Boolean.valueOf(value.toString()));
		default:
			assert false;
		}
		fireTableCellUpdated(rowIdx, colIdx);
	}

}

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

import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.table.AbstractTableModel;


public class DataTypeTableModel extends AbstractTableModel {

	private static String filter = new String();
	private static List<DataType> allDataTypes = new ArrayList<DataType>();
	private static List<DataType> activeDataTypes = new ArrayList<DataType>();
	private final String[] columnHeaders = {
			"Name",
			"Active",
			"Filename tag",
			"Use range",
			"From",
			"To",
			"Interval"
	};
	private static DefaultComboBoxModel presetComboBoxModel = new DefaultComboBoxModel();
	private int currentPresetIdx = 0;
	private List<DataTypeModelPreset> presets = new ArrayList<DataTypeModelPreset>();


	public DataTypeTableModel() {
		addPreset(new DataTypeModelPreset("", new ArrayList<DataType>()));
		for (DataTypeModelPreset preset : DataTypeModelPreset.read())
			addPreset(preset);
	}
	
	
	public static DefaultComboBoxModel getPresetComboBoxModel() {
		return presetComboBoxModel;
	}
	
	
	private void addPreset(DataTypeModelPreset preset) {
		presetComboBoxModel.addElement(preset.name);
		presets.add(preset);
	}
	
	
	public void setPreset(int presetIdx) {
		if (presetIdx == 0)
			return;

		allDataTypes.clear();
		updateActiveDataTypes();
		DataTypeModelPreset preset = presets.get(presetIdx);
		for (DataType dataType : preset.dataTypes)
			addRow(dataType);
		currentPresetIdx = presetIdx;
	}
	
	
	public static void updateActiveDataTypes() {
		activeDataTypes.clear();
		for (DataType dataType : allDataTypes) {
			if (dataType.isActive())
				activeDataTypes.add(dataType);
		}
	}
	
	
	public static List<DataType> getDataTypes(boolean onlyActive) {
		if (onlyActive)
			return activeDataTypes;
		return allDataTypes;
	}
	
	
	public static int getDataTypeCount(boolean onlyActive) {
		if (onlyActive)
			return activeDataTypes.size();
		return allDataTypes.size();
	}
	
	
	public static DataType getDataTypeOfLevel(boolean onlyActive, int level) {
		if (onlyActive)
			return activeDataTypes.get(level);
		return allDataTypes.get(level);
	}
	
	
	public static String getDataTypeName(boolean onlyActive, int level) {
		if (onlyActive)
			return activeDataTypes.get(level).getName();
		return allDataTypes.get(level).getName();
	}
	
	
	public static String getFilter() {
		return filter;
	}
	
	
	public static void setFilter(String filter) {
		DataTypeTableModel.filter = filter;
	}

	
	public void addRow() {
		addRow(allDataTypes.size());
	}
	
	
	public void addRow(int rowIdx) {
		rowIdx += 1;
		if (rowIdx >= allDataTypes.size())
			rowIdx = allDataTypes.size();
		DataType dataType = new DataType();
		allDataTypes.add(rowIdx, dataType);
		fireTableRowsInserted(rowIdx, rowIdx);
		updateActiveDataTypes();
	}
	
	
	public void addRow(DataType dataType) {
		int rowIdx = allDataTypes.size();
		allDataTypes.add(dataType);
		fireTableRowsInserted(rowIdx, rowIdx);
		updateActiveDataTypes();
	}

	
	public void removeRow(int rowIdx) {
		allDataTypes.remove(rowIdx);
		fireTableRowsDeleted(rowIdx, rowIdx);
		updateActiveDataTypes();
	}
	
	
	public boolean moveUp(int rowIdx) {
		if (rowIdx == 0)
			return false;
		DataType dataType = allDataTypes.remove(rowIdx);
		allDataTypes.add(rowIdx - 1, dataType);
		fireTableRowsUpdated(rowIdx - 1, rowIdx);
		updateActiveDataTypes();
		return true;
	}
	
	
	public boolean moveDown(int rowIdx) {
		if (rowIdx >= allDataTypes.size() - 1)
			return false;
		DataType dataType = allDataTypes.remove(rowIdx);
		allDataTypes.add(rowIdx + 1, dataType);
		fireTableRowsUpdated(rowIdx, rowIdx + 1);
		updateActiveDataTypes();
		return true;
	}


	@Override
	public int getColumnCount() {
		return 7;
	}


	@Override
	public String getColumnName(int colIdx) {
		return columnHeaders[colIdx];
	}

	
	@Override
	public Class<?> getColumnClass(int colIdx) {
		return getValueAt(0, colIdx).getClass();
	}

	
	@Override
	public int getRowCount() {
		return allDataTypes.size();
	}
	
	
	@Override
	public Object getValueAt(int rowIdx, int colIdx) {
		DataType dataType = allDataTypes.get(rowIdx);
		switch (colIdx) {
		case 0:
			return dataType.getName();
		case 1:
			return dataType.isActive();
		case 2:
			return dataType.getFileNameTag();
		case 3:
			return dataType.isRangeActive();
		case 4:
			return dataType.getRangeStart();
		case 5:
			return dataType.getRangeEnd();
		case 6:
			return dataType.getInterval();
		default:
			return null;
		}
	}
	
	
	@Override
	public boolean isCellEditable(int rowIdx, int colIdx) {
		return true;
	}
	
	
	@Override
	public void setValueAt(Object value, int rowIdx, int colIdx) {
		DataType dataType = allDataTypes.get(rowIdx);
		if (colIdx == 0)
			dataType.setName(value.toString());
		else if (colIdx == 1) {
			dataType.setActive(Boolean.parseBoolean(value.toString()));
			updateActiveDataTypes();
		}
		else if (colIdx == 2)
			dataType.setFileNameTag(value.toString());
		else if (colIdx == 3)
			dataType.setRangeActive(Boolean.parseBoolean(value.toString()));
		else if (colIdx == 4)
			dataType.setRangeStart(Integer.parseInt(value.toString()));
		else if (colIdx == 5)
			dataType.setRangeEnd(Integer.parseInt(value.toString()));
		else if (colIdx == 6)
			dataType.setInterval(Integer.parseInt(value.toString()));
		
		fireTableCellUpdated(rowIdx, colIdx);
		if (colIdx == 4)
			fireTableCellUpdated(rowIdx, 5);
		else if (colIdx == 5)
			fireTableCellUpdated(rowIdx, 4);
	}
	
}

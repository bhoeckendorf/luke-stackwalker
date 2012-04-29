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

import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class DataType implements ChangeListener {

	private String
		name = "",
		fileNameTag = "";
	private boolean
		active = true,
		rangeActive = false;
	private SpinnerNumberModel
		rangeStartSpinnerModel = new SpinnerNumberModel(0, 0, 99999, 1),
		rangeEndSpinnerModel = new SpinnerNumberModel(0, 0, 99999, 1),
		intervalSpinnerModel = new SpinnerNumberModel(1, 1, 999, 1);
	
	
	public DataType() {
		rangeStartSpinnerModel.addChangeListener(this);
		rangeEndSpinnerModel.addChangeListener(this);
	}
	
	
	public DataType(String name) {
		setName(name);
		rangeStartSpinnerModel.addChangeListener(this);
		rangeEndSpinnerModel.addChangeListener(this);
	}
	
	
	public void stateChanged(ChangeEvent event) {
		SpinnerNumberModel source = (SpinnerNumberModel)event.getSource();
		int value = source.getNumber().intValue();
		if (source == rangeStartSpinnerModel) {
			int rangeEndValue = getRangeEnd();
			if (rangeEndValue < value)
				setRangeEnd(value);
		}
		else {
			int rangeStartValue = rangeStartSpinnerModel.getNumber().intValue();
			if (rangeStartValue > value)
				setRangeStart(value);
		}
	}

	
	public void setName(String name) {
		this.name = name;
	}
	
	
	public String getName() {
		return name;
	}
	
	
	public void setActive(boolean state) {
		active = state;
	}
	
	
	public boolean isActive() {
		return active;
	}
	
	
	public void setRangeActive(boolean state) {
		rangeActive = state;
	}
	
	
	public boolean isRangeActive() {
		return rangeActive;
	}
	
	
	public void setFileNameTag(String tag) {
		fileNameTag = tag;
	}
	
	
	public String getFileNameTag() {
		return fileNameTag;
	}
	
	
	public void setRangeStart(int start) {
		rangeStartSpinnerModel.setValue(start);
	}
	
	
	public int getRangeStart() {
		return rangeStartSpinnerModel.getNumber().intValue();
	}
	
	
	public void setRangeEnd(int end) {
		rangeEndSpinnerModel.setValue(end);
	}
	
	
	public int getRangeEnd() {
		return rangeEndSpinnerModel.getNumber().intValue();
	}
	
	
	public void setInterval(int interval) {
		intervalSpinnerModel.setValue(interval);
	}
	
	
	public int getInterval() {
		return intervalSpinnerModel.getNumber().intValue();
	}
	
	
	public void setRange(int start, int end) {
		setRangeStart(start);
		setRangeEnd(end);
	}
	
	
	public void setRange(int start, int end, int interval) {
		setRange(start, end);
		setInterval(interval);
	}
	
	
	public SpinnerNumberModel getRangeStartSpinnerModel() {
		return rangeStartSpinnerModel;
	}
	
	
	public SpinnerNumberModel getRangeEndSpinnerModel() {
		return rangeEndSpinnerModel;
	}

	
	public SpinnerNumberModel getIntervalSpinnerModel() {
		return intervalSpinnerModel;
	}

}

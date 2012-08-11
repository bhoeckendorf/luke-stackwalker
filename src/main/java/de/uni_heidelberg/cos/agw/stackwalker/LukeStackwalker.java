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

package de.uni_heidelberg.cos.agw.stackwalker;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import de.uni_heidelberg.cos.agw.stackwalker.ui.MainFrame;


public class LukeStackwalker {


	// run as ImageJ plug-in
	public void run(String arg0) {
		String[] args = { arg0 };
		main(args);
	}
	
	
	// run standalone
	public static void main(String[] args) {
	    try {
	    	UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    }
	    catch (UnsupportedLookAndFeelException e) {}
	    catch (ClassNotFoundException e) {}
	    catch (InstantiationException e) {}
	    catch (IllegalAccessException e) {}
 	    
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
				    //DataSetModel dataSetModel = new DataSetModel();
				    DataSetTreeModel dataSetTreeModel = new DataSetTreeModel(new DataSetTreeModelNode());
					MainFrame frame = new MainFrame(dataSetTreeModel);
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.pack();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
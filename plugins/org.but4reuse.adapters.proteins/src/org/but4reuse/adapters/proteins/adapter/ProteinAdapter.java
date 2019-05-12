package org.but4reuse.adapters.proteins.adapter;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.but4reuse.adaptedmodel.manager.AdaptedModelManager;
import org.but4reuse.adapters.IAdapter;
import org.but4reuse.adapters.IElement;
import org.but4reuse.adapters.proteins.activator.Activator;
import org.but4reuse.adapters.proteins.methods.Context;
import org.but4reuse.adapters.proteins.methods.MethodAAC;
import org.but4reuse.adapters.proteins.methods.MethodCKSAAP;
import org.but4reuse.adapters.proteins.methods.MethodEAAC;
import org.but4reuse.adapters.proteins.methods.MethodEGAAC;
import org.but4reuse.adapters.proteins.preferences.ProteinsAdapterPreferencePage;
import org.but4reuse.adapters.proteins.utils.ProteinUtils;
import org.but4reuse.utils.files.FileUtils;
import org.but4reuse.utils.workbench.WorkbenchUtils;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.runtime.IProgressMonitor;

public class ProteinAdapter implements IAdapter {

	@Override
	public boolean isAdaptable(URI uri, IProgressMonitor monitor) {
		File file = FileUtils.getFile(uri);
		if (file != null && file.exists() && !file.isDirectory()&&ProteinUtils.isProteinFile(file.getName())) {
			return true;
		}
		return false;
	}

	@Override
	public List<IElement> adapt(URI uri, IProgressMonitor monitor) {
		ArrayList<String> letters = new ArrayList<String>() ;
		File file = FileUtils.getFile(uri);

		try {
			//read all the lines in the file
			FileInputStream fstream = new FileInputStream(file);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLetter;
			StringBuilder myStringBuilder= new StringBuilder();

			//to cut a protein file to small files  
			if(Activator.getDefault().getPreferenceStore().getBoolean(ProteinsAdapterPreferencePage.CUT_FILE)){
				String fileName = file.getName();
				String fileTyle=fileName.substring(fileName.lastIndexOf("."),fileName.length());

				IContainer output = AdaptedModelManager.getDefaultOutput();
				File outputFile = WorkbenchUtils.getFileFromIResource(output);
				File folder = new File(outputFile, "newFiles");
				folder.mkdir();
				FileWriter fw = null;

				while ((strLetter = br.readLine()) != null) {
					if(strLetter.startsWith(">")) {
						if(fileTyle.equals(".train")) {
							fw = new FileWriter(folder+"/"+strLetter.substring(1)+".train");
						}
						else if(fileTyle.equals(".test")) {
							fw = new FileWriter(folder+"/"+strLetter.substring(1)+".test");
						}
					}
					else {
						fw.append(strLetter + "\r\n");
						fw.close();
					}
				}
			}

			else {
				while ((strLetter = br.readLine()) != null) {
					myStringBuilder.append(strLetter);
				}

				//change to upper case letter if the letter is lower case letter
				for(Character p : myStringBuilder.toString().toUpperCase().toCharArray()){
					letters.add(p.toString());
				}
				in.close();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		//active the method that we choose
		if(Activator.getDefault().getPreferenceStore().getBoolean(ProteinsAdapterPreferencePage.METHOD_AAC)){
			Context context = new Context(new MethodAAC());
			return context.executeMethod(letters);
		}
		if(Activator.getDefault().getPreferenceStore().getBoolean(ProteinsAdapterPreferencePage.METHOD_CKSAAP)){
			Context context = new Context(new MethodCKSAAP());
			return context.executeMethod(letters);
		}
		if(Activator.getDefault().getPreferenceStore().getBoolean(ProteinsAdapterPreferencePage.METHOD_EAAC)){
			Context context = new Context(new MethodEAAC());
			return context.executeMethod(letters);
		}
		if(Activator.getDefault().getPreferenceStore().getBoolean(ProteinsAdapterPreferencePage.METHOD_EGAAC)){
			Context context = new Context(new MethodEGAAC());
			return context.executeMethod(letters);
		}		

		return null;
	}

	@Override
	public void construct(URI uri, List<IElement> elements, IProgressMonitor monitor) {
		try {
			// Use the given file or use a default name if a folder was given
			if (uri.toString().endsWith("/")) {
				uri = new URI(uri.toString() + "ProteinsConstruction.txt");
			}
			// Create file if it does not exist
			File file = FileUtils.getFile(uri);
			FileUtils.createFile(file);
			StringBuilder myStringBuilder= new StringBuilder();
			for (IElement element : elements) {
				ProteinElement proteinelement= (ProteinElement) element;
				int fre=proteinelement.frequency;
				for(int i =0; i<fre; i++){
					myStringBuilder.append(proteinelement.letter);	
				}
			} 
			FileUtils.appendToFile(file,myStringBuilder.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

package org.but4reuse.adapters.proteins.fca;

import java.io.File;
import java.io.IOException;

import org.but4reuse.adaptedmodel.AdaptedModel;
import org.but4reuse.adaptedmodel.helpers.AdaptedModelHelper;
import org.but4reuse.adaptedmodel.manager.AdaptedModelManager;
import org.but4reuse.adapters.proteins.activator.Activator;
import org.but4reuse.adapters.proteins.preferences.ProteinsAdapterPreferencePage;
import org.but4reuse.fca.utils.ContextProtein;
import org.but4reuse.fca.utils.FCAUtils;
import org.but4reuse.fca.utils.GenarateHTMLProtein;
import org.but4reuse.featurelist.FeatureList;
import org.but4reuse.utils.workbench.WorkbenchUtils;
import org.but4reuse.visualisation.IVisualisation;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.runtime.IProgressMonitor;


public class FCAVisualisation implements IVisualisation {

	@Override
	public void prepare(FeatureList featureList, AdaptedModel adaptedModel, Object extra, IProgressMonitor monitor) {
		if(Activator.getDefault().getPreferenceStore().getBoolean(ProteinsAdapterPreferencePage.CREAT_HTML)){

			monitor.subTask("Saving formal context analysis visualisations");
			// Here we try to find the folder to save it
			IContainer output = AdaptedModelManager.getDefaultOutput();
			File outputFile = WorkbenchUtils.getFileFromIResource(output);
			String name = AdaptedModelHelper.getName(adaptedModel);
			if (name == null) {
				name = "default";
			}

			// create folder
			File folder = new File(outputFile, "formalContextAnalysis");
			folder.mkdir();

			//creat html for protein
			ContextProtein fcProtein = FCAUtils.createArtefactsBlocksFormalContextProtein(adaptedModel);

			//new file for protein
			File filProtein = new File(folder, name + "_artefactsBlocksProtein.html");

			// new html for protein
			GenarateHTMLProtein htmlprotein = new GenarateHTMLProtein(fcProtein);
			htmlprotein.generateCode();

			try {
				//protein
				htmlprotein.toFile(filProtein.getAbsolutePath());
			} catch (IOException e) {
				e.printStackTrace();
			}

			// Refresh
			WorkbenchUtils.refreshIResource(output);
		}
	}
	@Override
	public void show() {
		// export is on prepare method
	}

}

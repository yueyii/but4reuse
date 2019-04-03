package org.but4reuse.adapters.ui.actions;

import java.io.File;
import java.io.IOException;

import org.but4reuse.adaptedmodel.AdaptedModel;
import org.but4reuse.adaptedmodel.helpers.AdaptedModelHelper;
import org.but4reuse.adaptedmodel.manager.AdaptedModelManager;
import org.but4reuse.fca.utils.ContextProtein;
import org.but4reuse.fca.utils.FCAUtils;
import org.but4reuse.fca.utils.GenarateHTMLProtein;
import org.but4reuse.utils.workbench.WorkbenchUtils;
import org.eclipse.contribution.visualiser.views.Menu;
import org.eclipse.core.resources.IContainer;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

public class ConstructProteinHtmlAction implements IViewActionDelegate{

	Menu menu;

	@Override
	public void run(IAction action) {
		AdaptedModel adaptedModel = AdaptedModelManager.getAdaptedModel();
		IContainer output = AdaptedModelManager.getDefaultOutput();
		File outputFile = WorkbenchUtils.getFileFromIResource(output);
		String name = AdaptedModelHelper.getName(adaptedModel);
		if (name == null) {
			name = "default";
		}

		// create folder
		File folder = new File(outputFile, "formalContextProteinAnalysis");
		folder.mkdir();

		ContextProtein fcProtein = FCAUtils.createArtefactsBlocksFormalContextProtein(adaptedModel);
		//new file for protein
		File filProtein = new File(folder, name + "_artefactsBlocksProtein.html");
		// new html for protein
		GenarateHTMLProtein htmlprotein = new GenarateHTMLProtein(fcProtein);
		htmlprotein.generateCode();
		//protein
		try {
			htmlprotein.toFile(filProtein.getAbsolutePath());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {

	}

	@Override
	public void init(IViewPart view) {
		menu = (Menu) view;
	}
}

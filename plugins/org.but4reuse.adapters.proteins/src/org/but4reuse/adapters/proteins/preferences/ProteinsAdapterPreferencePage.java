package org.but4reuse.adapters.proteins.preferences;

import org.but4reuse.adapters.proteins.activator.Activator;
import org.but4reuse.utils.ui.preferences.DoubleScaleFieldEditorProtein;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class ProteinsAdapterPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public static final String METHOD_AAC = "METHOD_AAC";
	public static final String METHOD_CKSAAP = "METHOD_CKSAAP";
	public static final String METHOD_EAAC = "METHOD_EAAC";
	public static final String METHOD_EGAAC = "METHOD_EGAAC";
	public static final String AUTOMATIC_EQUAL_THRESHOLD_PROTEIN = "automatic_threshold_protein";

	public ProteinsAdapterPreferencePage() {
		super(GRID);
		//setPreferenceStore(PreferencesHelper.getPreferenceStore());
		this.setPreferenceStore(Activator.getDefault().getPreferenceStore());
	}

	/**
	 * Creates the field editors.
	 */
	
	static IEclipsePreferences prefs = InstanceScope.INSTANCE
			.getNode(Activator.getDefault().getBundle().getSymbolicName());

	public static boolean isOnlyIdenticalMode() { 
		if (getAutomaticEqualThresholdProtein() == 1) {
			return true;
		}
		return false;
	}
	
	public static double getAutomaticEqualThresholdProtein() {
		return prefs.getDouble(AUTOMATIC_EQUAL_THRESHOLD_PROTEIN, 0.00);
	}
	
	public void createFieldEditors() {
		// This is to be used at your own risk. Use it if you are sure that the
		// matching is based on ids
		BooleanFieldEditor methodAAC = new BooleanFieldEditor(METHOD_AAC, "AAC",
				getFieldEditorParent());
		addField(methodAAC);
		
		BooleanFieldEditor methodCKSAAP = new BooleanFieldEditor(METHOD_CKSAAP, "CKSAAP",
				getFieldEditorParent());
		addField(methodCKSAAP);
		
		BooleanFieldEditor methodEAAC = new BooleanFieldEditor(METHOD_EAAC, "EAAC",
				getFieldEditorParent());
		addField(methodEAAC);
		
		BooleanFieldEditor methodEGAAC = new BooleanFieldEditor(METHOD_EGAAC, "EGAAC",
				getFieldEditorParent());
		addField(methodEGAAC);
		
		DoubleScaleFieldEditorProtein autoEqualFieldEditor = new DoubleScaleFieldEditorProtein(
				AUTOMATIC_EQUAL_THRESHOLD_PROTEIN, "Minimum percentage for automatic equal: ",
				getFieldEditorParent());
		addField(autoEqualFieldEditor);
		}

	@Override
	public void init(IWorkbench workbench) {
	}

}
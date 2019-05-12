package org.but4reuse.adapters.proteins.preferences;

import org.but4reuse.adapters.proteins.activator.Activator;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;


public class PreferenceInitializer extends AbstractPreferenceInitializer {

	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setDefault(ProteinsAdapterPreferencePage.METHOD_AAC, false);
		store.setDefault(ProteinsAdapterPreferencePage.METHOD_CKSAAP, false);
		store.setDefault(ProteinsAdapterPreferencePage.METHOD_EAAC, false);
		store.setDefault(ProteinsAdapterPreferencePage.METHOD_EGAAC, true);
		store.setDefault(ProteinsAdapterPreferencePage.CREAT_HTML, true);
		store.setDefault(ProteinsAdapterPreferencePage.CUT_FILE, true);
		store.setDefault(ProteinsAdapterPreferencePage.AUTOMATIC_EQUAL_THRESHOLD_PROTEIN, 0.00);
		store.setDefault(ProteinsAdapterPreferencePage.TRAIN_PROTEIN_FAMILY, "train_protein_family");

	}

}

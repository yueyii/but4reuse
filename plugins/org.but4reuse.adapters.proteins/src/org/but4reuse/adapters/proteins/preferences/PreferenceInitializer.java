package org.but4reuse.adapters.proteins.preferences;

import org.but4reuse.adapters.proteins.activator.Activator;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;


public class PreferenceInitializer extends AbstractPreferenceInitializer {

	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setDefault(ProteinsAdapterPreferencePage.METHOD_AAC, true);
		store.setDefault(ProteinsAdapterPreferencePage.METHOD_CKSAAP, false);
		store.setDefault(ProteinsAdapterPreferencePage.METHOD_EAAC, false);
		store.setDefault(ProteinsAdapterPreferencePage.METHOD_EGAAC, false);
		store.setDefault(ProteinsAdapterPreferencePage.PERCENTAGE, false);
	}

}

package org.but4reuse.fca.utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.but4reuse.adaptedmodel.AdaptedArtefact;
import org.but4reuse.adaptedmodel.AdaptedModel;
import org.but4reuse.adaptedmodel.Block;
import org.but4reuse.adaptedmodel.helpers.AdaptedModelHelper;
import org.but4reuse.adapters.ui.xmlgenerator.ArtefactData;
import org.but4reuse.adapters.ui.xmlgenerator.BlockData;
import org.but4reuse.artefactmodel.Artefact;
import org.but4reuse.featurelist.Feature;
import org.but4reuse.featurelist.FeatureList;
import org.but4reuse.featurelist.helpers.FeatureListHelper;

import fr.labri.galatea.BinaryAttribute;
import fr.labri.galatea.ConceptOrder;
import fr.labri.galatea.Context;
import fr.labri.galatea.Entity;
import fr.labri.galatea.algo.Ceres;

/**
 * Formal Context Utils
 * 
 * @author jabier.martinez
 * 
 */
public class FCAUtils {

	/**
	 * Create artefacts/blocks formal context
	 * 
	 * @param adaptedModel
	 * @return formal context
	 */
	public static Context createArtefactsBlocksFormalContext(AdaptedModel adaptedModel) {

		// Creates a formal context
		Context fc = new Context();

		// Creates an entity per artefact
		for (AdaptedArtefact aa : adaptedModel.getOwnedAdaptedArtefacts()) {
			Entity ent = new Entity(aa.getArtefact().getName());
			fc.addEntity(ent);
		}

		Map<String, BinaryAttribute> blockNameMap = new HashMap<String, BinaryAttribute>();

		// Creates a binary attribute per block
		for (Block block : adaptedModel.getOwnedBlocks()) {
			BinaryAttribute attr = new BinaryAttribute(block.getName());
			fc.addAttribute(attr);
			blockNameMap.put(block.getName(), attr);
		}

		// Add pairs
		for (AdaptedArtefact aa : adaptedModel.getOwnedAdaptedArtefacts()) {
			for (Block block : AdaptedModelHelper.getBlocksOfAdaptedArtefact(aa)) {
				fc.addPair(new Entity(aa.getArtefact().getName()), blockNameMap.get(block.getName()));
			}
		}

		return fc;
	}

	/**
	 * Create blocks/artefacts formal context
	 * 
	 * @param adaptedModel
	 * @return formal context
	 */
	public static Context createBlocksArtefactsFormalContext(AdaptedModel adaptedModel) {

		// Creates a formal context
		Context fc = new Context();

		// Creates an entity per block
		for (Block b : adaptedModel.getOwnedBlocks()) {
			Entity ent = new Entity(b.getName());
			fc.addEntity(ent);
		}

		Map<String, BinaryAttribute> artefactNameMap = new HashMap<String, BinaryAttribute>();

		// Creates a binary attribute per artefact
		for (AdaptedArtefact a : adaptedModel.getOwnedAdaptedArtefacts()) {
			BinaryAttribute attr = new BinaryAttribute(a.getArtefact().getName());
			fc.addAttribute(attr);
			artefactNameMap.put(a.getArtefact().getName(), attr);
		}

		// Add pairs
		for (AdaptedArtefact aa : adaptedModel.getOwnedAdaptedArtefacts()) {
			for (Block block : AdaptedModelHelper.getBlocksOfAdaptedArtefact(aa)) {
				fc.addPair(fc.getEntity(block.getName()), artefactNameMap.get(aa.getArtefact().getName()));
			}
		}

		return fc;
	}

	//for adapterProtein
	private static Map<BlockData, List<ArtefactData>> block_stats_reuse;

	public static String getPercentage(String blockName,String aretefactName) {
		String result = "";
		double value=0;
		DecimalFormat df = new DecimalFormat("#.####");
		for (BlockData n: block_stats_reuse.keySet()) {
			if(n.getName()==blockName) {
				for(ArtefactData ad : block_stats_reuse.get(n)) {
					if(ad.getName()==aretefactName) {
						value = n.getNbElem()*1.0/ad.getNb_elems()*100;
						result= String.valueOf(df.format(value));
					}
				}
			}
		}
		return result;	
	}


	public static ContextProtein createArtefactsBlocksFormalContextProtein(AdaptedModel adaptedModel) {

		// Creates a formal context
		ContextProtein fc = new ContextProtein();
		block_stats_reuse = new  HashMap<BlockData, List<ArtefactData>>();

		List<String> artefactsName = new ArrayList<String>();
		for(AdaptedArtefact aa : adaptedModel.getOwnedAdaptedArtefacts()) {
			artefactsName.add(AdaptedModelHelper.getArtefactName(aa.getArtefact()));	
		}

		for(Block b : adaptedModel.getOwnedBlocks()) {
			List<ArtefactData> listData = new ArrayList<ArtefactData>();
			for(int i=0 ; i< adaptedModel.getOwnedAdaptedArtefacts().size(); i++) {
				AdaptedArtefact aa =  adaptedModel.getOwnedAdaptedArtefacts().get(i);
				if( AdaptedModelHelper.getBlocksOfAdaptedArtefact(aa).contains(b) ) {
					List<Block> aa_block = AdaptedModelHelper.getBlocksOfAdaptedArtefact(aa);

					listData.add(
							new ArtefactData(aa_block.size(),
									AdaptedModelHelper.getElementsOfBlocks(aa_block).size(),
									artefactsName.get(i),
									aa)
							);
				}
				block_stats_reuse.put(new BlockData(b.getName(), AdaptedModelHelper.getElementsOfBlock(b)), listData);

			}
		}

		// Creates an entity per artefact
		for (AdaptedArtefact aa : adaptedModel.getOwnedAdaptedArtefacts()) {
			Entity ent = new Entity(aa.getArtefact().getName());
			fc.addEntity(ent);
		}

		Map<String, BinaryAttribute> blockNameMap = new HashMap<String, BinaryAttribute>();
	
		// Creates a binary attribute per block
		for (Block block : adaptedModel.getOwnedBlocks()) {
			BinaryAttribute attr = new BinaryAttribute(block.getName());
			fc.addAttribute(attr);
			blockNameMap.put(block.getName(), attr);
		}

		//create a binary attribute for average
		int compteur=0;
		float total=0;
		BinaryAttribute average = new BinaryAttribute("Average");
		fc.addAttribute(average);
		
		// Add pairs
		for (AdaptedArtefact aa : adaptedModel.getOwnedAdaptedArtefacts()) {
			for (Block block : AdaptedModelHelper.getBlocksOfAdaptedArtefact(aa)) {
				fc.addPair(fc.getEntity(aa.getArtefact().getName())
						, blockNameMap.get(block.getName()),getPercentage(block.getName(),aa.getArtefact().getName())
						);
				total+=Float.valueOf(getPercentage(block.getName(),aa.getArtefact().getName()));
				compteur++;
			}	
			//for calculate average
			fc.addPair(fc.getEntity(aa.getArtefact().getName()),average,String.valueOf(total/compteur));	
		}
		return fc;
	}

	/**
	 * Create Artefacts/Features formal context
	 * 
	 * @param featureList
	 * @return formal context
	 */
	public static Context createArtefactsFeaturesFormalContext(FeatureList featureList) {

		// Creates a formal context
		Context fc = new Context();

		// Creates an entity per artefact
		for (Artefact a : FeatureListHelper.getArtefactModel(featureList).getOwnedArtefacts()) {
			Entity ent = new Entity(a.getName());
			fc.addEntity(ent);
		}

		Map<String, BinaryAttribute> featureNameMap = new HashMap<String, BinaryAttribute>();

		// Creates a binary attribute per feature
		for (Feature feature : featureList.getOwnedFeatures()) {
			BinaryAttribute attr = new BinaryAttribute(feature.getName());
			fc.addAttribute(attr);
			featureNameMap.put(feature.getName(), attr);
		}

		// Add pairs
		for (Artefact a : FeatureListHelper.getArtefactModel(featureList).getOwnedArtefacts()) {
			for (Feature feature : FeatureListHelper.getArtefactFeatures(featureList, a)) {
				fc.addPair(fc.getEntity(a.getName()), featureNameMap.get(feature.getName()));
			}
		}

		return fc;
	}

	/**
	 * Create Features/Artefacts formal context
	 * 
	 * @param featureList
	 * @return formal context
	 */
	public static Context createFeaturesArtefactsFormalContext(FeatureList featureList) {

		// Creates a formal context
		Context fc = new Context();

		// Creates an entity per feature
		for (Feature f : featureList.getOwnedFeatures()) {
			Entity ent = new Entity(f.getName());
			fc.addEntity(ent);
		}

		Map<String, BinaryAttribute> artefactNameMap = new HashMap<String, BinaryAttribute>();

		// Creates a binary attribute per artefact
		for (Artefact a : FeatureListHelper.getArtefactModel(featureList).getOwnedArtefacts()) {
			BinaryAttribute attr = new BinaryAttribute(a.getName());
			fc.addAttribute(attr);
			artefactNameMap.put(a.getName(), attr);
		}

		// Add pairs
		for (Feature feature : featureList.getOwnedFeatures()) {
			for (Artefact a : FeatureListHelper.getArtefactModel(featureList).getOwnedArtefacts()) {
				if (feature.getImplementedInArtefacts().contains(a)) {
					fc.addPair(fc.getEntity(feature.getName()), artefactNameMap.get(a.getName()));
				}
			}
		}

		return fc;
	}

	/**
	 * Create concept lattice
	 * 
	 * @param formal
	 *            context
	 * @return concept lattice
	 */
	public static ConceptOrder createConceptLattice(Context fc) {
		Ceres algo = new Ceres(fc);
		algo.compute();
		return algo.getConceptOrder();
	}

	public static Context createArtefactsFeaturesAndBlocksFormalContext(FeatureList featureList,
			AdaptedModel adaptedModel) {
		// Creates a formal context
		Context fc = new Context();

		// Creates an entity per artefact
		for (AdaptedArtefact aa : adaptedModel.getOwnedAdaptedArtefacts()) {
			Entity ent = new Entity(aa.getArtefact().getName());
			fc.addEntity(ent);
		}

		Map<String, BinaryAttribute> featureNameMap = new HashMap<String, BinaryAttribute>();

		// Creates a binary attribute per feature
		for (Feature feature : featureList.getOwnedFeatures()) {
			BinaryAttribute attr = new BinaryAttribute("F: " + feature.getName());
			fc.addAttribute(attr);
			featureNameMap.put("F: " + feature.getName(), attr);
		}

		// Add pairs
		for (Artefact a : FeatureListHelper.getArtefactModel(featureList).getOwnedArtefacts()) {
			for (Feature feature : FeatureListHelper.getArtefactFeatures(featureList, a)) {
				fc.addPair(fc.getEntity(a.getName()), featureNameMap.get("F: " + feature.getName()));
			}
		}

		Map<String, BinaryAttribute> blockNameMap = new HashMap<String, BinaryAttribute>();

		// Creates a binary attribute per block
		for (Block block : adaptedModel.getOwnedBlocks()) {
			BinaryAttribute attr = new BinaryAttribute("B: " + block.getName());
			fc.addAttribute(attr);
			blockNameMap.put("B: " + block.getName(), attr);
		}

		// Add pairs
		for (AdaptedArtefact aa : adaptedModel.getOwnedAdaptedArtefacts()) {
			for (Block block : AdaptedModelHelper.getBlocksOfAdaptedArtefact(aa)) {
				fc.addPair(fc.getEntity(aa.getArtefact().getName()), blockNameMap.get("B: " + block.getName()));
			}
		}

		return fc;
	}
}

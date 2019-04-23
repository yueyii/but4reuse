package org.but4reuse.adapters.proteins.fca;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.but4reuse.adaptedmodel.AdaptedArtefact;
import org.but4reuse.adaptedmodel.AdaptedModel;
import org.but4reuse.adaptedmodel.Block;
import org.but4reuse.adaptedmodel.helpers.AdaptedModelHelper;
import org.but4reuse.adapters.proteins.activator.Activator;
import org.but4reuse.adapters.proteins.preferences.ProteinsAdapterPreferencePage;
import org.but4reuse.adapters.ui.xmlgenerator.ArtefactData;
import org.but4reuse.adapters.ui.xmlgenerator.BlockData;

import fr.labri.galatea.BinaryAttribute;
import fr.labri.galatea.Entity;

public class FCAUtils {
	//for adapterProtein
	private static Map<BlockData, List<ArtefactData>> block_stats_reuse;
	private static ArrayList<String> proteinlist;
	private static ArrayList<String> secondproteinlist;
	private static ArrayList<String> thirdproteinlist;
	static String str;
	static int count = 1;
	
//	public static String getPercentage(String blockName,String aretefactName) {
//		String result = "";
//		double value=0;
//		double valueWithProportion=0;
//		//int nbProtein = AdaptedModelManager.getAdaptedModel().getOwnedAdaptedArtefacts().size();
//		DecimalFormat df = new DecimalFormat("#.####");
//		for (BlockData n: block_stats_reuse.keySet()) {
//			if(n.getName()==blockName) {
//				int count = block_stats_reuse.get(n).size();
//				for(ArtefactData ad : block_stats_reuse.get(n)) {
//					if(ad.getName()==aretefactName) {
//						double proportion = Math.pow(2,count);
//						//write down the proportion of proteins which are in the same family
//						//proportionList.put(blockName, proportion);
//						value = n.getNbElem()*1.0/ad.getNb_elems()*100;
//						valueWithProportion=value*proportion;	
//						result = String.valueOf(df.format(valueWithProportion));
//					}
//				}
//			}
//		}
//		return result;	
//	}

	public static String getPercentage(String blockName,String aretefactName) {
		String result = "";
		double value=0;
		double valueWithProportion=0;
		//int nbProtein = AdaptedModelManager.getAdaptedModel().getOwnedAdaptedArtefacts().size();
		DecimalFormat df = new DecimalFormat("#.####");
		for (BlockData n: block_stats_reuse.keySet()) {
			//if there is the protein in diffrent familiy
			if(n.getName()==blockName && proteinlist.contains(blockName)) {
				//then we dont count it into poid
				int count = block_stats_reuse.get(n).size()-1;
				for(ArtefactData ad : block_stats_reuse.get(n)) {
					if(ad.getName()==aretefactName){
						double proportion=0;
						if(count==0) {
							proportion=0;
						}else {
							proportion = Math.pow(2,count);
						}
						value = n.getNbElem()*1.0/ad.getNb_elems()*100;
						valueWithProportion=value*proportion;	
						result = String.valueOf(df.format(valueWithProportion));
					}
				}
			}
			//if there is not the protein in diffrent familiy
			else if(n.getName()==blockName && !proteinlist.contains(blockName)) {
				System.out.println("blockName"+blockName);
				int count = block_stats_reuse.get(n).size();
				for(ArtefactData ad : block_stats_reuse.get(n)) {
					if(ad.getName()==aretefactName) {
						double proportion = Math.pow(2,count);
						value = n.getNbElem()*1.0/ad.getNb_elems()*100;
						valueWithProportion=value*proportion;	
						result = String.valueOf(df.format(valueWithProportion));
					}
				}
			}
		}
		return result;	
	}
	
	public static ContextProtein createArtefactsBlocksFormalContextProtein(AdaptedModel adaptedModel) {
		//todo
		String first = Activator.getDefault().getPreferenceStore()
		.getString(ProteinsAdapterPreferencePage.FIRST_PROTEIN_FAMILY);
		
		String second = Activator.getDefault().getPreferenceStore()
		.getString(ProteinsAdapterPreferencePage.SECOND_PROTEIN_FAMILY);
		
		String third = Activator.getDefault().getPreferenceStore()
		.getString(ProteinsAdapterPreferencePage.THIRD_PROTEIN_FAMILY);
		
		// Creates a formal context
		ContextProtein fc = new ContextProtein();
		block_stats_reuse = new  HashMap<BlockData, List<ArtefactData>>();

		List<String> artefactsName = new ArrayList<String>();
		proteinlist = new ArrayList<String>(); 
		
		for(AdaptedArtefact aa : adaptedModel.getOwnedAdaptedArtefacts()) {
			if(count==adaptedModel.getOwnedAdaptedArtefacts().size()) {
				str= AdaptedModelHelper.getArtefactName(aa.getArtefact());
			}
			artefactsName.add(AdaptedModelHelper.getArtefactName(aa.getArtefact()));	
			count ++;
		}

		for(Block b : adaptedModel.getOwnedBlocks()) {
			List<ArtefactData> listData = new ArrayList<ArtefactData>();

			for(int i=0 ; i< adaptedModel.getOwnedAdaptedArtefacts().size(); i++) {	
				AdaptedArtefact aa =  adaptedModel.getOwnedAdaptedArtefacts().get(i);			
				if( AdaptedModelHelper.getBlocksOfAdaptedArtefact(aa).contains(b)) {
					List<Block> aa_block = AdaptedModelHelper.getBlocksOfAdaptedArtefact(aa);
					if(artefactsName.get(i)==str) {
						proteinlist.add(b.getName());
					}
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
		//int compteur=0;
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
				//compteur++;
			}	
			//for calculate average /compteur
			fc.addPair(fc.getEntity(aa.getArtefact().getName()),average,String.valueOf(total/100));	
			total = 0; 
		}
		return fc;
	}

}

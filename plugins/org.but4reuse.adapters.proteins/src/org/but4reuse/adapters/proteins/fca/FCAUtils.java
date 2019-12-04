package org.but4reuse.adapters.proteins.fca;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.but4reuse.adaptedmodel.AdaptedArtefact;
import org.but4reuse.adaptedmodel.AdaptedModel;
import org.but4reuse.adaptedmodel.Block;
import org.but4reuse.adaptedmodel.helpers.AdaptedModelHelper;
import org.but4reuse.adapters.IElement;
import org.but4reuse.adapters.proteins.activator.Activator;
import org.but4reuse.adapters.proteins.preferences.ProteinsAdapterPreferencePage;
import org.but4reuse.adapters.proteins.utils.Count;
import org.but4reuse.adapters.ui.xmlgenerator.ArtefactData;
import org.but4reuse.adapters.ui.xmlgenerator.BlockData;

import fr.labri.galatea.BinaryAttribute;
import fr.labri.galatea.Entity;

public class FCAUtils {
	//for adapterProtein
	private static Map<BlockData, List<ArtefactData>> block_stats_reuse;
	private static Map<String, List<String>> train_name;

	private static ArrayList<String> train_proteinlist;
	private static String train = "";
	static String str;

	//calculate frequency of element in the block
	public static int countElementOfBlock(String filename, List<IElement> elementlist) {
		int count = 0;
		Count c = new Count();

		for (Entry<String, Map<IElement, Integer>> block : c.getMapBlock().entrySet()) {
			if(filename.equals(block.getKey())) {
				for (Entry<IElement, Integer> element : block.getValue().entrySet()) {
					if(elementlist.contains(element.getKey())) {
						count+=element.getValue();
					}
				}
			}
		}
		return count;
	}

	public static String getPercentage(String blockName,String aretefactName){
		String result = ""; 
		double value=0;
		double valueWithProportion=0;

		DecimalFormat df = new DecimalFormat("#.####");

		long startTime=System.currentTimeMillis();  	
		for (BlockData n: block_stats_reuse.keySet()) {
			double countsize=0;

			// the training protein in the block
			if(n.getName()==blockName) {	
				if(train_name.containsKey(blockName)) {
					countsize = train_name.get(blockName).size();
				} 
				for(ArtefactData ad : block_stats_reuse.get(n)) {
					if(ad.getName()==aretefactName){
						//percentage of the block 
						Count c = new Count();
						double nn= countElementOfBlock(ad.getName(),n.getList());
						double add = c.getCountFromName(ad.getName());
						value = nn / add ;	
						valueWithProportion=value*(countsize/Double.valueOf(train));
						result = String.valueOf(df.format(valueWithProportion*100));
					}
				}
			}
		}
		long endTime=System.currentTimeMillis(); 
		System.out.println("run time get percentage£º "+(endTime-startTime)+"ms");
		return result;	
	}

	public static ContextProtein createArtefactsBlocksFormalContextProtein(AdaptedModel adaptedModel) {
		train = Activator.getDefault().getPreferenceStore()
				.getString(ProteinsAdapterPreferencePage.TRAIN_PROTEIN_FAMILY);

		// Creates a formal context
		ContextProtein fc = new ContextProtein();
		block_stats_reuse = new  HashMap<BlockData, List<ArtefactData>>();
		List<String> artefactsName = new ArrayList<String>();
		train_proteinlist = new ArrayList<String>(); 
		train_name = new HashMap<>();

		int nb_train = Integer.valueOf(train);
		int count=0;

		//read all the proteins and save the training proteins in a list
		for(AdaptedArtefact aa : adaptedModel.getOwnedAdaptedArtefacts()) {
			String proteinName=AdaptedModelHelper.getArtefactName(aa.getArtefact());
			if(count<nb_train) {

				train_proteinlist.add(proteinName);
			}
			artefactsName.add(AdaptedModelHelper.getArtefactName(aa.getArtefact()));	
			count++;
		}

		for(Block b : adaptedModel.getOwnedBlocks()) {
			List<ArtefactData>listData = new ArrayList<ArtefactData>();
			ArrayList<String> proteinlist = new ArrayList<>() ;

			for(int i=0 ; i< adaptedModel.getOwnedAdaptedArtefacts().size(); i++) {	
				AdaptedArtefact aa =  adaptedModel.getOwnedAdaptedArtefacts().get(i);			

				if( AdaptedModelHelper.getBlocksOfAdaptedArtefact(aa).contains(b)) {
					List<Block> aa_block = AdaptedModelHelper.getBlocksOfAdaptedArtefact(aa);
					listData.add(
							new ArtefactData(aa_block.size(),
									AdaptedModelHelper.getElementsOfBlocks(aa_block).size(),
									artefactsName.get(i),
									aa)
							);

					if(train_proteinlist.contains(artefactsName.get(i))) {
						if(!train_name.containsKey(b.getName())
								||!train_name.get(b.getName()).contains(artefactsName.get(i))) {
							proteinlist.add(artefactsName.get(i));
						}
					}

				}
				//Add 

				train_name.put(b.getName(), proteinlist);
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
		BinaryAttribute average = new BinaryAttribute("Sum");
		fc.addAttribute(average);

		// Add pairs
		for (AdaptedArtefact aa : adaptedModel.getOwnedAdaptedArtefacts()) {
			for (Block block : AdaptedModelHelper.getBlocksOfAdaptedArtefact(aa)) {
				fc.addPair(fc.getEntity(aa.getArtefact().getName())
						, blockNameMap.get(block.getName()),getPercentage(block.getName(),aa.getArtefact().getName())
						);
				total+=Float.valueOf(getPercentage(block.getName(),aa.getArtefact().getName()));
			}	
			fc.addPair(fc.getEntity(aa.getArtefact().getName()),average,String.valueOf(total));	
			total = 0; 
		}

		return fc;
	}

}

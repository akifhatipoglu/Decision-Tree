package c45;


import grafik.TreeForC45;
import grafik.TreeForID3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Map.Entry;


import java.util.concurrent.LinkedBlockingQueue;

import main.Sample;
import main.SampleCollection;
import main.arayüz;

public class C45DecisionTree {

	public static int ONBUDAMA = -1;
	public C45DecisionTreeNode rootNode;
	private Queue<C45DecisionTreeNode> levelOrderdt = new LinkedBlockingQueue<C45DecisionTreeNode>();
	
	public C45DecisionTree(SampleCollection sampleColl) {
		System.out.println("Integer sütunlar:"+sampleColl.integerFeature);
		System.out.println();
		SayisalVerilerinDonusumu say = new SayisalVerilerinDonusumu(sampleColl.integerFeature,sampleColl.sampleList,sampleColl.FEATURES.getFeaturesList());
		say.toDiscrete(sampleColl.sampleList);
		
		
		for (Sample string : sampleColl.sampleList) {
			System.out.println(string);
			
		}
		
		
		List<Sample> samplesList = sampleColl.sampleList;
		System.out.println( sampleColl.FEATURES.subFeatureList.size());
		for (int i = 0; i < sampleColl.FEATURES.subFeatureList.size(); i++) {
			System.out.println(samplesList);
			//System.exit(0);
			for (Sample sample : samplesList) {
				
				sample.addDescreteVal(i, sample.getValue(i));
			}
		}
		
		rootNode = C45(sampleColl.FEATURES.subFeatureList,
				sampleColl.SINIF.sinifEtiketleri, SampleCollection.sampleList);
		
		new TreeForC45(this);
	
	}


	private C45DecisionTreeNode C45(List<String> FeatureList,ArrayList<String> sinifEtiketleri, List<Sample> sampleList) {
		
		C45DecisionTreeNode geciciNode = null; 
		// null kalkacak
		
		if (sampleList.size() == 0) {
			System.out.println("Hata sample list boş");
			System.exit(1);
		}
		if (FeatureList.isEmpty()) {
			geciciNode = new C45DecisionTreeNode(true, getProminentClass(sampleList));
			return geciciNode;
		}
		
		if(isCheckSinifEtiketi(sampleList)){
			geciciNode = new C45DecisionTreeNode(true, getProminentClass(sampleList));
			return geciciNode;
		}
		
		
		//Sütünun sayısal olarak değeri
		int bestFeatureIndex = getBestFeatureIndex(FeatureList, sampleList);
		
		if(bestFeatureIndex == ONBUDAMA){
			geciciNode = new C45DecisionTreeNode(true, getOnBuda(sampleList));
			return geciciNode;
		}
		
		System.out.println(bestFeatureIndex);
		
		Set<String> bestOfAttrVals = getUniqueValues(bestFeatureIndex, sampleList);
		System.out.println("bestOfAttrVals"+bestOfAttrVals);
		
		geciciNode = new C45DecisionTreeNode(FeatureList.get(bestFeatureIndex),bestOfAttrVals.size());
		
		List<String> subFeatures = new ArrayList<String>(FeatureList);
		subFeatures.remove(bestFeatureIndex);
		
		for (String attrVal : bestOfAttrVals) {
			
			List<Sample> subset = getSubset(sampleList, bestFeatureIndex, attrVal);
			// if(subset.size()==0)
			// System.out.println("there exist");
			
			C45DecisionTreeNode childNode = C45(new ArrayList<String>(subFeatures), sinifEtiketleri, subset);
			geciciNode.setChildNode(attrVal, childNode);
		}
		
		
		return geciciNode;
	}
	
	private String getOnBuda(List<Sample> sampleList) {
		double a = 0;
		String sinif = "";
		HashMap<String,Double> etiketler = new HashMap<String, Double>();
		for (Sample sample : sampleList) {
			etiketler.put(sample.getSampleSınıfEtiketi(), 0.0);
		}
		
		for (Sample sample : sampleList) {
				for(Entry<String, Double> entry : etiketler.entrySet()) {
					
					if (entry.getKey().equals(sample.getSampleSınıfEtiketi())){
						entry.setValue(entry.getValue() + 1 ); 
					}
				}
		}
		
		for(Entry<String, Double> entry : etiketler.entrySet()) {
			if(a<=entry.getValue()){
				a=entry.getValue();
				sinif = entry.getKey();
			}
		}
		
		return sinif;
	}


	private boolean isCheckSinifEtiketi(List<Sample> sampleList) {
		HashMap<String,String> etiketler = new HashMap<String, String>();
		for (Sample sample : sampleList) {
			etiketler.put(sample.getSampleSınıfEtiketi(), sample.getSampleSınıfEtiketi());
		}
		
		if(etiketler.size()==1)
			return true;
		
		return false;
	}


	private int getBestFeatureIndex(List<String> featureList, List<Sample> samples) {
		
		if (samples.size() == 0)
			System.out.println("Sample size 0 boş işte ");
		
		double maxInformationGain = Double.MIN_VALUE;
		int maxGainIndex = 0;
		for (int i = 0; i < featureList.size(); i++) {
			double infoGain = getInformationGain(samples, i);
			if (Double.compare(infoGain, maxInformationGain) > 0) {
				
				maxInformationGain = infoGain;
				maxGainIndex = i;
			}
		}
		 System.out.println("Max Informatin Gain : "+maxInformationGain);
		 if(maxInformationGain<= arayüz.threshold){
			 maxGainIndex = ONBUDAMA;
		 }
		return maxGainIndex;
	}
	
	public double getInformationGain(List<Sample> samples, int featureIndex) {
		double entropy = 0.0;
		Set<String> uniquevals = getUniqueValues(featureIndex, samples);
		System.out.println("Uniqevals"+uniquevals);
		for (String val : uniquevals) {
			HashMap<String,Double> etiketlerVeDegerleri = new HashMap<String, Double>();
			for (Sample sample : samples) {
				etiketlerVeDegerleri.put(sample.getSampleSınıfEtiketi(), 0.0);
			}
			for (Sample sample : samples) {
				if (sample.getDescreteValue(featureIndex).equals(val)) {
					for(Entry<String, Double> entry : etiketlerVeDegerleri.entrySet()) {
						if (entry.getKey().equals(sample.getSampleSınıfEtiketi())){
							entry.setValue(entry.getValue() + 1 ); 
						}
					}
				}
			}
			System.out.println("---------------------------");
			for(Entry<String, Double> entry : etiketlerVeDegerleri.entrySet()) {
				System.out.println(val+" : "+entry.getKey() +" --- "+entry.getValue());
			}
			
			
			double sumEtiket = 0;
			for(Entry<String, Double> entry : etiketlerVeDegerleri.entrySet()) {
				sumEtiket+= entry.getValue();
			}
			int sum = (int) sumEtiket;

			entropy += ((sumEtiket) / samples.size())*getEntropy(sum,etiketlerVeDegerleri);
			
			
			System.out.println("Entropy:"+entropy);
			
			System.out.println("----*-**/-*/-*/-*/-*/");
		}
		//KAZANC(OYUN;HAVA) = H(OYUN) - H(HAVA/OYUN)
		System.out.println("akif");
		
		entropy = getEntropyforALL(samples) - entropy;
		
		System.out.println("Entropy"+entropy);
		//System.exit(0);
		return entropy;
	}
	
	private Set<String> getUniqueValues(int bestFeature, List<Sample> samples) {
		Set<String> uniqvals = new HashSet<String>();
		for (Sample sample : samples) {
			uniqvals.add(sample.getDescreteValue(bestFeature));
			//System.out.println("UniqeVals"+sample+"-----"+sample.getDescreteValue(bestFeature)+"----"+bestFeature);
		}
		for (String string : uniqvals) {
			System.out.println("///"+string);
		}
		return uniqvals;
	}
	
	public double getEntropy(int size,HashMap<String,Double> etiketler) {
		double entropyforEtiketler = 0;
		
		HashMap<String,Double> etiketlerEntropy = new HashMap<String, Double>(etiketler);
		for(Entry<String, Double> entry : etiketlerEntropy.entrySet()) {
			entry.setValue(entry.getValue()/size);
		}
		
		for(Entry<String, Double> entry : etiketlerEntropy.entrySet()) {
			if(Double.compare(entry.getValue(), 0)==0){
				entry.setValue(1.0);
			}
		}
		
		for(Entry<String, Double> entry : etiketlerEntropy.entrySet()) {
			entropyforEtiketler += -(entry.getValue() * (Math.log(entry.getValue()) / Math.log(2)));
		}

		System.out.println("ent2 "+Math.abs(entropyforEtiketler));
		return Math.abs(entropyforEtiketler);
	}
	
	//Sınıf etiketi için
	public double getEntropyforALL(List<Sample> samples) {
		HashMap<String,Double> etiketler = new HashMap<String, Double>();
		for (Sample sample : samples) {
			etiketler.put(sample.getSampleSınıfEtiketi(), 0.0);
		}
		for(Entry<String, Double> entry : etiketler.entrySet()) {
			System.out.println(entry.getKey() +" --- "+entry.getValue());
		}
		for (Sample sample : samples) {
			for(Entry<String, Double> entry : etiketler.entrySet()) {
				if (entry.getKey().equals(sample.getSampleSınıfEtiketi())){
					entry.setValue(entry.getValue() + 1 ); 
				}
			}
		}
		for(Entry<String, Double> entry : etiketler.entrySet()) {
			System.out.println(entry.getKey() +" --- "+entry.getValue());
		}
		
		return getEntropy(samples.size(),etiketler);
	}


	private List<Sample> getSubset(List<Sample> samples, int bestFeatureIndex,String attrVal) {
		
		List<Sample> subset = new ArrayList<Sample>();
		for (Sample sample : samples) {
			if (sample.getDescreteValue(bestFeatureIndex).equals(attrVal)) {
				Sample newSample = new Sample(sample);
				newSample.removeVals(bestFeatureIndex);
				subset.add(newSample);
			}
		}
		
		return subset;
	}
	
	private String getProminentClass(List<Sample> samples) {
		String yaprak = "";
		for (Sample sample : samples) {
			System.out.println("sammm: "+sample+""+sample.getSampleSınıfEtiketi());
			yaprak = sample.getSampleSınıfEtiketi();
		}
		return yaprak;
	}
	
	/*private String levelOrderTraverse(ID3DecisionTreeNode rootNode2) {
		levelOrderdt.add(rootNode2);
		String returnString = "\n";
		while (!levelOrderdt.isEmpty()) {
			ID3DecisionTreeNode node = levelOrderdt.poll();
			if (node.isYaprak()) {
				returnString += "-> " + node.getNodesinifEtiketi();
			} else {
				returnString += node.getFeatureName();
				HashMap<String, ID3DecisionTreeNode> children = node.getChildren();
				String child = "";
				for (String val : children.keySet()) {
					levelOrderdt.add(children.get(val));
					child += "(" + val + ","
							+ children.get(val).getFeatureName() + ") ";
				}
				returnString += "\n" + child;
			}
		}
		return returnString;
	}
	*/
}

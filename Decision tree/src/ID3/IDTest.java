package ID3;

import java.util.HashMap;
import java.util.List;
import java.util.Queue;
import java.util.Map.Entry;
import java.util.concurrent.LinkedBlockingQueue;

import main.Sample;
import main.SampleCollection;

public class IDTest {
	private double accuracy;

	public double getAccuracy() {
		return accuracy;
	}

	private ID3DecisionTree dt;
	HashMap<String,Double> etiketler = new HashMap<String, Double>();
	
	public IDTest(ID3DecisionTree dt, SampleCollection testSamples, String typeOfTest) {
		this.dt = dt;
	
//		System.out.println(SampleCollection.FEATURES.subFeatureList);
		if(typeOfTest.equals("train")){
			classifyAndSetAccuracy(testSamples.sampleList,SampleCollection.FEATURES.subFeatureList);
		}
		if(typeOfTest.equals("test")){
			classifyAndSetAccuracy(testSamples.testList,SampleCollection.FEATURES.subFeatureList);
		}
}

	
	private void classifyAndSetAccuracy(List<Sample> samples,List<String> features) {
		double correct = 0, wrongs = 0;
	
		for (Sample sample : samples) {
			etiketler.put(sample.getSampleSınıfEtiketi(), 0.0);
		}
		for (Sample sample : samples) {
			String classifiedAs = classify(sample, features);
//			System.out.println("classifiedAs " + classifiedAs);
			if (classifiedAs.equals(sample.getSampleSınıfEtiketi()))
				correct++;
			else
				wrongs++;
		}
		accuracy = (correct / (correct + wrongs)) * 100;
	}

	private String classify(Sample sample, List<String> features) {
		ID3DecisionTreeNode root = dt.rootNode;
//		System.out.println("classifying  " + sample);
		while (true) {
			if (!root.isYaprak()) {
				String featureName = root.getFeatureName();
//				System.out.println(featureName);
				int featureindex = features.indexOf(featureName);
				String featureVal = sample.getDescreteValue(featureindex);
				if (root.getChildren().keySet().contains(featureVal)) {
					root = root.getChildren().get(featureVal);
				} else {
//					System.out.println("getting prominent");
					return prominentClass(root);
				}
			} else
				return root.getNodesinifEtiketi();
		}
	}

	private String prominentClass(ID3DecisionTreeNode root) {
		int pos = 0, neg = 0;
		Queue<ID3DecisionTreeNode> curr = new LinkedBlockingQueue<ID3DecisionTreeNode>();
		
		curr.add(root);
		while (!curr.isEmpty()) {
			ID3DecisionTreeNode node = curr.poll();
			if (node.isYaprak()) {
				
				for(Entry<String, Double> entry : etiketler.entrySet()) {
					if (entry.getKey().equals(node.getNodesinifEtiketi())){
						entry.setValue(entry.getValue() + 1 ); 
					}
				}
			} else {
				HashMap<String, ID3DecisionTreeNode> children = node.getChildren();
				for (String vals : children.keySet()) {
//					System.out.println("children " + children.get(vals));
					curr.add(children.get(vals));
				}
			}
			
		}
		
		String sinifEtiketi = "";
		Double counter = 0.0;
		for(Entry<String, Double> entry : etiketler.entrySet()) {
			if(entry.getValue()>=counter){
				sinifEtiketi = entry.getKey();
				counter = entry.getValue();
			}
		}
		
		
		
		return sinifEtiketi;
	}
	
	
	
}

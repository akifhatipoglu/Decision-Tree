package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;


public class Sample {
	private ArrayList<String> allSample = new ArrayList<String>();
	public ArrayList<String> subSample = new ArrayList<String>();
	public ArrayList<String> subDiscreteVals;
	private String sampleSınıfEtiketi;
	

	public static String Algorithm;
	public static String C45 = "C45";
	public static String ID3 = "ID3";

	public Sample(String sample) {
		StringTokenizer st = new StringTokenizer(sample, ",");
		int numTokens = st.countTokens();
		while (--numTokens != 0) {
			this.allSample.add(st.nextToken());
		}
		//System.out.println("All Feature Vals : "+allSample);
		sampleSınıfEtiketi = st.nextToken();
		new SinifEtiketi(sampleSınıfEtiketi);
		
		this.subSample = new ArrayList<String>(allSample);
		//System.out.println("Subsample"+subSample+""+subSample.size());
		this.subDiscreteVals = new ArrayList<String>(subSample.size());
		//System.out.println("sub discr size"+subDiscreteVals.size());
	}

	
	public Sample(Sample sample) {
		System.out.println("************************************************************************");
		this.subSample = new ArrayList<String>(sample.subSample);
		this.subDiscreteVals = new ArrayList<String>(sample.subDiscreteVals);
		this.sampleSınıfEtiketi = new String(sample.getSampleSınıfEtiketi());
	}
	
	
	public static boolean isNumeric(String str) {
		try {
			double d = Double.parseDouble(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
	
	/*public String getCellValue(int index) {
		return subSample.get(index);
	}*/
	public void addDescreteVal(int index, String descreteVal) {
		subDiscreteVals.add(index, descreteVal);
		//System.out.println(subDiscreteVals);
	}
	//Cell
	public String getDescreteValue(int index) {
		return subDiscreteVals.get(index);
	}

	public String getValue(int index) {
		return subSample.get(index);
	}
	
	public void setValue(int index,String element){
		this.subSample.set(index, element);
		this.allSample.set(index, element);
	}

	public void removeVals(int index) {
		this.subDiscreteVals.remove(index);
		this.subSample.remove(index);
	}
	
	
	@Override
	public String toString() {
		int i = 0;
		String sample = "Sample's : [";
		for (String feature : subSample) {
			if (i < SampleCollection.FEATURES.subFeatureList.size()){
				sample += SampleCollection.FEATURES.subFeatureList.get(i) + "="
						+ feature + " ";
			}
			i++;
		}
		sample += " ] " + sampleSınıfEtiketi;
		return sample;
	}
	
	public String getSampleSınıfEtiketi() {
		return sampleSınıfEtiketi;
	}
}

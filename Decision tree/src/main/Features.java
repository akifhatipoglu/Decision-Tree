package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Features {
	public List<String> FeaturesList = new ArrayList<String>();
	public List<String> subFeatureList = new ArrayList<String>();
	public  DataHolder dataHolder;
	
	
	public Features(DataHolder dataHolder) {
		super();
		this.dataHolder = dataHolder;
		String typeOfFile = dataHolder.getTypeOfFile();
		System.out.println("type: "+typeOfFile);
		if(typeOfFile.equals("csv")){
			ReadFeatureCSV(dataHolder.getDataset_Path());
		}
		if(typeOfFile.equals("arff")){
			ReadFeatureArff(dataHolder.getDataset_Path());
		}
		if(typeOfFile.equals("Multiple")){
			ReadFeatureMultiple(dataHolder.getATTRIBUTES_FILE_NAME());
		}
		getFeaturesList();
	}
	
	
	
	
	private void ReadFeatureMultiple(String dataset_Path) {
		try {
			BufferedReader bf = new BufferedReader(new FileReader(dataset_Path));
			String feature = bf.readLine();
			while (feature != null) {
				this.FeaturesList.add(feature);
				feature = bf.readLine();
			}
			this.subFeatureList = new ArrayList<String>(this.FeaturesList);
		} catch (FileNotFoundException e) {
			System.out.println("dosya bulunamadı...");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Satır okuma hatası");
			e.printStackTrace();
		}
	}
	
	



	private void ReadFeatureArff(String dataset_Path) {
		try {
			System.out.println("hhsahashsassd");
			BufferedReader bf = new BufferedReader(new FileReader(dataset_Path));
			String feature = bf.readLine();
			while (feature != null) {
				if(feature.contains("@ATTRIBUTE")||feature.contains("@attribute")){
					feature = feature.substring(11,feature.lastIndexOf(" "));
					this.FeaturesList.add(feature);
			
				}
				feature = bf.readLine();
			}
			System.out.println(FeaturesList);
			//System.exit(0);
			this.FeaturesList.remove(FeaturesList.size()-1);
			this.subFeatureList = new ArrayList<String>(this.FeaturesList);
		} catch (FileNotFoundException e) {
			System.out.println("dosya bulunamadı...");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Satır okuma hatası");
			e.printStackTrace();
		}
	}




	private void ReadFeatureCSV(String path) {
		try {
			BufferedReader bf = new BufferedReader(new FileReader(path));
			String rowFeature = bf.readLine();
			StringTokenizer st = new StringTokenizer(rowFeature, ",");
			int contTokens = st.countTokens()-1;
			while (st.hasMoreTokens()) {
				this.FeaturesList.add(st.nextToken());
			}
			FeaturesList.remove(contTokens);
			//System.out.println(FeaturesList);
			//System.exit(0);
			this.subFeatureList = new ArrayList<String>(this.FeaturesList);
		} catch (FileNotFoundException e) {
			System.out.println("dosya bulunamadı...");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Satır okuma hatası");
			e.printStackTrace();
		}
	}

	
	

	public List<String> getFeaturesList() {
		System.out.println(FeaturesList);
		System.out.println("\nFeature list size : "+FeaturesList.size());
		return FeaturesList;
	}
	public void setFeaturesList(List<String> featuresList) {
		FeaturesList = featuresList;
	}
	
	
	@Override
	public String toString() {
		int i=0;
		String features = "Features [";
		for (String feature : subFeatureList) {
			features += i+"="+feature + " ";
			i++;
		}
		features += "]";
		return features;
	}

}

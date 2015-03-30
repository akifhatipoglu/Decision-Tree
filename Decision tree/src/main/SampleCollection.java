package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;



public class SampleCollection {
	public static DataHolder dataHolder;
	public static Features FEATURES;
	public static Sample SAMPLE;
	public static SinifEtiketi SINIF;
	public static List<Sample> sampleList = new ArrayList<Sample>();// train data	
	public static List<Sample> testList = new ArrayList<Sample>(); // test data
	public static List<Integer> integerFeature = new ArrayList<Integer>();
	
	public SampleCollection(DataHolder dataholder) {
		this.dataHolder= dataholder;
		
		initFeatures();
		
		initSamples(sampleList);

		initClass();
	}
	
	private void initFeatures() {
		FEATURES = new Features(this.dataHolder);
	}
	
	private void initSamples( List<Sample> sampleList) {
		//SAMPLE = new Sample(this.dataHolder);
		String typeOfFile = dataHolder.getTypeOfFile();
		System.out.println("type: "+typeOfFile);
		if(typeOfFile.equals("csv")){
			ReadSampleCSV(dataHolder.getDataset_Path(),sampleList);
		}
		if(typeOfFile.equals("arff")){
			ReadSampleArff(dataHolder.getDataset_Path(),sampleList);
		}
		if(typeOfFile.equals("Multiple")){
			ReadSampleMultiple(dataHolder.getTRAINING_SAMPLES_FILE_NAME(),sampleList);
			ReadTestSampleMultiple(dataHolder.getTESTING_SAMPLES_FILE_NAME(),testList);
		}
		
		
		for (Sample sample : sampleList) {
			for (String sinif : SINIF.sinifEtiketleri) {
				if(sample.getSampleSınıfEtiketi().equals(sinif)){
					
				}
			}
		}
	}

	private void initClass() {
		SINIF = new SinifEtiketi();
		System.out.println(SINIF.sinifEtiketleri.toString());
	}

	
	private void ReadSampleMultiple(String dataset_Path ,List<Sample> sampleList) {
		try {
			BufferedReader bf = new BufferedReader(new FileReader(dataset_Path));
			String rowSample = bf.readLine();
			boolean lock = true;
			while (rowSample != null) {
				System.out.println("inlock "+rowSample);
				if(lock){
					isNumerical(rowSample);
					lock = false;
				}
				if(!isKayipData(rowSample)){
					sampleList.add(new Sample(rowSample));
					System.out.println("son "+sampleList.get(sampleList.size()-1));
					System.out.println("-----------------------");
					rowSample = bf.readLine();
				}else{
					rowSample = bf.readLine();
				}
			}
			//System.out.println("sampleList"+sampleList);
			System.out.println("Algorithm: "+SAMPLE.Algorithm);
		} catch (FileNotFoundException e) {
			System.out.println("dosya bulunamadı...");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Satır okuma hatası");
			e.printStackTrace();
		}	
	}

	private void ReadSampleArff(String dataset_Path, List<Sample> sampleList) {
		try {
			BufferedReader bf = new BufferedReader(new FileReader(dataset_Path));
			String rowSample = bf.readLine();
			boolean lock = false;
			while (rowSample != null) {
				if(rowSample.equals("@DATA")||rowSample.equals("@data")){
					lock = true;
					rowSample = bf.readLine();
					isNumerical(rowSample);
					System.out.println("ikinci "+rowSample);
				}
				if(lock){
					if(!isKayipData(rowSample)){
						System.out.println("inlock "+rowSample);
						sampleList.add(new Sample(rowSample));
						System.out.println("son "+sampleList.get(sampleList.size()-1));
						System.out.println("-----------------------");
					}else{
						rowSample = bf.readLine();
					}
				}
				rowSample = bf.readLine();
			}
			
			for (Sample sample : sampleList) {
				System.out.println(sample);
			}
			
			System.out.println("Size :"+sampleList.size());
			
			int testDataNumber = (int)( (sampleList.size()*75)/100 );
			
			System.out.println("test data number : "+testDataNumber);
		
			System.out.println("train data number :"+(sampleList.size()-testDataNumber));
			
			int counter = 0;
			for (Sample sample : sampleList) {
				if(counter >= testDataNumber){
					Sample newsample = new Sample(sample);
					testList.add(newsample);
				}
				counter++;
			}
			System.out.println(testList.size());
			
			for (int i = sampleList.size()-1; i>=testDataNumber; i--) {
				//System.out.println(i+"-"+sampleList.get(i));
				//testList.add(sampleList.get(i));
				sampleList.remove(i);				
			}
			
			System.out.println("train list");
			for (Sample sample : sampleList) {
				System.out.println(sample);
			}
			System.out.println("testList");
			for (Sample sample : testList) {
				System.out.println(sample);
			}
			
			//System.exit(0);
			
			
			System.out.println("Algorithm: "+SAMPLE.Algorithm);
		} catch (FileNotFoundException e) {
			System.out.println("dosya bulunamadı...");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Satır okuma hatası");
			e.printStackTrace();
		}		
	}

	private void ReadSampleCSV(String dataset_Path, List<Sample> sampleList) {
		try {
			BufferedReader bf = new BufferedReader(new FileReader(dataset_Path));
			String rowSample = bf.readLine();
			System.out.println("ilk "+rowSample);
			boolean lock = false;
			while (rowSample != null) {
				if(lock){
						System.out.println("inlock "+rowSample);
						if(!isKayipData(rowSample)){
							sampleList.add(new Sample(rowSample));
							System.out.println("son "+sampleList.get(sampleList.size()-1));
							rowSample = bf.readLine();
							System.out.println("-----------------------");
						}else{
							rowSample = bf.readLine();
						}
				}else{
					lock = true;
					rowSample = bf.readLine();
					isNumerical(rowSample);
					System.out.println("ikinci "+rowSample);
				}
			}
			//System.out.println("sampleList"+sampleList);
			
			for (Sample sample : sampleList) {
				System.out.println(sample);
			}
			
			
			System.out.println("Size :"+sampleList.size());
			
			int testDataNumber = (int)( (sampleList.size()*75)/100 );
			
			System.out.println("test data number : "+testDataNumber);
		
			System.out.println("train data number :"+(sampleList.size()-testDataNumber));
			
			
			int counter = 0;
			for (Sample sample : sampleList) {
				if(counter >= testDataNumber){
					Sample newsample = new Sample(sample);
					testList.add(newsample);
				}
				counter++;
			}
			System.out.println(testList.size());
			
			for (int i = sampleList.size()-1; i>=testDataNumber; i--) {
				//System.out.println(i+"-"+sampleList.get(i));
				//testList.add(sampleList.get(i));
				sampleList.remove(i);				
			}
			
			System.out.println("train list");
			for (Sample sample : sampleList) {
				System.out.println(sample);
			}
			System.out.println("testList");
			for (Sample sample : testList) {
				System.out.println(sample);
			}
			
			
			System.out.println("Algorithm: "+SAMPLE.Algorithm);
		} catch (FileNotFoundException e) {
			System.out.println("dosya bulunamadı...");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Satır okuma hatası");
			e.printStackTrace();
		}		
	}
	
	private void ReadTestSampleMultiple(String dataset_Path ,List<Sample> testList) {
		try {
			BufferedReader bf = new BufferedReader(new FileReader(dataset_Path));
			String rowSample = bf.readLine();
			boolean lock = true;
			while (rowSample != null) {
				System.out.println("inlock "+rowSample);
				if(lock){
					//isNumerical(rowSample);
					//System.out.println("row"+rowSample);
					lock = false;
				}
				if(!isKayipData(rowSample)){
					testList.add(new Sample(rowSample));
					System.out.println("son "+testList.get(testList.size()-1));
					System.out.println("-----------------------");
					rowSample = bf.readLine();
				}else{
					rowSample = bf.readLine();
				}
			}
			//System.exit(0);
			
			System.out.println("train list");
			for (Sample sample : sampleList) {
				System.out.println(sample);
			}
			System.out.println("testList");
			for (Sample sample : testList) {
				System.out.println(sample);
			}
			
			//System.exit(0);
			
			//System.out.println("sampleList"+sampleList);
			System.out.println("Algorithm: "+SAMPLE.Algorithm);
		} catch (FileNotFoundException e) {
			System.out.println("dosya bulunamadı...");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Satır okuma hatası");
			e.printStackTrace();
		}	
	}
	
	
	
	
	private boolean isKayipData(String rowSample) {
		boolean kayipveri = false;
		if(rowSample.contains("?")){
			kayipveri = true;
			System.out.println("Kayıp veri");
		}
		return kayipveri;
	}
	public static void isNumerical(String str){
		StringTokenizer st = new StringTokenizer(str, ",");
		int numTokens = st.countTokens();
		int counter = st.countTokens();
		boolean lockforAlgorithm = false;
		while (--numTokens != 0) {
			if(isNumeric(st.nextToken())){
				lockforAlgorithm = true;
				integerFeature.add(counter-numTokens-1);
				//System.out.println(integerFeature);
			}
		}
		if(lockforAlgorithm){
			SAMPLE.Algorithm = SAMPLE.C45;
		}else{
			SAMPLE.Algorithm = SAMPLE.ID3;
		}
	}
	
	public static boolean isNumeric(String str) {
		try {
			double d = Double.parseDouble(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
	
	
	
	@Override
	public String toString() {
		return FEATURES + "\n" + sampleList + "\n" /*+ CLASS*/+"\n"+SAMPLE.Algorithm;
	}
}

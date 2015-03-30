package main;

import java.awt.List;
import java.util.ArrayList;

public class DataHolder {
	private String TRAINING_SAMPLES_FILE_NAME;
	private String TESTING_SAMPLES_FILE_NAME;
	private String ATTRIBUTES_FILE_NAME;
	private String Dataset_Path;
	private String TypeOfFile;

	public DataHolder() {
	}
	
	public DataHolder(String path,String typeOfFile) {
		this.setDataset_Path(path);
		this.setTypeOfFile(typeOfFile);
	}
	
	public DataHolder(ArrayList<String> list ,String typeOfFile) {
		super();
		for (String string : list) {
			if (string.contains(".test")) {
				this.setTESTING_SAMPLES_FILE_NAME(string);
			}
			if (string.contains(".train")) {
				this.setTRAINING_SAMPLES_FILE_NAME(string);
			}
			if (string.contains(".attribute")) {
				this.setATTRIBUTES_FILE_NAME(string);
			}
		}
		this.setTypeOfFile(typeOfFile);
	}
	
	
	public String getTypeOfFile() {
		return TypeOfFile;
	}
	public void setTypeOfFile(String typeOfFile) {
		System.out.println(typeOfFile);
		TypeOfFile = typeOfFile;
	}
	public String getDataset_Path() {
		return Dataset_Path;
	}

	public void setDataset_Path(String dataset_Path) {
		Dataset_Path = dataset_Path;
	}

	public String getTRAINING_SAMPLES_FILE_NAME() {
		return TRAINING_SAMPLES_FILE_NAME;
	}

	public void setTRAINING_SAMPLES_FILE_NAME(String tRAINING_SAMPLES_FILE_NAME) {
		TRAINING_SAMPLES_FILE_NAME = tRAINING_SAMPLES_FILE_NAME;
	}

	public String getTESTING_SAMPLES_FILE_NAME() {
		return TESTING_SAMPLES_FILE_NAME;
	}

	public void setTESTING_SAMPLES_FILE_NAME(String tESTING_SAMPLES_FILE_NAME) {
		TESTING_SAMPLES_FILE_NAME = tESTING_SAMPLES_FILE_NAME;
	}

	public String getATTRIBUTES_FILE_NAME() {
		return ATTRIBUTES_FILE_NAME;
	}

	public void setATTRIBUTES_FILE_NAME(String aTTRIBUTES_FILE_NAME) {
		ATTRIBUTES_FILE_NAME = aTTRIBUTES_FILE_NAME;
	}

}

package c45;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.swing.JOptionPane;

import main.Features;
import main.Sample;
import main.arayüz;

public class SayisalVerilerinDonusumu {

	public List<Integer> integerFeature;
	public List<Sample> sampleList;
	public List<Sample> newSampleList;
	public List<String> featureList;
	public SayisalVerilerinDonusumu(List<Integer> integerFeature,List<Sample> sampleList, List<String> featureList) {
		this.integerFeature = integerFeature;
		this.sampleList = sampleList;
		this.newSampleList = sampleList;
		this.featureList = featureList;
	}
	
	public void toDiscrete(List<Sample> sampleList2){

		for (Integer sutun : this.integerFeature) {
			
			System.out.println("Sutun"+sutun);
			String featureName =this.featureList.get(sutun);
			System.out.println("Feature:"+featureName);
			double split = Double.parseDouble(JOptionPane.showInputDialog(featureName+" adlı sayısal içeriğe sahip feature için kaç alt parçaya bölünsün"));
			if(split<=1.0 || split>=20.0){
				split = 2.0;
			}
			List<Double> column = new LinkedList<Double>();
			
			for (Sample sample : this.sampleList) {
				System.out.print("-"+sample.getValue(sutun));
				column.add(Double.parseDouble(sample.getValue(sutun)));
			}
			System.out.println();
			
			List<Double> columnTwo = new LinkedList<Double>(column);
			System.out.println("columnTWo"+columnTwo);
			Collections.sort(column);
			System.out.println("Siralanmış hali "+column);
			int columSize = column.size();
			
			System.out.println("column size:"+columSize);
			double range = columSize/split;
			int rangeRounded = (int) Math.round(range);// ,5 den küçük ise 
			System.out.println("range roun edilmiş"+Math.round(range)+"+-"+rangeRounded);
			
			ArrayList<String> splitName = new ArrayList<String>();
			for (int i = 0; i < columSize; i+=rangeRounded) {
				int x = i +rangeRounded;
				if(x >= columSize) {
					x = columSize;
				}
				System.out.println(i+"+"+x);
				splitName.add(column.get(i)+"-"+column.get(x-1));
			}
			System.out.println("splitName"+splitName);
			List<String> newColumn = new ArrayList<String>();
			
			for (Double doub : columnTwo) {
				for (String name : splitName) {
					Double a = Double.parseDouble(name.substring(0,name.indexOf('-')));
					Double b = Double.parseDouble(name.substring(name.indexOf('-')+1,name.length()));
					System.out.println(a+"+"+b);
					if(a<=doub && doub<=b){
						newColumn.add(a+"ile"+b);
						System.out.println("=> "+a+"ile"+b);
						break;
					}
				}
			}
			
			
			System.out.println("columnTWo"+columnTwo);
			for (int i = 0; i < columnTwo.size(); i++) {
				System.out.println(columnTwo.get(i)+"  =>   "+newColumn.get(i));
			}
			int counter = 0;
			for (Sample string : sampleList2) {
				System.out.println(string.getValue(sutun));
				string.setValue(sutun, newColumn.get(counter));
				counter++;
			}
			
			
			
			
			/**
			 * buradan sonrasında string olarak güncelleyecez dataseti
			 * 
			 * 
			 * */
			
			//System.exit(0);
		}
	}

}

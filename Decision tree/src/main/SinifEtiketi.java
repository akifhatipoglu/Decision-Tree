package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class SinifEtiketi {
	
	public static HashMap<String,String> etiketler = new HashMap<String, String>();
	public static ArrayList<String> sinifEtiketleri = new ArrayList<String>();
	
		
	public SinifEtiketi(String etiket) {
		etiketler.put(etiket,etiket);
	}
	
	public SinifEtiketi() {
		for(Entry<String, String> entry : etiketler.entrySet()) {
		    String key = entry.getKey();
		    String value = entry.getValue();
		    sinifEtiketleri.add(key);
		}
	}
	
	public static ArrayList<String> getSinifEtiketleri() {
		return sinifEtiketleri;
	}
	

	@Override
	public String toString() {
		String sample = "SınıfEtiketleri : [";
		for (String iterable_element : sinifEtiketleri) {
			sample += iterable_element+" ";
		}
		sample += " ] ";
		return sample;
	}

}

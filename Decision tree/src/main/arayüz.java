package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.AbstractListModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import c45.C45DecisionTree;
import c45.c45Test;
import ID3.ID3DecisionTree;
import ID3.IDTest;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;

public class arayüz extends JFrame {

	private JPanel contentPane;
	JComboBox comboBox;
	public static double threshold;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					arayüz frame = new arayüz();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public arayüz() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 317, 299);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
	    comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Dataset türünüzü seçiniz", "CSV", "Arff", "Attributes,Train,Test"}));
		comboBox.setBounds(51, 98, 198, 20);
		contentPane.add(comboBox);
		
		JButton btnDosyaSe = new JButton("Dosya seç");
		btnDosyaSe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//System.out.println(comboBox.getSelectedIndex());
				
				threshold = 0;
				threshold = Double.parseDouble(JOptionPane.showInputDialog("Ön budama için Threshold değeri giriniz:"));
				
				if(comboBox.getSelectedIndex()!=0){
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.setCurrentDirectory(new File("."));
					fileChooser.setDialogTitle("Choose a file");
					fileChooser.setPreferredSize(new Dimension(500, 500));
					fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
					FileFilter filterCsv = new FileNameExtensionFilter("CSV", "csv");
					FileFilter filterTest = new FileNameExtensionFilter("Test", "test");
					FileFilter filterTrain = new FileNameExtensionFilter("Train", "train");
					FileFilter filterAttribute = new FileNameExtensionFilter("Attribute", "attribute");
					FileFilter filterArff = new FileNameExtensionFilter("Arff", "arff");
					fileChooser.addChoosableFileFilter(filterCsv);
					fileChooser.addChoosableFileFilter(filterTest);
					fileChooser.addChoosableFileFilter(filterTrain);
					fileChooser.addChoosableFileFilter(filterAttribute);
					fileChooser.addChoosableFileFilter(filterArff);
					
					if(comboBox.getSelectedIndex()==1){
						fileChooser.setFileFilter(filterCsv);
					}
					
					
					if(comboBox.getSelectedIndex()==2){
						fileChooser.setFileFilter(filterArff);
					}
					if(comboBox.getSelectedIndex()==3){
						fileChooser.setMultiSelectionEnabled(true);
						int result = fileChooser.showOpenDialog(null);
						if (result == JFileChooser.APPROVE_OPTION) {
							ArrayList<String> list = new ArrayList<String>();
							for(File f : fileChooser.getSelectedFiles()) {
								System.out.println("File selected: " + f.getPath());
								list.add(f.getPath());
						     }
							DataHolder dt = new DataHolder(list,"Multiple");
							initialProcess(dt);
						}
					}
					if(comboBox.getSelectedIndex()!=3){
						int result = fileChooser.showOpenDialog(null);
						if (result == JFileChooser.CANCEL_OPTION) {
							System.exit(1);
						}
		
						if (result == JFileChooser.APPROVE_OPTION) {
							File fileName = fileChooser.getSelectedFile();
							System.out.println("File selected: " + fileName.getPath());
							DataHolder dt = new DataHolder(fileName.getPath(),fileName.getPath().substring(fileName.getPath().indexOf('.')+1, fileName.getPath().length()));
							initialProcess(dt);
						}
					}
				}
			}
		});
		btnDosyaSe.setBounds(88, 129, 125, 23);
		contentPane.add(btnDosyaSe);
	}


	public void initialProcess(DataHolder dataHolder){
		SampleCollection sampleColl = new SampleCollection(dataHolder);
		//while (true) {
			if(Sample.Algorithm == Sample.ID3){
				ID3DecisionTree dt = new ID3DecisionTree(sampleColl);
				IDTest dtCtrain = new IDTest(dt,sampleColl,"train");
				System.out.println("Training Set Accuracy: "+ dtCtrain.getAccuracy());
				JOptionPane.showMessageDialog(this, "Training Set Accuracy: "+ dtCtrain.getAccuracy(), "Accuracy", JFrame.EXIT_ON_CLOSE);
				
				IDTest dtCtest = new IDTest(dt,sampleColl,"test");
				System.out.println("Test Set Accuracy: "+ dtCtest.getAccuracy());
				JOptionPane.showMessageDialog(this, "Test Set Accuracy: "+ dtCtest.getAccuracy(), "Accuracy", JFrame.EXIT_ON_CLOSE);
			
			}
			if(Sample.Algorithm == Sample.C45){
				C45DecisionTree dt = new C45DecisionTree(sampleColl);
				c45Test dtCtrain = new c45Test(dt,sampleColl,"train");
				System.out.println("Training Set Accuracy: "+ dtCtrain.getAccuracy());
				JOptionPane.showMessageDialog(this, "Training Set Accuracy: "+ dtCtrain.getAccuracy(), "Accuracy", JFrame.EXIT_ON_CLOSE);
				
				c45Test dtCtest = new c45Test(dt,sampleColl,"test");
				System.out.println("Test Set Accuracy: "+ dtCtest.getAccuracy());
				JOptionPane.showMessageDialog(this, "Test Set Accuracy: "+ dtCtest.getAccuracy(), "Accuracy", JFrame.EXIT_ON_CLOSE);
				
			}
			System.out.println("type of file : "+dataHolder.getTypeOfFile());
			
		}
		//System.out.println("stopped");
	//}
}

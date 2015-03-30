package grafik;

import java.awt.BorderLayout;
import java.awt.Container;
import java.util.HashMap;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;

import c45.C45DecisionTree;
import c45.C45DecisionTreeNode;
import main.Sample;
import ID3.ID3DecisionTree;
import ID3.ID3DecisionTreeNode;

public class TreeForC45 extends JFrame implements TreeSelectionListener {

	private JTree tree;
	private JTextField currentSelectionField;
	private Queue<C45DecisionTreeNode> levelOrderdt = new LinkedBlockingQueue<C45DecisionTreeNode>();

	public TreeForC45(C45DecisionTree dt) {
		super(Sample.Algorithm);
		
		addWindowListener(new ExitListener());
		
		WindowUtilities.setNativeLookAndFeel();
		
		Container content = getContentPane();

		MutableTreeNode gTreeRoot = new DefaultMutableTreeNode("");

		makeGraphicalTree(gTreeRoot,dt.rootNode);
		
		tree = new JTree(gTreeRoot);
		tree.addTreeSelectionListener(this);
		content.add(new JScrollPane(tree), BorderLayout.CENTER);
		currentSelectionField = new JTextField("Current Selection: NONE");
		content.add(currentSelectionField, BorderLayout.SOUTH);
		setSize(500, 575);
		setVisible(true);
	}
	//REKURSİF
	private void makeGraphicalTree(MutableTreeNode gTreeRoot,
			C45DecisionTreeNode rootNode) {
		
		if (rootNode.isYaprak()) {
			((DefaultMutableTreeNode) gTreeRoot).add(new DefaultMutableTreeNode(rootNode));
			return;
		}
		HashMap<String, C45DecisionTreeNode> children = rootNode.getChildren();
		int i=0;
		for (String val : children.keySet()) {
			((DefaultMutableTreeNode) gTreeRoot).add(new DefaultMutableTreeNode(rootNode.getFeatureName()+"="+val));
			makeGraphicalTree((MutableTreeNode) gTreeRoot.getChildAt(i++), children.get(val));
		}
	}

	public void valueChanged(TreeSelectionEvent event) {
		if(tree.getLastSelectedPathComponent()!=null)
		currentSelectionField.setText("Current Selection: "
				+ tree.getLastSelectedPathComponent().toString());
	}
}

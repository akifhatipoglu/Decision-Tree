package c45;

import java.util.HashMap;


public class C45DecisionTreeNode {
	
	private boolean isYaprak;
	private String NodesinifEtiketi;
	private String featureName;

	private HashMap<String, C45DecisionTreeNode> children;

	public HashMap<String, C45DecisionTreeNode> getChildren() {
		return children;
	}

	@Override
	public String toString() {
		if (isYaprak)
			return NodesinifEtiketi;
		return featureName;
	}

	public void setChildNode(String featureValue, C45DecisionTreeNode childNode) {
		children.put(featureValue, childNode);
	}

	public int getNumChild() {
		if(this.isYaprak)
			return 0;
		return this.children.size();
	}

	C45DecisionTreeNode(boolean isLeaf, String NodesinifEtiketi) {
		setYaprak(isLeaf);
		setNodesinifEtiketi(NodesinifEtiketi);
	}

	C45DecisionTreeNode(String featureName, int numArcs) {
		setFeatureName(featureName);
		setNumChildren(numArcs);
		setYaprak(false);
	}

	private void setNumChildren(int numArcs) {
		children = new HashMap<String, C45DecisionTreeNode>(numArcs);
	}

	public boolean isYaprak() {
		return isYaprak;
	}

	public void setYaprak(boolean isYaprak) {
		this.isYaprak = isYaprak;
	}

	public String getNodesinifEtiketi() {
		return NodesinifEtiketi;
	}

	public void setNodesinifEtiketi(String NodesinifEtiketi) {
		this.NodesinifEtiketi = NodesinifEtiketi;
	}

	public String getFeatureName() {
		return featureName;
	}

	public void setFeatureName(String featureName) {
		this.featureName = featureName;
	}
	
	

}

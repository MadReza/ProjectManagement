package view;

import java.awt.Color;
import java.awt.Component;
<<<<<<< HEAD
import java.awt.Dimension;
=======
>>>>>>> 7335317999b66617b53caf2e07915a8d918b402e
import java.awt.Font;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTree;
<<<<<<< HEAD
import javax.swing.event.TreeModelListener;
=======
>>>>>>> 7335317999b66617b53caf2e07915a8d918b402e
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;

import model.Activity;
import model.Job;
import model.Project;

@SuppressWarnings("serial")
public class TreePanel extends JPanel {
	
	private JTree viewTree;
	
	private DefaultTreeModel modelTree;
	private DefaultMutableTreeNode treeRoot;

<<<<<<< HEAD
	private ArrayList<Project> treeProjects;
	
	public TreePanel() {
		
		treeProjects = new ArrayList<Project>();
=======
	private ArrayList<Project> mProjects;
	
	public TreePanel() {
		
		mProjects = new ArrayList<Project>();
>>>>>>> 7335317999b66617b53caf2e07915a8d918b402e
		buildTree();
	}
	
	public void buildTree() {

		treeRoot = new DefaultMutableTreeNode("Manager Projects");
		populateTree();
		
		modelTree = new DefaultTreeModel(treeRoot);
		viewTree = new JTree(modelTree);
		viewTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
	
<<<<<<< HEAD
=======
        
>>>>>>> 7335317999b66617b53caf2e07915a8d918b402e
		// Tree Border
		viewTree.setBorder(BorderFactory.createLoweredBevelBorder());
		viewTree.setExpandsSelectedPaths(true);
		
		// Tree BackGround Colour
		viewTree.setBackground(Color.getHSBColor(0.1f, 0.3f, 0.9f));

		// Fonts
<<<<<<< HEAD
		viewTree.setMinimumSize(new Dimension(250,250));
=======
>>>>>>> 7335317999b66617b53caf2e07915a8d918b402e
		viewTree.setFont(new Font("Arial Black", Font.BOLD, 18));
		
		// Foreground
		viewTree.setForeground(Color.getHSBColor(3.14f, 0.8f, 0.8f));
		
	    TreeCellRenderer renderer = new MyCellRenderer();
	    viewTree.setCellRenderer(renderer);
/*		
 		expand all tree nodes
 		for (int i = 0; i < viewTree.getRowCount(); i++) {
			viewTree.expandRow(i);
		}
		*/
		
		add(viewTree);
	}
	
	private void populateTree() {
	
		DefaultMutableTreeNode projectNode = null;
		DefaultMutableTreeNode activityNode = null;

		try {
						
<<<<<<< HEAD
			for(Project proj: treeProjects){
=======
			for(Project proj: mProjects){
>>>>>>> 7335317999b66617b53caf2e07915a8d918b402e
				
				projectNode = new DefaultMutableTreeNode(proj);

				ArrayList<Activity> projectActivities = proj.getProjectActivities();
				if(!projectActivities.isEmpty()){
					
					for(Activity act: projectActivities){
						activityNode = new DefaultMutableTreeNode(act);
						projectNode.add(activityNode);
					}
				}
				treeRoot.add(projectNode);
			}
			
		} catch (Exception e) {
<<<<<<< HEAD
=======
			// TODO Auto-generated catch block

>>>>>>> 7335317999b66617b53caf2e07915a8d918b402e
			e.printStackTrace();
		}

	}
	
	public void refreshTree() {
<<<<<<< HEAD
		
		treeRoot.removeAllChildren();
		populateTree();
		reloadTree();
	}
	
	public void reloadTree(){
		modelTree.reload(treeRoot);
=======
		remove(viewTree);
		buildTree();
		
/*		treeRoot.removeAllChildren();
		populateTree();
		modelTree.reload(treeRoot);
		*/
//		viewTree.setModel(modelTree);
>>>>>>> 7335317999b66617b53caf2e07915a8d918b402e
	}
	
	public JTree getTree() {
		return viewTree;
	}

<<<<<<< HEAD
=======
	public DefaultTreeModel getModelTree() {
		return modelTree;
	}

>>>>>>> 7335317999b66617b53caf2e07915a8d918b402e
	public void addTreeSelectionListener(TreeSelectionListener tsl) {
		viewTree.addTreeSelectionListener(tsl);
	}
	
<<<<<<< HEAD
	public void addTreeSelectionListener(TreeModelListener tml){
		modelTree.addTreeModelListener(tml);
	}

=======
/*	public void addTreeSelectionListener(TreeModelListener tml){
		modelTree.addTreeModelListener(tml);
	}
*/
	public ArrayList<Project> getmProjects() {
		return mProjects;
	}

	public void setmProjects(ArrayList<Project> mProjects) {
		this.mProjects = mProjects;
	}
>>>>>>> 7335317999b66617b53caf2e07915a8d918b402e

	public DefaultMutableTreeNode getTreeRoot() {
		return treeRoot;
	}
	
	public DefaultMutableTreeNode searchNode(Job jobNode) 
	{ 
	   if(jobNode == null){
		   return null;
	   }
		DefaultMutableTreeNode node = null; 

	    @SuppressWarnings("unchecked")
		Enumeration<DefaultMutableTreeNode> enumeration= treeRoot.breadthFirstEnumeration(); 
	    while(enumeration.hasMoreElements()) {

	        node = (DefaultMutableTreeNode)enumeration.nextElement(); 
	        if(jobNode.equals(node.getUserObject())) {

	            return node;                          
	        } 
	    } 

	    //tree node with string node found return null 
	    return null; 
	}
	
<<<<<<< HEAD
	public DefaultTreeModel getModelTree() {
		return modelTree;
	}

	public ArrayList<Project> getTreeProjects() {
		return treeProjects;
	}

	public void setTreeProjects(ArrayList<Project> treeProjects) {
		this.treeProjects = treeProjects;
	}
	
=======
>>>>>>> 7335317999b66617b53caf2e07915a8d918b402e
	public class MyCellRenderer extends DefaultTreeCellRenderer {

	    @Override
	    public Color getBackgroundNonSelectionColor() {
	        return (null);
	    }

	    @Override
	    public Color getBackgroundSelectionColor() {
	        return Color.GREEN;
	    }

	    @Override
	    public Color getBackground() {
<<<<<<< HEAD
	        return (Color.getHSBColor(0.1f, 0.3f, 0.9f));
=======
	        return (null);
>>>>>>> 7335317999b66617b53caf2e07915a8d918b402e
	    }

	    @Override
	    public Component getTreeCellRendererComponent(final JTree tree, final Object value, final boolean sel, final boolean expanded, final boolean leaf, final int row, final boolean hasFocus) {
	        final Component ret = super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

/*	        final DefaultMutableTreeNode node = ((DefaultMutableTreeNode) (value));
	        this.setText(value.toString());*/
	        return ret;
	    }
	}
<<<<<<< HEAD
=======
	
>>>>>>> 7335317999b66617b53caf2e07915a8d918b402e
}

package view;

import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import model.Activity;
import model.ActivityOnArrow;
import model.PertNode;
import model.Project;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

@SuppressWarnings("serial")
public class PertView{

	private mxGraph graph;
	private Object parent; 
	private ArrayList<ActivityOnArrow> activities;
	private ArrayList<PertNode> eventNodes;
	private mxHierarchicalLayout layout;
	private mxGraphComponent graphComponent;
	
	public PertView(Project proj)
	{
		//super(tabName);
		graph = new mxGraph();
		layout = new mxHierarchicalLayout(graph, SwingConstants.WEST);
		layout.setParallelEdgeSpacing(layout.getParallelEdgeSpacing() * 5f);
		layout.setIntraCellSpacing(layout.getIntraCellSpacing() * 2f);
		layout.setInterRankCellSpacing(layout.getInterRankCellSpacing() * 2f);
		parent = graph.getDefaultParent();
		eventNodes = new ArrayList<PertNode>();
		activities = new ArrayList<ActivityOnArrow>();
		
		for(Activity act : proj.getProjectActivities())
		{
			try {
				activities.add(new ActivityOnArrow(act, 0.0f, 0.0f));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//System.out.println("Activity Size: " + activities.size() + "\nProject Activity size: " + proj.getProjectActivities().size());
		
		//Creating a new Style: just testing
		/*mxStylesheet stylesheet = graph.getStylesheet();
		Hashtable<String, Object> style = new Hashtable<String, Object>();
		style.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_DOUBLE_ELLIPSE);
		style.put(mxConstants.STYLE_OPACITY, 50);
		style.put(mxConstants.STYLE_FONTCOLOR, "#774400");
		stylesheet.putCellStyle("ROUNDED", style);*/
		//--------------------
		
		try {
			generateEventNodes();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		createGraph();
		graphComponent = new mxGraphComponent(graph);
		//graph.setCellsEditable(false);
		//mxGraphComponent graphComponent = new mxGraphComponent(graph);
		//getContentPane().add(graphComponent);
	}
	
	private void createGraph()
	{
		int counter = 1;
		int width = 120;
		int height = 45;
		int xPos = 20;
		int yPos = 20;
		Object n1, n2;
		
		//adding eventNodes to graph
		if(eventNodes.size() > 1)
		{
			graph.getModel().beginUpdate();
			try
			{
				for(PertNode node : eventNodes)
				{
					System.out.println("ID: " + node.getID());
					n1 = graph.insertVertex(parent, String.valueOf(node.getID()), node, (xPos+width)*counter, yPos, width, height);
					System.out.println("Vertexs: " + n1 );
					counter++;
				}
			}
			finally
			{
				graph.getModel().endUpdate();
			}
			try
			{
				for(PertNode node : eventNodes)
				{
					for(ActivityOnArrow a : node.getOutActivity())
					{
						System.out.println("Activity: " + a);
						PertNode temp = getNodeContainingInActivity(a, node); //Getting the node with 'a' as an InActivity
						if(temp != null)
						{
							System.out.println("IDs: " + node.getID() + "  -  " + temp.getID());
							n1 = ((mxGraphModel) graph.getModel()).getCell(String.valueOf(node.getID() + 1)); //TODO: figure out why 'n1' has a wrong memory address
							n2 = ((mxGraphModel) graph.getModel()).getCell(String.valueOf(temp.getID() + 1)); //TODO: related to above. +1 is a temporary fix. might not work later on
							System.out.println("Vertexs: " + n1 + "\n" + n2);
							graph.insertEdge(parent, null, a, n1, n2);
						}
					}
					//n1 = ((mxGraphModel) graph.getModel()).getCell(act2.getName());
				}
			}
			finally
			{
				layout.execute(parent);
				graph.getModel().endUpdate();
			}
		}
	}
	
	//Generates the correct amount of eventNodes according to the amount of activities in the project
	private void generateEventNodes() throws Exception
	{
		int eventID = 1;
		System.out.println("*************************************************");
		eventNodes.add(new PertNode(eventID));	//Start eventNode
		eventID++;
		for(ActivityOnArrow act : activities)
		{
			//If activity has no preReq then, link it with the first eventNode
			if(act.getPreReq().size() == 0)
			{
				System.out.print("PreReq SIZE: 0 : " + act.getName());
				if(!eventNodes.get(0).containsOutActivity(act))
				{
					System.out.println("ADDED act to start: " + eventNodes.get(0).getID() + "-->" + act.getName());
					eventNodes.get(0).addOutActivity(act);
				}
			}
			//If activity has preReqs, loop through preReqs and link current Activity and preReqs when necessary
			else if(act.getPreReq().size() > 0)
			{
				System.out.println("\nPreReq SIZE: " + act.getPreReq().size() + ". For each preReq of: " + act.getName());
				for(Activity preReq : act.getPreReq())
				{
					if(!eventNodesContainsOutActivity(act) && !eventNodesContainsInActivity(preReq))
					{
						System.out.println("Node Didnt exist. Created Node("+eventID+") and ADDED " + act.getName() + " :"+ preReq.getName() +"--> n-"+eventID+"-->" +act.getName());
						eventNodes.add(new PertNode(eventID));
						eventNodes.get(eventNodes.size()-1).addOutActivity(act);
						//eventNodes.get(eventNodes.size()-1).addInActivity(new ActivityOnArrow(preReq, 0.0f, 0.0f)); //TODO: loosing reference to aoa in 'activities'. FIX
						eventNodes.get(eventNodes.size()-1).addInActivity(activities.get(activities.indexOf(preReq)));
						eventID++;
					}
					else if(!eventNodesContainsOutActivity(act) && eventNodesContainsInActivity(preReq))
					{
						//PertNode temp = getNodeContainingInActivity(new ActivityOnArrow(preReq, 0.0f, 0.0f)); //TODO: loosing reference to aoa in 'activities'. FIX
						PertNode temp = getNodeContainingInActivity(activities.get(activities.indexOf(preReq)));
						temp.addOutActivity(act);
						System.out.println("Node Existed aready. added posReq: n-" + temp.getID() + " --> " + act.getName());
					}
					else if(eventNodesContainsOutActivity(act) && !eventNodesContainsInActivity(preReq))
					{
						PertNode temp = getNodeContainingOutActivity(act);
						//temp.addInActivity(new ActivityOnArrow(preReq, 0.0f, 0.0f)); //TODO: loosing reference to aoa in 'activities'. FIX
						temp.addInActivity(activities.get(activities.indexOf(preReq)));
						System.out.println("Node Existed aready. added preReq: "+ preReq.getName()+ " --> n-" + temp.getID());
					}
					if(!eventNodesContainsInActivity(preReq))
					{
						PertNode temp = getNodeContainingOutActivity(act);
						//temp.addInActivity(new ActivityOnArrow(preReq, 0.0f, 0.0f)); //TODO: loosing reference to aoa in 'activities'. FIX
						temp.addInActivity(activities.get(activities.indexOf(preReq)));
						System.out.println("No node had this PREREQ. added preReq: "+ preReq.getName()+ " --> n-" + temp.getID());
					}
				}
			}
		}
		eventNodes.add(new PertNode(eventID));	//Last eventNode. TODO: link it properly with the inActivity
		for(ActivityOnArrow act : activities)
		{
			if(act.getSuccessors().size() == 0)
				eventNodes.get(eventNodes.size()-1).addInActivity(act);
		}
		System.out.println("EventNodes size: " + eventNodes.size());
		System.out.println("*************************************************");
		
		forwardPass();
	}

	private void forwardPass()
	{
		//TODO: implement method
		float expectedDateResult = -1.0f;
		float currentT = 0.0f;
		float sd = -1.0f;
		float currentSd = 0.0f;
		for(ActivityOnArrow aoa : activities)
		{
			//TODO: call these methods in ActivityOnArrow constructor so i don't have to call them here
			aoa.calculateExpectedDuration();
			aoa.calculateStandardDeviation();
		}
		Collections.sort(eventNodes);
		for(PertNode node: eventNodes)
		{
			if(node.getID() == 1)
			{
				node.setExpectedDate(0f);
				node.setStandardDeviation(0f);
			}
			else
			{
				for(ActivityOnArrow aoa : node.getInActivity())
				{
					System.out.println("aoa: " + aoa.getExpectedDuration() + " - node: " + getNodeContainingOutActivity(aoa).getExpectedDate());
					currentT = aoa.getExpectedDuration() + getNodeContainingOutActivity(aoa).getExpectedDate();
					currentSd = aoa.getStandardDeviation() + getNodeContainingOutActivity(aoa).getStandardDeviation();
					System.out.println("CURRENT: " + currentT);
					if(currentT > expectedDateResult)
					{
						System.out.println("CURRENT > EXPE");
						expectedDateResult = currentT;
					}
					if(currentSd > sd)
					{
						sd = currentSd;
					}
				}
				System.out.println("Node-" + node.getID() + " expResult: " + expectedDateResult);
				node.setExpectedDate(expectedDateResult);
				node.setStandardDeviation(sd);
			}
			currentT = 0.0f;
			expectedDateResult = -1.0f;
			currentSd = 0.0f;
			sd = -1.0f;
		}
	}
	private boolean eventNodesContainsOutActivity(ActivityOnArrow outAct)
	{
		//System.out.println("eventNodesContainsOutActivity???");
		for(PertNode node : eventNodes)
		{
			if(node.containsOutActivity(outAct))
			{
				//System.out.println("BOOLEAN TRUE");
				return true;
			}
		}
		//System.out.println("BOOLEAN FALSE");
		return false;
	}
	private boolean eventNodesContainsInActivity(Activity inAct)
	{
		//System.out.println("eventNodesContainsInActivity???");
		/*ActivityOnArrow aoa = null;
		try {
			aoa = new ActivityOnArrow(inAct, 0.0f, 0.0f);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		for(PertNode node : eventNodes)
		{
			if(node.containsInActivity(activities.get(activities.indexOf(inAct))))
			{
				//System.out.println("BOOLEAN TRUE");
				return true;
			}
		}
		//System.out.println("BOOLEAN FALSE");
		return false;
	}
	private PertNode getNodeContainingOutActivity(ActivityOnArrow outAct)
	{
		PertNode result = null;
		for(PertNode node : eventNodes)
		{
			if(node.containsOutActivity(outAct))
				result = node;
		}
		return result;
	}
	private PertNode getNodeContainingInActivity(ActivityOnArrow inAct)
	{
		PertNode result = null;
		for(PertNode node : eventNodes)
		{
			if(node.containsInActivity(inAct))
				result = node;
		}
		return result;
	}
	private PertNode getNodeContainingOutActivity(ActivityOnArrow outAct, PertNode node)
	{
		PertNode result = null;
		for(PertNode n : eventNodes)
		{
			if(!n.equals(node) && n.containsOutActivity(outAct))
				result = node;
		}
		return result;
	}
	private PertNode getNodeContainingInActivity(ActivityOnArrow inAct, PertNode node)
	{
		//System.out.println("------------------------------------\nRunning Function");
		PertNode result = null;
		for(PertNode n : eventNodes)
		{
			System.out.println(n);
			if(!n.equals(node) && n.containsInActivity(inAct))
			{
				//System.out.println("made it inside");
				result = n;
				//System.out.println(result+"\n-------------------------------");
			}
		}
		return result;
	}
	public mxGraphComponent getGraphComponent()
	{
		return graphComponent;
	}
	
	public ArrayList<PertNode> getNodes()
	{
		return eventNodes;
	}
}

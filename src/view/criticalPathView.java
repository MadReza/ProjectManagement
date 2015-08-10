package view;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.SwingConstants;

import java.util.Iterator;

import model.Activity;
import model.ActivityOnArrow;
import model.PertNode;
import model.Project;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import com.mxgraph.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class criticalPathView extends JFrame {

	private mxGraph graph;
	private mxHierarchicalLayout layout;
	public criticalPathView(List<List<Activity>> criticalPaths)
	{
		//super(tabName);
		graph = new mxGraph();
		layout = new mxHierarchicalLayout(graph, SwingConstants.WEST);
		layout.setParallelEdgeSpacing(layout.getParallelEdgeSpacing() * 5f);
		layout.setIntraCellSpacing(layout.getIntraCellSpacing() * 2f);
		layout.setInterRankCellSpacing(layout.getInterRankCellSpacing() * 2f);
		try
		{
			graph.getModel().beginUpdate();
			int index=1;
			for(List<Activity> lists : criticalPaths){
				
				//graph.insertVertex(graph.getDefaultParent(), null,"Critical Path"+index , 0, 0, 100, 40);	
				Collections.reverse(lists);
				Iterator<Activity> ite = lists.iterator();
				
				Object[] array = new Object[lists.size()];
					//while(ite.hasNext())
					for(int i=0;i<array.length;i++){
						Activity temp = ite.next();
						if(i==0)
							array[i] = graph.insertVertex(graph.getDefaultParent(), null, "Critical path #"+index+"\n"+temp.getName() + "\n Starting date: "+temp.getStartDate().substring(4,10)+" " +temp.getStartDate().substring(24,28)   + "\n Finishing date: "+temp.getFinishDate().substring(4,10)+" "+temp.getFinishDate().substring(24,28), 0, 0, 175, 175);		
						else
							array[i] = graph.insertVertex(graph.getDefaultParent(), null, temp.getName() + "\n Starting date: "+temp.getStartDate().substring(4,10)+" " +temp.getStartDate().substring(24,28)   + "\n Finishing date: "+temp.getFinishDate().substring(4,10)+" "+temp.getFinishDate().substring(24,28), 0, 0, 175, 175);	
								
						//substring(0,10) + substring(22,26)
						if(i>0)
							graph.insertEdge(graph.getDefaultParent(), "", "", array[i-1], array[i]);
						
					}
				/*Object[] arrayGroup = new Object[array.length+1];
				
				arrayGroup[0] = "Critical path #"+index;
				for(int i =1;i<arrayGroup.length;i++){
					arrayGroup[i] = array[i-1];
					
				}
				*/
				//graph.insertVertex(graph.getDefaultParent(), null, "Critical path #"+index, 0, 0, 140, 40);	
					//			mxCell x = (mxCell) (graph.groupCells(graph.groupCells(),5,array));
				//mxCell x = (mxCell) (graph.groupCells(graph.groupCells(),0,array));
				//x.setValue("Critical path"+index);
				
				
					//graph.groupCells(array, 5, array);
					//layout.arrangeGroups(array,5);
					//layout.setUseBoundingBox(true);
				index++;
			}
		}
		finally
		{
			layout.execute(graph.getDefaultParent());
			graph.getModel().endUpdate();
	
		}
		this.add(new mxGraphComponent(graph));
	
	}
}

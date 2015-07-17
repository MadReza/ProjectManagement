package view;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import model.Project;

<<<<<<< HEAD
public class ProjectTableModel extends AbstractTableModel {

	private ArrayList<Project> tableProjects;
	private String[] colNames = {"Name" , "Description" , "Budget" , "Start Date" , "Finish Date" , "Status"};
	
	public ProjectTableModel(){
		tableProjects = new ArrayList<Project>();
	}
	
	public void setData(ArrayList<Project> allProjects){
		this.tableProjects = allProjects;
=======
@SuppressWarnings("serial")
public class ProjectTableModel extends AbstractTableModel {

	private ArrayList<Project> allProjects;
	private String[] colNames = {"Name" , "Description" , "Budget" , "Start Date" , "Finish Date" , "Status"};
	
	public ProjectTableModel(){
		allProjects = new ArrayList<Project>();
	}
	
	public void setData(ArrayList<Project> allProjects){
		this.allProjects = allProjects;
>>>>>>> 7335317999b66617b53caf2e07915a8d918b402e
	}
	
	public String getColumnName(int column) {
		
		return colNames[column];
	}

	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 6;
	}

	public int getRowCount() {
		
<<<<<<< HEAD
		if(!tableProjects.isEmpty()){
			return tableProjects.size();
=======
		if(!allProjects.isEmpty()){
			return allProjects.size();
>>>>>>> 7335317999b66617b53caf2e07915a8d918b402e
		}
		return 0;
	}

	public Object getValueAt(int row, int col) {
		
<<<<<<< HEAD
		Project project = tableProjects.get(row);
=======
		Project project = allProjects.get(row);
>>>>>>> 7335317999b66617b53caf2e07915a8d918b402e
		
		switch(col){
		case 0:	
			return project.getName();
		case 1:
			return project.getDescription();
		case 2:
			return project.getBudget();
		case 3:
			return project.getStartDate();
		case 4:
			return project.getFinishDate();
		case 5:
			return project.getStatus();
		}
		return null;
	}
<<<<<<< HEAD
	

	public ArrayList<Project> getTableProjects() {
		return tableProjects;
	}

	public void setTableProjects(ArrayList<Project> tableProjects) {
		this.tableProjects = tableProjects;
	}
	
	
=======

>>>>>>> 7335317999b66617b53caf2e07915a8d918b402e
}

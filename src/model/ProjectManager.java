package model;

import java.util.ArrayList;

public class ProjectManager extends Member {

	private ArrayList<Project> managerProjects;
	
	public ProjectManager(String username, String userPassword) {
		super(username, userPassword);
		managerProjects = new ArrayList<Project>();
	}

	public ArrayList<Project> getManagerProjects() {
		return managerProjects;
	}

	public void setManagerProjects(ArrayList<Project> arrayList) {
		this.managerProjects = arrayList;
	}

}

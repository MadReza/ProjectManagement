package view;

import javax.swing.*;

import controller.MainController;
import model.MainModel;

@SuppressWarnings("serial")
public class MainView extends JFrame{


	private MainModel mainModel;

	private MainController mainController;
	
	private StartupView startupView;
	
	public MainView(MainModel mModel) {
		
		mainModel = mModel;
		startupView = new StartupView();	
	}
	
	public StartupView getStartupView() {
		return startupView;
	}
	
}

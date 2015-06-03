package application;

import java.awt.EventQueue;

import controller.MainController;
import model.MainModel;
import view.MainView;

public class App {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {

				//Create instances of mainModel, mainView and mainController.
				MainModel mainModel = null;			
				try {
					mainModel = MainModel.getInstance();
				} catch (Exception e) {
					e.printStackTrace();
				}

				MainView mainView = new MainView(mainModel);

			//	MainController mainController = 
						new MainController(mainModel, mainView);
			}
		});
	}
}



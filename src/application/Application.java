package application;

import javax.swing.SwingUtilities;

import model.MainModel;
import view.MainView;
import controller.MainController;

public class Application {

<<<<<<< HEAD
=======

>>>>>>> 7335317999b66617b53caf2e07915a8d918b402e
	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable(){
			
			public void run() {
			
				MainModel mainModel = null;
				try {
					mainModel = MainModel.getInstance();

				} catch (Exception e) {
					e.printStackTrace();
				}
				
				MainView mainView = new MainView(mainModel);
				
				MainController mainController = new MainController(mainModel, mainView);
<<<<<<< HEAD
			}	
		});
	}
=======
			
			}	
		});
	}

>>>>>>> 7335317999b66617b53caf2e07915a8d918b402e
}

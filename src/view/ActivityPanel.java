package view;

import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.border.Border;

import model.Activity;

@SuppressWarnings("serial")
public class ActivityPanel extends JobPanel{

	private JButton prereqBtn;
	private JButton memberBtn;
	
	protected ActivityPanel() {

		// Form Border
		Border innerBorder = BorderFactory.createTitledBorder("Activity Form");
		Border outerBorder = BorderFactory.createEmptyBorder(15, 15, 15, 15);
		setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder)); 
		
		//laying out prerequisites components
		////// Row 7 //////
		setRow(1, 6);
		prereqBtn = new JButton("ADD / REMOVE Prerequisite ");
		add(prereqBtn, gc);

		
		//laying out MemberTeam components
		////// Row 8 //////
		setRow(1, 7);
		memberBtn = new JButton("ADD / REMOVE Member");
		add(memberBtn, gc);

	}

	////////////////////////////////////////////////////////////////	Listeners	\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	
	public void addSaveListener(ActionListener listener) {
		getSaveBtn().addActionListener(listener);
	}

	public void addDeleteListener(ActionListener listener) {
		// TODO Auto-generated method stub
		getDeleteBtn().addActionListener(listener);
	}
	
	@Override
	public void addNewActivityBtnListener(ActionListener aListener) {
		// TODO Auto-generated method stub	
	}

	public void addPrereqButtonListener(ActionListener aListener) {
		prereqBtn.addActionListener(aListener);
	}

	public void addMemberButtonListener(ActionListener aListener) {
		memberBtn.addActionListener(aListener);
	}
	
	public void addProjectActivitiesComboListener(ItemListener aListener) {
		
		// TODO Auto-generated method stub
		
	}
	////////////////////////////////////////////////////////////////	Getters and Setters	\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

	public JComboBox<Activity> getProjectActivitiesCombo() {
		return null;
	}

	
	////////////////////////////////////////////////////////////////	Helper Methods	\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	
	public JButton getPrereqBtn() {
		return prereqBtn;
	}

	public JButton getMemberBtn() {
		return memberBtn;
	}

	public void enableFieldsEdit(boolean bol){
		super.enableFieldsEdit(bol);
		prereqBtn.setEnabled(!bol);
		memberBtn.setEnabled(!bol);
	}

	@Override
	public JButton getNewActivityBtn() {
		// TODO Auto-generated method stub
		return null;
	}

}

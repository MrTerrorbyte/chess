package chess;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Player implements ActionListener {
	private String name;
	private JButton inputName;
	private JLabel scoreLabel;
	private int score;

	public Player(){
		name = "Anonymous";
		inputName = new JButton();
		score = 0;
		scoreLabel = new JLabel();
	}
	
	public int getScore() {
		return score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(inputName.equals(e.getSource())){
			name = JOptionPane.showInputDialog("Please input your name");
			inputName.setText(name);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public JButton getPlayerButton(){
		return inputName;
	}

	public JLabel getScoreLabel() {
		return scoreLabel;
	}
	
	public void setScoreLabel(int score){
		this.scoreLabel.setText("    "+name+"'s score: "+Integer.toString(score));
	}
}

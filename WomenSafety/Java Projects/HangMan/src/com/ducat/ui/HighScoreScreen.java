package com.ducat.ui;

import javax.swing.JFrame;
import javax.swing.JLabel;

import com.ducat.dto.ScoreDTO;

import java.awt.Font;
import java.util.List;

public class HighScoreScreen extends JFrame {
	
	public HighScoreScreen(List<ScoreDTO> scoreList) {
		getContentPane().setFont(new Font("Footlight MT Light", Font.BOLD, 13));
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		JLabel lblRank = new JLabel("Rank");
		lblRank.setFont(new Font("Footlight MT Light", Font.BOLD, 15));
		lblRank.setBounds(50, 32, 46, 14);
		getContentPane().add(lblRank);
		
		JLabel lblPlayerName = new JLabel("Player Name");
		lblPlayerName.setFont(new Font("Footlight MT Light", Font.BOLD, 15));
		lblPlayerName.setBounds(150, 32, 96, 14);
		getContentPane().add(lblPlayerName);
		
		JLabel lblScore = new JLabel("Score");
		lblScore.setFont(new Font("Footlight MT Light", Font.BOLD, 15));
		lblScore.setBounds(325, 32, 46, 14);
		getContentPane().add(lblScore);
		
		JLabel label = new JLabel("1.");
		label.setBounds(50, 80, 46, 14);
		getContentPane().add(label);
		
		JLabel label_1 = new JLabel("2.");
		label_1.setBounds(50, 120, 46, 14);
		getContentPane().add(label_1);
		
		JLabel label_2 = new JLabel("3.");
		label_2.setBounds(50, 160, 46, 14);
		getContentPane().add(label_2);
		
		JLabel label_3 = new JLabel("4.");
		label_3.setBounds(50, 200, 46, 14);
		getContentPane().add(label_3);
		
		JLabel label_4 = new JLabel("5.");
		label_4.setBounds(50, 240, 46, 14);
		getContentPane().add(label_4);
		
		JLabel label_5 = new JLabel(""+scoreList.get(0).getUser());
		label_5.setBounds(150, 80, 158, 14);
		getContentPane().add(label_5);
		
		JLabel label_6 = new JLabel(""+scoreList.get(1).getUser());
		label_6.setBounds(150, 120, 158, 14);
		getContentPane().add(label_6);
		
		JLabel label_7 = new JLabel(""+scoreList.get(2).getUser());
		label_7.setBounds(150, 160, 158, 14);
		getContentPane().add(label_7);
		
		JLabel label_8 = new JLabel(""+scoreList.get(3).getUser());
		label_8.setBounds(150, 200, 158, 14);
		getContentPane().add(label_8);
		
		JLabel label_9 = new JLabel(""+scoreList.get(4).getUser());
		label_9.setBounds(150, 240, 158, 14);
		getContentPane().add(label_9);
		
		JLabel label_10 = new JLabel(""+scoreList.get(0).getScore());
		label_10.setBounds(325, 80, 72, 14);
		getContentPane().add(label_10);
		
		JLabel label_11 = new JLabel(""+scoreList.get(1).getScore());
		label_11.setBounds(325, 120, 46, 14);
		getContentPane().add(label_11);
		
		JLabel label_12 = new JLabel(""+scoreList.get(2).getScore());
		label_12.setBounds(325, 160, 46, 14);
		getContentPane().add(label_12);
		
		JLabel label_13 = new JLabel(""+scoreList.get(3).getScore());
		label_13.setBounds(325, 200, 46, 14);
		getContentPane().add(label_13);
		
		JLabel label_14 = new JLabel(""+scoreList.get(4).getScore());
		label_14.setBounds(325, 240, 46, 14);
		getContentPane().add(label_14);
	}
}

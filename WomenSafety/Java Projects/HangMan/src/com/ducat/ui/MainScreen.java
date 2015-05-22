package com.ducat.ui;

import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;

import com.ducat.dto.ScoreDTO;
import com.ducat.jdbc.JDBC;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.List;

public class MainScreen extends JFrame {

	public static void main(String[] args) {
		MainScreen frame = new MainScreen();
		frame.setVisible(true);
	}

	public MainScreen() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 460, 325);
		this.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("Welcome to TIC TAC TOE");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(100, 11, 250, 29);
		getContentPane().add(lblNewLabel);

		JButton btnNewButton = new JButton("New Game");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				newgame();
			}
		});
		btnNewButton.setBounds(150, 75, 150, 25);
		btnNewButton.setMnemonic('n');
		getContentPane().add(btnNewButton);

		JButton btnNewButton_1 = new JButton("High Score");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				highscore();
			}
		});
		btnNewButton_1.setBounds(150, 150, 150, 25);
		btnNewButton_1.setMnemonic('h');
		getContentPane().add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("Exit");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exit();
			}
		});
		btnNewButton_2.setBounds(150, 225, 150, 25);
		btnNewButton_2.setMnemonic('e');
		getContentPane().add(btnNewButton_2);
	}

	private void newgame() {
		dispose();
		MainGame obj = new MainGame();
		obj.setVisible(true);
	}

	private void highscore() {
		try {
			List<ScoreDTO> scoreList = JDBC.foundHighScore();
			HighScoreScreen obj = new HighScoreScreen(scoreList);
			obj.setVisible(true);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	private void exit() {
		System.exit(EXIT_ON_CLOSE);
	}
}

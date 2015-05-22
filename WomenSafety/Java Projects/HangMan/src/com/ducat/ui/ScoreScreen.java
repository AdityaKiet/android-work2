package com.ducat.ui;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;

import com.ducat.dto.ScoreDTO;
import com.ducat.jdbc.JDBC;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class ScoreScreen extends JFrame {
	private JTextField textField;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	ScoreDTO scoreDTO;

	public ScoreScreen(ScoreDTO scoreDTO) {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		this.scoreDTO = scoreDTO;

		textField = new JTextField();
		textField.setBounds(151, 61, 234, 20);
		getContentPane().add(textField);
		textField.setColumns(10);

		lblNewLabel = new JLabel("Player Name:");
		lblNewLabel.setBounds(48, 64, 93, 14);
		getContentPane().add(lblNewLabel);

		lblNewLabel_1 = new JLabel("Score:");
		lblNewLabel_1.setBounds(48, 108, 46, 14);
		getContentPane().add(lblNewLabel_1);

		lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setBounds(151, 108, 46, 14);
		getContentPane().add(lblNewLabel_2);

		lblNewLabel_3 = new JLabel("" + scoreDTO.getScore());
		lblNewLabel_3.setBounds(151, 108, 46, 14);
		getContentPane().add(lblNewLabel_3);

		JButton btnNewButton = new JButton("Submit");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				submit();
			}
		});
		btnNewButton.setBounds(151, 160, 89, 23);
		getContentPane().add(btnNewButton);
	}

	private void submit() {
		scoreDTO.setUser(textField.getText());
		try {
			JDBC.loadResult(scoreDTO);
			dispose();
			MainScreen obj = new MainScreen();
			obj.setVisible(true);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
}

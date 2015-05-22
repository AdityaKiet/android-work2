package com.tkhts.ui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class MyCalc extends JFrame {

	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		MyCalc obj = new MyCalc();
		obj.setVisible(true);
		
	}

	/**
	 * Create the frame.
	 */
	public MyCalc() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 500);
		this.getContentPane().setLayout(null);
		
		ImageIcon icon = new ImageIcon(MyCalc.class.getResource("spiderman5.gif"));
		JLabel lblNewLabel = new JLabel("My First Program -2014");
		lblNewLabel.setIcon(icon);
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setBounds(40, 88, 400, 150);
		getContentPane().add(lblNewLabel);
	}
}

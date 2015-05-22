package com.tkhts.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ButtonDemo extends JFrame {
	private JTextField textField;
	private boolean isXorZero= true;

	public static void main(String[] args) {

					ButtonDemo frame = new ButtonDemo();
					frame.setVisible(true);

	}

	/**
	 * Create the frame.
	 */
	public ButtonDemo() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 381);
		
		getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("Click Me");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clickMe();
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnNewButton.setMnemonic('C');
		btnNewButton.setToolTipText("Click on this Button");
		btnNewButton.setBounds(51, 94, 200, 50);
		getContentPane().add(btnNewButton);
		
		textField = new JTextField();
		textField.setBounds(41, 31, 314, 50);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				XorZero(btnNewButton_1);
			}
		});
		btnNewButton_1.setBounds(23, 196, 97, 25);
		getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				XorZero(btnNewButton_2);
			}
		});
		btnNewButton_2.setBounds(147, 196, 97, 25);
		getContentPane().add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				XorZero(btnNewButton_3);
			}
		});
		btnNewButton_3.setBounds(273, 196, 97, 25);
		getContentPane().add(btnNewButton_3);
		
	}
	private void clickMe(){
		textField.setText("Hello Swing");
	}
	private void XorZero(JButton object){
		if(object.getText().trim().length()<=0){
		if(isXorZero){
			object.setText("X");
		}
		else
		{
			object.setText("0");
		}
		isXorZero = ! isXorZero;
		}
	}
}

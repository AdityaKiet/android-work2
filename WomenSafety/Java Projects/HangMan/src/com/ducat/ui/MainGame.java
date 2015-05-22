package com.ducat.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import com.ducat.dto.ScoreDTO;
import com.ducat.jdbc.JDBC;

public class MainGame extends JFrame {
	JButton q, w, e, r, t, y, u, i, o, p, a, s, d, f, g, h, j, k, l, z, x, c,
			v, b, n, m;
	int wrong = 0;
	String name = null;
	List<String> wordsList = new ArrayList<>();
	JLabel scoreLabel;
	static int score = 0;
	List<Character> vowels = new ArrayList<>();
	List<Object> newString = new ArrayList<>();
	List<Object> questionString = new ArrayList<>();
	JLabel labelQuestion;
	JPanel panel;
	List<Object> questionString2;
	static MainGame frame;
	String answerString;
	private JLabel resultLabel;
	private JLabel imageLabel;

	public static void main(String[] args) {
		frame = new MainGame();
		frame.setVisible(true);
	}

	public MainGame() {

		getvowels();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		setBounds(100, 100, 1000, 500);
		labelQuestion = new JLabel("");
		labelQuestion.setHorizontalAlignment(SwingConstants.CENTER);
		labelQuestion.setFont(new Font("Times New Roman", Font.BOLD, 18));
		labelQuestion.setBounds(10, 11, 414, 51);
		getContentPane().add(labelQuestion);
		createStrings();
		labelQuestion.setText((String) questionString.toString()
				.replace(",", " ").replace("[", " ").replace("]", " "));

		q = new JButton("Q");
		q.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setTextInString(q.getText().charAt(0));
				hasLost();
				hasWon();
			}
		});
		q.setBounds(10, 98, 62, 36);
		getContentPane().add(q);

		w = new JButton("W");
		w.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setTextInString(w.getText().charAt(0));
				hasLost();
				hasWon();
			}
		});
		w.setBounds(82, 98, 62, 36);
		getContentPane().add(w);

		e = new JButton("E");
		e.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent eq) {
				setTextInString(e.getText().charAt(0));
				hasLost();
				hasWon();
			}
		});
		e.setBounds(154, 98, 62, 36);
		getContentPane().add(e);

		r = new JButton("R");
		r.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setTextInString(r.getText().charAt(0));
				hasLost();
				hasWon();
			}
		});
		r.setBounds(226, 98, 62, 36);
		getContentPane().add(r);

		t = new JButton("T");
		t.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setTextInString(t.getText().charAt(0));
				hasLost();
				hasWon();
			}
		});
		t.setBounds(298, 98, 62, 36);
		getContentPane().add(t);

		y = new JButton("Y");
		y.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setTextInString(y.getText().charAt(0));
				hasLost();
				hasWon();
			}
		});
		y.setBounds(370, 98, 62, 36);
		getContentPane().add(y);

		u = new JButton("U");
		u.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setTextInString(u.getText().charAt(0));
				hasLost();
				hasWon();
			}
		});
		u.setBounds(442, 98, 62, 36);
		getContentPane().add(u);

		i = new JButton("I");
		i.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setTextInString(i.getText().charAt(0));
				hasLost();
				hasWon();
			}
		});
		i.setBounds(514, 98, 62, 36);
		getContentPane().add(i);

		o = new JButton("O");
		o.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setTextInString(o.getText().charAt(0));
				hasLost();
				hasWon();
			}
		});
		o.setBounds(586, 98, 62, 36);
		getContentPane().add(o);

		p = new JButton("P");
		p.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setTextInString(p.getText().charAt(0));
				hasLost();
				hasWon();
			}
		});
		p.setBounds(658, 98, 62, 36);
		getContentPane().add(p);

		a = new JButton("A");
		a.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setTextInString(a.getText().charAt(0));
				hasLost();
				hasWon();
			}
		});
		a.setBounds(33, 145, 62, 36);
		getContentPane().add(a);

		s = new JButton("S");
		s.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setTextInString(s.getText().charAt(0));
				hasLost();
				hasWon();
			}
		});
		s.setBounds(105, 145, 62, 36);
		getContentPane().add(s);

		d = new JButton("D");
		d.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setTextInString(d.getText().charAt(0));
				hasLost();
				hasWon();
			}
		});
		d.setBounds(177, 145, 62, 36);
		getContentPane().add(d);

		f = new JButton("F");
		f.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setTextInString(f.getText().charAt(0));
				hasLost();
				hasWon();
			}
		});
		f.setBounds(249, 145, 62, 36);
		getContentPane().add(f);

		g = new JButton("G");
		g.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setTextInString(g.getText().charAt(0));
				hasLost();
				hasWon();
			}
		});
		g.setBounds(321, 145, 62, 36);
		getContentPane().add(g);

		h = new JButton("H");
		h.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setTextInString(h.getText().charAt(0));
				hasLost();
				hasWon();
			}
		});
		h.setBounds(393, 145, 62, 36);
		getContentPane().add(h);

		j = new JButton("J");
		j.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setTextInString(j.getText().charAt(0));
				hasLost();
				hasWon();
			}
		});
		j.setBounds(465, 145, 62, 36);
		getContentPane().add(j);

		k = new JButton("K");
		k.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setTextInString(k.getText().charAt(0));
				hasLost();
				hasWon();
			}
		});
		k.setBounds(537, 145, 62, 36);
		getContentPane().add(k);

		l = new JButton("L");
		l.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setTextInString(l.getText().charAt(0));
				hasLost();
				hasWon();
			}
		});
		l.setBounds(609, 145, 62, 36);
		getContentPane().add(l);

		z = new JButton("Z");
		z.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setTextInString(z.getText().charAt(0));
				hasLost();
				hasWon();
			}
		});
		z.setBounds(63, 192, 62, 36);
		getContentPane().add(z);

		x = new JButton("X");
		x.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setTextInString(x.getText().charAt(0));
				hasLost();
				hasWon();
			}
		});
		x.setBounds(135, 192, 62, 36);
		getContentPane().add(x);

		c = new JButton("C");
		c.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setTextInString(c.getText().charAt(0));
				hasLost();
				hasWon();
			}
		});
		c.setBounds(207, 192, 62, 36);
		getContentPane().add(c);

		v = new JButton("V");
		v.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setTextInString(v.getText().charAt(0));
				hasLost();
				hasWon();
			}
		});
		v.setBounds(279, 192, 62, 36);
		getContentPane().add(v);

		b = new JButton("B");
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setTextInString(b.getText().charAt(0));
				hasLost();
				hasWon();
			}
		});
		b.setBounds(351, 192, 62, 36);
		getContentPane().add(b);

		n = new JButton("N");
		n.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setTextInString(n.getText().charAt(0));
				hasLost();
				hasWon();
			}
		});
		n.setBounds(423, 192, 62, 36);
		getContentPane().add(n);

		m = new JButton("M");
		m.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setTextInString(m.getText().charAt(0));
				hasLost();
				hasWon();
			}
		});
		m.setBounds(495, 192, 62, 36);
		getContentPane().add(m);

		resultLabel = new JLabel("");
		resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
		resultLabel.setForeground(Color.RED);
		resultLabel.setBounds(10, 253, 661, 80);
		getContentPane().add(resultLabel);

		scoreLabel = new JLabel("Score : " + score);
		scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
		scoreLabel.setBounds(465, 13, 255, 51);
		getContentPane().add(scoreLabel);
		Border border = BorderFactory.createLineBorder(Color.BLACK, 5);
		imageLabel = new JLabel("");

		imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		imageLabel.setBounds(743, 11, 231, 439);
		imageLabel.setBorder(border);
		getContentPane().add(imageLabel);
	}

	private void hasWon() {
		if (!labelQuestion.getText().contains("_")) {
			resultLabel.setText("You won");
			score++;
			scoreLabel.setText("Score : " + score);

			createStrings();
			labelQuestion.setText("");
			labelQuestion.setText((String) questionString.toString()
					.replace(",", " ").replace("[", " ").replace("]", " "));
		}
	}

	private void hasLost() {
		if (wrong == 5) {
			resultLabel.setText("you lost the game");
			q.setEnabled(false);
			disableButtons();
			ScoreDTO scoreDTO = new ScoreDTO();
			scoreDTO.setScore(score);
			score = 0;
			frame.setVisible(true);
			ScoreScreen scoreScreen = new ScoreScreen(scoreDTO);
			scoreScreen.setVisible(true);

		}
	}

	private void disableButtons() {
		q.setEnabled(false);
		w.setEnabled(false);
		e.setEnabled(false);
		r.setEnabled(false);
		y.setEnabled(false);
		t.setEnabled(false);
		u.setEnabled(false);
		i.setEnabled(false);
		o.setEnabled(false);
		p.setEnabled(false);
		a.setEnabled(false);
		s.setEnabled(false);
		d.setEnabled(false);
		f.setEnabled(false);
		g.setEnabled(false);
		h.setEnabled(false);
		j.setEnabled(false);
		k.setEnabled(false);
		l.setEnabled(false);
		z.setEnabled(false);
		x.setEnabled(false);
		c.setEnabled(false);
		v.setEnabled(false);
		b.setEnabled(false);
		n.setEnabled(false);
		m.setEnabled(false);

	}

	private void setTextInString(char name) {
		resultLabel.setText("");
		int index = 0;
		if (!newString.contains(name)) {
			wrong++;
			if (wrong != 0) {
				ImageIcon icon = new ImageIcon(MainGame.class.getResource(wrong
						+ ".jpg"));
				imageLabel.setIcon(icon);
			}
			return;
		}
		while (index != -1) {
			index = -1;
			if (newString.contains(name)) {
				index = newString.indexOf(name);
				System.out.println(index);
				questionString.set(index, name);
				labelQuestion.setText(questionString.toString()
						.replace(",", " ").replace("[", " ").replace("]", " "));
				newString.remove(index);
				newString.add(index, " ");
				System.out.println(newString);
			}
		}
	}

	private void createStrings() {
		newString = new ArrayList<>();
		questionString = new ArrayList<>();

		try {
			while (name == null) {
				int id = 0 + (int) (Math.random() * 12);
				if (!wordsList.contains(JDBC.getWords(id))) {
					name = JDBC.getWords(id);
					System.out.println(name);
					wordsList.add(name);
				}
			}
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		} catch (SQLException e) {

			e.printStackTrace();
		} catch (NullPointerException e) {
			System.out.println("ok");
			e.printStackTrace();
		}
		for (int i = 0; i < name.length(); i++) {
			newString.add(name.charAt(i));
		}
		answerString = newString.toString();
		System.out.println(answerString);
		for (int i = 0; i < name.length(); i++) {

			if (vowels.contains((char) name.charAt(i))) {

				questionString.add(name.charAt(i));
			} else {
				questionString.add("_");
			}
		}
		name = null;
	}

	private void getvowels() {
		vowels.add('A');
		vowels.add('E');
		vowels.add('O');
		vowels.add('I');
		vowels.add('U');
		vowels.add('a');
		vowels.add('e');
		vowels.add('i');
		vowels.add('o');
		vowels.add('u');
	}
}

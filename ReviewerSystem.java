// Reviewer System

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;
import java.io.*;

public class ReviewerSystem extends JFrame implements ActionListener {
	JPanel cards = new JPanel();
	JPanel panelA = new JPanel();
	JPanel panelB = new JPanel();
	JPanel panelC = new JPanel();
	JPanel panelD = new JPanel();
	JPanel panelE = new JPanel();
	JPanel panel1 = new JPanel();
	JPanel panel2 = new JPanel();
	JPanel panel3 = new JPanel();
	JPanel panel4 = new JPanel();
	JPanel panel5 = new JPanel();
	JPanel panel6 = new JPanel();
	JPanel panel7 = new JPanel();
	JButton btnSet = new JButton("Set");
	JTextField txtSet = new JTextField();
	JButton btnReview = new JButton("Review");
	JLabel lblQuestion = new JLabel("");
	JTextField txtAnswer = new JTextField();
	JButton btnSubmit = new JButton("Submit");
	JLabel lblMessage = new JLabel("");
	JButton btnNext = new JButton("Next");
	JButton btnReport = new JButton("Report");
	JLabel lblReport = new JLabel("");
	JButton btnCancel = new JButton("Back/Main");

	ReviewCenter rc = new ReviewCenter();
	Computation computation = new Computation();
	Random random = new Random();
	String operators = "+-*/";
	int num1, num2, looper = 1, mixer = 0, correcter = 0;

	Color color1 = Color.decode("#B3E6FF");					//blue (background)
	Color color2 = Color.decode("#00BF6F"); 				//green (button)
	Color color3 = Color.decode("#007BFF"); 				//blue (button)
	Color color4 = Color.decode("#FFC107");
	Color color5 = Color.decode("#28A745");
	Color color6 = Color.decode("#DC3545");
	Color color7 = Color.decode("#FF8000");
	Color color8 = Color.decode("#FF7CA8");

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				myGUI();
			}
		});
	}

	private static void myGUI() {
		ReviewerSystem r = new ReviewerSystem("RBA Review Center");
		r.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		r.myComponents(r.getContentPane());
		r.setSize(500,350);
		r.setLocationRelativeTo(null);
		r.setVisible(true);
	}

	public ReviewerSystem(String name) {
		super(name);
	}

	private void myComponents(final Container pane) {
		thePanel1();
		thePanel2();
		thePanel3();
		thePanel4();
		thePanel5();
		thePanel6();
		thePanel7();

		// Card Layout
		cards = new JPanel(new CardLayout());
		cards.add(panel1, "panel1");
		cards.add(panelA, "panelA");								// Containing panel2 and panel3
		cards.add(panelB, "panelB");								// Containing panel2 and panel4
		cards.add(panelC, "panelC");								// Containing panel2 and panel5
		cards.add(panelD, "panelD");								// Containing panel2 and panel6
		cards.add(panelE, "panelE");								// Containing panel2 and panel7
		cards.setVisible(true);
		pane.add(cards, BorderLayout.CENTER);

		CardLayout cl = (CardLayout)(cards.getLayout());
		cl.show(cards, "panel1");
	}

	public void actionPerformed(ActionEvent e) {
		cards.setVisible(true);

		if (e.getActionCommand().equals("Start") || e.getActionCommand().equals("Cancel")) {
			thePanelA();

			CardLayout cl = (CardLayout)(cards.getLayout());
			cl.show(cards, "panelA");

			if (e.getActionCommand().equals("Cancel"))
				enablePanel2();
		} else if (e.getActionCommand().equals("Set")) {
			panelB.setLayout(new BorderLayout(1, 2));
			panelB.add(panel2, BorderLayout.WEST);
			panelB.add(panel4, BorderLayout.EAST);

			CardLayout cl = (CardLayout)(cards.getLayout());
			cl.show(cards, "panelB");

			disablePanel2();
		} else if (e.getActionCommand().equals("SetEnter")) {
			try {
				rc.setNumberOfQuestions(rc.getNumberOfQuestions() + Integer.parseInt(txtSet.getText()));
				thePanelA();
				txtSet.setText("");

				CardLayout cl = (CardLayout)(cards.getLayout());
				cl.show(cards, "panelA");

				enablePanel2();
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(cards, "ERROR: You must enter an integer number");
				txtSet.setText("");
			}
		} else if (e.getActionCommand().equals("Review")) {
			if (rc.getNumberOfQuestions() > 0) {
				panelC.setLayout(new BorderLayout(1, 2));
				panelC.add(panel2, BorderLayout.WEST);
				panelC.add(panel5, BorderLayout.EAST);

				CardLayout cl = (CardLayout)(cards.getLayout());
				cl.show(cards, "panelC");

				disablePanel2();
			} else {
				JOptionPane.showMessageDialog(cards, "ERROR: You have not set the number of questions yet");
			}
		} else if (e.getActionCommand().equals("Add") || e.getActionCommand().equals("Sub") || e.getActionCommand().equals("Mul") || e.getActionCommand().equals("Div") || e.getActionCommand().equals("Mix")) {
			num1 = random.nextInt(50);
			num2 = random.nextInt(50);

			if (e.getActionCommand().equals("Add")) {
				computation.setOperator('+');
				rc.setAttemptAdd(rc.getAttemptAdd() + rc.getNumberOfQuestions());
				correcter = 1;									// correcter is to determine what operation was used (addition = 1, subtraction = 2, so on..)
			} else if (e.getActionCommand().equals("Sub")) {
				computation.setOperator('-');
				rc.setAttemptSub(rc.getAttemptSub() + rc.getNumberOfQuestions());
				correcter = 2;
			} else if (e.getActionCommand().equals("Mul")) {
				computation.setOperator('*');
				rc.setAttemptMul(rc.getAttemptMul() + rc.getNumberOfQuestions());
				correcter = 3;
			} else if (e.getActionCommand().equals("Div")) {
				computation.setOperator('/');
				rc.setAttemptDiv(rc.getAttemptDiv() + rc.getNumberOfQuestions());
				correcter = 4;
			} else if (e.getActionCommand().equals("Mix")) {
				char o = operators.charAt(random.nextInt(operators.length()));
				computation.setOperator(o);
				rc.setAttemptMix(rc.getAttemptMix() + rc.getNumberOfQuestions());
				mixer = 1;										// mixer is to determine that it is mixed operation (mixed = 1, not mixed = 0)
				correcter = 5;
			}

			multiplyDivide();

			computation.setA(num1);
			computation.setB(num2);
			lblQuestion.setText(computation.getA() + "  " + computation.getOperator() + "  " + computation.getB() + "  = ");

			panelD.setLayout(new BorderLayout(1, 2));
			panelD.add(panel2, BorderLayout.WEST);
			panelD.add(panel6, BorderLayout.EAST);

			CardLayout cl = (CardLayout)(cards.getLayout());
			cl.show(cards, "panelD");

			btnCancel.setEnabled(false);
		} else if (e.getActionCommand().equals("Submit")) {
			try {
				int answer = Integer.parseInt(txtAnswer.getText());

				if (answer == computation.compute()) {
					lblMessage.setText("Correct!");

					switch (correcter) {
						case 1:
							rc.setCorrectAdd(rc.getCorrectAdd() + 1);
							break;
						case 2:
							rc.setCorrectSub(rc.getCorrectSub() + 1);
							break;
						case 3:
							rc.setCorrectMul(rc.getCorrectMul() + 1);
							break;
						case 4:
							rc.setCorrectDiv(rc.getCorrectDiv() + 1);
							break;
						case 5:
							rc.setCorrectMix(rc.getCorrectMix() + 1);
							break;
					}
				} else {
					lblMessage.setText("Wrong! Correct answer: " + computation.compute());
				}

				if (looper == rc.getNumberOfQuestions())
					btnNext.setText("Done");

				btnSubmit.setEnabled(false);
				txtAnswer.setEnabled(false);
				btnNext.setVisible(true);
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(cards, "ERROR: You must enter an integer number");
				txtAnswer.setText("");
			}
		} else if (e.getActionCommand().equals("Next")) {
			if (looper < rc.getNumberOfQuestions()) {
				num1 = random.nextInt(50);
				num2 = random.nextInt(50);

				if (mixer == 1) {
					char o = operators.charAt(random.nextInt(operators.length()));
					computation.setOperator(o);
				}

				multiplyDivide();

				computation.setA(num1);
				computation.setB(num2);
				lblQuestion.setText(computation.getA() + "  " + computation.getOperator() + "  " + computation.getB() + "  = ");

				panelD.setLayout(new BorderLayout(1, 2));
				panelD.add(panel2, BorderLayout.WEST);
				panelD.add(panel6, BorderLayout.EAST);

				CardLayout cl = (CardLayout)(cards.getLayout());
				cl.show(cards, "panelD");

				txtAnswer.setText("");
				txtAnswer.setEnabled(true);
				btnSubmit.setEnabled(true);
				lblMessage.setText("");
				btnNext.setVisible(false);
				looper++;										// looper is to determine the limitation of the number of questions
			} else if (looper == rc.getNumberOfQuestions()) {
				lblQuestion.setText("");
				txtAnswer.setText("");
				lblMessage.setText("");
				btnNext.setText("Next");
				btnNext.setVisible(false);

				panelA.setLayout(new BorderLayout(1, 2));
				panelA.add(panel2, BorderLayout.WEST);
				panelA.add(panel3, BorderLayout.EAST);

				CardLayout cl = (CardLayout)(cards.getLayout());
				cl.show(cards, "panelA");

				enablePanel2();
				txtAnswer.setEnabled(true);
				btnSubmit.setEnabled(true);
				btnCancel.setEnabled(true);
				looper = 1;
				mixer = 0;
				correcter = 0;
			}
		} else if (e.getActionCommand().equals("Report")) {
			if (rc.getAttemptAdd() > 0 || rc.getAttemptSub() > 0 || rc.getAttemptMul() > 0 || rc.getAttemptDiv() > 0 || rc.getAttemptMix() > 0) {
				lblReport.setText(rc + "");

				panelE.setLayout(new BorderLayout(1, 2));
				panelE.add(panel2, BorderLayout.WEST);
				panelE.add(panel7, BorderLayout.EAST);

				CardLayout cl = (CardLayout)(cards.getLayout());
				cl.show(cards, "panelE");

				disablePanel2();
			} else {
				JOptionPane.showMessageDialog(cards, "ERROR: You have not answered Computation yet");
			}
		} 
	}

	// Starting panel
	private void thePanel1() {
		ImageIcon imgIcon = new ImageIcon("RBA Logo.png");
		imgIcon = new ImageIcon(imgIcon.getImage().getScaledInstance(300, 200, java.awt.Image.SCALE_SMOOTH));
		JLabel lblLogo = new JLabel(imgIcon);
		JLabel lblHeader = new JLabel("Welcome to RBA Review Center!");
		JButton btnStart = new JButton("START");

		lblLogo.setBounds(115, 60, 260, 75);
		lblHeader.setBounds(145, 132, 240, 26);
		lblHeader.setFont(new Font("Calibri", 1, 15));

		btnStart.setBounds(185, 180, 120, 50);
		btnStart.setFont(new Font("Calibri", 1, 17));
		btnStart.setFocusable(false);
		btnStart.setBackground(color2);
		btnStart.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		btnStart.setForeground(Color.white);
		btnStart.setActionCommand("Start");
		btnStart.addActionListener(this);

		panel1.setBackground(color1);
		panel1.setLayout(null);
		panel1.add(lblLogo, BorderLayout.CENTER);
		panel1.add(lblHeader);
		panel1.add(btnStart);
	}
	// Navigation panel
	private void thePanel2() {
		JLabel lblChoose = new JLabel("Choose:");

		lblChoose.setBounds(25, 25, 70, 26);
		lblChoose.setFont(new Font("Calibri", 1, 15));

		btnSet.setBounds(25, 65, 100, 40);
		btnSet.setFont(new Font("Calibri", 1, 14));
		btnSet.setFocusable(false);
		btnSet.setBackground(color3);
		btnSet.setForeground(Color.white);
		btnSet.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		btnSet.setActionCommand("Set");
		btnSet.addActionListener(this);

		btnReview.setBounds(25, 120, 100, 40);
		btnReview.setFont(new Font("Calibri", 1, 14));
		btnReview.setFocusable(false);
		btnReview.setBackground(color4);
		btnReview.setForeground(Color.white);
		btnReview.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		btnReview.setActionCommand("Review");
		btnReview.addActionListener(this);

		btnReport.setBounds(25, 175, 100, 40);
		btnReport.setFont(new Font("Calibri", 1, 14));
		btnReport.setFocusable(false);
		btnReport.setBackground(color5);
		btnReport.setForeground(Color.white);
		btnReport.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		btnReport.setActionCommand("Report");
		btnReport.addActionListener(this);

		btnCancel.setBounds(25, 230, 100, 40);
		btnCancel.setFont(new Font("Calibri", 1, 14));
		btnCancel.setFocusable(false);
		btnCancel.setBackground(color6);
		btnCancel.setForeground(Color.white);
		btnCancel.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		btnCancel.setActionCommand("Cancel");
		btnCancel.addActionListener(this);

		panel2.setPreferredSize(new Dimension(150, 350));
		panel2.setBackground(color1);
		panel2.setLayout(null);
		panel2.add(lblChoose);
		panel2.add(btnSet);
		panel2.add(btnReview);
		panel2.add(btnReport);
		panel2.add(btnCancel);
	}
	// Guidelines panel (main)
	private void thePanel3() {
		JLabel lblHeader = new JLabel("Guidelines (steps):");
		JLabel lblSetGuide = new JLabel("<html>1. Set button - to set number of questions to be answered.</html>");
		JLabel lblReviewGuide = new JLabel("<html>2. Review button - to answer certain number of questions.</html>");
		JLabel lblReportGuide = new JLabel("<html>3. Report button - to see the summary report of the activity.</html>");
		JLabel lblNote = new JLabel("<html>*Note: Steps are in chronological order.</html>");

		lblHeader.setBounds(25, 25, 150, 26);
		lblSetGuide.setBounds(25, 59, 300, 36);
		lblReviewGuide.setBounds(25, 99, 300, 36);
		lblReportGuide.setBounds(25, 139, 300, 36);
		lblNote.setBounds(25, 179, 300, 36);

		lblHeader.setFont(new Font("Calibri", 1, 15));
		lblSetGuide.setFont(new Font("Calibri", 1, 14));
		lblReviewGuide.setFont(new Font("Calibri", 1, 14));
		lblReportGuide.setFont(new Font("Calibri", 1, 14));
		lblNote.setFont(new Font("Calibri", 1, 14));

		panel3.setPreferredSize(new Dimension(335, 350));
		panel3.setLayout(null);
		panel3.add(lblHeader);
		panel3.add(lblSetGuide);
		panel3.add(lblReviewGuide);
		panel3.add(lblReportGuide);
		panel3.add(lblNote);
	}
	// Setting number of questions panel
	private void thePanel4() {
		JLabel lblHeader = new JLabel("Set Number of Questions:");
		JButton btnEnter = new JButton("Enter");

		lblHeader.setBounds(25, 25, 200, 26);
		lblHeader.setFont(new Font("Calibri", 1, 15));
		txtSet.setBounds(25, 65, 170, 40);
		txtSet.setMargin(new Insets(0, 7, 0, 7));

		btnEnter.setBounds(210, 65, 100, 40);
		btnEnter.setFont(new Font("Calibri", 1, 14));
		btnEnter.setFocusable(false);
		btnEnter.setBackground(color2);
		btnEnter.setForeground(Color.white);
		btnEnter.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		btnEnter.setActionCommand("SetEnter");
		btnEnter.addActionListener(this);

		panel4.setPreferredSize(new Dimension(335, 350));
		panel4.setLayout(null);
		panel4.add(lblHeader);
		panel4.add(txtSet);
		panel4.add(btnEnter);
	}
	// Choosing operations to be solved panel
	private void thePanel5() {
		JLabel lblHeader = new JLabel("Choose Competency:");
		JButton btnAdd = new JButton("Addition");
		JButton btnSub = new JButton("Subtraction");
		JButton btnMul = new JButton("Multiplication");
		JButton btnDiv = new JButton("Division");
		JButton btnMix = new JButton("Mixed");

		lblHeader.setBounds(25, 25, 200, 26);
		lblHeader.setFont(new Font("Calibri", 1, 14));

		btnAdd.setBounds(40, 65, 120, 50);
		btnAdd.setFont(new Font("Calibri", 1, 14));
		btnAdd.setFocusable(false);
		btnAdd.setActionCommand("Add");
		btnAdd.addActionListener(this);

		btnSub.setBounds(175, 65, 120, 50);
		btnSub.setFont(new Font("Calibri", 1, 14));
		btnSub.setFocusable(false);
		btnSub.setActionCommand("Sub");
		btnSub.addActionListener(this);

		btnMul.setBounds(40, 130, 120, 50);
		btnMul.setFont(new Font("Calibri", 1, 14));
		btnMul.setFocusable(false);
		btnMul.setActionCommand("Mul");
		btnMul.addActionListener(this);

		btnDiv.setBounds(175, 130, 120, 50);
		btnDiv.setFont(new Font("Calibri", 1, 14));
		btnDiv.setFocusable(false);
		btnDiv.setActionCommand("Div");
		btnDiv.addActionListener(this);

		btnMix.setBounds(107, 195, 120, 50);
		btnMix.setFont(new Font("Calibri", 1, 14));
		btnMix.setFocusable(false);
		btnMix.setActionCommand("Mix");
		btnMix.addActionListener(this);

		panel5.setPreferredSize(new Dimension(335, 350));
		panel5.setLayout(null);
		panel5.add(lblHeader);
		panel5.add(btnAdd);
		panel5.add(btnSub);
		panel5.add(btnMul);
		panel5.add(btnDiv);
		panel5.add(btnMix);
	}
	// Solving panel
	private void thePanel6() {
		JLabel lblHeader = new JLabel("Testing Your Competency:");

		lblHeader.setBounds(25, 25, 200, 26);
		lblHeader.setFont(new Font("Calibri", 1, 15));
		lblQuestion.setBounds(25, 70, 200, 26);
		lblQuestion.setFont(new Font("Calibri", 1, 14));
		txtAnswer.setBounds(95, 65, 100, 40);
		txtAnswer.setMargin(new Insets(0, 7, 0, 7));

		btnSubmit.setBounds(210, 65, 100, 40);
		btnSubmit.setFont(new Font("Calibri", 1, 14));
		btnSubmit.setFocusable(false);
		btnSubmit.setBackground(color2);
		btnSubmit.setForeground(Color.white);
		btnSubmit.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		btnSubmit.setActionCommand("Submit");
		btnSubmit.addActionListener(this);

		btnNext.setBounds(210, 120, 100, 40);
		btnNext.setFont(new Font("Calibri", 1, 14));
		btnNext.setFocusable(false);
		btnNext.setBackground(color8);
		btnNext.setForeground(Color.white);
		btnNext.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		btnNext.setActionCommand("Next");
		btnNext.addActionListener(this);
		btnNext.setVisible(false);

		lblMessage.setBounds(25, 115, 200, 26);
		lblMessage.setFont(new Font("Calibri", 1, 14));

		panel6.setPreferredSize(new Dimension(335, 350));
		panel6.setLayout(null);
		panel6.add(lblHeader);
		panel6.add(lblQuestion);
		panel6.add(txtAnswer);
		panel6.add(btnSubmit);
		panel6.add(lblMessage);
		panel6.add(btnNext);
	}
	// Printing the report panel
	private void thePanel7() {
		JLabel lblHeader = new JLabel("Report:");

		lblHeader.setBounds(25, 25, 200, 26);
		lblHeader.setFont(new Font("Calibri", 1, 15));

		lblReport.setBounds(25, 62, 300, 300);
		lblReport.setFont(new Font("Calibri", 1, 14));
		lblReport.setVerticalAlignment(JLabel.TOP);

		panel7.setPreferredSize(new Dimension(335, 350));
		panel7.setLayout(null);
		panel7.add(lblHeader);
		panel7.add(lblReport);
	}	
	private void thePanelA() {
		panelA.setLayout(new BorderLayout(1, 2));
		panelA.add(panel2, BorderLayout.WEST);
		panelA.add(panel3, BorderLayout.EAST);
	}
	// Disabling navigation buttons excluding Back
	private void disablePanel2() {
		btnSet.setEnabled(false);
		btnReview.setEnabled(false);
		btnReport.setEnabled(false);
	}
	// Enabling navigation buttons excluding Back
	private void enablePanel2() {
		btnSet.setEnabled(true);
		btnReview.setEnabled(true);
		btnReport.setEnabled(true);
	}
	private void multiplyDivide() {
		if (computation.getOperator() == '*') {
			num1 = random.nextInt(49) + 1;
			num2 = random.nextInt(9) + 1;
		} else if (computation.getOperator() == '/') {
			while (num1 % num2 != 0) {
				num1 = random.nextInt(49) + 1;
				num2 = random.nextInt(9) + 1;
			}
		}
	}
}

// Storage of data
class ReviewCenter {
	private int numberOfQuestions;
	private int correctAdd;
	private int correctSub;
	private int correctMul;
	private int correctDiv;
	private int correctMix;
	private int attemptAdd;
	private int attemptSub;
	private int attemptMul;
	private int attemptDiv;
	private int attemptMix;

	public void setNumberOfQuestions(int n) {
		numberOfQuestions = n;
	}
	public void setCorrectAdd(int c) {
		correctAdd = c;
	}
	public void setCorrectSub(int c) {
		correctSub = c;
	}
	public void setCorrectMul(int c) {
		correctMul = c;
	}
	public void setCorrectDiv(int c) {
	correctDiv = c;
	}
	public void setCorrectMix(int c) {
		correctMix = c;
	}
	public void setAttemptAdd(int a) {
		attemptAdd = a;
	}
	public void setAttemptSub(int a) {
		attemptSub = a;
	}
	public void setAttemptMul(int a) {
		attemptMul = a;
	}
	public void setAttemptDiv(int a) {
		attemptDiv = a;
	}
	public void setAttemptMix(int a) {
		attemptMix = a;
	}

	public int getNumberOfQuestions() {
		return numberOfQuestions;
	}
	public int getCorrectAdd() {
		return correctAdd;
	}
	public int getCorrectSub() {
		return correctSub;
	}
	public int getCorrectMul() {
		return correctMul;
	}
	public int getCorrectDiv() {
		return correctDiv;
	}
	public int getCorrectMix() {
		return correctMix;
	}
	public int getAttemptAdd() {
		return attemptAdd;
	}
	public int getAttemptSub() {
		return attemptSub;
	}
	public int getAttemptMul() {
		return attemptMul;
	}
	public int getAttemptDiv() {
		return attemptDiv;
	}
	public int getAttemptMix() {
		return attemptMix;
	}

	public ReviewCenter() { }

	public String toString() {
		String add = "", sub = "", mul = "", div = "", mix = "";

		if (attemptAdd > 0)
			add = "Addition " + correctAdd + " out of " + attemptAdd + "<br>";
		if (attemptSub > 0)
			sub = "Subtraction " + correctSub + " out of " + attemptSub + "<br>";
		if (attemptMul > 0)
			mul = "Mulitiplication " + correctMul + " out of " + attemptMul + "<br>";
		if (attemptDiv > 0)
			div = "Division " + correctDiv + " out of " + attemptDiv + "<br>";
		if (attemptMix > 0)
			mix = "Mixed Operations " + correctMix + " out of " + attemptMix;
		
		return "<html>" + add + sub + mul + div + mix + "</html>";
	}
}

// Computation of numbers
class Computation {
	private int a;
	private int b;
	private char operator;

	public void setA(int a) {
		this.a = a;
	}
	public void setB(int b) {
		this.b = b;
	}
	public void setOperator(char o) {
		operator = o;
	}

	public int getA() {
		return a;
	}
	public int getB() {
		return b;
	}
	public char getOperator() {
		return operator;
	}

	public Computation() {}

	public int compute() {
		return compute(operator);
	}
	public int compute(char c) {
		int result = 0;

		switch (c) {
			case '+':
				result = getA() + getB();
				break;
			case '-':
				result = getA() - getB();
				break;
			case '*':
				result = getA() * getB();
				break;
			case '/':
				result = getA() / getB();
				break;
		}

		return result;
	}

	public void result() {
		System.out.print(getA() + " " + getOperator() + " " + getB() + " = ");
	}
}
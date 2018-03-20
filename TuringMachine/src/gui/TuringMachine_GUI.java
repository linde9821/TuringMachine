package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import turingMachine.ProgrammLine;
import turingMachine.TapeCell;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;

public class TuringMachine_GUI extends JFrame {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField tfZeile;
    private JTextField tfGoalLineIfStar;
    private JTextField tfGoalLineIfOne;
    private JTextField tfGoToLineIfStar;
    private JTextField tfGotoLineIfOne;
    private JTextField tfStartposition;
    private JLabel lblTape;
    private JComboBox<String> comboBoxIfStar;
    private JComboBox<String> comboBoxIfOne;

    private int position;
    private ArrayList<TapeCell> tape;
    private ArrayList<ProgrammLine> programm;
    private DefaultListModel<String> programmliste;
    private JTextField tfBand;
    private JList<String> List;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
	try {
	    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	} catch (Throwable e) {
	    e.printStackTrace();
	}
	EventQueue.invokeLater(new Runnable() {
	    public void run() {
		try {
		    TuringMachine_GUI frame = new TuringMachine_GUI();
		    frame.setVisible(true);
		} catch (Exception e) {
		    e.printStackTrace();
		}
	    }
	});
    }

    /**
     * Create the frame.
     */
    public TuringMachine_GUI() {
	String language = System.getProperty("user.language");
	System.out.println(language);

	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setBounds(100, 100, 564, 420);
	contentPane = new JPanel();
	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	setContentPane(contentPane);
	contentPane.setLayout(null);

	programmliste = new DefaultListModel<String>();
	tape = new ArrayList<TapeCell>();
	programm = new ArrayList<ProgrammLine>();

	JScrollPane scrollPane = new JScrollPane();
	scrollPane.setBounds(10, 129, 247, 241);
	contentPane.add(scrollPane);

	List = new JList<String>();
	List.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	scrollPane.setViewportView(List);

	List.setModel(programmliste);

	JButton btnStarten = new JButton("Starten");
	btnStarten.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		perform();
	    }
	});
	btnStarten.setBounds(267, 313, 89, 23);
	contentPane.add(btnStarten);

	JButton btnSchritt = new JButton("1 Schritt");
	btnSchritt.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		oneStep();
	    }
	});
	btnSchritt.setBounds(366, 313, 141, 23);
	contentPane.add(btnSchritt);

	lblTape = new JLabel("");
	lblTape.setBounds(267, 288, 271, 14);
	contentPane.add(lblTape);
	renderTape();

	JLabel lblProgramm = new JLabel("Programm:");
	lblProgramm.setBounds(10, 104, 247, 14);
	contentPane.add(lblProgramm);

	JButton btnHinzufgen = new JButton("Hinzuf\u00FCgen");
	btnHinzufgen.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		addProgrammLine();
	    }
	});
	btnHinzufgen.setBounds(382, 11, 138, 82);
	contentPane.add(btnHinzufgen);

	JButton btnMarkiertenEintragBearbeiten = new JButton("markierten Eintrag bearbeiten");
	btnMarkiertenEintragBearbeiten.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		adjustProgrammLine();
	    }
	});
	btnMarkiertenEintragBearbeiten.setBounds(267, 127, 204, 23);
	contentPane.add(btnMarkiertenEintragBearbeiten);

	JLabel lblZeile = new JLabel("Zeile:");
	lblZeile.setBounds(10, 15, 46, 14);
	contentPane.add(lblZeile);

	JLabel lblAktionBei = new JLabel("Aktion bei \"*\"");
	lblAktionBei.setBounds(66, 15, 89, 14);
	contentPane.add(lblAktionBei);

	JLabel lblAktionBei_1 = new JLabel("Aktion bei \"1\"");
	lblAktionBei_1.setBounds(225, 15, 92, 14);
	contentPane.add(lblAktionBei_1);

	tfZeile = new JTextField();
	tfZeile.setBounds(10, 46, 27, 20);
	contentPane.add(tfZeile);
	tfZeile.setColumns(10);

	JButton btnZurcksetzen = new JButton("Regeln zur\u00FCcksetzen");
	btnZurcksetzen.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		programmliste.clear();
		programm.clear();
		programm = new ArrayList<ProgrammLine>();
		programmliste = new DefaultListModel<String>();
		List = new JList<String>();
		List.setModel(programmliste);
		programmliste.clear();
		updateListComponent();
	    }
	});
	btnZurcksetzen.setBounds(267, 161, 204, 23);
	contentPane.add(btnZurcksetzen);

	tfGoalLineIfStar = new JTextField();
	tfGoalLineIfStar.setColumns(10);
	tfGoalLineIfStar.setBounds(167, 46, 27, 20);
	contentPane.add(tfGoalLineIfStar);

	tfGoalLineIfOne = new JTextField();
	tfGoalLineIfOne.setColumns(10);
	tfGoalLineIfOne.setBounds(322, 46, 27, 20);
	contentPane.add(tfGoalLineIfOne);

	comboBoxIfStar = new JComboBox<String>();
	comboBoxIfStar.setSelectedItem("left");
	comboBoxIfStar.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		if (comboBoxIfStar.getSelectedIndex() == ProgrammLine.goTo) {
		    tfGoToLineIfStar.setEnabled(true);
		} else {
		    tfGoToLineIfStar.setEnabled(false);
		    tfGoToLineIfStar.setText("");
		}
	    }
	});
	comboBoxIfStar.setModel(
		new DefaultComboBoxModel<String>(new String[] { "left", "right", "star", "one", "stop ", "goTo" }));
	comboBoxIfStar.setBounds(66, 46, 89, 20);
	contentPane.add(comboBoxIfStar);

	comboBoxIfOne = new JComboBox<String>();
	comboBoxIfOne.setSelectedItem("left");
	comboBoxIfOne.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		if (comboBoxIfOne.getSelectedIndex() == ProgrammLine.goTo) {
		    tfGotoLineIfOne.setEnabled(true);
		} else {
		    tfGotoLineIfOne.setEnabled(false);
		    tfGotoLineIfOne.setText("");
		}
	    }
	});
	comboBoxIfOne.setModel(
		new DefaultComboBoxModel<String>(new String[] { "left", "right", "star", "one", "stop ", "goTo" }));
	comboBoxIfOne.setBounds(225, 46, 89, 20);
	contentPane.add(comboBoxIfOne);

	tfGoToLineIfStar = new JTextField();
	tfGoToLineIfStar.setEnabled(false);
	tfGoToLineIfStar.setBounds(66, 73, 86, 20);
	contentPane.add(tfGoToLineIfStar);
	tfGoToLineIfStar.setColumns(10);

	tfGotoLineIfOne = new JTextField();
	tfGotoLineIfOne.setEnabled(false);
	tfGotoLineIfOne.setColumns(10);
	tfGotoLineIfOne.setBounds(225, 73, 86, 20);
	contentPane.add(tfGotoLineIfOne);

	JLabel lblStartposition = new JLabel("Startposition:");
	lblStartposition.setBounds(267, 263, 82, 14);
	contentPane.add(lblStartposition);

	tfStartposition = new JTextField();
	tfStartposition.setBounds(355, 260, 21, 20);
	contentPane.add(tfStartposition);
	tfStartposition.setColumns(10);

	JButton btnSchlieen = new JButton("Schlie\u00DFen");
	btnSchlieen.addKeyListener(new KeyAdapter() {
	    @Override
	    public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER)
		    close();
	    }
	});
	btnSchlieen.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		close();
	    }
	});
	btnSchlieen.setBounds(267, 347, 89, 23);
	contentPane.add(btnSchlieen);

	JLabel lblBand = new JLabel("Band:");
	lblBand.setBounds(267, 195, 46, 14);
	contentPane.add(lblBand);

	tfBand = new JTextField();
	tfBand.setColumns(10);
	tfBand.setBounds(355, 195, 21, 20);
	contentPane.add(tfBand);

	JButton btnAdd = new JButton("add");
	btnAdd.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		addTapeCell();
	    }
	});
	btnAdd.setBounds(418, 195, 89, 23);
	contentPane.add(btnAdd);

	JButton btnNewButton = new JButton("Band z.");
	btnNewButton.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		resetTape();
	    }
	});
	btnNewButton.setBounds(418, 229, 89, 23);
	contentPane.add(btnNewButton);

	JButton btnSetzen = new JButton("setzen");
	btnSetzen.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		position = Integer.parseInt(tfStartposition.getText());
		renderTape();
	    }
	});
	btnSetzen.setBounds(418, 259, 89, 23);
	contentPane.add(btnSetzen);

	JLabel lblZielzeile = new JLabel("Zielzeile");
	lblZielzeile.setBounds(165, 11, 46, 14);
	contentPane.add(lblZielzeile);

	JLabel lblZielzeile2 = new JLabel("Zielzeile");
	lblZielzeile2.setBounds(322, 11, 46, 14);
	contentPane.add(lblZielzeile2);

	JButton btnAllesZurcksetzen = new JButton("alles zur\u00FCcksetzen");
	btnAllesZurcksetzen.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent arg0) {
		lblTape.setText("");

		position = 0;
		tape = new ArrayList<TapeCell>();
		List = new JList<String>();
		programmliste.clear();
		List.setModel(programmliste);
		programm = new ArrayList<ProgrammLine>();
		programmliste = new DefaultListModel<String>();
		programmliste.clear();
		tfBand.setText("");
		tfStartposition.setText("0");

		renderTape();
		updateListComponent();
	    }
	});
	btnAllesZurcksetzen.setBounds(366, 347, 141, 23);
	contentPane.add(btnAllesZurcksetzen);
	contentPane.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[] { tfZeile, comboBoxIfStar,
		tfGoToLineIfStar, tfGoalLineIfStar, comboBoxIfOne, tfGoalLineIfOne, tfGotoLineIfOne, btnHinzufgen,
		btnMarkiertenEintragBearbeiten, btnZurcksetzen, tfBand, btnAdd, btnNewButton, tfStartposition,
		btnSetzen, btnStarten, btnSchritt, btnSchlieen }));

	setTitle("Turing machine");

	tfZeile.requestFocus();

	updateListComponent();

	if (language.equals("de")) {
	    lblZielzeile2.setText("Zielzeile");
	    lblZielzeile.setText("Zielzeile");
	    btnSetzen.setText("setzen");
	    btnNewButton.setText("Band z.");
	    btnAdd.setText("hinz.");
	    lblBand.setText("Band");
	    lblStartposition.setText("Startposition");
	    btnSchlieen.setText("Schließen");
	    btnZurcksetzen.setText("Regeln zurücksetzen");
	    lblZeile.setText("Zeile:");
	    lblAktionBei.setText("Aktion bei \"*\"");
	    lblAktionBei_1.setText("Aktion bei \"1\"");
	    btnStarten.setText("Starten");
	    btnSchritt.setText("ein Schritt");
	    lblProgramm.setText("Programm:");
	    btnHinzufgen.setText("Hinzufügen");
	    btnMarkiertenEintragBearbeiten.setText("markierten Eintrag bearbeiten");
	} else {
	    lblZielzeile2.setText("goalline");
	    lblZielzeile.setText("goalline");
	    btnSetzen.setText("set");
	    btnNewButton.setText("reset T.");
	    btnAdd.setText("add");
	    lblBand.setText("Tape");
	    lblStartposition.setText("startposition");
	    btnSchlieen.setText("close");
	    btnZurcksetzen.setText("reset rules");
	    lblZeile.setText("line:");
	    lblAktionBei.setText("action by \"*\"");
	    lblAktionBei_1.setText("action by \"1\"");
	    btnStarten.setText("start");
	    btnSchritt.setText("one step");
	    lblProgramm.setText("programm:");
	    btnHinzufgen.setText("add");
	    btnMarkiertenEintragBearbeiten.setText("edit marked rule");
	}
    }

    boolean stoponeStep = false;
    int programPositionOneStep = 0;
    int durchlaufeOneStep = 0;

    private void oneStep() {
	if (!stoponeStep) {
	    int lastPos;

	    lastPos = this.position;

	    ProgrammLine currentLine = programm.get(programPositionOneStep);

	    position += currentLine.perform(position, tape);

	    programPositionOneStep = currentLine.getGoalLine() - 1;

	    if (currentLine.getHasStoped()) {
		stoponeStep = true;
		this.position = lastPos;
	    }

	    renderTape();
	    durchlaufeOneStep++;
	} else {
	    JOptionPane.showMessageDialog(null, "Programm ist beendet! " + durchlaufeOneStep + " Schritte!");
	    durchlaufeOneStep = 0;
	    stoponeStep = false;
	    programPositionOneStep = 0;
	}

    }

    private void perform() {
	boolean stop = false;
	int programPosition = 0;
	int lastPos;
	int steps = 0;

	while (stop == false) {
	    lastPos = this.position;

	    ProgrammLine currentLine = programm.get(programPosition);

	    position += currentLine.perform(position, tape);

	    programPosition = currentLine.getGoalLine() - 1;

	    if (currentLine.getHasStoped()) {
		stop = true;
		this.position = lastPos;
	    }

	    renderTape();

	    steps++;
	}

	JOptionPane.showMessageDialog(null, "Programm ist beendet! " + steps + " Schritte!");

    }

    private void renderTape() {
	StringBuffer strbf = new StringBuffer();

	for (TapeCell currentCell : tape) {
	    strbf.append(currentCell.getSign());
	}

	strbf.append(" : " + position);

	System.out.println(strbf.toString() + "\n");
	lblTape.setText(strbf.toString());
    }

    private void addTapeCell() {
	String temp = tfBand.getText();

	if (temp.equals("1")) {
	    tape.add(new TapeCell(TapeCell.one));
	    renderTape();
	} else if (temp.equals("*")) {
	    tape.add(new TapeCell(TapeCell.star));
	    renderTape();
	} else {
	    JOptionPane.showMessageDialog(null, "Das ist kein zugelassener Wert.");
	}

	tfBand.requestFocus();
    }

    private void addProgrammLine() {
	int line = Integer.parseInt(tfZeile.getText());

	int ifStar = comboBoxIfStar.getSelectedIndex();

	int goToPosifStar = 0;
	boolean goToCommandifStar = false;

	switch (ifStar) {
	/*
	 * left right star one stop goTo
	 * 
	 */
	case (0):
	    ifStar = ProgrammLine.left;
	    break;
	case (1):
	    ifStar = ProgrammLine.right;
	    break;
	case (2):
	    ifStar = ProgrammLine.star;
	    break;
	case (3):
	    ifStar = ProgrammLine.one;
	    break;
	case (4):
	    ifStar = ProgrammLine.stop;
	    break;
	case (5):
	    ifStar = ProgrammLine.goTo;
	    goToPosifStar = Integer.parseInt(tfGoToLineIfStar.getText());
	    goToCommandifStar = true;
	    break;
	}

	int goalLineIfStar = Integer.parseInt(tfGoalLineIfStar.getText());

	int ifOne = comboBoxIfOne.getSelectedIndex();

	int goToPosifOne = 0;
	boolean goToCommandifOne = false;

	switch (ifOne) {
	/*
	 * left right star one stop goTo
	 * 
	 */
	case (0):
	    ifOne = ProgrammLine.left;
	    break;
	case (1):
	    ifOne = ProgrammLine.right;
	    break;
	case (2):
	    ifOne = ProgrammLine.star;
	    break;
	case (3):
	    ifOne = ProgrammLine.one;
	    break;
	case (4):
	    ifOne = ProgrammLine.stop;
	    break;
	case (5):
	    ifOne = ProgrammLine.goTo;
	    goToPosifOne = Integer.parseInt(tfGotoLineIfOne.getText());
	    goToCommandifOne = true;
	    break;
	}

	int goalLineIfOne = Integer.parseInt(tfGoalLineIfOne.getText());

	ProgrammLine current = new ProgrammLine(line, ifStar, goalLineIfStar, ifOne, goalLineIfOne);

	if (goToCommandifStar) {
	    current.setGoToPosIfStar(goToPosifStar);
	}

	if (goToCommandifOne) {
	    current.setGoToPosIfOne(goToPosifOne);
	}

	programm.add(line - 1, current);

	tfZeile.setText(Integer.toString(programmliste.size() + 2));
	comboBoxIfStar.setSelectedItem("left");
	comboBoxIfOne.setSelectedItem("left");
	tfGoalLineIfOne.setText("");
	tfGoalLineIfStar.setText("");
	tfGotoLineIfOne.setText("");
	tfGoToLineIfStar.setText("");
	tfGoToLineIfStar.setEnabled(false);
	tfGotoLineIfOne.setEnabled(false);
	tfGoalLineIfOne.setText("");

	orderProgramm();

	updateListComponent();
    }

    private void adjustProgrammLine() {
	int index = List.getSelectedIndex();

	ProgrammLine current = new ProgrammLine(programm.get(index));
	programm.remove(index);

	tfZeile.setText(Integer.toString(current.getLine()));

	switch (current.getIfStar()) {
	case (-1):
	    comboBoxIfStar.setSelectedIndex(0);
	    break;
	case (1):
	    comboBoxIfStar.setSelectedIndex(1);
	    break;
	case (2):
	    comboBoxIfStar.setSelectedIndex(2);
	    break;
	case (3):
	    comboBoxIfStar.setSelectedIndex(3);
	    break;
	case (-100):
	    comboBoxIfStar.setSelectedIndex(4);
	    break;
	case (5):
	    comboBoxIfStar.setSelectedIndex(5);
	    break;
	}

	if (current.getGoToPosIfStar() != -1) {
	    tfGoToLineIfStar.setEnabled(true);
	    tfGoToLineIfStar.setText(Integer.toString(current.getGoToPosIfStar()));
	} else
	    tfGoToLineIfStar.setEnabled(false);

	tfGoalLineIfStar.setText(Integer.toString(current.getGoalLineIfStar()));

	switch (current.getIfOne()) {
	case (-1):
	    comboBoxIfOne.setSelectedIndex(0);
	    break;
	case (1):
	    comboBoxIfOne.setSelectedIndex(1);
	    break;
	case (2):
	    comboBoxIfOne.setSelectedIndex(2);
	    break;
	case (3):
	    comboBoxIfOne.setSelectedIndex(3);
	    break;
	case (-100):
	    comboBoxIfOne.setSelectedIndex(4);
	    break;
	case (5):
	    comboBoxIfOne.setSelectedIndex(5);
	    break;
	}

	if (current.getGoToPosIfOne() != -1) {
	    tfGotoLineIfOne.setEnabled(true);
	    tfGotoLineIfOne.setText(Integer.toString(current.getGoToPosIfOne()));
	} else
	    tfGotoLineIfOne.setEnabled(false);

	tfGoalLineIfOne.setText(Integer.toString(current.getGoalLineIfOne()));

	updateListComponent();
    }

    private void orderProgramm() {
	for (int n = programm.size(); n > 1; n--) {
	    for (int i = 0; i < n - 1; i++) {
		if (programm.get(i).getLine() == programm.get(i + 1).getLine()) {
		    for (int d = i + 1; d < programm.size(); d++) {
			programm.get(d).setLine(programm.get(d).getLine() + 1);
		    }

		    System.out.println("Swapped: " + i + " and " + (i + 1));
		}
	    }
	}
    }

    private void updateListComponent() {
	programmliste.clear();

	for (ProgrammLine temp : programm) {
	    programmliste.addElement(temp.toString());
	}
    }

    private void resetTape() {
	tape.clear();
	this.position = 0;

	tfStartposition.setText("0");
	renderTape();
    }

    private void close() {
	System.exit(0);
    }
}

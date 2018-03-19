package turingMachine;

import java.util.ArrayList;

public class ProgrammLine {
    //
    public final static int left = -1;
    public final static int right = 1;
    public final static int star = 2;
    public final static int one = 3;
    public final static int stop = -100;
    public final static int goTo = 5;

    int line;
    int ifStar;
    int ifOne;
    int goalLineIfStar;
    int goalLineIfOne;

    int goToPosIfStar;
    int goToPosIfOne;

    private int goalLine;

    private boolean hasStoped = false;

    void setHasStoped() {
	hasStoped = true;
    }

    public int getLine() {
	return line;
    }

    public void setLine(int line) {
	this.line = line;
    }

    public boolean getHasStoped() {
	return hasStoped;
    }

    public void setGoToPosIfStar(int goToPosIfStar) {
	this.goToPosIfStar = goToPosIfStar;
    }

    public void setGoToPosIfOne(int goToPosIfOne) {
	this.goToPosIfOne = goToPosIfOne;
    }

    public ProgrammLine(int line, int ifStar, int goalLineIfStar, int ifOne, int goalLineIfOne) {
	this.line = line;
	this.goalLineIfOne = goalLineIfOne;
	this.goalLineIfStar = goalLineIfStar;
	this.ifOne = ifOne;
	this.ifStar = ifStar;
	goToPosIfStar = -1;
	goToPosIfOne = -1;
    }

    public static int getLeft() {
	return left;
    }

    public static int getRight() {
	return right;
    }

    public static int getStar() {
	return star;
    }

    public static int getOne() {
	return one;
    }

    public static int getStop() {
	return stop;
    }

    public static int getGoto() {
	return goTo;
    }

    public int getIfStar() {
	return ifStar;
    }

    public int getIfOne() {
	return ifOne;
    }

    public int getGoalLineIfStar() {
	return goalLineIfStar;
    }

    public int getGoalLineIfOne() {
	return goalLineIfOne;
    }

    public int getGoToPosIfStar() {
	return goToPosIfStar;
    }

    public int getGoToPosIfOne() {
	return goToPosIfOne;
    }

    public void setIfStar(int ifStar) {
	this.ifStar = ifStar;
    }

    public void setIfOne(int ifOne) {
	this.ifOne = ifOne;
    }

    public void setGoalLineIfStar(int goalLineIfStar) {
	this.goalLineIfStar = goalLineIfStar;
    }

    public void setGoalLineIfOne(int goalLineIfOne) {
	this.goalLineIfOne = goalLineIfOne;
    }

    public void setHasStoped(boolean hasStoped) {
	this.hasStoped = hasStoped;
    }

    public ProgrammLine(ProgrammLine temp) {
	line = temp.line;
	ifStar = temp.ifStar;
	ifOne = temp.ifOne;
	goalLineIfStar = temp.goalLineIfStar;
	goalLineIfOne = temp.goalLineIfOne;

	goToPosIfStar = temp.goToPosIfStar;
	goToPosIfOne = temp.goToPosIfOne;

	goalLine = temp.goalLine;

	hasStoped = temp.hasStoped;
    }

    public int getGoalLine() {
	return goalLine;
    }

    private void setGoalLine(int goalLine) {
	this.goalLine = goalLine;
    }

    public int perform(int n, ArrayList<TapeCell> tape) {
	TapeCell currentCell = tape.get(n);
	
	if (currentCell.getSign() == '1') {
	    if (isWriteCommand(ifOne)) {
		currentCell.setSign(ifOne);
		setGoalLine(goalLineIfOne);
		return 0;
	    } else {
		setGoalLine(goalLineIfOne);

		if (ifOne == ProgrammLine.stop)
		    setHasStoped();

		if (ifOne == ProgrammLine.goTo)
		    ifOne = goToPosIfOne;

		return ifOne;
	    }
	} else if (currentCell.getSign() == '*') {
	    if (isWriteCommand(ifStar)) {
		currentCell.setSign(ifStar);
		setGoalLine(goalLineIfStar);
		return 0;
	    } else {
		setGoalLine(goalLineIfStar);

		if (ifStar == ProgrammLine.stop)
		    setHasStoped();

		if (ifStar == ProgrammLine.goTo)
		    ifStar = goToPosIfStar;

		return ifStar;
	    }
	} else
	    return 0;
    }

    private boolean isWriteCommand(int command) {
	switch (command) {
	case (ProgrammLine.one):
	    return true;

	case (ProgrammLine.star):
	    return true;

	default:
	    return false;
	}
    }

    public String toString() {
	StringBuffer strbf = new StringBuffer();

	strbf.append(line);

	switch (ifStar) {
	/*
	 * left right star one stop goTo
	 * 
	 */
	case (-1):
	    strbf.append(" (left)");
	    break;
	case (1):
	    strbf.append(" (right)");
	    break;
	case (2):
	    strbf.append(" (*)");
	    break;
	case (3):
	    strbf.append(" (1)");
	    break;
	case (-100):
	    strbf.append(" (stop)");
	    break;
	case (5):
	    strbf.append(" (goto: " + goToPosIfStar + ")");
	    break;
	}

	strbf.append(" " + goalLineIfStar);

	switch (ifOne) {
	/*
	 * left right star one stop goTo
	 * 
	 */
	case (-1):
	    strbf.append(" (left)");
	    break;
	case (1):
	    strbf.append(" (right)");
	    break;
	case (2):
	    strbf.append(" (*)");
	    break;
	case (3):
	    strbf.append(" (1)");
	    break;
	case (-100):
	    strbf.append(" stop");
	    break;
	case (5):
	    strbf.append(" (goto " + goToPosIfOne + ")");
	    break;
	}

	strbf.append(" " + goalLineIfOne);

	return strbf.toString();
    }
}

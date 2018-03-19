package turingMachine;

public class TapeCell {
    public final static int one = 0;
    public final static int star = 1;

    int sign;

    public TapeCell(int sign) {
	this.sign = sign;
    }

    public char getSign() {
	if (sign == TapeCell.one)
	    return '1';
	else if (sign == TapeCell.star)
	    return '*';
	else
	    return 'e';
    }

    public void setSign(int sign) {
	if (sign == ProgrammLine.star)
	    this.sign = star;

	else if (sign == ProgrammLine.one)
	    this.sign = one;
	else
	    this.sign = -1;
    }
}

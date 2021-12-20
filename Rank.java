public enum Rank {
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    TEN(10),
    JACK(10),
    QUEEN(10),
    KING(10),
    ACE(1, 11);

    public final int value;
    public final int aceValue2;

    private Rank(int value, int aceValue2) {
		this.value = value;
        this.aceValue2 = aceValue2;
	}

    // Constructor for ace
    private Rank(int value) {
		this.value = value;
        this.aceValue2 = -1;           // aceValue2 does not exist. Card is not ace
	}

    public int getValue() {
        return value;
    }

    public int getAceValue2() {
        return aceValue2;
    }

}
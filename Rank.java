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
    public final int value2;

    private Rank(int value, int value2) {
		this.value = value;
        this.value2 = value2;
	}

    // Constructor for ace
    private Rank(int value) {
		this.value = value;
        this.value2 = -1;           // Value2 does not exist
	}

    public int[] getValue() {
        int[] values = new int[2];

        values[0] = value;
        values[1] = value2;

        return values;
    }

}
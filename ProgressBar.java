class ProgressBar {
    private int barLength;
    private int startNum;
    private int numMax;
    private int decimals = 3;
    private String endBuffer = "";
    private String fillChar = "#";

    private double lastValue = 0.0;

    public ProgressBar(int barLength, int startNum, int numMax, int decimals, String endBuffer, String fillChar) {
        this.barLength = barLength;
        this.startNum = startNum;
        this.numMax = numMax;
        this.decimals = decimals;
        this.endBuffer = endBuffer;
        this.fillChar = fillChar;
    }

    public String updateBar(int newNum) {
        if ((double) newNum / numMax <= lastValue) {
            return "no change";
        }
        int barProportion = (int) ((double) newNum / numMax) * barLength;
        lastValue = (double) newNum / numMax;
        String barString = fillChar.repeat(barProportion);
        return endBuffer + barString + endBuffer;
    }
}
class ProgressBar {
    private int barLength;
    private int startNum;
    private int numMax;
    private int decimals = 1;
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
        double barPercentage = ( Math.pow(10, 2 + decimals) * ((double) newNum / numMax) ) / Math.pow(10, 2 + decimals);
        if (barPercentage <= lastValue) {
            return "no change";
        }
        int barProportion = (int) (barPercentage * barLength);
        lastValue = barPercentage;
        String barString = fillChar.repeat(barProportion);
        return endBuffer + barString + endBuffer;
    }
}
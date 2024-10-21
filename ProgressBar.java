public class ProgressBar {
    private int barLength;
    private int maxValue;
    private int decimals = 1;
    private String endBuffer = "";
    private String fillChar = "#";

    private double lastValue = 0.0;

    public ProgressBar(int barLength, int numMax, int decimals, String endBuffer, String fillChar) {
        this.barLength = barLength;
        this.maxValue = numMax;
        this.decimals = decimals;
        this.endBuffer = endBuffer;
        this.fillChar = fillChar;
    }

    public String updateBar(int currentValue) {
        
        //((int) (Math.pow(10, 2 + decimals) * currentValue / maxValue)) / Math.pow(10, 2 + decimals);
        final double BAR_PERCENTAGE = ((int) (Math.pow(10, 2 + decimals) * (currentValue / maxValue))) / Math.pow(10, 2 + decimals);
        if (BAR_PERCENTAGE <= lastValue) {
            return null;
        }

        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        final int BAR_PROPORTION = (int) (BAR_PERCENTAGE * barLength);
        lastValue = BAR_PERCENTAGE;

        final String BAR_STRING = fillChar.repeat(BAR_PROPORTION) + " ".repeat(barLength - BAR_PROPORTION);
        clearScreen();

        return BAR_PERCENTAGE * 100 + "%" + endBuffer + "[" + BAR_STRING + "]" +endBuffer;
    }

    private static void clearScreen() {  
        System.out.print("\033[H\033[2J");  
     } 
}
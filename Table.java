//import java.util.Arrays;


public class Table {
    private String[] headers;
    private String[][] data;

    public Table(String[] headers, String[][] data) {
        this.headers = headers;
        this.data = data;
    }

    public String getTable() {
        /* +---+---+
         * |   |   |
         * |   |   |
         * +---+---+
         * 
         * find longest element in each column
         * adjust to find length
         */

        int[] columnWidths = new int[headers.length];

        for (int i = 0; i < columnWidths.length; i++) {
            int dataLength = 0;
            for (int j = 0; j < data.length; j++) {
                if (data[j][i].length() > dataLength) {
                    dataLength = data[j][i].length();
                }
            }
            final int HEADER_LENGTH = headers[i].length();
            columnWidths[i] = dataLength > HEADER_LENGTH ? dataLength : HEADER_LENGTH;
        }

        String headerString = "";

        for (int width : columnWidths) {
            headerString += "+" + "-".repeat(width + 2/* +2 spaces for bookending spaces */);
        }

        headerString += "+\n";

        String rowsString = "";

        for (int i = 0; i < data.length; i++) {
            rowsString += tableRow(columnWidths, data[i]);
        }

        return headerString + tableRow(columnWidths, headers) + headerString + rowsString + headerString;
    }

    private String tableRow(int[] columnWidths, String[] values) {
        /*
         * | value (width-existing space) | 
        */

        String rowString = "| ";

        for (int i = 0; i < values.length; i++) {
            rowString += values[i] + " ".repeat(columnWidths[i] - values[i].length()) + " | ";
        }

        return rowString + "\n";
    }
}

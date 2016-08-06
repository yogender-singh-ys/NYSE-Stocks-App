package com.sam_chordas.android.stockhawk.data;

/**
 * Created by yogenders on 7/21/16.
 */
public class Quote {

    private String symbol;
    private String percentChange;
    private String change;
    private String bidPrice;
    private String is_up;

    public Quote(String bidPrice, String change, String percentChange, String symbol, String is_up) {
        this.bidPrice = bidPrice;
        this.change = change;
        this.percentChange = percentChange;
        this.symbol = symbol;
        this.is_up = is_up;
    }

    public String getSymbol()
    {
        return symbol;
    }

    public String getBidPrice() {
        return bidPrice;
    }

    public String getPercentChange() {
        return percentChange;
    }

    public String getChange() {
        return change;
    }

    public String getIs_up() {
        return is_up;
    }
}

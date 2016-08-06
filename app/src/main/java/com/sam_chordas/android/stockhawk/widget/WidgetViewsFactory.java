package com.sam_chordas.android.stockhawk.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.data.Quote;
import com.sam_chordas.android.stockhawk.data.QuoteColumns;
import com.sam_chordas.android.stockhawk.data.QuoteProvider;

import java.util.ArrayList;

/**
 * Created by yogenders on 7/10/16.
 */
public class    WidgetViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private ArrayList<Quote> quoteItem = new ArrayList<Quote>(); ;

    private Context ctxt=null;
    private int appWidgetId;

    public WidgetViewsFactory(Context ctxt, Intent intent) {
        this.ctxt=ctxt;
        appWidgetId=intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {

        Cursor initQueryCursor;
        initQueryCursor = ctxt.getContentResolver().query(QuoteProvider.Quotes.CONTENT_URI,
                new String[]{ QuoteColumns._ID, QuoteColumns.SYMBOL, QuoteColumns.BIDPRICE,
                        QuoteColumns.PERCENT_CHANGE, QuoteColumns.CHANGE, QuoteColumns.ISUP},
                QuoteColumns.ISCURRENT + " = ?",
                new String[]{"1"},
                null);

        initQueryCursor.moveToFirst();

        for (int i=0; i < initQueryCursor.getCount(); i++ )
        {
            initQueryCursor.moveToPosition(i);
            String bidPrice = initQueryCursor.getString(initQueryCursor.getColumnIndex(QuoteColumns.BIDPRICE));
            String change = initQueryCursor.getString(initQueryCursor.getColumnIndex(QuoteColumns.CHANGE));
            String percentChange = initQueryCursor.getString(initQueryCursor.getColumnIndex(QuoteColumns.PERCENT_CHANGE));
            String symbol = initQueryCursor.getString(initQueryCursor.getColumnIndex(QuoteColumns.SYMBOL));
            String is_up = initQueryCursor.getString(initQueryCursor.getColumnIndex(QuoteColumns.ISUP));
            Quote quote = new Quote(bidPrice,change,percentChange,symbol,is_up);
            quoteItem.add(quote);
        }

    }

    @Override
    public void onDestroy() {
        // no-op
    }


    @Override
    public int getCount() {
        return(quoteItem.size());
    }


    @Override
    public RemoteViews getViewAt(int position) {

        Quote quote = quoteItem.get(position);

        RemoteViews row=new RemoteViews(ctxt.getPackageName(), R.layout.list_item_quote);
        row.setTextViewText(R.id.stock_symbol, quote.getSymbol());
        row.setTextViewText(R.id.bid_price, quote.getBidPrice());
        row.setTextViewText(R.id.change,quote.getChange());
        Intent i=new Intent();
        Bundle extras=new Bundle();

        extras.putString(WidgetProvider.EXTRA_WORD, quote.getSymbol());
        i.putExtras(extras);
        row.setOnClickFillInIntent(R.id.stock_symbol, i);

        return(row);
    }


    @Override
    public RemoteViews getLoadingView() {
        return(null);
    }

    @Override
    public int getViewTypeCount() {
        return(1);
    }

    @Override
    public long getItemId(int position) {
        return(position);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public void onDataSetChanged() {

    }
}

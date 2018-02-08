package com.foxconn.alan.my_tvapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v17.leanback.app.BrowseFragment;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.Presenter;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by alan on 02/02/2018.
 */

public class MainFragment extends BrowseFragment {
    private static final String TAG = MainFragment.class.getSimpleName();

    private ArrayObjectAdapter mRowsAdapter;
    private static final int GRID_ITEM_WIDTH = 300;
    private static final int GRID_ITEM_HEIGHT = 200;

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        Log.i(TAG, "onActivityCreated");
        super.onActivityCreated(savedInstanceState);

        setupElements();
        loadRows();
    }

    private void setupElements(){
        //setBadgeDrawable(getActivity().getResources().getDrawable(R.drawable.lb_action_bg));
        setTitle("Hello Android TV");
        setHeadersState(HEADERS_ENABLED);
        setHeadersTransitionOnBackEnabled(true);

        setBrandColor(getResources().getColor(R.color.fastlane_BG));
        setSearchAffordanceColor(getResources().getColor(R.color.search_opaque));
    }

    private class GridItemPresenter extends Presenter{
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent) {
            TextView view = new TextView(parent.getContext());
            view.setLayoutParams(new ViewGroup.LayoutParams(GRID_ITEM_WIDTH, GRID_ITEM_HEIGHT));
            view.setFocusable(true);
            view.setFocusableInTouchMode(true);
            view.setBackgroundColor(getResources().getColor(R.color.default_background));
            view.setTextColor(Color.WHITE);
            view.setGravity(Gravity.CENTER);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, Object item) {
            ((TextView) viewHolder.view).setText((String) item);
        }

        @Override
        public void onUnbindViewHolder(ViewHolder viewHolder) {

        }
    }

    private void loadRows(){
        mRowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());

        HeaderItem gridItemPresenterHeader = new HeaderItem(0, "GridItemPresenter");
        HeaderItem cardPresenterHeader = new HeaderItem(1, "CardPresenter");

        GridItemPresenter mGridPresenter = new GridItemPresenter();
        CardPresenter cardPresenter = new CardPresenter();

        /*GridPresenter*/
        ArrayObjectAdapter gridRowAdapter = new ArrayObjectAdapter(mGridPresenter);
        gridRowAdapter.add("ITEM 1");
        gridRowAdapter.add("ITEM 2");
        gridRowAdapter.add("ITEM 3");
        /*CardPresenter*/
        ArrayObjectAdapter cardRowAdapter = new ArrayObjectAdapter(cardPresenter);
        for(int i=0; i<10; i++) {
            Movie movie = new Movie();
            movie.setTitle("title" + i);
            movie.setStudio("studio" + i);
            cardRowAdapter.add(movie);
        }
        /*Set Rows*/
        mRowsAdapter.add(new ListRow(gridItemPresenterHeader, gridRowAdapter));
        mRowsAdapter.add(new ListRow(cardPresenterHeader, cardRowAdapter));


        setAdapter(mRowsAdapter);
    }
}

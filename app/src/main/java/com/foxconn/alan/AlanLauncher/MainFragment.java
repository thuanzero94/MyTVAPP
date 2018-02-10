package com.foxconn.alan.AlanLauncher;

import android.os.Bundle;
import android.support.v17.leanback.app.BrowseFragment;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.OnItemViewSelectedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.util.Log;

/**
 * Created by alan on 02/02/2018.
 */

public class MainFragment extends BrowseFragment {
    private static final String TAG = MainFragment.class.getSimpleName();

    private ArrayObjectAdapter mRowsAdapter;
    private SimpleBackgroundManager simpleBackgroundManager = null;
    private PicassoBackgroundManager picassoBackgroundManager = null;

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        Log.i(TAG, "onActivityCreated");
        super.onActivityCreated(savedInstanceState);
        simpleBackgroundManager = new SimpleBackgroundManager(getActivity());
        picassoBackgroundManager = new PicassoBackgroundManager(getActivity());
        setupElements();
        loadRows();
        setupEventListeners();
    }

    private void setupEventListeners() {
        setOnItemViewSelectedListener(new ItemViewSelectedListener());
        setOnItemViewClickedListener(new ItemViewClickedListener());
    }

    private final class ItemViewSelectedListener implements OnItemViewSelectedListener{
        @Override
        public void onItemSelected(Presenter.ViewHolder itemViewHolder, Object item,
                                   RowPresenter.ViewHolder rowViewHolder, Row row) {
            // each time the item is selected, code inside here will be executed.
            if (item instanceof String) { // GridItemPresenter row
                //simpleBackgroundManager.clearBackground();
                picassoBackgroundManager.updateBackgroundWithDelay("http://heimkehrend.raindrop.jp/kl-hacker/wp-content/uploads/2014/10/RIMG0656.jpg");
            } else if (item instanceof Movie) { // CardPresenter row
                picassoBackgroundManager.updateBackgroundWithDelay(((Movie) item).getCardImageUrl());
            }
        }
    }
    private final class ItemViewClickedListener implements OnItemViewClickedListener {
        @Override
        public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item,
                                  RowPresenter.ViewHolder rowViewHolder, Row row) {
            // each time the item is clicked, code inside here will be executed.

        }
    }

    private void setupElements(){
        //setBadgeDrawable(getResources().getDrawable(R.drawable.lb_action_bg));
        setTitle("Hello Android TV");
        setHeadersState(HEADERS_HIDDEN);
        setHeadersTransitionOnBackEnabled(true);

        setBrandColor(getResources().getColor(R.color.fastlane_BG));
        setSearchAffordanceColor(getResources().getColor(R.color.search_opaque));
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
            if(i%3 == 0) {
                movie.setCardImageUrl("http://heimkehrend.raindrop.jp/kl-hacker/wp-content/uploads/2014/08/DSC02580.jpg");
            } else if (i%3 == 1) {
                movie.setCardImageUrl("http://heimkehrend.raindrop.jp/kl-hacker/wp-content/uploads/2014/08/DSC02630.jpg");
            } else {
                movie.setCardImageUrl("http://heimkehrend.raindrop.jp/kl-hacker/wp-content/uploads/2014/08/DSC02529.jpg");
            }
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

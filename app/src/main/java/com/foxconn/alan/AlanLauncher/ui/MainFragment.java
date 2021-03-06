package com.foxconn.alan.AlanLauncher.ui;

import android.content.Intent;
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

import com.foxconn.alan.AlanLauncher.R;
import com.foxconn.alan.AlanLauncher.data.MovieProvider;
import com.foxconn.alan.AlanLauncher.model.Movie;
import com.foxconn.alan.AlanLauncher.ui.background.PicassoBackgroundManager;
import com.foxconn.alan.AlanLauncher.ui.presenter.CardPresenter;
import com.foxconn.alan.AlanLauncher.ui.presenter.GridItemPresenter;

import java.util.ArrayList;


/**
 * Created by alan on 02/02/2018.
 */

public class MainFragment extends BrowseFragment {
    private static final String TAG = MainFragment.class.getSimpleName();

    private ArrayObjectAdapter mRowsAdapter;
    //private SimpleBackgroundManager simpleBackgroundManager = null;
    private PicassoBackgroundManager picassoBackgroundManager = null;

    private String  selectedCardUrl;
    private boolean clickedMovie = false;

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        Log.i(TAG, "onActivityCreated");
        super.onActivityCreated(savedInstanceState);
        //simpleBackgroundManager = new SimpleBackgroundManager(getActivity());
        picassoBackgroundManager = new PicassoBackgroundManager(getActivity());
        setupElements();
        loadRows();
        setupEventListeners();
    }

    private void setupEventListeners() {
        setOnItemViewSelectedListener(new ItemViewSelectedListener());
        setOnItemViewClickedListener(new ItemViewClickedListener());
    }

    @Override
    public void onResume() {
        super.onResume();
        if(clickedMovie)
            picassoBackgroundManager.updateBackgroundWithDelay(selectedCardUrl);
    }

    private final class ItemViewSelectedListener implements OnItemViewSelectedListener{
        @Override
        public void onItemSelected(Presenter.ViewHolder itemViewHolder, Object item,
                                   RowPresenter.ViewHolder rowViewHolder, Row row) {
            // each time the item is selected, code inside here will be executed.
            if (item instanceof String) { // GridItemPresenter row
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
            if (item instanceof Movie) {
                Movie movie = (Movie) item;
                Log.d(TAG, "Item: " + item.toString());

                selectedCardUrl = ((Movie) item).getCardImageUrl();
                clickedMovie = true;

                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                intent.putExtra(DetailsActivity.MOVIE, movie);

                getActivity().startActivity(intent);
            } else if (item instanceof String){
                if(item == "ErrorFragment"){
                    Intent intent = new Intent(getActivity(), ErrorActivity.class);
                    startActivity(intent);
                }
            }

        }
    }

    private void setupElements(){
        //setBadgeDrawable(getResources().getDrawable(R.drawable.lb_action_bg));
        setTitle("Hello Android TV");
        setHeadersState(HEADERS_DISABLED);
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
        gridRowAdapter.add("ErrorFragment");
        /*CardPresenter*/
        ArrayObjectAdapter cardRowAdapter = new ArrayObjectAdapter(cardPresenter);

        /*ArrayObjectAdapter cardRowAdapter = new ArrayObjectAdapter(cardPresenter);
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
        }*/

        ArrayList<Movie> mItems = MovieProvider.getMovieItems();
        for (Movie movie : mItems) {
            cardRowAdapter.add(movie);
        }

        /*Set Rows*/
        mRowsAdapter.add(new ListRow(gridItemPresenterHeader, gridRowAdapter));
        mRowsAdapter.add(new ListRow(cardPresenterHeader, cardRowAdapter));


        setAdapter(mRowsAdapter);
    }
}

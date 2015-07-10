/*
 * Greplr : A super-aggregator. One app to rule them all.
 *     Copyright (C) 2015  Greplr Team
 *     Where Greplr Team consists of :
 *       1. Arnav Gupta
 *       2. Abhinav Sinha
 *       3. Prempal Singh
 *       4. Raghav Apoorv
 *       5. Shubham Dokania
 *       6. Yogesh Balan
 *
 *     The source code of this program is confidential and proprietary. If you are not part of the
 *     Greplr Team (one of the above 6 named individuals) you should not be viewing this code.
 *
 *     You should immediately close your copy of code, and destory the file. You are not authorised to
 *     be in possession of this code or view or modify it or use it in any capacity.
 */

package com.greplr.subcategories.events;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import com.greplr.App;
import com.greplr.R;
import com.greplr.adapters.ErrorAdapter;
import com.greplr.adapters.LoaderAdapter;
import com.greplr.api.Api;
import com.greplr.models.events.Movies;
import com.greplr.subcategories.UnderSubCategoryFragment;
import com.parse.ParseAnalytics;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by raghav on 21/06/15.
 */
public class EventMovieFragment extends UnderSubCategoryFragment {

    public static final String LOG_TAG = "Greplr/Event/Movie";

    private RecyclerView mRecyclerView;
    private List<Movies> movieList;

    public static EventMovieFragment newInstance() {
        return new EventMovieFragment();
    }

    @Override
    public int getFragmentIcon() {
        return R.drawable.cardicon_events_movies;
    }

    @Override
    public String getPageTitle() {
        return "Movies";
    }

    @Override
    public int getBackgroundResId() {
        return R.drawable.background_events_movies;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(LOG_TAG, "EventMovieFragment onCreateView");
        return inflater.inflate(R.layout.fragment_events_movies, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_movies);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(new RecyclerViewMaterialAdapter(new LoaderAdapter()));
        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);

        Api apiHandler = ((App) getActivity().getApplication()).getApiHandler();
        apiHandler.getEventMovies(
                new Callback<List<Movies>>() {

                    @Override
                    public void success(List<Movies> movies, Response response) {
                        Log.d(LOG_TAG, "success" + response.getUrl() + response.getStatus());
                        movieList = movies;
                        mRecyclerView.setAdapter(new RecyclerViewMaterialAdapter(new MoviesAdapter()));
                        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);
                        Map<String, String> params = new HashMap<>();
                        params.put("success", "true");
                        ParseAnalytics.trackEventInBackground("events/movies/search", params);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d(LOG_TAG, "failure" + error.getUrl() + error.getMessage());
                        mRecyclerView.setAdapter(new RecyclerViewMaterialAdapter(new ErrorAdapter()));
                        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);
                        Map<String, String> params = new HashMap<>();
                        params.put("success", "false");
                        ParseAnalytics.trackEventInBackground("events/movies/search", params);
                    }
                }
        );
    }

    public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            CardView v = (CardView) LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.cardview_movies_list_item, viewGroup, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
            viewHolder.movieTitle.setText(movieList.get(i).getEventTitle());
            viewHolder.rating.setText(movieList.get(i).getRatings() + "/10");
            viewHolder.language.setText(movieList.get(i).getLanguage());
            viewHolder.length.setText(movieList.get(i).getLength());
            if (movieList.get(i).getActors().equals("")) {
                viewHolder.actorsImage.setVisibility(View.GONE);
                viewHolder.actors.setVisibility(View.GONE);
            } else {
                viewHolder.actors.setText(movieList.get(i).getActors());
            }
            Picasso.with(getActivity()).load(movieList.get(i).getBannerURL()).
                    fit().centerCrop().into(viewHolder.banner);
        }

        @Override
        public int getItemCount() {
            return movieList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView movieTitle;
            TextView rating;
            TextView language;
            TextView length;
            TextView actors;
            ImageView banner;
            ImageView actorsImage;

            public ViewHolder(CardView v) {
                super(v);
                movieTitle = (TextView) v.findViewById(R.id.movie_title);
                rating = (TextView) v.findViewById(R.id.rating);
                language = (TextView) v.findViewById(R.id.language);
                length = (TextView) v.findViewById(R.id.length);
                actors = (TextView) v.findViewById(R.id.actors);
                banner = (ImageView) v.findViewById(R.id.banner);
                actorsImage = (ImageView) v.findViewById(R.id.actors_image);
            }
        }
    }

}

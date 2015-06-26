/*
 * Greplr : A super-aggregator. One app to rule them all.
 *     Copyright (C) 2015  Greplr Team
 *     Where Greplr Team consists of :
 *       Arnav Gupta, Abhinv Sinha, Raghav Apoorv,
 *       Shubham Dokania, Yogesh Balan
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
import android.widget.TextView;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import com.greplr.MainActivity;
import com.greplr.R;
import com.greplr.adapters.NumberedAdapter;
import com.greplr.api.Api;
import com.greplr.models.events.Movies;
import com.greplr.subcategories.UnderSubCategoryFragment;
import com.parse.ParseAnalytics;

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
    private RecyclerView.Adapter mAdapter;
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

        View rootView = inflater.inflate(R.layout.fragment_events_movies, container, false);

        Api apiHandler = ((MainActivity) getActivity()).getApiHandler();
        apiHandler.getEventMovies(
                new Callback<List<Movies>>() {

                    @Override
                    public void success(List<Movies> movies, Response response) {
                        Log.d(LOG_TAG, "success" + response.getUrl() + response.getStatus());
                        movieList = movies;
                        updateMovies(movieList);
                        Map<String, String> params = new HashMap<>();
                        params.put("success", "true");
                        ParseAnalytics.trackEventInBackground("travel/events/movies", params);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d(LOG_TAG, "failure" + error.getUrl() + error.getMessage());
                        Map<String, String> params = new HashMap<>();
                        params.put("success", "false");
                        ParseAnalytics.trackEventInBackground("travel/events/movies", params);
                    }
                }
        );

        return rootView;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_movies);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new RecyclerViewMaterialAdapter(new NumberedAdapter(0));
        mRecyclerView.setAdapter(mAdapter);
        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);

    }

    public void updateMovies(List<Movies> movies) {
        mRecyclerView.setAdapter(new RecyclerViewMaterialAdapter(new MoviesAdapter()));
    }

    public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            CardView v = (CardView) LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.movies_cardview_list_item, viewGroup, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
            viewHolder.EventTitle.setText(movieList.get(i).getEventTitle());
            viewHolder.Ratings.setText(movieList.get(i).getRatings() + "/10");
            viewHolder.Language.setText(movieList.get(i).getLanguage());
            viewHolder.Length.setText(movieList.get(i).getLength());
            viewHolder.Actors.setText(movieList.get(i).getActors());

        }

        @Override
        public int getItemCount() {
            return movieList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView EventTitle;
            TextView Ratings;
            TextView Language;
            TextView Length;
            TextView Actors;

            public ViewHolder(CardView v) {
                super(v);
                EventTitle = (TextView) v.findViewById(R.id.event_title);
                Ratings = (TextView) v.findViewById(R.id.rating);
                Language = (TextView) v.findViewById(R.id.language);
                Length = (TextView) v.findViewById(R.id.length);
                Actors = (TextView) v.findViewById(R.id.actors);
            }
        }
    }

}

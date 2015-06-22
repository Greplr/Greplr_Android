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
import com.greplr.models.events.Plays;
import com.greplr.subcategories.UnderSubCategoryFragment;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by raghav on 21/06/15.
 */
public class EventPlayFragment extends UnderSubCategoryFragment {
    
    public static final String LOG_TAG = "Greplr/Event/Play";


    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private List<Plays> playList;

    public static EventPlayFragment newInstance() {
        return new EventPlayFragment();
    }

    @Override
    public int getFragmentIcon() {
        return R.drawable.cardicon_events_plays;
    }

    @Override
    public String getPageTitle() {
        return "Plays";
    }

    @Override
    public int getBackgroundResId() {
        return R.drawable.background_events_play;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(LOG_TAG, "EventPlaysFragment onCreateView");

        View rootView =  inflater.inflate(R.layout.fragment_events_plays, container, false);

        Api apiHandler = ((MainActivity) getActivity()).getApiHandler();
        apiHandler.getEventPlays(
                new Callback<List<Plays>>() {

                    @Override
                    public void success(List<Plays> plays, Response response) {
                        Log.d(LOG_TAG, "success" + response.getUrl() + response.getStatus());
                        playList = plays;
                        updatePlay(playList);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d(LOG_TAG, "failure" + error.getUrl() + error.getMessage());

                    }
                }
        );

        return rootView;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_plays);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new RecyclerViewMaterialAdapter(new NumberedAdapter(0));
        mRecyclerView.setAdapter(mAdapter);
        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);
    }

    public void updatePlay(List<Plays> plays) {
        mRecyclerView.setAdapter(new RecyclerViewMaterialAdapter(new PLayAdapter()));
    }

    public class PLayAdapter extends RecyclerView.Adapter<PLayAdapter.ViewHolder> {


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            CardView v = (CardView) LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.play_cardview_list_item, viewGroup, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
            viewHolder.EventTitle.setText(playList.get(i).getEventTitle());
            viewHolder.Ratings.setText(playList.get(i).getRatings()+"/10");
            viewHolder.Director.setText(playList.get(i).getDirector());
            viewHolder.Length.setText(playList.get(i).getLength());
            viewHolder.Actors.setText(playList.get(i).getActors());

        }

        @Override
        public int getItemCount() {
            return playList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView EventTitle;
            TextView Ratings;
            TextView Director;
            TextView Length;
            TextView Actors;

            public ViewHolder(CardView v) {
                super(v);
                EventTitle = (TextView) v.findViewById(R.id.play_name);
                Ratings = (TextView) v.findViewById(R.id.play_rating);
                Director = (TextView) v.findViewById(R.id.Director_name);
                Length = (TextView) v.findViewById(R.id.play_length);
                Actors = (TextView) v.findViewById(R.id.play_actors);
            }
        }
    }


}

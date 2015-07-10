package com.greplr.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.greplr.R;
import com.greplr.common.ui.GlassCardView;

/**
 * Created by prempal on 10/7/15.
 */
public class ErrorAdapter extends RecyclerView.Adapter<ErrorAdapter.ViewHolder> {

    @Override
    public ErrorAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        GlassCardView v = (GlassCardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_error, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ErrorAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView errorTitle;
        private TextView errorMessage;
        private GlassCardView cardView;

        public ViewHolder(GlassCardView v) {
            super(v);
            cardView = v;
            errorTitle = (TextView) v.findViewById(R.id.error_title);
            errorMessage = (TextView) v.findViewById(R.id.error_message);
        }
    }
}

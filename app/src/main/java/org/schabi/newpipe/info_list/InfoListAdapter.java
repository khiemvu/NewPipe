package org.schabi.newpipe.info_list;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.schabi.newpipe.R;
import org.schabi.newpipe.extractor.InfoItem;

import java.util.List;
import java.util.Vector;

/**
 * Created by Christian Schabesberger on 01.08.16.
 *
 * Copyright (C) Christian Schabesberger 2016 <chris.schabesberger@mailbox.org>
 * InfoListAdapter.java is part of NewPipe.
 *
 * NewPipe is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * NewPipe is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with NewPipe.  If not, see <http://www.gnu.org/licenses/>.
 */

public class InfoListAdapter extends RecyclerView.Adapter<InfoItemHolder> {
    private static final String TAG = InfoListAdapter.class.toString();

    private final InfoItemBuilder infoItemBuilder;
    private final List<InfoItem> infoItemList;

    public InfoListAdapter(Activity a, View rootView) {
        infoItemBuilder = new InfoItemBuilder(a, rootView);
        infoItemList = new Vector<>();
    }

    public void setOnStreamInfoItemSelectedListener
            (InfoItemBuilder.OnInfoItemSelectedListener listener) {
        infoItemBuilder.setOnStreamInfoItemSelectedListener(listener);
    }

    public void setOnChannelInfoItemSelectedListener
            (InfoItemBuilder.OnInfoItemSelectedListener listener) {
        infoItemBuilder.setOnChannelInfoItemSelectedListener(listener);
    }

    public void addInfoItemList(List<InfoItem> videos) {
        if(videos!= null) {
            infoItemList.addAll(videos);
            notifyDataSetChanged();
        }
    }

    public void clearSteamItemList() {
        infoItemList.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return infoItemList.size();
    }

    // don't ask why we have to do that this way... it's android accept it -.-
    @Override
    public int getItemViewType(int position) {
        switch(infoItemList.get(position).infoType()) {
            case STREAM:
                return 0;
            case CHANNEL:
                return 1;
            case PLAYLIST:
                return 2;
            default:
                Log.e(TAG, "Trollolo");
                return -1;
        }
    }

    @Override
    public InfoItemHolder onCreateViewHolder(ViewGroup parent, int type) {
        switch(type) {
            case 0:
                return new StreamInfoItemHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.stream_item, parent, false));
            case 1:
                return new ChannelInfoItemHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.channel_item, parent, false));
            case 2:
                Log.e(TAG, "Playlist is not yet implemented");
                return null;
            default:
                Log.e(TAG, "Trollolo");
                return null;
        }
    }

    @Override
    public void onBindViewHolder(InfoItemHolder holder, int i) {
        infoItemBuilder.buildByHolder(holder, infoItemList.get(i));
    }
}

package com.hippoapp.example.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hippoapp.example.R;
import com.hippoapp.example.model.value.Place;

public class AdapterListPlace extends BaseAdapter {

	private Place[] mPlaces = new Place[0];
	private LayoutInflater mLayoutInflater;

	public AdapterListPlace(Context context) {
		mLayoutInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return mPlaces.length;
	}

	@Override
	public Place getItem(int position) {
		return mPlaces[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = mLayoutInflater.inflate(R.layout.item_list_place,
					null);
			viewHolder = new ViewHolder();
			viewHolder.mTitleText = (TextView) convertView
					.findViewById(R.id.text_title);
			viewHolder.mDescText = (TextView) convertView
					.findViewById(R.id.text_desc);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.mTitleText.setText(mPlaces[position].getName());
		viewHolder.mDescText.setText(mPlaces[position].getVicinity());

		return convertView;
	}

	class ViewHolder {
		TextView mTitleText;
		TextView mDescText;
	}

	public void setPlaces(Place[] mPlaces) {
		this.mPlaces = mPlaces;
		notifyDataSetChanged();
	}
}

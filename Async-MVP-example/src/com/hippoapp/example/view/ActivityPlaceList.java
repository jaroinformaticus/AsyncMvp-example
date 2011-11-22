package com.hippoapp.example.view;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.hippoapp.asyncmvp.core.Presenter;
import com.hippoapp.example.R;
import com.hippoapp.example.layer.LayerProtocol;
import com.hippoapp.example.model.value.Place;
import com.hippoapp.example.utils.Constants;

/**
 * The main purpose of this application is demonstration of AsyncMvp framework
 * facilities.
 *
 * This application according user location gets places by Google Place API.
 *
 * This activity show brief information about places near user. To get more
 * information about place tap on item.
 *
 * @author homo
 *
 */
public class ActivityPlaceList extends ListActivity implements Callback,
		LayerProtocol, Constants, OnItemClickListener {

	private static final String TAG = ActivityPlaceList.class.getSimpleName();

	private AdapterListPlace mAdapterPlace;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mAdapterPlace = new AdapterListPlace(this);
		setListAdapter(mAdapterPlace);

		getListView().setOnItemClickListener(this);

		// firstly we need to subscribe view to receive messages from model or
		// other views.
		Presenter.getInst().subscribe(this);
		// when application starts we need to activate geolocation layer to get
		// current or last best known user location. For more information look
		// at com.hippoapp.asyncmvp.location.GeoLocationClient
		Presenter.getInst().sendModelMessage(V_ENABLE_UPDATE_LOCATION);
	}

	@Override
	protected void onResume() {
		// when activity resume we need to get list of places. Send message to
		// model. This message will catch by LayerRemote.
		Presenter.getInst().sendModelMessage(V_GET_ITEM_LIST);
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		// when application exits we need to turn off location updates
		Presenter.getInst().sendModelMessage(V_DISABLE_UPDATE_LOCATION);
		// activity unsubscribe from receiving messages
		Presenter.getInst().unsubscribe(this);
		// dispose
		Presenter.getInst().dispose();
		super.onDestroy();
	}

	/**
	 * This method catches message from model and other views
	 *
	 * @see android.os.Handler.Callback#handleMessage(android.os.Message)
	 */
	@Override
	public boolean handleMessage(Message msg) {
		switch (msg.what) {
		case P_GET_ITEM_LIST:
			mAdapterPlace.setPlaces((Place[]) msg.obj);
			break;

		default:
			break;
		}
		return false;
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view,
			int position, long id) {
		Intent intentActivityPlaceItem = new Intent(this,
				ActivityPlaceItem.class);
		intentActivityPlaceItem.putExtra(EXTRA_INTENT_ITEM_ID, mAdapterPlace
				.getItem(position).getId());
		intentActivityPlaceItem.putExtra(EXTRA_INTENT_ITEM_REFERENCE,
				mAdapterPlace.getItem(position).getReference());
		startActivity(intentActivityPlaceItem);

	}
}
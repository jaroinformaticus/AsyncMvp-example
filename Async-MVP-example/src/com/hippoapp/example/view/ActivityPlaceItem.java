package com.hippoapp.example.view;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler.Callback;
import android.os.Message;
import android.widget.TextView;

import com.hippoapp.asyncmvp.core.Presenter;
import com.hippoapp.example.R;
import com.hippoapp.example.layer.LayerProtocol;
import com.hippoapp.example.model.value.Place;
import com.hippoapp.example.utils.Constants;

/**
 * This activity show full information about place
 *
 * @author homo
 *
 */
public class ActivityPlaceItem extends Activity implements Callback,
		LayerProtocol, Constants {

	private TextView mTextName;
	private TextView mTextTypes;
	private TextView mTextAddress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.place);

		mTextName = (TextView) findViewById(R.id.text_name);
		mTextTypes = (TextView) findViewById(R.id.text_types);
		mTextAddress = (TextView) findViewById(R.id.text_address);

		// firstly we need to subscribe view to receive messages from model or
		// other views.
		Presenter.getInst().subscribe(this);
	}

	@Override
	protected void onResume() {
		// when activity resume we need to get full information about place.
		// Send message to
		// model. This message will catch by LayerCache.
		String id = getIntent().getStringExtra(EXTRA_INTENT_ITEM_ID);
		String reference = getIntent().getStringExtra(
				EXTRA_INTENT_ITEM_REFERENCE);
		Presenter.getInst().sendModelMessage(V_GET_ITEM, 0, id.hashCode(),
				reference);
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		// activity unsubscribe from receiving messages
		Presenter.getInst().unsubscribe(this);
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
		case P_GET_ITEM:
			Place place = (Place) msg.obj;
			mTextName.setText("Name: " + place.getName());
			StringBuilder sb = new StringBuilder();
			for (String type : place.getTypes()) {
				sb.append(type + ",");
			}
			mTextTypes.setText("Types: " + sb.toString());
			mTextAddress.setText("Address: " + place.getVicinity());
			break;
		default:
			break;
		}
		return false;
	};
}

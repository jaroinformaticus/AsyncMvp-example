package com.hippoapp.example.layer;

import android.content.Context;
import android.location.Location;
import android.os.Message;
import android.util.Log;

import com.hippoapp.asyncmvp.core.Presenter.LayerStatus;
import com.hippoapp.asyncmvp.core.Presenter.ModelLayer;
import com.hippoapp.asyncmvp.core.Presenter.ModelLayerInterface;
import com.hippoapp.asyncmvp.http.AsyncHttpClient;
import com.hippoapp.asyncmvp.http.AsyncHttpRequestParams;
import com.hippoapp.asyncmvp.http.IAsyncHttpResponseHandler;
import com.hippoapp.example.model.ResponseHandler;
import com.hippoapp.example.utils.Constants;

@ModelLayer(nameInt = 1)
public class LayerRemote implements ModelLayerInterface, LayerProtocol,
		Constants {

	private static final String TAG = LayerRemote.class.getSimpleName();

	private static final String BASE_URL = "https://maps.googleapis.com/maps/api/place/";

	private static final String SEARCH_APPENDIX = "search/";
	private static final String DETAIL_APPENDIX = "details/";

	private static final String FORMAT_APPENDIX = "json";

	private static final String PARAM_SENSOR = "sensor";
	private static final String PARAM_LOCATION = "location";
	private static final String PARAM_RADIUS = "radius";
	private static final String PARAM_API_KEY = "key";
	private static final String PARAM_REFERENCE = "reference";

	private Location mCurLocation;
	private AsyncHttpClient mHttpClient;
	private IAsyncHttpResponseHandler mResponseHandler;

	@Override
	public boolean handleMessage(Message msg) {
		AsyncHttpRequestParams params;
		switch (msg.what) {
		case P_UPDATE_LOCATION:
			mCurLocation = (Location) msg.obj;
			break;
		case V_GET_ITEM_LIST:
			params = new AsyncHttpRequestParams(BASE_URL + SEARCH_APPENDIX
					+ FORMAT_APPENDIX);
			params.put(PARAM_SENSOR, Boolean.toString(true));
			// TODO stub for 100% getting result
			params.put(PARAM_LOCATION, "-33.8670522,151.1957362");
			// params.put(PARAM_LOCATION, mCurLocation.getLatitude() + "," +
			// mCurLocation.getLongitude());

			params.put(PARAM_RADIUS, Integer.toString(RADIUS));
			params.put(PARAM_API_KEY, KEY_API_GOOGLE_PLACES);
			mHttpClient.get(params, mResponseHandler, V_GET_ITEM_LIST, 0);
			break;
		case P_UPDATE_ITEM:
			params = new AsyncHttpRequestParams(BASE_URL + DETAIL_APPENDIX
					+ FORMAT_APPENDIX);
			params.put(PARAM_REFERENCE, (String) msg.obj);
			params.put(PARAM_SENSOR, Boolean.toString(true));
			params.put(PARAM_API_KEY, KEY_API_GOOGLE_PLACES);
			Log.e(TAG, "url: " + params.toString());
			mHttpClient.get(params, mResponseHandler, P_UPDATE_ITEM, 0);
			break;
		default:
			break;
		}
		return false;
	}

	@Override
	public void init(Context context) {
		mHttpClient = new AsyncHttpClient();
		mResponseHandler = new ResponseHandler();
	}

	@Override
	public LayerStatus getStatus() {
		return null;
	}

}

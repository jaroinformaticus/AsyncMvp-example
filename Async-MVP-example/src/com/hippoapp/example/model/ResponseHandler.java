package com.hippoapp.example.model;

import android.util.Log;

import com.google.gson.Gson;
import com.hippoapp.asyncmvp.core.Presenter;
import com.hippoapp.asyncmvp.http.IAsyncHttpResponseHandler;
import com.hippoapp.example.layer.LayerProtocol;
import com.hippoapp.example.model.value.JsonObject;
import com.hippoapp.example.utils.Constants;

public class ResponseHandler implements IAsyncHttpResponseHandler,
		LayerProtocol, Constants {

	private static final String TAG = ResponseHandler.class.getSimpleName();

	private static final String STATUS_OK = "OK";
	private static final String STATUS_ZERO_RESULTS = "ZERO_RESULTS";
	private static final String STATUS_OVER_QUERY_LIMIT = "OVER_QUERY_LIMIT";
	private static final String STATUS_REQUEST_DENIED = "REQUEST_DENIED";
	private static final String STATUS_INVALID_REQUEST = "INVALID_REQUEST";

	@Override
	public void onStart(int protocol) {
	}

	@Override
	public void onFinish(int protocol) {
	}

	@Override
	public void onSuccess(int protocol, byte[] content) {
		Gson gson = new Gson();
		JsonObject jsonObject;
		switch (protocol) {
		case V_GET_ITEM_LIST:
			jsonObject = gson.fromJson(new String(content), JsonObject.class);
			if (jsonObject.getStatus().equals(STATUS_OK)) {
				Presenter.getInst().sendViewMessage(P_GET_ITEM_LIST,
						jsonObject.getResults());
			} else {
				// TODO catch failure results
				Log.e(TAG, "status: " + jsonObject.getStatus());
			}
			break;
		case P_UPDATE_ITEM:
			jsonObject = gson.fromJson(new String(content), JsonObject.class);
			if (jsonObject.getStatus().equals(STATUS_OK)) {
				Presenter.getInst().sendModelMessage(P_GET_UPDATE_ITEM, 0,
						jsonObject.getResult().getId().hashCode(),
						jsonObject.getResult());
			} else {
				// TODO catch failure results
				Log.e(TAG, "status: " + jsonObject.getStatus());
			}
			break;
		default:
			break;
		}

	}

	@Override
	public void onFailure(int protocol, Throwable error) {
		Log.e(TAG, "onFailure", error);
	}

}

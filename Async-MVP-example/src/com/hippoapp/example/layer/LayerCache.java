package com.hippoapp.example.layer;

import java.io.IOException;

import android.content.Context;
import android.os.Message;
import android.os.Parcelable;

import com.hippoapp.asyncmvp.cache.AsyncCacheClient;
import com.hippoapp.asyncmvp.core.Presenter;
import com.hippoapp.asyncmvp.core.Presenter.LayerStatus;
import com.hippoapp.asyncmvp.core.Presenter.ModelLayer;
import com.hippoapp.asyncmvp.core.Presenter.ModelLayerInterface;
import com.hippoapp.example.model.value.Place;
import com.hippoapp.example.utils.Constants;

@ModelLayer(nameInt = 2)
public class LayerCache implements ModelLayerInterface, LayerProtocol,
		Constants {

	private AsyncCacheClient mAsyncCacheClient;

	@Override
	public boolean handleMessage(Message msg) {
		switch (msg.what) {
		case V_GET_ITEM:
			Place place = (Place) mAsyncCacheClient.get(V_GET_ITEM,
					Integer.toString(msg.arg2));
			if (place == null) {
				Presenter.getInst().sendModelMessage(P_UPDATE_ITEM, msg.obj);
			} else {
				Presenter.getInst().sendViewMessage(P_GET_ITEM, place);
			}
			break;
		case P_GET_UPDATE_ITEM:
			mAsyncCacheClient.put(V_GET_ITEM, Integer.toString(msg.arg2),
					(Parcelable) msg.obj);
			Presenter.getInst().sendViewMessage(P_GET_ITEM, msg.obj);
			break;
		default:
			break;
		}
		return false;
	}

	@Override
	public void init(Context context) {
		mAsyncCacheClient = AsyncCacheClient.newInstance(context);
		try {
			mAsyncCacheClient.initCacheInstance(V_GET_ITEM, 5, 10, 3, 30,
					AsyncCacheClient.EXTERNAL_CACHE);
		} catch (IOException e) {
			// TODO if there is no mounted card maybe notify user or something
			// else
		}
	}

	@Override
	public LayerStatus getStatus() {
		return null;
	}

}

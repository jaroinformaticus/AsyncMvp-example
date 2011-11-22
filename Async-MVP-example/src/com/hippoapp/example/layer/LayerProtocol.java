package com.hippoapp.example.layer;

import android.os.Message;

import com.hippoapp.asyncmvp.utils.AsyncMvpPresenterProtocol;
import com.hippoapp.example.model.ResponseHandler;
import com.hippoapp.example.model.value.Place;
import com.hippoapp.example.view.ActivityPlaceItem;
import com.hippoapp.example.view.ActivityPlaceList;

/**
 * Container of protocols and their description. Extends
 * {@link AsyncMvpPresenterProtocol}
 *
 * @author homo
 *
 */
public interface LayerProtocol extends AsyncMvpPresenterProtocol {

	/**
	 * empty message to request list of places near user. Sends from
	 * {@link ActivityPlaceList} to {@link LayerRemote}. Response sends by
	 * {@link #P_GET_ITEM_LIST}.
	 */
	int V_GET_ITEM_LIST = 201;

	/**
	 * response of {@link #V_GET_ITEM_LIST} message, which contains an array of
	 * {@link Place} in {@link Message#obj}. Sends from {@link ResponseHandler}
	 * to {@link ActivityPlaceList}
	 */
	int P_GET_ITEM_LIST = 202;

	/**
	 * message to get full information about place. Message must contain
	 * {@link Place#getReference()} as {@link Message#obj} and hashcode of
	 * {@link Place#getId()} as {@link Message#arg2} for put / get key from
	 * cache (see {@link LayerCache}). Sends from {@link ActivityPlaceItem} to
	 * {@link LayerCache}. In {@link LayerCache} we check if place is in cache:
	 * if true then get {@link Place} from cache and send to
	 * {@link ActivityPlaceItem} by {@link #P_GET_ITEM} protocol; if false then
	 * send {@link #P_UPDATE_ITEM} to {@link LayerRemote}.
	 */
	int V_GET_ITEM = 301;
	/**
	 * response of {@link #V_GET_ITEM} message, which contains {@link Place} as
	 * {@link Message#obj}. Sends from {@link LayerCache} to
	 * {@link ActivityPlaceItem}.
	 */
	int P_GET_ITEM = 302;
	/**
	 * if requested {@link Place} by {@link #V_GET_ITEM} protocol not in cache
	 * then get {@link Place} from network. Sends from {@link LayerCache} to
	 * {@link LayerRemote}.
	 */
	int P_UPDATE_ITEM = 303;
	/**
	 * response of {@link #P_UPDATE_ITEM}, which contains {@link Place} as
	 * {@link Message#obj} and hashcode of {@link Place#getId()} as
	 * {@link Message#arg2}. Sends from {@link ResponseHandler} to
	 * {@link LayerCache}.
	 */
	int P_GET_UPDATE_ITEM = 304;
}

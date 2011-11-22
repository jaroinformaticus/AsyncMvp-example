package com.hippoapp.example.utils;

import com.hippoapp.asyncmvp.utils.AsyncMvpConstants;

public interface Constants extends AsyncMvpConstants {

	int RADIUS = 500;

	/**
	 * You can get your API key from: {@link http
	 * ://code.google.com/apis/maps/documentation/places/#Limits}
	 */
	String KEY_API_GOOGLE_PLACES = "AIzaSyDIXjCAkUiv3GBht15IdBgLPtRTjSroOls";

	String EXTRA_INTENT_ITEM_ID = "INTENT_ITEM_ID";
	String EXTRA_INTENT_ITEM_REFERENCE = "INTENT_ITEM_REFERENCE";

	String BUNDLE_ITEM_REFERENCE = "ITEM_REFERENCE";
}

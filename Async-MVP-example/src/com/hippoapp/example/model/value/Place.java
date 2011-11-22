package com.hippoapp.example.model.value;

import android.os.Parcel;
import android.os.Parcelable;

public class Place implements Parcelable {

	private String id;
	private String name;
	private String[] types;
	private String lat;
	private String lng;
	private String reference;
	private String vicinity;

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeString(id);
		parcel.writeString(name);
		parcel.writeInt(types.length);
		parcel.writeStringArray(types);
		parcel.writeString(lat);
		parcel.writeString(lng);
		parcel.writeString(reference);
		parcel.writeString(vicinity);
	}

	public static final Parcelable.Creator<Place> CREATOR = new Parcelable.Creator<Place>() {
		@Override
		public Place createFromParcel(Parcel p) {
			Place place = new Place();
			place.id = p.readString();
			place.name = p.readString();

			int typeLength = p.readInt();
			place.types = new String[typeLength];
			p.readStringArray(place.types);

			place.lat = p.readString();
			place.lng = p.readString();
			place.reference = p.readString();
			place.vicinity = p.readString();
			return place;
		}

		@Override
		public Place[] newArray(int size) {
			return new Place[size];
		}
	};

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String[] getTypes() {
		return types;
	}

	public void setTypes(String[] types) {
		this.types = types;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getVicinity() {
		return vicinity;
	}

	public void setVicinity(String vicinity) {
		this.vicinity = vicinity;
	}

}

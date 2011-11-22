package com.hippoapp.example.model.value;

public class JsonObject {
	private String status;
	private Place[] results;
	private Place result;

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public Place[] getResults() {
		return results;
	}

	public void setResults(Place[] results) {
		this.results = results;
	}

	public void setResult(Place result) {
		this.result = result;
	}

	public Place getResult() {
		return result;
	}
}

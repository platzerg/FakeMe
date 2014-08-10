package com.platzerworld.fakefb.places.jackson;
import java.io.Serializable;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@SuppressWarnings("serial")
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlaceSummaryVO implements Serializable {
	private GeometryVO geometry;
	private String icon;
	private String id;
	private String name;
	private String rating;
	private String reference;
	private String vicinity;
	private ArrayList<String> types;
	
	public ArrayList<String> getTypes() {
		return types;
	}
	public void setTypes(ArrayList<String> types) {
		this.types = types;
	}
	public GeometryVO getGeometry() {
		return geometry;
	}
	public void setGeometry(GeometryVO geometry) {
		this.geometry = geometry;
	}
	public String getId() {
		return id;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
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
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
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

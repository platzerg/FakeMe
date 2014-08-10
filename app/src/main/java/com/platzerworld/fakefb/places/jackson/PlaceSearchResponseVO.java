package com.platzerworld.fakefb.places.jackson;
import java.util.List;

public class PlaceSearchResponseVO {
	private List<String> html_attributions;
	private List<PlaceSummaryVO> results;
    private String next_page_token;
    private String status;

    public String getNext_page_token() {
        return next_page_token;
    }
    public void setNext_page_token(String next_page_token) {
        this.next_page_token = next_page_token;
    }
	public List<String> getHtml_attributions() {
		return html_attributions;
	}
	public void setHtml_attributions(List<String> html_attributions) {
		this.html_attributions = html_attributions;
	}
	public List<PlaceSummaryVO> getResults() {
		return results;
	}
	public void setResults(List<PlaceSummaryVO> results) {
		this.results = results;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}

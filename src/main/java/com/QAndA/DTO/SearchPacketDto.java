package com.QAndA.DTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by George on 26/03/2015.
 */
public class SearchPacketDto {

	private String pageNumber;
	private String maxPages;
	private String resultsPerPage;
	private String query;
	private List<QuestionDTO> results = new ArrayList<QuestionDTO>();


	public String getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(String pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getMaxPages() {
		return maxPages;
	}

	public void setMaxPages(String maxPages) {
		this.maxPages = maxPages;
	}

	public String getResultsPerPage() {
		return resultsPerPage;
	}

	public void setResultsPerPage(String resultsPerPage) {
		this.resultsPerPage = resultsPerPage;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public List<QuestionDTO> getResults() {
		return results;
	}

	public void setResults(List<QuestionDTO> results) {
		this.results = results;
	}
}

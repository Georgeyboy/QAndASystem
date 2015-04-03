package com.QAndA.DTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by George on 26/03/2015.
 */
public class SearchPacketDto {

	private int pageNumber;
	private int maxPages;
	private int resultsPerPage;
	private String query;
	private List<QuestionDTO> results = new ArrayList<QuestionDTO>();


	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getMaxPages() {
		return maxPages;
	}

	public void setMaxPages(int maxPages) {
		this.maxPages = maxPages;
	}

	public int getResultsPerPage() {
		return resultsPerPage;
	}

	public void setResultsPerPage(int resultsPerPage) {
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

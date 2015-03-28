package com.QAndA.Domain;

import com.QAndA.DTO.SearchPacketDto;

import java.util.List;

/**
 * Created by George on 27/03/2015.
 */
public class SearchPacket {

	private int pageNumber;
	private int maxPages;
	private int resultsPerPage;
	private String query;
	private List<Question> results;

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

	public List<Question> getResults() {
		return results;
	}

	public void setResults(List<Question> results) {
		this.results = results;
	}
}

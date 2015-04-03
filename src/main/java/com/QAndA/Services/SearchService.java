package com.QAndA.Services;

import com.QAndA.DAO.QuestionDao;
import com.QAndA.DTO.SearchPacketDto;
import com.QAndA.Domain.Question;
import com.QAndA.Domain.SearchPacket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by George on 27/03/2015.
 */
@Service
public class SearchService {

	@Autowired
	private QuestionDao questionDao;

	@Autowired
	private QuestionService questionService;



	public SearchPacket dtoToSearchPacket(SearchPacketDto dto){
		System.out.println("MaxPages : " + dto.getMaxPages());
		System.out.println("PageNumber : " + dto.getPageNumber());
		System.out.println("ResultsPerPage : " + dto.getResultsPerPage());
		System.out.println("Query : " + dto.getQuery());

		SearchPacket result = new SearchPacket();
		result.setMaxPages(dto.getMaxPages());
		result.setPageNumber(dto.getPageNumber());
		result.setResultsPerPage(dto.getResultsPerPage());
		result.setQuery(dto.getQuery());
//		Results are not set as they will not be needed. QuestionDto's will never be needed to pass back to the controller from the search platform
		return result;
	}

	public SearchPacketDto searchPacketToDto(SearchPacket searchPacket){
		SearchPacketDto dto = new SearchPacketDto();
		dto.setMaxPages(searchPacket.getMaxPages());
		dto.setPageNumber(searchPacket.getPageNumber());
		dto.setResultsPerPage(searchPacket.getResultsPerPage());
		dto.setQuery(searchPacket.getQuery());
		dto.setResults(questionService.questionsToDtos(searchPacket.getResults()));
		return dto;
	}


	/**
	 * For searching questions. Search only searches title
	 * @param query The search term(s)
	 * @return A list of questions found by searching, matching the appropriate page number;
	 */
	public SearchPacket initialSearch(String query){

		SearchPacket results = new SearchPacket();
		results.setPageNumber(1);
		results.setResultsPerPage(10);
		results.setQuery(query);
		results.setResults(new ArrayList<Question>());

		// Get total number of results from query
		long searchCount = questionDao.getSearchCount(query);
		// Calculate max pages
		results.setMaxPages(((int) (searchCount/results.getResultsPerPage())) + 1);

//		results.setResults(questionDao.searchQuestions(searchPacket.getQuery(), ((searchPacket.getPageNumber() - 1) * searchPacket.getResultsPerPage()), (searchPacket.getPageNumber() * searchPacket.getResultsPerPage())));
//		results.setResults(questionDao.searchQuestions(searchPacket.getQuery(), 0, searchPacket.getResultsPerPage()-1));

		return results;
	}

	public List<Question> getSearchResultsPage(SearchPacket searchPacket){
		return questionDao.searchQuestions(searchPacket.getQuery(), ((searchPacket.getPageNumber() - 1) * searchPacket.getResultsPerPage()), (searchPacket.getPageNumber() * searchPacket.getResultsPerPage()));
	}
}

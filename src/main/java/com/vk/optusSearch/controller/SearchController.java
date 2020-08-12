package com.vk.optusSearch.controller;

import com.vk.optusSearch.domain.SearchRequest;
import com.vk.optusSearch.domain.SearchResults;
import com.vk.optusSearch.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class SearchController {

    @Autowired
    private SearchService searchService;

    @PostMapping(value = "/counter-api/search",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public SearchResults search(@RequestBody final SearchRequest searchRequest) {
        log.info("Received request to search for words : {}", String.join(",", searchRequest.getSearchText()));
        SearchResults searchResults = searchService.search(searchRequest.getSearchText());
        log.info("Returning searchResults : {}", searchResults.getCounts().toString());
        return searchResults;
    }

    @GetMapping("/counter-api/top/{topN}")
    public String getTopN(@PathVariable("topN") final String topN) {
        log.info("Received request to fetch top : {} words in paragraph", topN);
        String topNSearchResults = searchService.getTopNSearchResults(Integer.parseInt(topN));
        log.info("Returning response : {}", topNSearchResults);
        return topNSearchResults;
    }
}

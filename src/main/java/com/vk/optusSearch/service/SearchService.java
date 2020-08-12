package com.vk.optusSearch.service;


import com.vk.optusSearch.domain.SearchResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SearchService {

    @Autowired
    private LoadFileAndCacheWordCounts loadFileAndCacheWordCounts;

    public SearchResults search(final List<String> textsToSearch) {
        SearchResults searchResults = new SearchResults();
        for (String text: textsToSearch) {
            Integer countForWord = loadFileAndCacheWordCounts.getCountForWord(text);
            searchResults.getCounts().put(text, countForWord);
        }
        return searchResults;
    }

    public String getTopNSearchResults(int topN) {
        Map<String, Integer> wordCounts = loadFileAndCacheWordCounts.getWordCounts();
        Map<String, Integer> result = wordCounts.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (Map.Entry entry : result.entrySet()) {
            sb.append(entry.getKey() + "|" + entry.getValue() + "\n");
            if (++i >= topN) {
                break;
            }
        }
        return sb.toString();
    }
}

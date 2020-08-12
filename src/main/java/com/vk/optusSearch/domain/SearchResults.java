package com.vk.optusSearch.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
@EqualsAndHashCode
public class SearchResults {

    private Map<String, Integer> counts;

    public SearchResults() {
        counts = new HashMap<>();
    }
}

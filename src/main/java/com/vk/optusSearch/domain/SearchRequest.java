package com.vk.optusSearch.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SearchRequest {

    private List<String> searchText;
}

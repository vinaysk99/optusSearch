package com.vk.optusSearch;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vk.optusSearch.domain.SearchRequest;
import com.vk.optusSearch.domain.SearchResults;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SearchControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void search() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        Resource resourceRequest = new ClassPathResource("searchRequest1.json");
        SearchRequest searchRequest = mapper.readValue(resourceRequest.getFile(), SearchRequest.class);

        String response = mvc.perform(MockMvcRequestBuilders.post("/counter-api/search")
                .header("Authorization", "Basic b3B0dXM6Y2FuZGlkYXRlcw==")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(searchRequest)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        SearchResults searchResults = mapper.readValue(response, SearchResults.class);

        Resource resource = new ClassPathResource("searchResults1.json");
        SearchResults searchResultsExpected = mapper.readValue(resource.getFile(), SearchResults.class);
        assertThat(searchResults, CoreMatchers.is(searchResultsExpected));
    }

    @Test
    public void getTopN() throws Exception {
        String response = mvc.perform(MockMvcRequestBuilders.get("/counter-api/top/5")
                .header("Authorization", "Basic b3B0dXM6Y2FuZGlkYXRlcw=="))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        String responseExpected = "et|23\n" +
                "sed|15\n" +
                "ut|15\n" +
                "in|15\n" +
                "eget|14\n";
        assertThat(response, is(responseExpected));
    }

    @Test
    public void getTopNMustThrow400ClientErrorWhenInvalidHeaderIsPassed() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/counter-api/top/5")
                .header("Authorization", "Basic b3B0dXM6Y2FuZGlkYXRldw=="))
                .andExpect(status().is4xxClientError());
    }
}
package com.cv.sc.pipeline;

import com.cv.sc.exception.HttpClientException;
import com.cv.sc.model.Config;
import com.cv.sc.model.SearchResponse;
import com.cv.sc.pipeline.searcher.GitHubSearcher;
import com.cv.sc.pipeline.searcher.Searcher;
import com.cv.sc.storage.StorageService;
import com.cv.sc.storage.impl.DBStorageServiceImpl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class OrchestratorImpl implements Orchestrator {

    private static OrchestratorImpl instance;
    private final StorageService storageService;

    private final Searcher gitHubSearcher;

    public static OrchestratorImpl getInstance() {
        if (instance == null) {
            instance = new OrchestratorImpl();
        }
        return instance;
    }
    public OrchestratorImpl() {
        storageService = DBStorageServiceImpl.getInstance();
        gitHubSearcher = GitHubSearcher.getInstance();
    }

    @Override
    public SearchResponse search(Config config) throws HttpClientException, IOException {
        SearchResponse searchResponse = gitHubSearcher.search(config);
        searchResponse = saveSearchResult(searchResponse);
        return searchResponse;
    }

    @Override
    public SearchResponse saveSearchResult(SearchResponse searchResponse) throws UnsupportedEncodingException {
        searchResponse.setCreatedOn(System.currentTimeMillis());
        return (SearchResponse) storageService.save(searchResponse);
    }

}

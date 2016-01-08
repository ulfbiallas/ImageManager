package de.ulfbiallas.imagemanager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.ulfbiallas.imagemanager.body.SearchIndexDocument;

@Component
public class SearchServiceImpl implements SearchService {

    final static Logger logger = LoggerFactory.getLogger(SearchServiceImpl.class);

    @Autowired
    private NodeClientService nodeClientService;

    @SuppressWarnings("unchecked")
    @Override
    public List<SearchIndexDocument> searchFor(String query) {
        Client client = nodeClientService.getClient();

        SearchResponse response = client.prepareSearch("imagemanager")
                .setTypes("images")
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.queryStringQuery("*"+query+"*"))
                .execute()
                .actionGet();


        SearchHit[] results = response.getHits().getHits();
        logger.info("searched for: "+query+" - took: " + response.getTookInMillis() + "ms - hits: " + results.length);

        List<SearchIndexDocument> imageResponse = new ArrayList<SearchIndexDocument>();
        for(SearchHit result : results) {
            Map<String, Object> src = result.getSource();
            SearchIndexDocument doc = new SearchIndexDocument();
            doc.setId((String) src.get("id"));
            doc.setOriginalFilename((String) src.get("originalFilename"));
            doc.setTags((List<String>) src.get("tags"));
            imageResponse.add(doc);
        }

        return imageResponse;
    }

}

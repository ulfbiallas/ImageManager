package de.ulfbiallas.imagemanager.service;

import java.util.List;

import de.ulfbiallas.imagemanager.body.SearchIndexDocument;

public interface SearchService {

    List<SearchIndexDocument> searchFor(String query);

}
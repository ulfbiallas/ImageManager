package de.ulfbiallas.imagemanager.service;

import java.util.Set;

public interface AutoTagService {

    Set<String> getTagNamesForTokens(Set<String> tokens);

}

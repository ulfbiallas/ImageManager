package de.ulfbiallas.imagemanager.service;

import org.elasticsearch.client.Client;

public interface NodeClientService {

    Client getClient();

}

package de.ulfbiallas.imagemanager.service;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.stereotype.Component;

@Component
public class NodeClientServiceImpl implements NodeClientService {

    private Client client;

    public NodeClientServiceImpl() {
        Settings settings = Settings.settingsBuilder()
                .put("cluster.name", "elasticsearch").build();

        try {
            client = TransportClient.builder().settings(settings).build().addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Client getClient() {
        return client;
    }

}

package org.example.global;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;

public class MyEsClient {
    static {
        RestClient restClient = RestClient.builder(new HttpHost("127.0.0.1", 9200)).build();
        RestClientTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        client = new ElasticsearchClient(transport);
    }
    private static ElasticsearchClient client;
    public static ElasticsearchClient getClient(){
        return client;
    }
}

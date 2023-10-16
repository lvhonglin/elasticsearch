package org.example.use_java_client.index_single_documents;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.example.model.Product;

import java.io.IOException;

public class Test1 {
    public static void main(String[] args) throws IOException {
        RestClient restClient = RestClient.builder(new HttpHost("127.0.0.1", 9200)).build();
        RestClientTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        ElasticsearchClient client = new ElasticsearchClient(transport);
        Product product = new Product();
        //查询当前这个文档的信息，比如版本号一类的
        IndexResponse index = client.index(i -> {
            IndexRequest.Builder<Object> document = i.index("productjuhe").id("Qf1ZIosBIC5m2drcqSws").document(product);
            return document;
        });
        System.out.println(index);
    }
}

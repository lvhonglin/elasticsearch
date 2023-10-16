package org.example.api_conventions.createApiByJson;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.endpoints.BooleanResponse;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.example.model.Product;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CreateApiByJson {
    public static void main(String[] args) throws IOException {
        RestClient restClient = RestClient.builder(new HttpHost("127.0.0.1", 9200)).build();
        RestClientTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        ElasticsearchClient client = new ElasticsearchClient(transport);
        //从resource下获取some-index.json文件
        InputStream resourceAsStream = CreateApiByJson.class.getResourceAsStream("/some-index.json");
        SearchRequest.Builder query = new SearchRequest.Builder().index("productjuhe").withJson(resourceAsStream);
        SearchResponse<Product> search = client.search(query.build(), Product.class);
        List<Hit<Product>> hits = search.hits().hits();
        for(Hit<Product> hit:hits){
            Product source = hit.source();
            System.out.println(source);
        }
    }
}

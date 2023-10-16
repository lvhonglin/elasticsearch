package org.example.use_java_client.read_doc_by_id;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.GetRequest;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import co.elastic.clients.util.ObjectBuilder;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.example.model.Product;

import java.io.IOException;
import java.util.function.Function;

public class Test1 {
    public static void main(String[] args) throws IOException {
        RestClient restClient = RestClient.builder(new HttpHost("127.0.0.1", 9200)).build();
        RestClientTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        ElasticsearchClient client = new ElasticsearchClient(transport);
        //通过id查询doc
        GetResponse<Product> productGetResponse = client.get(g -> {
            return g.index("productjuhe").id("Q_1ZIosBIC5m2drczywL");
        }, Product.class);
//        GetResponse<Product> productGetResponse = client.get(new Function<GetRequest.Builder, ObjectBuilder<GetRequest>>() {
//            @Override
//            public ObjectBuilder<GetRequest> apply(GetRequest.Builder builder) {
//                return builder.index("productjuhe").id("Q_1ZIosBIC5m2drczywL");
//            }
//        }, Product.class);
        System.out.println(productGetResponse.source());
    }
}

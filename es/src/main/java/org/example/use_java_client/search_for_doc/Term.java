package org.example.use_java_client.search_for_doc;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.example.model.Product;

import java.io.IOException;
import java.util.List;

public class Term {
    public static void main(String[] args) throws IOException {
        RestClient restClient = RestClient.builder(new HttpHost("127.0.0.1", 9200)).build();
        RestClientTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        ElasticsearchClient client = new ElasticsearchClient(transport);
        //查询分词后的结果，比如小米手机会被分词为小、米、手、机，如果query小米手机是搜不到的，所以只能搜索小
        SearchResponse<Product> search = client.search(s -> s
                        .index("productjuhe")
                        .query(q -> q.term(t->
                            t.field("id").value("小")
                                )
                        ),
                Product.class
        );
        System.out.println(search.hits().total());
        List<Hit<Product>> hits = search.hits().hits();
        for(Hit<Product> hit:hits){
            System.out.println(hit.source());
        }
    }
}

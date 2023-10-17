package org.example.use_java_client.search_for_doc.NestedSearch;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.JsonData;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.example.model.Product;

import java.io.IOException;
import java.util.List;

//嵌套查询
public class Nested {
    public static void main(String[] args) throws IOException {
        RestClient restClient = RestClient.builder(new HttpHost("127.0.0.1", 9200)).build();
        RestClientTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        ElasticsearchClient client = new ElasticsearchClient(transport);
        SearchResponse<Product> search = client.search(s -> s
                        .index("productjuhe")
                        .query(t->t.bool(m->m.must(a->a.match(a1->a1.field("id").query("小米")))
                                .must(b->b.matchPhrase(b1->b1.field("type").query("手机"))))),
                Product.class
        );
        System.out.println(search.hits().total());
        List<Hit<Product>> hits = search.hits().hits();
        for(Hit<Product> hit:hits){
            System.out.println(hit.source());
        }
    }
}

package org.example.use_java_client.search_for_doc;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import co.elastic.clients.util.ObjectBuilder;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.example.model.Product;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;

public class Match {
    public static void main(String[] args) throws IOException {
        RestClient restClient = RestClient.builder(new HttpHost("127.0.0.1", 9200)).build();
        RestClientTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        ElasticsearchClient client = new ElasticsearchClient(transport);
        SearchResponse<Product> search = client.search(s -> s
                        .index("productjuhe")
                        .query(q -> q
                                .match(t -> t
                                        .field("id")
                                        .query("小米手机10")
                                )
                        ),
                Product.class
        );
//        SearchResponse<Product> search = client.search(new Function<SearchRequest.Builder, ObjectBuilder<SearchRequest>>() {
//            @Override
//            public ObjectBuilder<SearchRequest> apply(SearchRequest.Builder builder) {
//                return builder.index("productjuhe").query(new Function<Query.Builder, ObjectBuilder<Query>>() {
//                    @Override
//                    public ObjectBuilder<Query> apply(Query.Builder builder) {
//                        return builder.match(new Function<MatchQuery.Builder, ObjectBuilder<MatchQuery>>() {
//                            @Override
//                            public ObjectBuilder<MatchQuery> apply(MatchQuery.Builder builder) {
//                                return builder.field("type").query("手机");
//                            }
//                        });
//                    }
//                });
//            }
//        }, Product.class);
        System.out.println(search.hits().total());
        List<Hit<Product>> hits = search.hits().hits();
        for(Hit<Product> hit:hits){
            System.out.println(hit.source());
        }
    }
}

package org.example.client.query;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.FieldValue;
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

public class TermQuery {
    //使用lambda表达式
    //最开始使用的时候如果es的yaml中没有配置xpack.security.enabled: false会报错
    public static void main(String[] args) throws IOException {
        RestClient restClient = RestClient.builder(new HttpHost("127.0.0.1", 9200)).build();
        RestClientTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        ElasticsearchClient client = new ElasticsearchClient(transport);
        SearchRequest.Builder query = new SearchRequest.Builder().index("productjuhe").query(t -> {
            return t.term(p -> {
                return p.field("type").value(r -> {
                    return r.stringValue("电");
                });
            });
        });

        SearchResponse<Product> search = client.search(query.build(), Product.class);
        List<Hit<Product>> hits = search.hits().hits();
        for(Hit<Product> hit:hits){
            Product source = hit.source();
            System.out.println(source);
        }
    }
    //如果不用lambda表达式特别繁琐
//    public static void main(String[] args) throws IOException {
//        RestClient restClient = RestClient.builder(new HttpHost("127.0.0.1", 9200)).build();
//        RestClientTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
//        ElasticsearchClient client = new ElasticsearchClient(transport);
//        SearchRequest.Builder query = new SearchRequest.Builder().index("productjuhe").query(new Function<Query.Builder, ObjectBuilder<Query>>() {
//            public ObjectBuilder<Query> apply(Query.Builder builder) {
//                ObjectBuilder<Query> term = builder.term(new Function<co.elastic.clients.elasticsearch._types.query_dsl.TermQuery.Builder, ObjectBuilder<co.elastic.clients.elasticsearch._types.query_dsl.TermQuery>>() {
//                    @Override
//                    public ObjectBuilder<co.elastic.clients.elasticsearch._types.query_dsl.TermQuery> apply(co.elastic.clients.elasticsearch._types.query_dsl.TermQuery.Builder builder) {
//                        co.elastic.clients.elasticsearch._types.query_dsl.TermQuery.Builder value = builder.field("type").value(new Function<FieldValue.Builder, ObjectBuilder<FieldValue>>() {
//                            @Override
//                            public ObjectBuilder<FieldValue> apply(FieldValue.Builder builder) {
//                                ObjectBuilder<FieldValue> value = builder.stringValue("电");
//                                return value;
//                            }
//                        });
//                        return value;
//                    }
//                });
//                return term;
//            }
//        });
//        SearchResponse<Product> search = client.search(query.build(), Product.class);
//        List<Hit<Product>> hits = search.hits().hits();
//        for(Hit<Product> hit:hits){
//            Product source = hit.source();
//            System.out.println(source);
//        }
//    }
}

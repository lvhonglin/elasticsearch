package org.example.use_java_client.search_for_doc;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.FieldSort;
import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.JsonData;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import co.elastic.clients.util.ObjectBuilder;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.example.model.Product;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;

public class Sort {
    public static void main(String[] args) throws IOException {
        RestClient restClient = RestClient.builder(new HttpHost("127.0.0.1", 9200)).build();
        RestClientTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        ElasticsearchClient client = new ElasticsearchClient(transport);
        //sort只能排序int类型，如果sort其他类型，比如int或者create_time会报错
        SearchResponse<Product> search = client.search(s -> s
                        .index("productjuhe")
//                        .query(t->t.range(a->a.field("price").gt(JsonData.of("4000"))))
                        .sort(new Function<SortOptions.Builder, ObjectBuilder<SortOptions>>() {
                            @Override
                            public ObjectBuilder<SortOptions> apply(SortOptions.Builder builder) {
                                return builder.field(FieldSort.of(c->c.field("price").order(SortOrder.Asc)));
                            }
                        }),
                Product.class
        );
        System.out.println(search.hits().total());
        List<Hit<Product>> hits = search.hits().hits();
        for(Hit<Product> hit:hits){
            System.out.println(hit.source());
        }
    }
}

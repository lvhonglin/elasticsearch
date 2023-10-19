package org.example.use_java_client.appregations.metrics_agg;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregate;
import co.elastic.clients.elasticsearch._types.aggregations.StatsAggregate;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;

import java.io.IOException;
import java.util.Map;

public class Min {
    public static void main(String[] args) throws IOException {
        RestClient restClient = RestClient.builder(new HttpHost("127.0.0.1", 9200)).build();
        RestClientTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        ElasticsearchClient client = new ElasticsearchClient(transport);
        SearchResponse<Void> response = client.search(a -> a
                .size(0)
                .index("productjuhe")
                .query(d -> d.
                        constantScore(e -> e.
                                filter(f -> f.
                                        match(t -> t.
                                                field("type").query("手")))))
                .aggregations("max-price",b->b.stats(c->c.field("price"))), void.class);
        Map<String, Aggregate> aggregations = response.aggregations();
        Aggregate aggregate = aggregations.get("tags-count");
        StatsAggregate stats = aggregate.stats();
        System.out.println(stats.max());
        System.out.println(stats.min());
        System.out.println(stats.avg());

    }
}

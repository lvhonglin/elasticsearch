package org.example.use_java_client.appregations.bucket_agg;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.aggregations.*;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import co.elastic.clients.util.ObjectBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Terms {
    public static void main(String[] args) throws IOException {
        RestClient restClient = RestClient.builder(new HttpHost("127.0.0.1", 9200)).build();
        RestClientTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        ElasticsearchClient client = new ElasticsearchClient(transport);
        SearchResponse<Void> response = client.search(a -> a.
                aggregations("tags-count", b -> b.
                        terms(c -> c.
                                field("tags.keyword")))
                .size(0)
                .index("productjuhe")
                .query(d -> d.
                        constantScore(e -> e.
                                filter(f -> f.
                                        match(t -> t.
                                                field("type").query("手"))))), void.class);
        Map<String, Aggregate> aggregations = response.aggregations();
        Aggregate aggregate = aggregations.get("tags-count");
        System.out.println(aggregate);
        StringTermsAggregate stringTermsAggregate = (StringTermsAggregate) aggregate._get();
        Buckets<StringTermsBucket> buckets = stringTermsAggregate.buckets();
        List<StringTermsBucket> array = buckets.array();
        for (StringTermsBucket stringTermsBucket : array) {
            System.out.println(stringTermsBucket.key()._get() + ":" + stringTermsBucket.docCount());
        }

    }
}

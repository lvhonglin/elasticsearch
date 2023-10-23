package org.example.use_java_client.appregations.nested;

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

public class NestAggregation {
    public static void main(String[] args) throws IOException {
        RestClient restClient = RestClient.builder(new HttpHost("127.0.0.1", 9200)).build();
        RestClientTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        ElasticsearchClient client = new ElasticsearchClient(transport);
        SearchResponse<Void> response = client.search(a -> a.
                aggregations("tags-agg", b -> b.terms(c -> c.field("tags.keyword")).
                        aggregations("max-price", o -> o.max(p -> p.field("price"))).
                        aggregations("min-price", q -> q.min(t -> t.field("price"))).
                        aggregations("lv-agg",l->l.terms(k->k.field("lv.keyword"))))
                .size(0), void.class);
        Map<String, Aggregate> aggregations = response.aggregations();
        Aggregate aggregate = aggregations.get("tags-agg");
        System.out.println(aggregate);
        StringTermsAggregate stringTermsAggregate = (StringTermsAggregate) aggregate._get();
        Buckets<StringTermsBucket> buckets = stringTermsAggregate.buckets();
        List<StringTermsBucket> array = buckets.array();
        array.stream().forEach(j -> {
            System.out.println(j.key().stringValue() + ":" + j.docCount()+":"+j.aggregations().get("max-price").max().value());
            Aggregate lvAggregate = j.aggregations().get("lv-agg");
            StringTermsAggregate o = (StringTermsAggregate)lvAggregate._get();
            o.buckets().array().stream().forEach(r-> System.out.println(r.key().stringValue()+":"+r.docCount()));
            System.out.println("===============");
        });
    }
}

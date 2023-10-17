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

public class MatchPharse {
    public static void main(String[] args) throws IOException {
        RestClient restClient = RestClient.builder(new HttpHost("127.0.0.1", 9200)).build();
        RestClientTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        ElasticsearchClient client = new ElasticsearchClient(transport);
        //如果索引数据和Model对象不匹配会报json异常，matchphrase表示小、米、手、机，这个字的顺序不能改变，并且中间不能插入其他
        //字或者字符，但是可以插入空格，标点符号
        SearchResponse<Product> search = client.search(s -> s
                        .index("productjuhe")
                        .query(q -> q.matchPhrase(t->
                            t.field("create_time").query("2020-10-01 08:00:00")
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

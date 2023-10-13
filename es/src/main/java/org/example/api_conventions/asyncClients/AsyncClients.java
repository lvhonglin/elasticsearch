package org.example.api_conventions.asyncClients;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.endpoints.BooleanResponse;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class AsyncClients {
    public static void main(String[] args) throws IOException {
        RestClient restClient = RestClient.builder(new HttpHost("127.0.0.1", 9200)).build();
        RestClientTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        ElasticsearchAsyncClient client = new ElasticsearchAsyncClient(transport);
        CompletableFuture<BooleanResponse> exists = client.exists(b -> {
            return b.index("productjuhe").id("QP1YIosBIC5m2drc4Sz0");
        });
        exists.whenComplete((booleanResponse,throwable)->{
            if(throwable!=null){
                System.out.println("error");
            }else {
                if(booleanResponse.value()) {
                    System.out.println("Product exist");
                }else {
                    System.out.println("Product not exist");
                }
            }
        });
        //不用lambda改写
//        exists.whenComplete(new BiConsumer<BooleanResponse, Throwable>() {
//            @Override
//            public void accept(BooleanResponse booleanResponse, Throwable throwable) {
//                if(throwable!=null){
//                    System.out.println("error");
//                }else {
//                    if(booleanResponse.value()) {
//                        System.out.println("Product exist");
//                    }else {
//                        System.out.println("Product not exist");
//                    }
//                }
//            }
//        });
    }
}

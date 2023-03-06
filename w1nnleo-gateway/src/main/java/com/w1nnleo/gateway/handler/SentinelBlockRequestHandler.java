//
//package com.w1nnleo.gateway.handler;
//
//import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.InvalidMediaTypeException;
//import org.springframework.http.MediaType;
//import org.springframework.web.reactive.function.server.ServerResponse;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//import java.net.URI;
//import java.util.List;
//
//import static org.springframework.web.reactive.function.BodyInserters.fromValue;
//
///**
// * @Author: w1nnleo
// * @date: 2023/2/20
// * @Description: sentinel 自定义异常
// */
//public class SentinelBlockRequestHandler implements BlockRequestHandler {
//
//    @Override
//    public Mono<ServerResponse> handleRequest(ServerWebExchange exchange, Throwable t) {
//        // 判断是否是html访问，如果是则转发url
//        if (acceptsHtml(exchange)) {
//            return htmlErrorResponse();
//        }
//        // 如果是接口访问，则返回提示
//        return ServerResponse.status(HttpStatus.TOO_MANY_REQUESTS)
//            .contentType(MediaType.APPLICATION_JSON_UTF8)
//            .body(fromValue(new ResponseData(HttpStatus.TOO_MANY_REQUESTS.value(),"请求太多了")));
//    }
//
//    private Mono<ServerResponse> htmlErrorResponse() {
//        String url="http://www.baidu.com";
//        URI uri =URI.create(url);
//        return ServerResponse.temporaryRedirect(uri).build();
//    }
//
//    private boolean acceptsHtml(ServerWebExchange exchange) {
//        try {
//            List<MediaType> acceptedMediaTypes = exchange.getRequest().getHeaders().getAccept();
//            acceptedMediaTypes.remove(MediaType.ALL);
//            MediaType.sortBySpecificityAndQuality(acceptedMediaTypes);
//            return acceptedMediaTypes.stream()
//                .anyMatch(MediaType.TEXT_HTML::isCompatibleWith);
//        } catch (InvalidMediaTypeException ex) {
//            return false;
//        }
//    }
//
//    /**
//     * 定义返回的实体类，字段根据需要添加
//     */
//    @Data
//    @AllArgsConstructor
//    @NoArgsConstructor
//    static class ResponseData {
//        private int code;
//        private String msg;
//    }
//}

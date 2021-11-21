package siru.springwebflux.reactor;

import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SimpleReactorTest {

    @Test
    public void 간단한_리액터생성_구독() throws Exception {
        List<Integer> list = new ArrayList<>();

        Flux.just(1,2,3,4)
                .log()
                .subscribe(data -> {
                    System.out.println("data add: " + data);
                    list.add(data);
                });

        System.out.println("=== 검증 ===");
        assertThat(list).asList().containsExactly(1, 2, 3, 4);
    }

    @Test
    public void test_iterable_flux() throws Exception {
        List<String> singerNameList = new ArrayList<>();

       Flux.fromIterable(Arrays.asList("아이유", "이무진"))
               .subscribe(singerNameList::add);

       assertThat(singerNameList).asList().isEqualTo(Arrays.asList("아이유", "이무진"));
    }

    @Test
    public void test_stream_flux() throws Exception {
        List<String> singerNameList = new ArrayList<>();

        Flux.fromStream(Stream.of("아이유", "이무진"))
                .subscribe(singerNameList::add);

        assertThat(singerNameList).asList().isEqualTo(Arrays.asList("아이유", "이무진"));
    }

    @Test
    public void flux_lazy_test() throws Exception {

        Flux<Long> flux = Flux.range(1,9)
                .flatMap(n -> {
                    try{
                        Thread.sleep(1000);
                    } catch (InterruptedException ignored) {}
                    System.out.println("호옹");
                    return Mono.just(3L * n);
                });

        System.out.println("구독전...");
        try{
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {}
        flux.subscribe(value -> {
                    System.out.println("value: " + value);
                },
                null,
                () -> {
                    System.out.println("데이터 전송 완료..");
                }
        );

        System.out.println("전체완료..");
    }

    @Test
    public void map_flatmap_test() throws Exception {
        Flux<Integer> flux = Flux.just(1,2,3)
                .map(v -> {
                    System.out.println("map: " + v);
                    return v + 1;
                });

        flux.flatMap(v -> {
                    System.out.println("flat map: " + v);
                    return Mono.just(v);
                }).subscribe(v -> {
                    System.out.println("subscribe: " + v);
                });
    }

    @Test
    public void webClient_test() throws Exception {
        WebClient webClient = WebClient.create();
        Mono<String> resultMono = webClient.get()
                .uri("http://localhost:8080/test")
                .retrieve()
                .bodyToMono(String.class);

        String result = resultMono.block();

        assertThat(result).isEqualTo("test is tasty");
    }

    @Test
    public void webClient_toFlux_test() throws Exception {
        WebClient webClient = WebClient.create();
        webClient.get()
                .uri("http://localhost:8080/users")
                .retrieve()
                .bodyToMono(List.class)
                .log()
                        .flatMapMany(Flux::fromIterable)
                                .flatMap(userIndex ->
                                    webClient.get()
                                            .uri("http://localhost:8080/users/{userIndex}", userIndex)
                                            .retrieve()
                                            .bodyToMono(String.class)
                                )
                                        .subscribe(System.out::println);
        Thread.sleep(10000);
        System.out.println("데이터 전송완료");
    }
}

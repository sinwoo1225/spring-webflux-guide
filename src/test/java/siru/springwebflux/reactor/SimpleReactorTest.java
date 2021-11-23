package siru.springwebflux.reactor;

import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.util.function.Tuple2;

import java.time.Duration;
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
    public void stepVerifier_test() throws Exception {
        List<Integer> list = Arrays.asList(1,2,3,4);

        Flux<Integer> flux = Flux.fromIterable(list)
                .log();

        StepVerifier.create(flux)
                .expectNext(1,2,3,4)
                .verifyComplete();

        StepVerifier.create(flux)
                .expectNext(1)
                .expectNext(2)
                .expectNext(3)
                .expectNext(4)
                .verifyComplete();
    }

    @Test
    public void interval_test() throws Exception {
        Flux<Long> intervalFlux =
                Flux.interval(Duration.ofSeconds(1))
                        .log()
                        .take(10); // 1초 간격으로 1씩 증가하면서 정수데이터 방출 (10개 받을때까지)

        StepVerifier.create(intervalFlux)
                .expectNext(0L, 1L ,2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L)
                .verifyComplete();
    }

    @Test
    public void mergeFluxes_test() throws Exception {
        // given
        Flux<String> characterFlux =
                Flux.just("SpongeBob", "Patrick", "Squidward")
                        .delayElements(Duration.ofMillis(500));
        Flux<String> foodFlux =
                Flux.just("Apples", "Lollipops")
                        .delaySubscription(Duration.ofMillis(250))
                        .delayElements(Duration.ofMillis(500));

        //when
        Flux<String> mergedFlux = characterFlux.mergeWith(foodFlux).log();

        // then
        StepVerifier.create(mergedFlux)
                .expectNext("SpongeBob", "Apples", "Patrick", "Lollipops", "Squidward")
                .verifyComplete();
    }

    @Test
    public void zip_test() throws Exception {
        // given
        Flux<String> characterFlux =
                Flux.just("SpongeBob", "Patrick", "Squidward");
        Flux<String> foodFlux =
                Flux.just("Apples", "Lollipops", "Lasagna");

        //when
        Flux<Tuple2<String, String>> zippedFlux = Flux.zip(characterFlux, foodFlux).log();

        // then
        StepVerifier.create(zippedFlux)
                .expectNextMatches(t ->
                    t.getT1().equals("SpongeBob") &&
                    t.getT2().equals("Apples"))
                .expectNextMatches(t ->
                        t.getT1().equals("Patrick") &&
                        t.getT2().equals("Lollipops"))
                .expectNextMatches(t ->
                        t.getT1().equals("Squidward") &&
                        t.getT2().equals("Lasagna"))
                .verifyComplete();
    }

    @Test
    public void flux_lazy_test() throws Exception {

        Flux<Long> flux = Flux.range(1,9)
                .flatMap(n -> {
                    try{
                        Thread.sleep(1000);
                    } catch (InterruptedException ignored) {}
                    System.out.println("호옹") ;
                    return Mono.just(3L * n);
                });

        System.out.println("구독전...");

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

    /**
     * webclient 테스트
     * - 테스트전 반드시 서버를 띄워놓을것
     * - 실제 테스트 코드 작성시에는 WebTestClient 이용
     */
    @Test
    public void webClient_test() throws Exception {
        WebClient webClient = WebClient.create();
        List<String> result = webClient.get()
                .uri("http://localhost:8080/users")
                .retrieve()
                .bodyToFlux(Integer.class)
                .flatMap(ui -> webClient
                        .get()
                        .uri("http://localhost:8080/users/{userIndex}", ui)
                        .retrieve()
                        .bodyToMono(String.class))
                .log()
                .collectList()
                .block();
        System.out.println(result);
        assertThat(result.size()).isEqualTo(3);
    }
}

package siru.springwebflux.reactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SimpleReactorTest {

    @Test
    public void 간단한_리액터생성_구독() throws Exception {
        List<Integer> list = new ArrayList<>();

        Flux.just(1,2,3,4)
                .log()
                .subscribe(list::add);

        assertThat(list).asList().containsExactly(1, 2, 3, 4);
    }

}

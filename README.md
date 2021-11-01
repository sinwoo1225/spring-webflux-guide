# Spring Webflux Guide
spring webflux

---

# Spring Webflux 란? 

---
# Reactor 간단히 살펴보기

`Reactor`는 Reactive Stream를 구현한 구현체이다. 이는 복잡한 비동기식 요청을 효과적으로 처리하기 위한 비동기 프로그래밍을 위한 API이다. 

```java
@Test
public void 간단한_리액터생성_구독() throws Exception {
    List<Integer> list = new ArrayList<>();

    Flux.just(1,2,3,4)
            .log()
            .subscribe(list::add);
    
    assertThat(list).asList().containsExactly(1, 2, 3, 4);
}
```

---
# Annotation 기반으로 Controller 작성해보기

---
# R2DBC
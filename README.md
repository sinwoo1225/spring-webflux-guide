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

---
# Jooq
jpa에서 동적쿼리를 작성하기위해 querydsl을 사용한다. sql을 코드레벨에서 작성하기에는
r2dbc에서는 jpa를 사용할 수 없어 querydsl을 사용할 수 있는지 알아봐야한다. 일단 현재 
내가 이해하는바로는 querydsl은 jpql을 코드레벨의 쿼리로 변환할 수 있는 라이브러리인데
이를 r2dbc에서도 쓸 수 있는지 추가적으로 jooq라는 라이브러리에 대해서 조사후 정리해야겠다.
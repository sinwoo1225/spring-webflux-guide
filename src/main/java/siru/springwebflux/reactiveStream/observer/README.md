# Reactive Stream

## 목차
- Reactive Streams
- Observer 패턴

---

# Reactive Streams
Reactive Streams 란 non-blocking, async 와 역압(back-pressure)를 지원하는 스트림 처리에 대한 패러다임, 표준입니다.

## Reactive 선언
[리액티브 선언](https://www.reactivemanifesto.org/)
현대의 어플리케이션은 분산처리되고 점점 더 복잡해지고 있으며 이에 따라 시스템은 더욱더 강력해지고 탄력적이며 유연할 필요가 있다.
이러한 변화에 대응하기위해 reactive 라는 패러다임이 등장했고 이에 따하 reactive 개념에 대한 선언이 등장했다. 리액티브에서는 4가지
특성을 가지고 있다.
- Responsive(응답성) : 시스템은 가능한 즉각 응답해야한다는 특성이다. 시스템의 응답은 빠르고 안정적으로 일관되게 처리되어야한다.
- Resilient(복원력) : 시스템은 장애가 발생해도 응답성을 유지해야한다. 하나의 시스템이 장애가 발생해도 다른 시스템에 영향을 주어서는 안된다.
- Elastic(탄력성) : 시스템은 다양한 요청에 탄력적으로 대응할 수 있어야하는 특성이다. 요청이 늘거나 줄어들면 시스템은 이에 맞춰 리소스를 늘리고 줄일 수 있어야 한다.
- Message-Driven(메시지 기반 통신) : 서로다른 시스템 사이에서는 비동기 메시지 전달을 통해 시스템간의 경계를 느슨하게 하고 위치 투명성을 통해 확장성을 향상시킨다.

--- 

# Observer 패턴

## Iterable과 Observer, pull-push 개념
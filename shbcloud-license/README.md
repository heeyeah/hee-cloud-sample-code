
# SHB license App



## Getting Started
비즈니스 로직이 있는 게 아니라, 테스트 역할을 위한 프로젝트이다.



### Test Role
- Path Variable이 잘 동작하는가
- Path Variable과 Query Parameter의 처리는 어떻게 하는가
- 다른 App과 통신할 때 어떻게 하는가 (WebClient)
- R2DBC(PostgreSQL)와 Reactive WebClient를 사용했을 때, 데이터를 어떻게 합쳐서 return하는가
  - 비동기 처리 방식을 기본으로 가져가기 때문에, 이해가 잘 안될 수 있음
  - DB Select한 값은 Flux, WebClient로 가져온 값은 Mono인데 어떻게 합쳐..? (에 관한 내용)
  - 그럼 Flux랑 Flux는 어떻게 합쳐..? (zipWith 사용해야 함)
- [Reactor에 언제/어떤 Operator를 써야해?](https://luvstudy.tistory.com/100)
- API 명세 tool인 Swagger는 어떻게 쓸 수 있는가
- API는 어떻게 구성해서 사용할 수 있는가 (Controller, Handler+Router Function)
- R2DBC 설정은 어떻게 하는가 (여리선임님 도움:D)
  - [JPA와 같은 ReactiveCrudRepository는 어떻게 사용하는가](https://docs.spring.io/spring-data/r2dbc/docs/1.1.0.RC1/reference/html/#preface)
- entity 정의와 lombok 사용은 어떻게 하는가
- central maven repository가 아닌 milestone, snapshot 버전의 라이브러리를 사용하기 위해서 pom.xml에 어떤 설정을 해야하는가
- 패키지는 어떻게 나눠야 하는가
- Facade Pattern은 어떻게 사용하는가 (사용안함, Interface만 생성해놓았다.)


### Configuration
```yaml
server:
    port: 8000
```


### Running the tests
[Swagger UI](http://localhost:8000/swagger-ui.html)

### Todo list
- Test는 어떻게 하는가 (**TODO : WebFluxTestClient**)
- Exception 처리는 어떻게 해야 하는가 (**TODO : onError 작성?**)
- postgre config를 yaml파일로 빼야함
- 로깅 컨테이너 만들면 연결해야 함
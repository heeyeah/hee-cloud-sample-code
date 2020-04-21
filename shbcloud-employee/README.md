
# SHB employee App



## Getting Started
비즈니스 로직이 있는 게 아니라, 테스트 역할을 위한 프로젝트이다.

테스트해봐야 할 기능들은 [shb-cloud-license project]()를 참고한다.


### Test Role
- shb-cloud-license app에서 shb-cloud-employee app의 API를 호출
- 해당 프로젝트는 호출되는 Application 정도이다!


### Configuration
```yaml
server:
    port: 9000
```

### Todo list
- postgre config를 yaml파일로 빼야함
- 로깅 컨테이너 만들면 연결해야 함
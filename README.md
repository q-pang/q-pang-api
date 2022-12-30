# q-pang api
> 이커머스를 설계 및 구축하며 대규모 시스템에 필요한 개념과 기술을 적용해보기 위한 프로젝트입니다.  
> DDD, Hexagonal Architecture 개념과 Spring Cloud, Apache Kafka 기술을 적용하여 MSA 서버를 구성하였습니다.

## 프로젝트 진행 과정
- [#1 개요](https://velog.io/@ddkds66/%EC%BF%A0%ED%8C%A1-%ED%81%B4%EB%A1%A0-%EC%BD%94%EB%94%A9-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EA%B0%9C%EC%9A%94)
- [#2 요구사항 정의](https://velog.io/@ddkds66/%EC%9D%B4%EC%BB%A4%EB%A8%B8%EC%8A%A4-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EC%9A%94%EA%B5%AC%EC%82%AC%ED%95%AD-%EC%A0%95%EC%9D%98)
- [#3 이벤트 스토밍을 통한 마이크로서비스 도출](https://velog.io/@ddkds66/%EC%9D%B4%EC%BB%A4%EB%A8%B8%EC%8A%A4-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EC%9D%B4%EB%B2%A4%ED%8A%B8-%EC%8A%A4%ED%86%A0%EB%B0%8D%EC%9D%84-%ED%86%B5%ED%95%9C-%EB%A7%88%EC%9D%B4%ED%81%AC%EB%A1%9C%EC%84%9C%EB%B9%84%EC%8A%A4-%EB%8F%84%EC%B6%9C)
- [#4 헥사고날 아키텍처를 적용한 마이크로서비스 상세 설계](https://velog.io/@ddkds66/%EC%9D%B4%EC%BB%A4%EB%A8%B8%EC%8A%A4-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EB%A7%88%EC%9D%B4%ED%81%AC%EB%A1%9C%EC%84%9C%EB%B9%84%EC%8A%A4-%EC%83%81%EC%84%B8-%EC%84%A4%EA%B3%84)
- [#5 프로젝트 마무리 및 회고](https://velog.io/@ddkds66/%EC%9D%B4%EC%BB%A4%EB%A8%B8%EC%8A%A4-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EB%A7%88%EB%AC%B4%EB%A6%AC-%EB%B0%8F-%ED%9A%8C%EA%B3%A0)

## 아키텍처
![q-pang 아키텍처](https://user-images.githubusercontent.com/49021557/209222999-cc6ac453-1a96-4be1-940c-77b8ea4a83e2.png)
- q-pang 서버는 총 4가지의 마이크로서비스로 이루어진다.
- 각 마이크로서비스로의 접근은 Gateway Server를 통해 이루어지며, 사용자 인증은 Gateway Server에서 수행한다.
- Gateway Server에서 request header에 담긴 jwt를 사용하여 사용자를 검증하고, payload에 담긴 username을 꺼내어 request header에 담아 마이크로서비스를 호출한다.
- Database Schema, Eureka Service Url, JWT Secret 등의 환경 정보는 Config Server에서 중앙 집중하여 관리한다.
- 마이크로서비스간 호출이 필요한 경우, 컨텍스트간에 항상 일관된 데이터가 필요하면 openfeign을 사용하여 동기 방식으로 호출하고, 최종 일관성으로 처리 가능한 경우 Kafka를 사용하여 이벤트 기반의 비동기 방식으로 호출한다.

## 실행하기
- [Docker Desktop](https://www.docker.com/products/docker-desktop/) 설치
- ```sh gradle-build.sh``` 명령어 실행
- ```docker-compose up -d``` 명령어 실행
  - ```docker-compose up -d --build``` rebuild 하고자 하는 경우 해당 명령어 사용

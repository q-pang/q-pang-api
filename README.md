# q-pang api
> 이커머스를 설계 및 구축하며 대규모 시스템에 필요한 개념과 기술을 적용해보기 위한 프로젝트입니다.

## 마이크로서비스 도출 과정
- [#1 요구사항 정의](https://velog.io/@ddkds66/%EC%9D%B4%EC%BB%A4%EB%A8%B8%EC%8A%A4-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EC%9A%94%EA%B5%AC%EC%82%AC%ED%95%AD-%EC%A0%95%EC%9D%98)
- [#2 이벤트 스토밍을 통한 마이크로서비스 도출](https://velog.io/@ddkds66/%EC%9D%B4%EC%BB%A4%EB%A8%B8%EC%8A%A4-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EC%9D%B4%EB%B2%A4%ED%8A%B8-%EC%8A%A4%ED%86%A0%EB%B0%8D%EC%9D%84-%ED%86%B5%ED%95%9C-%EB%A7%88%EC%9D%B4%ED%81%AC%EB%A1%9C%EC%84%9C%EB%B9%84%EC%8A%A4-%EB%8F%84%EC%B6%9C)
- [#3 헥사고날 아키텍처를 적용한 마이크로서비스 상세 설계](https://velog.io/@ddkds66/%EC%9D%B4%EC%BB%A4%EB%A8%B8%EC%8A%A4-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EB%A7%88%EC%9D%B4%ED%81%AC%EB%A1%9C%EC%84%9C%EB%B9%84%EC%8A%A4-%EC%83%81%EC%84%B8-%EC%84%A4%EA%B3%84)

## 실행하기
- [Docker Desktop](https://www.docker.com/products/docker-desktop/) 을 설치
- ```sh gradle-build.sh``` 명령어 실행
- ```docker-compose up -d``` 명령어 실행
  - ```docker-compose up -d -- build``` rebuild 하고자 하는 경우 해당 명령어 사용
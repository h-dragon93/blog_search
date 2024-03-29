# 블로그 검색 결과 제공 API

💡 오픈 API를 활용한 블로그 검색 결과 제공 서비스

💻 Java 11, Spring Boot 2.7.10, JPA, H2, Apache Tomcat, JUnit5(Mockito), Gradle                                

✔ 프로젝트 구조는 크게 인프라(infra)와 도메인(domain)으로 설계

### 테스트 방법 소개

😊 서비스 테스트 방법 (Fat JAR)

      JAR 파일 다운로드 링크 👇👇

        https://drive.google.com/file/d/1JUd04an7lQed-7FtSaQ9VdnQDm7Xv2MV/view?usp=sharing

      실행방법  →  java -jar blog-0.0.1-SNAPSHOT.jar

😊 테스트 URL 예시 (Post Man / Chrome)

        http://localhost:8081/search/kakao?query=%EB%B8%94%EB%A1%9C%EA%B7%B8&sort=recency&page=6&size=3

😊 DB 데이터 검증 (H2)

        http://localhost:8081/h2

     saved Settings → Generic H2 (Server)

Driver Class     → org.h2.Driver

jdbc url            → jdbc:h2:mem:test

User Name      → sa  (패스워드 없음)

---

🔑 **도메인 디렉토리**

- 도메인은 서비스 단위로 분리 후 web 디렉토리에서 공통 로직(Controller) 수행
- 계층간 데이터 이동은 RequestDTO와 ResponseDTO를 통해 수행
- Http통신, Url 빌더, DTO 생성 등은 유틸 클래스로 분리
- 인터페이스를 활용한 서비스 구현
- JPA와 JPQL을 활용한 DB 컨트롤 (@Transactional, @Modifying(clearAutomatically =true) 활용)

---

🔑 **인프라 디렉토리**

- 인프라는 @ControllerAdvice @ExceptionHandler를 활용해 공통 예외 처리와 개별 예외를 처리
- 표준예외 처리를 위해 ErrorCode, ErrorResponse, Custom Exception활용
- Checked Exception과 UnChecked Exception을 구분해 상속을 이용한 Custom Exception 구현
- 키워드 별 검색 횟수 저장을 위한 로직은 서비스 공통 로직이므로 interceptor에서 처리
- 검색 결과는 JSON 형태로 반환 및 이를 통해 블로그 제목, URL, 작성자, 작성일 등의 정보를 페이지네이션 가능한 형태로 제공

---

📃 전체 프로젝트 구조

![Untitled](https://user-images.githubusercontent.com/34955578/226884133-64f51829-2d1b-4682-a680-5ed2ac9c3ada.png)

 ⛏ 프로젝트 구조 요약본

![Untitled (1)](https://user-images.githubusercontent.com/34955578/226884436-8e78daed-616f-4da7-8779-46ea8745a0b8.png)

## API 명세서

---

[요청](https://www.notion.so/336d8c5ab4dc4e8482e3e3acb6a46379)

⚙ **GET /search/kakao**

Authorization : KakaoAK ${API_KEY}

   * API_KEY는 프로퍼티 주입

| 파라미터 | 타입 | 설명 | 필수 여부 |
| --- | --- | --- | --- |
| query | String | 검색 키워드 | Y |
| sort | String | accuracy(정확도/기본값)  recency(최신순) | N |
| page | Integer | 1~50, default 1 | N |
| size | Integer | 1~50, default 10 | N |

[응답](https://www.notion.so/b981ad2816444048b17d3672d47f6dd2)

@ResponseBody, List<T> 형태 반환

MappingJackson2HttpMessageConverter에 의해 JSON 형태로 브라우저에 전달

| 키 | 타입 | 설명 |
| --- | --- | --- |
| title | String | 블로그 제목 |
| contents | String | 블로그 요약 |
| url | String | 블로그 URL |
| blogname | String | 블로그 이름 |
| thumbnail | String | 블로그 이미지 URL |
| datetime | DateTime | 블로그 작성시간 |

---

[요청](https://www.notion.so/aa9638b9b73e426d846145f2f77da082)

⚙ **GET /search/topKeyword**

[응답](https://www.notion.so/dcca14b35f2c49e6b8093784cd00efff)

최다 검색 키워드 상위 10개 까지만 제공

| 키 | 타입 | 설명 |
| --- | --- | --- |
| id | Long | @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RANKING_SEQ_GENERATOR")  |
| keyword | String | 검색 키워드 |
| count | Integer | 검색 횟수 |

---

## API 테스트

⚙ **GET /search/kakao**

![Untitled (2)](https://user-images.githubusercontent.com/34955578/226884917-6899bbf3-8f50-481b-9981-a0e9964662b1.png)

⚙ **GET /search/topKeyword**

![Untitled (3)](https://user-images.githubusercontent.com/34955578/226884956-37d03a62-614c-4a76-80ef-8099bc0455b3.png)
---

## Junit5, Mockito를 활용한 단위 테스트 작성
![image](https://user-images.githubusercontent.com/34955578/226894467-3d57babd-8315-4300-ae5a-45e391dc883c.png)

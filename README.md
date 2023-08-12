# 📖 취업 CS 걱정하지마, CS;tudy

<p align="center">
  
![image](https://github.com/CStudyTeam/CStudy-backend/assets/103854287/794e4e83-26ae-4504-bc0f-a36f15d7566c)</p>

<br>

## ✨ 프로젝트 소개
``` Introduce ```
- 취업에 있어 필요한 CS 지식을 학습할 수 있는 웹 어플리케이션 플랫폼 입니다. 카테고리 별 문제를 통해 필요한 지식을 빠르게 학습할 수 있으며 틀린 문제를 모아서 부족한 부분을 빠르게 인지할 수 있습니다.  또한 경쟁을 통하여 지루한 CS 학습에 재미를 부여하며 선의의 경쟁을 통해 동료와 함께 성장이 가능합니다.

- [⭐️ 팀 노션](https://sunny-radiator-7f3.notion.site/CStudy-5da03f7b12d5477eae1e35caacd04615?pvs=4) | ~~[📝 배포 사이트 ](https://dbsyacmkozvg1.cloudfront.net/)~~

<br>



## 🌞 팀원소개
<table>
  <tr>
    <td>
         <img src="https://user-images.githubusercontent.com/103854287/211192470-8aa1b1b8-0547-4da4-b674-3e08778bdf98.png" width="100px" />
    </td>
     <td>
         <img src="https://user-images.githubusercontent.com/103854287/211192470-8aa1b1b8-0547-4da4-b674-3e08778bdf98.png" width="100px" />
    </td>
  </tr>
  <tr>
    <td><b>김무건</b></td>
    <td><b>김준하</b></td>
  </tr>
</table>

<br>

### 프로젝트 구조도
```bash
src
├── global 
│   ├── exception # 도메인별 에러 정의
│   ├── config
│   ├── redis
│   ├── util
│   ├── initializer
│   └── jwt 
│ 
└── Domain   
       └── <도메인>  # 각도메인 ex : order ,ticket
             └── controller # 도메인 컨트롤러
             └── domain # 도메인 오브젝트
             └── repostiory # 도메인 리포지토리
             └── service # 도메인 서비스, 도메인 이벤트 핸들러
```

<br>
<br>

## 👨‍기술 스택

<h3 align="center">어플리케이션</h3>

<p align="center">

<img src="https://img.shields.io/badge/Java 11-008FC7?style=for-the-badge&logo=Java&logoColor=white"/>
<img src="https://img.shields.io/badge/spring 2.7.9-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white"/>
<img src="https://img.shields.io/badge/Spring Security-6DB33F?style=for-the-badge&logo=Spring Security&logoColor=white"/>
<img src="https://img.shields.io/badge/Spring Data JPA-6DB33F?style=for-the-badge&logo=JPA&logoColor=white"/>

<img src="https://img.shields.io/badge/-QueryDSL-blue?style=for-the-badge"/>
<img src="https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=Gradle&logoColor=white"/>
<img src="https://img.shields.io/badge/Junit-25A162?style=for-the-badge&logo=Junit5&logoColor=white"/>

<img src="https://img.shields.io/badge/Mockito-FF9900?style=for-the-badge&logo=Mockito&logoColor=white"/>
<img src="https://img.shields.io/badge/JSON Web Tokens-000000?style=for-the-badge&logo=JSON Web Tokens&logoColor=white"/>

</p>


<h3 align="center">DB</h3>

<p align="center">  
<img src="https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white"/>
<img src="https://img.shields.io/badge/redis-%23DD0031.svg?style=for-the-badge&logo=redis&logoColor=white"/>

</p>

<h3 align="center">인프라</h3>

<p align="center">   

<img src="https://img.shields.io/badge/Jenkins-D24939?style=for-the-badge&logo=Jenkins&logoColor=white"/>
<img src="https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white"/>
<img src="https://img.shields.io/badge/Amazon EC2-FF9900?style=for-the-badge&logo=Amazon EC2&logoColor=white"/>
<img src="https://img.shields.io/badge/Amazon RDS-527FFF?style=for-the-badge&logo=Amazon RDS&logoColor=white"/>

</p>

<h3 align="center">문서 / 협업</h3>

<p align="center">   

<img src="https://img.shields.io/badge/swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=white"/>
<img src="https://img.shields.io/badge/Notion-000000?style=for-the-badge&logo=Notion&logoColor=white"/>
<img src="https://img.shields.io/badge/Git-F05032.svg?style=for-the-badge&logo=Git&logoColor=white"/>
<img src="https://img.shields.io/badge/GitHub-181717.svg?style=for-the-badge&logo=GitHub&logoColor=white"/>
<img src="https://img.shields.io/badge/Slack-4A154B?style=for-the-badge&logo=Slack&logoColor=white"/>
<img src="https://img.shields.io/badge/Postman-FF6C37.svg?style=for-the-badge&logo=Postman&logoColor=white"/>

</p><br>


<br>

## 🐌Git Commit Convention
<table>
  <tr>
    <td>
         ✨feat
    </td>
     <td>
        새로운 기능과 관련된 것을 의미
    </td>
  </tr>
  <tr>
    <td>
         🐛fix
    </td>
     <td>
        오류와 같은 것을 수정을 하였을 때 사용
    </td>
  </tr>
   <tr>
    <td>
         ✅test
    </td>
     <td>
        테스트를 추가하거나 수정
    </td>
  </tr>
  <tr>
    <td>
         📝docs
    </td>
     <td>
        문서와 관련하여 수정한 부분이 있을 때 사용
    </td>
  </tr>
    <tr>
    <td>
         🔥move
    </td>
     <td>
        파일, 코드의 이동
    </td>
  </tr>
    <tr>
    <td>
         💚build
    </td>
     <td>
         빌드 관련 파일을 수정
    </td>
  </tr>
    <tr>
    <td>
         ♻️refactor
    </td>
     <td>
       코드의 리팩토링을 의미
    </td>
  </tr>
</table>

<br>


## 🎨 ERD Diagram

<p align="center">
  
![image](https://github.com/CStudyTeam/CStudy-backend/assets/103854287/35e21686-098a-4d00-b046-0b24e55f3c2f)


</p>

<br/>

## 🥕 Back-end 기술적 의사결정

### JWT Refresh Token

- 로그인, 로그아웃을 위해 RefreshToken 사용
- Token의 탈취의 보안적인 측면을 고려하여 AccessToken : 15 min , RefreshToken: 7 Day
- 저장의 위치 : Coockie vs LocalStorage
  - 현재 저장의 위치는 LocalStorage에 저장을 하였습니다. 왜냐하면 LocalStorage에 저장하면 구현의 난이도가 쉬워지기 때문에 초기 프로젝트에서 LocalStorage를 선택을 하였습니다.
  - 하지만 보안적인 측면을 생각하면 Coockie에 저장하는 방식이 적합합니다. 왜냐하면 Rest Api의 특성은 상태의 관리를 클라이언트에서 처리를 합니다. LocalStorage에 저장하면 RDB, Redis에 저장을 하여 상태관리를 Server에서 위치를 합니다.
  - XSS, CSRF 보안적인 측면
    - XSS 공격 : Script를 통하여 Token의 탈취의 가능성이 있습니다. Coockie에 Token을 저장하면 HttpOnly를 통하여 스크립트를 통하여 Token의 문제를 방지할 수 있습니다.
    - CSRF 공격 : SameSite, Csrf Token을 통하여 방지를 할 수 있다. SameSite를 사용하면 쿠키를 해당 사이트와 동일한 사이트로만 전송되도록 제한할 수 있습니다. Chrome은 기본적으로 Lax로 설정이 되어져 있습니다.

### Swagger
- 팀 간 효율적인 소통을 위해 도입
- API를 문서화하여 구조와 기능을 쉽게 이해
- 테스트 가능한 사용자 인터페이스 제공
- API 호출을 직접 실행해보며 결과를 확인할 수 있어 테스트 및 디버깅 과정이 효율적으로 진행

### 랭킹 점수는 실시간

- 실시간으로 랭킹의 점수와 랭킹의 불러오는 쿼리의 성능의 부담을 줄이기 위하여 캐싱을 사용을 했습니다.
- 문제를 성공/실패를 하였을 때 캐싱의 정합성을 맞추기 위해서 많은 Write 작업이 발생을 하여 캐싱 오버헤드가 발생을 한다고 생각합니다.
- 기존의 ```@CacheEvict```로 캐싱의 정합성을 맞추기 보다 Redis Pub/Sub으로 분산 환경에서 비동기 캐시 정합성을 맞추게 변경을 하였습니다.

### 동일한 점수의 회원의 랭킹 처리
- 동일한 점수를 가진 회원 A,B가 있다면 등수를 처리하는데 문제가 있습니다.
- 해당 회원의 등수를 처리하기 위해 Redis Structure를 Double로 변경
- 기존의 데이터의 구조는  { UserName , Score }에서  { UserName , Score.Time }으로 처리를 하였습니다.
  
<br>

## 🐻 프로젝트 설명


<details>

<summary> 🥃 Wireframe </summary>

[📝 Figma 바로가기 ](https://www.figma.com/file/67asFaSpQCu4s2CKAJqxac/Untitled?type=design&node-id=0-1&mode=design&t=DdRtY5ictOvnNkSn-0)

![image](https://github.com/CStudyTeam/CStudy-backend/assets/103854287/cf4eae6b-43b5-409d-9125-178e33b89473)

</details>


<details>

<summary> 🏛️ CI/CD (배포 자동화) </summary>

![image](https://github.com/CStudyTeam/CStudy-backend/assets/103854287/d34ed6b2-b91f-4e27-a175-6dc2629e5747)


</details>




<details>

<summary> 🔧 트러블 슈팅 </summary>

</details>

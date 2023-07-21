# 📖 취업 CS 걱정하지마, CS;tudy

## ✨ 프로젝트 소개

- 취업에 있어 필요한 CS 지식을 학습할 수 있는 웹 어플리케이션 플랫폼 입니다. 카테고리 별 문제를 통해 필요한 지식을 빠르게 학습할 수 있으며 틀린 문제를 모아서 부족한 부분을 빠르게 인지할 수 있습니다.  또한 경쟁을 통하여 지루한 CS 학습에 재미를 부여하며 선의의 경쟁을 통해 동료와 함께 성장이 가능합니다.

- [⭐️ 팀 노션](https://sunny-radiator-7f3.notion.site/CStudy-5da03f7b12d5477eae1e35caacd04615?pvs=4) | [📝 배포 사이트 ](https://dbsyacmkozvg1.cloudfront.net/)

<p align="center">
  
![image](https://github.com/CStudyTeam/CStudy-backend/assets/103854287/794e4e83-26ae-4504-bc0f-a36f15d7566c)</p>

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
<br>

## 🐻 프로젝트 설명
<details>

<summary> 👀 주요 기능 </summary>

</details>

<details>

<summary> 🥃 Wireframe </summary>

https://www.figma.com/file/67asFaSpQCu4s2CKAJqxac/Untitled?type=design&node-id=0-1&mode=design&t=DdRtY5ictOvnNkSn-0
![image](https://github.com/CStudyTeam/CStudy-backend/assets/103854287/cf4eae6b-43b5-409d-9125-178e33b89473)

</details>

<details>

<summary> 🧊 ERD Diagram </summary>

![image](https://github.com/CStudyTeam/CStudy-backend/assets/103854287/b538e33b-a12a-44ef-9f73-fbf0dfe36c1c)

</details>


<details>

<summary> 🏛️ CI/CD (배포 자동화) </summary>

![image](https://github.com/CStudyTeam/CStudy-backend/assets/103854287/88f188ca-3b95-4296-a671-bd14d4fc1e4b)

</details>

<details>

<summary> 🥕 Back-end 기술적 의사결정 </summary>

</details>

<details>

<summary> 🔧 트러블 슈팅 </summary>

</details>

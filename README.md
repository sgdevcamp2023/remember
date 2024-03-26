# remember

- 스마일게이트 개발 캠프 2023 - Remember
- 개발 기간 : 2023.12 ~ 2024.02

2023 Smilegate 개발 캠프에서진행한 Discord 클론 프로젝트 Harmony입니다!  
4명의 백엔드 개발자가 서로 다른 언어를 사용하여 MSA구조의 서로 다른 서버를 조화롭게 구현하였습니다.  
자세한 내용은 각 [서버](#역활-분담)와 [Docs](./docs/)를 참고해주세요.

## 로고

<img src="./resources/harmony.png" width="250px">

## 팀 소개

- 저희 팀은 총 백엔드 4명으로 이루어진 팀입니다.
- 각각 Spring 개발자 2명, Node.js 개발자 1명, ASP.NET Core 개발자 1명으로 이루어져 있습니다.

<table align="center">
    <tr align="center">
        <td><B>김영현(Spring)<B></td>
        <td><B>홍지현(ASP.NET)<B></td>
        <td><B>안재진(Spring)<B></td>
        <td><B>최성민(Node.js)<B></td>
    </tr>
    <tr align="center">
        <td>
            <img src="https://github.com/0chord.png" style="max-width: 100px">
            <br>
            <a href="https://github.com/0chord"><I>0chord</I></a>
        </td>
        <td>
          <img src="https://github.com/Hong-Study.png" style="max-width: 100px">
            <br>
            <a href="https://github.com/Hong-Study"><I>Hong-Study</I></a>
        </td>
        <td>
            <img src="https://github.com/acs0209.png" style="max-width: 100px">
            <br>
            <a href="https://github.com/acs0209"><I>acs0209</I></a>
        </td>
        <td>
            <img src="https://github.com/smaivnn.png" style="max-width: 100px">
            <br>
            <a href="https://github.com/smaivnn"><I>smaivnn</I></a>
        </td>
        </td>
    </tr>
</table>

## 기술 스택

### Frontend

- React, Javascript
- Zustand
- styled-components
- Stomp.js

### Backend

- Language
  - node.js, Nest.js
  - Java 17
  - Spring Boot 3.2.1
  - Spring MVC
  - C#
  - ASP.NET Core 16
- Library
  - WebSocket, STOMP, SockJS
  - mediasoup
  - socket.io
- Database
  - MSSQL
  - MySQL
  - Mongo DB
  - Redis
- Common
  - Jenkins(CI/CD)
  - Google Cloud Platform
  - Docker
  - NGINX
  - EFK
  - kafka

## 전체 아키텍처

![image](./resources/전체%20아키텍처.png)

## 역활 분담

### 김영현

- [커뮤니티 서버](./docs/서버-소개/커뮤니티-서버)
- [로그 서버](./docs/서버-소개/로그-서버)
- Jenkins
- NGINX
- GCP

### 최성민

- [미디어 서버](./src/backend/media-service/server)
- STUN + TURN 서버

### 안재진

- [채팅 서버](./src/backend/chat-service)
- [상태 관리 서버](./src/backend/state-service)
- [kafka](./docs/서버-소개/카프카)

### 홍지현

- [Smile Gateway](./src/backend/api-gateway/)
- [유저 서버](./src/backend/user-service/)
- NGINX

### 프론트

- 모두가 함께 만듬.

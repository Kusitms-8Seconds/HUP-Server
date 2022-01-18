# 💰 HUP-Server

<p align="center"><img src="https://user-images.githubusercontent.com/61726631/149874766-fcb10202-e727-4841-bfa4-2ebddc515b8d.jpg" width="400" height="400"/><p>
<div align="center"><h2>HURRY UP! HUP! </br> 중고거래에 실시간의 재미를 더하다, 실시간 중고 물품 경매 중개 플랫폼입니다.:grinning:</h2></div>
  
## :raising_hand: Introduce

- 큐시즘 학술제 2조 에잇세컨즈의 HUP!입니다.
- 경매에 대한 경험이 부족하고, 경매에 부담을 갖고 있는 z세대들에게, 더 쉽게 다가갈 수 있는 방안이 필요했습니다. 
- 따라서, 중고거래의 즐거움을 높이고, 경매 진입장벽은 낮추고, 실시간 거래는 빠른 HUP을 제작하였습니다.
- 서버 레포지토리와 안드로이드 레포지토리로 나뉘어져 있습니다.
- 현재 레포지토리는 서버 레포지토리이며 안드로이드 레포지토리는 아래 주소에서 확인 가능합니다.
- [안드로이드 레포지토리](https://github.com/Kusitms-8Seconds/HUP-App)

## :mag_right: ItemPoster

 <p align="center"><img src="https://user-images.githubusercontent.com/61726631/149875042-4a0d2719-c8c3-48b9-905d-693234d99310.png" width="800" height="1200"/><p>

## ⚙️ Back-End CI/CD
  
 <p align="center"><img src="https://user-images.githubusercontent.com/61726631/149875210-3e394e02-a4f7-4924-b9c5-86880ed02a84.jpg" width="800" height="400"/><p>
   
 <h3>Back-End CI/CD는 다음과 같은 절차로 작동합니다.</br></h3>
 1. Local에서 Github로 푸쉬합니다.</br>
 2. Github의 WebHook을 이용해 Jenkins에 전달합니다.</br>
 3. Jenkins는 Github의 코드를 받아 빌드와 테스트를 진행하고 Dockerfile을 이용해 이미지를 빌드한 후 Dockerhub에 푸쉬합니다.</br>
 4. Jenkins는 각종 배포에 필요한 파일들을 Jenkins의 SSH2Easy Plugin을 이용해 운영EC2에 명령어와 같이 전송합니다.</br>
 5. 각종 배포에 필요한 파일들에는 DockerHub에서 이미지를 받아오고, 실행시키는 코드가 담겨있습니다.</br>
 6. 마지막으로, 운영용 EC2에서 배포에 필요한 파일들을 실행하여 Docker로 SpringBoot와 MariaDB를 띄웁니다.</br>

## :electric_plug: Development environment

분류 | 항목 
----- | ----- 
Language | JAVA
Framework | Spring, Spring Boot, Spring Security, Spring Validation, Hibernate, Swagger
DMBS | MairaDB
Library | Lombok, Querydsl, STOMP, JWT, Hateoas 
API | Google Login API, Kakao Login API, Naver Login API

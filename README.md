![Image](https://github.com/user-attachments/assets/373609bd-30a4-42e7-8a91-1aac6f558303)

# SPRING LEGACY & VUE.JS 개인 프로젝트 CafeBoard

## 목차
+ [개요](#chapter1)
+ [기술 스택](#chapter2)
+ [핵심 기능](#chapter3)
+ [주요기능 실행 화면](#chapter4)
+ [개선사항](#chapter5)

  
<br/>
<br/>


## 1. 개요 <a id="chapter1"></a>
+ 프로젝트 목표: Axios 라이브러리를 이용하여 Spring Legacy(Backend)와 Vue.js(Frontend)를 연동, 네이버카페를 모티브로한 게시판 제작
+ 개발 기간 : 25/03/17 - 25/04/04

  
<br/>
<br/>


## 2. 기술 스택 <a id="chapter2"></a>
+ Language : Java(11), JavaScript(1.5)
+ Library & Framework : Spring(5.2.7), JUnit(4.12), Vue/cli(5.0.8), Axios(1.8.3)
+ Database : Oracle(11g)
+ Target: Web browser
+ Tool : Spring Tool Suite 3.9.18.RELEASE
+ ETC : Git

  
<br/>
<br/>


## 3. 핵심 기능 <a id="chapter3"></a>
#### 게시판
+ 각 게시판별 게시글 목록 조회
+ 공지 숨기기/게시판 타입 변경 기능
+ 페이징 처리
+ 검색 기능
#### 게시글
+ 게시글 작성
+ 게시글 조회
+ 게시글 수정/삭제
#### 댓글
+ 댓글 작성/조회
+ 댓글 수정/삭제

  
<br/>
<br/>


## 4. 주요 기능 실행 화면 <a id="chapter4"></a>
#### 게시판
+ Router-link에 파라미터로 각 게시판 이름을 전달하고, 해당되는 게시판의 게시글을 목록으로 조회

![Image](https://github.com/user-attachments/assets/63ddbbef-c0a2-4b29-80a9-6a3c29d6e53b)
  
+ 스타일 바인딩 기능으로 체크박스 체크시 display: none 처리를 하여 공지 목록 숨기기
+ 클래스 바인딩 기능으로 각 게시판 타입 선택시 나머지 게시판 타입의 숨김 처리
+ 목록 타입의 게시판은 한 페이지에 노출되는 게시글의 개수를 데이터 바인딩 기능으로 변경

![Image](https://media.githubusercontent.com/media/InhoKang92/CafeBoard/refs/heads/main/src/main/webapp/resources/gifs/BoardList2.gif)

+ 목록 페이징 처리 + 검색 타입과 검색어를 입력받아서 해당 데이터를 axios.get의 파라미터로 전달하여 서버 재호출

![Image](https://github.com/user-attachments/assets/ca7dd444-19c5-498f-9d09-b9122602ac85)

#### 게시글
+ 게시판 선택 후 게시판 종류에 따른 말머리 목록 변경
+ 제목 및 내용 입력 후 등록 버튼 클릭하면 게시글 등록과 함께 목록으로 자동 이동

![Image](https://github.com/user-attachments/assets/bf2f91f2-4d52-4996-adff-a6aa67709fce)

+ 수정페이지에서 필요 입력 항목 입력 후 수정 버튼 클릭하면 게시글 수정과 함께 목록으로 자동 이동
+ 삭제 버튼 클릭시 삭제 경고창 출력, 확인 클릭하면 게시글 삭제와 함께 목록으로 자동 이동

![Image](https://github.com/user-attachments/assets/a6ddccee-e8b5-48af-8459-d5bc9f07250d)

#### 댓글
+ 게시글 조회 페이지 하단에서 댓글 작성 가능, 등록 버튼 클릭시 댓글 작성과 함께 댓글 목록 조회
+ 댓글 목록 최신순/등록순으로 정렬 가능

![Image](https://github.com/user-attachments/assets/4c245bee-18e7-40e9-93a1-e6ba223bdb0b)

+ 해당 댓글 우측 미트볼 메뉴 버튼 클릭시 수정/삭제 버튼 노출
+ 수정 버튼 클릭시 댓글 수정 가능
+ 삭제 버튼 클릭시 경고창 출력, 확인 클릭시 댓글 삭제 후 페이지 재렌더링

![Image](https://github.com/user-attachments/assets/dec0cca4-1aa9-4d58-9a1d-baddbc7dc0f8)

  
<br/>
<br/>


## 5. 개선사항 <a id="chapter5"></a>
+ 회원과 관련된 기능(로그인, 시큐리티 등)

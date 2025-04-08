<template>
  <div class="container">
    <div class="mainImgBox">
      <router-link to="/">
        <img src="./assets/main_img.jpg" alt="" width="1080px" style="margin-top: -220px;">
      </router-link> |
    </div>
    <div class="shortCut">
      <router-link v-bind:class="{shorcutActive: All}" @click="onAll" :to="{ path: '/AllBoard', query: { boardName: '' } }">전체글보기</router-link>
      <router-link v-bind:class="{shorcutActive: Notice}" @click="onNotice" :to="{ path: '/AllBoard', query: { boardName: '공지사항' } }">공지사항</router-link>
      <router-link v-bind:class="{shorcutActive: News}" @click="onNews" :to="{ path: '/AllBoard', query: { boardName: '뉴스게시판' } }">뉴스게시판</router-link>
      <router-link v-bind:class="{shorcutActive: Debate}" @click="onDebate" :to="{ path: '/AllBoard', query: { boardName: '토론게시판' } }">토론게시판</router-link>
      <router-link v-bind:class="{shorcutActive: Free}" @click="onFree" :to="{ path: '/AllBoard', query: { boardName: '자유게시판' } }">자유게시판</router-link>
    </div>

    <!-- <router-link to="/AllVue">AllVue</router-link><br>
    <router-link to="/MemberVue">MemberVue</router-link><br>
    <router-link to="/AdminVue">AdminVue</router-link><br> -->



    <div class="bodyContainer">
      <router-view/>
      <div class="bodyRight" ref="bodyRight">
        <div class="bodyRight_top">
          <div class="top_btn">
            <a href="#">카페정보</a>
            <router-link to="/LoginPage">나의활동</router-link>
          </div>

        
          <router-link to="/BoardWrite" class="write_btn">카페 글쓰기</router-link>
          <button class="chat_btn">카페 채팅</button>
        </div>
        
        <div class="boardLinks">
          <div>
            <div>
              <font-awesome-icon class="list_icon" icon="list" />
              <router-link v-bind:class="{shorcutActive: All}" @click="onAll" :to="{ path: '/AllBoard', query: { boardName: '' } }">전체글보기</router-link>
            </div>
            <span class="total">{{ totalFormat }}</span>
          </div>
          <div class="noticeBoard">
            <div>Announcements</div>
            <div>
              <font-awesome-icon class="list_icon" icon="list" />
              <router-link v-bind:class="{shorcutActive: Notice}" @click="onNotice" :to="{ path: '/AllBoard', query: { boardName: '공지사항' } }">공지사항</router-link>
            </div>
          </div>
          <div class="otherBoards">
            <div>Boards</div>
            <div>
              <font-awesome-icon class="list_icon" icon="list" />
              <router-link v-bind:class="{shorcutActive: News}" @click="onNews" :to="{ path: '/AllBoard', query: { boardName: '뉴스게시판' } }">뉴스게시판</router-link>
            </div>
            <div>
              <font-awesome-icon class="list_icon" icon="list" />
              <router-link v-bind:class="{shorcutActive: Debate}" @click="onDebate" :to="{ path: '/AllBoard', query: { boardName: '토론게시판' } }">토론게시판</router-link>
            </div>
            <div>
              <font-awesome-icon class="list_icon" icon="list" />
              <router-link v-bind:class="{shorcutActive: Free}" @click="onFree" :to="{ path: '/AllBoard', query: { boardName: '자유게시판' } }">자유게시판</router-link>
            </div>
          </div>
        </div>

      </div>
    </div>



  </div>
</template>

<script>
import axios from 'axios';

axios.defaults.baseURL = 'http://localhost:8087';

  export default {
    data() {
      return {
        All: false,
        Notice: false,
        News: false,
        Debate: false,
        Free: false,
        pageNum: 1,
        amount: 15,
        total: 0,
        totalFormat: '',
      }
    },
    watch: {
      '$route.path'(path){
        if(path == '/BoardWrite' || path == '/LoginPage'){
          this.$refs.bodyRight.style.display = 'none';
        }else {
          this.$refs.bodyRight.style.display = 'block';
        }
      }
    },
    created() {
      this.getTotal();
    },
    mounted() {
      
    },
    methods: {
      onAll(){
        this.All = true;
        this.Notice = false;
        this.News = false;
        this.Debate = false;
        this.Free = false;
      },
      onNotice(){
        this.All = false;
        this.Notice = true;
        this.News = false;
        this.Debate = false;
        this.Free = false;
      },
      onNews(){
        this.All = false;
        this.Notice = false;
        this.News = true;
        this.Debate = false;
        this.Free = false;
      },
      onDebate(){
        this.All = false;
        this.Notice = false;
        this.News = false;
        this.Debate = true;
        this.Free = false;
      },
      onFree(){
        this.All = false;
        this.Notice = false;
        this.News = false;
        this.Debate = false;
        this.Free = true;
      },
      getTotal(){
        axios.get('/board/getTotal?pageNum=1&amount=15&boardName=&type=&keyword=')
        .then((response) => {
          this.total = response.data;
          this.totalFormat = response.data.toLocaleString();
        })
        .catch(error => {
          console.log(error);
        });
      },
      
    },
  }
</script>

<style>
* {
    margin: 0;
    padding: 0;
    text-decoration: none;
    color: #000;
    list-style: none;
    box-sizing: border-box;
  }
  .container {
    width: 1080px;
    margin: 0 auto;
  }
  .mainImgBox {
    width: 100%;
    height: 340px;
    overflow: hidden;
  }
  .shortCut {
    width: 100%;
    border: 1px solid #ddd;
    padding: 10px 20px;
    display: flex;
    gap: 30px;
    margin-top: 8px;
    margin-bottom: 20px;
  }
  .shortCut a {
    position: relative;
    font-size: 13px;
  }
  .shortCut a:hover {
    text-decoration: underline;
  }
  .shortCut a+::before {
    position: absolute;
    top: 8px;
    left: -16px;
    display: block;
    content: '';
    background: #888;
    width: 2px;
    height: 2px;
    border-radius: 50%;
  }
  .shorcutActive{
    font-weight: bold;
  }
  .bodyContainer {
    display: flex;
    gap: 20px;
  }
  .bodyLeft {
    flex: 1;
  }
  .boardTitle {
    margin-bottom: 6px;
    font-size: 22px;
  }
  .homeImg {
    width: 100%;
  }
  .bodyRight {
    width: 200px;
    border-top: 2px solid #333;
  }
  .bodyRight_top {
    border-bottom: 2px solid #333;
  }
  .bodyRight a:hover {
    text-decoration: underline;
  }
  .boardLinks a {
    font-size: 13px;
  }
  .top_btn {
    display: flex;
    border-bottom: 1px solid #ccc;
    margin-bottom: 10px;
  }
  .top_btn a{
    position: relative;
    border: none;
    background: none;
    width: 50%;
    font-size: 13px;
    padding: 10px;
    font-weight: bold;
    text-align: center;
    cursor: pointer;
  }
  .top_btn a:last-child::before {
    position: absolute;
    top: 12px;
    left: 0;
    display: block;
    content: '';
    width: 1px;
    height: 15px;
    background: #ccc;
  }
  .write_btn {
    display: block;
    text-align: center;
    width: 100%;
    font-size: 13px;
    font-weight: bold;
    color: #fff;
    background: #5a5a5a;
    border: 1px solid #5a5a5a;
    margin-bottom: 10px;
    padding: 10px;
    border-radius: 6px;
    cursor: pointer;
  }
  .chat_btn {
    width: 100%;
    font-size: 13px;
    font-weight: bold;
    border: 1px solid #aaa;
    padding: 10px;
    border-radius: 6px;
    background: none;
    margin-bottom: 10px;
    cursor: pointer;
  }
  .write_btn:hover {
    text-decoration: underline;
  }
  .chat_btn:hover {
    text-decoration: underline;
  }
  .boardLinks> div:first-child {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 10px 5px;
  }
  .list_icon {
    font-size: 14px;
    margin-right: 5px;
    opacity: 0.5;
  }
  .total {
    font-size: 13px;
    color: #333;
  }
  .noticeBoard {
    border-top: 2px solid #ccc;
    border-bottom: 2px solid #ccc;
  }
  .noticeBoard div:first-child {
    border-bottom: 1px solid #ccc;
    padding: 10px 5px;
    font-size: 13px;
    font-weight: bold;
  }
  .noticeBoard div:last-child {
    display: flex;
    padding: 10px 5px;
    align-items: center;
  }
  .noticeBoard a:last-child {
    font-size: 13px;
  }
  .otherBoards {
    border-bottom: 2px solid #ccc;
    padding-bottom: 10px;
  }
  .otherBoards div:first-child {
    border-bottom: 1px solid #ccc;
    padding: 10px 5px;
    font-size: 13px;
    font-weight: bold;
  }
  .otherBoards div:not(:first-child) {
    display: flex;
    padding: 10px 5px;
    align-items: center;
  }
  .otherBoards a {
    font-size: 13px;
  }
  .boardInfo {
    display: flex;
    justify-content: space-between;
  }
  .boardInfo p {
    font-size: 13px;
    line-height: 30px;
  }
  .boardInfo p span {
    font-weight: bold;
  }
  .boardInfoRight {
    display: flex;
    gap: 10px;
  }
  .noticeHide {
    font-size: 14px;
  }
  .amountSelect {
    font-size: 14px;
  }
  .amountSelect option {
    font-size: 14px;
  }
  .totalHide {
    display: none;
  }






  #noticeHideCheck {
    display: none;
  }

  .noticeHide {
    position: relative;
    font-size: 13px;
    line-height: 30px;
  }

  .noticeHide:before {
    display: block;
    position: absolute;
    top: 1px;
    left: -22px;
    content: '';
    width: 16px;
    height: 16px;
    background-image: url(./assets/sp_icon_white_72ca546e.svg);
    background-position: -28px -66px;
  }

  .noticeHided:before {
    display: block;
    position: absolute;
    top: 1px;
    left: -22px;
    content: '';
    width: 16px;
    height: 16px;
    background-image: url(./assets/sp_icon_white_72ca546e.svg);
    background-position: -4px -66px;
  }

  .displayType {
    position: relative;
    display: flex;
    gap: 10px;
    margin-top: 1px;
    margin-left: 10px;
  }

  .displayType::before {
    display: block;
    position: absolute;
    top: 7px;
    left: -10px;
    content: '';
    width: 1px;
    height: 15px;
    background: #ddd;
  }

  .displayType button {
    border: none;
    background: none;
    font-size: 13px;
    cursor: pointer;
  }

  .displayType button:hover {
    text-decoration: underline;
  }

  .typeActive {
    color: #03c75a;
  }

  .amountSelect {
    width: 78px;
    height: 28px;
    border: 1px solid #ddd;
    position: relative;
    cursor: pointer;
  }

  .amountSelect .selected {
    display: flex;
  }

  .amountSelect .selected .selected-value {
    width: 60px;
    height: 28px;
    font-size: 12px;
    color: #333;
    line-height: 16px;
    padding: 5px 10px;
  }

  .arrow {
    width: 10px;
    height: 10px;
    background-image: url(./assets/sp_icon_white_72ca546e.svg);
    background-position: -70px -48px;
    margin-top: 7px;
    margin-left: -2px;
  }

  .reverseArrow {
    width: 10px;
    height: 10px;
    background-image: url(./assets/sp_icon_white_72ca546e.svg);
    background-position: -102px -86px;
    margin-top: 7px;
    margin-left: -2px;
  }

  .selected-value:hover {
    text-decoration: underline;
  }

  .amountSelect ul {
    border: 1px solid #ddd;
    width: 78px;

    position: absolute;
    top: 26px;
    left: -1px;
  }

  .amountSelect ul li {
    width: 100%;
    height: 28px;
    font-size: 12px;
    color: #333;
    line-height: 16px;
    padding: 5px 10px;
    background: #fff;
  }

  .amountSelect ul li:hover {
    text-decoration: underline;
    background: #E2FAEA;
    color: #03C75A;
  }



  

  .bodyLeft table {
    width: 100%;
    border-collapse: collapse;
    border-top: 1px solid black;
    margin-top: 10px;
  }

  .bodyLeft tr {
    border: 1px solid #ddd;
    border-left: none;
    border-right: none;
  }

  .bodyLeft th {
    font-size: 13px;
    padding: 6px 10px;
    height: 44.5px;
  }

  .bodyLeft td {
    font-size: 13px;
    padding: 6px 10px;
    height: 37px;
  }

  .bodyLeft th:nth-child(1) {
    width: 80px;
  }

  .bodyLeft td:nth-child(2) span {
    font-size: 13px;
    color: #959595;
  }

  .bodyLeft th:nth-child(3) {
    width: 118px;
  }

  .bodyLeft th:nth-child(4) {
    width: 82px;
  }

  .bodyLeft th:nth-child(5) {
    width: 68px;
  }

  .bodyLeft td:nth-child(1) {
    font-size: 11px;
  }

  .bodyLeft td:nth-child(3) > a {
    color: #666;
  }

  .bodyLeft td:nth-child(4) {
    text-align: center;
    color: #666;
  }

  .bodyLeft td:nth-child(5) {
    text-align: center;
    color: #666;
  }

  .noticeList {
    background-color: #eee;
  }

  .noticeList td:nth-child(1) p {
    color: #ff4e59;
    font-weight: bold;
    border: 1px solid #ffc6c9;
    background-color: #ffe3e4;
    border-radius: 3px;
    padding: 3px 10px;
    text-align: center;
  }

  .noticeList td:nth-child(2) > a {
    color: #ff4e59;
    font-weight: bold;
  }





  .cardType {
    width: 100%;
  }

  .cardType>div {
    border-bottom: 1px solid #ddd;
    padding: 16px 0;
    display: flex;
    justify-content: space-between;
  }
  
  .cardType>div>div>div:nth-child(1)>a {
    display: block;
    font-size: 13px;
    color: #666;
    margin-bottom: 12px;
  }

  .cardType>div>div>div:nth-child(2)>a {
    display: block;
    font-size: 16px;
    margin-bottom: 6px;
  }

  .cardType>div>div>div:nth-child(3)>a {
    display: block;
    font-size: 14px;
    color: #666;
    margin-bottom: 23px;
  }

  .cardType>div>div>div:nth-child(4) {
    display: flex;
    gap: 10px;
  }

  .cardType>div>div>div:nth-child(4)>div {
    font-size: 13px;
    color: #666;
  }







  .albumType {
    display: grid;
    grid-template-columns: 1fr 1fr 1fr 1fr;
    gap: 20px;
    width: 100%;
  }

  .albumType>div>div:nth-child(1) {
    width: 200px;
    height: 200px;
    margin-bottom: 8px;
  }

  .albumType>div>div:nth-child(2)>a {
    display: block;
    font-size: 13px;
    margin-bottom: 7px;
  }

  .albumType>div>div:nth-child(3)>a {
    font-size: 13px;
    color: #666;
  }

  .albumType>div>div:nth-child(4) {
    display: flex;
    gap: 10px;
  }

  .albumType>div>div:nth-child(4)>div {
    font-size: 13px;
    color: #666;
  }









  .pagination {
    margin-top: 40px;
    width: 100%;
    display: flex;
    justify-content: center;
    gap: 5px;
    background-color: #eee;
    padding: 30px;
  }

  .pagination .page_btn > a {
    display: block;
    width: 24px;
    height: 24px;
    font-size: 13px;
    text-align: center;
    line-height: 22px;
  }

  .pagination .next_btn > a{
    display: block;
    font-size: 13px;
    line-height: 22px;
    color: #666;
    margin-left: 15px;
  }

  .pagination .next_btn {
    position: relative;
  }

  .pagination .next_btn::before {
    display: block;
    position: absolute;
    top: 5px;
    left: 4px;
    content: '';
    width: 1px;
    height: 12px;
    background: #ddd;
  }

  .pagination .next_btn::after {
    display: block;
    position: absolute;
    top: 4.5px;
    right: -14px;
    content: '';
    width: 10px;
    height: 20px;
    background-image: url(./assets/sp_icon_white_72ca546e.svg);
    background-position: -56px -38px;
  }

  .pagination .prev_btn > a{
    display: block;
    font-size: 13px;
    line-height: 22px;
    color: #666;
    margin-right: 15px;
  }

  .pagination .prev_btn {
    position: relative;
  }

  .pagination .prev_btn::before {
    display: block;
    position: absolute;
    top: 5px;
    right: 4px;
    content: '';
    width: 1px;
    height: 12px;
    background: #ddd;
  }

  .pagination .prev_btn::after {
    display: block;
    position: absolute;
    top: 4.5px;
    left: -14px;
    content: '';
    width: 10px;
    height: 20px;
    background-image: url(./assets/sp_icon_white_72ca546e.svg);
    background-position: -76px -66px;
  }

  .pagination .active {
    font-weight: bold;
    border: 1px solid #ddd;
    background: #fff;
    color: #03c75a;
  }

  .pagination a:hover {
    text-decoration: underline;
  }








  .searchBox2 {
    background: #eee;
    display: flex;
    justify-content: center;
    gap: 10px;
    padding-bottom: 30px;
    margin-bottom: 200px;
  }

  .typeSelect {
    font-size: 14px;
    background: #fff;
    border-radius: 5px;
  }
  .typeSelect option {
    font-size: 14px;
  }
  .typeSelect {
    width: 200px;
    height: 36px;
    border: 1px solid #ddd;
    position: relative;
    cursor: pointer;
  }

  .typeSelect .selected {
    display: flex;
  }

  .typeSelect .selected .selected-value {
    width: 180px;
    height: 36px;
    font-size: 14px;
    color: #333;
    line-height: 24px;
    padding: 5px 10px;
  }
  .typeSelect ul {
    display: none;
    border: 1px solid #ddd;
    width: 200px;
    position: absolute;
    top: 36px;
    left: -1px;
    border-radius: 5px;

  }

  .typeSelect ul li {
    width: 100%;
    height: 36px;
    font-size: 14px;
    color: #333;
    line-height: 24px;
    padding: 5px 10px;
    background: #fff;
  }
  .typeSelect ul li:first-child {
    border-top-left-radius: 5px;
    border-top-right-radius: 5px;
  }
  .typeSelect ul li:last-child {
    border-bottom-left-radius: 5px;
    border-bottom-right-radius: 5px;
  }

  .typeSelect ul li:hover {
    text-decoration: underline;
    background: #E2FAEA;
    color: #03C75A;
  }
  .searchBox2 input {
    border: 1px solid #ddd;
    width: 260px;
    height: 36px;
    border-top-left-radius: 5px;
    border-bottom-left-radius: 5px;
    padding: 0 10px;
    font-size: 14px;
  }

  .searchBox2 .arrow {
    width: 10px;
    height: 10px;
    background-image: url(./assets/sp_icon_white_72ca546e.svg);
    background-position: -70px -48px;
    margin-top: 12px;
    margin-left: -2px;
  }

  .searchBox2 .reverseArrow {
    width: 10px;
    height: 10px;
    background-image: url(./assets/sp_icon_white_72ca546e.svg);
    background-position: -102px -86px;
    margin-top: 12px;
    margin-left: -2px;
  }
  .searchBox2 button {
    width: 40px;
    height: 36px;
    background: #03C75A;
    border: none;
    border-top-right-radius: 5px;
    border-bottom-right-radius: 5px;
    margin-left: -10px;
    cursor: pointer;
  }
  .searchBox2 button .searchIcon {
    font-size: 16px;
  }











  

























</style>

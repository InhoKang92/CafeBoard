<template>
    <div class="bodyLeft">
      <h3 class="boardTitle">{{ boardNameShow }}</h3>
      <div class="boardInfo">
        <p v-bind:style="{opacity: totalShow? '100%' : '0%'}"><span>{{ totalFormat }}</span>개의 글</p>
        <div class="boardInfoRight">
          <div>
            <input id="noticeHideCheck" type="checkbox" v-model="isChecked">
            <label class="noticeHide" for="noticeHideCheck" v-bind:class="{noticeHided: isChecked}">공지 숨기기</label>
          </div>
          <div class="displayType">
            <button v-bind:class="{typeActive: cardType}" @click="onCardType">카드형</button>
            <button v-bind:class="{typeActive: albumType}" @click="onAlbumType">앨범형</button>
            <button v-bind:class="{typeActive: listType}" @click="onListType">목록형</button>
            <div class="amountSelect" v-show="listType" name="amountSelect">
              <div class="selected" @click="amountShow">
                <div class="selected-value" ref="selectedValue"><span>{{ changedAmount }}</span>개씩</div>
                <div class="arrow" v-bind:class="{reverseArrow: isShow}"></div>
              </div>
              <ul v-bind:style="{display: isShow? 'block' : 'none'}">
                <li class="options" value="5" @click="amountChange">5개씩</li>
                <li class="options" value="10" @click="amountChange">10개씩</li>
                <li class="options" value="15" @click="amountChange">15개씩</li>
                <li class="options" value="20" @click="amountChange">20개씩</li>
              </ul>
            </div>
          </div>
        </div>
      </div>
      <table>
        <thead>
          <tr>
            <th></th>
            <th>제목</th>
            <th>작성자</th>
            <th>작성일</th>
            <th>조회수</th>
          </tr>
        </thead>
        <tbody>
          <tr v-bind:style="{display: isChecked? 'none' : ''}" class="noticeList" v-for="notice in noticeList" :key="notice.bno">
            <td><p>{{ notice.classification }}</p></td>
            <td><router-link :to="{ path: '/ReadBoard', query: { bno: notice.bno, boardName: notice.boardName } }"><span>[{{ notice.classification }}]</span> {{ notice.title }}</router-link></td>
            <td><a href="">{{ notice.writer }}</a></td>
            <td>{{ notice.regDate }}</td>
            <td>{{ notice.readCount }}</td>
          </tr>
          <tr v-for="board in boardList" :key="board.bno" class="listType" v-show="listType">
            <td><a href="#">{{ board.boardName }}</a></td>
            <td><router-link :to="{ path: '/ReadBoard', query: { bno: board.bno } }"><span>[{{ board.classification }}]</span> {{ board.title }} <span style="color: #ff2f3b; font-weight: bold;">[{{ board.replyCount }}]</span></router-link></td>
            <td><a href="#">{{ board.writer }}</a></td>
            <td>{{ board.regDate }}</td>
            <td>{{ board.readCount }}</td>
          </tr>
        </tbody>
      </table>

      <div class="cardType" v-show="cardType">
        <div v-for="board in boardList" :key="board.bno">
          <div>
            <div><a href="">{{ board.boardName }}</a></div>
            <div><router-link :to="{ path: '/ReadBoard', query: { bno: board.bno } }"><span>[{{ board.classification }}]</span> {{ board.title }}</router-link></div>
            <div><a href="">{{ board.content }}</a></div>
            <div>
              <div>{{ board.writer }}</div>
              <div>{{ board.regDate }}</div>
              <div>조회 {{ board.readCount }}</div>
            </div>
          </div>
          <div>
            <img src="../assets/logo.png" alt="" width="120px" height="120px">
          </div>
        </div>
      </div>

      <div class="albumType" v-show="albumType">
        <div v-for="board in boardList" :key="board.bno">
          <div>
            <img src="../assets/logo.png" alt="" width="200px" height="200px">
          </div>
          <div><router-link :to="{ path: '/ReadBoard', query: { bno: board.bno } }"><span>[{{ board.classification }}]</span> {{ board.title }}</router-link></div>
          <div><a href="">{{ board.writer }}</a></div>
          <div>
            <div>{{ board.regDate }}</div>
            <div>조회 {{ board.readCount }}</div>
          </div>
        </div>
      </div>
      

      <div>
        <ul class="pagination">
					<li class="prev_btn" v-bind:style="{display: prev? 'block' : 'none'}"><a href="javascript:void(0);" @click="prevPage">이전</a></li>
					<li class="page_btn" v-for="pageNum in pages" :key="pageNum"><a href="javascript:void(0);" @click="pageChange" v-bind:class="{'active': pageNum === this.pageNum}">{{ pageNum }}</a></li>
					<li class="next_btn" v-bind:style="{display: next? 'block' : 'none'}"><a href="javascript:void(0);" @click="nextPage">다음</a></li>
        </ul>
      </div>

      <!-- 검색조건 -->
      <div>
        <div class="searchBox2">
          <div class="typeSelect" name="typeSelect">
            <div class="selected" @click="typeShow">
              <div class="selected-value" ref="selectedType">{{ changedType }}</div>
              <div class="arrow" v-bind:class="{reverseArrow: typeIsShow}"></div>
            </div>
            <ul v-bind:style="{display: typeIsShow? 'block' : 'none'}">
              <li class="options" value="C" @click="typeChange">글 + 댓글</li>
              <li class="options" value="T" @click="typeChange">제목만</li>
              <li class="options" value="W" @click="typeChange">글작성자</li>
            </ul>
          </div>
          <input ref="keyword" type='text' name='keyword' placeholder="검색어를 입력해주세요"/>
          <button @click="boardSearch"><font-awesome-icon class="searchIcon" icon="magnifying-glass" /></button>
        </div>
      </div>
      <!-- end 검색조건 -->  

    </div>
</template>
<script>
import axios from 'axios';
import dayjs from 'dayjs';

axios.defaults.baseURL = 'http://localhost:8087';

export default {
  data() {
    return {
      boardList: [],
      noticeList: [],
      total: 0,
      totalFormat: '',
      selectedAmount: '15',
      pageNum: 1,
      amount: 15,
      isChecked: false,
      pages: [],
      endPage: 0,
      startPage: 0,
      prev: false,
      next: false,
      isShow: false,
      changedAmount : 15,
      cardType: false,
      albumType: false,
      listType: true,
      boardName: '',
      boardNameShow: '',
      totalShow: false,
      type: '',
      keyword: '',
      selectedType: '',
      changedType: '검색 유형 선택',
      typeIsShow: false,
      replyCountList: [],
    }
  },
  created() {
    if(this.$route.query.boardName !== ''){
      this.boardNameShow = this.$route.query.boardName;
    }else {
      this.boardNameShow = '전체글보기';
      this.totalShow = true;
    }

    this.getTotal();
    this.getBoardList();
    this.getNoticeList();
  },
  mounted() {
    document.addEventListener('click', this.handleClick);
  },
  watch: {
    '$route.query.boardName'(newBoardName) {
      this.boardName = newBoardName;
      this.type= '';
      this.keyword= '';
      if(this.$route.query.boardName !== ''){
        this.boardNameShow = this.$route.query.boardName;
        this.totalShow = false;
      }else {
      this.boardNameShow = '전체글보기';
      this.totalShow = true;
    }
      this.getBoardList();  // 새 boardName에 맞게 데이터를 다시 불러오기
    }
  },
  methods: {
    amountChange(e){
      this.amount = parseInt(e.target.getAttribute('value'));
      this.changedAmount = this.amount
      this.pageNum = 1;
      this.isShow = false;
      this.getBoardList();
    },
    typeChange(e){
      this.type = e.target.getAttribute('value');
      if(this.type == 'C'){
        this.changedType = '글 + 댓글';
      }else if(this.type == 'T'){
        this.changedType = '제목만';
      }else if(this.type == 'W'){
        this.changedType = '글작성자';
      }
      this.typeIsShow = false;
    },
    async updateReplyCounts() {
    // 비동기 처리를 기다린 후 실행
    for (let i = 0; i < this.boardList.length; i++) {
      try {
        // axios 요청이 완료될 때까지 기다림
        const response = await axios.get('/replies/getTotalReplyCount?bno=' + this.boardList[i].bno);
        this.replyCountList.push(response.data);
        this.boardList[i].replyCount = response.data;
      } catch (error) {
        console.log(error);
      }
    }
    },
    getBoardList(){

      axios.get('/board/list?pageNum=' + this.pageNum + '&amount=' + this.amount + '&boardName=' + this.boardName + '&type=' + this.type + '&keyword=' + this.keyword)
      .then((response) => {
          this.boardList = response.data;
          for(var i=0; i<this.boardList.length; i++){
            this.boardList[i].regDate = dayjs(this.boardList[i].regDate).format('YYYY.MM.DD HH:mm');
            const currentDay = dayjs().format('YYYY.MM.DD HH:mm:ss');
            if(this.boardList[i].regDate.substr(0, 10) == currentDay.substr(0, 10)){
              this.boardList[i].regDate = this.boardList[i].regDate.substr(11, 5);
            }else {
              this.boardList[i].regDate = this.boardList[i].regDate.substr(0, 10);
            }
          }

          this.updateReplyCounts();

          this.endPage = parseInt(Math.ceil(this.pageNum / 10.0)) * 10;
          this.startPage = this.endPage - 9;
          const realEnd = parseInt(Math.ceil((this.total * 1.0) / this.amount));
          if (realEnd <= this.endPage) {
            this.endPage = realEnd;
          }
          this.prev = this.startPage > 1;
          this.next = this.endPage < realEnd;

          this.pages = [];

          for(var j=this.startPage; j<this.endPage+1; j++){
            this.pages.push(j);
          }

        })
        .catch(error => {
          console.log(error);
        });
    },
    getNoticeList(){
      axios.get('/board/noticeList')
      .then((response) => {
          this.noticeList = response.data;
          for(var i=0; i<this.noticeList.length; i++){
            this.noticeList[i].regDate = dayjs(this.noticeList[i].regDate).format('YYYY.MM.DD HH:mm');
            const currentDay = dayjs().format('YYYY.MM.DD HH:mm:ss');
            if(this.noticeList[i].regDate.substr(0, 10) == currentDay.substr(0, 10)){
              this.noticeList[i].regDate = this.noticeList[i].regDate.substr(11, 5);
            }else {
              this.noticeList[i].regDate = this.noticeList[i].regDate.substr(0, 10);
            }
          }
        })
        .catch(error => {
          console.log(error);
        });
    },
    getTotal(){
      axios.get('/board/getTotal?pageNum=' + this.pageNum + '&amount=' + this.amount + '&boardName=' + this.boardName + '&type=' + this.type + '&keyword=' + this.keyword)
      .then((response) => {
          this.total = response.data;
          this.totalFormat = response.data.toLocaleString();
        })
        .catch(error => {
          console.log(error);
        });
    },
    pageChange(e){
      this.pageNum = parseInt(e.target.innerHTML);
      this.getBoardList();
    },
    prevPage(){
      this.pageNum = this.startPage - 1;
      this.getBoardList();
      this.paginationUpdate();
    },
    nextPage(){
      this.pageNum = this.endPage + 1;
      this.getBoardList();
      this.paginationUpdate();
    },
    paginationUpdate(){
      this.endPage = parseInt(Math.ceil(this.pageNum / 10.0)) * 10;
      this.startPage = this.endPage - 9;
      const realEnd = parseInt(Math.ceil((this.total * 1.0) / this.amount));
      if (realEnd <= this.endPage) {
        this.endPage = realEnd;
      }
      this.prev = this.startPage > 1;
      this.next = this.endPage < realEnd;

      this.pages = [];

      for(var j=this.startPage; j<this.endPage+1; j++){
        this.pages.push(j);
      }
    },
    amountShow(){
      this.isShow = !this.isShow;
      console.log(this.isShow);
    },
    typeShow(){
      this.typeIsShow = !this.typeIsShow;
      console.log(this.typeIsShow);
    },
    onCardType(){
      this.amount = 10;
      this.changedAmount = 10;
      if(this.albumType){
        this.albumType = !this.albumType;
      }
      if(this.listType){
        this.listType = !this.listType;
      }
      this.cardType = !this.cardType;
      this.getBoardList();
    },
    onAlbumType(){
      this.amount = 20;
      this.changedAmount = 20;
      if(this.cardType){
        this.cardType = !this.cardType;
      }
      if(this.listType){
        this.listType = !this.listType;
      }
      this.albumType = !this.albumType;
      this.getBoardList();
    },
    onListType(){
      if(this.albumType){
        this.albumType = !this.albumType;
      }
      if(this.cardType){
        this.cardType = !this.cardType;
      }
      this.listType = !this.listType;
    },
    handleClick(e){
      if(e.target !== this.$refs.selectedValue){
        if(this.isShow){
          this.isShow = false;
        }
      }
      if(e.target !== this.$refs.selectedType){
        if(this.typeIsShow){
          this.typeIsShow = false;
        }
      }
    },
    boardSearch(){
      this.keyword = this.$refs.keyword.value;
      this.pageNum = 1;
      this.getTotal();
      this.getBoardList();
    }
  },
  getTotalReplyCount(bno){
            axios.get('/replies/getTotalReplyCount?bno=' + bno)
                .then((response) => {
                    this.replyCount = response.data;
                })
                .catch(error => {
                    console.log(error);
                });
        },
}
</script>
<style>
  
</style>
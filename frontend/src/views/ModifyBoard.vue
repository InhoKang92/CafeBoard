<template>
    <div class="bodyLeft">
        <div class="writeHeader">
            <h3 class="boardTitle">게시글 수정</h3>
            <div class="writeButtons">
                <button class="temp_register">임시등록</button>
                <button class="register" @click="modify">수정</button>
            </div>
        </div>
        <div class="writeTopBox">
            <div>
                <div class="boardSelect" name="boardSelect">
                    <div class="selected" ref="selected1" @click="boardSelectShow1">
                        <div class="selected-value" ref="selectedValue1">{{ selectedValue1 }}</div>
                        <div class="arrow" v-bind:class="{reverseArrow: isShow1}"></div>
                    </div>
                    <ul v-bind:style="{display: isShow1? 'block' : 'none'}">
                        <li class="options" value="공지사항" @click="boardChange1">공지사항</li>
                        <li class="options" value="뉴스게시판" @click="boardChange1">뉴스게시판</li>
                        <li class="options" value="토론게시판" @click="boardChange1">토론게시판</li>
                        <li class="options" value="자유게시판" @click="boardChange1">자유게시판</li>
                    </ul>
                </div>
                <div class="boardSelect" name="boardSelect">
                    <div class="selected" @click="boardSelectShow2">
                        <div class="selected-value" ref="selectedValue2" style="color: #000;">{{ selectedValue2 }}</div>
                        <div class="arrow" ref="arrow2" v-bind:class="{reverseArrow: isShow2}" style="opacity: 0.3;"></div>
                    </div>
                    <ul v-bind:style="{display: isShow2? 'block' : 'none'}">
                        <li v-for="option in boardSelect2" :key="option" class="options" :value="option" @click="boardChange2">{{ option }}</li>
                    </ul>
                </div>
            </div>
            <input v-model="title" type="text" placeholder="제목을 입력해주세요.">
        </div>
        <div class="writeBottomBox">
            <textarea v-model="content" name="" id="" placeholder="내용을 입력하세요."></textarea>
        </div>
    </div>
</template>
<script>
import axios from 'axios';
import dayjs from 'dayjs';

axios.defaults.baseURL = 'http://localhost:8087';

export default {
    data() {
        return {
            isShow1: false,
            isShow2: false,
            selectedValue1: '게시판을 선택해주세요.',
            selectedValue2: '말머리 선택',
            boardSelect2: [],
            boardName: '',
            classification: '',
            title: '',
            content: '',
            writer: 'user00',
            board: {},
        }
    },
    created() {
        this.readBoard(this.$route.query.bno);
    },
    mounted() {
        document.addEventListener('click', this.handleClick);
    },
    methods: {
        readBoard(bno){
            axios.get('/board/readBoard?bno=' + bno)
            .then((response) => {
                this.board = response.data;
                this.board.regDate = dayjs(this.board.regDate).format('YYYY.MM.DD HH:mm');
                this.title = this.board.title;
                this.content = this.board.content;
                this.selectedValue1 = this.board.boardName;
                this.selectedValue2 = this.board.classification;
            })
            .catch(error => {
                console.log(error);
            });
        },
        boardSelectShow1(){
            this.isShow1 = !this.isShow1;
        },
        boardSelectShow2(){
            this.isShow2 = !this.isShow2;
        },
        boardChange1(e){
            this.isShow1 = false;
            this.selectedValue1 = e.target.getAttribute('value');
            this.$refs.selectedValue2.style.color = 'black';
            this.$refs.arrow2.style.opacity = 1;
            this.selectedValue2 = '말머리 선택';
            if(e.target.getAttribute('value')=='공지사항'){
                this.boardSelect2 = ['공지', '필독', '일정'];
            }else if(e.target.getAttribute('value')=='뉴스게시판'){
                this.boardSelect2 = ['뉴스', '인터뷰', 'SNS', '기타'];
            }else if(e.target.getAttribute('value')=='토론게시판'){
                this.boardSelect2 = ['토크', '투표', '질문', '기타'];
            }else if(e.target.getAttribute('value')=='자유게시판'){
                this.boardSelect2 = ['일상', '유머', '게임', '기타'];
            }
        },
        boardChange2(e){
            this.isShow2 = false;
            this.selectedValue2 = e.target.getAttribute('value');
        },
        handleClick(e){
            if(e.target !== this.$refs.selectedValue2){
                if(this.isShow2){
                    this.isShow2 = false;
                }
            }
            if(e.target !== this.$refs.selectedValue1){
                if(this.isShow1){
                    this.isShow1 = false;
                }
            }
        },
        modify(){

            this.boardName = this.selectedValue1;
            this.classification = this.selectedValue2;

            console.log(this.boardName);
            console.log(this.classification);
            console.log(this.title);
            console.log(this.content);
            

            axios.post('/board/modify', {
                            bno: this.$route.query.bno,
                            boardName: this.boardName,
                            classification: this.classification,
                            title: this.title,
                            content: this.content,
                            writer: this.writer
            })
            .then((response) => {
                console.log('서버 응답:', response.data);
            })
            .catch(error => {
                console.error('서버 오류:', error);
            });

            this.$router.push({ path: '/AllBoard', query: { boardName: '' } });
        },
    },
}
</script>
<style>
    .writeHeader {
        width: 100%;
        display: flex;
        justify-content: space-between;
        border-bottom: 1px solid black;
        padding-bottom: 16px;
        margin-bottom: 22px;
    }
    .temp_register {
        width: 110px;
        height: 36px;
        border: 1px solid rgba(0, 0, 0, 0.2);
        border-radius: 6px;
        background: #fff;
        font-size: 13px;
        font-weight: bold;
        cursor: pointer;
    }
    .register {
        width: 50px;
        height: 36px;
        background: rgba(3, 199, 90, 0.12);
        color: #009f47;
        border: none;
        border-radius: 6px;
        margin-left: 12px;
        font-size: 13px;
        font-weight: bold;
        cursor: pointer;
    }
    .writeTopBox {
        width: 100%;
        padding: 20px 28px;
        border-radius: 12px;
        border: 1px solid #eee;
        background: #fff;
    }
    .writeTopBox div:first-child {
        display: flex;
        gap: 10px;
    }
    .boardSelect {
        flex: 1;
        border-bottom: 1px solid #ebecef;
        padding: 0 25px 0 11px;
        position: relative;
    }
    .boardSelect .selected {
        display: flex;
        justify-content: space-between;
        width: 100%;
        cursor: pointer;
    }
    .boardSelect .selected .selected-value {
        font-size: 16px;
        line-height: 48px;
    }
    .boardSelect .selected .arrow {
        width: 20px;
        height: 20px;
        background-image: url(../assets/sp_icon_white_72ca546e.svg);
        background-size: 1000%;
        background-repeat: no-repeat;
        background-position: -100px -68px;
        margin-top: 11px;
        margin-left: -2px;
    }
    .boardSelect .selected .reverseArrow {
        width: 20px;
        height: 20px;
        background-image: url(../assets/sp_icon_white_72ca546e.svg);
        background-size: 1000%;
        background-repeat: no-repeat;
        background-position: -100px -68px;
        margin-top: 17px;
        margin-right: -8px;
        transform: rotate(180deg);
    }
    .writeTopBox input {
        width: 100%;
        margin-top: 20px;
        border: none;
        background: #f5f6f8;
        font-size: 16px;
        padding: 13px 16px 11px;
        height: 48px;
        border-radius: 10px;
    }
    .writeBottomBox textarea {
        width: 100%;
        height: 500px;
        margin-top: 12px;
        border: 1px solid #eee;
        font-size: 14px;
        padding: 20px;
    }
    .boardSelect ul {
        border: 1px solid #ddd;
        width: 506px;
        position: absolute;
        top: 52px;
        left: 0px;
        padding: 6px 0;
    }
    .boardSelect ul li {
        width: 100%;
        height: 32px;
        font-size: 13px;
        color: #333;
        line-height: 26px;
        padding: 5px 10px;
        background: #fff;
        cursor: pointer;
    }
    .boardSelect ul li:hover {
        text-decoration: underline;
        background: #eee;
    }
</style>
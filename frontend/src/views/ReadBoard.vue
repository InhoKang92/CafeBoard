<template>
    <div class="bodyLeft">
        <div class="bottonBox">
            <div class="buttons">
                <router-link :to="{ path: '/ModifyBoard', query: { bno: board.bno } }">수정</router-link>
                <a href="#" @click="removeBoard(board.bno)">삭제</a>
            </div>
            <div class="buttons">
                <a href="#" @click="previousBoard">이전글</a>
                <a href="#" @click="nextBoard">다음글</a>
                <router-link :to="{ path: '/AllBoard', query: { boardName: board.boardName } }">목록</router-link>
            </div>
        </div>
        
        <div class="boardBody">
            <div class="boardName"><router-link :to="{ path: '/AllBoard', query: { boardName: board.boardName } }">{{ board.boardName }}</router-link> <span>></span></div>
            <div class="boardTitle"><span>[{{ board.classification }}]</span> {{ board.title }}</div>
            <div class="boardEtc">
                <div>
                    <div>{{ board.writer }}</div>
                    <div>
                        <p>{{ board.regDate }}</p>
                        <p>조회 {{ board.readCount }}</p>
                    </div>
                </div>
                <div>
                    <font-awesome-icon class="replyIcon" icon="comment-dots" /> 
                    <p>
                        댓글 <span>{{ replyCount }}</span>
                    </p>
                </div>   
            </div>
            <div>{{ board.content }}</div>
            <div class="imgBanner"><img src="../assets/readBoardImg.jpg" alt="" width="100%"></div>
            <div class="replyList">
                <div>
                    <button v-bind:style="{color: recentOrder? 'black' : '#b7b7b7'}" @click="replyListOrderChange">최신순</button>
                    <button v-bind:style="{color: registerOrder? 'black' : '#b7b7b7'}" @click="replyListOrderChange">등록순</button>
                </div>
                <ul>
                    <li v-for="(reply, index) in replyList" :key="reply.rno">
                        <div :ref="'reference'+index" v-bind:class="{modifyActive: reply.active}">
                            <div>{{ reply.replyer }}</div>
                            <div>{{ reply.reply }}</div>
                            <div>{{ reply.replyDate }}</div>
                        </div>
                        <div v-bind:class="{modifyActive: reply.inactive}">
                            <input type="text" v-model="replyModifyReplyer" readonly onfocus="this.blur()">
                            <textarea name="reply" id="" v-model="replyModifyContent" @keyup="modifyButtonActive"></textarea>
                            <div>
                                <button @click="modifyCancel" :id="'cancel'+index">취소</button>
                                <button :id="'modifyBtn'+index" :ref="'modifyBtn'+index" @click="replyModify">등록</button>
                            </div>
                        </div>
                        <div v-bind:class="{modifyActive: reply.active}">
                            <p @click="etcShow" :id="'p'+index">...</p>
                            <div v-bind:style="{display: reply.buttonShow? 'block' : 'none'}" ref="replyEtc" class="replyEtc">
                                <button :id="'modify'+index" @click="modify">수정</button>
                                <button @click="replyDelete" :id="'delete'+index">삭제</button>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
            <div class="replyWrite">
                <input type="text" placeholder="작성자 아이디를 입력하세요" v-model="replyer">
                <textarea name="reply" id="" placeholder="댓글을 남겨보세요" v-model="replyContent" @keyup="buttonActive"></textarea>
                <div>
                    <button ref="replyBtn" @click="replyRegister">등록</button>
                </div>
            </div>
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
            board: {},
            replyPage: 1,
            replyList: [],
            recentOrder: true,
            registerOrder: false,
            replyContent: '',
            replyer: '',
            replyCount: 0,
            isShow: false,
            replyModifyContent: '',
            replyModifyReplyer: '',
            replyModifyRno: 0,
            replyModifyBno: 0,
        }
    },
    created() {
        this.readBoard(this.$route.query.bno);
        this.getReplyList(this.$route.query.bno);
        this.getTotalReplyCount(this.$route.query.bno);
    },
    methods: {
        readBoard(bno){
            axios.get('/board/readBoard?bno=' + bno)
            .then((response) => {
                this.board = response.data;
                this.board.regDate = dayjs(this.board.regDate).format('YYYY.MM.DD HH:mm');
            })
            .catch(error => {
                console.log(error);
            });

            this.readCountUp(bno);
        },
        readCountUp(bno){
            axios.get('/board/readCountUp?bno=' + bno)
            .then((response) => {
                this.board.readCount = response.data;
            })
            .catch(error => {
                console.log(error);
            });
        },
        previousBoard(){
            const newBno = this.board.bno -1;
            this.readBoard(newBno);
        },
        nextBoard(){
            const newBno = this.board.bno +1;
            this.readBoard(newBno);
        },
        removeBoard(bno){
            if(confirm('게시글을 삭제하시겠습니까?')){
                axios.post('/board/remove?bno=' + bno)
                .then((response) => {
                    console.log(response);
                    this.$router.push({ path: '/AllBoard', query: { boardName: '' } });
                })
                .catch(error => {
                    console.log(error);
                });
            }
        },
        getReplyList(bno){
            axios.get('/replies/replyListDesc/' + bno)
                .then((response) => {
                    this.replyList = response.data;
                    for(var i=0; i<this.replyList.length; i++){
                        this.replyList[i].replyDate = dayjs(this.replyList[i].replyDate).format('YYYY.MM.DD HH:mm');
                    }
                    this.replyList.forEach((reply) => {
                        reply.buttonShow = false;
                        reply.active = false;
                        reply.inactive = true;
                    });
                })
                .catch(error => {
                    console.log(error);
                });
        },
        getReplyListAsc(bno){
            axios.get('/replies/replyListAsc/' + bno)
                .then((response) => {
                    this.replyList = response.data;
                    for(var i=0; i<this.replyList.length; i++){
                        this.replyList[i].replyDate = dayjs(this.replyList[i].replyDate).format('YYYY.MM.DD HH:mm');
                    }
                    this.replyList.forEach((reply) => {
                        reply.buttonShow = false;
                        reply.active = false;
                        reply.inactive = true;
                    });
                })
                .catch(error => {
                    console.log(error);
                });
        },
        replyListOrderChange(){
            this.recentOrder = !this.recentOrder;
            this.registerOrder = !this.registerOrder;
            if(this.recentOrder){
                this.getReplyList(this.$route.query.bno);
            }else if(this.registerOrder){
                this.getReplyListAsc(this.$route.query.bno);
            }
        },
        buttonActive(){
            console.log(this.$refs.replyBtn);
            if(this.replyContent!==''){
                this.$refs.replyBtn.style.color = '#03c75a';
                this.$refs.replyBtn.style.background = '#03c75a1f';
            }else{
                this.$refs.replyBtn.style.color = '#b7b7b7';
                this.$refs.replyBtn.style.background = 'none';
            }
        },
        modifyButtonActive(e){
            const string = e.target.id;
            const index = string.substr(9, 1);
            const button = this.$refs['modifyBtn' + index];
            console.log(button); // 이 버튼 요소를 참조
            console.log(this.replyModifyContent);
            // if(this.replyModifyContent!==''){
            //     this.$refs.modifyBtn0.style.color = '#03c75a';
            //     this.$refs.modifyBtn0.style.background = '#03c75a1f';
            // }else{
            //     this.$refs.modifyBtn0.style.color = '#b7b7b7';
            //     this.$refs.modifyBtn0.style.background = 'none';
            // }
        },
        replyRegister(){
            axios.post('/replies/new', {
                            bno: this.$route.query.bno,
                            reply: this.replyContent,
                            replyer: this.replyer
            })
            .then((response) => {
                console.log('서버 응답:', response.data);
            })
            .catch(error => {
                console.error('서버 오류:', error);
            });

            window.location.reload(true);
        },
        replyModify(){
            axios.put('/replies/' + this.replyModifyRno, {
                            bno: this.replyModifyBno,
                            reply: this.replyModifyContent,
                            replyer: this.replyModifyReplyer
            })
            .then((response) => {
                console.log('서버 응답:', response.data);
            })
            .catch(error => {
                console.error('서버 오류:', error);
            });

            window.location.reload(true);
        },
        replyDelete(e){
            const string = e.target.id;
            const index = string.substr(6, 1);
            this.replyModifyRno = this.replyList[index].rno;
            if(confirm('댓글을 삭제하시겠습니까?')){
                axios.delete('/replies/' + this.replyModifyRno)
                .then((response) => {
                    console.log('서버 응답:', response.data);
                })
                .catch(error => {
                    console.error('서버 오류:', error);
                });
                window.location.reload(true);
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
        etcShow(e){
            const string = e.target.id;
            const index = string.substr(1, 1);
            this.replyList[index].buttonShow = !this.replyList[index].buttonShow;
        },
        modify(e){
            const string = e.target.id;
            const index = string.substr(6, 1);
            this.replyList[index].active = !this.replyList[index].active;
            this.replyList[index].inactive = !this.replyList[index].inactive;
            this.replyModifyContent = this.replyList[index].reply;
            this.replyModifyReplyer = this.replyList[index].replyer;
            this.replyModifyRno = this.replyList[index].rno;
            this.replyModifyBno = this.replyList[index].bno;
        },
        modifyCancel(e){
            const string = e.target.id;
            const index = string.substr(6, 1);
            this.replyList[index].active = !this.replyList[index].active;
            this.replyList[index].inactive = !this.replyList[index].inactive;
            this.replyList[index].buttonShow = !this.replyList[index].buttonShow;
        }
    },  
}
</script>
<style>
    .bottonBox {
        display: flex;
        justify-content: space-between;
        margin-bottom: 14px;
    }
    .buttons {
        display: flex;
        gap: 10px;
    }

    .buttons a {
        position: relative;
        font-size: 13px;
        padding: 10px 12px;
        background: #eff0f2;
        border-radius: 5px;
        font-weight: bold;
    }

    .buttons:last-child a:nth-child(1) {
        padding-left: 28px;
    }

    .buttons:last-child a:nth-child(2) {
        padding-left: 28px;
    }

    .buttons:last-child a:nth-child(1)::before {
        display: block;
        position: absolute;
        top: 9px;
        left: 7px;
        content: '';
        width: 10px;
        height: 20px;
        background-image: url(../assets/sp_icon_white_72ca546e.svg);
        background-position: -76px -66px;
        transform: rotate(90deg) scale(0.8);
    }

    .buttons:last-child a:nth-child(2)::before {
        display: block;
        position: absolute;
        top: 8px;
        left: 12px;
        content: '';
        width: 10px;
        height: 20px;
        background-image: url(../assets/sp_icon_white_72ca546e.svg);
        background-position: -76px -66px;
        transform: rotate(-90deg) scale(0.8);
    }

    .boardBody {
        width: 100%;
        border: 1px solid #ddd;
        padding: 29px;
    }

    .boardBody .boardName {
        margin-bottom: 10px;
    }

    .boardBody .boardName a {
        color: #03c75a;
        font-size: 13px;
        cursor: pointer;
    }

    .boardBody .boardName span {
        color: #03c75a;
        font-size: 13px;
        font-weight: bold;
    }

    .boardBody .boardTitle {
        font-size: 26px;
        margin-bottom: 5px;
    }

    .boardBody .boardEtc {
        display: flex;
        justify-content: space-between;
        padding-bottom: 20px;
        margin-bottom: 20px;
        border-bottom: 1px solid #ddd;
    }

    .boardBody .boardEtc>div:first-child div:first-child {
        font-size: 13px;
    }

    .boardBody .boardEtc>div:first-child div:last-child {
        display: flex;
        gap: 8px;
    }

    .boardBody .boardEtc>div:first-child div:last-child p {
        font-size: 12px;
        color: #979797;
    }

    .boardBody .boardEtc>div:last-child {
        display: flex;
        align-items: flex-end;
    }

    .boardBody .boardEtc>div:last-child .replyIcon{
        font-size: 15px;
    }

    .boardBody .boardEtc>div:last-child p {
        display: inline-block;
        font-size: 13px;
        margin-left: 5px;
    }

    .boardBody .boardEtc>div:last-child p span {
        font-size: 14px;
        font-weight: bold;
    }

    .imgBanner {
        width: 100%;
        height: 134px;
        overflow: hidden;
        margin-top: 20px;
        border-radius: 10px;
    }

    .imgBanner img {
        margin-top: -255px;
    }

    .replyList {
        border-top: 1px solid #ddd;
        margin-top: 20px;
    }

    .replyList>div {
        display: flex;
        gap: 10px;
        margin-top: 20px;
        margin-bottom: 10px;
    }

    .replyList>div>button {
        border: none;
        background: none;
        font-size: 15px;
        font-weight: bold;
        color: #b7b7b7;
        cursor: pointer;
    }

    .replyList ul li>div:last-child p{
        font-size: 20px;
        color: #888;
        cursor: pointer;
        margin-top: -20px;
    }

    .replyList ul li>div:first-child div:nth-child(1) {
        font-size: 14px;
        font-weight: bold;
        margin-bottom: 4px;
    }

    .replyList ul li>div:first-child div:nth-child(2) {
        font-size: 15px;
        margin-bottom: 4px;
    }

    .replyList ul li>div:first-child div:nth-child(3) {
        font-size: 13px;
        color: #979797;
    }

    .replyList ul li {
        border-bottom: 1px solid #ddd;
        padding: 15px 0;
        display: flex;
        justify-content: space-between;
    }

    .replyList ul li:last-child {
        border-bottom: none;
    }

    .replyWrite {
        width: 100%;
        border: 2px solid #ddd;
        border-radius: 10px;
        margin-top: 20px;
        padding: 20px 10px;
    }

    .replyWrite input {
        border: none;
        background: none;
        width: 100%;
        font-size: 14px;
        font-weight: bold;
        padding: 0 10px;
        margin-bottom: 10px;
        outline-color: #fff;
    }

    .replyWrite textarea {
        border: none;
        padding: 0 10px;
        width: 100%;
        outline-color: #fff;
        font-size: 13px;
        resize: none;
    }

    .replyWrite div {
        display: flex;
        justify-content: flex-end;
    }

    .replyWrite div button {
        display: block;
        width: 46px;
        height: 34px;
        border-radius: 6px;
        background: none;
        border: none;
        font-size: 13px;
        font-weight: bold;
        color: #b7b7b7;
        margin-right: 10px;
        cursor: pointer;
    }

    .replyList>ul>li>div:last-child {
        position: relative;
    }

    .replyEtc {
        position: absolute;
        top: 20px;
        left: -88px;
        width: 102px;
        height: 82px;
        border: 1px solid rgba(0,0,0,.06);
        background: #fff;
        border-radius: 6px;
        padding: 7px 0;
        z-index: 100;
        box-shadow: 0 1px 12px 0 rgba(0,0,0,.06);
    }

    .replyEtc button {
        display: block;
        border: none;
        background: none;
        width: 100%;
        height: 50%;
        text-align: left;
        padding: 0 10px;
        font-size: 13px;
    }

    .replyEtc button:hover {
        background: #f6f6f6;
        text-decoration: underline;
    }

    .modifyActive {
        display: none;
    }

    .replyList>ul>li>div:nth-child(2) {
        width: 100%;
        border: 2px solid #ddd;
        border-radius: 10px;
        padding: 20px 10px 10px;
    }

    .replyList>ul>li>div:nth-child(2) input {
        border: none;
        background: none;
        font-size: 14px;
        font-weight: bold;
        padding: 0 10px;
        margin-bottom: 10px;
    }

    .replyList>ul>li>div:nth-child(2) textarea {
        border: none;
        padding: 0 10px;
        width: 100%;
        outline-color: #fff;
        font-size: 13px;
        resize: none;
    }

    .replyList>ul>li>div:nth-child(2) div {
        display: flex;
        justify-content: flex-end;
    }

    .replyList>ul>li>div:nth-child(2) div button:first-child {
        display: block;
        width: 46px;
        height: 34px;
        border-radius: 6px;
        background: none;
        border: none;
        font-size: 13px;
        font-weight: bold;
        color: #b7b7b7;
        cursor: pointer;
    }

    .replyList>ul>li>div:nth-child(2) div button:last-child {
        display: block;
        width: 46px;
        height: 34px;
        border-radius: 6px;
        background: #03c75a1f;
        border: none;
        font-size: 13px;
        font-weight: bold;
        color: #03c75a;
        margin-right: 10px;
        cursor: pointer;
    }

</style>
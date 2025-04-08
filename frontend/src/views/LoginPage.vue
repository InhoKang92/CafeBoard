<template>
    <div class="bodyLeft">
        <div class="loginBox">
            <h3>LOGIN</h3>
            <div>
                <input ref="userid" type="text" placeholder="아이디">
                <input ref="userpw" type="password" placeholder="비밀번호">
                <button @click="login">로그인</button>
            </div>
            <div>
                <a href="#">비밀번호 찾기</a>
                <a href="#">아이디 찾기</a>
                <a href="#">회원가입</a>
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
            
        }
    },
    methods: {
        
    },
}
</script>
<style>
    .loginBox {
        width: 50%;
        margin: 0 auto;
    }
    .loginBox h3 {
        text-align: center;
        font-size: 30px;
        margin: 20px 0;
        color: #666;
    }
    .loginBox div:nth-child(2) {
        width: 100%;
        border: 1px solid #ccc;
        border-radius: 10px;
        padding: 40px;
        margin-bottom: 20px;
    }
    .loginBox div:nth-child(2) input {
        display: block;
        width: 100%;
        font-size: 18px;
        padding: 20px;
        border: 1px solid #ccc;
    }
    .loginBox div:nth-child(2) input:first-child {
        border-top-left-radius: 10px;
        border-top-right-radius: 10px;
    }
    .loginBox div:nth-child(2) input:nth-child(2) {
        border-bottom-left-radius: 10px;
        border-bottom-right-radius: 10px;
        border-top: none;
    }
    .loginBox div:nth-child(2) button:last-child {
        width: 100%;
        border-radius: 10px;
        margin-top: 20px;
        font-size: 18px;
        padding: 14px;
        border: none;
        background: #aaa;
        color: #fff;
        font-weight: bold;
        cursor: pointer;
    }
    .loginBox div:nth-child(3) {
        width: 100%;
        display: flex;
        gap: 40px;
        justify-content: center;
    }
    .loginBox div:nth-child(3) a {
        font-weight: bold;
        color: #999;
        position: relative;
    }
    .loginBox div:nth-child(3) a+::before {
        display: block;
        content: '';
        width: 1px;
        height: 12px;
        background: #ccc;
        position: absolute;
        top: 6px;
        left: -20px;
    }
</style>
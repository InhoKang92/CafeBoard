<template>
    <div>
      <h2>로그인</h2>
      <form @submit.prevent="login">
        <input v-model="username" placeholder="Username" />
        <input v-model="password" type="password" placeholder="Password" />
        <button type="submit">로그인</button>
      </form>
      <p v-if="error">로그인 실패</p>
    </div>
  </template>
  
  <script>
  import axios from "axios";
  
  export default {
    data() {
      return {
        username: "",
        password: "",
        error: false
      };
    },
    methods: {
      login() {
        axios
          .post("http://localhost:8087/login", {
            username: this.username,
            password: this.password
          })
          .then((response) => {
            localStorage.setItem("token", response.data.token); // 토큰 저장
            this.$router.push("/home"); // 로그인 후 홈으로 리다이렉트
          })
          .catch((error) => {
            this.error = true; // 로그인 실패 시 오류 처리
          });
      }
    }
  };
  </script>
  
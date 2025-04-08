<template>
    <div>
        member
    </div>
</template>
<script>
import axios from 'axios';

axios.defaults.baseURL = 'http://localhost:8087';

export default {
    created() {
      this.getMember();
    },
    methods: {
      getMember(){
        axios.get('/sample/member')
        .then((response) => {
          console.log(12345);

            console.log(response);
        })
        .catch(error => {
          console.log(67890);

          console.log(error);
        });
      },
    },
}
</script>
<style>
    
</style>
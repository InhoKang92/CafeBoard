<template>
    <div>
        admin
    </div>
</template>
<script>
import axios from 'axios';

axios.defaults.baseURL = 'http://localhost:8087';

export default {
    created() {
      this.getAdmin();
    },
    methods: {
      getAdmin(){
        axios.get('/sample/admin')
        .then((response) => {
            console.log(response);
        })
        .catch(error => {
          console.log(error);
        });
      },
    },
}
</script>
<style>
    
</style>
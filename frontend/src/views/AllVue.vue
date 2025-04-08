<template>
    <div>
        all
    </div>
</template>
<script>
import axios from 'axios';

axios.defaults.baseURL = 'http://localhost:8087';

export default {
    created() {
      this.getAll();
    },
    methods: {
      getAll(){
        axios.get('/sample/all')
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
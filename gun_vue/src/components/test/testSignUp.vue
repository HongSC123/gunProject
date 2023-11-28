<template>
  <span class="p-float-label">
    <InputText type="email" id="email" v-model="email" />
    <label for="email">email</label>
    <Button label="인증발송" class="p-mr-2 p-mb-2" @click="fnEmailCheck"></Button>
  </span>

  
</template>

<script>
export default {
  name: "testSignUp",
  data() {
    return {
      email: null,
    };
  },
  methods: {
    fnEmailCheck(){
      if(this.email == null){
        alert("메일 없음");
        return;
      }
      else{
        alert("인증 누름?"+ this.email);
        this.$axios.post(this.$serverURL + "/email/" + this.email)
        .then((res) => {
          console.log('전송 성공' + typeof(res.data));
          console.log(res.data);
          
        })
        .catch((err) => {
          console.log('전송 실패' + err);
          return;
        });
      }
      

    },
  }
};
</script>

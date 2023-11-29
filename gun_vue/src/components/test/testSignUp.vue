<template>
  <div class="card">
    <h5>메일 인증</h5>
    <div class="p-formgroup-inline">
      <div>
        <label for="email1">Email</label>
        <InputText
          id="email1"
          type="text"
          v-model="email"
          :disabled="emailInput"
          placeholder="ex) ABCD@example.com"
        />
      </div>
      <div>
        <Button
          label="인증발송"
          class="p-mr-2 p-mb-2"
          @click="fnEmailCheck"
          :disabled="emailInput"
        ></Button>
      </div>
    </div>
    <div v-show="codeView">
      <h5>인증 확인</h5>
      <div class="p-formgroup-inline">
        <div>
          <label for="code">인증번호</label>
          <InputText
            id="code"
            type="text"
            v-model="code"
            :disabled="codeInput"
            placeholder="인증코드"
          />
        </div>
        <div>
          <Button
            label="인증확인"
            class="p-mr-2 p-mb-2"
            @click="fnCodeCheck(this.code)"
            :disabled="codeInput"
          ></Button>
        </div>
      </div>
    </div>

    <div id="pwbox" v-show="PwboxView">
      <h5>암호 확인</h5>
      <div class="p-formgroup-inline">
        <div>
          <label for="password1">암호</label>
          <InputText
            id="password1"
            type="password"
            v-model="password"
            placeholder="password"
            @input="fnPwCheck"
          />
        </div>
        <div>
          <label for="password2">암호확인</label>
          <InputText
            id="password2"
            type="password"
            placeholder="password"
            v-model="password2"
            @input="fnPwCheck"
          />
        </div>
        <div>
          <Button
            label="다음"
            class="p-mr-2 p-mb-2"
            @click="fnJoinNext(this.joinPw)"
          ></Button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "testSignUp",
  data() {
    return {
      serverCode: null,
      email: null,
      code: null,
      emailInput: false,
      codeInput: false,
      PwboxView: true,
      codeView: false,
      pwCheck: null,
      joinPw: false,
    };
  },
  methods: {
    fnJoinNext() {
      if (this.joinPw) {
        alert("비밀번호 일치");
      } else {
        alert("불일치");
      }
    },
    fnPwCheck() {
      if (this.password === this.password2) {
        this.joinPw = true;
      } else {
        this.joinPw = false;
      }
    },
    fnCodeView() {
      this.codeView = true;
    },
    fnPwboxView() {
      this.PwboxView = true;
      this.codeView = false;
    },
    fnCodeCheck(code) {
      this.fnPwboxView();
      // alert("인증확인!" + code);
      if (this.serverCode == code) {
        alert("코드 일치");
        this.codeInput = true;
      } else {
        alert("코드 불일치");
      }
    },
    fnEmailCheck() {
      if (this.email == null) {
        alert("메일 없음");
        return;
      } else {
        this.emailInput = true;
        this.fnCodeView();
        // alert("인증 누름?"+ this.email);
        this.$axios
          .post(this.$serverURL + "/email/" + this.email)
          .then((res) => {
            this.serverCode = res.data;
          })
          .catch((err) => {
            console.log("전송 실패" + err);
            return;
          });
      }
    },
  },
};
</script>

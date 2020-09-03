<template>
  <div class="updatePassword animated flipInY">
    <el-input v-model="formdata.oldPassWord" class="input" type="password" placeholder="old"
              style="width:90%;margin: 4px 14px;margin-top: 20px"></el-input>
    <el-input v-model="formdata.newPassWord" class="input" placeholder="new" type="password" style="width:90%;margin: 4px 14px;"></el-input>
    <el-input v-model="formdata.replayNewPW" class="input" placeholder="re new" type="password" style="width:90%;margin: 4px 14px;"></el-input>
    <el-button type="primary" round style="margin:5px 9px;width: 110px" @click="updatePW">ok</el-button>
    <el-button type="info" round style="width: 100px" @click="cancel">cancel</el-button>
  </div>
</template>

<script>
  export default {
    name: "app-userinfo",
    data() {
      return {
        formdata: {
          oldPassWord: '',
          newPassWord: '',
          replayNewPW: ''
        }
      }
    },
    methods: {
      updatePW() {

        if(this.formdata.newPassWord!=this.formdata.replayNewPW){
            this.$message.warning("tow password mast be same")
        }else {
          var params = {
            oldPassword: this.formdata.oldPassWord,
            newPassword: this.formdata.newPassWord
          }
          // updatePassword
          this.$api.updatePassword(params).then(result => {
            let data = this.$config.executeResult(result.data)
            if (data) {
              this.$message.success("success")
              this.$router.push({name: 'login'})
            }
          })
        }
      },
      cancel(){
        this.$emit("cancel")
      }
    }
  }
</script>

<style scoped>
  .updatePassword {
    position: absolute;
    right: 3px;
    z-index: 1000;
    width: 250px;
    height: 80%;
    border: 1px solid #ccc;
    background-color: white;
    box-shadow: -6px 8px 7px -6px #888888;
  }
  .input{
    padding-bottom: 20px;
  }
</style>

<template>
<div class="fullscreen">
  <div class="register-box">
    <div style="text-align: center">
      <h1>Online Appointment Booking System</h1>
    </div>
    <p class="text-tips">welcome to register</p>
    <el-form :rules="rules"  action="" class="login-form">
      <div class="m-list-group">
        <div class="m-list-group-item">
          <el-input type="text" placeholder="Username" v-model="user.loginName" class="m-input"></el-input>
        </div>
        <div class="m-list-group-item">
          <el-input type="password" prop="password" placeholder="Password" v-model="user.password" class="m-input"></el-input>
        </div>
        <div class="m-list-group-item">
          <el-input type="text" placeholder="name" v-model="user.name" class="m-input"></el-input>
        </div>
        <div class="m-list-group-item">
          <el-input type="text" placeholder="phone" v-model="user.phone" class="m-input"></el-input>
        </div>
        <div class="m-list-group-item">
          <el-input type="text" placeholder="address" v-model="user.address" class="m-input"></el-input>
        </div>
      </div>
      <button @click.prevent="handleRegister" class="m-btn sub select-none">sign up</button>
      <p class="text-tips">has user?</p>
      <a href="/login" class="m-btn m-btn-text">to login</a>
    </el-form>
  </div>
</div>
</template>
<script>
export default {
  name: 'register',
  data () {
    return {
      user: {
        loginName: '',
        password: '',
        name: '',
        phone: '',
        address: '',
      },
      rules:{
        password:[
          {required: true,trigger: 'blur'},
          {pattern: /^[0-9a-zA-Z\u4E00-\u9FA5]+$/, message: 'Special characters cannot be entered' }
        ],
      },
      isLoging: false,
    };
  },
  methods: {
    handleRegister () {
      if (!this.user.loginName || !this.user.password) {
        return this.$message.warning('username or password can not be null')
      }

      if (new RegExp("[0-9a-zA-Z\u4E00-\u9FA5]").test(this.user.password) == false) {
        return this.$message.warning('Special characters cannot be entered')
      }

      this.$api.toRegister(this.user).then(httpResult=>{
        let { data } = httpResult
        let flag = this.$config.executeResult(data)
        if(flag) {
          this.$message.success('sign in success')
          this.$router.push({name: 'login'})
          this.isLoging = false
        }
        this.isLoging = false
      })
    }
  }
}
</script>
<style type="text/css">
  .m-list-group{
    border-radius: 3px;
    padding: 0;
    margin: 0 0 20px;
  }
  .m-list-group .m-list-group-item{
    position: relative;
    display: block;
    padding: 6px 10px;
    margin-bottom: -1px;
    background-color: #fff;
    border: 1px solid #e7ecee;
  }
  .m-list-group .m-list-group-item:first-child{
    border-top-left-radius: 3px;
    border-top-right-radius: 3px;
  }
  .fullscreen{
    position: absolute;
    width: 100%;
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
  }
  .register-box{
    position: relative;
    width: 330px;
    margin: 0 auto;
    padding: 0px 15px;
  }
  .register-box .logo{
    max-width: 40%;
    margin-bottom: 30px;
  }
  .register-box .text-tips{
    text-align: center;
    color: #909DB7;
  }
  .register-box .m-input{
    width: 100%;
    padding: 10px;
    border: none;
    outline: none;
    box-sizing: border-box;
  }
  .register-box .m-btn{
    font-size: 18px;
    width: 100%;
    color: #fff;
    background-color: #1bbd70;
    display: inline-block;
    padding: 10px 12px;
    margin-bottom: 5px;
    line-height: 1.42857143;
    text-align: center;
    cursor: pointer;
    outline: none;
    border-radius: 2px;
    border: 1px solid #1bbd70;
    box-sizing: border-box;
    text-decoration: none;
  }
  .register-box .m-btn.m-btn-text{
    background: #fff;
    color: #1bbd70;
  }
  .register-box .m-btn:hover{
    background-color: #15A25F;
  }
  .register-box .m-btn.m-btn-text:hover{
    background-color: #F4F5F5;
  }
  .register-box .m-btn:active{
    opacity: 0.8;
  }
  @media (max-width: 768px) {
    .register-box{
      width: auto;
    }
  }
</style>

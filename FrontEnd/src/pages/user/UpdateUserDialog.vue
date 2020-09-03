<template>
  <el-dialog title="update" :visible.sync="dialogFormVisible" width="30%" :append-to-body="true">
    <el-form :model="formData" ref="formData" :rules="rules" status-icon>
      <el-form-item label="loginName" prop="loginName" :label-width="formLabelWidth">
        <el-input v-model="formData.loginName" placeholder="loginName" auto-complete="on"></el-input>
      </el-form-item>
      <el-form-item label="name" prop="name" :label-width="formLabelWidth">
        <el-input v-model="formData.name" auto-complete="on"></el-input>
      </el-form-item>
      <el-form-item label="phone" prop="phone" :label-width="formLabelWidth">
        <el-input v-model="formData.phone" placeholder="phone" auto-complete="on"></el-input>
      </el-form-item>
      <el-form-item label="address" prop="address" :label-width="formLabelWidth">
        <el-input v-model="formData.address" placeholder="address" auto-complete="on"></el-input>
      </el-form-item>
      <el-input type="hidden" v-model="formData.id" auto-complete="on"></el-input>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="dialogFormVisible = false">cancel</el-button>
      <el-button type="primary" @click="updateUser('formData')">ok</el-button>
    </div>
  </el-dialog>
</template>

<script>
  export default {
    name: "UpdateUserDialog",
    data() {
      var checkrelus = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('content can not be null'));
        } else {
          callback();
        }
      };
      var phone = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('content can not be null'));
        }
        setTimeout(() => {
          if (!Number(value)) {
            callback(new Error('please input number'));
          } else {
            if (value.length != 11) {
              callback(new Error('please input right phone'));
            }
            else {
              callback();
            }
          }
        });
      };
      return {
        formData: {
          id: '',
          loginName: '',
          name: '',
          address: '',
          phone: ''
        },
        rules:{
          loginName:[
            {required: true,validator: checkrelus, trigger: 'blur'},
            { min: 6, max: 20, message: 'length in 6 - 20', trigger: 'blur' }
          ],
          name:[
            {required: true,validator: checkrelus, trigger: 'blur'},
            { min: 2, max: 20, message: 'length in 2 - 20', trigger: 'blur' }
          ],
          phone:[
            {required: true,validator: phone, trigger: 'blur'},
            { pattern: /^[A-Za-z0-9\u4e00-\u9fa5]+$/, message: 'Special symbols such as spaces are not allowed' },
            { pattern: /^[1][3456789][0-9]{9}$/, message: 'Please enter the correct mobile phone number' }
          ]
        },
        dialogFormVisible: false,
        formLabelWidth: '80px'
      }
    },
    methods: {
      showDialog(val) {
        this.dialogFormVisible = val;
      },
      initData(row){
        this.formData.id = row.id
        this.formData.loginName = row.loginName
        this.formData.name = row.name
        this.formData.address = row.address
        this.formData.phone = row.phone
      },
      updateUser(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid){
            this.$api.addOrUpdateUser(this.formData).then(result => {
                let {data} = result
                let flag = this.$config.executeResult(data)
                if (flag) {
                  this.$message.success('success')
                  this.$emit('reloadData')
                  this.dialogFormVisible = false
                }
            })
          } else {
            return false;
          }
        })

      },
    }

  }
</script>

<style scoped>

</style>

<template>
  <el-dialog title="add" :visible.sync="dialogFormVisible" width="30%" :append-to-body="true">
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
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="dialogFormVisible = false">cancel</el-button>
      <el-button type="primary" @click="addUser('formData')">ok</el-button>
    </div>
  </el-dialog>
</template>

<script>
  export default {
    name: "AddUserDialog",
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
          phone: '',
          address: ''
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
            { pattern: /^[A-Za-z0-9\u4e00-\u9fa5]+$/, message: 'dont input space' },
            { pattern: /^[1][3456789][0-9]{9}$/, message: 'please input right phone' }
          ]
        },
        dialogFormVisible: false,
        formLabelWidth: '120px'
      }
    },
    methods: {
      showDialog(val) {
        this.dialogFormVisible = val;
      },
      addUser(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid){
            this.$api.addOrUpdateUser(this.formData).then(result => {
              let {data} = result
              let flag = this.$config.executeResult(data)
              if (flag) {
                this.$message.success('success')
                this.$refs['formData'].resetFields();
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

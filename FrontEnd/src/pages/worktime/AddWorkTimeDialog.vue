<template>
  <el-dialog title="new" :visible.sync="dialogFormVisible" width="30%" :append-to-body="true">
    <el-form :model="formData" ref="formData" :rules="rules" status-icon>
      <el-form-item label="code" prop="code" :label-width="formLabelWidth">
        <el-input v-model="formData.code" auto-complete="on"></el-input>
      </el-form-item>
      <el-form-item label="name" prop="name" :label-width="formLabelWidth">
        <el-input v-model="formData.name" auto-complete="on"></el-input>
      </el-form-item>
      <el-form-item label="phone" prop="phone" :label-width="formLabelWidth">
        <el-input v-model="formData.phone" auto-complete="on"></el-input>
      </el-form-item>
      <el-form-item label="address" prop="address" :label-width="formLabelWidth">
        <el-input v-model="formData.address"  auto-complete="on"></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="dialogFormVisible = false">cancel</el-button>
      <el-button type="primary" @click="addWorkTime('formData')">ok</el-button>
    </div>
  </el-dialog>
</template>

<script>
  export default {
    name: "AddWorkTimeDialog",
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
        } else {
          callback();
        }
      };
      return {
        formData: {
          code: '',
          name: '',
          address: '',
          phone: ''
        },
        rules:{
          code:[
            {required: true,validator: checkrelus, trigger: 'blur'},
          ],
          name:[
            {required: true,validator: checkrelus, trigger: 'blur'},
            {min: 2, max: 20, message: 'length 2 - 20', trigger: 'blur' }
          ],
          phone:[
            {required: true,validator: phone, trigger: 'blur'},
            {pattern: /^[1][3456789][0-9]{9}$/, message: 'input right phone' }
          ],
          address:[
            {required: true,validator: checkrelus, trigger: 'blur'},
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
      addWorkTime(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid){
            this.$api.addOrUpdateWorkTime(this.formData).then(result => {
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

<template>
  <el-dialog title="修改用户" :visible.sync="dialogFormVisible" width="30%" :append-to-body="true">
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
      <el-button @click="dialogFormVisible = false">取 消</el-button>
      <el-button type="primary" @click="updateEmployee('formData')">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
  export default {
    name: "UpdateEmployeeDialog",
    data() {
      var checkrelus = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('内容不能为空'));
        } else {
          callback();
        }
      };
      var phone = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('内容不能为空'));
        }
        setTimeout(() => {
          if (!Number(value)) {
            callback(new Error('请输入数字值'));
          } else {
            if (value.length != 11) {
              callback(new Error('请输入正确的手机号'));
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
            {min: 2, max: 20, message: '长度在 2 到 20 个字符', trigger: 'blur' }
          ],
          phone:[
            {required: true,validator: phone, trigger: 'blur'},
            {pattern: /^[1][3456789][0-9]{9}$/, message: '请输入正确的手机号' }
          ],
          address:[
            {required: true,validator: checkrelus, trigger: 'blur'},
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
        this.formData.code = row.code
        this.formData.name = row.name
        this.formData.address = row.address
        this.formData.phone = row.phone
      },
      updateEmployee(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid){
            this.$api.addOrUpdateEmployee(this.formData).then(result => {
                let {data} = result
                let flag = this.$config.executeResult(data)
                if (flag) {
                  this.$message.success('修改成功')
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

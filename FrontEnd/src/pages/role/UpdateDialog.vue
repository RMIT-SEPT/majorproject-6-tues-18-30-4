<template>
  <el-dialog
    title="update role"
    :visible.sync="dialogVisible"
    :close-on-click-modal="false" :append-to-body="true">
    <el-form :label-position="labelPosition" label-width="80px" status-icon :model="formLabelAlign" ref="clearForm" :rules="rules">
      <el-form-item label="name" prop="name">
        <el-input v-model="formLabelAlign.name"></el-input>
      </el-form-item>
      <el-form-item label="detail" prop="detail">
        <el-input v-model="formLabelAlign.detail"></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click.native="dialogVisible = false">cancel</el-button>
      <el-button type="primary" @click="updateRole">ok</el-button>
    </div>
  </el-dialog>
</template>

<script>
  export default {
    name: "UpdateDialog",
    data() {
      var checkrelus = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('content can not be null'));
        } else {
          callback();
        }
      };
      return {
        dialogVisible: false,
        labelPosition: 'right',
        formLabelAlign: {
          name: '',
          detail: '',
          id:''
        },
        rules:{
          name: [
            { validator: checkrelus, trigger: 'blur' },
            { min: 1, max: 6, message: 'length 1 - 6', trigger: 'blur' },
            { pattern: /^[A-Za-z0-9\u4e00-\u9fa5]+$/, message: 'can not input space' }
          ]
        },
      }
    },
    methods:{
      updateDialog(val,rowData){
        this.dialogVisible=val;
        this.formLabelAlign.name=rowData.name;
        this.formLabelAlign.detail=rowData.description;
        this.id=rowData.id;
      },
      updateRole(){
        var par = {
          id:this.id,
          name: this.formLabelAlign.name,
          description: this.formLabelAlign.detail
        }
        this.$api.addRole(par).then(httpResult => {
          if (httpResult.data.code==1) {
            this.$message('please input right info');
          }else {
            let flag = this.$config.executeResult(httpResult.data)
            if (flag) {
              this.$message.success("success")
              this.dialogVisible = false;
              this.$emit('reloadData')
            }
          }
        })
      }
    }
  }
</script>

<style scoped>

</style>

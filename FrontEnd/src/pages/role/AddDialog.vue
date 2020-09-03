<template>
  <el-dialog
    title="add"
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
      <el-button type="primary" @click="addRole">ok</el-button>
    </div>
  </el-dialog>
</template>

<script>
  export default {
    name: "AddDialog",
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
        },
        rules:{
          name: [
            { validator: checkrelus, trigger: 'blur' },
            { min: 1, max: 6, message: 'length 1 - 6', trigger: 'blur' },
            { pattern: /^[A-Za-z0-9\u4e00-\u9fa5]+$/, message: 'can not be input space' }
          ]
        },
      }

    },
    methods: {
      showDialog(val) {
        this.dialogVisible = val;
      },
      addRole: function () {
        var par = {
          name: this.formLabelAlign.name,
          description: this.formLabelAlign.detail
        }
        this.$api.addRole(par).then(httpResult => {
          if (httpResult.data.code==1) {
            this.$message('input rgith info');
          }else {
            let flag = this.$config.executeResult(httpResult.data)
            if (flag) {
              this.$message.success("success");
              this.$refs['clearForm'].resetFields();
              this.$emit('reloadData');
              this.dialogVisible = false;
            }
          }
        })
      }
    }
  }
</script>

<style scoped>

</style>

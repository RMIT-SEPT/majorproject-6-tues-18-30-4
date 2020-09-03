<template>
  <el-dialog title="新增菜单" :visible.sync="dialogFormVisible" :append-to-body="true">
    <el-form :model="form" ref="form" :rules="rules" status-icon>
      <el-form-item label="上级菜单" :label-width="formLabelWidth">
        <el-select clearable v-model="form.parentId" placeholder="请选择" @focus="loadData()">
          <el-option
            v-for="item in parentsMenuList"
            :key="item.name"
            :label="item.name"
            :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="name" :label-width="formLabelWidth" prop="name">
        <el-input v-model="form.name" auto-complete="on"></el-input>
      </el-form-item>
      <el-form-item label="sort" :label-width="formLabelWidth">
        <el-input-number v-model="form.seq" :min="1" :max="999" label="sort"></el-input-number>
      </el-form-item>
      <el-form-item label="icon" :label-width="formLabelWidth">
        <el-input v-model="form.icon" placeholder="Font Awesome（url：http://fontawesome.dashgame.com)" auto-complete="on"></el-input>
      </el-form-item>
      <el-form-item label="url" :label-width="formLabelWidth" prop="indexUrl">
        <el-input v-model="form.indexUrl" auto-complete="on"></el-input>
      </el-form-item>
      <el-form-item label="description" :label-width="formLabelWidth" prop="description">
        <el-input type="textarea" v-model="form.description" auto-complete="on"></el-input>
      </el-form-item>
      <el-input type="hidden" v-model="form.id" auto-complete="on"></el-input>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="dialogFormVisible = false">cancel</el-button>
      <el-button type="primary" @click="updateMenu('form')">ok</el-button>
    </div>
  </el-dialog>
</template>

<style>

</style>

<script>
  export default {
    name: 'UpdateMenuDialog',
    data() {
      var checkrelus = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('content can not be null'));
        } else {
          callback();
        }
      };
      return {
        parentsMenuList: [],
        form: {
          parentId: '',
          id: null,
          name: '',
          parentName: '',
          icon: '',
          seq: 0,
          indexUrl: '',
          description: '',
        },
        rules: {
          name: [
            {required: true, validator: checkrelus, trigger: 'blur'},
            {min: 2, max: 50, message: 'length in 2-50', trigger: 'blur'},
            {pattern: /^[A-Za-z0-9\u4e00-\u9fa5]+$/, message: 'Special symbols such as spaces are not allowed'}
          ],
          indexUrl: [
            {required: true, validator: checkrelus, trigger: 'blur'},
          ],
          description: [
            {required: true, validator: checkrelus, trigger: 'blur'},
          ]
        },
        dialogFormVisible: false,
        formLabelWidth: '120px'
      }
    },
    methods: {
      loadData() {
        this.$api.getMenu().then(result => {
          this.parentsMenuList = result.data;
        })
      },
      initData(row) {
        this.form.parentId = row.parentId;
        this.form.id = row.id;
        this.form.description = row.description;
        this.form.icon = row.icon;
        this.form.indexUrl = row.indexUrl;
        this.form.name = row.name;
        this.form.seq = row.seq;
        this.form.parentName = row.parentName;
      },
      showDialog(val) {
        this.dialogFormVisible = val;
      },
      updateMenu(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.$api.addOrUpdateMenu(this.form).then(result => {
              this.$message('Please fill in the valid information');

              let {data} = result
              let flag = this.$config.executeResult(data)
              if (flag) {
                this.$message.success('success')
                this.$refs['form'].resetFields();
                this.$emit('reloadData');
                this.dialogFormVisible = false
              }
            })
          } else {
            return false;
          }
        })
      },
      mounted: function () {
        this.loadData()
      }
    }
  }
</script>

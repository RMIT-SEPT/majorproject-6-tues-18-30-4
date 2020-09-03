<template>
  <!-- 配置菜单模态窗口 -->
  <el-dialog title="Configuration menu" :visible.sync="dialogFormVisible" :append-to-body="true">
    <el-tree
      :data="MenuManagement"
      show-checkbox
      node-key="id"
      ref="tree"
      :default-expanded-keys="[1]"
      :default-checked-keys="checkId"
      :props="defaultProps">
    </el-tree>
    <div slot="footer" class="dialog-footer">
      <el-button @click="dialogFormVisible = false">cancel</el-button>
      <el-button type="primary" @click="updatePermissions">ok</el-button>
    </div>
  </el-dialog>
</template>

<script>
  export default {
    name: "MenuDialog",
    data() {
      return {
        dialogFormVisible: false,
        MenuManagement: [],
        checkId: [],
        roleId: '',
        boxCheck: [],
        defaultProps: {
          children: 'children',
          label: 'text'
        }
      }
    },
    methods: {
      menu(val, id) {
        this.requestId = id;
        this.dialogFormVisible = val;
        this.$api.findMenuById(this.requestId).then(httpResult => {
          this.checkId = [];
          this.MenuManagement = httpResult.data;
          let menuId = httpResult.data;
          for (var i = 0; i < menuId.length; i++) {
            if (menuId[i].checked == true && menuId[i].children.length == 0) {
              this.checkId.push(menuId[i].id)
            }
            for (var j = 0; j < menuId[i].children.length; j++) {
              if (menuId[i].children[j].checked == true) {
                this.checkId.push(menuId[i].children[j].id)
              }
            }
          }
        })
      },
      updatePermissions() {
        this.boxCheck = this.$refs.tree.getCheckedKeys();
        if (this.boxCheck.length == 0) {
          this.$message.error("Select at least one menu")
        } else {
          var menuIdStrs = '';
          for (var i = 0; i < this.boxCheck.length; i++) {
            i == this.boxCheck.length - 1 ? menuIdStrs += this.boxCheck[i] : menuIdStrs += this.boxCheck[i] + ",";
          }
          var parags = {
            roleId: this.requestId,
            menuIdStr: menuIdStrs
          }
          this.$api.saveMenu(parags).then(httpResult => {
            if (httpResult.data.codeMsg == "success") {
              this.$message.success("success")
              this.dialogFormVisible = false;
            } else {
              this.$message.error("error")
            }
          })
        }
      }
    }

  }
</script>

<style scoped>

</style>

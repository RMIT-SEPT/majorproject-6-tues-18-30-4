<template>
  <el-dialog title="role" :visible.sync="dialogFormVisible" width="30%" :append-to-body="true">
    <el-table :data="tableData" width="500" ref="roleTable" @selection-change="handleSelectionChange">
      <el-table-column
        type="selection"
        width="200">
      </el-table-column>
      <el-table-column prop="name" label="name" width="200"></el-table-column>
    </el-table>
    <div slot="footer" class="dialog-footer">
      <el-button @click="dialogFormVisible = false">cancel</el-button>
      <el-button type="primary" @click="saveUserRole">ok</el-button>
    </div>
  </el-dialog>
</template>

<script>
  export default {
    name: "RoleDialog",
    data() {
      return {
        tableData: [],
        dialogFormVisible: false,
        multipleSelection: [],
        userId: '',
        roleIdStr:''
      }
    },
    methods: {
      showDialog(val) {
        this.dialogFormVisible = val;
      },
      initData(id) {
        this.$api.getRole().then(result => {
          let {data} = result
          this.tableData = data
          this.userId = id
        })
        this.$api.getUserRole(id).then(result=>{
            let {data} = result
          data.forEach(row=>{
            this.$refs.roleTable.toggleRowSelection(this.tableData[row.id-1]);
          })
        })
      },
      handleSelectionChange(val) {
        this.multipleSelection = val;
      },
      saveUserRole(){
        this.roleIdStr = ''
        for (let i = 0; i < this.multipleSelection.length; i++) {
          this.roleIdStr += this.multipleSelection[i].id+','
        }
        this.roleIdStr = this.roleIdStr.substring(0, this.roleIdStr.lastIndexOf(','));
        console.log(this.userId)
        console.log(this.roleIdStr)
        this.$api.saveUserRoles({userId:this.userId,roleIdStr:this.roleIdStr}).then(result=>{
          this.$message.success("System message: the role was set successfully")
          this.dialogFormVisible = false;
          this.$emit('reloadData');
        })
      }
    }
  }
</script>

<style scoped>

</style>

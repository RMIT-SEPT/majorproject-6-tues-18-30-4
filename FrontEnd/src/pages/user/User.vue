<template>
  <div class="page-body">
    <div class="box">
      <el-button type="primary" plain icon="el-icon-plus" @click="addUser">add</el-button>
      <el-table ref="singleTable" v-loading="loading" :data="tableData" stripe highlight-current-row
                @current-change="handleCurrentChange"
                style="margin-top: 15px">
        <el-table-column label="#" type="index"></el-table-column>
        <el-table-column prop="loginName" label="loginName" align="center"></el-table-column>
        <el-table-column prop="name" label="name" align="center"></el-table-column>
        <el-table-column prop="phone" label="phone" align="center"></el-table-column>
        <el-table-column prop="address" label="address" align="center"></el-table-column>
        <el-table-column prop="roleName" label="roleName" align="center"></el-table-column>
        <el-table-column prop="createTime" label="createTime" align="center"></el-table-column>
        <el-table-column label="operation" align="center">
          <div slot-scope="scope">
            <el-button type="success" class="fa fa-edit" plain @click="updateUser(scope.$index, scope.row)"></el-button>
            <el-button type="danger" class="fa fa-trash-o" plain
                       @click="deleteUser(scope.$index, scope.row)"></el-button>
          </div>
        </el-table-column>
      </el-table>

      <add-user-dialog @reloadData="loadData" ref="addUserDialog"></add-user-dialog>
      <update-user-dialog @reloadData="loadData" ref="updateUserDialog"></update-user-dialog>
      <role-dialog @reloadData="loadData" ref="roleDialog"></role-dialog>
      <app-page :total="total" @reloadData="loadData" ref="appPage"></app-page>

      <div class="changeRole">
        <div class="">
          <el-button @click="changeRole" :disabled="!currentRow" icon="fa fa-user-o"> Assign roles</el-button>
          <el-button @click="resetPassword" :disabled="!currentRow" icon="fa fa-user-o"> reset password</el-button>
        </div>
      </div>
    </div>
  </div>

</template>
<style>


  .changeRole {
    margin-top: 20px;
    margin-bottom: 10px;
    margin-left: 10px;
  }

</style>


<script>
  import AddUserDialog from './AddUserDialog'
  import UpdateUserDialog from './UpdateUserDialog'
  import RoleDialog from './RoleDialog'
  import AppTabs from '@/components/app-tabs'
  import AppPage from '@/components/app-page'

  export default {
    data() {
      return {
        tableData: [],
        total: 1,
        loading: false,
        currentRow: null,
        user: {
          loginName: '',
          phone: '',
          name: '',
          address: '',
          createTime: ''
        },
      }
    },
    components: {
      AddUserDialog,
      UpdateUserDialog,
      RoleDialog,
      AppTabs,
      AppPage
    },
    methods: {
      loadData(page = 1, rows = 10) {
        this.loading = true
        this.$api.getUser({page: page, rows: rows}).then(result => {
          let {data} = result
          this.tableData = data.rows
          this.total = data.total
          this.loading = false
        })
      },
      addUser() {
        this.$refs.addUserDialog.showDialog(true)
      },
      updateUser(index, row) {
        this.$refs.updateUserDialog.initData(row)
        this.$refs.updateUserDialog.showDialog(true)
      },
      deleteUser(index, row) {
        this.$confirm('This operation will permanently delete the information. Do you want to continue?', 'tips', {
          confirmButtonText: 'ok',
          cancelButtonText: 'cancel',
          type: 'warning'
        }).then(() => {
          this.$api.deleteUser(row.id).then(result => {
            let {data} = result
            let flag = this.$config.executeResult(data)
            if (flag) {
              this.$message.success('success')
              this.loadData()
            }
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: 'cancel'
          });
        });
      },
      changeRole() {
        if (this.currentRow === null) {
          this.$message.warning("tips", "Please select at least one row")
        } else {
          this.$refs.roleDialog.showDialog(true)
          this.$refs.roleDialog.initData(this.currentRow.id)
        }
      },
      resetPassword() {
        this.$confirm('Are you sure you want to reset the password?', 'tip', {
          confirmButtonText: 'ok',
          cancelButtonText: 'cancel',
          type: 'warning'
        }).then(() => {
          this.$api.resetUserPassword(this.currentRow.id).then(result => {
            let {data} = result
            let flag = this.$config.executeResult(data)
            if (flag) {
              this.$message.success('Password initialization succeeded')
              this.loadData()
            }
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: 'cancel'
          });
        });
      },
      handleCurrentChange(val) {
        this.currentRow = val;
      },
    },
    mounted: function () {
      this.loadData()
    }
  }


</script>

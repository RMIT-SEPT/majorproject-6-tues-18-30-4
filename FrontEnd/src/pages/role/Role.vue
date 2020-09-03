<template>
  <div class="page-body">
    <div class="box">
      <el-button type="primary" plain icon="el-icon-plus" @click="addRole">add</el-button>
      <el-table :data="roleLists" highlight-current-row
                @current-change="handleCurrentChange" style="margin-top: 15px">
        <el-table-column label="#" type="index"></el-table-column>
        <el-table-column label="name" prop="name"></el-table-column>
        <el-table-column label="description" prop="description"></el-table-column>
        <el-table-column label="operation">
          <template slot-scope="scope">
            <el-button type="primary" class="fa fa-bars" plain @click="openMenu(scope.$index, scope.row)"></el-button>
            <el-button type="success" class="fa fa-edit" plain @click="updateRole(scope.$index, scope.row)"></el-button>
            <el-button type="danger" class="fa fa-trash-o" plain
                       @click="deleteRole(scope.$index, scope.row)"></el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <menu-dialog ref="MenuDialog"></menu-dialog>
    <add-dialog ref="AddDialog" @reloadData="loadData"></add-dialog>
    <update-dialog ref="UpdateDialog" @reloadData="loadData"></update-dialog>
  </div>
</template>

<script>

  import MenuDialog from './MenuDialog'
  import AddDialog from './AddDialog'
  import UpdateDialog from './UpdateDialog'

  export default {

    data() {
      return {
        addLoading: false,
        roleLists: [],
        currentRow: null,
        val: 10
      }
    },
    components: {
      MenuDialog,
      AddDialog,
      UpdateDialog
    },
    methods: {
      loadData() {
        this.$api.roleList().then(httpResult => {
          this.roleLists = httpResult.data;
          return httpResult;
        })
      },
      handleCurrentChange(val) {
        this.currentRow = val;
      },
      addRole: function () {
        this.$refs.AddDialog.showDialog(true)
      },
      updateRole: function (val,row) {
          this.$refs.UpdateDialog.updateDialog(true, row)
      },
      openMenu: function (val,row) {
          this.$refs.MenuDialog.menu(true, row.id)
      },
      deleteRole(index, row) {
        this.$confirm('This operation will permanently delete the information. Do you want to continue?', 'tips', {
          confirmButtonText: 'ok',
          cancelButtonText: 'cancel',
          type: 'warning'
        }).then((error) => {
          this.$api.deleteRole(row.id).then(result => {
            let flag = this.$config.executeResult(result.data)
            if (flag) {
              this.$message.success("success")
              this.loadData()
            }
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: 'cancel'
          });
        });
      }
    },
    mounted() {
      this.loadData()
    }
  }
</script>

<style>
  .box-body {
    width: 100%;
    overflow: auto;
  }
</style>

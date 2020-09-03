<template>
  <div class="page-body">
    <div class="box">
      <el-button type="primary" plain icon="el-icon-plus" @click="addMenu()">add</el-button>
      <el-button type="primary" plain class="inputSearch">search </el-button>
      <el-input v-model="user.loginName" placeholder="menu name" class="input" prefix-icon="el-icon-search"></el-input>
      <el-table ref="singleTable" v-loading="loading" :data="tableData" stripe
                style="margin-top: 15px">
        <el-table-column label="#" type="index" align="center"></el-table-column>
        <el-table-column label="name" prop="name" width="80" align="center"></el-table-column>
        <el-table-column label="parentName" prop="parentName" align="center"></el-table-column>
        <el-table-column label="icon" prop="icon" align="center"></el-table-column>
        <el-table-column label="description" prop="description" align="center"></el-table-column>
        <el-table-column label="operation" align="center">
          <template slot-scope="scope">
            <el-button type="success" class="fa fa-edit" plain @click="updateMenu(scope.$index, scope.row)"></el-button>
            <el-button type="danger" class="fa fa-trash-o" plain @click="handleDel(scope.row)"></el-button>
          </template>
        </el-table-column>
      </el-table>
      <add-menu-dialog @reloadData="loadData" ref="addMenuDialog" :rowLength="rowLength"></add-menu-dialog>
      <update-menu-dialog @reloadData="loadData" ref="updateMenuDialog"></update-menu-dialog>

    </div>
  </div>
</template>
<style>
  .inputSearch {
    float: right;
  }
  .bottom{
    margin-top: 20px;
    margin-bottom: 10px;
    margin-left: 10px;
  }
  .input {
    width: 180px;
    float: right;
  }
</style>
<script>
  import AddMenuDialog from './AddMenuDialog'
  import UpdateMenuDialog from './UpdateMenuDialog'
  export default {
    data() {
      return {
        selectRollInfo: '',
        tableData: [],
        rowLength: 0,
        loading: false,
        currentRow: null,
        user: {
          loginName: '',
          phone: '',
          name: '',
          createTime: ''
        },
      }
    },
    components: {
      AddMenuDialog,
      UpdateMenuDialog
    },
    methods: {
      loadData() {
        this.loading = true
        this.$api.getMenu().then(result => {
          this.tableData = result.data;
          console.log(result)
          this.tableData = result.data;
          this.rowLength = this.tableData.length + 1;
          this.loading = false
        })
      },
      addMenu() {
        this.$refs['addMenuDialog'].showDialog(true);
      },
      updateMenu(index, row) {
        this.$refs.updateMenuDialog.initData(row);
        this.$refs.updateMenuDialog.showDialog(true);
      },
      //delete
      handleDel(rowData) {
        this.$confirm('This operation will permanently delete the menu. Do you want to continue?', 'tips', {type: 'warning'})
          .then(() => {
            this.$api.deleteMenu(rowData.id)
              .then((result) => {
                if (result.data.code == 0) {
                  this.$message({
                    type: 'danger',
                    message: `success`
                  });
                }
                this.loadData();
              }).catch((result) => {
              this.$message.error('error!');
            });
          }).catch(() => {
          this.$message.info('cancel!');
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

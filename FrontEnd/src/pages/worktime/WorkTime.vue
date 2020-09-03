<template>
  <div class="page-body">
    <div class="box">
      <el-button type="primary" plain icon="el-icon-plus" @click="addWorkTime">add</el-button>
      <el-table ref="singleTable" v-loading="loading" :data="tableData" stripe highlight-current-row
                @current-change="handleCurrentChange"
                style="margin-top: 15px">
        <el-table-column label="#" type="index"></el-table-column>
        <el-table-column prop="code" label="code" align="center"></el-table-column>
        <el-table-column prop="name" label="name" align="center"></el-table-column>
        <el-table-column prop="phone" label="phone" align="center"></el-table-column>
        <el-table-column prop="address" label="address" align="center"></el-table-column>
        <el-table-column label="operation" align="center">
          <div slot-scope="scope">
            <el-button type="success" class="fa fa-edit" plain @click="updateWorkTime(scope.$index, scope.row)"></el-button>
            <el-button type="danger" class="fa fa-trash-o" plain
                       @click="deleteWorkTime(scope.$index, scope.row)"></el-button>
          </div>
        </el-table-column>
      </el-table>
      <add-WorkTime-dialog @reloadData="loadData" ref="addUserDialog"></add-WorkTime-dialog>
      <update-WorkTime-dialog @reloadData="loadData" ref="updateUserDialog"></update-WorkTime-dialog>
      <app-page :total="total" @reloadData="loadData" ref="appPage"></app-page>

<!--      <div class="changeRole">-->
<!--        <div class="">-->
<!--          <el-button @click="changeRole" :disabled="!currentRow" icon="fa fa-user-o"> </el-button>-->
<!--        </div>-->
<!--      </div>-->
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
  import AddWorkTimeDialog from './AddWorkTimeDialog'
  import UpdateWorkTimeDialog from './UpdateWorkTimeDialog'
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
          code: '',
          phone: '',
          name: '',
          address: '',
          createTime: ''
        },
      }
    },
    components: {
      AddWorkTimeDialog,
      UpdateWorkTimeDialog,
      AppTabs,
      AppPage
    },
    methods: {
      loadData(page = 1, rows = 10) {
        this.loading = true
        this.$api.getWorkTime({page: page, rows: rows}).then(result => {
          let {data} = result
          this.tableData = data.rows
          this.total = data.total
          this.loading = false
        })
      },
      addWorkTime() {
        this.$refs.addUserDialog.showDialog(true)
      },
      updateWorkTime(index, row) {
        this.$refs.updateUserDialog.initData(row)
        this.$refs.updateUserDialog.showDialog(true)
      },
      deleteWorkTime(index, row) {
        this.$confirm('Password initialization succeeded?', 'tips', {
          confirmButtonText: 'ok',
          cancelButtonText: 'cancel',
          type: 'warning'
        }).then(() => {
          this.$api.deleteWorkTime(row.id).then(result => {
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
          this.$message.warning("tip", "Please select at least one row")
        } else {
          this.$refs.roleDialog.showDialog(true)
          this.$refs.roleDialog.initData(this.currentRow.id)
        }
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

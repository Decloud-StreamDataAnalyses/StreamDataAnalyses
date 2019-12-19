
<template>

  <div class="app-container">
    <!--<Search :query="query"/>-->
    <!--<el-button type="primary" plain @click="sendString">鼠标滑过/点击背景变深色调</el-button>-->
    <!--表格渲染-->
    <el-table v-loading="loading" :data="data" size="small" style="width: 100%;">
      <el-table-column type="expand">
        <template slot-scope="props">
          <!--<el-form label-position="left" inline class="demo-table-expand">-->
            <!--&lt;!&ndash;<el-form-item label="请求方法">&ndash;&gt;-->
              <!--&lt;!&ndash;<span>{{ props.row.method }}</span>&ndash;&gt;-->
            <!--&lt;!&ndash;</el-form-item>&ndash;&gt;-->
            <!--&lt;!&ndash;<el-form-item label="请求参数">&ndash;&gt;-->
              <!--&lt;!&ndash;<span>{{ props.row.params }}</span>&ndash;&gt;-->
            <!--&lt;!&ndash;</el-form-item>&ndash;&gt;-->
          <!--</el-form>-->
        </template>
      </el-table-column>
      <el-table-column prop="name"  label="服务名称"/>
      <el-table-column prop="startTime" label="开始时间"/>
      <!--<el-table-column :show-overflow-tooltip="true" prop="address" label="IP来源"/>-->
      <el-table-column prop="duration" label="持续时间"/>
      <el-table-column prop="jid" label="Job ID"/>
      <el-table-column prop="state" label="Status" width="180px">
        <!--<template slot-scope="scope">-->
          <!--<span>{{ parseTime(scope.row.createTime) }}</span>-->
        <!--</template>-->
      </el-table-column>
    </el-table>
    <!--分页组件-->
    <el-pagination
      :total="total"
      :current-page="page + 1"
      style="margin-top: 8px;"
      layout="total, prev, pager, next, sizes"
      @size-change="sizeChange"
      @current-change="pageChange"/>
  </div>
</template>

<script>
import { parseTime } from '@/utils/index'
import Search from './search'
import initData from "../../mixins/initData2"
import { postTest } from "@/api/data"
// import initData2 from "../../mixins/initData3"

export default {
  name: 'Log',
  components: { Search },
  mixins: [initData],
  created() {
    this.$nextTick(() => {
      this.init()
    })
  },
  methods: {

    parseTime,
    beforeInit() {
      this.url = 'api/logs/cluster'
      const sort = 'id,desc'
      const query = this.query
      const value = query.value
      // console.log("value:"+value)
      this.params = { page: this.page, size: this.size, sort: sort }
      // console.log("page:"+this.page)
      console.log("size:"+this.size)
      if (value) { this.params['blurry'] = value }
      this.params['logType'] = 'INFO'
      if (query.date) {
        this.params['startTime'] = query.date[0]
        this.params['endTime'] = query.date[1]
      }
      return true
    },
    sendString:function(){
      this.url = 'api/logs/postTest'

      postTest(this.url, {
        name : 'postName'
      }).then(res => {
        console.log('res:',res.jobs)
      }).catch(err => {
        // this.loading = false
        console.log(err.response.data.message)
      })
      // this.axios.post('localhost:8013/api/logs/postTest', 'sendString').then(function(response) {
      //   console.log('11111111111111')
      //   console.log(response)
      // }).catch(function(error) {
      //   console.log(error)
      // })

    }
  }
}
</script>

<style>
  .demo-table-expand {
    font-size: 0;
  }
  .demo-table-expand label {
    width: 70px;
    color: #99a9bf;
  }
  .demo-table-expand .el-form-item {
    margin-right: 0;
    margin-bottom: 0;
    width: 100%;
  }
  .demo-table-expand .el-form-item__content {
    font-size: 12px;

  }
</style>

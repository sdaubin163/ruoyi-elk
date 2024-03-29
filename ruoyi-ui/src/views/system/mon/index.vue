<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="请求日期" prop="requestDate">
        <el-date-picker clearable
          v-model="queryParams.requestDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择请求日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="响应日期" prop="responseDate">
        <el-date-picker clearable
          v-model="queryParams.responseDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择响应日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="系统标识" prop="sysId">
        <el-input
          v-model="queryParams.sysId"
          placeholder="请输入系统标识"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:mon:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:mon:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:mon:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:mon:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="monList" @selection-change="handleSelectionChange">
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
          >Zipkin</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
          >Log</el-button>
        </template>
      </el-table-column>
      <el-table-column label="跟踪id" align="center" prop="traceid" width="150"/>
      <el-table-column label="请求日期" align="center" prop="requestDate" />
      <el-table-column label="请求时间" align="center" prop="requestTime" />
      <el-table-column label="响应日期" align="center" prop="responseDate" />
      <el-table-column label="响应时间" align="center" prop="responseTime" />
      <el-table-column label="交易耗时(毫秒)" align="center" prop="processTime" />
      <el-table-column label="请求url" align="center" prop="url" />
      <el-table-column label="系统标识" align="center" prop="sysId" />
      <el-table-column label="系统名称" align="center" prop="sysName" />
      <el-table-column label="交易标识" align="center" prop="transId" />
      <el-table-column label="交易名称" align="center" prop="transName" />
      <el-table-column label="交易状态" align="center" prop="transStatus" />
      <el-table-column label="附属信息" align="center" prop="additionMsg" />
      <el-table-column label="备注" align="center" prop="remark1" />
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改fpsmonitor对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="唯一索引" prop="id">
          <el-input v-model="form.id" placeholder="请输入唯一索引" />
        </el-form-item>
        <el-form-item label="全局跟踪id" prop="traceid">
          <el-input v-model="form.traceid" placeholder="请输入全局跟踪id" />
        </el-form-item>
        <el-form-item label="请求时间" prop="requestTimestamp">
          <el-input v-model="form.requestTimestamp" placeholder="请输入请求时间" />
        </el-form-item>
        <el-form-item label="响应时间" prop="responseTimestamp">
          <el-input v-model="form.responseTimestamp" placeholder="请输入响应时间" />
        </el-form-item>
        <el-form-item label="处理时间，以毫秒为单位" prop="processTime">
          <el-input v-model="form.processTime" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="请求日期" prop="requestDate">
          <el-date-picker clearable
            v-model="form.requestDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择请求日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="请求时间" prop="requestTime">
          <el-input v-model="form.requestTime" placeholder="请输入请求时间" />
        </el-form-item>
        <el-form-item label="响应日期" prop="responseDate">
          <el-date-picker clearable
            v-model="form.responseDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择响应日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="响应时间" prop="responseTime">
          <el-input v-model="form.responseTime" placeholder="请输入响应时间" />
        </el-form-item>
        <el-form-item label="请求url" prop="url">
          <el-input v-model="form.url" placeholder="请输入请求url" />
        </el-form-item>
        <el-form-item label="系统标识" prop="sysId">
          <el-input v-model="form.sysId" placeholder="请输入系统标识" />
        </el-form-item>
        <el-form-item label="系统名称" prop="sysName">
          <el-input v-model="form.sysName" placeholder="请输入系统名称" />
        </el-form-item>
        <el-form-item label="交易标识" prop="transId">
          <el-input v-model="form.transId" placeholder="请输入交易标识" />
        </el-form-item>
        <el-form-item label="交易名称" prop="transName">
          <el-input v-model="form.transName" placeholder="请输入交易名称" />
        </el-form-item>
        <el-form-item label="附属信息，存储各业务特别关注的业务信息，以json格式存储" prop="additionMsg">
          <el-input v-model="form.additionMsg" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="备注" prop="remark1">
          <el-input v-model="form.remark1" placeholder="请输入备注" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listMon, getMon, delMon, addMon, updateMon } from "@/api/system/mon";

export default {
  name: "Mon",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // fpsmonitor表格数据
      monList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        traceid: null,
        requestDate: null,
        responseDate: null,
        sysId: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        id: [
          { required: true, message: "唯一索引不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询fpsmonitor列表 */
    getList() {
      this.loading = true;
      listMon(this.queryParams).then(response => {
        this.monList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        traceid: null,
        requestTimestamp: null,
        responseTimestamp: null,
        processTime: null,
        requestDate: null,
        requestTime: null,
        responseDate: null,
        responseTime: null,
        url: null,
        sysId: null,
        sysName: null,
        transId: null,
        transName: null,
        transStatus: null,
        additionMsg: null,
        remark1: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加fpsmonitor";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {

      const traceId = row.traceid;
      const url = `http://localhost:9411/zipkin/traces/${traceId}`;
      window.open(url, '_blank');
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateMon(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addMon(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除fpsmonitor编号为"' + ids + '"的数据项？').then(function() {
        return delMon(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/mon/export', {
        ...this.queryParams
      }, `mon_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>

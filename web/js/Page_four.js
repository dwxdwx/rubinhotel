$(function() {
	$("#productTable").bootstrapTable({
		url: 'adminservlet?method=getAll', // 初始化加载数据来源
		method: 'get',
		toolbar: '#toolbar', // 为表格绑定工具栏
		striped: true, // 显示为斑马线格式，奇偶行不通背景色

		showRefresh: "true", // 显示刷新按钮
		showToggle: "true", // 显示格式切换按钮
		showColumns: "true", // 显示列过滤按钮	

		// 分页相关  服務器端返回的数据需要包含有total属性和rows属性
		pagination: true, // 显示分页
		pageNumber: 1, // 初始化加载第一页
		pageSize: 4, // 每页2条数据
		pageList: [4, 6, 10], // 可以选择的每页数量
		sidePagination: "server", //表示服务端请求分页数据 

		// 页面初始化时排序有关					
		sortName: 'id',
		sortOrder: 'asc',

		// 提交到Server的参数列表 ， 
		// 参数设定相关
		queryParamsType: "undefined", // undefined：提交到服务器端的参数自定义
		queryParams: function(params) {
			// 参数params中自带 pageSize , pageNumber , sortName , sortOrder
			params.nameKey = $("#search_aname").val();

			console.dir(params);
			return params; // params 是一个js对象，该对象所有属性值都将被提交到服务器端

		},

		columns: [{
				checkbox: true
			}, // 显示为复选框
			{
				field: 'aid',
				title: '管理员编号',
			},
			{
				field: 'aname',
				title: '管理员名字'
			},
			
			{
				field: 'atel',
				title: '联系电话'
			},

			{
				title: '操作',
				formatter: operateFormatter, // 使用叫做operateFormatter函数来设置该列所显示的内容
				events: operateEvents, // 设置该列按照class名称加载相应的用户动作，动作描述在window.operateEvents函数中
			}
		],
	});
	// 查询按钮动作
	$("#btn_query").click(function() {
		$("#productTable").bootstrapTable('refresh');
	});

})
// 操作列对应的函数 ：该函数用于描述本列所要显示的html元素
function operateFormatter(value, row, index) {
	var update = '<button type="button" class="edit btn btn-xs btn-info"' +
		'<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改' +
		'</button>&nbsp;&nbsp;';

		return update ;//+ del
}

window.operateEvents = {
		// click表示绑定click动作，   .edit 表示为class是edit的元素绑定事件 【注意中间的空格】
		'click .edit': function(e, value, row, index) { // 参数row是本行的数据
			// console.dir('edit : ') ;
			// console.dir(row) ;
			// 弹框：表单==>表单元素应该填充上本行数据默认值
			// bootstrap -- 模态框
			// 模态框显示数据
			$("#editWindow #edit_aid").val(row.aid);
			$("#editWindow #edit_aname").val(row.aname);
			$("#editWindow #edit_atel").val(row.atel);
			// 模态框显示
			$("#editWindow").modal("show");
			$("#editSubmitBtn").one('click', function() {
				// ajax 提交更新
				$.post(
					'adminservlet?method=updateAdmin',
					$("#editForm").serialize(),
					function(result) {
						if(result == "true") {
							bootbox.alert('更新成功!');
							$("#productTable").bootstrapTable('refresh');
						} else {
							bootbox.alert('更新失败！');
						}
						// 表单重置
						$("#editForm")[0].reset();
						// 模态框关闭
						$("#editWindow").modal("hide");
					}
				);

			});
		}
		
	}



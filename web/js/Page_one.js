$(function() {
	$("#productTable").bootstrapTable({
		url: 'userservlet?method=getAll', // 初始化加载数据来源
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
		pageList: [2, 5, 10], // 可以选择的每页数量
		sidePagination: "server", //表示服务端请求分页数据 

		// 页面初始化时排序有关					
		sortName: 'id',
		sortOrder: 'asc',

		// 提交到Server的参数列表 ， 
		// 参数设定相关
		queryParamsType: "undefined", // undefined：提交到服务器端的参数自定义
		queryParams: function(params) {
			// 参数params中自带 pageSize , pageNumber , sortName , sortOrder
			params.nameKey = $("#search_name").val();

			console.dir(params);
			return params; // params 是一个js对象，该对象所有属性值都将被提交到服务器端

		},

		columns: [{
				checkbox: true
			}, // 显示为复选框
			{
				field: 'uid',
				title: '住户ID'
			},
			{
				field: 'uname',
				title: '姓名'
			},
			{
				field: 'uidnum',
				title: '身份证号'
			},
			{
				field: 'ucheck_in',
				title: '入住时间'
			},
			{
				field: 'ucheck_out',
				title: '离店时间'
			},
			{
				field: 'utel',
				title: '联系电话'
			},
			/*
			{field : 'state' , title : '状态' , formatter : function(value , row , index){   // 对该列值进行加工处理
				if(value == '0') {
					return '未激活' ;
				} else if(value == '1') {
					return '激活' ;
				} else if(value == '2') {
					return '禁用' ;
				}
			}} ,
			*/
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

	//工具栏“添加”按钮动作
	$("#btn_add").click(function() {
		// 模态框显示
		$("#addWindow").modal("show");
		$("#addSubmitBtn").one('click', function() {
			// ajax 提交更新
			$.post(
				'userservlet?method=addUser',
				$("#addForm").serialize(),
				function(result) {
					if(result == "true") {
						bootbox.alert('添加成功!');
						$("#productTable").bootstrapTable('refresh');
					} else {
						bootbox.alert('添加失败！');
					}
					// 表单重置
					$("#addForm")[0].reset();
					// 模态框关闭
					$("#addWindow").modal("hide");
				}
			);
		});
	});

	// 工具栏“删除”按钮动作
	$("#btn_delete").click(function() {
		// 获取表格选中行
		var rows = $("#productTable").bootstrapTable('getSelections');
		// console.dir(rows) ;
		if(rows.length == 0) {
			bootbox.alert('请选中要删除的记录！');
			return;
		}
		bootbox.confirm('确认删除?', function(confirmR) {
			if(confirmR) {
				// 将选中行的id拼为一个字符串，使用逗号间隔
				var idStr = "";
				// each() 遍历函数，$.each(数组,functin(数组元素下标,元素值){})
				$.each(rows, function(index, item) {
					idStr += item.uid + ",";
				})
				// 去掉末尾多余的逗号
				idStr = idStr.substr(0, idStr.length - 1);
				console.dir(idStr);
				// ajax请求删除
				$.get(
					'userservlet?method=delMore&delIds=' + idStr,
					function(result) {
						if(result == "true") {
							bootbox.alert('删除成功！');
							$("#productTable").bootstrapTable('refresh');
						} else {
							bootbox.alert('删除失败！')
						}
					}
				);
			}
		});
	});
})
// 操作列对应的函数 ：该函数用于描述本列所要显示的html元素
function operateFormatter(value, row, index) {
	var update = '<button type="button" class="edit btn btn-xs btn-info"' +
		'<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改' +
		'</button>&nbsp;&nbsp;';
	var del = '<button type="button" class="remove btn btn-xs btn-danger"' +
		'<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除' +
		'</button>';
	return update + del;
}
// 操作列按钮动作的函数
window.operateEvents = {
	// click表示绑定click动作，   .edit 表示为class是edit的元素绑定事件 【注意中间的空格】
	'click .edit': function(e, value, row, index) { // 参数row是本行的数据
		// console.dir('edit : ') ;
		// console.dir(row) ;
		// 弹框：表单==>表单元素应该填充上本行数据默认值
		// bootstrap -- 模态框
		// 模态框显示数据
		$("#editWindow #editId").val(row.uid);
		$("#editWindow #edit_uname").val(row.uname);
		$("#editWindow #edit_uidnum").val(row.uidnum);
		$("#editWindow #edit_ucheck_out").val(row.ucheck_out);
		$("#editWindow #edit_utel").val(row.utel);
		// 模态框显示
		$("#editWindow").modal("show");
		$("#editSubmitBtn").one('click', function() {
			// ajax 提交更新
			$.post(
				'userservlet?method=updateUser',
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
	},
	'click .remove': function(e, value, row, index) {
		//console.dir("del : ") ;
		//console.dir(row) ;
		bootbox.setLocale("zh_CN");
		bootbox.confirm('确认删除?', function(confirmR) {
			if(confirmR) {
				// ajax 执行删除操作
				$.get(
					'userservlet?method=delOne&delId=' + row.uid,
					function(result) {
						if(result == "true") {
							bootbox.alert('删除成功！');
							$("#productTable").bootstrapTable('refresh');
						} else {
							bootbox.alert('删除失败！')
						}
					}
				);
			}
		});

	}

}
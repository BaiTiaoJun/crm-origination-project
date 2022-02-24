<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";%>
<html>
<head>
<base href="<%=basePath%>">
<meta charset="UTF-8">
<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />
<link href="jquery/bs_pagination/jquery.bs_pagination.min.css" type="text/css" rel="stylesheet">

<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="jquery/bs_pagination/bs_typeahead/bootstrap3-typeahead.min.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="jquery/bs_pagination/jquery.bs_pagination.min.js"></script>
<script type="text/javascript" src="jquery/bs_pagination/en.js"></script>

<script type="text/javascript">
	$(function(){

		pageList(1, 2);

		$(".time").datetimepicker({
			minView: 'month',
			language: 'zh-CN',
			format: 'yyyy-mm-dd',
			autoclose: true,
			todayBtn: true,
			pickerPosition: 'bottom-left'
		})

		$("#createBtn").click(function () {
			$.ajax({
				url: "workbench/activity/getUserList.do",
				dataType: "json",
				type: "get",
				success: function (data) {
					let html = "<option></option>";
					$.each(data, function (i, n) {
						html += "<option value=\"" + n.id + "\">" + n.name + "</option>";
					})
					$("#create-owner").html(html).val("${user.id}");
				}
			})
			$("#createActivityModal").modal("show");
		})

		$("#saveBtn").click(function () {
			$.ajax({
				type: "post",
				url: "workbench/activity/save.do",
				data: {
					"owner": $.trim($("#create-owner").val()),
					"name": $.trim($("#create-name").val()),
					"startDate": $("#create-startDate").val(),
					"endDate": $("#create-endDate").val(),
					"cost": $.trim($("#create-cost").val()),
					"description": $.trim($("#create-description").val())
				},
				dataType: "json",
				success: function (data) {
					if (data.success) {
						$("#addActivityForm").get(0).reset();
						pageList(1, $("#activityPage").bs_pagination('getOption', 'rowsPerPage'));
						$("#createActivityModal").modal("hide");
					} else {
						alert("添加市场活动失败");
					}
				}
			})
		})

		$("#searchBtn").click(function () {
			$("#hidden-name").val($.trim($("#search-name").val()));
			$("#hidden-owner").val($.trim($("#search-owner").val()));
			$("#hidden-startDate").val($.trim($("#search-startDate").val()));
			$("#hidden-endDate").val($.trim($("#search-endDate").val()));

			pageList(1, $("#activityPage").bs_pagination('getOption', 'rowsPerPage'));

		});
		
		$("#selAll").click(function () {
			$("input[name=selected]").prop("checked", this.checked);
		})

		$("#activityBody").on("click", $("input[name=selected]"), function () {
			$("#selAll").prop("checked", $("input[name=selected]").length === $("input[name=selected]:checked").length);
		})

		$("#delBtn").click(function () {
			let $sel = $("input[name=selected]:checked");
			if ($sel.length === 0) {
				alert("请选择要删除的选项");
			} else {
				if (confirm("确认要删除市场活动吗？")) {
					let param = "";
					for (let i = 0; i < $sel.length; i++) {
						param += "value=" + $sel[i].value;
						if (i < $sel.length - 1) {
							param += "&";
						}
					}
					$.ajax({
						url: "workbench/activity/delete.do",
						data: param,
						type: "post",
						dataType: "json",
						success: function (data) {
							if (data.success) {
								pageList(1, $("#activityPage").bs_pagination('getOption', 'rowsPerPage'));
							} else {
								alert("删除市场活动失败");
							}
						}
					})
				}
			}
		})

		$("#editBtn").click(function () {
			let $sel = $("input[name=selected]:checked");
			if ($sel.length === 0) {
				alert("请选择需要修改的记录");
			} else if ($sel.length > 1) {
				alert("只能选择一条记录修改");
			} else {
				let id = $sel.val();
				$.ajax({
					url: "workbench/activity/getUserListAndActivity.do",
					data: {
						"id" : id
					},
					dataType: "json",
					type: "get",
					success: function (data) {
						let html = "<option></option>";
						$.each(data.uList, function (i, n) {
							html += "<option value='" + n.id + "'>" + n.name + "</option>";
						})
						$("#edit-owner").html(html);

						let $a = data.a;
						$("#edit-id").val($a.id);
						$("#edit-owner").val($a.owner);
						$("#edit-name").val($a.name);
						$("#edit-startDate").val($a.startDate);
						$("#edit-endDate").val($a.endDate);
						$("#edit-cost").val($a.cost);
						$("#edit-description").val($a.description);
					}
				})
				$("#editActivityModal").modal("show");
			}
		})

		$("#updateBtn").click(function () {
			$.ajax({
				type: "post",
				url: "workbench/activity/update.do",
				data: {
					"id": $.trim($("#edit-id").val()),
					"owner": $.trim($("#edit-owner").val()),
					"name": $.trim($("#edit-name").val()),
					"startDate": $("#edit-startDate").val(),
					"endDate": $("#edit-endDate").val(),
					"cost": $.trim($("#edit-cost").val()),
					"description": $.trim($("#edit-description").val())
				},
				dataType: "json",
				success: function (data) {
					if (data.success) {
						pageList($("#activityPage").bs_pagination('getOption', 'currentPage')
								,$("#activityPage").bs_pagination('getOption', 'rowsPerPage'));
						$("#editActivityModal").modal("hide");
					} else {
						alert("添加市场活动失败");
					}
				}
			})
		})
	});

	function pageList(pageNo, pageSize) {
		$("#selAll").prop("checked", false);

		$("#search-name").val($.trim($("#hidden-name").val()));
		$("#search-owner").val($.trim($("#hidden-owner").val()));
		$("#search-startDate").val($.trim($("#hidden-startDate").val()));
		$("#search-endDate").val($.trim($("#hidden-endDate").val()));

		$.ajax({
			url: "workbench/activity/pageList.do",
			data: {
				"search-name": $.trim($("#search-name").val()),
				"search-owner": $.trim($("#search-owner").val()),
				"search-startDate": $.trim($("#search-startDate").val()),
				"search-endDate": $.trim($("#search-endDate").val()),
				"pageNo": pageNo,
				"pageSize": pageSize
			},
			dataType: "json",
			type: "get",
			success: function (data) {
				let html = "";
				$.each(data.dataList, function (i, n) {
					html += "<tr class=\"active\">";
					html += "<td><input type=\"checkbox\" value='" + n.id + "' name='selected'/></td>";
					html += "<td><a style=\"text-decoration: none; cursor: pointer;\" onclick=\"window.location.href='workbench/activity/detail.do?id=" + n.id + "';\">" + n.name + "</a></td>";
					html += "<td>" + n.owner + "</td>";
					html += "<td>" + n.startDate + "</td>";
					html += "<td>" + n.endDate + "</td>";
					html += "</tr>";
				})

				$("#activityBody").html(html);

				let totalPages = data.total%pageSize===0?data.total/pageSize:parseInt(data.total/pageSize) + 1;

				$("#activityPage").bs_pagination({
					currentPage: pageNo, // 页码
					rowsPerPage: pageSize, // 每页显示的记录条数
					maxRowsPerPage: 20, // 每页最多显示的记录条数
					totalPages: totalPages, // 总页数
					totalRows: data.total, // 总记录条数

					visiblePageLinks: 3, // 显示几个卡片

					showGoToPage: true,
					showRowsPerPage: true,
					showRowsInfo: true,
					showRowsDefaultInfo: true,

					onChangePage : function(event, data){
						pageList(data.currentPage , data.rowsPerPage);
					}
				});
			}
		})
	}
	
</script>
</head>
<body>
	<input type="hidden" id="hidden-name">
	<input type="hidden" id="hidden-owner">
	<input type="hidden" id="hidden-startDate">
	<input type="hidden" id="hidden-endDate">
	<!-- 创建市场活动的模态窗口 -->
	<div class="modal fade" id="createActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel1">创建市场活动</h4>
				</div>
				<div class="modal-body">
				
					<form id="addActivityForm" class="form-horizontal" role="form">
					
						<div class="form-group">
							<label for="create-owner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-owner"></select>
							</div>
                            <label for="create-name" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-name">
                            </div>
						</div>
						
						<div class="form-group">
							<label for="create-startDate" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="create-startDate" readonly>
							</div>
							<label for="create-endDate" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="create-endDate" readonly>
							</div>
						</div>
                        <div class="form-group">

                            <label for="create-cost" class="col-sm-2 control-label">成本</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-cost">
                            </div>
                        </div>
						<div class="form-group">
							<label for="create-description" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="create-description"></textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" data-dismiss="modal" id="saveBtn">保存</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 修改市场活动的模态窗口 -->
	<div class="modal fade" id="editActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel2">修改市场活动</h4>
				</div>
				<div class="modal-body">

					<form class="form-horizontal" role="form">

						<input type="hidden" id="edit-id">

						<div class="form-group">
							<label for="edit-owner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-owner"></select>
							</div>
                            <label for="edit-name" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-name">
                            </div>
						</div>

						<div class="form-group">
							<label for="edit-startDate" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="edit-startDate" readonly>
							</div>
							<label for="edit-endDate" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="edit-endDate" readonly>
							</div>
						</div>

						<div class="form-group">
							<label for="edit-cost" class="col-sm-2 control-label">成本</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-cost">
							</div>
						</div>

						<div class="form-group">
							<label for="edit-description" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="edit-description"></textarea>
							</div>
						</div>

					</form>

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" data-dismiss="modal" id="updateBtn">更新</button>
				</div>
			</div>
		</div>
	</div>
	
	<div>
		<div style="position: relative; left: 10px; top: -10px;">
			<div class="page-header">
				<h3>市场活动列表</h3>
			</div>
		</div>
	</div>
	<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
		<div style="width: 100%; position: absolute;top: 5px; left: 10px;">
		
			<div class="btn-toolbar" role="toolbar" style="height: 80px;">
				<form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">名称</div>
				      <input class="form-control" type="text" id="search-name"/>
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">所有者</div>
				      <input class="form-control" type="text" id="search-owner"/>
				    </div>
				  </div>


				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">开始日期</div>
					  <input class="form-control" type="text" id="search-startDate"/>
				    </div>
				  </div>
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">结束日期</div>
					  <input class="form-control" type="text" id="search-endDate"/>
				    </div>
				  </div>
				  
				  <button type="button" id="searchBtn" class="btn btn-default">查询</button>
				  
				</form>
			</div>
			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
				<div class="btn-group" style="position: relative; top: 18%;">
				  <button type="button" class="btn btn-primary" id="createBtn"><span class="glyphicon glyphicon-plus"></span> 创建</button>
				  <button type="button" class="btn btn-default" id="editBtn"><span class="glyphicon glyphicon-pencil"></span> 修改</button>
				  <button type="button" class="btn btn-danger" id="delBtn"><span class="glyphicon glyphicon-minus"></span> 删除</button>
				</div>
				
			</div>
			<div style="position: relative;top: 10px;">
				<table class="table table-hover">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input type="checkbox" id="selAll"/></td>
							<td>名称</td>
                            <td>所有者</td>
							<td>开始日期</td>
							<td>结束日期</td>
						</tr>
					</thead>
					<tbody id="activityBody"></tbody>
				</table>
			</div>
			
			<div style="height: 50px; position: relative;top: 30px;">
				<div id="activityPage"></div>
			</div>
			
		</div>
		
	</div>
</body>
</html>
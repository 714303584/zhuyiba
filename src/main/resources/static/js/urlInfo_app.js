angular.module('urlInfo_app', [])
  .controller('UrlInfoController', function($scope, $http) {
    this.code;
    this.shortUrl;
    this.page = function(){
    	
    	
    	var InitTable = function (url) {
    	    //先销毁表格
    	    $('#tb_callList').bootstrapTable("destroy");
    	    //加载表格
    	    $('#tb_callList').bootstrapTable({
    	        rowStyle: function (row, index) {//row 表示行数据，object,index为行索引，从0开始
    	            var style = "";
    	            if (row.SignInTime == '' || row.SignOutTime=='') {
    	                style = { css: { 'color': 'red' } };
    	            }
    	            return  style;
    	        },
    	        searchAlign: 'left',
    	        search: true,   //显示隐藏搜索框
    	        showHeader: true,     //是否显示列头
    	      //  classes: 'table-no-bordered',
    	        showLoading: true,
    	        undefinedText: '',
    	        showFullscreen: true,
    	        toolbarAlign: 'left',
    	        paginationHAlign: 'right',
    	        silent: true,
    	        url: url,
    	        method: 'get',                      //请求方式（*）
    	        toolbar: '#toolbar',                //工具按钮用哪个容器
    	        striped: true,                      //是否显示行间隔色
    	        cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
    	        pagination: true,                   //是否显示分页（*）
    	        sortable: false,                     //是否启用排序
    	        sortOrder: "asc",                   //排序方式
    	        //queryParams: InitTable.queryParams,  //传递参数（*）
    	        sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
    	        pageNumber: 1,                       //初始化加载第一页，默认第一页
    	        pageSize: 10,                       //每页的记录行数（*）
    	        pageList: [2, 5, 10, 15],        //可供选择的每页的行数（*）
    	        search: false,                      //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
    	        strictSearch: true,
    	        showColumns: true,                  //是否显示所有的列
    	        showRefresh: true,                  //是否显示刷新按钮
    	        minimumCountColumns: 2,             //最少允许的列数
    	        clickToSelect: true,                //是否启用点击选中行
    	        //height: 680,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
    	        uniqueId: "ID",                     //每一行的唯一标识，一般为主键列
    	        showToggle: true,                    //是否显示详细视图和列表视图的切换按钮
    	        cardView: false,                    //是否显示详细视图
    	        detailView: false,                   //是否显示父子表
    	        showExport: true,
    	        //exportDataType: 'all',
    	        exportDataType: "selected",        //导出checkbox选中的行数
    	        paginationLoop: false,             //是否无限循环
    	        columns: [{
    	            checkbox: true
    	        }, {
    	                field: 'OrderNO',
    	                title: '订单编号'
    	        }, {
    	                field: 'ProductNo',
    	                title: '产品编号'
    	        }, {
    	                field: 'CustName',
    	                title: '客户姓名'
    	        }, {
    	                field: 'CustAddress',
    	                title: '客户地址',
    	        }, {
    	                field: 'CustPhone',
    	                title: '客户电话',
    	        }, {
    	                field: 'CustCompany',
    	                title: '客户公司',
    	        }, {
    	                field: 'CreateDateTime',
    	                title: '订单创建时间',
    	        }, {
    	                field: 'UpdateDateTime',
    	                title: '订单更新时间',
    	        }]
    	    });
    	    return InitTable;
    	};
    	
//    	//初始化
//    	var InitTable = function (url) {
//    	    //先销毁表格
//    	    $('#tb_callList').bootstrapTable("destroy");
//    	    //加载表格
//    	    $('#tb_callList').bootstrapTable({
//    	        rowStyle: function (row, index) {//row 表示行数据，object,index为行索引，从0开始
//    	            var style = "";
//    	            if (row.SignInTime == '' || row.SignOutTime=='') {
//    	                style = { css: { 'color': 'red' } };
//    	            }
//    	            return  style;
//    	        },
//    	        //searchAlign: 'left',
//    	        //search: true,   //显示隐藏搜索框
//    	        showHeader: true,     //是否显示列头
//    	        //classes: 'table-no-bordered',
//    	        showLoading: true,
//    	        undefinedText: '',
//    	        showFullscreen: true,
//    	        toolbarAlign: 'left',
//    	        paginationHAlign: 'right',
//    	        silent: true,
//    	        url: url,
//    	        method: 'get',                      //请求方式（*）
//    	        toolbar: '#toolbar',                //工具按钮用哪个容器
//    	        striped: true,                      //是否显示行间隔色
//    	        cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
//    	        pagination: true,                   //是否显示分页（*）
//    	        sortable: false,                     //是否启用排序
//    	        sortOrder: "asc",                   //排序方式
//    	        //queryParams: InitTable.queryParams,  //传递参数（*）
//    	        sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
//    	        pageNumber: 1,                       //初始化加载第一页，默认第一页
//    	        pageSize: 10,                       //每页的记录行数（*）
//    	        pageList: [2, 5, 10, 15],        //可供选择的每页的行数（*）
//    	        search: false,                      //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
//    	        strictSearch: true,
//    	        showColumns: true,                  //是否显示所有的列
//    	        showRefresh: true,                  //是否显示刷新按钮
//    	        minimumCountColumns: 2,             //最少允许的列数
//    	        clickToSelect: true,                //是否启用点击选中行
//    	        //height: 680,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
//    	        uniqueId: "ID",                     //每一行的唯一标识，一般为主键列
//    	        showToggle: true,                    //是否显示详细视图和列表视图的切换按钮
//    	        cardView: false,                    //是否显示详细视图
//    	        detailView: false,                   //是否显示父子表
//    	        showExport: true,
//    	        //exportDataType: 'all',
//    	        exportDataType: "selected",        //导出checkbox选中的行数
//    	        paginationLoop: false,             //是否无限循环
//    	        columns: [{
//    	            checkbox: true
//    	        }, {
//    	                field: 'OrderNO',
//    	                title: '订单编号'
//    	        }, {
//    	                field: 'ProductNo',
//    	                title: '产品编号'
//    	        }, {
//    	                field: 'CustName',
//    	                title: '客户姓名'
//    	        }, {
//    	                field: 'CustAddress',
//    	                title: '客户地址',
//    	        }, {
//    	                field: 'CustPhone',
//    	                title: '客户电话',
//    	        }, {
//    	                field: 'CustCompany',
//    	                title: '客户公司',
//    	        }, {
//    	                field: 'CreateDateTime',
//    	                title: '订单创建时间',
//    	        }, {
//    	                field: 'UpdateDateTime',
//    	                title: '订单更新时间',
//    	        }]
//    	    });
//    	    return InitTable;
//    	};
//    	
    	InitTable("");
    	
    	
    	
    	$http({
    	    method: 'GET',
    	    url: '/short/statistics/'+this.code,
    	    params: { 
        		page: 0,
        		pageSize: 10
        		} 
    	}).then(function successCallback(response) {
//	    		$scope.index.shortUrl = response.
    	    },
    	    function errorCallback(response) {
    	    	
    	    });
    };
  });
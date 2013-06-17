//tb_dish表创建和对表的操作
var tb_dish = new Chen.Table({
	container: "#m_dish",
	id: "tb_dish",
	theme: "Dish",
	load_method: "loadAll",
	th_name: ["菜品图片", "菜品名", "价格<元>", "当前价<元>", "主食材", "副食材", "销售量<份>"],
	labels: ["dish_pic", "dish_name", "dish_price", "dish_c_price", "m_mate", "v_mate", "dish_sales"],
	toolbar: [
		["add", "添加菜品"],
		["remove", "删除菜品"],
		["update", "更新菜品"]
	],
	initialize: false,
	pagenation: true,
	orderby: "dish_name",
	orderasc: "asc",
	query:true
});

tb_dish.tb_btn_override = function(btn) {
	if (btn == 'add') {
		// $("#dish_form").toggle();
		if (!this.tr_add) {
			// $('#myModal').modal();
			var addPanel = new Chen.Panel({
				container: "#panel",
				id: tb_dish.id + "_panel",
				theme: "Dish",
				load_method: "add",
				title: "添加菜品",
				type: "add",
				labels: ["菜品名称", "菜品图片", "菜品描述", "菜品价格", "菜品当前价", "菜品主食材", "菜品副食材"],
				inputnames: ["dish_name", "dish_pic", "dish_content", "dish_price", "dish_c_price", "m_mate", "v_mate"],
				inputtypes: ["text", "file", "text", "text", "text", "text", "text"],
				tb:this.id
			});
		
			addPanel.show();
		}
		return;
	}
	if (btn == 'remove') {
		if (this.row == null) {
			alert('please click some row');
			return;
		}
		if (!confirm('确认删除吗?')) {
			return false;
		}
		var rowid = this.row[0].id;
		this.tb_remove_ajax(rowid);


	}
	if (btn == 'update') {
		if (!this.tr_add) {
			if (this.row == null) {
				alert('please click some row');
				return;
			}
			if (!confirm('确认更新吗?')) {
				return false;
			}
			var res = this.res;
			var rows = res.rows;
			var row = "";

			var collspan = this.labels.length + 1;
			var rowid = this.row[0].id;
			var dbid = rowid.substring(rowid.lastIndexOf("_") + 1, rowid.length);
			for (var n = 0; n < rows.length; n++) {
				if (rows[n].id == dbid) row = rows[n];
			}

			var updatePanel = new Chen.Panel({
				container: "#panel",
				id: tb_dish.id + "_panel",
				theme: "Dish",
				load_method: "update",
				title: "添加菜品",
				type: "update",
				labels: ["菜品名称", "菜品图片", "菜品描述", "菜品价格", "菜品当前价", "菜品主食材", "菜品副食材"],
				inputnames: ["dish_name", "dish_pic", "dish_content", "dish_price", "dish_c_price", "m_mate", "v_mate"],
				inputtypes: ["text", "file", "text", "text", "text", "text", "text"],
				rowid:rowid,
				row:row,
				tb:this.id

			});
			updatePanel.show();
		}
		return;
	}

};




//tb_meterial表的创建和对表的操作

var tb_mate = new Chen.Table({
    container:"#m_material",
    id:"tb_mate",
    theme:"Material",
    load_method:"loadAll",
    th_name:["食材图片","食材名称","食材描述"],
    labels: ["mate_pic", "mate_name", "mate_content"],
    toolbar:[["add","添加食材"],["remove","删除食材"],["update","更新食材"]],
    initialize:false,
    pagenation:true,
    orderby:"mate_name",
    orderasc: "asc",
    query:true

});
tb_mate.tb_btn_override = function(btn) {
	if (btn == 'add') {
		// $("#mate_form").toggle();
		if (!this.tr_add) {
			// $('#myModal').modal();
			var addPanel = new Chen.Panel({
				container: "#panel",
				id: tb_mate.id + "_panel",
				theme: "Material",
				load_method: "add",
				title: "添加菜材",
				type: "add",
				labels: ["食材名称", "食材图片", "食材描述"],
				inputnames: ["mate_name", "mate_pic", "mate_content"],
				inputtypes: ["text", "file", "text"],
				tb:this.id
			});
		
			addPanel.show();
		}
		return;
	}
	if (btn == 'remove') {
		if (this.row == null) {
			alert('please click some row');
			return;
		}
		if (!confirm('确认删除吗?')) {
			return false;
		}
		var rowid = this.row[0].id;
		this.tb_remove_ajax(rowid);
	}
	if (btn == 'update') {
		if (!this.tr_add) {
			if (this.row == null) {
				alert('please click some row');
				return;
			}
			if (!confirm('确认更新吗?')) {
				return false;
			}
			var res = this.res;
			var rows = res.rows;
			var row = "";

			var collspan = this.labels.length + 1;
			var rowid = this.row[0].id;
			var dbid = rowid.substring(rowid.lastIndexOf("_") + 1, rowid.length);
			for (var n = 0; n < rows.length; n++) {
				if (rows[n].id == dbid) row = rows[n];
			}

			var updatePanel = new Chen.Panel({
				container: "#panel",
				id: tb_mate.id + "_panel",
				theme: "mate",
				load_method: "update",
				title: "添加食材",
				type: "update",
				labels: ["食材名称", "食材图片", "食材描述"],
				inputnames: ["mate_name", "mate_pic", "mate_content"],
				inputtypes: ["text", "file", "text"],
				rowid:rowid,
				row:row,
				tb:this.id

			});
			updatePanel.show();
		}
		return;
	}

};




//tb_order表的创建和对表的操作
 var tb_order = new Chen.Table({
      container:"#m_order",
     id:"tb_order",
     theme:"Order",
     load_method:"loadAll",
     th_name:["订单号","订单人","订单时间"],
     labels:["id","order_user","order_date"],
     toolbar:[["remove","删除"]],
     initialize:false,
     pagenation:true,
     query:true,
     orderby:"id",
     orderasc: "asc"
 });
 tb_order.tb_btn_override = function(btn) {
	
 	if (btn == 'remove') {
 		if (this.row == null) {
 			alert('please click some row');
 			return;
 		}
 		if (!confirm('确认删除吗?')) {
 			return false;
 		}
 		var rowid = this.row[0].id;
 		this.tb_remove_ajax(rowid);
 	}
 };
 
 
 
 
//tb_user表的创建和对表的操作

 var tb_user = new Chen.Table({
     container:"#m_user",
     id:"tb_user",
     theme:"User",
     load_method:"loadAll",
     th_name:["用户名","用户昵称","用户密码","用户Email"],
     labels: ["user_name", "user_niname", "user_password","user_email"],
     toolbar:[["add","添加用户"],["remove","删除用户"],["update","更新用户"]],
     initialize:false,
     pagenation:true,
     orderby:"user_name",
     orderasc: "asc",
     query:true

 });
 tb_user.tb_btn_override = function(btn) {
 	if (btn == 'add') {
 		// $("#mate_form").toggle();
 		if (!this.tr_add) {
 			// $('#myModal').modal();
 			var addPanel = new Chen.Panel({
 				container: "#panel",
 				id: tb_mate.id + "_panel",
 				theme: "Material",
 				load_method: "add",
 				title: "添加用户",
 				type: "add",
 				labels:["用户名","用户昵称","用户密码","用户Email"],
 				inputnames: ["user_name", "user_niname", "user_password","user_email"],
 				inputtypes: ["text", "text", "text","text"],
 				tb:this.id
 			});
 		
 			addPanel.show();
 		}
 		return;
 	}
 	if (btn == 'remove') {
 		if (this.row == null) {
 			alert('please click some row');
 			return;
 		}
 		if (!confirm('确认删除吗?')) {
 			return false;
 		}
 		var rowid = this.row[0].id;
 		this.tb_remove_ajax(rowid);
 	}
 	if (btn == 'update') {
 		if (!this.tr_add) {
 			if (this.row == null) {
 				alert('please click some row');
 				return;
 			}
 			if (!confirm('确认更新吗?')) {
 				return false;
 			}
 			var res = this.res;
 			var rows = res.rows;
 			var row = "";

 			var collspan = this.labels.length + 1;
 			var rowid = this.row[0].id;
 			var dbid = rowid.substring(rowid.lastIndexOf("_") + 1, rowid.length);
 			for (var n = 0; n < rows.length; n++) {
 				if (rows[n].id == dbid) row = rows[n];
 			}

 			var updatePanel = new Chen.Panel({
 				container: "#panel",
 				id: tb_mate.id + "_panel",
 				theme: "mate",
 				load_method: "update",
 				title: "更新用户",
 				type: "update",
 				labels:["用户名","用户昵称","用户密码","用户Email"],
 				inputnames: ["user_name", "user_niname", "user_password","user_email"],
 				inputtypes: ["text", "text", "text","text"],
 				rowid:rowid,
 				row:row,
 				tb:this.id

 			});
 			updatePanel.show();
 		}
 		return;
 	}

 };
	tb_user.tb_val= function(row) {
		var vals = [];
		for (var n = 0; n < this.labels.length; n++) {
			var val = '';

			try {
				eval("val =row." + this.labels[n] + ";");
			} catch (e) {}
			vals[n] = val;
		}
		return vals;
	};


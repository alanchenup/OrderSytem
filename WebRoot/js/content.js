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
		["delete", "删除菜品"],
		["update", "更新菜品"]
	],
	initialize: false,
	pagenation: true,
	orderby: "dish_name",
	orderasc: "asc"
});

tb_dish.tb_btn_override = function(btn) {
	if (btn == 'add') {
		// $("#dish_form").toggle();
		if (!this.tr_add) {

			var h = '<tr id="' + this.id + '_0"><td></td>';
			for (var n = 0; n < this.labels.length; n++) {
				h += '<td><input type="text" /></td>';
			}
			h += '</tr>'
			var collspan = this.labels.length + 1;
			var btn_id = this.id + "_0_btn";
			h += '<tr id="' + this.id + '_0_btn"><td colspan="' + collspan + '"><span><button class="btn btn-primary" type="button" onclick="Chen.TableManager.btn_add_ok(\'' + this.id + '\')">确定</button><button class="btn btn-primary" type="button" onclick="Chen.TableManager.btn_add_cel(\'' + this.id + '\')">取消</button></span></td></tr>'
			var th_id = this.id + '_th';
			$("#" + th_id).after(h);
			this.tr_add = true;
		} else {
			alert("请保存或取消你的新添加后继续操作！");
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
			var collspan = this.labels.length + 1;
			var rowid = this.row[0].id;
			var btn_id = rowid + "_btn";
			var tds = this.row.find("td");
			var h = '<tr id="' + rowid + '_n"><td><input type="radio"/></td>';
			for (var n = 0; n < this.labels.length; n++) {
				h += '<td><input type="text" value="' + tds[n + 1].innerHTML + '"/></td>';
			}
			h += '</tr>';
			h += '<tr id="' + btn_id + '"><td colspan="' + collspan + '"><span><button class="btn btn-primary" type="button" onclick="Chen.TableManager.btn_update_ok(\'' + this.id + '\',\'' + rowid + '\')">确定</button><button class="btn btn-primary" type="button" onclick="Chen.TableManager.btn_update_cel(\'' + this.id + '\',\'' + rowid + '\')">取消</button></span></td></tr>';
			this.row.after(h);
			this.row.hide();
			this.tr_update = true;
		} else {
			alert("请保存或取消你更新操作后继续操作！");
		}
		return;
	}

};



//tb_meterial表的创建和对表的操作

// var tb_meterial = new Table({
//     container:"#c_material",
//     id:"tb_dish",
//     theme:"Dish",
//     load_method:"loadAll",
//     th_name:["食材图片","食材名称","食材描述"],
//     toolbar:[["add","添加食材"],["delete","删除食材"],["update","更新食材"]],
//     initialize:false,
//     pagenation:true
//     orderby:mate
// });



//tb_order表的创建和对表的操作
// var tb_order = new Table({
//      container:"#c_order",
//     id:"tb_dish",
//     theme:"Dish",
//     load_method:"loadAll",
//     th_name:["图片","菜品名","价格<元>","当前价<元>","主食材","副食材","销售量<份>"],
//     toolbar:[["add","添加"],["delete","删除"]],
//     initialize:false,
//     pagenation:true
// });
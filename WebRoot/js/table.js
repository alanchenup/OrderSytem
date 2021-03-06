/**
 * 通过Class.create创建一个Table类
 */

Chen.Table = Class.create({
	//initialize是构造函数
	initialize: function(p) {
		//包含表格的div容器
		this.container = p.container;
		//表id
		this.id = p.id;
		//后台请求类名
		this.theme = p.theme;
		//请求方法名
		this.load_method = p.load_method;
		//表头名
		this.th_name = p.th_name;
		//要从json中取的字段名
		this.labels = p.labels;

		//支持操作按钮
		this.toolbar = p.toolbar;

		//支持查询
		this.query = p.query;
		this.keyword = ''; // Query用 keyword

		// 支持分页  true:false
		this.pagenation = p.pagenation;
		//每页数据行数
		this.pagesize = 10;
		//当前页数
		this.pageindex = 0;
		//总共有几页
		this.pagenum = 2;
		//总共有多少数据行
		this.count = 0;
		// 直接加载
		this.initialize = p.initialize;
		//
		this.order = p.order; // 支持排序

		this.orderby = p.orderby; // 默认
		this.orderasc = p.orderasc;
		this.editable = p.editable; // 支持编辑

		//定位到当前行
		this.row = false;

		//确定当前表是否有添加的tr
		this.tr_add = false;
		this.tr_update = false;
		//后端返回的每页的数据
		this.res = {};



		this.tb_init();
		Chen.TableManager.add(this);
	},
	//表初始化
	tb_init: function() {
		this.tb_cbtn();
		if (this.pagenation) this.tb_ctb();
		// if (this.initialize) this.tb_refresh();
	},
	//创建操作按钮
	tb_cbtn: function() {
		if (this.toolbar || this.query) {
			//表格和操作按钮的html代码
			var h = "<span>";
			//操作按钮渲染
			if (this.toolbar) {
				for (var i = 0; i < this.toolbar.length; i++) {
					var bar = this.toolbar[i];

					h += '<button id="' + this.id + '_btn_' + bar[0] + '" class="btn btn-primary" type="button" onclick="Chen.TableManager.tb_btn(\'' + this.id + '\',\'' + bar[0] + '\')">' + bar[1] + '</button>';
				}
			}
			if (this.query) {
				h += '<input type="text" class="input-medium search-query" data-provide="typeahead"> <button class="btn btn-primary">查询</button>';
			}
			h += '</span><hr>';
			$(this.container).append(h);
		}
	},
	//创建表
	tb_ctb: function() {
		var h = '';
		//创建表
		h += '<table class="table table-hover table-bordered table-striped" id="' + this.id + '">';
		h += '</table>';
		$(this.container).append(h);
		this.tb_idx(1);
	},
	//对分页按钮的处理
	tb_to: function(num) {
		this.tb_idx(num);
	},
	tb_pre: function() {
		if (this.pageindex <= 1) return;
		this.tb_idx(this.pageindex - 1);
	},
	tb_next: function() {
		if (this.pageindex >= this.pagenum) return;
		this.tb_idx(this.pageindex + 1);
	},
	
	
	tb_idx: function(idx) {
		if (idx > this.pagenum) return;
		this.pageindex = idx;
		this.tb_refresh();
	},
	tb_reload: function() {

		var param = {
			'method': this.load_method,
			'pagesize': this.pagesize,
			'pageindex': this.pageindex,
			'keyword': this.keyword,
			'orderby': this.orderby,
			'orderasc': this.orderasc
		};
		param.theme = this.theme;
		Chen.Ajax.ajax_send(param, this.tb_res.bind(this));
	},
	tb_res: function(vo) {
		this.res = vo;
		this.tb_set(vo.rows);
		if (this.pagenation) {
			this.tb_cpage(vo.page);
		}
	},
	//生成表头
	tb_ctbh: function() {
		var h = '<tr id="' + this.id + '_th">';
		h += '<th></th>';
		var i = 0;
		for (i = 0; i < this.th_name.length; i++) {
			h += '<th>' + this.th_name[i] + '</th>';
		}
		h += '</tr>';
		return h;

	},
	//创建分页
	tb_cpage: function(page) {

		var pageindex = page.pageindex;
		var pagenum = page.pagesize;
		//创建分页按钮

		var btn_page = '<div id="' + this.id + '_pagenation" class="pagination pagination-right">';
		if (!page.hasPre) {
			btn_page += '<ul> <li class="disabled"><a href="#">Prev</a></li>';
		} else {
			btn_page += '<ul> <li ><a href="#" onclick="Chen.TableManager.tb_pre(\'' + this.id + '\')">Prev</a></li>';
		}

		for (var i = 1; i <= page.pageall; i++) {
			if (i == page.pageindex) {
				btn_page += '<li class="active"><a href="#" onclick="Chen.TableManager.tb_to(\'' + this.id + '\',\'' + i + '\')">' + i + '</a></li>';
			} else {
				btn_page += '<li><a href="#" onclick="Chen.TableManager.tb_to(\'' + this.id + '\',\'' + i + '\')">' + i + '</a></li>';
			}

		}
		if (!page.hasNext) {
			btn_page += '<ul> <li class="disabled"><a href="#" >Next</a></li>';
		} else {
			btn_page += '<ul> <li ><a href="#" onclick="Chen.TableManager.tb_next(\'' + this.id + '\')">Next</a></li>';
		}
		if ($("#" + this.id + "_pagenation").length > 0) {
			$("#" + this.id + "_pagenation").replaceWith(btn_page);
		} else {
			$(this.container).append(btn_page);
		}

	},
	//根据返回的数据和字段去生成表格
	tb_set: function(rows) {
		var i = 0;

		//生成表头
		var tb_th = this.tb_ctbh();
		$("#" + this.id).append(tb_th);

		for (i = 0; i < rows.length; i++) {
			var row = rows[i];
			var rowid = this.id + "_" + row.id;
			var h = '<tr id="' + rowid + '"  onclick="Chen.TableManager.tb_check(\'' + this.id + '\',id)"><td><input type="radio" name="' + this.id + '"></td>';
			var vals = this.tb_val(row);
			for (var n = 0; n < vals.length; n++) {

				h += '<td>' + vals[n] + '</td>';

			}
			h += '</tr>';

			$("#" + this.id).append(h);
		}


	},
	tb_val: function(row) {
		var vals = [];
		for (var n = 0; n < this.labels.length; n++) {
			var val = '';

			try {
				eval("val =row." + this.labels[n] + ";");
				if (n == 0) {
					val = '<img src="http://localhost:8888/OrderSystem/pic?id=' + val + '"/>';
				}
			} catch (e) {}
			vals[n] = val;
		}
		return vals;
	},
	tb_click_all: function(id, o) {
		$("#" + id).children("[type=checkbox]").attr("checked", "true");
	},

	/**
	 * 表格左上角的按钮, 默认已经添加上了 refresh 按钮，如果需要其他的按钮功能这需要覆盖 tb_btn_override(btn) btn
	 * 是根据 this.toolbar 属性中的值依次传递的. 注意: 如果多于一个 refresh 按钮，那么一定要覆盖
	 * tb_btn_override(btn) 利用 btn 的值来编写按钮的操作.
	 *
	 * @param btn
	 *            按钮的名字（不一定是显示的名字）
	 */
	tb_btn: function(btn) {
		if (btn == 'refresh') {
			this.tb_refresh();
		} else {
			this.tb_btn_override(btn);
		}
	},
	tb_click: function(rowid) {



	},
	tb_check: function(rowid) {
		this.row = $("#" + rowid);
	},
	tb_add_ajax: function(id) {
		var inputs = $("#" + id + "_0").find("input[type='text']");
		var param = {};
		param.theme = this.theme;
		param.method = "add";
		for (var n = 0; n < inputs.length; n++) {
			eval("param." + this.labels[n] + "='" + inputs[n].value + "';");
		}
		Chen.Ajax.ajax_send(param, function(r) {
			if (r.flag) {
				alert("添加成功！");
			}
		});
		Chen.TableManager.btn_add_cel(id);

	},
	tb_update_ajax: function(id, rowid) {

		var dbid = rowid.substring(rowid.lastIndexOf("_") + 1, rowid.length);
		var inputs = $("#" + rowid + "_n").find("input[type='text']");
		var param = {};
		param.id = dbid;
		param.theme = this.theme;
		param.method = "update";
		for (var n = 0; n < inputs.length; n++) {
			eval("param." + this.labels[n] + "='" + inputs[n].value + "';");
		}
		Chen.Ajax.ajax_send(param, function(r) {
			if (r.flag) {
				alert("更新成功！");
			}
		});
		Chen.TableManager.btn_update_cel(id, rowid);

	},
	tb_remove_ajax: function(rowid) {
		var dbid = rowid.substring(rowid.lastIndexOf("_") + 1, rowid.length);
		var param = {};
		param.id = dbid;
		param.theme = this.theme;
		param.method = "delete";
		Chen.Ajax.ajax_send(param, this.r_tb_remove_ajax.bind(this));

	},
	//删除后的回调函数
	r_tb_remove_ajax: function(r) {
		if (r.flag) {

			alert("删除成功！");
			Chen.TableManager.tb_refresh(this.id);
		}

	},

	tb_refresh: function() {
		$("#" + this.id).empty();
		this.tb_reload();
	}

});

Chen.TableManager = {};
Object.extend(Chen.TableManager, {
	TABLES: [],
	radio: false,
	get: function(id) {
		for (var i = this.TABLES.length - 1; i >= 0; i--) {
			if (this.TABLES[i].id == id) {
				return this.TABLES[i];
			}
		}
		return null;
	},
	add: function(t) {
		this.TABLES.push(t);
	},
	remove: function(id) {
		var idx = -1;
		for (var i = 0; i < this.TABLES.length; i++) {
			if (this.TABLES[i].id == id) {
				idx = i;
				break;
			}
		}
		if (idx > -1) {
			this.TABLES = this.TABLES.del(idx);
		}
	},

	// 全选 点击 勾选 下拉 Query 按钮
	tb_click_all: function(id, o) {
		var t = this.get(id);
		if (t != null) {
			t.tb_click_all(id);
		}
	},
	tb_click: function() {
		if (Chen.TableManager.radio) {
			Chen.TableManager.radio = false;
			return;
		}
		var table = Chen.TableManager.get(this.tid);
		if (table.row) table.row.className = '';
		table.row = this;
		table.row.className = 'cur';
		if (table.trtype == 1) {
			table.row.getElementsByTagName('input')[0].checked = true;
		}
		table.tb_click(this);
	},
	tb_check: function(id, rowid) {
		Chen.TableManager.radio = true;
		var t = this.get(id);
		if (t != null) {
			t.tb_check(rowid);
		}
	},
	tb_leibie: function(id) {
		var t = this.get(id);
		if (t != null) {
			t.tb_leibie();
		}
	},
	tb_search: function(id) {
		var t = this.get(id);
		if (t != null) {
			t.tb_search();
		}
	},
	tb_btn: function(id, btn) {
		var t = this.get(id);
		if (t != null) {
			t.tb_btn(btn);
		}
	},
	tb_order: function(id, order) {
		var t = this.get(id);
		if (t != null) {
			t.tb_order(order);
		}
	},
	tb_btn_txt: function(id, txt, w) {
		var btn = $('btn_' + id).childNodes[0].childNodes[0];
		btn.innerHTML = txt;
		$('btn_' + id).width = w;
	},
	btn_add_ok: function(id) {
		var t = this.get(id);
		if (t != null) {
			t.tb_add_ajax(id);
		}
	},
	btn_add_cel: function(id) {
		var t = this.get(id);
		if (t != null) {
			$("#" + id + "_0").remove();
			$("#" + id + "_0_btn").remove();
			t.tr_add = false;
		}
	},
	btn_update_ok: function(id, rowid) {
		var t = this.get(id);
		if (t != null) {
			t.tb_update_ajax(id, rowid);
		}

	},
	btn_update_cel: function(id, rowid) {
		var t = this.get(id);
		if (t != null) {
			$("#" + rowid + "_n").remove();
			$("#" + rowid + "_btn").remove();
			$("#" + rowid).show();
			t.tr_update = false;
		}

	},
	tb_refresh: function(id) {
		var t = this.get(id);
		if (t != null) {
			t.tb_refresh();
		}
	},
	tb_to: function(id, idx) {
		var t = this.get(id);
		if (t != null) {
			t.tb_to(idx);
		}
	},
	tb_pre: function(id) {
		var t = this.get(id);
		if (t != null) {
			t.tb_pre();
		}
	},
	tb_next: function(id) {
		var t = this.get(id);
		if (t != null) {
			t.tb_next();
		}
	}


});
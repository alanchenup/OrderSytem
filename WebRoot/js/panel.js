Chen.Panel = Class.create({
	//initialize是构造函数
	initialize: function(p) {
		//包含Panel的div容器
		this.container = p.container;
		//Panel id
		this.id = p.id;
		//后台请求类名
		this.theme = p.theme;
		//请求方法名
		this.load_method = p.load_method;
		//标题
		this.title = p.title;
		//panel类型 是添加还是更新
		this.type = p.type;
		//标签名
		this.labels = p.labels;
		//input标签name保持与数据库字段一致
		this.inputnames = p.inputnames;
		//input标签类型
		this.inputtypes = p.inputtypes;
		//input值
		this.values = [];
		//update时需要rowid
		this.rowid = p.rowid;
		this.row = p.row;
		//属于哪个表格的panel
		this.tb=p.tb;
		//判断是否操作成功
		this.flag=false;

		this.panel_init();
		Chen.PanelManager.add(this);
	},
	panel_init: function() {
		var h = '<div id="' + this.id + '" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">';
		if (this.type == "add") h += this.panel_add();
		if (this.type == "update") h += this.panel_update();
		h += '</div>';
		$(this.container).html(h);
		$(this.container).show();
		return;

	},
	panel_header: function() {
		var h = '<div class="modal-header">';
		h += '<button type="button" class="close" data-dismiss="modal" aria-hidden="true" >×</button>';
		h += '<h3 id="myModalLabel">' + this.title + '</h3>';
		h += '</div>'
		return h;

	},
	panel_body: function() {
		var h = '<div class="modal-body">';
		if (this.type == "add") {
			for (var n = 0; n < this.labels.length; n++) {
				h += '<label>' + this.labels[n] + ':</label>';
				if (this.inputtypes[n] == "file") {
					h += '<span id="updatefile" ><input id="file" type="file" name="image"/><button class="btn btn-primary"  onclick="return Chen.PanelManager.ajaxFileUpload(\'' + this.id + '\');">上传</button></span>';
				} else {

					h += '<input id="' + this.inputnames[n] + '" type="' + this.inputtypes[n] + '" name="' + this.inputnames[n] + '"/><br>';

				}


			}
		}
		if (this.type == "update") {
			for (var n = 0; n < this.labels.length; n++) {
				eval('var value=this.row.' + this.inputnames[n] + ';');
				h += '<label>' + this.labels[n] + ':</label>';

				if (this.inputtypes[n] == "file") {
					// h += '<span id="updatefile" ><input id="file" type="file" name="image"/></span>';
					h += '<span id=updatefile"><img src="/OrderSystem/pic?id=' + this.row.dish_pic + '"/><input type="hidden" value="' + this.row.dish_pic+ '"/><button class="btn btn-primary"  onclick="return Chen.PanelManager.changeImage(\'' + this.id + '\');">上传</button></span>';

				} else {

					h += '<input id="' + this.inputnames[n] + '" type="' + this.inputtypes[n] + '" name="' + this.inputnames[n] + '" value="' + value + '"/><br>';

				}


			}


		}
		h += '</div>';
		return h;

	},
	panel_footer: function() {

		var h = '<div class="modal-footer">';
		h += '<button class="btn" data-dismiss="modal" aria-hidden="true" >关闭</button>';
		h += '<button class="btn btn-primary"  onclick="Chen.PanelManager.panel_send(\'' + this.id + '\')">' + this.title + '</button>';
		h += '</div>';
		return h;

	},
	panel_add: function() {
		var h = this.panel_header();
		h += this.panel_body();
		h += this.panel_footer();
		return h;

	},
	panel_update: function() {
		var h = this.panel_header();
		h += this.panel_body();
		h += this.panel_footer();
		return h;


	},
	panel_send: function() {
		var inputs = $("#" + this.id).find("input");


		var param = {};
		param.theme = this.theme;
		param.method = this.load_method;
		if (this.row!= null) {
			param.id = this.row.id;
		}

		for (var n = 0; n < this.inputnames.length; n++) {
			var name = this.inputnames[n];
			var value = inputs[n].value;

			eval("param." + name + "= value ;");
		}
		Chen.Ajax.ajax_send(param, this.send_over.bind(this));
	},
	panel_cancel: function() {
		$("#" + this.id).replaceWith(' <div id="panel" class="hide"></div>');

	},
	show: function() {
		$("#" + this.id).modal();
	},
	ajaxFileUpload: function() {

		$.ajaxFileUpload({
			url: '/OrderSystem/pic', //用于文件上传的服务器端请求地址
			secureuri: false, //一般设置为false
			fileElementId: 'file', //文件上传空间的id属性  <input type="file" id="file" name="file" />
			dataType: 'json', //返回值类型 一般设置为json
			success: function(data, status) //服务器成功响应处理函数
			{

				alert("上传成功！");
				$('#updatefile').html('<img src="/OrderSystem/pic?id=' + data.id + '"/><input type="hidden" value="' + data.id + '"/>');

				return true;

			},
			error: function(data, status, e) //服务器响应失败处理函数
			{
				alert(e);
			}
		})

		return false;
	},
	send_over: function(r) {
		if (r.flag) {
			this.flag=true;
			$("#" + this.id).modal('hide');
			$(this.container).hide();
			$(this.container).html("");
			Chen.TableManager.tb_refresh(this.tb);
		}

	}

});

Chen.PanelManager = {};
Object.extend(Chen.PanelManager, {
	PANELS: [],
	get: function(id) {
		for (var i = this.PANELS.length - 1; i >= 0; i--) {
			if (this.PANELS[i].id == id) {
				return this.PANELS[i];
			}
		}
		return null;
	},
	add: function(t) {
		this.PANELS.push(t);
	},
	remove: function(id) {
		var idx = -1;
		for (var i = 0; i < this.PANELS.length; i++) {
			if (this.PANELS[i].id == id) {
				idx = i;
				break;
			}
		}
		if (idx > -1) {
			this.PANELS = this.PANELS.del(idx);
		}
	},
	panel_send: function(id) {
		var panel = this.get(id);
		if (panel != null) {
			panel.panel_send();
		}

	},
	panel_cancel: function(id) {
		var panel = this.get(id);
		if (panel != null) {
			panel.panel_cancel();
		}
	},

	ajaxFileUpload: function(id) {
		var panel = this.get(id);
		if (panel != null) {
			panel.ajaxFileUpload();
		}


	}
});
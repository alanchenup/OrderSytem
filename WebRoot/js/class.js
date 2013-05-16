
 var Chen={};
var Prototype = {
    emptyFunction: function() {}
};

var Class = {
    create: function() {
        // 如果第一个参数是函数，则作为父类
        var parent = null,
            properties = $A(arguments);
        if (Object.isFunction(properties[0])) parent = properties.shift();
        // 子类构造函数的定义

        function klass() {
            this.initialize.apply(this, arguments);
        }
        // 为子类添加原型方法Class.Methods.addMethods
        Object.extend(klass, Class.Methods);
        // 不仅为当前类保存父类的引用，同时记录了所有子类的引用
        klass.superclass = parent;
        klass.subclasses = [];

        if (parent) {
            // 核心代码 - 如果父类存在，则实现原型的继承
            // 这里为创建类时不调用父类的构造函数提供了一种新的途径
            // - 使用一个中间过渡类，这和我们以前使用全局initializing变量达到相同的目的，
            // - 但是代码更优雅一点。
            var subclass = function() {};
            subclass.prototype = parent.prototype;
            klass.prototype = new subclass;
            parent.subclasses.push(klass);
        }
        // 核心代码 - 如果子类拥有父类相同的方法，则特殊处理，将会在后面详解
        for (var i = 0; i < properties.length; i++)
        klass.addMethods(properties[i]);

        if (!klass.prototype.initialize) klass.prototype.initialize = Prototype.emptyFunction;
        // 修正constructor指向错误
        klass.prototype.constructor = klass;

        return klass;
    }
};

Class.Methods = {
    // 如果父类存在，ancestor指向父类的原型对象
    addMethods: function(source) {
        var ancestor = this.superclass && this.superclass.prototype;
        var properties = Object.keys(source);
        // Firefox和Chrome返回1，IE8返回0，所以这个地方特殊处理
        if (!Object.keys({
            toString: true
        }).length) properties.push("toString", "valueOf");
        // 循环子类原型定义的所有属性，对于那些和父类重名的函数要重新定义
        for (var i = 0, length = properties.length; i < length; i++) {
            // property为属性名，value为属性体（可能是函数，也可能是对象）
            var property = properties[i],
                value = source[property];
            // 如果父类存在，并且当前当前属性是函数，并且此函数的第一个参数为 $super
            if (ancestor && Object.isFunction(value) && value.argumentNames().first() == "$super") {
                var method = value;
                // 下面三行代码是精华之所在，大概的意思：
                // - 首先创建一个自执行的匿名函数返回另一个函数，此函数用于执行父类的同名函数
                // - （因为这是在循环中，我们曾多次指出循环中的函数引用局部变量的问题）
                // - 其次把这个自执行的匿名函数的作为method的第一个参数（也就是对应于形参$super）
                // 不过，窃以为这个地方作者有点走火入魔，完全没必要这么复杂，后面我会详细分析这段代码。
                value = (function(m) {
                    return function() {
                        return ancestor[m].apply(this, arguments)
                    };
                })(property).wrap(method);

                value.valueOf = method.valueOf.bind(method);
                // 因为我们改变了函数体，所以重新定义函数的toString方法
                // 这样用户调用函数的toString方法时，返回的是原始的函数定义体
                value.toString = method.toString.bind(method);
            }
            this.prototype[property] = value;
        }

        return this;
    }
};

Object.extend = function(destination, source) {
    for (var property in source)
    destination[property] = source[property];
    return destination;
};

function $A(iterable) {
    if (!iterable) return [];
    if (iterable.toArray) return iterable.toArray();
    var length = iterable.length || 0,
        results = new Array(length);
    while (length--) results[length] = iterable[length];
    return results;
}

Object.extend(Object, {
    keys: function(object) {
        var keys = [];
        for (var property in object)
        keys.push(property);
        return keys;
    },
    isFunction: function(object) {
        return typeof object == "function";
    },
    isUndefined: function(object) {
        return typeof object == "undefined";
    },
    toJSON : function(object) {
                var type = typeof object;
                switch (type) {
                    case 'undefined' :
                    case 'function' :
                    case 'unknown' :
                        return;
                    case 'boolean' :
                        return object.toString();
                }
                if (object === null)
                    return 'null';
                if (object.toJSON)
                    return object.toJSON();

                var results = [];
                for (var property in object) {
                    var value = Object.toJSON(object[property]);
                    if (!Object.isUndefined(value))
                        results.push('"' + property.toJSON() + '":"' + value
                                + '"');
                }

                return '{' + results.join(',') + '}';
            }
});

Object.extend(Function.prototype, {
    //argumentNames: 获取函数的参数数组        
    argumentNames: function() {
        var names = this.toString().match(/^[\s\(]*function[^(]*\(([^\)]*)\)/)[1].replace(/\s+/g, '').split(',');
        return names.length == 1 && !names[0] ? [] : names;
    },
    //bind: 绑定函数的上下文this到一个新的对象（一般是函数的第一个参数） 
    bind: function() {
        if (arguments.length < 2 && Object.isUndefined(arguments[0])) return this;
        var __method = this,
            args = $A(arguments),
            object = args.shift();
        return function() {
            return __method.apply(object, args.concat($A(arguments)));
        }
    },
    //wrap: 把当前调用函数作为包裹器wrapper函数的第一个参数 
    wrap: function(wrapper) {
        var __method = this;
        return function() {
            return wrapper.apply(this, [__method.bind(this)].concat($A(arguments)));
        }
    }
});

Object.extend(Array.prototype, {
    first: function() {
        return this[0];
    }
});


// 公共工具类

// 添加静态方法


//Ajax处理
Chen.Ajax = {};
Object.extend(Chen.Ajax ,{
    ajax_send: function(param,cback){
    jQuery.ajax({
      url: '/OrderSystem/ajax',
      type: 'POST',
      dataType: 'json',
      data: param,
      complete: function(xhr, textStatus) {
        //called when complete
      },
      success: function(data, textStatus, xhr) {
        //called when successful
        cback(data);
      },
      error: function(xhr, textStatus, errorThrown) {
        //called when there is an error
      }
    });
    
    }
});
/*
 * 常量
 * 1、DataTable
 * 		<p>相关属性配置，请参考：http://www.cnblogs.com/liang-ling/p/5853156.html <p>
 * **/
var CONSTANT = {
    DATA_TABLES: {
        DEFAULT_OPTION: { //DataTables初始化选项
            language: {
                "sProcessing": "处理中...",
                "sLengthMenu": "每页 _MENU_ 项",
                "sZeroRecords": "没有匹配结果",
                "sInfo": "<span class='page_total'>共<span class='page_number'>_PAGES_</span>页,<span class='page_number'> _TOTAL_</span> 条</span><span class='jump_to'>到第<input type='input' class='input_page searchPageNum' >页&nbsp;&nbsp;<button type='button' class='btn btn-primary searchPageButton'>确认</button>",
                "sInfoEmpty": "当前显示第 0 至 0 项，共 0 项",
                "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
                "sInfoPostFix": "",
                "sSearch": "搜索:",
                "sUrl": "",
                "sEmptyTable": "表中数据为空",
                "sLoadingRecords": "载入中...",
                "sInfoThousands": ",",
                "oPaginate": {
                    "sFirst": "首页",
                    "sPrevious": "<上一页",
                    "sNext": "下一页>",
                    "sLast": "末页",
                    "sJump": "跳转"
                },
                "oAria": {
                    "sSortAscending": ": <img>",
                    "sSortDescending": ": <img>"
                }
            },
            autoWidth: false,	//禁用自动调整列宽
            stripeClasses: ["odd", "even"],//为奇偶行加上样式，兼容不支持CSS伪类的场合
            order: [],			//取消默认排序查询,否则复选框一列会出现小箭头
            processing: false,	//隐藏加载提示,自行处理
            serverSide: true,	//启用服务器端分页
            bInfo: true,
            sPaginationType: "simple_numbers",
            bLengthChange: false, //隐藏（SELECT选择每页显示10,20,50）
            bSort: false, //取消排序
            bSortClasses: false, //隐藏排序样式(classes 'sorting_1', 'sorting_2' and 'sorting_3')
            dom: 'rt<"bottom"pi>', //控件显示顺序
            searching: false	//禁用原生搜索

        },
        COLUMN: {
            CHECKBOX: {	//复选框单元格
                className: "td-checkbox",
                orderable: false,
                width: "30px",
                data: null,
                render: function (data, type, row, meta) {
                    return '<input type="checkbox" class="iCheck">';
                }
            }
        },
        RENDER: {	//常用render可以抽取出来，如日期时间、头像等
            ELLIPSIS: function (data, type, row, meta) {
                data = data || "";
                return '<span title="' + data + '">' + data + '</span>';
            }
        },
        PAGE: {
            getQueryCondition: function (data, searchForm) {
                var param = {};
                var orderby;
                //组装排序参数
                if (data.order && data.order.length && data.order[0]) {
                    param.orderBy = data.columns[data.order[0].column].data + " " + data.order[0].dir; //排序参数
                }
                //组装查询参数
                if (searchForm) {
                    //把form里面的数据序列化成数组
                    var formData = $(searchForm).serializeArray();
                    formData.forEach(function (e) {
                        param[e.name] = e.value;
                    });
                }
                //组装分页参数
                param.startIndex = data.start;
                param.pageSize = data.length;
                param.pageNum = (data.start / data.length) + 1;//当前页码
                param.draw = data.draw;

                //输出参数，方便调试
                console.log(param);
                return param;
            }
        }

    },
    DICTIONARY: {
        DATA_TYPE: "datatype",
        DATA_VALUE: "datavalue",
        /*SHOW_TYPE ：funtion(){

        }*/
        DATA_REFER: "datarefer",
        VALUES: "",
        CACHE_NAME: "dictionary_map"
    },
    AREA: {
        DATA_AREA: "dataarea",
        DATA_VALUE: "datavalue",
        DATA_REFER: "datarefer"
    },
    SMS: {
        COUNT: 60 //倒计时默认60秒
    }
};

var ToolApi = {
    ajax: {
        //注意：contentType默认值为application/x-www-form-urlencoded;charset=utf-8，springMVC不能接受json自动封装成实体
        //当contentType为application/json的时候，controller直接获取参数，获取不到
        ajaxPost: function (url, params, callback) {
            if (params && typeof params == "object") {
                params = deleteEmptyProp(params);
            }
            jQuery.ajax({
                url: url,
                type: 'post',
                data: JSON.stringify(params),
                dataType: 'json',
                contentType: 'application/json',
                beforSend: function () {
                },
                success: function (result) {//请求成功后调用的回调函数
                    if (result && result.code && result.code < 0) {
                        /*modals.error({
                            text:result.msg,
                            large: true
                        });
                        return false;*/
                        console.log(result.msg);
                    } else if (result && result.code && result.code > 0) {
                        /*modals.error({
                            text:'操作失败，具体错误：' + result.message,
                            large: true
                        });
                        return false;*/
                        console.log('操作失败，具体错误：' + result.message);
                    }
                    if (callback) {
                        callback.call(this, result);
                    }
                },
                error: function (err, err1, err2) {
                    console.log("ajaxPost发生异常，请仔细检查请求url是否正确，如下面错误信息中出现success，则表示csrftoken更新，请忽略");
                    if (err && err.readyState && err.readyState == '4') {
                        var sessionstatus = err.getResponseHeader("session-status");
                        if (sessionstatus == "timeout") {
                            //如果超时就处理 ，指定要跳转的页面
                            window.location.href = basePath + "/";
                        } else if (err1 == "parsererror") {//csrf异常
                            var responseBody = err.responseText;
                            if (responseBody) {
                                responseBody = "{'retData':" + responseBody;
                                var resJson = eval('(' + responseBody + ')');
                                jQuery("#csrftoken").val(resJson.csrf.CSRFToken);
                                this.success(resJson.retData, 200);
                            }
                            return;
                        } else {
                            modals.error({
                                /*text: JSON.stringify(err) + '<br/>err1:' + JSON.stringify(err1) + '<br/>err2:' + JSON.stringify(err2),*/
                                text: '系统异常，请联系管理员。',
                                large: true
                            });
                            return;
                        }
                    }

                    modals.error({
                        /*text: JSON.stringify(err) + '<br/>err1:' + JSON.stringify(err1) + '<br/>err2:' + JSON.stringify(err2),*/
                        text: '系统异常，请联系管理员。',
                        large: true
                    });
                },
                complete: function (msg) {
                }
            })
        },
        ajaxPut: function (url, params, callback) {
            if (params && typeof params == "object") {
                params = deleteEmptyProp(params);
            }
            jQuery.ajax({
                url: url,
                type: 'put',
                data: JSON.stringify(params),
                dataType: 'json',
                contentType: 'application/json',
                beforSend: function () {
                },
                success: function (result) {
                    if (result && result.code && result.code < 0) {
                        modals.error({
                            text: result.msg,
                            large: true
                        });
                        return false;
                    } else if (result && result.code && result.code > 0) {
                        modals.error({
                            text: '操作失败，具体错误：' + result.message,
                            large: true
                        });
                        return false;
                    }
                    if (callback) {
                        callback.call(this, result);
                    }
                },
                error: function (err, err1, err2) {
                    modals.error({
                        /*text: JSON.stringify(err) + '<br/>err1:' + JSON.stringify(err1) + '<br/>err2:' + JSON.stringify(err2),*/
                        text: '系统异常，请联系管理员。',
                        large: true
                    });
                    return;
                },
                complete: function (msg) {
                }
            })
        },
        ajaxGet: function (url, params, callback) {
            if (params && typeof params == "object") {
                params = deleteEmptyProp(params);
            }
            jQuery.ajax({
                url: url,
                type: 'get',
                data: params,
                beforSend: function () {
                },
                success: function (result) {
                    if (result && result.code && result.code < 0) {
                        /*modals.error({
                            text:result.msg,
                            large: true
                        });
                        return false;*/
                        console.log(result.msg);
                    } else if (result && result.code && result.code > 0) {
                        /*modals.error({
                            text:'操作失败，具体错误：' + result.message,
                            large: true
                        });
                        return false;*/
                        console.log('操作失败，具体错误：' + result.message);
                    }
                    if (callback) {
                        callback.call(this, result);
                    }
                },
                error: function (err, err1, err2) {
                    modals.error({
                        /*text: JSON.stringify(err) + '<br/>err1:' + JSON.stringify(err1) + '<br/>err2:' + JSON.stringify(err2),*/
                        text: '系统异常，请联系管理员。',
                        large: true
                    });
                    return;
                },
                complete: function (msg) {

                }
            })
        },
        ajaxDelete: function (url, params, callback) {
            if (params && typeof params == "object") {
                params = deleteEmptyProp(params);
            }
            jQuery.ajax({
                url: url,
                type: 'delete',
                data: params,
                beforSend: function () {
                },
                success: function (result) {
                    if (result && result.code && result.code < 0) {
                        modals.error({
                            text: result.msg,
                            large: true
                        });
                        return false;
                    } else if (result && result.code && result.code > 0) {
                        modals.error({
                            text: '操作失败，具体错误：' + result.message,
                            large: true
                        });
                        return false;
                    }
                    if (callback) {
                        callback.call(this, result);
                    }
                },
                error: function (err, err1, err2) {
                    modals.error({
                        /*text: JSON.stringify(err) + '<br/>err1:' + JSON.stringify(err1) + '<br/>err2:' + JSON.stringify(err2),*/
                        text: '系统异常，请联系管理员。',
                        large: true
                    });
                    return;
                },
                complete: function (msg) {
                }
            })
        },
        formData: function (formData, url, callback, errorback) {
            //遮盖层
            var $wrapper = $(document.body);
            $.ajax({
                type: 'post',
                url: url,
                data: formData,
                dataType: "json",
                contentType: false,// 当有文件要上传时，此项是必须的，否则后台无法识别文件流的起始位置(详见：#1)
                processData: false,// 是否序列化data属性，默认true(注意：false时type必须是post，详见：#2)
                beforeSend: function () {
                    $wrapper.spinModal();
                },
                success: function (result) {
                    callback(result);
                    $wrapper.spinModal(false);
                },
                error: function (errorMsg) {
                    $wrapper.spinModal(false);
                },
                complete: function (XMLHttpRequest, status) {
                    $wrapper.spinModal(false);
                }
            })
        }
    },
    file: {
        //上传
        upload: function (formData, callback, errorback) {
            var url = basePath + "/resource/rest/upload";
            ToolApi.ajax.formData(formData, url, callback, errorback);
        },
        //下载
        download: function (ywid) {
            var url = basePath + "/resource/download?id=" + ywid;
            window.open(url);
        },
        //获取资源对象
        get: function (ywid, callback) {
            var url = basePath + "/resource/rest/" + ywid;
            $.ajax({
                type: 'GET',
                url: url,
                dataType: "json",
                beforeSend: function () {

                },
                success: function (result) {
                    callback(result);
                },
                error: function (errorMsg) {
                },
                complete: function (XMLHttpRequest, status) {

                }
            })
        },
        //删除资源对象
        del: function (resourceid, callback) {
            var url = basePath + "/resource/rest/" + resourceid;
            $.ajax({
                type: 'delete',
                url: url,
                beforeSend: function () {

                },
                success: function (result) {
                    if (callback) {
                        callback.call(this, result);
                    }
                },
                error: function (errorMsg) {
                },
                complete: function (XMLHttpRequest, status) {

                }
            })
        }
    },
    dictionary: {
        CODEMAP: {},
        isFirst: true,
        init: function (noLoad, cb, datatypes) {
            if (!dictionaryMap || dictionaryMap == '' || dictionaryMap == 'null') {
                var _params = {};
                var url = '';
                if (!ToolApi.dictionary.isFirst && datatypes && datatypes != '') {
                    url = basePath + "/dictionary/findByDatatypes";
                    _params.datatypes = datatypes;
                } else {
                    url = basePath + "/dictionary/listAll";
                }
                ToolApi.dictionary.isFirst = false;

                ToolApi.ajax.ajaxGet(url, _params, function (result) {
                    ToolApi.dictionary.CODEMAP = new ToolApi.Map();
                    var TYPEMAP;
                    $(result).each(function (_i, _o) {
                        //是否存在此类型的字典
                        if (ToolApi.dictionary.CODEMAP.containsKey(_o.datatype)) {
                            TYPEMAP = ToolApi.dictionary.CODEMAP.get(_o.datatype);
                            TYPEMAP.put(_o.value, _o.display);
                            ToolApi.dictionary.CODEMAP.remove(_o.datatype);
                            ToolApi.dictionary.CODEMAP.put(_o.datatype, TYPEMAP);
                        } else {
                            TYPEMAP = new ToolApi.Map();
                            TYPEMAP.put(_o.value, _o.display);
                            ToolApi.dictionary.CODEMAP.put(_o.datatype, TYPEMAP);
                        }
                    });
                    dictionaryMap = JSON.stringify(ToolApi.dictionary.CODEMAP);
                    //set到全局缓存
                    window.sessionStorage.setItem(CONSTANT.DICTIONARY.CACHE_NAME, dictionaryMap);
                    if (!noLoad) {
                        ToolApi.dictionary.load(ToolApi.dictionary.CODEMAP);
                    }
                    if (cb && jQuery.isFunction(cb)) {
                        cb();
                    }
                });
            } else {
                ToolApi.dictionary.CODEMAP = new ToolApi.Map(JSON.parse(dictionaryMap));
                if (!noLoad) {
                    ToolApi.dictionary.load(ToolApi.dictionary.CODEMAP);
                }
                if (jQuery.isFunction(cb)) {
                    cb();
                }
            }
        },
        load: function (_CODEMAP) {
            //加载SELECT标签
            $("select").each(function (_i, _o) {
                var datatype = $(_o).attr(CONSTANT.DICTIONARY.DATA_TYPE);
                var datavalue = $(_o).attr(CONSTANT.DICTIONARY.DATA_VALUE);
                var datafilter = $(_o).attr("datafilter");
                if (typeof (datatype) != "undefined" && datatype !== null && datatype !== "0") {
                    var typeMap = _CODEMAP.get(datatype);
                    var _html = "<option value=\"\">请选择..</option>";
                    if (typeMap && typeMap.elements && typeMap.elements.length > 0) {
                        for (var i in typeMap.elements) {
                            var map = typeMap.elements[i];
                            if (datafilter) {
                                if (datafilter.indexOf(map.key + ',') >= 0) {
                                    if (map.key === datavalue) {
                                        _html += "<option value=\"" + map.key + "\" selected>" + map.value + "</option>";
                                    } else {
                                        _html += "<option value=\"" + map.key + "\">" + map.value + "</option>";
                                    }
                                }
                            } else {
                                if (map.key === datavalue) {
                                    _html += "<option value=\"" + map.key + "\" selected>" + map.value + "</option>";
                                } else {
                                    _html += "<option value=\"" + map.key + "\">" + map.value + "</option>";
                                }
                            }
                        }
                    }

                    $(_o).empty();
                    $(_o).append(_html);
                }
            });

            //其他页签
        },
        get: function (datatype) {
            if (!dictionaryMap || dictionaryMap.length == 0) {
                ToolApi.dictionary.init();
            }
            var _CODEMAP = new ToolApi.Map(JSON.parse(dictionaryMap));
            for (var i in _CODEMAP.elements) {
                if (_CODEMAP.elements[i].key === datatype) {
                    return new ToolApi.Map(_CODEMAP.elements[i].value);
                }
            }
            return '';
        },
        getMap: function () {
            return new ToolApi.Map(JSON.parse(dictionaryMap));
        }
    },
    //自动下拉
    autoSelect: {
        init: function () {

        }
    },
    area: {
        init: function (noLoad) {
            $("select").each(function (_i, _o) {
                var dataarea = $(_o).attr(CONSTANT.AREA.DATA_AREA);
                var datavalue = $(_o).attr(CONSTANT.AREA.DATA_VALUE);
                var datarefer = $(_o).attr(CONSTANT.AREA.DATA_REFER);
                if (!dataarea) return;
                switch (dataarea) {
                    case "1":
                        //加载数据
                        ToolApi.area.load("0", _o);
                        $(_o).bind("change", function () {
                            var code = $("#" + this.id + " option:selected").attr(CONSTANT.AREA.DATA_VALUE);
                            var refer = ToolApi.area.getRefer($(this)); //获取引用的元素
                            //加载引用的数据
                            ToolApi.area.load(code, refer);
                        });
                        break;
                    case "2":

                        $(_o).bind("click", function () {
                            var thLength = $("select[name=" + this.name + "] option").length;
                            var referValue = $("#" + $(this).attr(CONSTANT.AREA.DATA_REFER)).val();
                            if ((referValue == null || referValue == "") && thLength < 2) {
                                var prompt = $("#" + $(this).attr(CONSTANT.AREA.DATA_REFER)).attr("placeholder");
                                modals.info(prompt == null ? "请先选择区域省份" : prompt);
                            }
                        });

                        $(_o).bind("change", function () {
                            var code = $("#" + this.id + " option:selected").attr(CONSTANT.AREA.DATA_VALUE);
                            var refer = ToolApi.area.getRefer($(this)); //获取引用的元素
                            ToolApi.area.load(code, refer);

                        });
                        break;
                    case "3":
                        $(_o).bind("click", function () {
                            var thLength = $("select[name=" + this.name + "] option").length;
                            var referValue = $("#" + $(this).attr(CONSTANT.AREA.DATA_REFER)).val();
                            if ((referValue == null || referValue == "") && thLength < 2) {
                                var prompt = $("#" + datarefer).attr("placeholder");
                                //modals.info(prompt ==null?"请先选择"+$("#" + datarefer).attr("id"):prompt);
                                modals.info(prompt == null ? "请先选择区域城市" : prompt);
                            }
                        });
                        break;
                    default:
                        console.log("非法值");
                        break;
                }

            });

        },
        load: function (parentid, element, callback) {
            if (!parentid) parentid = '0';
            var params = parentid.length === 32 || parentid === '0' ? ("?parentid=" + parentid) : ("?code=" + parentid)
            var url = basePath + "/area/select" + params;
//				if($(element).find("option").length > 0){
//					return;
//				}
            ToolApi.ajax.ajaxGet(url, null, function (result) {
                var _html = "<option value=\"\">请选择..</option>";
                var isSelected = false;
                $(result).each(function (_i, _o) {
                    if (_o.code == $(element).attr(CONSTANT.AREA.DATA_VALUE)) {
                        _html += "<option value=\"" + _o.code + "\" datavalue=\"" + _o.id + "\" selected >" + _o.name + "</option>";
                        isSelected = true;
                    } else {
                        _html += "<option value=\"" + _o.code + "\" datavalue=\"" + _o.id + "\" >" + _o.name + "</option>";
                    }
                });
                $(element).empty();
                $(element).append(_html);

                //如果已经赋值，就触发事件，加载下级
                if (isSelected) {
                    $(element).trigger("change");
                }
                if ($.isFunction(callback)) {
                    callback();
                }
            });

        },
        loadbyvalue: function (parentid, element, selectvalue, childelement, childvalue, callback) {
            var params = "";
            if (parentid == 'undefined' || parentid.length == 32 || parentid == '0') {
                params = "?parentid=" + parentid;
            } else {
                params = "?code=" + parentid;
            }
            var url = basePath + "/area/select" + params;
            ToolApi.ajax.ajaxGet(url, null, function (result) {
                var elementparentid;
                var _html = "<option value=\"\">请选择..</option>";
                var isSelected = false;
                $(result).each(function (_i, _o) {
                    if (_o.code == selectvalue) {
                        _html += "<option value=\"" + _o.code + "\" datavalue=\"" + _o.id + "\" selected >" + _o.name + "</option>";
                        elementparentid = _o.id;
                        isSelected = true;
                    } else {
                        _html += "<option value=\"" + _o.code + "\" datavalue=\"" + _o.id + "\" >" + _o.name + "</option>";
                    }
                });
                $(element).empty();
                $(element).append(_html);
                if (childelement != null) {
                    ToolApi.area.loadbyvalue(elementparentid, childelement, childvalue, null, null, callback);
                } else if ($.isFunction(callback)) {
                    callback();
                }
            });
        },
        loadbyvalue2: function (parentid, element, selectvalue, childelement, childvalue, callback) {
            var params = "";
            if (parentid.length == 32 || parentid == '0') {
                params = "?parentid=" + parentid;
            } else {
                params = "?code=" + parentid;
            }
            var url = basePath + "/area/select" + params;
            ToolApi.ajax.ajaxGet(url, null, function (result) {
                var elementparentid;
                var _html = "<option value=\"\">请选择..</option>";
                var isSelected = false;
                $(result).each(function (_i, _o) {
                    if (_o.code == selectvalue) {
                        _html += "<option value=\"" + _o.code + "\" datavalue=\"" + _o.id + "\" selected >" + _o.name + "</option>";
                        elementparentid = _o.id;
                        isSelected = true;
                    } else {
                        _html += "<option value=\"" + _o.code + "\" datavalue=\"" + _o.id + "\" >" + _o.name + "</option>";
                    }
                });
                if (element) {
                    $(element).empty();
                    $(element).append(_html);
                }
                if (childelement) {
                    $(childelement).empty();
                    $(childelement).append(_html);
                }
            });
        },
        loadbyauth: function (cityEle, cityValue, countyEle, countyValue) {
            if (!cityEle && !countyEle) return false;
            cityValue = cityValue
                ? cityValue : $(cityEle).attr(CONSTANT.AREA.DATA_VALUE);
            countyValue = countyValue
                ? countyValue : $(countyEle).attr(CONSTANT.AREA.DATA_VALUE);
            var url = basePath + "/area/selectAuth";
            ToolApi.ajax.ajaxGet(url, {cityCode: cityValue, countyCode: countyValue}, function (result) {
                fillCity(result.citys, cityEle);
                fillCounty(result.countys, countyEle);
                return false;
            });

            function fillCity(citys, cityEle) {
                if (!cityEle) return false;
                var _html = "<option value=\"\">请选择..</option>";
                if (citys && citys.length > 0) {
                    $(citys).each(function (_i, _o) {
                        _html += "<option value=\"" + _o.code + "\" datavalue=\"" + _o.id
                            + "\" " + (_o.selected ? "selected" : "") + " >" + _o.name + "</option>";
                    });
                }
                $(cityEle).empty();
                $(cityEle).each(function () {
                    $(this).append(_html);
                });
                if (citys.length == 1) {
                    $(cityEle).attr("disabled", true);
                }
                return false;
            };

            function fillCounty(countys, countyEle) {
                if (!countyEle) return false;
                var _html = "<option value=\"\">请选择..</option>";
                if (countys && countys.length > 0) {
                    $(countys).each(function (_i, _o) {
                        _html += "<option value=\"" + _o.code + "\" datavalue=\"" + _o.id
                            + "\" " + (_o.selected ? "selected" : "") + " >" + _o.name
                    });
                }
                $(countyEle).empty();
                $(countyEle).each(function () {
                    $(this).append(_html);
                });
                if (countys.length == 1) {
                    $(countyEle).attr("disabled", true);
                }
                return false;
            };
        },
        getRefer: function (refer) {
            //console.log("id="+$(refer).attr("id"));
//				var tag = document.getElementsByTagName("select");
//				for(var i= 0 ; i<tag.length ; i ++ ){
//						var _obj = tag[i];
//						if($(_obj).attr(CONSTANT.AREA.DATA_REFER) == $(refer).attr("id")){
//							return _obj;
//						}
//
//				}
            return $('select[' + CONSTANT.AREA.DATA_REFER + '="' + $(refer).attr("id") + '"]');
        }
    },
    Map: function (_json) {
        this.elements = _json && _json.elements ? _json.elements : [];
        this.size = function () {
            return this.elements.length;
        },

            this.isEmpty = function () {
                return (this.elements.length < 1);
            },

            this.clear = function () {
                this.elements = [];
            },

            this.put = function (_key, _value) {
                this.elements.push({
                    key: _key,
                    value: _value
                });
            },

            this.remove = function (_key) {
                try {
                    for (var i = 0; i < this.elements.length; i++) {
                        if (this.elements[i].key === _key) {
                            this.elements.splice(i, 1);
                            return true;
                        }
                    }
                } catch (e) {
                }
                return false;
            },

            this.get = function (_key) {
                try {
                    for (var i = 0; i < this.elements.length; i++) {
                        if (this.elements[i].key === _key) {
                            return this.elements[i].value;
                        }
                    }
                } catch (e) {
                    return null;
                }
            },

            this.element = function (_index) {
                if (_index < 0 || _index >= this.elements.length) {
                    return null;
                }
                return this.elements[_index];
            },

            this.containsKey = function (_key) {
                try {
                    for (var i = 0; i < this.elements.length; i++) {
                        if (this.elements[i].key === _key) {
                            return true;
                        }
                    }
                } catch (e) {
                }
                return false;
            },

            this.containsValue = function (_value) {
                try {
                    for (var i = 0; i < this.elements.length; i++) {
                        if (this.elements[i].value === _value) {
                            return true;
                        }
                    }
                } catch (e) {
                }
                return false;
            },

            this.values = function () {
                var arr = [];
                for (var i = 0; i < this.elements.length; i++) {
                    arr.push(this.elements[i].value);
                }
                return arr;
            },

            this.keys = function () {
                var arr = [];
                for (var i = 0; i < this.elements.length; i++) {
                    arr.push(this.elements[i].key);
                }
                return arr;
            }
    },
    date: {
        format: function (ns) {
            if (!ns) return "";
            var date = new Date(ns);
            return date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate() + "&nbsp;" + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
        },
        formatYYYYMMDD: function (ns) {
            if (!ns) return "";
            var date = new Date(ns);
            return date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate();
        },
        formatToDay: function (ns) {
            if (!ns) return "";
            var date = new Date(ns);
            return date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate();
        },
        getThisYear: function () {
            return new Date().getFullYear();
        },
        getThisMonth: function () {
            return new Date().getMonth() + 1;
        },
        getThisDate: function () {
            return new Date().getDate();
        },
        getThisYYYMMDD: function () {
            var date = new Date();
            return (date.getFullYear() + '年' + (date.getMonth() + 1) + '月' + date.getDate());
        }
    },
    string: {
        isEmpty: function (str) {
            if (str == "" || str == null || str == "null" || typeof (str) == "undefined") {
                return true;
            }
            return false;

        },
        isNotEmpty: function (str) {
            return tool.string.isEmpty(str) == true ? false : true;
        }
    },
    browser: {
        IsPC: function () {
            var userAgentInfo = navigator.userAgent;
            var Agents = ["Android", "iPhone",
                "SymbianOS", "Windows Phone",
                "iPad", "iPod"];
            var flag = true;
            for (var v = 0; v < Agents.length; v++) {
                if (userAgentInfo.indexOf(Agents[v]) > 0) {
                    flag = false;
                    break;
                }
            }
            return flag;
        },
        IsPhone: function () {
            return tool.browser.IsPC() == true ? false : true;
        },
        IsWeChar: function () {
            var ua = navigator.userAgent.toLowerCase();
            if (ua.match(/MicroMessenger/i) == "micromessenger") {
                return true;
            } else {
                return false;
            }
        }
    },
    sms: {
        send: function (btn, mobile, count) {
            var InterValBtn = btn;
            if (mobile == "") return;
            if (!count) window.curCount = CONSTANT.SMS.COUNT;
            //设置button效果，开始计时
            $(InterValBtn).attr("disabled", "true");
            $(InterValBtn).text(window.curCount + "秒后重新获取");
            window.InterValObj = window.setInterval(function () {
                ToolApi.sms.setRemainTime(InterValBtn);
            }, 1000); //启动计时器，1秒执行一次
            var params = {"mobile": mobile};
            ToolApi.ajax.ajaxPost(basePath + "/enterprise/base/sendSms/" + mobile, JSON.stringify(params), function (result) {
                if (result || result == "true") {
                    modals.info("手机验证码发送成功，请留意手机短信!")
                } else {
                    modals.info("手机验证码发送失败，请重新获取！")
                }
            })

        },
        setRemainTime: function (InterValBtn) {
            if (window.curCount == 0) {
                window.clearInterval(window.InterValObj);//停止计时器
                $(InterValBtn).removeAttr("disabled");//启用按钮
                $(InterValBtn).text("重新获取验证码");
            } else {
                window.curCount--;
                $(InterValBtn).text(curCount + "秒后重新获取");
            }
        }

    },
    dataTable: {
        /**
         *param为json对象
         *一、param对象说明
         *1.1、url:查询地址                      【必设属性】
         *1.2、tableid:表格id                  【必设属性】
         *1.3、columns:列组(与thoad中的列对应)【必设属性】
         *1.4、type:查询方式(POST/GET)          【必设属性】
         *1.5、formid：查询表单formid
         *1.6、config:DataTable配置，#参考：http://www.cnblogs.com/liang-ling/p/5853156.html
         *二、方法
         *2.1、draw()： 查询
         *三、其他说明
         *3.1：默认返回dataTable对象
         */
        init: function (param) {
            var $wrapper = $('#mainDiv');
            var $table = $("#" + param.tableid);
            var $param = param;
            var defaultConfig = CONSTANT.DATA_TABLES.DEFAULT_OPTION;
            if (defaultConfig.fnCreatedRow) defaultConfig.fnCreatedRow = null;
            if (defaultConfig.createdRow) defaultConfig.createdRow = null;
            if (defaultConfig.fnRowCallback) defaultConfig.fnRowCallback = null;
            if (defaultConfig.drawCallback) defaultConfig.drawCallback = null;
            if (defaultConfig.fnInitComplete) defaultConfig.fnInitComplete = null;
            if (defaultConfig.initComplete) defaultConfig.initComplete = null;
            if (defaultConfig.drawCallback) defaultConfig.drawCallback = null;
            if (defaultConfig.fnDrawCallback) defaultConfig.fnDrawCallback = null;

            //特殊排序
            defaultConfig.bSort = $param.bSort ? $param.bSort : false;
            defaultConfig.bSortClasses = $param.bSortClasses ? $param.bSortClasses : false;
            defaultConfig.aaSorting = $param.aaSorting ? $param.aaSorting : null;
            defaultConfig.aoColumnDefs = $param.aoColumnDefs ? $param.aoColumnDefs : null;

            for (var c in $param.config) {
                defaultConfig[c] = $param.config[c];
            }

            var dataTable = $table.dataTable($.extend(true, {}, defaultConfig, {
                columns: $param.columns,
                ajax: function (data, callback, settings) {
                    $wrapper.spinModal();
                    if ($param.paging) {
                        var pageParams = $param.paging(data, $param.formid, $param);
                    } else {
                        var pageParams = ToolApi.dataTable.paging(data, $param.formid);
                    }
                    for (var c in $param.extra) {
                        pageParams[c] = $param.extra[c];
                    }
                    //如果是POST需要使用JSON.stringify
                    if ($param.type != "GET") pageParams = JSON.stringify(pageParams);
                    $.ajax({
                        type: $param.type,
                        url: $param.url,
                        cache: false,
                        data: pageParams,
                        dataType: 'json',
                        contentType: 'application/json',
                        success: function (result) {
                            console.log("result", result)
                            if (result.code && result.code != "0") {
                                $wrapper.spinModal(false);
                                modals.info("查询失败," + result.message);
                                return;
                            }

                            var returnData = {};
                            returnData.draw = data.draw;
                            var maxPage;
                            if (result.pageInfo) {
                                returnData.recordsTotal = result.pageInfo.total ? result.pageInfo.total : 0;
                                returnData.recordsFiltered = result.pageInfo.total ? result.pageInfo.total : 0;
                                returnData.data = result.pageInfo.list ? result.pageInfo.list : [];
                                maxPage = result.pageInfo.pages ? result.pageInfo.pages : 0;
                            } else {
                                returnData.recordsTotal = result.total ? result.total : 0;
                                returnData.recordsFiltered = result.total ? result.total : 0;
                                returnData.data = result.list ? result.list : 0;
                                maxPage = result.pages ? result.pages : 0;
                            }
                            callback(returnData);
                            $wrapper.spinModal(false);

                            //设置input框当前页码
                            //$(".searchPageNum").val(result.pageNum);

                            //分页确认事件
                            $(".searchPageButton").on('click', function () {
                                var pageNum = $(this).parent().find(".searchPageNum").val();
                                var re = /^[0-9]+$/;
                                if (re.test(pageNum) && parseInt(pageNum) > 0 && parseInt(pageNum) <= parseInt(maxPage)) {
                                    $('#' + settings.sTableId).dataTable().fnPageChange(parseInt(pageNum) - 1);
                                }
                            });

                        },
                        error: function (XMLHttpRequest, textStatus, errorThrown) {
                            $wrapper.spinModal(false);
                            modals.info("查询失败");
                        }
                    });
                }

            })).api();

            if (defaultConfig.fnCreatedRow) defaultConfig.fnCreatedRow = null;
            if (defaultConfig.createdRow) defaultConfig.createdRow = null;
            if (defaultConfig.fnRowCallback) defaultConfig.fnRowCallback = null;
            if (defaultConfig.drawCallback) defaultConfig.drawCallback = null;
            if (defaultConfig.fnInitComplete) defaultConfig.fnInitComplete = null;
            if (defaultConfig.initComplete) defaultConfig.initComplete = null;
            if (defaultConfig.drawCallback) defaultConfig.drawCallback = null;
            if (defaultConfig.fnDrawCallback) defaultConfig.fnDrawCallback = null;
            return dataTable;

        }, //分页对象
        paging: function (data, dataTableSearchForm) {
            var param = {};
            //组装排序参数
            if (data.order && data.order.length && data.order[0]) {
                param.orderBy = data.columns[data.order[0].column].data + " " + data.order[0].dir; //排序参数
            }
            //组装查询参数
            if (dataTableSearchForm) {
                //把form里面的数据序列化成数组
                var formData = $("#" + dataTableSearchForm).serializeArray();
                formData.forEach(function (e) {
                    param[e.name] = e.value;
                });
            }
            //组装分页参数
            param.startIndex = data.start;
            param.pageSize = data.length;
            param.pageNum = (data.start / data.length) + 1;//当前页码
            param.draw = data.draw;

            //输出参数，方便调试
            return param;
        }
    },
    webUploader: function (options) {
        var _setting = {
            // 自动上传。
            auto: true,
            swf: basePath + '/static/js/webupload/Uploader.swf',
            // 文件接收服务端。
            server: basePath + "/resource/rest/upload",
            // 内部根据当前运行是创建
            pick: '.filePicker',
            fileNumLimit: 8, //限制上传个数
            fileSingleSizeLimit: 10240000 //限制单个上传文件的大小
        };

        jQuery.extend(_setting, options);
        var _uploader = WebUploader.create(_setting);
        //当文件被加入队列之前触发,用于校验
        _uploader.on('beforeFileQueued', function (file) {
            modals.showLoading();
            if (_setting.beforeFileQueued) {
                _setting.beforeFileQueued(file);
            }
        });

        // 文件上传过程中创建进度条实时显示。file文件对象 percentage传输进度 Nuber类型
        _uploader.on('uploadProgress', function (file, percentage) {
            if (_setting.uploadProgress) {
                _setting.uploadProgress(file, percentage);
            }
        });

        //发送前触发，主要用来询问是否要添加附带参数，大文件在开起分片上传的前提下此事件可能会触发多次。
        _uploader.on('uploadBeforeSend', function (block, data) {
            if (_setting.uploadBeforeSend) {
                _setting.uploadBeforeSend(block, data);
            }
        });

        // 文件上传成功，给item添加成功class, 用样式标记上传成功。
        _uploader.on('uploadSuccess', function (file, response) {//response:服务端返回的数据
            if (_setting.uploadSuccess) {
                _setting.uploadSuccess(file, response);
            }
            modals.hideLoading();
        });

        // 当validate不通过时，会以派送错误事件的形式通知调用者
        _uploader.on('error', function (type) {
            modals.hideLoading();
            if (_setting.error) {
                _setting.error(type);
            } else {
                if (type == "Q_EXCEED_NUM_LIMIT") {
                    //多文件上传的时候限制数量
                    modals.error("超出文件上传数限制");
                } else if (type == "F_EXCEED_SIZE") {
                    modals.error("此处文件大小不能大于10M");
                } else if (type == "Q_TYPE_DENIED") {
                    modals.error("文件类型必须是" + _setting.accept.extensions);
                } else {
                    modals.error("验证不通过");
                }
            }
        });

        // 文件上传失败，现实上传出错。
        _uploader.on('uploadError', function (file) {
            modals.hideLoading();
            if (_setting.uploadError) {
                _setting.uploadError(file);
            } else {
                modals.error("上传失败");
            }
        });

        // 完成上传完后
        _uploader.on('uploadComplete', function (file) {
            if (_setting.uploadComplete) {
                _setting.uploadComplete(file);
            }
        });
    },
    crypto: {
        //加密
        Encrypt: function (word, _key) {
            var key = CryptoJS.enc.Utf8.parse(_key);
            var srcs = CryptoJS.enc.Utf8.parse(word);
            var encrypted = CryptoJS.AES.encrypt(srcs, key, {mode: CryptoJS.mode.ECB, padding: CryptoJS.pad.Pkcs7});
            return encrypted.toString();
        }
    },
    import: {
        js: function (path) {
            var head = document.getElementsByTagName('head')[0];
            var script = document.createElement('script');
            script.type = 'text/javascript';
            script.src = basePath + path;
            if (typeof (callback) == 'function') {
                script.onload = script.onreadystatechange = function () {
                    if (!this.readyState || this.readyState === "loaded" || this.readyState === "complete") {
                        callback();
                        script.onload = script.onreadystatechange = null;
                    }
                };
            }
            head.appendChild(script);
        }
    }

}

//递归删除空属性防止把null变成空值
function deleteEmptyProp(obj) {
    for (var a in obj) {
        if (typeof (obj[a]) === "object" && obj[a] !== null) {
            deleteEmptyProp(obj[a]);
        } else {
            if (!obj[a]) {
                delete obj[a];
            }
        }
    }
    return obj;
}
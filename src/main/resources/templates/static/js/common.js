$(function () {
    $.ajax({
        url: "/login/getUser",
        async: true,
        success: function (data) {
            $("#tokenName").text(data.name);
            $("#side_ul").children('li').each(function () {
                if (data.flag + "" != $(this).attr("role")) {
                    $(this).remove();
                }
            })
            // if(data.flag!=role){
            //     $("sidebar").remove()
            // }
        }
    })
});
$(function () {
    $.ajax({
        url: "/login/getToken",
        async: true,
        success: function (data) {
            $("#tokenName").text(data);
        }
    })
});
$(function () {
    let ctx = {
        ajaxUrl: "profile/meals/",
        filterUrl: null,
        datatableApi: $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime"
                },
                {
                    "data": "description",
                    "orderable": false
                },
                {
                    "data": "calories"
                },
                {
                    "defaultContent": "Edit",
                    "orderable": false
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false
                }
            ],
            "order": [
                [
                    0,
                    "asc"
                ]
            ]
        })
    };

    makeEditable(ctx);

    $(".filter").click(function (e) {
        e.preventDefault();
        ctx.filterUrl = ctx.ajaxUrl + 'filter?' + $(this).closest('form').serialize();

        updateTable();
    });

    $(".reset").click(function (e) {
        e.preventDefault();
        ctx.filterUrl = null;

        $(this).closest('form').find("input[type=date], input[type=time]").val("");
        updateTable();
    });
});
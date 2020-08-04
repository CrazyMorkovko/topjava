$(function () {
    let ctx = {
        ajaxUrl: "admin/users/",
        datatableApi: $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "name"
                },
                {
                    "data": "email"
                },
                {
                    "data": "roles"
                },
                {
                    "data": "enabled"
                },
                {
                    "data": "registered"
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

    $('.user-checkbox').change(function () {
        $.ajax({
            type: 'POST',
            url: ctx.ajaxUrl + $(this).closest('tr').attr('id'),
            data: {
                enabled: $(this).prop('checked')
            }
        }).done(function () {
            updateTable();
            successNotification('Saved');
        });
    })
});
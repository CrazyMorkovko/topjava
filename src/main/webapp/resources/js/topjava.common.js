let context, form, failedNote;

function makeEditable(ctx) {
    context = ctx;
    form = $('#detailsForm');

    $('.delete').click(function (e) {
        e.preventDefault();

        if (confirm('Are you sure?')) {
            deleteRow($(this).closest('tr').attr('id'));
        }
    });

    $(document).ajaxError(function (event, jqXHR, options, jsExc) {
        failNotification(jqXHR);
    });

    $.ajaxSetup({cache: false});
}

function add() {
    form.find(':input').val('');
    $('#editRow').modal();
}

function deleteRow(id) {
    $.ajax({
        url: context.ajaxUrl + id,
        type: 'DELETE'
    }).done(function () {
        updateTable();
        successNotification('Deleted');
    });
}

function updateTable() {
    $.get(context.filterUrl ? context.filterUrl : context.ajaxUrl, function (data) {
        context.datatableApi.clear().rows.add(data).draw();
    });
}

function save() {
    $.ajax({
        type: 'POST',
        url: context.ajaxUrl,
        data: form.serialize()
    }).done(function () {
        $('#editRow').modal('hide');
        updateTable();
        successNotification('Saved');
    });
}

function closeNotification() {
    if (failedNote) {
        failedNote.close();
        failedNote = undefined;
    }
}

function successNotification(text) {
    closeNotification();
    new Noty({
        text: "<span class='fa fa-lg fa-check'></span> &nbsp;" + text,
        type: 'success',
        layout: 'bottomRight',
        timeout: 1000
    }).show();
}

function failNotification(jqXHR) {
    closeNotification();
    failedNote = new Noty({
        text: "<span class='fa fa-lg fa-exclamation-circle'></span> &nbsp;Error status: " + jqXHR.status,
        type: 'error',
        layout: 'bottomRight'
    }).show();
}
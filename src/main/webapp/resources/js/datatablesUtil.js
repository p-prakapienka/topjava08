var filtered = false;

function makeEditable() {
    $('.delete').click(function () {
        deleteRow($(this).parent().parent().attr("id"));
    });

    $('#detailsForm').submit(function () {
        save();
        return false;
    });

    $(document).ajaxError(function (event, jqXHR, options, jsExc) {
        failNoty(event, jqXHR, options, jsExc);
    });
}

function add() {
    $('#id').val(null);
    $('#editRow').modal();
}

function filter() {
    filtered = true;
    $.ajax({
        url: ajaxUrl + 'filter',
        type: 'GET',
        data: filterForm.serialize(),
        success: function (data) {
            fillTable(data);
        }
    });
}

function deleteRow(id) {
    $.ajax({
        url: ajaxUrl + id,
        type: 'DELETE',
        success: function () {
            updateTable();
            successNoty('Deleted');
        }
    });
}

function updateTable() {
    if (filtered) {
        filter();
        return;
    }
    $.get(ajaxUrl, function (data) {
        fillTable(data);
    });
}

function fillTable(data) {
    datatableApi.fnClearTable();
    $.each(data, function (key, item) {
        datatableApi.fnAddData(item);
    });
    datatableApi.fnDraw();
}

function save() {
    var form = $('#detailsForm');
    $.ajax({
        type: "POST",
        url: ajaxUrl,
        data: form.serialize(),
        success: function () {
            $('#editRow').modal('hide');
            updateTable();
            successNoty('Saved');
        }
    });
}

var failedNote;

function closeNoty() {
    if (failedNote) {
        failedNote.close();
        failedNote = undefined;
    }
}

function successNoty(text) {
    closeNoty();
    noty({
        text: text,
        type: 'success',
        layout: 'bottomRight',
        timeout: true
    });
}

function failNoty(event, jqXHR, options, jsExc) {
    closeNoty();
    failedNote = noty({
        text: 'Failed: ' + jqXHR.statusText + "<br>",
        type: 'error',
        layout: 'bottomRight'
    });
}

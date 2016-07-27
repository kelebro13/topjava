function makeEditable() {

    $('.checked').on("click", function () {
        var userId = $(this).parents('tr').first().attr("id");
        var userEnabled = $(this).is(":checked");

        var data = {
            id: userId,
            check: userEnabled
        };

        $.ajax({
            type: 'POST',
            url: ajaxUrl + 'active',
            data: data,
            success: function () {
                successNoty('Change Active');
            }

        });
    });

    $('#filter').click(function () {
        debugger;
        fiterDate = $('#filterForm').serialize();
        updateTable();
        successNoty('Filter');
    });
    
    $('#add').click(function () {
        $('#id').val(0);
        $('#editRow').modal();
    });

    $('.delete').click(function () {
        deleteRow($(this).parents('tr').first().attr("id"));
    });

    $('#detailsForm').submit(function () {
        save();
        return false;
    });

    $(document).ajaxError(function (event, jqXHR, options, jsExc) {
        failNoty(event, jqXHR, options, jsExc);
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

var fiterDate;

function updateTable() {
    $.ajax({
        type: 'GET',
        url: ajaxUrl,
        data: fiterDate,
        success: function (data) {
            datatableApi.clear();
            $.each(data, function (key, item) {
                datatableApi.row.add(item);
            });
            datatableApi.draw();
        }
    })
}

function save() {
    var form = $('#detailsForm');
    debugger;
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

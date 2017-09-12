function makeEditable() {
    $(".delete").click(function () {
        deleteRow($(this).closest('tr').attr("id"));
    });

    $("#detailsForm").submit(function () {
        save();
        return false;
    });

    if(isUserMode()) {
        $(".checkActivity").change(function() {
            $.ajax({
                type: "POST",
                url: ajaxUrl+"activity",
                data: {id: $(this).closest('tr').attr("id"), active: this.checked},
                dataType: 'json',
                success: function () {
                    updateTable();
                    successNoty("Saved");
                }
            });
        });
    }

    $(document).ajaxError(function (event, jqXHR, options, jsExc) {
        failNoty(event, jqXHR, options, jsExc);
    });

    // solve problem with cache in IE: https://stackoverflow.com/a/4303862/548473
    $.ajaxSetup({cache: false});
}

function filter() {
    $.get(ajaxUrl + "/filter", {
        startDate: $("#startDate").val(), endDate: $("#endDate").val(),
        startTime: $("#startTime").val(), endTime: $("#endTime").val()
        }, function (data) {
            datatableApi.clear().rows.add(data).draw();
    });
}

function reset() {
    document.getElementById("startDate").value  = "";
    document.getElementById("endDate").value  = "";
    document.getElementById("endTime").value  = "";
    document.getElementById("startTime").value  = "";
}


function add() {
    $("#detailsForm").find(":input").val("");
    $("#editRow").modal();
}

function deleteRow(id) {
    $.ajax({
        url: ajaxUrl + id,
        type: "DELETE",
        success: function () {
            updateTable();
            successNoty("Deleted");
        }
    });
}

function updateTable() {
    if( isMealMode() ) {
        filter();
    } else {
        $.get(ajaxUrl, function (data) {
            datatableApi.clear().rows.add(data).draw();
        });
    }
}

function save() {
    var form = $("#detailsForm");
    $.ajax({
        type: "POST",
        url: ajaxUrl,
        data: form.serialize(),
        success: function () {
            $("#editRow").modal("hide");
            updateTable();
            successNoty("Saved");
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
    new Noty({
        text: "<span class='glyphicon glyphicon-ok'></span> &nbsp;" + text,
        type: 'success',
        layout: "bottomRight",
        timeout: 1000
    }).show();
}

function failNoty(event, jqXHR, options, jsExc) {
    closeNoty();
    failedNote = new Noty({
        text: "<span class='glyphicon glyphicon-exclamation-sign'></span> &nbsp;Error status: " + jqXHR.status,
        type: "error",
        layout: "bottomRight"
    }).show();
}

function isMealMode() {
    return $('#startDate').length;
}

function isUserMode() {
    return !isMealMode();
}
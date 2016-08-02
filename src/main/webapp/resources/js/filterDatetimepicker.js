jQuery(document).ready(function() {

    $.datetimepicker.setLocale('ru');

    jQuery('#startDate').datetimepicker({
        timepicker:false,
        format:'Y-m-d'
    });

    jQuery('#endDate').datetimepicker({
        timepicker:false,
        format:'Y-m-d'
    });

    jQuery('#startTime').datetimepicker({
        datepicker:false,
        format:'H:i',
    });

    jQuery('#endTime').datetimepicker({
        datepicker:false,
        format:'H:i',
    });

    jQuery('#dateTime').datetimepicker({
        format: 'Y-m-d H:i',
    });


});

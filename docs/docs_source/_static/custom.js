$(document).ready(function() {
    /* Add 'hover' links to glossary definitions */
    $("dl.glossary dt").append(
        $("<a class='def-link'><i class='fa fa-link'></i></a>")
    );

    $("a.def-link").attr('href', function() {
        return '#' + $(this).parent().attr('id');
    });

    /* Highlight deprecated items */
    $("div.deprecated p").addClass(
        'label label-default'
    );

    /* Highlight added items */
    $("div.versionadded p").addClass(
        'label label-success'
    );

    /* Highlight added items */
    $("div.versionchanged p").addClass(
        'label label-primary'
    );

});

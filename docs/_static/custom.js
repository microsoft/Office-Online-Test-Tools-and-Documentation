$(document).ready(function() {
    /* Add 'hover' links to glossary definitions */
    $("dl.glossary dt").append(
        $("<a class='def-link'><i class='fa fa-link'></i></a>")
    );

    $("a.def-link").attr('href', function() {
        return '#' + $(this).parent().attr('id');
    });

    var external_links = $("div.wy-menu-vertical * a.external");
    external_links.attr('target', 'external');
    external_links.append(
        $(" <i class='fa fa-external-link' style='padding-left: 5px;'></i>")
    );

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

    var issues_div = $("#gh_known_issues");
    if(issues_div) {
        $.getJSON(
            "https://api.github.com/repos/Microsoft/Office-Online-Test-Tools-and-Documentation/issues?state=open&labels=known%20issue",
            function(data) {
                var converter = new showdown.Converter();
                var issues = [];
                $.each(data, function(key, issue) {
                    var issue_html = "<div class='issue' id='issue" + issue['number'] + "'>";
                    issue_html += "<div class='title'><a href='" + issue['html_url'] + "'>#" + issue['number'] + "</a>: ";
                    issue_html += issue['title'] + "</div>";
                    issue_html += "<div class='last_updated'>Last updated: <time class='timeago' datetime='"+ issue['updated_at'] + "'>" + issue['updated_at'] + "</time></div>";
                    issue_html += "<div class='details'>"+ converter.makeHtml(issue['body']) + "</div>";
                    issue_html += "</div>";
                    issue_html += "<hr />";
                    issues.push(issue_html);
                });
                issues.reverse();
                $(issues.join("")).appendTo(issues_div);
                $("time.timeago").timeago();
            }
        );
    }

    var validator_div = $("#validator_issues");
    if(validator_div) {
        $.getJSON(
            "https://api.github.com/repos/Microsoft/Office-Online-Test-Tools-and-Documentation/issues?state=open&labels=known%20issue,validator",
            function(data) {
                var converter = new showdown.Converter();
                var issues = [];
                var status = $('.admonition-status .admonition-title').first();
                if(data.length == 0) {
                    status.text('Current status: Operational');
                    $('.admonition-status *:not(:first-child)').remove();
                    status.parent().attr('style', "margin-bottom: 0 !important");
                } else {
                    status.text('Current status: Down');
                    $('.admonition-status').first().addClass('error');
                }

                $.each(data, function(key, issue) {
                    var issue_html = "<div class='issue' id='issue" + issue['number'] + "'>";
                    issue_html += "<div class='title'><a href='" + issue['html_url'] + "'>#" + issue['number'] + "</a>: ";
                    issue_html += issue['title'] + "</div>";
                    issue_html += "<div class='last_updated'>Last updated: <time class='timeago' datetime='"+ issue['updated_at'] + "'>" + issue['updated_at'] + "</time></div>";
                    issue_html += "<div class='details'>"+ converter.makeHtml(issue['body']) + "</div>";
                    issue_html += "</div>";

                    if(key === 0){}
                    else {
                        issue_html += "<hr />";
                    }
                    issues.push(issue_html);
                });
                issues.reverse();
                $(issues.join("")).appendTo(validator_div);
                $("time.timeago").timeago();
            }
        );
    }
});

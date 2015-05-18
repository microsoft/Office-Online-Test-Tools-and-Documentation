# coding=utf-8

from docutils import nodes
from docutils.parsers.rst import Directive

__author__ = 'Tyler Butler <tylerbu@microsoft.com>'


class IssueDirective(Directive):
    required_arguments = 1
    optional_arguments = 0
    final_argument_whitespace = False
    has_content = False

    def run(self):
        issue_num = self.arguments[0]
        app = self.state.document.settings.env.app
        conf = app.config.extlinks

        url, prefix = conf['issue']
        url = url % issue_num
        prefix += issue_num

        entry = nodes.inline()
        entry += nodes.reference(prefix, prefix, refuri=url, internal=False)
        return [entry]


def setup(app):
    app.add_directive(
        'issue', IssueDirective
    )

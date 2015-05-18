# coding=utf-8

from sphinxcontrib.domaintools import custom_domain

__author__ = 'Tyler Butler <tylerbu@microsoft.com>'


def setup(app):
    app.add_domain(
        custom_domain(
            'WOPIDomain',
            name='wopi',
            label="WOPI Domain",
            elements=dict(
                action=dict(
                    objname="WOPI Action",
                ),
                header=dict(
                    objname="WOPI HTTP Header",
                ),
                req=dict(
                    objname="WOPI HTTP Header",
                ),
            )
        )
    )

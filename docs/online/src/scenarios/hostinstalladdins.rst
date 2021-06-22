
..  _HostInstallAddIns:

Enable Office Web Add-Ins provided by Host.PostMessage
======================================================

..  default-domain:: js


You can enable |wac| applications to show a list of Office Web add-ins at |wac| application Ribbon UI. This way, 
when a document is opened or created from your host, a list of Office Web add-ins provided by host will be available 
to be used with the document.

Currently, only add-ins that utilize app commands are supported.

In order to enable this feature, WOPI host developers need to:

1. register with |wac| server for this feature by contacting the team of |wac| server.
2. provide the add-ins as a Form field value as part of the POST request sent to |wac| server. 

Add-In List
-----------

The list of add-ins provided by Host page needs to be sent as a Form field value as part of the POST request body sent
to |wac| server. The data format is the JSON-formatted object of the form. The Form data field name of data is host_install_addins.

See FAQ section for the example data payload and form data filed name.


The following example shows a list of add-ins.

..  code-block:: JSON

    {
        {"addinId": "WA123456781", "type": "TaskPaneApp"},
        {"addinId": "WA123456782", "type": "TaskPaneApp"},
        {"addinId": "WA123456783", "type": "TaskPaneApp"},
        {"addinId": "WA123456784", "type": "TaskPaneApp"}
    }

Also, see FAQ section for the example data payload and Form field name.

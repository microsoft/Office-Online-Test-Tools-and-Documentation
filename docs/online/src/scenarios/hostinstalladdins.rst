
..  _HostInstallAddIns:

Enable Office Web Add-Ins provided by Host.PostMessage
===============================================================

..  default-domain:: js


You can enable |wac| applications to show a list of Office Web add-ins at |wac| application Ribbon UI. This way, 
when a document is opened or created from your host, a list of Office Web add-ins provided by host will be available 
to be used with the document.

Currently, we only support App Command Add-ins.

In order to enable this feature, WOPI host developers need to:
1. register with |wac| server for this feature.
2. the list of add-ins are provided via as Form field values as part of POST request sent
to |wac| server. 

Add-In List
--------------

The list of add-ins provided by Host page needs to be sent as POST Request body as a part of POST request sent
to |wac| server. The data format is the JSON-formatted object of the form. The Form field name of data is host_install_addins.

See FAQ section for the example data payload and Form field name.


The following example shows a list of add-ins.

..  code-block:: JSON

    { 
        { addinId: "WA123456781", type: "TaskPaneApp"}, 
        { addinId: "WA123456782", type: "TaskPaneApp"}, 
        { addinId: "WA123456783", type: "TaskPaneApp"}, 
        { addinId: "WA123456784", type: "TaskPaneApp"} 
    }

Also, see FAQ section for the example data payload and Form field name.


Add-In Prompt
--------------

There are two kinds of add-ins in term of the behavior: 
1. Add-ins are a part of WOPI host core service. When these add-ins are registered with |wac| server for the specified
WOPI host , users won't be prompted for any permission to run the add-ins, since these add-ins are the part of host core service. 
By using host, users have consented to use these add-ins.
2. Any add-ins that are not a part of WOPI host core service, and have not been registered for the WOPI host. For this kind
of add-ins, users will get a prompted for a permissions to allow add-ins to run.



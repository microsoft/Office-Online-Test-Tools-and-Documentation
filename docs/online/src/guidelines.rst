
..  _guidelines:

Guidelines & limitations
========================

..  spelling::

    IP
    
In order to integrate with the Microsoft-hosted Office Online cloud service, hosts should keep in mind the
following guidelines.


Unsupported scenarios
---------------------

IP ACLs
~~~~~~~

Office Online does not support IP-based ACLs. Office Online adds new servers and datacenters regularly and IP ACLs
will break often. Hosts should use :ref:`proof keys <Proof Keys>` if they wish to verify that requests are coming
from Office Online.


Authentication
~~~~~~~~~~~~~~

Office Online does not do any authentication, except as part of the :ref:`business user edit <Business editing>`
flow. Hosts are expected to handle authentication and authorization by providing WOPI
:term:`access tokens <access token>`. All user-related information is provided to Office Online by the host using
properties in :ref:`CheckFileInfo`.


..  _ui guidelines:

|stub-icon| UI Guidelines
-------------------------

..  include:: ../../_shared/stub.rst

..  |issue| issue:: 49

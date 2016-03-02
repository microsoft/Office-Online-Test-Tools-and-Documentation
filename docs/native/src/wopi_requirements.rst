
..  _requirements:

|stub-icon| WOPI implementation requirements for |Office iOS| integration
=========================================================================

This section documents specific requirements for your WOPI implementation for integration with |Office iOS| beyond
what is documented in general for WOPI.

* The **X-WOPI-ItemVersion** header must be included on :ref:`PutFile`, :ref:`Lock`, and :ref:`Unlock` responses
* For the :ref:`Bootstrap` operation, the :http:header:`Content-Type` response header must be set to
  ``application/json``

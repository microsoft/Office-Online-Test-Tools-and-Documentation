
.. meta::
    :robots: noindex

..  _Validator app:

Using the validator application to validate your WOPI implementation
====================================================================

Since WOPI is used in both the |wac| and |Office iOS| integrations, you can verify your WOPI implementation by
following the instructions at :ref:`validator`.

If you do not integrate with |wac|, you can still verify your WOPI implementation using the above instructions by
building a minimal :term:`host page` and setting the :term:`VALIDATOR_TEST_CATEGORY` to ``OfficeNativeClient``. This
will run only the tests that are necessary for |Office iOS| integration.

The validator application will not be able to verify the end user experience entirely, so manual validation must also
be done.

..  note::
    You will not be able to invoke any :ref:`WOPI Actions` successfully unless your WOPI domain has been added to the
    :ref:`allow list`.


Using the validator application to validate your WOPI implementation
====================================================================

Since WOPI is used in both the Office Online and Office for iOS integrations, you can verify your WOPI implementation by following
the instructions at :ref:`validator`

If you do not integrate with Office Online, you can still verify your WOPI implementation using the above instructions by building
a minimal :term:`host page` and setting the :term:`VALIDATOR_TEST_CATEGORY` to ``OfficeNativeClient``. This will run the
WopiValidator application in a mode that will run only the tests which are necessary for Office for iOS integration. 

The validator application will not be able to verify the end user experience entirely, so manual validation must also be done.

..  note::
    You will not be able to invoke any :ref:`WOPI Actions` successfully unless your WOPI domain has been added to the :ref:`allow list`.

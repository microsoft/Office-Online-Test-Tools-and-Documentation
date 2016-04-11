
..  _testing:

Testing |wac| integration
=========================

Before starting the :ref:`'go live' process <shipping>`, you should do the following testing on your integration.

..  important::
    You should do this testing only *after* the :ref:`validator` tests are passing.


WOPI implementation verification
--------------------------------

The :ref:`validator` is a very useful tool in testing your WOPI implementation. However, it cannot find all potential
problems in your implementation. Check the following items in addition to the results from the validator.


Non-standard CheckFileInfo properties
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Hosts should avoid returning properties in :ref:`CheckFileInfo` that are not defined as part of the WOPI protocol.
Properties may be added to CheckFileInfo in the future, and if hosts are already returning something for that property,
then it might not be the expected type, or the value might not be in the expected format, etc. Thus, hosts should not
return properties that aren't in this documentation.


CheckFileInfo property return types
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

The :ref:`validator` tests do not currently check for the proper return types. For example, while the :term:`version`
value is supposed to be a **string**, you can return an **int** and the tests will pass. This can cause errors in the
Office Online applications that are difficult to diagnose. For this reason, check that your return types are accurate.


Non-empty response body
~~~~~~~~~~~~~~~~~~~~~~~

Most WOPI operations do not return a response body. You should not return a response body unless the operation
specifically requires it. For example, successful :ref:`PutFile` responses should not have a body.


|wac| feature verification
--------------------------

Many |wac| features rely on a host's WOPI implementation. You should test the following features to help ensure your
WOPI implementation is correct and that the |wac| integration is well-executed.


Downloaded files should have most recent document changes
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

If you are providing a :term:`DownloadUrl`, you should ensure that a file downloaded using the |wac|
:guilabel:`Download a Copy` buttons contain the most recent edits. To test this:

#.  Open a document in edit mode.
#.  Make an edit to the document and wait for the :guilabel:`Saved to <HostName>` text to display.
#.  Click the :menuselection:`File --> Save As --> Download a Copy` button.
#.  Check that the downloaded file has the most recent edits you made to the document.


Download a Copy should not re-direct
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

As describe in :term:`DownloadUrl`, |wac| expects that when directing users to the DownloadUrl, the file will be
immediately downloaded. This URL should not direct the user to some separate UI to download the file.


Rename
~~~~~~

If you support renaming documents within |wac| (i.e. :term:`SupportsRename` is true), you should check that
the rename operation behaves as expected. To test this:

#.  Open a document in edit mode.
#.  Click on the document name in the top title bar.
#.  Rename the document.
#.  Exit the |wac| application and check that the file was renamed.

If you are displaying the document name in the browser window/tab using the HTML ``title`` tag, you should check that
the document name is updated after the file is renamed. If it is not, check that you are properly handling the
:js:data:`File_Rename` PostMessage.


Save As in Excel Online
~~~~~~~~~~~~~~~~~~~~~~~

Excel Online supports saving an open document as a new copy of that document using the
:menuselection:`File --> Save As --> Save As` button. This feature uses the :ref:`PutRelativeFile` WOPI operation.
You should test that this feature works as expected.


Co-authoring
~~~~~~~~~~~~

Co-authoring support is a major boon to users, but it also provides a useful way to verify your implementation of
file IDs and lock-related WOPI operations.

..  important::

    The |wac| applications each have unique behavior with respect to co-authoring. Thus it is critical to test
    co-authoring in all three applications.

To check that co-authoring behaves as expected, you'll need at least two different user accounts. Then, follow these
steps:

#.  As User A, share a document with User B.
#.  Open the document in edit mode as User A.
#.  Open that same document in edit mode as User B.
#.  Check that both instances of the |wac| application are participating in the co-authoring session.
#.  Make edits to the document as both users and ensure that both instances of the application remain connected to
    the co-authoring session.
#.  After making some edits, leave the session and verify that the saved file contains the edits made by both User A
    and User B.

Common issues
^^^^^^^^^^^^^

#.  If the users remain in different sessions (i.e. co-authoring does not occur) then it likely means your WOPI file
    IDs are not consistent. See :term:`file ID` for more information.
#.  If one of the users is 'kicked out' of the session while editing, then it likely means that you're rejecting
    lock-related requests that come from a different user than the one who originally took the lock. WOPI locks are
    not user-owned. See :term:`Lock` for more information.


Single-user co-authoring
~~~~~~~~~~~~~~~~~~~~~~~~

While the typical co-authoring scenario is two or more users collaborating on a single document in real-time, the
feature also provides other benefits as outlined in :ref:`coauth benefits`.

..  important::

    The |wac| applications each have unique behavior with respect to co-authoring. Thus it is critical to test
    co-authoring in all three applications.

To check that single-user co-authoring behaves as expected:

#.  Open a document in edit mode.
#.  Open a document in edit mode using the same user account originally used, but in a different browser.
#.  Check that both instances of the |wac| application are participating in the co-authoring session.


UI integration
--------------

Ensure you follow the :ref:`ui guidelines` as well as the terms of the Cloud Storage Partner Program contract.

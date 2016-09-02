
..  _Create New:

Creating new files using Office Online
======================================

Hosts can create new Office files using blank document templates from Office Online. In order to support this, hosts use
the :wopi:action:`editnew` WOPI action as follows:

#. Create a zero-byte file with the appropriate file extension (``docx`` for Word documents, ``pptx`` for PowerPoint
   presentations, and ``xlsx`` for Excel workbooks).
#. Invoke the :wopi:action:`editnew` action on the newly created file. Office Online will detect that the file is
   zero bytes based on the :term:`Size` property in the :ref:`CheckFileInfo` response.
#. Once the :wopi:action:`editnew` action has been invoked, Office Online will perform a :ref:`PutFile` operation on
   the file, overwriting it with template content appropriate to the file type. Note that this :ref:`PutFile`
   operation will be performed on an unlocked file, so hosts must ensure that :ref:`PutFile` operations on
   unlocked files that are zero bytes succeed. See the :ref:`PutFile` documentation for more information.

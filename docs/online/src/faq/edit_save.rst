
If I make an edit and immediately close the application, occasionally my edit is lost - why?
============================================================================================

|wac| applications send edits from the browser to the server as often as possible. However, this process is
not instantaneous and can be influenced by many factors including network latency and quality.

|wac| displays the save status in the bottom status bar:

..  figure:: /images/saved_to.png
    :alt: An image showing the 'Saved to...' UI in Office.

    The 'Saved to...' UI for OneDrive

If the status bar reads :guilabel:`Saved` or :guilabel:`Saved to <HOST NAME>`, then the edits have successfully made
it to the server. However, if the status bar reads :guilabel:`Saving...` or :guilabel:`Working...`, then the edits
have not yet been sent to the server and may be lost if the browser is closed or if you navigate away from the Office
Online application immediately.

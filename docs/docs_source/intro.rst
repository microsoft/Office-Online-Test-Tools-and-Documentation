
..  _intro:

Using the WOPI protocol to integrate with Office Online
=======================================================

You can use the Web Application Open Platform Interface (WOPI) protocol to integrate Office Online with your
application. The WOPI protocol enables Office Online to access and change files that are stored in your service.

To integrate your application with Office Online, you need to do the following:

#. Be a member of the *Office 365 - Cloud Storage Partner Program*. Currently integration with the Office Onlince cloud
   service is available to cloud storage partners. You can learn more about the program, as well as how to apply,
   at http://dev.office.com/programs/officecloudstorage.

#. Implement the WOPI protocol - a set of REST endpoints that expose information about the documents that you want to
   view or edit in Office Online.

#. Read some XML from an Office Online URL that provides information about the capabilities that Office Online
   applications expose, and how to invoke them – a process referred to as :ref:`WOPI discovery<Discovery>`.

#. Provide an HTML page (or pages) that will host the Office Online iframe. This is called the :term:`host page` and is
   the page your users visit when they open or edit Office documents in Office Online.

#. You can also optionally integrate your own UI elements with Office Online. For example, when users choose *Share* in
   Office Online, you can show your own sharing UI. These interaction points are described in the section titled
   :ref:`PostMessage`.


..  Hyperlinks

..  _[MS-WOPI]\: Web Application Open Platform Interface API:
    http://msdn.microsoft.com/en-us/library/hh622722(v=office.12).aspx

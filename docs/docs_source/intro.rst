
..  _intro:

Using the WOPI protocol to integrate with Office Online
=======================================================

You can use the Web Application Open Platform Interface (WOPI) protocol to integrate Office Online with your
application. The WOPI protocol enables you to access and change files that are stored on a server.

To integrate your application with Office Online, you need to do the following:

* Read some XML from an Office Online URL that provides information about the capabilities that Office Online
  applications expose, and how to invoke them – a process referred to as :ref:`WOPI discovery<Discovery>`.
* Provide a set of REST endpoints that expose information about the documents that you want to open or edit in Office
  Online. This is the core of the WOPI protocol.
* Provide an HTML page (or pages) that will host the Office Online iframe. This is called the :term:`host page` and is
  the page your users visit when they open or edit Office documents in Office Online.

You can also integrate your own UI elements with Office Online. For example, when users choose Share in Office Online,
you can show your own sharing UI. These interaction points are described in the section titled :ref:`PostMessage`.

The WOPI protocol is specified in `[MS-WOPI]\: Web Application Open Platform Interface API`_. This article
summarizes the key points that you'll need to know to use the protocol to integrate with Office Online.


..  Hyperlinks

..  _[MS-WOPI]\: Web Application Open Platform Interface API:
    http://msdn.microsoft.com/en-us/library/hh622722(v=office.12).aspx

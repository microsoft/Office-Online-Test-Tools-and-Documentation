
Glossary
========

..  glossary::
    :sorted:

    Broadcast
        A broadcast is a special |wac| scenario where navigation through a document is driven by one or more
        presenters. A set of attendees can follow along with the presenter remotely.

        ..  seealso:: :wopi:action:`present`, :wopi:action:`attend`

    Host Page
    Host Frame
    Outer Frame
        The host page (also called the 'host frame' or 'outer frame') is the HTML page which will host an iframe that
        points to an |wac| application.

        .. seealso:: :ref:`Host page`

    Session Context
        The Session Context is an optional parameter that a host can include on a WOPI request. It is a **string**, and
        is passed to |wac| in the ``sc`` query string parameter. If included on a WOPI request, |wac| will return the
        value of ``sc`` as the value of the **X-WOPI-SessionContext** HTTP header when making the :ref:`CheckFileInfo`
        and :ref:`CheckFolderInfo` WOPI requests.

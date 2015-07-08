
..  _Endpoints:

WOPI REST endpoints
===================

..  spelling::

    wopi


A WOPI host needs to provide some information about the files it stores, as well as the binary contents of those files.
Because WOPI is a REST-based callback interface, this information is provided via specific URLs. A WOPI host provides a
small REST API around its files. Office Online uses those REST API to work with the files.

The following table shows the structure of the REST API for each resource type.

+----------------+-----------------------------------------------------+-----------------------------------------------+
| Resource       | URL                                                 | Description                                   |
+================+=====================================================+===============================================+
| Files          | HTTP://server/<...>/wopi*/files/<file_id>           | Provides access to information about a file   |
|                |                                                     | and allows for file-level operations.         |
+----------------+-----------------------------------------------------+-----------------------------------------------+
| Folders        | HTTP://server/<...>/wopi*/folders/<file_id>         | Provides access to information about a folder |
|                |                                                     | and allows for folder-level operations.       |
+----------------+-----------------------------------------------------+-----------------------------------------------+
| File contents  | HTTP://server/<...>/wopi*/files/<file_id>/contents  | Provides access to operations that get and    |
|                |                                                     | update the contents of a file.                |
+----------------+-----------------------------------------------------+-----------------------------------------------+
| Folder contents| HTTP://server/<...>/wopi*/folder/<file_id>/children | Provides access to the files and folders in   |
|                |                                                     | a folder.                                     |
+----------------+-----------------------------------------------------+-----------------------------------------------+

Not all operations and endpoints are required. The discovery XML describes which WOPI endpoints and operations are
required for particular actions. All actions require the :ref:`CheckFileInfo` (exposed via the :ref:`Files endpoint`)
and :ref:`GetFile` (exposed via the :ref:`File contents endpoint`) operations.

..  _Executing WOPI operations:

Executing WOPI operations
-------------------------

Each WOPI endpoint supports a number of operations specified by the caller in the **X-WOPI-Override** request header.
Parameters for each operation are also passed in HTTP headers, which all begin with ``X-WOPI-``. This way, executing a
WOPI operation is as simple as issuing a request to the appropriate REST endpoint and passing appropriate HTTP header
values with the request.


..  _Common headers:

Common WOPI request and response headers
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

..  default-domain:: http

..  get:: /wopi*/

    All WOPI requests from Office Online may contain the following request and response headers. Note that individual
    WOPI operations may send additional request headers or require additional response headers. These unique headers
    are described in the documentation for each WOPI operation.

    :reqheader Authorization:
        The **string** value ``Bearer <token>`` where ``<token>`` is the :term:`access token` for the request. Note that
        Office Online also passes the access token as a query parameter.

    :reqheader X-WOPI-ClientVersion:
        A **string** indicating the version of the Office Online server making the request. There is no standard
        for how this string is formatted, and it must not be used for anything other than logging.

    :reqheader X-WOPI-CorrelationID:
        A **string** that the host should log when logging server activity to correlate that activity with Office Online
        server logs.

    :reqheader X-WOPI-MachineName:
        A **string** indicating the name of the Office Online server making the request. This string must not be
        used for anything other than logging.

    :reqheader X-WOPI-PerfTraceRequested:
        This header is not currently used by Office Online but is reserved for future use.

    :reqheader X-WOPI-Proof:
        A **string** representing data signed using a SHA256 (A 256 bit SHA-2-encoded [`FIPS 180-2`_]) encryption algorithm.
        See :ref:`Proof keys` for more information regarding the use of this header value.

    :reqheader X-WOPI-ProofOld:
        A **string** representing data signed using a SHA256 (A 256 bit SHA-2-encoded [`FIPS 180-2`_]) encryption algorithm.
        See :ref:`Proof keys` for more information regarding the use of this header value.

    :reqheader X-WOPI-TimeStamp:
        A **64-bit integer** that represents the number of 100-nanosecond intervals that have elapsed between
        12:00:00 midnight, January 1, 0001, :abbr:`UTC (Coordinated Universal Time)` and the :abbr:`UTC (Coordinated
        Universal Time)` time of the request. Office Online uses the following C# code to set this value:
        :code:`DateTime.UtcNow.Ticks`.

        ..  seealso::
            `DateTime.Ticks Property <https://msdn.microsoft.com/en-us/library/cc319699.aspx>`_

    :resheader X-WOPI-MachineName:
        A **string** indicating the name of the WOPI host server handling the request. Office Online only uses this string
        for logging purposes.

    :resheader X-WOPI-PerfTrace:
        This header is not currently used by Office Online but is reserved for future use.

    :resheader X-WOPI-ServerError:
        A **string** indicating that an error occurred while processing the WOPI request. This header should be included
        in a WOPI response if the status code is :http:statuscode:`500`. The value should contain details about the error.
        Office Online only uses this string for logging purposes.

    :resheader X-WOPI-ServerVersion:
        A **string** indicating the version of the WOPI host server handling the request. There is no standard
        for how this string is formatted, and Office Online uses it only for logging purposes.


Common query string parameters
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

The following query string parameters will be included with all WOPI requests from Office Online.

=============  ===========
URI Parameter  Description
=============  ===========
``token``      A string representing the :term:`Access Token` for the request.
``id``         A string representing the :term:`File ID` for the request.
=============  ===========

The ``token`` and ``id`` parameters are a core part of all WOPI requests. The URI syntax for using these parameters is
described in the documentation for each WOPI operation. The host provides both ``token`` and ``id`` by transforming
the **urlsrc** value for the action (provided in :ref:`discovery`) and appending parameters to the URL as described in
:ref:`Action URLs`.


..  _Common headers:
..  _standard headers:

Standard WOPI request and response headers
==========================================

..  default-domain:: http

..  get:: /wopi*/
    :noindex:

    All WOPI requests may contain the following request and response headers. Note that individual WOPI operations
    may send additional request headers or require additional response headers. These unique headers are described in
    the documentation for each WOPI operation.

    :reqheader Authorization:
        The **string** value ``Bearer <token>`` where ``<token>`` is the :term:`access token` for the request.

        ..  admonition:: |wac| Tip

            Note that |wac| also passes the access token as a query parameter.

    :reqheader X-WOPI-AppEndpoint:
        A **string** used to indicate the endpoint of the WOPI client sending the request. This is typically used to
        indicate geographic location, datacenter, etc. This string must not be used for anything other than logging.

    :reqheader X-WOPI-ClientVersion:
        A **string** indicating the version of the WOPI client making the request. There is no standard
        for how this string is formatted, and it must not be used for anything other than logging.

    :reqheader X-WOPI-CorrelationId:
        A **string** that the host should log when logging server activity to correlate that activity with WOPI
        client activity.

        ..  admonition:: |wac| Tip

            See :ref:`Troubleshooting` for more information on how this ID is used in |wac|.

    :reqheader X-WOPI-DeviceId:
        A **string** indicating the ID of the device making the request. This string must not be used for anything
        other than logging.

    :reqheader X-WOPI-MachineName:
        A **string** indicating the name of the WOPI client machine making the request. This string must not be
        used for anything other than logging.

    :reqheader X-WOPI-PerfTraceRequested:
        This header is reserved for future use.

    :reqheader X-WOPI-Proof:
        A **string** representing data signed using a SHA256 (A 256 bit SHA-2-encoded [`FIPS 180-2`_]) encryption
        algorithm. See :ref:`Proof keys` for more information regarding the use of this header value.

    :reqheader X-WOPI-ProofOld:
        A **string** representing data signed using a SHA256 (A 256 bit SHA-2-encoded [`FIPS 180-2`_]) encryption
        algorithm. See :ref:`Proof keys` for more information regarding the use of this header value.

    :reqheader X-WOPI-TimeStamp:
        A **64-bit integer** that represents the number of 100-nanosecond intervals that have elapsed between
        12:00:00 midnight, January 1, 0001, :abbr:`UTC (Coordinated Universal Time)` and the :abbr:`UTC (Coordinated
        Universal Time)` time of the request. This value can be set in .NET using the following C# code:
        :code:`DateTime.UtcNow.Ticks`.

        ..  seealso::
            `DateTime.Ticks Property <https://msdn.microsoft.com/en-us/library/cc319699.aspx>`_

    :resheader Content-Type:
        This header should be set to a value appropriate to the type of data being included in the response. For
        example, when responding to a WOPI request with JSON-encoded data in the response body, the
        :http:header:`Content-Type` header should be set to ``application/json``. WOPI clients may ignore a response
        with a :http:header:`Content-Type` header that does not match the expected type.

    :resheader X-WOPI-HostEndpoint:
        A **string** used to indicate the endpoint of the WOPI host handling the request. This is analogous to the
        **X-WOPI-AppEndpoint** request header and is typically used to indicate geographic location, datacenter, etc.
        This string must not be used for anything other than logging.

    :resheader X-WOPI-MachineName:
        A **string** indicating the name of the WOPI host server handling the request. This string must not be
        used for anything other than logging.

    :resheader X-WOPI-PerfTrace:
        This header is reserved for future use.

    :resheader X-WOPI-ServerError:
        A **string** indicating that an error occurred while processing the WOPI request. This header should be included
        in a WOPI response if the status code is :http:statuscode:`500`. The value should contain details about the
        error. This string must not be used for anything other than logging.

    :resheader X-WOPI-ServerVersion:
        A **string** indicating the version of the WOPI host server handling the request. There is no standard
        for how this string is formatted, and it must not be used for anything other than logging.


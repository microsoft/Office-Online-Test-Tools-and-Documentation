
..  important::

    Note that WOPI clients are not required to pass the :term:`access token` in the :http:header:`Authorization`
    header, but they must send it as a URL parameter in all WOPI operations. Thus, for maximum compatibility, WOPI
    hosts should either use the URL parameter in all cases, or fall back to it if the :http:header:`Authorization`
    header is not included in the request.

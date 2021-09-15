
.. meta::
    :robots: noindex

What are the file sizes supported by |wac|?
===========================================

The explicit limits, where applicable, are listed in the table below. However, note that there is a 60-second file
download time out that applies to all :ref:`GetFile` operations, and this time out can affect the perceived file size
limit. In practice, this time out is rarely hit, since connectivity and bandwidth is typically very good between
|wac| and host datacenters. However, hosts should be aware of this limit.

..  tip::
    The :term:`FileUrl` property can be set to change the URL that |wac| will use to download files from the
    host. This can be used to increase download speeds depending on the host's architecture.

..  csv-table:: File size limits
    :file: ../tables/file_sizes.csv
    :header-rows: 1

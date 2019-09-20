
What are the IP ranges and ports used by |wac|?
===============================================

|wac| does not provide IP ranges for partners to use to restrict traffic (i.e. IP-based ACLs). |wac| adds new servers
and datacenters regularly and such IP lists will be out of date often. Hosts should use :ref:`proof keys <Proof Keys>`
if they wish to verify that requests are coming from |wac|.

All WOPI communication is done using port 443, the standard HTTPS port.

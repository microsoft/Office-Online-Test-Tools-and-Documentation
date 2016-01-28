
Hosts can optionally include the ContainerInfo property, which should match the :ref:`CheckContainerInfo` response
for the root container. If not provided, the WOPI client will call :ref:`CheckContainerInfo` to retrieve it.
Including this property in the CreateChildContainer response is strongly recommended so that the WOPI client does not
need to make an additional call to :ref:`CheckContainerInfo`.

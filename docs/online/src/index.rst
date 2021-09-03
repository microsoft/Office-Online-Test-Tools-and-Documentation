
..  _intro:

Using the WOPI protocol to integrate with |wac|
===============================================

..  sidebar:: Note

    This documentation is a work in progress. Topics marked with a |stub-icon| are placeholders that have not been
    written yet. You can track the status of these topics through our public documentation `issue tracker`_.

..  _issue tracker: https://github.com/Microsoft/Office-Online-Test-Tools-and-Documentation/issues

You can use the Web Application Open Platform Interface (WOPI) protocol to integrate |wac| with your
application. The WOPI protocol enables |wac| to access and change files that are stored in your service.

To integrate your application with |wac|, you need to do the following:

#. Be a member of the |cspp|. Currently integration with the |wac| cloud service is available to cloud storage
   partners. You can learn more about the program, as well as how to apply, at
   https://developer.microsoft.com/office/cloud-storage-partner-program.

   ..  include:: /_fragments/intended_isv.rst

#. Implement the WOPI protocol - a set of REST endpoints that expose information about the documents that you want to
   view or edit in |wac|. The set of WOPI operations that must be supported is described
   in the section titled :ref:`requirements`.

#. Read some XML from an |wac| URL that provides information about the capabilities that |wac|
   applications expose, and how to invoke them; this process is called :ref:`WOPI discovery<Discovery>`.

#. Provide an HTML page (or pages) that will host the |wac| iframe. This is called the :term:`host page` and is
   the page your users visit when they open or edit |Office| documents in |wac|.

#. You can also optionally integrate your own UI elements with |wac|. For example, when users choose
   :guilabel:`Share` in |wac|, you can show your own sharing UI. These interaction points are described in
   the section titled :ref:`PostMessage`.


How to read this documentation
------------------------------

This documentation contains an immense amount of information about how to integrate with |wac|,
including details about how to implement the WOPI protocol, how |wac| uses the protocol, how you can test
your integration, the :ref:`process for shipping your integration <shipping>`, and much more. It can be difficult to
know where to begin. The following guidelines can help you find the specific sections in this documentation that will
be most helpful to you.


**If you want to know why Office integration may be useful to you,** and what capabilities it provides, you
should read the following sections:

* :ref:`overview` - A high level overview of the scenarios enabled by |wac| integration, as well as a brief
  description of some of the key technical elements in a successful integration.
* :ref:`intro` - A brief description of the technical pieces that you must implement to integrate with |wac|.


**If you are an engineer** about to begin implementing a WOPI host, you should first read the :ref:`key concepts`
section. When designing your WOPI implementation, you must keep in mind the expectations around
:term:`file IDs <file ID>`, :term:`access tokens <access token>`, and :term:`locks <lock>`. These concepts are
critical to a successful integration with |wac|. You should also read the following sections:

* :ref:`validator`
* :ref:`troubleshooting`
* :ref:`environments`

**If you are a back-end engineer,** you should begin with the following sections in addition to the :ref:`key concepts`
section and other general sections listed above:

* :ref:`discovery`
* :ref:`endpoints`
* :ref:`CheckFileInfo`
* :ref:`Lock`

Once you have read those sections, any of the other core WOPI operations are useful to read through, such as
:ref:`GetFile`, :ref:`PutFile`, :ref:`PutRelativeFile`, :ref:`UnlockAndRelock`, etc.


**If you are a front-end engineer,** you should begin with the following sections in addition to the
:ref:`key concepts` section and other general sections listed above:

* :ref:`host page`
* :ref:`postmessage`
* :ref:`discovery`, specifically the :ref:`WOPI actions` section


Finally, **if you are looking for more details about the process for shipping your integration,** see the
:ref:`shipping` section.


..  Hyperlinks

..  _[MS-WOPI]\: Web Application Open Platform Interface API:
    http://msdn.microsoft.com/en-us/library/hh622722(v=office.12).aspx


..  Navigation/TOC

..  toctree::
    :maxdepth: 2
    :glob:
    :hidden:
    :caption: Overview
    :name: overviewtoc

    /overview
    WOPI REST API Reference <https://wopi.readthedocs.io/projects/wopirest/>
    /changelog


..  toctree::
    :maxdepth: 2
    :glob:
    :hidden:
    :caption: Shipping
    :name: shippingtoc

    /build_test_ship/shipping
    /build_test_ship/trello


..  toctree::
    :maxdepth: 2
    :glob:
    :hidden:
    :caption: Building

    /discovery
    /hostpage
    /build_test_ship/environments
    /build_test_ship/settings
    /build_test_ship/code_samples


..  toctree::
    :maxdepth: 2
    :glob:
    :hidden:
    :caption: Testing
    :name: testingtoc

    /build_test_ship/validator
    /build_test_ship/testing
    /build_test_ship/ui_guidelines
    /build_test_ship/troubleshooting
    /build_test_ship/common_issues


..  toctree::
    :maxdepth: 2
    :glob:
    :hidden:
    :caption: Customization

    /scenarios/customization
    PostMessage: interact with Office </scenarios/postmessage>


..  toctree::
    :maxdepth: 2
    :glob:
    :hidden:
    :caption: Scenarios

    /scenarios/coauth
    /scenarios/createnew
    /scenarios/business
    /scenarios/conversion
    /scenarios/proofkeys
    /scenarios/hostinstalladdins
    /scenarios/workflow
    /scenarios/embedding


..  toctree::
    :maxdepth: 2
    :glob:
    :hidden:
    :caption: Other Considerations

    /security
    /performance


..  toctree::
    :maxdepth: 2
    :glob:
    :hidden:
    :caption: Reference

    /faq
    /known_issues
    /glossary
    /legal

..  toctree::
    :maxdepth: 2
    :glob:
    :hidden:
    :caption: Contributing

    /contributing/build_docs
    /contributing/style_guide

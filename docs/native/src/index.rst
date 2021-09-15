
.. meta::
    :robots: noindex

..  _native wopi:

Using the WOPI protocol to integrate with Office for iOS and Android
====================================================================

..  sidebar:: Note

    This documentation is a work in progress. Topics marked with a |stub-icon| are placeholders that have not been
    written yet. You can track the status of these topics through our public documentation `issue tracker`_.

..  _issue tracker: https://github.com/Microsoft/Office-Online-Test-Tools-and-Documentation/issues

You can use the Web Application Open Platform Interface (WOPI) protocol to integrate |Office iOS Android| with your
application. The WOPI protocol enables |Office iOS Android| to access and change files that are stored in your service.

To integrate your application with |Office iOS Android|, you need to do the following:

#. Be a member of the |cspp|. Currently integration with |Office iOS Android| using WOPI
   is available to cloud storage partners. You can learn more about the program, as well as how to apply, at
   https://developer.microsoft.com/office/cloud-storage-partner-program.

#. Implement the WOPI protocol - a set of REST endpoints that expose information about the documents that you want to
   view or edit in |Office iOS Android|. The set of WOPI operations that must be supported is described
   in the section titled :ref:`requirements`.

#. Provide the required on-boarding information as described in the section titled :ref:`onboarding`.

..  Navigation/TOC

..  toctree::
    :maxdepth: 2
    :glob:
    :hidden:
    :caption: Overview
    :name: overviewtoc

    /overview
    /differences
    /wopi_requirements
    WOPI REST API Reference <https://wopi.readthedocs.io/projects/wopirest/>


..  toctree::
    :maxdepth: 2
    :glob:
    :hidden:
    :caption: Scenarios

    /scenarios/browse
    /scenarios/from_app
    /scenarios/operational_flows

..  toctree::
    :maxdepth: 2
    :glob:
    :hidden:
    :caption: Onboarding
    :name: onboardingtoc

    /onboarding/onboarding

..  toctree::
    :maxdepth: 2
    :glob:
    :hidden:
    :caption: Identity

    /identity/bootstrapper
    /identity/tokenissuance
    /identity/specifytokenissuance
    /identity/sessioncontext
    /identity/oauthparams
    /identity/clientsecret
    /identity/apptoapp

..  toctree::
    :maxdepth: 2
    :glob:
    :hidden:
    :caption: Testing

    /testing/testcases
    /testing/validator

..  toctree::
    :maxdepth: 2
    :glob:
    :hidden:
    :caption: Shipping

    /shipping/launchrequirements

..  toctree::
    :maxdepth: 2
    :glob:
    :hidden:
    :caption: Reference

    /reference/faq
    /reference/knownissues
    /reference/glossary


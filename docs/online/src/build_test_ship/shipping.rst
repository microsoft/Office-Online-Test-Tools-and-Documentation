
..  _shipping:

Shipping your |wac| integration
=======================================

Once you believe you are ready to ship your integration, you should contact Microsoft and ask to begin the launch
process. This process will prepare you to ship your |wac| integration.

..  include:: /_fragments/readiness.rst

The launch process consists of three phases:

#. **Validation** - Microsoft will validate your WOPI implementation as well as your UI integration. Depending on what
   issues are uncovered during validation, this may take some time. You should estimate a two-week turnaround time
   assuming there are no major issues uncovered. However, we recommend allowing at least a month unless your
   integration is very simple.

   ..  important::
       You can avoid delays in validation by ensuring that your implementation is consistent with this documentation
       and that you have done manual testing using our :ref:`testing guide<testing>` and that the :ref:`validator`
       tests are passing before beginning the launch process.

#. **Production testing** - If requested, Microsoft will enable you to use the production |wac| environment
   for preliminary smoke-testing prior to enabling your full production access. This should be basic testing performed
   by the host since the production environment is slightly different than the test environment, and different issues
   may be uncovered. This can be as long or as short as the host deems necessary.

#. **Sign off and roll out** - Once Microsoft has signed off on your integration, you can begin to roll out to your
   users. Depending on your traffic estimates, Microsoft may request that you roll out over a period of several days
   to ensure you do not overload |wac| or your WOPI servers.

To manage this process, Microsoft will create a dedicated `Trello <https://trello.com>`_ board to track issues and
provide a common communication channel between your team and Microsoft. You can learn more about how to use this
Trello board in the :ref:`trello board` section.

Before starting the launch process, you should read the below sections for an overview of the types of questions
and issues that you may need to address during the process.


Preparing for the launch process
--------------------------------

WOPI validation
~~~~~~~~~~~~~~~

As part of the validation process, Microsoft will test your WOPI implementation using the :ref:`validator`. All of
the tests in the following categories must be passing:

..  include:: /_fragments/required_validator_tests.rst


Manual testing
~~~~~~~~~~~~~~

In addition to checking the WOPI validation results, Microsoft will do some manual validation of scenarios that
cannot currently be tested using the WOPI validator. You should follow the steps in the :ref:`testing guide<testing>`
and fully test these scenarios prior to starting the launch process.

..  important::

    Multi-user co-authoring is used to test parts of a WOPI server implementation. Therefore, the validation process
    requires that multi-user co-authoring can be tested using the test accounts

..  _test accounts:

Test accounts and video
~~~~~~~~~~~~~~~~~~~~~~~

In order to enable your WOPI host to use |wac|'s :ref:`production environment <production environment>`,
Microsoft will perform some validation of your WOPI implementation and |wac| integration. This requires that
you provide Microsoft **test accounts** in addition to **a video demonstrating the testing.**


Requirements
^^^^^^^^^^^^

You must provide the following information when submitting your integration for validation:

*   **At least two (2)** test accounts for your test system; the accounts you provide *must* be capable of doing the
    following:

    *   **Upload new files** of any file type you open with |wac|, including ``.wopitest`` and ``.wopitestx``
        files used by the WOPI validator
    *   **Open .wopitest files in the WOPI validator**
    *   The accounts **must be capable of testing multi-user co-authoring;** co-authoring with a single user account
        is not sufficient

*   A login URL to use to sign in to your test system
*   A video (or several videos) demonstrating the following scenarios **using the test accounts provided**

    *   Upload a new ``.wopitest`` file and open it in the WOPI validator

        *   Run all the WOPI validator tests and capture the passing results in your
            video; :ref:`all tests listed in the documentation<validator tests>` must be passing

    *   Demonstrate your integration with **Word** by uploading a new Word document and editing it in Word on the web
    *   Demonstrate your integration with **PowerPoint** by uploading a new PowerPoint document and editing it in
        |ppt-web|
    *   Demonstrate your integration with **Excel** by uploading a new Excel document and editing it in |excel-web|
    *   Open a document as two different user accounts and demonstrate multi-user coauthoring
        (:ref:`test details<coauth tests>`)

        *   If an explicit "share" action is needed to share a document between two users, demonstrate how to do this
            in your video

..  include:: /_fragments/readiness.rst

Once test accounts are provided, Microsoft will typically test your implementation the first Tuesday following when the
accounts are provided. Usually testing can be completed within two to four weeks. However, this time line is subject
to demand; if other partners are already being tested it may take additional time for Microsoft to begin testing your
implementation. In addition, if implementation issues are uncovered during testing the process may take longer.


WOPI implementation questionnaire
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

There are some aspects of your WOPI implementation that are particularly critical to the success of your integration.
In order to verify these parts of your implementation, Microsoft will ask you to answer some questions regarding
your specific WOPI implementation. These questions are included below.

..  note::

    This list of questions is subject to change. Microsoft will provide you with a specific list of questions as part
    of the launch process that may differ from the list below.

#. Confirm that your File IDs meet the :term:`criteria listed in the documentation <file id>`. |wac|
   expects file IDs to be unique and consistent over time, as well as when accessed by different users or via
   different UI paths (e.g. a given file might be available in two different parts of your UI, such as in a typical
   folder and also in search results. If the document is meant to be the same, then the file IDs should match.
   Otherwise users will see unexpected behavior when they access the same file via different UI paths).

#. Confirm you're providing a user ID using the :term:`UserId` field and that the ID is unique and consistent
   over time :ref:`as described here <User identity requirements>`.

#. Confirm that the value in the :term:`OwnerId` field represents the user who owns the document and is unique
   and consistent over time :ref:`as described here <User identity requirements>`.

#. Are you sending the :term:`SHA256` value in :ref:`CheckFileInfo`?

#. Are you using the :ref:`business user flow <Business editing>`?

#. What :ref:`supports properties` are you passing in :ref:`CheckFileInfo`?

#. Do you use IPv6 in your datacenters?


Production settings check
~~~~~~~~~~~~~~~~~~~~~~~~~

Prior to enabling your integration in the :ref:`production environment <production environment>`, Microsoft will ask
you to verify your current :ref:`settings`, including your entries in the :ref:`allow list` and
:ref:`redirect domains`.

..  include:: /_fragments/settings_change_warning.rst


Service management contacts
~~~~~~~~~~~~~~~~~~~~~~~~~~~

|wac| is a worldwide cloud service, and is thus monitored at all times. As part of the launch process,
Microsoft will provide you with information regarding how to escalate service quality issues with |wac|'s
on-call engineers.

In order to use the :ref:`production environment <production environment>`, you must also provide a contact for
Microsoft's on-call engineers to reach if |wac| detects an issue that we suspect is due to a problem on the
host side. For example, |wac|'s monitoring systems might detect error rates for sessions spiking, and the
on-call engineer would contact the host to see if it's a known issue on the host side. Ideally this emergency contact
can be reached 24x7, either by phone or email.


Rollout schedule and traffic estimates
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Typically Microsoft asks partners to roll out over a period of time - between a few days to two weeks - depending
on the anticipated traffic. For smaller hosts this is not always necessary. If you're already planning on doing this,
you should communicate the schedule to Microsoft (i.e. 10% day 1, 50% day 2, etc.). If you're not, you must
coordinate with Microsoft to ensure this is appropriate given your traffic estimates.

In order to best plan the rollout, you should be prepared to provide Microsoft with updated traffic estimates.
Ideally these will be broken down by view/edit, file type, and geography, but provide whatever you can.


Production access
~~~~~~~~~~~~~~~~~

Once you and Microsoft have agreed on a rollout plan and Microsoft has signed off on your WOPI implementation, your
WOPI host will be enabled in the :ref:`production environment <production environment>`. You should plan to do some
basic testing against the production environment prior to rollout to ensure there are no unique issues using that
environment. Once you have completed that testing, you can roll your integration out to users according the
agreed-upon rollout schedule.

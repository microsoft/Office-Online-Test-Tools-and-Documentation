
..  _workflow:

Integrating |wac| with document workflow processes
==================================================

..  include:: ../../../_shared/prerelease.rst

Hosts can integrate |wac| into workflow processes, so that users can use UI directly in |wac| to manage documents in
a workflow.

For example, consider a host that caters to customers in the education field. The host may provide a way for students
to submit documents as part of an assignment. With the |wac| workflow features, hosts can configure |wac| to display
a :guilabel:`Submit` button directly in the |wac| UI that, when activated, will display host-specific UI allowing the
student to submit the document.


Supporting workflows in |wac|
-----------------------------

In |wac|, documents participating in workflows display a button that takes the place of the :guilabel:`Share`
button. The button's text is specific to the :term:`WorkflowType`.

======================  ==========================
:term:`WorkflowType`    |wac| workflow button text
======================  ==========================
Assign                  Manage Assignment
Submit                  Submit
======================  ==========================

..  important::

    In |wac|, the *Assign* and *Submit* workflow types are mutually exclusive. Only one of the two workflow
    types should be set for a given document session.

Much like :guilabel:`Share`, when clicked, the workflow button can either navigate to a URL in a new browser tab/window
or send a message to the :term:`host frame`.

To navigate to a URL, set the :term:`WorkflowUrl` property to the URL that |wac| should navigate to.

To post a message to the host frame instead, set the :term:`WorkflowPostMessage` property to ``true``. When clicked,
|wac| will send the :js:data:`UI_Workflow` message to the host frame. The message includes the type of workflow that
the document is participating in.

..  tip::

    |wac| will always save the latest copy of the document to the host when the workflow UI is activated. This
    ensures that the host always has the latest copy of the document in order to use it in the workflow.


Important considerations
------------------------

WOPI clients, including |wac|, do not understand what the workflow is and how it behaves. In other words, when you use
the :term:`WorkflowUrl` or :term:`WorkflowPostMessage` properties, |wac| will display the workflow button and behave
appropriately when triggered. However, if, for example, you wish to prevent a user from submitting the same document
multiple times, you must handle that in your own UI. In other words, |wac| and other WOPI clients do not know
anything about the workflow process except that the document is participating in a workflow so that the appropriate
UI can be displayed. This provides the WOPI host great flexibility in their workflows, but the host is also
responsible for managing the workflow as a whole.

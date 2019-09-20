
..  _coauth:
..  _coauthoring:

Co-authoring using |wac|
========================

..  include:: /_fragments/edit_support.rst

|wac| supports multiple users editing a document at the same time. Co-authoring in |wac| includes
real-time content updates between all users editing the document, as well as presence information and real-time
cursor tracking for each user.

There are no unique WOPI host requirements beyond those described in this documentation in order to support
co-authoring. However, the guidelines around file IDs and locks, as described in the :ref:`key concepts` section, are
very important in order to enable co-authoring.


..  _coauth benefits:

Benefits from co-authoring support
----------------------------------

Editing documents requires that |wac| take a :term:`lock` on the file to ensure that only |wac| is writing to the
file. In cases where co-authoring is not supported, this causes two problems.

First, users are unable to make changes to the file while someone else is editing it. This problem is avoided when
co-authoring is supported. With co-authoring in |wac|, users are never locked out of editing a document.

Second, while |wac| makes every attempt to unlock files after users have finished editing documents, there are
circumstances where this may not happen. If :ref:`Unlock` is not called successfully, then the file will stay locked
until the lock times out. This can mean that a user can be locked out of editing a file even if they were the one
that originally locked it. When co-authoring is supported, the fact that the file is locked is not a problem; |wac|
will allow the user to edit the document as if he or she is 'co-authoring with him/herself'.

Thus, in addition to the obvious benefits for multi-user editing that come along with real-time co-authoring,
co-authoring in |wac| provides benefits for single-user editing as well.


How co-authoring works in |wac|
-------------------------------

When multiple users edit a single document using |wac|, the |wac| service manages the document
changes and any necessary merges internally.

When a user attempts to edit a document, |wac| takes a lock using the :ref:`Lock` operation and the access
token of the current user. When additional users then attempt to edit the same document, |wac| will verify
those users have permission to edit the document by calling :ref:`CheckFileInfo` using each user's access token. If
the CheckFileInfo call succeeds and the user has permissions to edit, they will join the editing session already in
progress.

Users who are collaborating will see the document content update in real-time as other users edit. However, Office
Online will only call :ref:`PutFile` periodically with the updated document contents. There are three critical
questions to consider with respect to co-authoring sessions:

#. **Auto-save frequency**: How frequently will the application call :ref:`PutFile`?
#. **PutFile access token**: Which user's access token will be used when :ref:`PutFile` is called?
#. **Permissions check frequency**: How often will the application verify that a user still has appropriate permissions
   to edit the document?

Question 3 is important because :ref:`PutFile` is called using a single user's access token, so a WOPI host will only
be able to check permissions of the user whose access token is used. Hosts rely on |wac| to verify users
still have edit permissions periodically.

The answers to these questions differ between the |wac| applications. The table below summarizes the
behavior for each application, and the following sections describe this behavior in more detail.

..  csv-table:: Summary of co-authoring behavior for Office applications
    :file: ../tables/coauth_behavior.csv
    :header-rows: 1


|word-web| co-authoring behavior
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

**Auto-save frequency**: Every 30 seconds if document is updated. In other words, if users are actively editing a
document, :ref:`PutFile` will be called every 30 seconds. However, if users stop editing for a period of time, Word
Online will not call :ref:`PutFile` until document changes are made again.

**PutFile access token**: For each auto-save interval, |word-web| will use the access token of the user who made the
most recent change to the document. In other words, if User A and B both make changes to the document within the
same auto-save interval, but User B made the last change, |word-web| will use User B's access token when calling
:ref:`PutFile`. The file will have both users' changes, but the PutFile request will use User B's access token.

If, on the other hand, User A made a change in one auto-save interval, and User B made a change in another auto-save
interval, then |word-web| will make two PutFile requests, each using the access token of the user who made the change.

**Permissions check frequency**: |word-web| will verify that a user has permissions by calling CheckFileInfo at
least every 5 minutes while the user is in an active session.


|excel-web| co-authoring behavior
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

**Auto-save frequency**: Every 2 minutes.

**PutFile access token**: |excel-web| will always use the access token of the user who joined the editing session
most recently. This user is called the *principal user*. If the principal user leaves the session, then the last user
who joined the session becomes the principal user. In other words, if User A starts editing, then User A is the
principal user. If User B then joins the session, User B becomes the principal user, and |excel-web| will use User B's
access token when calling :ref:`PutFile`. The file will have both users' changes, but the PutFile request will use
User B's access token. If User C then joins the session, User C becomes the principal user.

If User C then leaves the session, then User B becomes the principal user, and User B's access token will be used when
calling PutFile.

**Permissions check frequency**: |excel-web| will verify that a user has permissions by calling :ref:`RefreshLock` at
least every 15 minutes while the user is in an active session.


|ppt-web| co-authoring behavior
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

**Auto-save frequency**: Every 60 seconds if document is updated. In other words, if users are actively editing a
document, :ref:`PutFile` will be called every 60 seconds. However, if users stop editing for a period of time,
|ppt-web| will not call :ref:`PutFile` until document changes are made again.

..  note::

    During a single-user editing session, |ppt-web| will only call :ref:`PutFile` every 3 minutes. During
    an active co-authoring session, that frequency is increased to every 60 seconds.

**PutFile access token**: For each auto-save interval, |ppt-web| will use the access token of the user who made
the most recent change to the document. In other words, if User A and B both make changes to the document within the
same auto-save interval, but User B made the last change, |ppt-web| will use User B's access token when calling
:ref:`PutFile`. The file will have both users' changes, but the PutFile request will use User B's access token.

If, on the other hand, User A made a change in one auto-save interval, and User B made a change in another auto-save
interval, then |ppt-web| will make two PutFile requests, each using the access token of the user who made the
change.

**Permissions check frequency**: |ppt-web| will verify that a user has permissions by calling CheckFileInfo at
least every 5 minutes while the user is in an active session.


Scenarios
---------

The following scenarios illustrate the behavior WOPI hosts can expect for each |wac| application when
users are co-authoring.

All scenarios described here assume the following baseline flow.

..  note::

    The pattern of WOPI calls described below is not meant to be absolutely accurate. |wac| may make
    additional WOPI calls beyond those described below. These scenarios are meant only to illustrate the key behavioral
    aspects of the |wac| applications; they are not an absolute transcript of WOPI traffic between Office
    Online and a WOPI host.

Scenario baseline
~~~~~~~~~~~~~~~~~

#. User A begins editing a document.
#. |wac| calls :ref:`CheckFileInfo` using User A's access token to verify the user has edit permissions.
#. |wac| calls :ref:`Lock` using User A's access token.
#. User B tries to edit the same document.
#. |wac| calls :ref:`CheckFileInfo` using User B's access token to verify the user has edit permissions.

Key points
^^^^^^^^^^

* |wac| will always verify each user has appropriate edit permissions to the document by calling
  :ref:`CheckFileInfo` using that user's access token before allowing them to join the edit session.
* :ref:`Lock` will always be called using the access token of the first user to start editing the document.
* If users leave the editing session while others are still editing, |wac| will call other lock-related
  operations, such as :ref:`Unlock` or :ref:`RefreshLock`, using the access tokens of other users that are still
  editing.

Scenario 1
~~~~~~~~~~

#. User A continues editing the document.
#. User B makes no changes.

..  csv-table:: Co-authoring scenario 1
    :file: ../tables/coauth_scenario_1.csv
    :header-rows: 1


Scenario 2
~~~~~~~~~~

#. User A continues editing the document.
#. User B also edits the document.

..  csv-table:: Co-authoring scenario 2
    :file: ../tables/coauth_scenario_2.csv
    :header-rows: 1


Scenario 3
~~~~~~~~~~

#. User A leaves the editing session by closing the |wac| application or navigating away.
#. User B continues editing the document.
#. User C tries to edit the same document.
#. |wac| calls :ref:`CheckFileInfo` using User C's access token to verify the user has edit permissions.

..  csv-table:: Co-authoring scenario 3
    :file: ../tables/coauth_scenario_3.csv
    :header-rows: 1


Scenario 4
~~~~~~~~~~

#. User A continues editing the document.
#. User B is in the session but is not editing the document.
#. While the editing session is in progress, User B's permissions to edit the document are removed.

..  csv-table:: Co-authoring scenario 4
    :file: ../tables/coauth_scenario_4.csv
    :header-rows: 1


Scenario 5
~~~~~~~~~~

#. User A continues editing the document.
#. User B also continues editing the document.
#. While the editing session is in progress, User B's permissions to edit the document are removed.

..  csv-table:: Co-authoring scenario 5
    :file: ../tables/coauth_scenario_5.csv
    :header-rows: 1


Scenario 6
~~~~~~~~~~

#. User A continues editing the document.
#. User B also continues editing the document.
#. While the editing session is in progress, User A's permissions to edit the document are removed.

..  csv-table:: Co-authoring scenario 6
    :file: ../tables/coauth_scenario_6.csv
    :header-rows: 1

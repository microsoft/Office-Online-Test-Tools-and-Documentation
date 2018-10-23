
..  _trello board:

Using Trello to manage the launch process
=========================================

To manage the overall launch process, including testing and validation, Microsoft uses a service called
`Trello <https://trello.com>`_. Once you have started the launch process, Microsoft will create a dedicated
Trello board to track issues and provide a common communication channel between your team and Microsoft.
The board will be pre-populated with cards tracking various questions about your WOPI implementation as well as
discussion cards to determine launch dates, etc.

If you are new to Trello, you can learn more about it at https://trello.com/guide/.

..  important::

    You should use the Trello board to communicate with Microsoft throughout the launch process. This will ensure
    that all Office Online team members are aware of the communications, and it provides a straightforward way to
    isolate conversations about specific issues.


Adding people to the board
--------------------------

You can invite other relevant people to the board as needed; only people from the Office Online team and people you
explicitly invite will have access to see or edit the content of the board.

We recommend that you add relevant engineers from your team to the board as well, since many of the discussions will
be engineering-focused. You might add designers or business people to the board as well; simply add whomever makes
sense for your team.

..  seealso::

    Learn how to add more people to your board at http://help.trello.com/article/717-adding-people-to-a-board.


Board structure
---------------

..  figure:: /images/trello_initial.png
    :alt: An example partner launch board in Trello.

    Example partner launch board in Trello

The board structure is fairly basic. There are seven lists, and you can move cards between the lists as needed. The
lists serve two main purposes. First, they keep issues organized at a high level, so it is easy to see what issues
are being investigated and what has been resolved, etc. In addition, the lists provide a simple way to configure
Trello's notifications such that both you and Microsoft are aware of what requires attention.

#.  **Reference**: This list contains cards that have reference information, such as current test accounts for
    the :ref:`business user flow <Business editing>` or known issues that may affect your testing.

#.  **New: Microsoft**: This list contains new cards that Microsoft needs to be aware of. You should add cards to this
    list to ensure it is brought to Microsoft's attention. Any card on this list represents something that
    Microsoft has not yet acknowledged or taken action on. Once Microsoft is aware of the card, it will be moved to
    another list like *Under Discussion/Investigation* for action.

#.  **New: Partner**: This list contains new cards that you, the Office Online partner, need to take action on.
    Initially, this list will contain a number of cards tracking various questions about your WOPI implementation or
    launch plans. As testing is done, Microsoft will create new cards to track implementation issues or additional
    questions that arise during testing. Like the *New: Microsoft* list, cards should be moved from this list once
    they are acknowledged.

#.  **Under Discussion/Investigation**: This list contains cards that are being discussed or investigated, either by
    you or Microsoft. Once a resolution is reached on the particular card, it should be moved to the
    *Fix In Progress* or *Re-verify* list.

#.  **Fix In Progress**: This list contains cards that are in the process of being addressed. These cards may
    represent a bug fix by you or Microsoft, or a settings change that is in progress, etc. Once the issue is
    addressed, the card should be moved to the *Re-verify* list.

#.  **Re-verify**: This list contains cards that are ready to be verified. For example, you may have answered a
    question about your WOPI implementation, at which point you can move the card to the *Re-verify* list. Once it has
    been verified, it can be moved to the *Resolved* list. If there are follow-up questions or further discussion is
    needed, the card might be moved back to the *Under Discussion/Investigation* list.

#.  **Resolved**: This list contains cards that are resolved, either because the issue has been fixed and verified,
    or a question has been answered and verified.


Card flow
~~~~~~~~~

With the exception of the left-most *Reference* list, the lists represent a process flow that issues will go
through as they are discussed and addressed. Cards will typically move from left to right, starting at either the
*New: Microsoft* or *New: Partner* lists, then moving right through the other relevant lists. In some cases, a card
might be moved back to a previous list. For example, if a card in the *Re-verify* list is found to not be resolved,
it may be moved back to the *Under Discussion/Investigation* or *Fix In Progress* lists.

..  tip::

    You should always create new cards in either the *New: Microsoft* or *New: Partner* lists. That ensures that people
    are notified about the new cards. See :ref:`trello notifications` for more information.


Labels
~~~~~~

..  figure:: /images/trello_labels.png
    :alt: The default labels configured for partner boards

    Default labels configured for partner boards

Labels are used to help flag particular cards for easy filtering. You can filter the board based on the label colors,
so it's easy to focus on items that need to resolved before you can be enabled in the production environment, for
example, by filtering to just the red "Production Blocker" cards.

Four labels are defined initially:

#.  Production Blocker
#.  Implementation Question
#.  Launch Planning
#.  Resources

You should feel empowered to add new labels to your board if you wish.


..  _trello notifications:

Notifications
-------------

Trello supports a wide variety of
`notification options <http://help.trello.com/article/793-receiving-trello-notifications>`_. You can be notified of
activity on your board by subscribing to individual cards, lists, or even the whole board. You'll receive
notifications when things that you're subscribed to are changed. You can configure how these notifications behave in
your Trello settings.

..  tip::

    You can subscribe to an individual card yourself, but you can also be 'added' to a card by someone else. When you
    are added to a card you are automatically subscribed to it. See
    http://help.trello.com/article/717-adding-people-to-a-board for more information.


..  seealso::

    Learn how to subscribe to items in Trello at
    http://help.trello.com/article/799-subscribing-to-cards-lists-and-boards.


Recommended configuration and best practices
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

By default, Office Online team members will subscribe to the *New: Microsoft* list. This ensures that they will be
notified any time a card is added or moved to that list. We recommend that your team members similarly subscribe to the
*New: Partner* list for the same reason.

In addition, we recommend the following:

* When you create a new card, subscribe to it so you are notified when it is updated.
* The board is pre-populated with cards. Consider subscribing to the cards that you'd like to explicitly track.
* You might also choose to subscribe to the entire board, though this can result in 'notification overload,' especially
  early on in the validation process. However, it can be useful after the board activity has lessened to ensure you
  don't miss any changes.

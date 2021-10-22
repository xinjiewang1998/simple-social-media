# [Team Name] Report

The following is a report template to help your team successfully provide all the details necessary for your report in a structured and organised manner. Please give a straightforward and concise report that best demonstrates your project. Note that a good report will give a better impression of your project to the reviewers.

*Here are some tips to write a good report:*

* *Try to summarise and list the `bullet points` of your project as much as possible rather than give long, tedious paragraphs that mix up everything together.*

* *Try to create `diagrams` instead of text descriptions, which are more straightforward and explanatory.*

* *Try to make your report `well structured`, which is easier for the reviewers to capture the necessary information.*

*We give instructions enclosed in square brackets [...] and examples for each sections to demonstrate what are expected for your project report.*

*Please remove the instructions or examples in `italic` in your final report.*

## Table of Contents

1. [Team Members and Roles](#team-members-and-roles)
2. [Conflict Resolution Protocol](#conflict-resolution-protocol)
2. [Application Description](#application-description)
3. [Application UML](#application-uml)
3. [Application Design and Decisions](#application-design-and-decisions)
4. [Summary of Known Errors and Bugs](#summary-of-known-errors-and-bugs)
5. [Testing Summary](#testing-summary)
6. [Implemented Features](#implemented-features)
7. [Team Meetings](#team-meetings)

## Team Members and Roles

| UID | Name | Role |
| :--- | :----: | ---: |
| u7368778 | Lingchao Zhang | developer |
| u7201825 | Xinjie Wang | developer |
| u7224266 | Xiang Lu | developer |
| u7323660 | Jiaan Guo | developer |

## Conflict Resolution Protocol

1. *Listen to the counterpart.*
2. *Explain to other members.*
3. *Argue with clear text.*
4. *Establish a constructive dialogue.*
5. *Vote for the decision.*

## Application Description

*DrawAndMatch is a social media application specifically designed for artists and people who like painting, It provides painting and matching functions to promote user communication and make friends. For example, users can draw pictures, match friends through the similarity of pictures, send messages directly between friends, like and favorite posts, have a user-friendly UI, and so on. *

**Application Use Cases and or Examples**

*Targets Users: Painters and people who like painting*

* *Users can draw a picture they like.*
* *Users can match with similar person base on the image*

*Targets Users: People watching the Forum*

* *Users can search and view others posts.*
* *Users can chat to the author of post.*
* *Users can interact with posts, such like and favorites.*

## Application UML

![ClassDiagramExample](/Group%20Assignment/images/ClassDiagramExample.png)
*[Replace the above with a class diagram. You can look at how we have linked an image here as an example of how you can do it too.]*

Chat.java
ChatBox.java
ExtractEngine.java
FriendAdapter.java - FriendHolder.class
MessageActivity.java
MessageAdaptor.java - MessageHolder.class
User.java

AllPostPage.java - MyTask.class PostsConcreteCollection.class - PCIterator.class
eachpost.java - LruImageCache.class
PostAdaptor.java - LruImageCache.class (Least recently used) PostHolder.class
Iterator.java
IterableCollection.java

## Application Design and Decisions

*Please give clear and concise descriptions for each subsections of this part. It would be better to list all the concrete items for each subsection and give no more than `5` concise, crucial reasons of your design. Here is an example for the subsection `Data Structures`:*

*I used the following data structures in my project:*

1. *BTreeMap*
   * *Objective: It is used for storing xxxx for xxx feature.*

   * *Locations: line xxx in XXX.java, ..., etc.*

   * *Reasons:*

      * *It is more efficient than Arraylist for insertion with a time complexity O(1)*

      * *We don't need to access the item by index for this feature*
2. *HashSet*
   * *Objective: It is used for storing xxxx for xxx feature.*

   * *Locations: line xxx in XXX.java, ..., etc.*

   * *Reasons:*

      * *It is more efficient than Arraylist for insertion with a time complexity O(1)*

      * *We don't need to access the item by index for this feature*
3. *ArrayList*
   * *Objective: It is used for storing xxxx for xxx feature.*

   * *Locations: line xxx in XXX.java, ..., etc.*

   * *Reasons:*

      * *It is more efficient than Arraylist for insertion with a time complexity O(1)*

      * *We don't need to access the item by index for this feature*
4. *Hashmap [string] object -/- firebase*
   * *Objective: It is used for storing xxxx for xxx feature.*

   * *Locations: line xxx in XXX.java, ..., etc.*

   * *Reasons:*

      * *It is more efficient than Arraylist for insertion with a time complexity O(1)*

      * *We don't need to access the item by index for this feature*

   
**Data Structures**

*[What data structures did your team utilise? Where and why?]*

**Design Patterns**

**Iterator**
**Singleton**

*[What design patterns did your team utilise? Where and why?]*

**Grammars**

*Search Engine*
<br> *Production Rules* <br>
\<Non-Terminal> ::= \<some output>
<br>
\<Non-Terminal> ::= \<some output>


*[How do you design the grammar? What are the advantages of your designs?]*

*If there are several grammars, list them all under this section and what they relate to.*

**Tokenizer and Parsers**

*[Where do you use tokenisers and parsers? How are they built? What are the advantages of the designs?]*

**Surprise Item**

//

*[If you implement the surprise item, explain how your solution addresses the surprise task. What decisions do your team make in addressing the problem?]*

**Other**

//

*[What other design decisions have you made which you feel are relevant? Feel free to separate these into their own subheadings.]*

## Summary of Known Errors and Bugs

*[Where are the known errors and bugs? What consequences might they lead to?]*

*Here is an example:*

1. *Bug 1:*

- *A space bar (' ') in the sign in email will crash the application.*
- ...

2. *Bug 2:*
3. ...

*List all the known errors and bugs here. If we find bugs/errors that your team do not know of, it shows that your testing is not through.*

## Testing Summary

*Number of test cases: 10*

*Code coverage: StartActivity, MainActivity, MessageActivity, ChatBoxActivity, PostActivity, FavoritePostActivity, EachPostActivity and PaintActivity*

*Types of tests created: displayUITest, positionUITest, buttonTest, intentTest, searchViewTest and recycleViewTest*

[StartActivityTest](/Group%20Assignment/images/test1.png)
[StartActivityLoginTest](/Group%20Assignment/images/test2.png)
[MainActivityTest](/Group%20Assignment/images/test4.png)
[PostActivityTest](/Group%20Assignment/images/test3.png)
[PaintTest](/Group%20Assignment/images/test5.png)
[MessageTest](/Group%20Assignment/images/test6.png)

## Implemented Features

*UI Design and Testing*

2. *UI tests using espresso or similar. Please note that your tests must be of reasonable quality. (For UI testing, you may use something such as espresso) a. Espresso is not covered in lectures/labs, but is a simple framework to write Android UI tests. (hard) *

*Greater Data Usage, Handling and Sophistication*

2. *User profile activity containing a media file (image, animation (e.g. gif), video). (easy)*

*User Interactivity*

1. *The ability to micro-interact with 'posts' (e.g. like, report, etc.) [stored in-memory].(easy)*

*Peer to Peer Messaging*

1. *Provide users with the ability to message each other directly. (hard)*

*Firebase Integration*

1. *Use Firebase to implement user Authentication/Authorisation. (easy)*
2. *Use Firebase to persist all data used in your app (this item replace the requirement to retrieve data from a local file) (medium)*

*New feature*

1. *Paint and matching similar graphs. (hard)*

## Team Meetings

- *[Team Meeting 1](/Group%20Assignment/MeetingMinuteWeek6.md)*
- *[Team Meeting 2](/Group%20Assignment/MeetingMinutesWeek7.md)*
- *[Team Meeting 3](/Group%20Assignment/MettingMinutesWeek8.md)*
- *[Team Meeting 4](/Group%20Assignment/MeetingMinutesWeek9.md)*

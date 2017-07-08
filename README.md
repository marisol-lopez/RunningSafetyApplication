# Running Safety App | Product Plan | Marisol Lopez

## Learning Goals
- Become familiar with Android App Development
  - Specifically with Java language and Android IDE
 - Gain knowledge and experience with app driven text notifications

## Problem Statement

Running safety app that will allow you do document where you are running, when you expect to be finished, what you are wearing, and who you would like designated as a safety contact. The app will send a text message with this information to your designated safety contact when you begin a run, with the app, and it will send another text message if the user does not end the run, with the app, before their pre-estimated end time.

## Market Research

- Glympse
  - Shortcomings of the competition:
    - Continuous GPS location sharing
    - No MapMyRun API integration
  - My product is different because this app will alert a user's designated safety contact with the specific information the user needs to provide to the app. While Glymse, is very similar, and perhaps a more sophisticated version of this app, it acts primarily as a gps sharing location app, which can drain the user's battery life, and unnecessarily share their gps location when they might not want to.

## Target Audience

My app targets runners who are characterized by running outside, which can sometimes be unsafe.

## Trello Board

[Trello](https://trello.com/b/1UI4O9q1)

## Technologies

- Back-end Technology
	- Java
- Front-end Technology
	- Java
	- Android IDE
- Infrastructure
	- Android Store

## Wireframes

| home page | designated contact page | end run page  |
| ------ | ------ | ----- |
|  <img src="https://image.ibb.co/bOXvTQ/homepage.png" alt="homepage" width= "300px"/>  |  <img src="https://image.ibb.co/dWneoQ/Screen_Shot_2017_06_18_at_6_31_17_PM.png" alt="contactpage" width= "300px"/>  |   <img src="https://preview.ibb.co/cHd28Q/end_run.png" alt="contactpage" width= "300px"/>  |

## MVP Feature Set

1.  Text message alert(s)
	- Alert will be sent to user's designated contact when they begin run.
	- Alert will be send to user's designated contact if do not end the run by estimated end time.
2.  Storing information for run.
	- Store a selfie with that specific run's outfit.
	- Set estimated end time.
	- Store route for run.
	- Set designated contact for each run.
3. Start and End buttons.
	- Start Run button will trigger alert that user has begun a run.
	- End Run button will end run, thereby avoiding emergency alert text.
4. Set designated contacts
	- Add information for person you would like contacted when you begin or fail to end a run.

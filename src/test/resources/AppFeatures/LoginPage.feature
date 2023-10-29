#Author: your.email@your.domain.com

Feature: Chat Monitoring

	@smoke
  Scenario Outline: Verifying the O2 Chat Monitoring System
    #Given User Launch Browser
    Given User Launch Browser and opens URL "https://www.o2online.de/chat-auswahl"
    When User clicks on link O2 Service live chat
    Then Update the Url "https://www.o2online.de/chat-ui/chat.html?cg=bk&sn=CCT_C_SUPPORT"
    
 		And click LoginButton
    And User enters username as "xxx" and password as "xxx"
    Then Verify If System is Up
    #And Click on Login   
    
    Then start the chat <ChatMsg>
    Then Verify if Agent is Online
    Then Continue Chat <ChatMsgTest1> <ChatMsgTest2> <ChatMsgTest3>
    And Get Chat History
    Then End the Chat
    And close the browser
    
     Examples:
    | ChatMsg | ChatMsgTest1 | ChatMsgTest2 | ChatMsgTest3 |
    | "Hi" | "I am a test user" | "I need support Sim" | "Thank you so much" |

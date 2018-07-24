Feature: This is test about galenframework sample page layout test

  Scenario: Verification of Galen Test App page in desktopBrowser
    Given I launch desktop browser with url http://testapp.galenframework.com
    When I load gspec file welcomePage.gspec
    Then Test Info Basic gspecTest with desktop browser and Report Info desktop type is updated

  Scenario: Verification of Galen Test App page in Mobile Browser
    Given I launch mobile browser with url http://testapp.galenframework.com
    When I load gspec file welcomePage.gspec
    Then Test Info Basic gspecTest with mobile browser and Report Info mobile browser type is updated

  Scenario: Verification of Google logo comparison
    Given I launch desktop browser with url https://www.google.com/
    When I load gspec file imagecompare.gspec
    Then Test Info image comparison test in Desktop and Report Info Desktop layout is updated

Feature: send file with wrong content type end-point

  Background:
    * url "http://localhost:8081"
    * configure lowerCaseResponseHeaders = true

  Scenario: upload file
    Given path '/'
    And header Content-Type = 'video/mp4'
    And multipart file file =  { read: '../data/micro.mp4', filename: 'micro.mp4', contentType: 'video/mp4' }
    When method post
    Then status 400
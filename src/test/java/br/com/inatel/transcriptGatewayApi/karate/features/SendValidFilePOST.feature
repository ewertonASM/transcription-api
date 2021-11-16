Feature: file upload end-point

  Background:
    * url "http://localhost:8081"
    * configure lowerCaseResponseHeaders = true

  Scenario: upload file
    Given path '/'
    And header Content-Type = 'multipart/form-data'
    And multipart file file =  { read: '../data/micro.mp4', filename: 'micro.mp4', contentType: 'video/mp4' }
    When method post
    Then status 201
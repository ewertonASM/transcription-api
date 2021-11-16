Feature: Send invalid type file

  Background:
    * url "http://localhost:8081"
    * configure lowerCaseResponseHeaders = true

  Scenario: upload file
    Given path '/'
    And header Content-Type = 'multipart/form-data'
    And multipart file file =  { read: '../data/imagem.jpeg', filename: 'imagem.jpeg', contentType: 'video/mp4' }
    When method post
    Then status 400
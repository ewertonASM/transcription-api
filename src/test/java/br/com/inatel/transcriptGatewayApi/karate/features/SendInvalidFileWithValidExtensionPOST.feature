Feature: send invalid file with valid extension end-point

  Background:
    * url "http://localhost:8081"
    * configure lowerCaseResponseHeaders = true

  Scenario: upload file
    Given path '/'
    And header Content-Type = 'multipart/form-data'
    And multipart file file =  { read: '../data/imagem.mp4', filename: 'imagem.mp4', contentType: 'video/mp4' }
    When method post
    Then status 400
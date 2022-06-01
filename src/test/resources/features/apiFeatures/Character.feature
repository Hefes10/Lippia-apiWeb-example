@Regresion @Character
Feature: BFF - Character por ID
  Como Usuario
  QUIERO consultar un character por id
  PARA conocer todos los datos en relacion al character consultado

  Background:
    Given deshabilito la opcion de screenshot

  Scenario Outline: Consulta de character por ID - Resultado exitoso
    Given el id '<id>' de un character
    When realizo una peticion 'GET' al endpoint de la entidad 'CHARACTER' con el jsonName 'character/rq_consultaPorId'
    Then se obtuvo el status code 200
    And la respuesta es la esperada para la consulta de character por id '<id>'
    Examples:
      | id   |
      | 1    |
      | 2    |
      | 3    |
      | 4    |
      | 5    |
      | 6000 |
      | 7    |
      | 8    |
      | 9    |
      | 10   |
      | 11   |
      | 12   |
      | 13   |
      | 14   |
      | 15   |
      | 16   |
      | 17   |
      | 18   |
      | 19   |
      | 20   |
      | 21   |
      | 22   |
      | 23   |
      | 24   |




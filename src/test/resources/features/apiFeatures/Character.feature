@Regresion @Character
Feature: BFF - Character por ID
  Como Usuario
  QUIERO consultar un character por id
  PARA conocer todos los datos en relacion al character consultado

  Scenario Outline: Consulta de character por ID - Resultado exitoso
    Given el id '<id>' de un character
    When realizo una peticion 'GET' al endpoint de la entidad 'CHARACTER' con el jsonName 'character/rq_consultaPorId'
    Then se obtuvo el status code 200
    Examples:
      | id  |
      | 02  |
      | 03  |
      | 04  |
      | asd |



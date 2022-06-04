@Regresion @Web @Character
Feature: Web - Character por nombre
  Como Usuario
  QUIERO consultar un character por nombre
  PARA conocer todos los datos en relacion al character consultado

  @Smoke
  Scenario Outline: Consulta de character por nombre - Resultado exitoso
    Given que me encuentro en la pantalla de home
    When hago click en en el personaje <personaje>
    Then aparece la informacion del personaje <personaje> <id>
    Examples:
      | personaje                 | id |
      | Rick Sanchez              | 1  |
      | Morty Smith               | 2  |
      | Summer Smith              | 3  |
      | Beth Smith                | 4  |
      | Jerry Smith               | 5  |
      | Abadango Cluster Princess | 6  |
      | Abradolf Lincler          | 7  |
      | Adjudicator Rick          | 8  |
      | Agency Director           | 9  |
      | Alan Rails                | 10 |
      | Albert Einstein           | 11 |
      | Alexander                 | 12 |
      | Alien Googah              | 13 |
      | Alien Morty               | 14 |
      | Alien Rick                | 15 |
      | Amish Cyborg              | 16 |
      | Annie                     | 17 |
      | Antenna Morty             | 18 |
      | Antenna Rick              | 19 |
      | Ants in my Eyes Johnson   | 20 |

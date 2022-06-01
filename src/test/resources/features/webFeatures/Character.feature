@Regresion @Web @Character
Feature: Web - Character por nombre
  Como Usuario
  QUIERO consultar un character por nombre
  PARA conocer todos los datos en relacion al character consultado

  @Smoke
  Scenario Outline: Consulta de character por nombre - Resultado exitoso
    Given que me encuentro en la pantalla de home
    When hago click en en el personaje <personaje>
    Then aparece la informacion del personaje <personaje>
    Examples:
      | personaje                   |
      | "Rick Sanchez"              |
      | "Morty Smith"               |
      | "Summer Smith"              |
      | "Beth Smith"                |
      | "Jerry Smith"               |
      | "Abadango Cluster Princess" |
      | "Abradolf Lincler"          |
      | "Adjudicator Rick"          |
      | "Agency Director"           |
      | "Alan Rails"                |
      | "Albert Einstein"           |
      | "Alexander"                 |
      | "Alien Googah"              |
      | "Alien Morty"               |
      | "Alien Rick"                |
      | "Amish Cyborg"              |
      | "Annie"                     |
      | "Antenna Morty"             |
      | "Antenna Rick"              |
      | 'Ants in my Eyes Johnson'   |

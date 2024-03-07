# Documentación de ProyectoDI4

Esta es la documentación técnica detallada de la aplicación ProyectoDI4 que consiste en un login con acceso a una API.

## Funcionalidades

- **Autenticación con AWS a través de Cognito con Auth:** Un usuario puede registrarse e iniciar sesión para poder acceder a la aplicación a través de su correo y contraseña. Además, puede hacer sign out para acceder con otra cuenta y también puede recuperar su contraseña en caso de que se le haya olvidado. Al registrarse se deberá validar con un código MFA que le llegará al correo, lo que da mucha seguridad a la aplicación.

- **Cambio de idioma instantáneo:** Con un menú de idiomas, podemos seleccionar entre español, inglés, francés, alemán y chino para ver la aplicación.

- **Acceso a API:** Se accede a una API a través de Retrofit donde se recogen los datos y se puede enviar una pregunta con parámetros, aunque no acaba de funcionar.

## Instalación

Debe seguir estos pasos para instalar la aplicación:

1. Descargue el archivo APK.
2. Habilite la opción "Instalar aplicaciones de fuentes desconocidas" en la configuración del dispositivo.
3. Instale la aplicación.

## Uso

Para utilizar la aplicación, siga estos pasos:

1. Abra la aplicación.
2. Si quiere cambiar el idioma, pulse en el menú de arriba donde pone Español y seleccione el idioma que quiera.
3. Para hacer LogIn, introduzca su correo y contraseña si ya tiene una cuenta verificada.
4. Para registrarse, pulse el texto "Registrarse" y rellene los campos.
5. Una vez rellenados los datos, pulse el botón "Registrarse". Le saldrá una pantalla para que introduzca el código que le habrá llegado a su correo, pudiendo validar su registro. Una vez validado su registro, ya podrá hacer LogIn.
6. Si ha olvidado su contraseña, pulse en el texto "He olvidado mi contraseña". Este le llevará a otra pantalla donde deberá introducir su correo y pulsar el botón "Obtener código" para que le llegue un código al correo y podrá poner una nueva contraseña introduciendo ambos valores en los campos y pulsando el botón "Acceder".
7. Si ya ha iniciado sesión previamente y ha salido de la aplicación, deberá pulsar el botón "Desconectar" para poder quitar esa cuenta e introducir una nueva.
8. Una vez haya hecho LogIn y haya accedido correctamente, le saldrá el texto agregado desde la API y podrá enviar los parámetros previamente establecidos pulsando el botón "Enviar datos".

### Control del usuario con advertencias

En caso de incumplirse algún campo, saldrá un mensaje avisando de que se está incumpliendo para que el usuario pueda corregirlo:

- Se deben rellenar todos los campos. Si alguno se queda vacío, saldrá un mensaje avisando: "Debes rellenar todos los campos".
- El correo debe contener un símbolo "@" y tener una longitud mínima de 30 caracteres. Si no se cumple, saldrá un mensaje avisando: "El correo debe contener un @ y tener menos de 30 caracteres".
- La contraseña debe tener una longitud mayor a 9 caracteres y menor a 30. Si no se cumple, saldrá un mensaje avisando: "La contraseña debe tener entre 9 y 30 caracteres".
- La contraseña debe tener un símbolo ("@", "#", "$", "%", o "&"). Si no se cumple, saldrá un mensaje avisando: "La contraseña debe tener una mayúscula, una minúscula y un número".
- Si el correo no se encuentra a la hora de hacer LogIn, saldrá un mensaje advirtiendo: "Error en el correo".
- Si el código de validación MFA tiene una longitud desigual a 6 caracteres o no es numérico, saldrá un mensaje advirtiendo: "El código debe tener una longitud igual a 6 números".

## Test
### Tests unitarios

# Tests Unitarios para la pantalla de Registro (Register Screen)

Las siguientes pruebas unitarias se han diseñado utilizando el framework JUnit para evaluar el comportamiento de Register Screen en diversos escenarios. Estas pruebas abordan casos específicos relacionados con la validación de datos al intentar registrar un nuevo usuario. Para ello se utilizará también la clase Register Screen ViewModel, ya que la funcionalidad de dicha pantalla se encuentra en esta clase.

## Registro de Usuario

### Inicialización del ViewModel

Inicializamos un objeto "Register Screen ViewModel" antes de realizar las pruebas, para que pueda ser utilizado en todos los casos de prueba.

### Pruebas Unitarias

#### Todos los campos vacíos ("camposVacios")

En este test, se verifica el comportamiento del método "registrarUsuario" cuando todos los campos están vacíos. Se espera que el método devuelva el código 1.

#### Email demasiado largo ("emailLargo")

Este test evalúa la longitud del correo electrónico, concretamente cuando tiene más de 30 caracteres. Se espera que el método "registrarUsuario" devuelva el código 2.

#### Email sin @ ("emailSinArroba")

En esta prueba, se verifica el comportamiento del método "registrarUsuario" cuando la dirección de correo no contiene el símbolo "@". Se espera obtener el código 3.

#### Contraseña corta ("passwordCorta")

En este caso, se comprueba la longitud de la contraseña cuando es menor a 9 caracteres. Se espera que el método "registrarUsuario" devuelva el código 4.

#### Contraseña demasiado larga ("passwordLarga")

En este test se verifica el comportamiento del método "registrarUsuario" cuando la contraseña tiene más de 30 caracteres. Se espera obtener el código 5.

#### Contraseña sin @ ("passwordSinArroba")

Se evalúa el caso en el que la contraseña no contiene el símbolo "@". Se espera que el método "registrarUsuario" devuelva el código 5.

#### Contraseña sin mayúsculas ("passwordSinMayus")

Este test verifica el comportamiento del método "registrarUsuario" cuando la contraseña no contiene letras mayúsculas. Se espera que el método devuelva el código 6.

#### Contraseña sin minúsculas ("passwordSinMinus")

Se comprueba el comportamiento del método "registrarUsuario" cuando la contraseña no contiene letras minúsculas. Se espera que el método devuelva el código 6.

#### Contraseña y Confirmación de Contraseña son diferentes ("noPasswordEquals")

En este test, se verifica el caso en el que la contraseña y la confirmación de contraseña introducidas no son iguales. Se espera que el método "registrarUsuario" devuelva el código 7.

#### Correo y Usuario diferentes ("noEmailUserEquals")

Este test comprueba el comportamiento del método "registrarUsuario" cuando el email y el usuario no son iguales. Se espera que el método devuelva el código 8.

#### Registro Exitoso ("registroExitoso")

La última prueba verifica el caso en el que todos los campos son correctos. Se espera que el método "registrarUsuario" devuelva el código 9.

## Conclusión

Estas pruebas unitarias evalúan diversos casos de uso para el registro de usuarios en la aplicación. El objetivo es garantizar que la lógica de validación implementada en "Register Screen ViewModel" funcione correctamente y proporcione el comportamiento esperado en diferentes situaciones.

# Tests Unitarios para la pantalla principal (LogIn Screen)

El conjunto de pruebas unitarias para testear la pantalla principal "LogIn Screen" se ha diseñado utilizando el framework JUnit. En ellas se evalúa el comportamiento de la clase "Login Screen ViewModel" en diversos escenarios, ya que la funcionalidad de LogIn se encuentra realmente en esta clase. Estas pruebas abordan casos específicos relacionados con la validación de datos al intentar realizar el inicio de sesión de un usuario.

## Inicio de Sesión

### Inicialización del ViewModel

Antes de realizar las pruebas, se inicializa un objeto "Login Screen ViewModel" para ser utilizado en todos los casos de prueba.

### Pruebas Unitarias

#### Campos Vacíos ("camposVacios")

Este test comprueba el comportamiento del método "loginScreen" cuando los campos correo y contraseña están vacíos. Se espera que el método devuelva el código 1.

#### Email sin @ ("emailSinArroba")

Esta prueba evalúa el caso en el que el correo electrónico no contiene el símbolo "@". Se espera que el método "loginUsuario" devuelva el código 2.

#### Email demasiado largo ("emailLargo")

Este test verifica el caso en el que la dirección de correo electrónico tiene más de 30 caracteres. Se espera obtener el código 2.

#### Contraseña corta ("passwordCorta")

En este caso se evalúa que la contraseña tenga menos de 9 caracteres. Se espera que el método "loginUsuario" devuelva el código 3.

#### Contraseña demasiado larga ("passwordLarga")

Este test verifica el comportamiento del método "loginUsuario" cuando la contraseña tiene más de 30 caracteres. Se espera que el método devuelva el código 3.

#### Contraseña sin símbolo ("passwordSinSimbolo")

Se evalúa el caso en el que la contraseña no contiene ningún símbolo. Se espera que el método devuelva el código 4.

#### Contraseña sin mayúsculas ("passwordSinMayus")

Esta prueba verifica el comportamiento del método "loginUsuario" cuando la contraseña no contiene letras mayúsculas. Se espera obtener el código 5.

#### Contraseña sin minúsculas ("passwordSinMinus")

Esta prueba verifica el comportamiento del método "loginUsuario" cuando la contraseña no contiene letras minúsculas. Se espera obtener el código 5.

#### Contraseña sin números ("passwordSinNumeros")

Esta prueba evalúa el caso en el que la contraseña no contiene ningún número. Se espera que el método "loginUsuario" devuelva 5.

#### Campos Correctos ("camposCorrectos")

Por último, este test evalúa el caso en el que el correo y la contraseña tienen el formato correcto. Se espera que el método devuelva el código 6.

## Conclusión

Estas pruebas unitarias abarcan diversos casos de uso para el inicio de sesión de usuarios en la aplicación. El objetivo es garantizar que la lógica de validación implementada en "Login Screen ViewModel" funcione correctamente y proporcione el comportamiento esperado en diferentes situaciones.

# Tests Unitarios para la pantalla de verificación (MFA Screen)

Este conjunto de pruebas unitarias se ha diseñado utilizando el framework JUnit para evaluar el comportamiento de la clase "MFA Screen ViewModel" en diversos escenarios. Estas pruebas abordan casos específicos relacionados con la validación de códigos en el contexto de la autenticación de doble factor (MFA).

## Inicialización del ViewModel

Antes de realizar las pruebas, se inicializa un objeto "MFA Screen ViewModel" para ser

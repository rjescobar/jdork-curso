# jdork-curso
Script para obtener resultados de búsqueda mediante la plataforma CustomSearch de Google.
Actualmente las búsquedas se realizan en el sitio web [LinkeIn](https://linkedin.com)

## Configuración

1. Crear archivo ```config.json``` con los valores correspondientes (ver https://developers.google.com/custom-search/v1/overview)
```
{
  "key" : "API_KEY",
  "cx": "CX_VALUE"
}
```
2. Ejecutar el archivo mediante línea de comandos
```
java -jar jdork.jar <objetivo>
```


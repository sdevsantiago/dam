# Android Studio
- Descargar el archivo comprimido (`tar.gz`).
- Descomprimir el archivo en `/usr/local` (usuario) o `/opt` (todos los usuarios), preferiblemente la segunda, con el comando `tar -xvzf <archivo.tar.gz>`.
- Acceder al directorio `/usr/local/android-studio/bin` y ejecutar el binario `studio` con el comando `./studio`.
- Acceder al directorio `/usr/share/applications` y generar el acceso acceso directo (archivo `.desktop`) para ejecutarlo desde el launcher con el comando `dex -c /usr/local/android-studio/bin/studio`.

```desktop
[Desktop Entry]
Name=Android Studio
Exec=/opt/android-studio/bin/studio
Icon=/opt/android-studio/bin/studio.svg
Type=Application
Categories=TextEditor;Development;IDE;
Keywords=android
```

Opcional:
- Instalar dependencias:
	- Ubuntu: qemu-kvm libvirt-daemon-system libvirt-clients bridge-utils
	- Arch: 

[Guía oficial](https://developer.android.com/studio/install?hl=es-419)
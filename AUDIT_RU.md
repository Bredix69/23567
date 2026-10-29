# LastWorld — заметки по сборке

Проект приведён к структуре ForgeGradle, как в предоставленном SculkTitan:
- версии вынесены в `gradle.properties`;
- `settings.gradle` использует Forge Maven и toolchain resolver;
- `build.gradle` использует переменные `mod_id`, `mod_version`, `minecraft_version`, `forge_version`;
- добавлен GitHub Actions workflow для попытки автоматической сборки.

## Проверки
В текущей среде не установлен Gradle, а скачивание зависимостей не выполнялось, поэтому JAR здесь не собран и компиляция не подтверждена. GitHub Actions попробует выполнить сборку при загрузке репозитория. Если сборка упадёт, открой вкладку Actions и смотри первый красный шаг/ошибку компиляции.

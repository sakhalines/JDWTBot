# JDWTBot - Java Discord WarThunder Bot.

Discord бот, созданный на основе zekroBot, с добавлением в него функций получения статистики игроков с сайта thunderskill.com,
а также для мониторинга или получения по запросу информации об обновлении игры и новостей c официального сайта, с дальнейшей публикацией её в специальном канале чата Discord.

# Установка и настройка:

Открываем командную строку и запускаем бота:
```bat
java -jar JDWTBot.jar
```
Нажмиам CTRL+ C чтобы завершить работу бота

При первом запуске бот создаст файл настроек **SETTINGS.txt**. Открываем его для редактирования.


**Для начала нужно получить API token.**

Для этого переходим на страницу https://discordapp.com/developers/applications/me/
Нажимаем **"New App"** и вводим имя приложение, которое в дальнейшем и будет являться именем бота.
Далее в блоке **"App Details"** напротив **"Client Secret:"** нажимаем  **"click to reveal"** и записываем его как значение **TOKEN** в файле **SETTINGS.txt**.


**После этого узнаём свой DiscordID.**

Для этого переходим в настройки дискорда и выбираем **"Внешний вид"**, и в блоке **"Расширенные"** включаем **"Режим разработчика"**.  Закрываем настройки.  
Нажимаем правой кн. по своему нику и выбираем **"Копировать ID"** и записываем его как значение **"BOT_OWNER_ID**" в файле **"SETTINGS.txt"**.

**Первичная настройка завершена.**

В файле **"SETTINGS.txt"** в блоке **"PERMISSION SETTINGS"** укажите ваши группы сервера, для настройки полномочий на выполнение команд.

Всего их три уровня, например:
```ini 
# PERMISSION SETTINGS #
    # команды доступные всем
        MEMBER_PERMISSION_ROLES = "server admin, moder, admin, member, member+"
    # команды доступные модераторам и админам
        PERMISSION_ROLES = "server admin, moder, admin"
    # команды доступные только админам
        FULL_PERMISSION_ROLES = "server admin"
```
Остальные параметры можно оставить по умолчанию. Комментарии к ним есть в самом файле настроек, изменяйте как угодно.

Можно запускать.
```bat
java -jar JDWTBot.jar
```

**Для Linux систем для запуска в качестве демона используйте screen:**
```bat
screen -dmLS JDWTBot java -jar JDWTBot.jar
```
screen также нужен для корректного ручного перезапуска бота или после обновления.

**Все команды доступны по команде ".help"**

**Ссылки на страницы загрузки:**
**[JDWTBot](https://github.com/sakhalines/JDWTBot) - Java Discord WarThunder Bot.**

Стабильная версия&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Версия в разработке.

[![Github Releases (by Release)](https://img.shields.io/github/downloads/atom/atom/v1.0.3/total.svg?style=plastic)](https://github.com/sakhalines/JDWTBot/releases/latest) &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[![Github Releases (by Release)](https://img.shields.io/github/downloads/atom/atom/v1.0.4/total.svg?style=plastic)](https://github.com/sakhalines/JDWTBot/releases/pre-release) 

**Выражаю свою благодарность [zekroTJA](https://github.com/zekroTJA)** 



````

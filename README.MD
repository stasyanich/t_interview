1. Входящие параметры:

    один параметр это количество элементов
    ```
    xmltoxml-1.0-SNAPSHOT [numberOfElements]
    ```

    два параметра это (первый_файл, второй_файл )
    ```
    xmltoxml-1.0-SNAPSHOT [fistFile] [secondFile]
    ```

    три параметра это (первый_файл, второй_файл, количество элементов)
    ```
    xmltoxml-1.0-SNAPSHOT [fistFile] [secondFile] [numberOfElements]
    ```
    * пример:
        ```
        xmltoxml-1.0-SNAPSHOT fistFile secondFile 777
        ```
        на выходе будет:
        **fistFile.xml, secondFile.xml, xmltoxml.db,  \logs\propertieslogs.log**
1. Использована **sqlite**

1. Сборка происходит череp **Maven**:
    ```
    mvn install
    ```
1. в директории /target/ через консоль идет запуск через **java** с ключом **-jar** для вывода служебной информации и логов в консоль
    ```
    java -jar xmltoxml-1.0-SNAPSHOT
    ```
1. лог пишется в
    ```
    \logs\propertieslogs.log
    ```
* Время работы с 1_000_000 элементов 7-10 секунд.

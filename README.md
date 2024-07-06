# pockey
Дипломный проект: Pockey
1. Добро пожаловать в репозиторий дипломного проекта.

Этот проект был создан в рамках выполнения дипломной работы в 2022 году. Приложение предназначено для ведения учета и анализа расходов и доходов пользователя.
В проекте использовались следующие инструменты и библиотеки:
- Java;
- [MPAndroidChart](https://github.com/PhilJay/MPAndroidChart/tree/master/);
- [Google Mobile Vision API](https://developers.google.com/ml-kit);
- [SQLite](https://developer.android.com/training/data-storage/sqlite);
- [DB Browser for SQLite](https://SQLitebrowser.org/ ).
2. Обратите внимание, что с момента создания проекта, некоторые из этих инструментов и библиотек могли устареть или быть модифицированы.
3. Поддержка и баги. После защиты дипломной работы данный проект не поддерживался, и могут возникать баги или неверные отображения.
Если вы обнаружите какие-либо проблемы, пожалуйста, имейте в виду, что проект не обновлялся с момента защиты.

4. Pockey

Главный экран содержит информацию о расходах, поделенных на 3 пункта: ежедневные, недельные и месячные расходы. Данные расходы отображаются при актуальной дат. Далее находится блок с месячным доходом пользователя. Блок бюджета содержит информацию о доступном капитале пользователя. Блок дневного лимита отображает сумму, которую может потратить пользователь. Далее отображается сколько из дневных финансов осталось. Дневной лимит рассчитывается на основе всех трат пользователя.
Следующие элементы отправляют пользователя на странице всех доходов и расходов. По центру в нижней части экрана расположена основная функция добавления средств. Слева от кнопки расположена кнопка со страницей «Other» описанной в структуре приложения. В правом нижнем углу расположена функция добавления средств по сканированию чека через QR-код.
![image](https://github.com/Goondle/pockey/assets/60343348/8593f406-d9e6-4cec-942c-c8c8c2c7dd90)
  
Страница всех расходов.

![image](https://github.com/Goondle/pockey/assets/60343348/2a1d8359-67dd-4e61-b76b-e04bd80937a9)

Данная страница имеет несколько полей ввода: суммы, категории, комментария, даты и времени и отдельного переключателя для выбора между расходом и доходом. Поле суммы имеет ограничение на вводимые значения в 10 символов и добавлен ввод через клавиатуру только цифрового варианта. Пользователь не сможет внести другие символы. Так же при добавлении новой траты время устанавливается автоматически. 

![image](https://github.com/Goondle/pockey/assets/60343348/c5b1f811-1481-4854-bd25-be84c536ce6a)

Кнопка «QR» запускает страницу сканирования с основной камеры смартфона. Перед использованием функции Android потребует предоставить права на использование камеры. Сканирование QR-кодов происходит в реальном времени. 
Найденный объект для сканирования будет отображен в верхнем правом углу камеры. Данное отображение доступно для текста в виде ссылок или предложений. В нижней части экрана при сканировании QR-кода с чека появляется активная кнопка «Добавление средств», при сканировании ссылки данная кнопка автоматически заменяется на кнопку «Открыть ссылку».
После сканирования QR-кода на чеке, пользователь переходит на страницу отображения будущей ссылки перехода и суммы чека.

![image](https://github.com/Goondle/pockey/assets/60343348/64d2b9b5-c5ef-40cb-b096-011583cf43d8)

В самом начале «ScrollView» отображается базовая информация о финансовом состоянии пользователя: общий расход за все время использования приложения, общий доход, бюджет.
Формирование средних значений расходов происходит на основе всех расходов за все месяца. Данное значение позволяет пользователю прогнозировать свои финансовые возможности. Значения в блоке «Min/Max» нужны для определения минимально и максимально возможного расхода за весь период дня, недели и месяца.

![image](https://github.com/Goondle/pockey/assets/60343348/8d55de31-83c9-40cd-901a-0939fa6a44a1)
 
Диаграмма расходов категорий отображает проценты по каждой существующей и имеющей траты категории

![image](https://github.com/Goondle/pockey/assets/60343348/aee91954-c1e5-48bb-9d97-5998262e4934)

График всех расходов представляет информацию о «Верхнем лимите» и «Нижнем лимите» всех расходов пользователя с начала года до текущего месяца. 

![image](https://github.com/Goondle/pockey/assets/60343348/a9c2c4ca-ef87-4452-abd1-dc9d9f54ed16)

График расходов и доходов отображает информацию о всех расходов и доходов пользователя с начала года до текущего месяца

![image](https://github.com/Goondle/pockey/assets/60343348/2193d6b6-5ca2-4eac-a984-007e8ddfbfac)

Блок «Платежи» содержит расходы, которые будут автоматически записываться в базу данных по определенному периоду. 

![image](https://github.com/Goondle/pockey/assets/60343348/50dbf57e-31b1-4df6-9f50-618441f8854b)

Страница добавления, редактирования или удаления платежей имеет следующий список атрибутов: название платежа для его предназначения, дата и время начала платежа, дата окончания, статус активности, статус уведомления о зачислении платежа, выпадающий список периода зачисления, на данный момент имеет период действия на единоразовый, каждодневный и ежемесячный расход или доход.

![image](https://github.com/Goondle/pockey/assets/60343348/2803ee53-e97b-424b-b7b9-660a2b9f271c)

Демонстрация уведомлений на разных устройствах 

![image](https://github.com/Goondle/pockey/assets/60343348/61f192ed-3731-4f5a-982f-3b62ba4bd7c8)


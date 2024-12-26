# TicTacToe CosmoVer
Типичная реализация культовой игры "Крестики-нолики" для мобильных android утройств. Однако, вместо знакомых X и O выступают цвета: красный и желтый. Этими цветами подвечиваются
`ImageButton` на игровом поле. Условия победы такие же как в игре оригинале.
_P.S. В будущем возможны некоторые нововведения на игровые механики или иные плюшки_

**Дизайн игры:**

<img src="https://github.com/user-attachments/assets/4c8771f1-23c1-4456-9510-3c8c9ccaa42a" width="250" height="530">

**Другая ориентация:** (фон изменен намеренно)

<img src="https://github.com/user-attachments/assets/3578d2b4-ee82-4bfc-bde4-669e80878dbb" width="530" height="250"> 

## Замена фона

В игре реализована прокрутка GIF анимации. Для изменения анимации замените ресурсы метода `startGifAnimation` в `MainActivity.java`.
По умолчанию в программе заложено три GIF: _cosmo_animation_slow, cosmo_animation_fast, cosmo_animation_default_.
```Java
    private void startGifAnimation(){
        ImageView gifImageView = findViewById(R.id.imageViewGif);
        Glide.with(this)
             .asGif()
             .load(R.drawable.cosmo_animation_slow)
             .into(gifImageView);

    }
```
Для добавления новых свойств к анимации найдите следующий элемент в `activity_main.xml`:
```XML
    <ImageView
        android:id="@+id/imageViewGif"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:importantForAccessibility="no"
        android:scaleType="centerCrop"
        android:alpha="0.15" />
```

## Анимации

Для изменения анимаций найдите файл `TicTacToe_Animation.java`. В этом файле содержатся все анимации игровых объектов: клетки игрового поля, счетчик, ракета.
Все анимации построены на классе `ObjectAnimator` и имеют следующий вид (_или подобные структуры_):
```Java
    public static void animateRocket(ImageView rocket, int screenWidth) {
        float currentX = rocket.getX();
        float newX = currentX + (screenWidth / 9.0f);

        ObjectAnimator rocketAnimator = ObjectAnimator.ofFloat(rocket, "x", currentX, newX);
        rocketAnimator.setDuration(300);
        rocketAnimator.start();
    }
```

## Изменение размеров игровых элементов

Игровые компоненты имеют адаптивную структуру, которая задается в `MainActivity.java`. Это сделано с целью комфортного отображения элементов
на разных дисплеях, что тяжело достичь только настройками xml. Для адаптивности используются методы `setTableLayoutSize`, `setImageHeightSize`, `setTextScoreSize`.
Пример одного из методов:
```Java
    // формирование квадратных ImageButton в зависимости от наименьшего вектора
    private void setTableLayoutSize(TableLayout tableLayout) {
        int min = Math.min(tableLayout.getWidth(), tableLayout.getHeight());

        ViewGroup.LayoutParams params = tableLayout.getLayoutParams();
        params.width = min;
        params.height = min;
        tableLayout.setLayoutParams(params);
    }
```

## Проблемы, которые можно обнаружить в проекте

**Отсуствует сохранение состояния активности**. При смене ориентации во время игры происходит сброс счета и остальных подсветок,
что происходит из-за перезапуска активности при развороте экрана.

**Анимация ракеты иногда может быть неправильной**. В редких случаях ракета находится не в том положении, котором она должна быть 
(суть анимации в том, что при каждлом ходе игрока, ракета движется по Х на 1/9 ширины экрана).

**Нет смены начального игрока**. Каждый раунд начинается за RED команду, что создает некоторый дисбаланс.
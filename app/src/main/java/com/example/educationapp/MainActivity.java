package com.example.educationapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvCode, rvTarget;
    private CodeAdapter codeAdapter, targetAdapter;
    private List<String> shuffledCode, targetCode, correctCode;

    private int currentLevel = 0; // Текущий уровень
    private boolean levelCompleted = false; // Завершён ли текущий уровень
    private final List<List<String>> levels = new ArrayList<>(); // Список уровней

    private Button btnNextLevel; // Кнопка для перехода на следующий уровень

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvCode = findViewById(R.id.rv_code);
        rvTarget = findViewById(R.id.rv_target);
        btnNextLevel = findViewById(R.id.btn_next_level);

        // Загрузка данных для уровней
        loadLevels();

        // Настройка адаптеров
        shuffledCode = new ArrayList<>();
        targetCode = new ArrayList<>();
        correctCode = new ArrayList<>(levels.get(currentLevel));

        Runnable onDataChanged = () -> {
            rvCode.getAdapter().notifyDataSetChanged();
            rvTarget.getAdapter().notifyDataSetChanged();
        };

        codeAdapter = new CodeAdapter(shuffledCode, targetCode, onDataChanged);
        targetAdapter = new CodeAdapter(targetCode, shuffledCode, onDataChanged);

        rvCode.setLayoutManager(new LinearLayoutManager(this));
        rvTarget.setLayoutManager(new LinearLayoutManager(this));

        rvCode.setAdapter(codeAdapter);
        rvTarget.setAdapter(targetAdapter);

        // Начальный уровень
        loadLevel(currentLevel);

        // Кнопка проверки
        Button btnCheck = findViewById(R.id.btn_check);
        btnCheck.setOnClickListener(view -> checkAnswer());

        // Кнопка перехода к следующему уровню
        btnNextLevel.setOnClickListener(view -> nextLevel());
        btnNextLevel.setEnabled(false); // Отключаем кнопку перехода на следующий уровень
    }

    private void loadLevels() {
        levels.add(List.of(
                "a = 7",
                "b = 8",
                "print(\"Первое число:\", a)",
                "print(\"Второе число:\", b)",
                "result = a + b",
                "print(\"Сумма чисел:\", result)"
        ));
        levels.add(List.of(
                "x = 10",
                "print(\"Число для проверки:\", x)",
                "if x % 2 == 0:",
                "    print(f\"Число {x} является четным.\")",
                "else:",
                "    print(f\"Число {x} является нечетным.\")"
        ));
        levels.add(List.of(
                "a, b, c = 3, 7, 12",
                "print(\"Числа для сравнения:\", a, b, c)",
                "min_value = min(a, b, c)",
                "print(\"Минимум:\", min_value)"
        ));
        levels.add(List.of(
                "x = 4",
                "y = 6",
                "print(\"Первое число:\", x)",
                "print(\"Второе число:\", y)",
                "product = x * y",
                "print(f\"Произведение чисел {x} и {y} равно {product}\")"
        ));
        levels.add(List.of(
                "text = \"Python\"",
                "print(\"Исходная строка:\", text)",
                "reversed_text = text[::-1]",
                "print(\"Строка в обратном порядке:\", reversed_text)"
        ));
    }

    private void loadLevel(int level) {
        if (level < 0 || level >= levels.size()) return;

        correctCode.clear();
        correctCode.addAll(levels.get(level));

        shuffledCode.clear();
        shuffledCode.addAll(correctCode);
        Collections.shuffle(shuffledCode);

        targetCode.clear();

        levelCompleted = false; // Уровень ещё не пройден
        btnNextLevel.setEnabled(false); // Отключаем кнопку перехода

        rvCode.getAdapter().notifyDataSetChanged();
        rvTarget.getAdapter().notifyDataSetChanged();
    }

    private void checkAnswer() {
        if (targetCode.equals(correctCode)) {
            Toast.makeText(this, "Правильно! Перейдите к следующему уровню.", Toast.LENGTH_SHORT).show();
            levelCompleted = true; // Уровень завершён
            btnNextLevel.setEnabled(true); // Делаем кнопку активной
        } else {
            Toast.makeText(this, "Ошибка! Попробуйте ещё раз.", Toast.LENGTH_SHORT).show();
        }
    }

    private void nextLevel() {
        if (currentLevel < levels.size() - 1) {
            currentLevel++;
            loadLevel(currentLevel);
        } else {
            Toast.makeText(this, "Поздравляем! Вы завершили все уровни.", Toast.LENGTH_SHORT).show();
        }
    }
}

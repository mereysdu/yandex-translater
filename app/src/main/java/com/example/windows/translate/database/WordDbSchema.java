//Переменные для каждой колоны в таблице
package com.example.windows.translate.database;

public class WordDbSchema {
    public static final class WordTable {
        public static final String NAME = "words";
        public static final class Cols {
            public static final String WORD = "word";
            public static final String FAVORITES = "favorites";
        }
    }
}

package com.puc.myapplication;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.puc.myapplication.TimeDao;
import com.puc.myapplication.Time;
import com.puc.myapplication.UsuarioDao;
import com.puc.myapplication.Usuario;

@Database(entities = {Usuario.class, Time.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public abstract UsuarioDao usuarioDao();
    public abstract TimeDao timeDao();

    // ✅ O callback é um campo 'final static' definido uma única vez (sem reatribuição)
    private static final RoomDatabase.Callback ROOM_CALLBACK = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            // Executado apenas quando o banco é criado pela primeira vez
            db.execSQL("INSERT INTO times (nome) VALUES ('Flamengo')");
            db.execSQL("INSERT INTO times (nome) VALUES ('Palmeiras')");
            db.execSQL("INSERT INTO times (nome) VALUES ('Corinthians')");
            db.execSQL("INSERT INTO times (nome) VALUES ('São Paulo')");
            db.execSQL("INSERT INTO times (nome) VALUES ('Vasco')");
            db.execSQL("INSERT INTO times (nome) VALUES ('Fluminense')");
            db.execSQL("INSERT INTO times (nome) VALUES ('Cruzeiro')");
            db.execSQL("INSERT INTO times (nome) VALUES ('Atlético-MG')");
            db.execSQL("INSERT INTO times (nome) VALUES ('Botafogo')");
            db.execSQL("INSERT INTO times (nome) VALUES ('Santos')");
        }
    };

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "app_database"
                    )
                    .allowMainThreadQueries()
                    .addCallback(ROOM_CALLBACK) // ✅ Usa o callback definido acima
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}

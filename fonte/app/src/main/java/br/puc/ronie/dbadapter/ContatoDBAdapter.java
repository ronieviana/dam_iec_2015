package br.puc.ronie.dbadapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ContatoDBAdapter {

    public static final String KEY_ROWID = "_id";
    public static final String KEY_NOME = "nome";
    public static final String KEY_EMAIL = "email";

    private static final String TAG = "ContatoDBAdapter";

    private static final String DATABASE_NAME = "MyDB";
    private static final String DATABASE_TABLE = "contacts";
    private static final int DATABASE_VERSION = 2;

    private static final String DATABASE_CREATE =
            "create table contacts (_id integer primary key autoincrement, "
                    + "nome text not null, email text not null);";

    private final Context context;

    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    /**
     * @param context
     */
    public ContatoDBAdapter(Context context) {
        this.context = context;
        DBHelper = new DatabaseHelper(this.context);
    }

    /**
     * @return
     * @throws SQLException
     */
    public ContatoDBAdapter open() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    /**
     *
     */
    public void close() {
        DBHelper.close();
    }

    //---insert a contact into the database---
    private long insertContact(String name, String email) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NOME, name);
        initialValues.put(KEY_EMAIL, email);
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    /**
     * Apaga um contato pelo seu id
     *
     * @param rowId
     * @return
     */
    private boolean deleteContact(long rowId) {
        return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    /**
     * Recupera do banco de dados todos os contatos.
     *
     * @return
     */
    private Cursor getAllContacts() {
        return db.query(DATABASE_TABLE, new String[]{KEY_ROWID, KEY_NOME,
                KEY_EMAIL}, null, null, null, null, null);
    }


    /**
     * @param rowId
     * @return
     * @throws SQLException
     */
    private Cursor getContact(long rowId) throws SQLException {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE, new String[]{KEY_ROWID,
                                KEY_NOME, KEY_EMAIL}, KEY_ROWID + "=" + rowId, null,
                        null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    /**
     * @param rowId
     * @param name
     * @param email
     * @return
     */
    private boolean updateContact(long rowId, String name, String email) {
        ContentValues args = new ContentValues();
        args.put(KEY_NOME, name);
        args.put(KEY_EMAIL, email);
        return db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(DATABASE_CREATE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS contacts");
            onCreate(db);
        }
    }

    /**
     * @return
     */
    public List<ContatoModel> getAllContatosModel() {
        List<ContatoModel> result = new ArrayList<ContatoModel>();

        this.open();
        Cursor c = this.getAllContacts();
        if (c.moveToFirst()) {
            do {
                result.add(new ContatoModel(c.getInt(0), c.getString(1), c.getString(2)));
            } while (c.moveToNext());
        }
        c.close();
        this.close();
        return result;
    }

    /**
     * @param contatoModel
     */
    public void insertContatoModel(ContatoModel contatoModel) {
        this.open();
        contatoModel.setId(this.insertContact(contatoModel.getNome(), contatoModel.getEmail()));
        this.close();
    }

    /**
     * @param contatoModel
     */
    public void deleteContatoModel(ContatoModel contatoModel) {
        this.open();
        this.deleteContact(contatoModel.getId());
        this.close();
    }

    /**
     *
     * @param contatoModel
     */
    public void updateContatoModel(ContatoModel contatoModel) {
        this.open();
        this.updateContact(contatoModel.getId(), contatoModel.getNome(), contatoModel.getEmail());
        this.close();
    }
}

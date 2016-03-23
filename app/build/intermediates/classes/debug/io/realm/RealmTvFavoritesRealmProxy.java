package io.realm;


import android.util.JsonReader;
import android.util.JsonToken;
import com.atlantbh.mymoviesapp.model.realm.RealmTvFavorites;
import io.realm.exceptions.RealmMigrationNeededException;
import io.realm.internal.ColumnInfo;
import io.realm.internal.ColumnType;
import io.realm.internal.ImplicitTransaction;
import io.realm.internal.LinkView;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.Table;
import io.realm.internal.TableOrView;
import io.realm.internal.android.JsonUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RealmTvFavoritesRealmProxy extends RealmTvFavorites
    implements RealmObjectProxy {

    static final class RealmTvFavoritesColumnInfo extends ColumnInfo {

        public final long idIndex;
        public final long titleIndex;
        public final long basicTextIndex;
        public final long posterPathIndex;
        public final long voteAverageIndex;

        RealmTvFavoritesColumnInfo(String path, Table table) {
            final Map<String, Long> indicesMap = new HashMap<String, Long>(5);
            this.idIndex = getValidColumnIndex(path, table, "RealmTvFavorites", "id");
            indicesMap.put("id", this.idIndex);

            this.titleIndex = getValidColumnIndex(path, table, "RealmTvFavorites", "title");
            indicesMap.put("title", this.titleIndex);

            this.basicTextIndex = getValidColumnIndex(path, table, "RealmTvFavorites", "basicText");
            indicesMap.put("basicText", this.basicTextIndex);

            this.posterPathIndex = getValidColumnIndex(path, table, "RealmTvFavorites", "posterPath");
            indicesMap.put("posterPath", this.posterPathIndex);

            this.voteAverageIndex = getValidColumnIndex(path, table, "RealmTvFavorites", "voteAverage");
            indicesMap.put("voteAverage", this.voteAverageIndex);

            setIndicesMap(indicesMap);
        }
    }

    private final RealmTvFavoritesColumnInfo columnInfo;
    private static final List<String> FIELD_NAMES;
    static {
        List<String> fieldNames = new ArrayList<String>();
        fieldNames.add("id");
        fieldNames.add("title");
        fieldNames.add("basicText");
        fieldNames.add("posterPath");
        fieldNames.add("voteAverage");
        FIELD_NAMES = Collections.unmodifiableList(fieldNames);
    }

    RealmTvFavoritesRealmProxy(ColumnInfo columnInfo) {
        this.columnInfo = (RealmTvFavoritesColumnInfo) columnInfo;
    }

    @Override
    @SuppressWarnings("cast")
    public int getId() {
        realm.checkIfValid();
        return (int) row.getLong(columnInfo.idIndex);
    }

    @Override
    public void setId(int value) {
        realm.checkIfValid();
        row.setLong(columnInfo.idIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String getTitle() {
        realm.checkIfValid();
        return (java.lang.String) row.getString(columnInfo.titleIndex);
    }

    @Override
    public void setTitle(String value) {
        realm.checkIfValid();
        if (value == null) {
            row.setNull(columnInfo.titleIndex);
            return;
        }
        row.setString(columnInfo.titleIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String getBasicText() {
        realm.checkIfValid();
        return (java.lang.String) row.getString(columnInfo.basicTextIndex);
    }

    @Override
    public void setBasicText(String value) {
        realm.checkIfValid();
        if (value == null) {
            row.setNull(columnInfo.basicTextIndex);
            return;
        }
        row.setString(columnInfo.basicTextIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String getPosterPath() {
        realm.checkIfValid();
        return (java.lang.String) row.getString(columnInfo.posterPathIndex);
    }

    @Override
    public void setPosterPath(String value) {
        realm.checkIfValid();
        if (value == null) {
            row.setNull(columnInfo.posterPathIndex);
            return;
        }
        row.setString(columnInfo.posterPathIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public Float getVoteAverage() {
        realm.checkIfValid();
        if (row.isNull(columnInfo.voteAverageIndex)) {
            return null;
        }
        return (float) row.getFloat(columnInfo.voteAverageIndex);
    }

    @Override
    public void setVoteAverage(Float value) {
        realm.checkIfValid();
        if (value == null) {
            row.setNull(columnInfo.voteAverageIndex);
            return;
        }
        row.setFloat(columnInfo.voteAverageIndex, value);
    }

    public static Table initTable(ImplicitTransaction transaction) {
        if (!transaction.hasTable("class_RealmTvFavorites")) {
            Table table = transaction.getTable("class_RealmTvFavorites");
            table.addColumn(ColumnType.INTEGER, "id", Table.NOT_NULLABLE);
            table.addColumn(ColumnType.STRING, "title", Table.NULLABLE);
            table.addColumn(ColumnType.STRING, "basicText", Table.NULLABLE);
            table.addColumn(ColumnType.STRING, "posterPath", Table.NULLABLE);
            table.addColumn(ColumnType.FLOAT, "voteAverage", Table.NULLABLE);
            table.addSearchIndex(table.getColumnIndex("id"));
            table.setPrimaryKey("id");
            return table;
        }
        return transaction.getTable("class_RealmTvFavorites");
    }

    public static RealmTvFavoritesColumnInfo validateTable(ImplicitTransaction transaction) {
        if (transaction.hasTable("class_RealmTvFavorites")) {
            Table table = transaction.getTable("class_RealmTvFavorites");
            if (table.getColumnCount() != 5) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Field count does not match - expected 5 but was " + table.getColumnCount());
            }
            Map<String, ColumnType> columnTypes = new HashMap<String, ColumnType>();
            for (long i = 0; i < 5; i++) {
                columnTypes.put(table.getColumnName(i), table.getColumnType(i));
            }

            final RealmTvFavoritesColumnInfo columnInfo = new RealmTvFavoritesColumnInfo(transaction.getPath(), table);

            if (!columnTypes.containsKey("id")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'id' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("id") != ColumnType.INTEGER) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'int' for field 'id' in existing Realm file.");
            }
            if (table.isColumnNullable(columnInfo.idIndex)) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Field 'id' does support null values in the existing Realm file. Use corresponding boxed type for field 'id' or migrate using io.realm.internal.Table.convertColumnToNotNullable().");
            }
            if (table.getPrimaryKey() != table.getColumnIndex("id")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Primary key not defined for field 'id' in existing Realm file. Add @PrimaryKey.");
            }
            if (!table.hasSearchIndex(table.getColumnIndex("id"))) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Index not defined for field 'id' in existing Realm file. Either set @Index or migrate using io.realm.internal.Table.removeSearchIndex().");
            }
            if (!columnTypes.containsKey("title")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'title' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("title") != ColumnType.STRING) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'String' for field 'title' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.titleIndex)) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Field 'title' is required. Either set @Required to field 'title' or migrate using io.realm.internal.Table.convertColumnToNullable().");
            }
            if (!columnTypes.containsKey("basicText")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'basicText' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("basicText") != ColumnType.STRING) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'String' for field 'basicText' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.basicTextIndex)) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Field 'basicText' is required. Either set @Required to field 'basicText' or migrate using io.realm.internal.Table.convertColumnToNullable().");
            }
            if (!columnTypes.containsKey("posterPath")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'posterPath' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("posterPath") != ColumnType.STRING) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'String' for field 'posterPath' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.posterPathIndex)) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Field 'posterPath' is required. Either set @Required to field 'posterPath' or migrate using io.realm.internal.Table.convertColumnToNullable().");
            }
            if (!columnTypes.containsKey("voteAverage")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'voteAverage' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("voteAverage") != ColumnType.FLOAT) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'Float' for field 'voteAverage' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.voteAverageIndex)) {
                throw new RealmMigrationNeededException(transaction.getPath(),"Field 'voteAverage' does not support null values in the existing Realm file. Either set @Required, use the primitive type for field 'voteAverage' or migrate using io.realm.internal.Table.convertColumnToNullable().");
            }
            return columnInfo;
        } else {
            throw new RealmMigrationNeededException(transaction.getPath(), "The RealmTvFavorites class is missing from the schema for this Realm.");
        }
    }

    public static String getTableName() {
        return "class_RealmTvFavorites";
    }

    public static List<String> getFieldNames() {
        return FIELD_NAMES;
    }

    @SuppressWarnings("cast")
    public static RealmTvFavorites createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        RealmTvFavorites obj = null;
        if (update) {
            Table table = realm.getTable(RealmTvFavorites.class);
            long pkColumnIndex = table.getPrimaryKey();
            if (!json.isNull("id")) {
                long rowIndex = table.findFirstLong(pkColumnIndex, json.getLong("id"));
                if (rowIndex != TableOrView.NO_MATCH) {
                    obj = new RealmTvFavoritesRealmProxy(realm.getColumnInfo(RealmTvFavorites.class));
                    obj.realm = realm;
                    obj.row = table.getUncheckedRow(rowIndex);
                }
            }
        }
        if (obj == null) {
            obj = realm.createObject(RealmTvFavorites.class);
        }
        if (json.has("id")) {
            if (json.isNull("id")) {
                throw new IllegalArgumentException("Trying to set non-nullable field id to null.");
            } else {
                obj.setId((int) json.getInt("id"));
            }
        }
        if (json.has("title")) {
            if (json.isNull("title")) {
                obj.setTitle(null);
            } else {
                obj.setTitle((String) json.getString("title"));
            }
        }
        if (json.has("basicText")) {
            if (json.isNull("basicText")) {
                obj.setBasicText(null);
            } else {
                obj.setBasicText((String) json.getString("basicText"));
            }
        }
        if (json.has("posterPath")) {
            if (json.isNull("posterPath")) {
                obj.setPosterPath(null);
            } else {
                obj.setPosterPath((String) json.getString("posterPath"));
            }
        }
        if (json.has("voteAverage")) {
            if (json.isNull("voteAverage")) {
                obj.setVoteAverage(null);
            } else {
                obj.setVoteAverage((float) json.getDouble("voteAverage"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    public static RealmTvFavorites createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        RealmTvFavorites obj = realm.createObject(RealmTvFavorites.class);
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("id")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field id to null.");
                } else {
                    obj.setId((int) reader.nextInt());
                }
            } else if (name.equals("title")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    obj.setTitle(null);
                } else {
                    obj.setTitle((String) reader.nextString());
                }
            } else if (name.equals("basicText")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    obj.setBasicText(null);
                } else {
                    obj.setBasicText((String) reader.nextString());
                }
            } else if (name.equals("posterPath")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    obj.setPosterPath(null);
                } else {
                    obj.setPosterPath((String) reader.nextString());
                }
            } else if (name.equals("voteAverage")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    obj.setVoteAverage(null);
                } else {
                    obj.setVoteAverage((float) reader.nextDouble());
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return obj;
    }

    public static RealmTvFavorites copyOrUpdate(Realm realm, RealmTvFavorites object, boolean update, Map<RealmObject,RealmObjectProxy> cache) {
        if (object.realm != null && object.realm.getPath().equals(realm.getPath())) {
            return object;
        }
        RealmTvFavorites realmObject = null;
        boolean canUpdate = update;
        if (canUpdate) {
            Table table = realm.getTable(RealmTvFavorites.class);
            long pkColumnIndex = table.getPrimaryKey();
            long rowIndex = table.findFirstLong(pkColumnIndex, object.getId());
            if (rowIndex != TableOrView.NO_MATCH) {
                realmObject = new RealmTvFavoritesRealmProxy(realm.getColumnInfo(RealmTvFavorites.class));
                realmObject.realm = realm;
                realmObject.row = table.getUncheckedRow(rowIndex);
                cache.put(object, (RealmObjectProxy) realmObject);
            } else {
                canUpdate = false;
            }
        }

        if (canUpdate) {
            return update(realm, realmObject, object, cache);
        } else {
            return copy(realm, object, update, cache);
        }
    }

    public static RealmTvFavorites copy(Realm realm, RealmTvFavorites newObject, boolean update, Map<RealmObject,RealmObjectProxy> cache) {
        RealmTvFavorites realmObject = realm.createObject(RealmTvFavorites.class, newObject.getId());
        cache.put(newObject, (RealmObjectProxy) realmObject);
        realmObject.setId(newObject.getId());
        realmObject.setTitle(newObject.getTitle());
        realmObject.setBasicText(newObject.getBasicText());
        realmObject.setPosterPath(newObject.getPosterPath());
        realmObject.setVoteAverage(newObject.getVoteAverage());
        return realmObject;
    }

    static RealmTvFavorites update(Realm realm, RealmTvFavorites realmObject, RealmTvFavorites newObject, Map<RealmObject, RealmObjectProxy> cache) {
        realmObject.setTitle(newObject.getTitle());
        realmObject.setBasicText(newObject.getBasicText());
        realmObject.setPosterPath(newObject.getPosterPath());
        realmObject.setVoteAverage(newObject.getVoteAverage());
        return realmObject;
    }

    @Override
    public String toString() {
        if (!isValid()) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("RealmTvFavorites = [");
        stringBuilder.append("{id:");
        stringBuilder.append(getId());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{title:");
        stringBuilder.append(getTitle() != null ? getTitle() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{basicText:");
        stringBuilder.append(getBasicText() != null ? getBasicText() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{posterPath:");
        stringBuilder.append(getPosterPath() != null ? getPosterPath() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{voteAverage:");
        stringBuilder.append(getVoteAverage() != null ? getVoteAverage() : "null");
        stringBuilder.append("}");
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public int hashCode() {
        String realmName = realm.getPath();
        String tableName = row.getTable().getName();
        long rowIndex = row.getIndex();

        int result = 17;
        result = 31 * result + ((realmName != null) ? realmName.hashCode() : 0);
        result = 31 * result + ((tableName != null) ? tableName.hashCode() : 0);
        result = 31 * result + (int) (rowIndex ^ (rowIndex >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RealmTvFavoritesRealmProxy aRealmTvFavorites = (RealmTvFavoritesRealmProxy)o;

        String path = realm.getPath();
        String otherPath = aRealmTvFavorites.realm.getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;;

        String tableName = row.getTable().getName();
        String otherTableName = aRealmTvFavorites.row.getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (row.getIndex() != aRealmTvFavorites.row.getIndex()) return false;

        return true;
    }

}

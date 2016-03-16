package io.realm;


import android.util.JsonReader;
import android.util.JsonToken;
import com.atlantbh.mymoviesapp.model.realm.RealmActor;
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

public class RealmActorRealmProxy extends RealmActor
    implements RealmObjectProxy {

    static final class RealmActorColumnInfo extends ColumnInfo {

        public final long idIndex;
        public final long nameIndex;
        public final long posterPathIndex;

        RealmActorColumnInfo(String path, Table table) {
            final Map<String, Long> indicesMap = new HashMap<String, Long>(3);
            this.idIndex = getValidColumnIndex(path, table, "RealmActor", "id");
            indicesMap.put("id", this.idIndex);

            this.nameIndex = getValidColumnIndex(path, table, "RealmActor", "name");
            indicesMap.put("name", this.nameIndex);

            this.posterPathIndex = getValidColumnIndex(path, table, "RealmActor", "posterPath");
            indicesMap.put("posterPath", this.posterPathIndex);

            setIndicesMap(indicesMap);
        }
    }

    private final RealmActorColumnInfo columnInfo;
    private static final List<String> FIELD_NAMES;
    static {
        List<String> fieldNames = new ArrayList<String>();
        fieldNames.add("id");
        fieldNames.add("name");
        fieldNames.add("posterPath");
        FIELD_NAMES = Collections.unmodifiableList(fieldNames);
    }

    RealmActorRealmProxy(ColumnInfo columnInfo) {
        this.columnInfo = (RealmActorColumnInfo) columnInfo;
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
    public String getName() {
        realm.checkIfValid();
        return (java.lang.String) row.getString(columnInfo.nameIndex);
    }

    @Override
    public void setName(String value) {
        realm.checkIfValid();
        if (value == null) {
            row.setNull(columnInfo.nameIndex);
            return;
        }
        row.setString(columnInfo.nameIndex, value);
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

    public static Table initTable(ImplicitTransaction transaction) {
        if (!transaction.hasTable("class_RealmActor")) {
            Table table = transaction.getTable("class_RealmActor");
            table.addColumn(ColumnType.INTEGER, "id", Table.NOT_NULLABLE);
            table.addColumn(ColumnType.STRING, "name", Table.NULLABLE);
            table.addColumn(ColumnType.STRING, "posterPath", Table.NULLABLE);
            table.addSearchIndex(table.getColumnIndex("id"));
            table.setPrimaryKey("id");
            return table;
        }
        return transaction.getTable("class_RealmActor");
    }

    public static RealmActorColumnInfo validateTable(ImplicitTransaction transaction) {
        if (transaction.hasTable("class_RealmActor")) {
            Table table = transaction.getTable("class_RealmActor");
            if (table.getColumnCount() != 3) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Field count does not match - expected 3 but was " + table.getColumnCount());
            }
            Map<String, ColumnType> columnTypes = new HashMap<String, ColumnType>();
            for (long i = 0; i < 3; i++) {
                columnTypes.put(table.getColumnName(i), table.getColumnType(i));
            }

            final RealmActorColumnInfo columnInfo = new RealmActorColumnInfo(transaction.getPath(), table);

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
            if (!columnTypes.containsKey("name")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'name' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("name") != ColumnType.STRING) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'String' for field 'name' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.nameIndex)) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Field 'name' is required. Either set @Required to field 'name' or migrate using io.realm.internal.Table.convertColumnToNullable().");
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
            return columnInfo;
        } else {
            throw new RealmMigrationNeededException(transaction.getPath(), "The RealmActor class is missing from the schema for this Realm.");
        }
    }

    public static String getTableName() {
        return "class_RealmActor";
    }

    public static List<String> getFieldNames() {
        return FIELD_NAMES;
    }

    @SuppressWarnings("cast")
    public static RealmActor createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        RealmActor obj = null;
        if (update) {
            Table table = realm.getTable(RealmActor.class);
            long pkColumnIndex = table.getPrimaryKey();
            if (!json.isNull("id")) {
                long rowIndex = table.findFirstLong(pkColumnIndex, json.getLong("id"));
                if (rowIndex != TableOrView.NO_MATCH) {
                    obj = new RealmActorRealmProxy(realm.getColumnInfo(RealmActor.class));
                    obj.realm = realm;
                    obj.row = table.getUncheckedRow(rowIndex);
                }
            }
        }
        if (obj == null) {
            obj = realm.createObject(RealmActor.class);
        }
        if (json.has("id")) {
            if (json.isNull("id")) {
                throw new IllegalArgumentException("Trying to set non-nullable field id to null.");
            } else {
                obj.setId((int) json.getInt("id"));
            }
        }
        if (json.has("name")) {
            if (json.isNull("name")) {
                obj.setName(null);
            } else {
                obj.setName((String) json.getString("name"));
            }
        }
        if (json.has("posterPath")) {
            if (json.isNull("posterPath")) {
                obj.setPosterPath(null);
            } else {
                obj.setPosterPath((String) json.getString("posterPath"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    public static RealmActor createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        RealmActor obj = realm.createObject(RealmActor.class);
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
            } else if (name.equals("name")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    obj.setName(null);
                } else {
                    obj.setName((String) reader.nextString());
                }
            } else if (name.equals("posterPath")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    obj.setPosterPath(null);
                } else {
                    obj.setPosterPath((String) reader.nextString());
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return obj;
    }

    public static RealmActor copyOrUpdate(Realm realm, RealmActor object, boolean update, Map<RealmObject,RealmObjectProxy> cache) {
        if (object.realm != null && object.realm.getPath().equals(realm.getPath())) {
            return object;
        }
        RealmActor realmObject = null;
        boolean canUpdate = update;
        if (canUpdate) {
            Table table = realm.getTable(RealmActor.class);
            long pkColumnIndex = table.getPrimaryKey();
            long rowIndex = table.findFirstLong(pkColumnIndex, object.getId());
            if (rowIndex != TableOrView.NO_MATCH) {
                realmObject = new RealmActorRealmProxy(realm.getColumnInfo(RealmActor.class));
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

    public static RealmActor copy(Realm realm, RealmActor newObject, boolean update, Map<RealmObject,RealmObjectProxy> cache) {
        RealmActor realmObject = realm.createObject(RealmActor.class, newObject.getId());
        cache.put(newObject, (RealmObjectProxy) realmObject);
        realmObject.setId(newObject.getId());
        realmObject.setName(newObject.getName());
        realmObject.setPosterPath(newObject.getPosterPath());
        return realmObject;
    }

    static RealmActor update(Realm realm, RealmActor realmObject, RealmActor newObject, Map<RealmObject, RealmObjectProxy> cache) {
        realmObject.setName(newObject.getName());
        realmObject.setPosterPath(newObject.getPosterPath());
        return realmObject;
    }

    @Override
    public String toString() {
        if (!isValid()) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("RealmActor = [");
        stringBuilder.append("{id:");
        stringBuilder.append(getId());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{name:");
        stringBuilder.append(getName() != null ? getName() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{posterPath:");
        stringBuilder.append(getPosterPath() != null ? getPosterPath() : "null");
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
        RealmActorRealmProxy aRealmActor = (RealmActorRealmProxy)o;

        String path = realm.getPath();
        String otherPath = aRealmActor.realm.getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;;

        String tableName = row.getTable().getName();
        String otherTableName = aRealmActor.row.getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (row.getIndex() != aRealmActor.row.getIndex()) return false;

        return true;
    }

}

package io.realm;


import android.util.JsonReader;
import android.util.JsonToken;
import com.atlantbh.mymoviesapp.model.realm.RealmMovieBasic;
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

public class RealmMovieBasicRealmProxy extends RealmMovieBasic
    implements RealmObjectProxy {

    static final class RealmMovieBasicColumnInfo extends ColumnInfo {

        public final long idIndex;
        public final long posterPathIndex;
        public final long overviewIndex;
        public final long titleIndex;
        public final long voteAverageIndex;
        public final long indexPopularIndex;
        public final long indexNowPlayingIndex;
        public final long indexTopRatedIndex;
        public final long categoryIndex;

        RealmMovieBasicColumnInfo(String path, Table table) {
            final Map<String, Long> indicesMap = new HashMap<String, Long>(9);
            this.idIndex = getValidColumnIndex(path, table, "RealmMovieBasic", "id");
            indicesMap.put("id", this.idIndex);

            this.posterPathIndex = getValidColumnIndex(path, table, "RealmMovieBasic", "posterPath");
            indicesMap.put("posterPath", this.posterPathIndex);

            this.overviewIndex = getValidColumnIndex(path, table, "RealmMovieBasic", "overview");
            indicesMap.put("overview", this.overviewIndex);

            this.titleIndex = getValidColumnIndex(path, table, "RealmMovieBasic", "title");
            indicesMap.put("title", this.titleIndex);

            this.voteAverageIndex = getValidColumnIndex(path, table, "RealmMovieBasic", "voteAverage");
            indicesMap.put("voteAverage", this.voteAverageIndex);

            this.indexPopularIndex = getValidColumnIndex(path, table, "RealmMovieBasic", "indexPopular");
            indicesMap.put("indexPopular", this.indexPopularIndex);

            this.indexNowPlayingIndex = getValidColumnIndex(path, table, "RealmMovieBasic", "indexNowPlaying");
            indicesMap.put("indexNowPlaying", this.indexNowPlayingIndex);

            this.indexTopRatedIndex = getValidColumnIndex(path, table, "RealmMovieBasic", "indexTopRated");
            indicesMap.put("indexTopRated", this.indexTopRatedIndex);

            this.categoryIndex = getValidColumnIndex(path, table, "RealmMovieBasic", "category");
            indicesMap.put("category", this.categoryIndex);

            setIndicesMap(indicesMap);
        }
    }

    private final RealmMovieBasicColumnInfo columnInfo;
    private static final List<String> FIELD_NAMES;
    static {
        List<String> fieldNames = new ArrayList<String>();
        fieldNames.add("id");
        fieldNames.add("posterPath");
        fieldNames.add("overview");
        fieldNames.add("title");
        fieldNames.add("voteAverage");
        fieldNames.add("indexPopular");
        fieldNames.add("indexNowPlaying");
        fieldNames.add("indexTopRated");
        fieldNames.add("category");
        FIELD_NAMES = Collections.unmodifiableList(fieldNames);
    }

    RealmMovieBasicRealmProxy(ColumnInfo columnInfo) {
        this.columnInfo = (RealmMovieBasicColumnInfo) columnInfo;
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
    public String getOverview() {
        realm.checkIfValid();
        return (java.lang.String) row.getString(columnInfo.overviewIndex);
    }

    @Override
    public void setOverview(String value) {
        realm.checkIfValid();
        if (value == null) {
            row.setNull(columnInfo.overviewIndex);
            return;
        }
        row.setString(columnInfo.overviewIndex, value);
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

    @Override
    @SuppressWarnings("cast")
    public int getIndexPopular() {
        realm.checkIfValid();
        return (int) row.getLong(columnInfo.indexPopularIndex);
    }

    @Override
    public void setIndexPopular(int value) {
        realm.checkIfValid();
        row.setLong(columnInfo.indexPopularIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public int getIndexNowPlaying() {
        realm.checkIfValid();
        return (int) row.getLong(columnInfo.indexNowPlayingIndex);
    }

    @Override
    public void setIndexNowPlaying(int value) {
        realm.checkIfValid();
        row.setLong(columnInfo.indexNowPlayingIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public int getIndexTopRated() {
        realm.checkIfValid();
        return (int) row.getLong(columnInfo.indexTopRatedIndex);
    }

    @Override
    public void setIndexTopRated(int value) {
        realm.checkIfValid();
        row.setLong(columnInfo.indexTopRatedIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public int getCategory() {
        realm.checkIfValid();
        return (int) row.getLong(columnInfo.categoryIndex);
    }

    @Override
    public void setCategory(int value) {
        realm.checkIfValid();
        row.setLong(columnInfo.categoryIndex, value);
    }

    public static Table initTable(ImplicitTransaction transaction) {
        if (!transaction.hasTable("class_RealmMovieBasic")) {
            Table table = transaction.getTable("class_RealmMovieBasic");
            table.addColumn(ColumnType.INTEGER, "id", Table.NOT_NULLABLE);
            table.addColumn(ColumnType.STRING, "posterPath", Table.NULLABLE);
            table.addColumn(ColumnType.STRING, "overview", Table.NULLABLE);
            table.addColumn(ColumnType.STRING, "title", Table.NULLABLE);
            table.addColumn(ColumnType.FLOAT, "voteAverage", Table.NULLABLE);
            table.addColumn(ColumnType.INTEGER, "indexPopular", Table.NOT_NULLABLE);
            table.addColumn(ColumnType.INTEGER, "indexNowPlaying", Table.NOT_NULLABLE);
            table.addColumn(ColumnType.INTEGER, "indexTopRated", Table.NOT_NULLABLE);
            table.addColumn(ColumnType.INTEGER, "category", Table.NOT_NULLABLE);
            table.addSearchIndex(table.getColumnIndex("id"));
            table.setPrimaryKey("id");
            return table;
        }
        return transaction.getTable("class_RealmMovieBasic");
    }

    public static RealmMovieBasicColumnInfo validateTable(ImplicitTransaction transaction) {
        if (transaction.hasTable("class_RealmMovieBasic")) {
            Table table = transaction.getTable("class_RealmMovieBasic");
            if (table.getColumnCount() != 9) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Field count does not match - expected 9 but was " + table.getColumnCount());
            }
            Map<String, ColumnType> columnTypes = new HashMap<String, ColumnType>();
            for (long i = 0; i < 9; i++) {
                columnTypes.put(table.getColumnName(i), table.getColumnType(i));
            }

            final RealmMovieBasicColumnInfo columnInfo = new RealmMovieBasicColumnInfo(transaction.getPath(), table);

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
            if (!columnTypes.containsKey("posterPath")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'posterPath' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("posterPath") != ColumnType.STRING) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'String' for field 'posterPath' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.posterPathIndex)) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Field 'posterPath' is required. Either set @Required to field 'posterPath' or migrate using io.realm.internal.Table.convertColumnToNullable().");
            }
            if (!columnTypes.containsKey("overview")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'overview' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("overview") != ColumnType.STRING) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'String' for field 'overview' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.overviewIndex)) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Field 'overview' is required. Either set @Required to field 'overview' or migrate using io.realm.internal.Table.convertColumnToNullable().");
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
            if (!columnTypes.containsKey("voteAverage")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'voteAverage' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("voteAverage") != ColumnType.FLOAT) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'Float' for field 'voteAverage' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.voteAverageIndex)) {
                throw new RealmMigrationNeededException(transaction.getPath(),"Field 'voteAverage' does not support null values in the existing Realm file. Either set @Required, use the primitive type for field 'voteAverage' or migrate using io.realm.internal.Table.convertColumnToNullable().");
            }
            if (!columnTypes.containsKey("indexPopular")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'indexPopular' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("indexPopular") != ColumnType.INTEGER) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'int' for field 'indexPopular' in existing Realm file.");
            }
            if (table.isColumnNullable(columnInfo.indexPopularIndex)) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Field 'indexPopular' does support null values in the existing Realm file. Use corresponding boxed type for field 'indexPopular' or migrate using io.realm.internal.Table.convertColumnToNotNullable().");
            }
            if (!columnTypes.containsKey("indexNowPlaying")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'indexNowPlaying' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("indexNowPlaying") != ColumnType.INTEGER) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'int' for field 'indexNowPlaying' in existing Realm file.");
            }
            if (table.isColumnNullable(columnInfo.indexNowPlayingIndex)) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Field 'indexNowPlaying' does support null values in the existing Realm file. Use corresponding boxed type for field 'indexNowPlaying' or migrate using io.realm.internal.Table.convertColumnToNotNullable().");
            }
            if (!columnTypes.containsKey("indexTopRated")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'indexTopRated' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("indexTopRated") != ColumnType.INTEGER) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'int' for field 'indexTopRated' in existing Realm file.");
            }
            if (table.isColumnNullable(columnInfo.indexTopRatedIndex)) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Field 'indexTopRated' does support null values in the existing Realm file. Use corresponding boxed type for field 'indexTopRated' or migrate using io.realm.internal.Table.convertColumnToNotNullable().");
            }
            if (!columnTypes.containsKey("category")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'category' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("category") != ColumnType.INTEGER) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'int' for field 'category' in existing Realm file.");
            }
            if (table.isColumnNullable(columnInfo.categoryIndex)) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Field 'category' does support null values in the existing Realm file. Use corresponding boxed type for field 'category' or migrate using io.realm.internal.Table.convertColumnToNotNullable().");
            }
            return columnInfo;
        } else {
            throw new RealmMigrationNeededException(transaction.getPath(), "The RealmMovieBasic class is missing from the schema for this Realm.");
        }
    }

    public static String getTableName() {
        return "class_RealmMovieBasic";
    }

    public static List<String> getFieldNames() {
        return FIELD_NAMES;
    }

    @SuppressWarnings("cast")
    public static RealmMovieBasic createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        RealmMovieBasic obj = null;
        if (update) {
            Table table = realm.getTable(RealmMovieBasic.class);
            long pkColumnIndex = table.getPrimaryKey();
            if (!json.isNull("id")) {
                long rowIndex = table.findFirstLong(pkColumnIndex, json.getLong("id"));
                if (rowIndex != TableOrView.NO_MATCH) {
                    obj = new RealmMovieBasicRealmProxy(realm.getColumnInfo(RealmMovieBasic.class));
                    obj.realm = realm;
                    obj.row = table.getUncheckedRow(rowIndex);
                }
            }
        }
        if (obj == null) {
            obj = realm.createObject(RealmMovieBasic.class);
        }
        if (json.has("id")) {
            if (json.isNull("id")) {
                throw new IllegalArgumentException("Trying to set non-nullable field id to null.");
            } else {
                obj.setId((int) json.getInt("id"));
            }
        }
        if (json.has("posterPath")) {
            if (json.isNull("posterPath")) {
                obj.setPosterPath(null);
            } else {
                obj.setPosterPath((String) json.getString("posterPath"));
            }
        }
        if (json.has("overview")) {
            if (json.isNull("overview")) {
                obj.setOverview(null);
            } else {
                obj.setOverview((String) json.getString("overview"));
            }
        }
        if (json.has("title")) {
            if (json.isNull("title")) {
                obj.setTitle(null);
            } else {
                obj.setTitle((String) json.getString("title"));
            }
        }
        if (json.has("voteAverage")) {
            if (json.isNull("voteAverage")) {
                obj.setVoteAverage(null);
            } else {
                obj.setVoteAverage((float) json.getDouble("voteAverage"));
            }
        }
        if (json.has("indexPopular")) {
            if (json.isNull("indexPopular")) {
                throw new IllegalArgumentException("Trying to set non-nullable field indexPopular to null.");
            } else {
                obj.setIndexPopular((int) json.getInt("indexPopular"));
            }
        }
        if (json.has("indexNowPlaying")) {
            if (json.isNull("indexNowPlaying")) {
                throw new IllegalArgumentException("Trying to set non-nullable field indexNowPlaying to null.");
            } else {
                obj.setIndexNowPlaying((int) json.getInt("indexNowPlaying"));
            }
        }
        if (json.has("indexTopRated")) {
            if (json.isNull("indexTopRated")) {
                throw new IllegalArgumentException("Trying to set non-nullable field indexTopRated to null.");
            } else {
                obj.setIndexTopRated((int) json.getInt("indexTopRated"));
            }
        }
        if (json.has("category")) {
            if (json.isNull("category")) {
                throw new IllegalArgumentException("Trying to set non-nullable field category to null.");
            } else {
                obj.setCategory((int) json.getInt("category"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    public static RealmMovieBasic createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        RealmMovieBasic obj = realm.createObject(RealmMovieBasic.class);
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
            } else if (name.equals("posterPath")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    obj.setPosterPath(null);
                } else {
                    obj.setPosterPath((String) reader.nextString());
                }
            } else if (name.equals("overview")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    obj.setOverview(null);
                } else {
                    obj.setOverview((String) reader.nextString());
                }
            } else if (name.equals("title")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    obj.setTitle(null);
                } else {
                    obj.setTitle((String) reader.nextString());
                }
            } else if (name.equals("voteAverage")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    obj.setVoteAverage(null);
                } else {
                    obj.setVoteAverage((float) reader.nextDouble());
                }
            } else if (name.equals("indexPopular")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field indexPopular to null.");
                } else {
                    obj.setIndexPopular((int) reader.nextInt());
                }
            } else if (name.equals("indexNowPlaying")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field indexNowPlaying to null.");
                } else {
                    obj.setIndexNowPlaying((int) reader.nextInt());
                }
            } else if (name.equals("indexTopRated")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field indexTopRated to null.");
                } else {
                    obj.setIndexTopRated((int) reader.nextInt());
                }
            } else if (name.equals("category")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field category to null.");
                } else {
                    obj.setCategory((int) reader.nextInt());
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return obj;
    }

    public static RealmMovieBasic copyOrUpdate(Realm realm, RealmMovieBasic object, boolean update, Map<RealmObject,RealmObjectProxy> cache) {
        if (object.realm != null && object.realm.getPath().equals(realm.getPath())) {
            return object;
        }
        RealmMovieBasic realmObject = null;
        boolean canUpdate = update;
        if (canUpdate) {
            Table table = realm.getTable(RealmMovieBasic.class);
            long pkColumnIndex = table.getPrimaryKey();
            long rowIndex = table.findFirstLong(pkColumnIndex, object.getId());
            if (rowIndex != TableOrView.NO_MATCH) {
                realmObject = new RealmMovieBasicRealmProxy(realm.getColumnInfo(RealmMovieBasic.class));
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

    public static RealmMovieBasic copy(Realm realm, RealmMovieBasic newObject, boolean update, Map<RealmObject,RealmObjectProxy> cache) {
        RealmMovieBasic realmObject = realm.createObject(RealmMovieBasic.class, newObject.getId());
        cache.put(newObject, (RealmObjectProxy) realmObject);
        realmObject.setId(newObject.getId());
        realmObject.setPosterPath(newObject.getPosterPath());
        realmObject.setOverview(newObject.getOverview());
        realmObject.setTitle(newObject.getTitle());
        realmObject.setVoteAverage(newObject.getVoteAverage());
        realmObject.setIndexPopular(newObject.getIndexPopular());
        realmObject.setIndexNowPlaying(newObject.getIndexNowPlaying());
        realmObject.setIndexTopRated(newObject.getIndexTopRated());
        realmObject.setCategory(newObject.getCategory());
        return realmObject;
    }

    static RealmMovieBasic update(Realm realm, RealmMovieBasic realmObject, RealmMovieBasic newObject, Map<RealmObject, RealmObjectProxy> cache) {
        realmObject.setPosterPath(newObject.getPosterPath());
        realmObject.setOverview(newObject.getOverview());
        realmObject.setTitle(newObject.getTitle());
        realmObject.setVoteAverage(newObject.getVoteAverage());
        realmObject.setIndexPopular(newObject.getIndexPopular());
        realmObject.setIndexNowPlaying(newObject.getIndexNowPlaying());
        realmObject.setIndexTopRated(newObject.getIndexTopRated());
        realmObject.setCategory(newObject.getCategory());
        return realmObject;
    }

    @Override
    public String toString() {
        if (!isValid()) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("RealmMovieBasic = [");
        stringBuilder.append("{id:");
        stringBuilder.append(getId());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{posterPath:");
        stringBuilder.append(getPosterPath() != null ? getPosterPath() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{overview:");
        stringBuilder.append(getOverview() != null ? getOverview() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{title:");
        stringBuilder.append(getTitle() != null ? getTitle() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{voteAverage:");
        stringBuilder.append(getVoteAverage() != null ? getVoteAverage() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{indexPopular:");
        stringBuilder.append(getIndexPopular());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{indexNowPlaying:");
        stringBuilder.append(getIndexNowPlaying());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{indexTopRated:");
        stringBuilder.append(getIndexTopRated());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{category:");
        stringBuilder.append(getCategory());
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
        RealmMovieBasicRealmProxy aRealmMovieBasic = (RealmMovieBasicRealmProxy)o;

        String path = realm.getPath();
        String otherPath = aRealmMovieBasic.realm.getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;;

        String tableName = row.getTable().getName();
        String otherTableName = aRealmMovieBasic.row.getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (row.getIndex() != aRealmMovieBasic.row.getIndex()) return false;

        return true;
    }

}

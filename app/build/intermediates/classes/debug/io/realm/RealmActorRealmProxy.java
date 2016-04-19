package io.realm;


import android.util.JsonReader;
import android.util.JsonToken;
import com.atlantbh.mymoviesapp.model.realm.RealmActor;
import com.atlantbh.mymoviesapp.model.realm.RealmMovieCredits;
import com.atlantbh.mymoviesapp.model.realm.RealmTvCredits;
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
        public final long profilePathIndex;
        public final long backdropPathIndex;
        public final long biographyIndex;
        public final long jobsIndex;
        public final long realmMovieCreditsIndex;
        public final long realmTvCreditsIndex;

        RealmActorColumnInfo(String path, Table table) {
            final Map<String, Long> indicesMap = new HashMap<String, Long>(8);
            this.idIndex = getValidColumnIndex(path, table, "RealmActor", "id");
            indicesMap.put("id", this.idIndex);

            this.nameIndex = getValidColumnIndex(path, table, "RealmActor", "name");
            indicesMap.put("name", this.nameIndex);

            this.profilePathIndex = getValidColumnIndex(path, table, "RealmActor", "profilePath");
            indicesMap.put("profilePath", this.profilePathIndex);

            this.backdropPathIndex = getValidColumnIndex(path, table, "RealmActor", "backdropPath");
            indicesMap.put("backdropPath", this.backdropPathIndex);

            this.biographyIndex = getValidColumnIndex(path, table, "RealmActor", "biography");
            indicesMap.put("biography", this.biographyIndex);

            this.jobsIndex = getValidColumnIndex(path, table, "RealmActor", "jobs");
            indicesMap.put("jobs", this.jobsIndex);

            this.realmMovieCreditsIndex = getValidColumnIndex(path, table, "RealmActor", "realmMovieCredits");
            indicesMap.put("realmMovieCredits", this.realmMovieCreditsIndex);

            this.realmTvCreditsIndex = getValidColumnIndex(path, table, "RealmActor", "realmTvCredits");
            indicesMap.put("realmTvCredits", this.realmTvCreditsIndex);

            setIndicesMap(indicesMap);
        }
    }

    private final RealmActorColumnInfo columnInfo;
    private RealmList<RealmMovieCredits> realmMovieCreditsRealmList;
    private static RealmList<RealmMovieCredits> EMPTY_REALM_LIST_REALMMOVIECREDITS;
    private RealmList<RealmTvCredits> realmTvCreditsRealmList;
    private static RealmList<RealmTvCredits> EMPTY_REALM_LIST_REALMTVCREDITS;
    private static final List<String> FIELD_NAMES;
    static {
        EMPTY_REALM_LIST_REALMMOVIECREDITS = new RealmList<RealmMovieCredits>();
        EMPTY_REALM_LIST_REALMTVCREDITS = new RealmList<RealmTvCredits>();
        List<String> fieldNames = new ArrayList<String>();
        fieldNames.add("id");
        fieldNames.add("name");
        fieldNames.add("profilePath");
        fieldNames.add("backdropPath");
        fieldNames.add("biography");
        fieldNames.add("jobs");
        fieldNames.add("realmMovieCredits");
        fieldNames.add("realmTvCredits");
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
    public String getProfilePath() {
        realm.checkIfValid();
        return (java.lang.String) row.getString(columnInfo.profilePathIndex);
    }

    @Override
    public void setProfilePath(String value) {
        realm.checkIfValid();
        if (value == null) {
            row.setNull(columnInfo.profilePathIndex);
            return;
        }
        row.setString(columnInfo.profilePathIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String getBackdropPath() {
        realm.checkIfValid();
        return (java.lang.String) row.getString(columnInfo.backdropPathIndex);
    }

    @Override
    public void setBackdropPath(String value) {
        realm.checkIfValid();
        if (value == null) {
            row.setNull(columnInfo.backdropPathIndex);
            return;
        }
        row.setString(columnInfo.backdropPathIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String getBiography() {
        realm.checkIfValid();
        return (java.lang.String) row.getString(columnInfo.biographyIndex);
    }

    @Override
    public void setBiography(String value) {
        realm.checkIfValid();
        if (value == null) {
            row.setNull(columnInfo.biographyIndex);
            return;
        }
        row.setString(columnInfo.biographyIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String getJobs() {
        realm.checkIfValid();
        return (java.lang.String) row.getString(columnInfo.jobsIndex);
    }

    @Override
    public void setJobs(String value) {
        realm.checkIfValid();
        if (value == null) {
            row.setNull(columnInfo.jobsIndex);
            return;
        }
        row.setString(columnInfo.jobsIndex, value);
    }

    @Override
    public RealmList<RealmMovieCredits> getRealmMovieCredits() {
        realm.checkIfValid();
        // use the cached value if available
        if (realmMovieCreditsRealmList != null) {
            return realmMovieCreditsRealmList;
        } else {
            LinkView linkView = row.getLinkList(columnInfo.realmMovieCreditsIndex);
            if (linkView == null) {
                // return empty non managed RealmList if the LinkView is null
                // useful for non-initialized RealmObject (async query returns empty Row while the query is still running)
                return EMPTY_REALM_LIST_REALMMOVIECREDITS;
            } else {
                realmMovieCreditsRealmList = new RealmList<RealmMovieCredits>(RealmMovieCredits.class, linkView, realm);
                return realmMovieCreditsRealmList;
            }
        }
    }

    @Override
    public void setRealmMovieCredits(RealmList<RealmMovieCredits> value) {
        realm.checkIfValid();
        LinkView links = row.getLinkList(columnInfo.realmMovieCreditsIndex);
        links.clear();
        if (value == null) {
            return;
        }
        for (RealmObject linkedObject : (RealmList<? extends RealmObject>) value) {
            links.add(linkedObject.row.getIndex());
        }
    }

    @Override
    public RealmList<RealmTvCredits> getRealmTvCredits() {
        realm.checkIfValid();
        // use the cached value if available
        if (realmTvCreditsRealmList != null) {
            return realmTvCreditsRealmList;
        } else {
            LinkView linkView = row.getLinkList(columnInfo.realmTvCreditsIndex);
            if (linkView == null) {
                // return empty non managed RealmList if the LinkView is null
                // useful for non-initialized RealmObject (async query returns empty Row while the query is still running)
                return EMPTY_REALM_LIST_REALMTVCREDITS;
            } else {
                realmTvCreditsRealmList = new RealmList<RealmTvCredits>(RealmTvCredits.class, linkView, realm);
                return realmTvCreditsRealmList;
            }
        }
    }

    @Override
    public void setRealmTvCredits(RealmList<RealmTvCredits> value) {
        realm.checkIfValid();
        LinkView links = row.getLinkList(columnInfo.realmTvCreditsIndex);
        links.clear();
        if (value == null) {
            return;
        }
        for (RealmObject linkedObject : (RealmList<? extends RealmObject>) value) {
            links.add(linkedObject.row.getIndex());
        }
    }

    public static Table initTable(ImplicitTransaction transaction) {
        if (!transaction.hasTable("class_RealmActor")) {
            Table table = transaction.getTable("class_RealmActor");
            table.addColumn(ColumnType.INTEGER, "id", Table.NOT_NULLABLE);
            table.addColumn(ColumnType.STRING, "name", Table.NULLABLE);
            table.addColumn(ColumnType.STRING, "profilePath", Table.NULLABLE);
            table.addColumn(ColumnType.STRING, "backdropPath", Table.NULLABLE);
            table.addColumn(ColumnType.STRING, "biography", Table.NULLABLE);
            table.addColumn(ColumnType.STRING, "jobs", Table.NULLABLE);
            if (!transaction.hasTable("class_RealmMovieCredits")) {
                RealmMovieCreditsRealmProxy.initTable(transaction);
            }
            table.addColumnLink(ColumnType.LINK_LIST, "realmMovieCredits", transaction.getTable("class_RealmMovieCredits"));
            if (!transaction.hasTable("class_RealmTvCredits")) {
                RealmTvCreditsRealmProxy.initTable(transaction);
            }
            table.addColumnLink(ColumnType.LINK_LIST, "realmTvCredits", transaction.getTable("class_RealmTvCredits"));
            table.addSearchIndex(table.getColumnIndex("id"));
            table.setPrimaryKey("id");
            return table;
        }
        return transaction.getTable("class_RealmActor");
    }

    public static RealmActorColumnInfo validateTable(ImplicitTransaction transaction) {
        if (transaction.hasTable("class_RealmActor")) {
            Table table = transaction.getTable("class_RealmActor");
            if (table.getColumnCount() != 8) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Field count does not match - expected 8 but was " + table.getColumnCount());
            }
            Map<String, ColumnType> columnTypes = new HashMap<String, ColumnType>();
            for (long i = 0; i < 8; i++) {
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
            if (!columnTypes.containsKey("profilePath")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'profilePath' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("profilePath") != ColumnType.STRING) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'String' for field 'profilePath' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.profilePathIndex)) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Field 'profilePath' is required. Either set @Required to field 'profilePath' or migrate using io.realm.internal.Table.convertColumnToNullable().");
            }
            if (!columnTypes.containsKey("backdropPath")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'backdropPath' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("backdropPath") != ColumnType.STRING) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'String' for field 'backdropPath' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.backdropPathIndex)) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Field 'backdropPath' is required. Either set @Required to field 'backdropPath' or migrate using io.realm.internal.Table.convertColumnToNullable().");
            }
            if (!columnTypes.containsKey("biography")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'biography' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("biography") != ColumnType.STRING) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'String' for field 'biography' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.biographyIndex)) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Field 'biography' is required. Either set @Required to field 'biography' or migrate using io.realm.internal.Table.convertColumnToNullable().");
            }
            if (!columnTypes.containsKey("jobs")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'jobs' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("jobs") != ColumnType.STRING) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'String' for field 'jobs' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.jobsIndex)) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Field 'jobs' is required. Either set @Required to field 'jobs' or migrate using io.realm.internal.Table.convertColumnToNullable().");
            }
            if (!columnTypes.containsKey("realmMovieCredits")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'realmMovieCredits'");
            }
            if (columnTypes.get("realmMovieCredits") != ColumnType.LINK_LIST) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'RealmMovieCredits' for field 'realmMovieCredits'");
            }
            if (!transaction.hasTable("class_RealmMovieCredits")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing class 'class_RealmMovieCredits' for field 'realmMovieCredits'");
            }
            Table table_6 = transaction.getTable("class_RealmMovieCredits");
            if (!table.getLinkTarget(columnInfo.realmMovieCreditsIndex).hasSameSchema(table_6)) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid RealmList type for field 'realmMovieCredits': '" + table.getLinkTarget(columnInfo.realmMovieCreditsIndex).getName() + "' expected - was '" + table_6.getName() + "'");
            }
            if (!columnTypes.containsKey("realmTvCredits")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'realmTvCredits'");
            }
            if (columnTypes.get("realmTvCredits") != ColumnType.LINK_LIST) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'RealmTvCredits' for field 'realmTvCredits'");
            }
            if (!transaction.hasTable("class_RealmTvCredits")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing class 'class_RealmTvCredits' for field 'realmTvCredits'");
            }
            Table table_7 = transaction.getTable("class_RealmTvCredits");
            if (!table.getLinkTarget(columnInfo.realmTvCreditsIndex).hasSameSchema(table_7)) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid RealmList type for field 'realmTvCredits': '" + table.getLinkTarget(columnInfo.realmTvCreditsIndex).getName() + "' expected - was '" + table_7.getName() + "'");
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
        if (json.has("profilePath")) {
            if (json.isNull("profilePath")) {
                obj.setProfilePath(null);
            } else {
                obj.setProfilePath((String) json.getString("profilePath"));
            }
        }
        if (json.has("backdropPath")) {
            if (json.isNull("backdropPath")) {
                obj.setBackdropPath(null);
            } else {
                obj.setBackdropPath((String) json.getString("backdropPath"));
            }
        }
        if (json.has("biography")) {
            if (json.isNull("biography")) {
                obj.setBiography(null);
            } else {
                obj.setBiography((String) json.getString("biography"));
            }
        }
        if (json.has("jobs")) {
            if (json.isNull("jobs")) {
                obj.setJobs(null);
            } else {
                obj.setJobs((String) json.getString("jobs"));
            }
        }
        if (json.has("realmMovieCredits")) {
            if (json.isNull("realmMovieCredits")) {
                obj.setRealmMovieCredits(null);
            } else {
                obj.getRealmMovieCredits().clear();
                JSONArray array = json.getJSONArray("realmMovieCredits");
                for (int i = 0; i < array.length(); i++) {
                    com.atlantbh.mymoviesapp.model.realm.RealmMovieCredits item = RealmMovieCreditsRealmProxy.createOrUpdateUsingJsonObject(realm, array.getJSONObject(i), update);
                    obj.getRealmMovieCredits().add(item);
                }
            }
        }
        if (json.has("realmTvCredits")) {
            if (json.isNull("realmTvCredits")) {
                obj.setRealmTvCredits(null);
            } else {
                obj.getRealmTvCredits().clear();
                JSONArray array = json.getJSONArray("realmTvCredits");
                for (int i = 0; i < array.length(); i++) {
                    com.atlantbh.mymoviesapp.model.realm.RealmTvCredits item = RealmTvCreditsRealmProxy.createOrUpdateUsingJsonObject(realm, array.getJSONObject(i), update);
                    obj.getRealmTvCredits().add(item);
                }
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
            } else if (name.equals("profilePath")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    obj.setProfilePath(null);
                } else {
                    obj.setProfilePath((String) reader.nextString());
                }
            } else if (name.equals("backdropPath")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    obj.setBackdropPath(null);
                } else {
                    obj.setBackdropPath((String) reader.nextString());
                }
            } else if (name.equals("biography")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    obj.setBiography(null);
                } else {
                    obj.setBiography((String) reader.nextString());
                }
            } else if (name.equals("jobs")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    obj.setJobs(null);
                } else {
                    obj.setJobs((String) reader.nextString());
                }
            } else if (name.equals("realmMovieCredits")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    obj.setRealmMovieCredits(null);
                } else {
                    reader.beginArray();
                    while (reader.hasNext()) {
                        com.atlantbh.mymoviesapp.model.realm.RealmMovieCredits item = RealmMovieCreditsRealmProxy.createUsingJsonStream(realm, reader);
                        obj.getRealmMovieCredits().add(item);
                    }
                    reader.endArray();
                }
            } else if (name.equals("realmTvCredits")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    obj.setRealmTvCredits(null);
                } else {
                    reader.beginArray();
                    while (reader.hasNext()) {
                        com.atlantbh.mymoviesapp.model.realm.RealmTvCredits item = RealmTvCreditsRealmProxy.createUsingJsonStream(realm, reader);
                        obj.getRealmTvCredits().add(item);
                    }
                    reader.endArray();
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
        realmObject.setProfilePath(newObject.getProfilePath());
        realmObject.setBackdropPath(newObject.getBackdropPath());
        realmObject.setBiography(newObject.getBiography());
        realmObject.setJobs(newObject.getJobs());

        RealmList<RealmMovieCredits> realmMovieCreditsList = newObject.getRealmMovieCredits();
        if (realmMovieCreditsList != null) {
            RealmList<RealmMovieCredits> realmMovieCreditsRealmList = realmObject.getRealmMovieCredits();
            for (int i = 0; i < realmMovieCreditsList.size(); i++) {
                RealmMovieCredits realmMovieCreditsItem = realmMovieCreditsList.get(i);
                RealmMovieCredits cacherealmMovieCredits = (RealmMovieCredits) cache.get(realmMovieCreditsItem);
                if (cacherealmMovieCredits != null) {
                    realmMovieCreditsRealmList.add(cacherealmMovieCredits);
                } else {
                    realmMovieCreditsRealmList.add(RealmMovieCreditsRealmProxy.copyOrUpdate(realm, realmMovieCreditsList.get(i), update, cache));
                }
            }
        }


        RealmList<RealmTvCredits> realmTvCreditsList = newObject.getRealmTvCredits();
        if (realmTvCreditsList != null) {
            RealmList<RealmTvCredits> realmTvCreditsRealmList = realmObject.getRealmTvCredits();
            for (int i = 0; i < realmTvCreditsList.size(); i++) {
                RealmTvCredits realmTvCreditsItem = realmTvCreditsList.get(i);
                RealmTvCredits cacherealmTvCredits = (RealmTvCredits) cache.get(realmTvCreditsItem);
                if (cacherealmTvCredits != null) {
                    realmTvCreditsRealmList.add(cacherealmTvCredits);
                } else {
                    realmTvCreditsRealmList.add(RealmTvCreditsRealmProxy.copyOrUpdate(realm, realmTvCreditsList.get(i), update, cache));
                }
            }
        }

        return realmObject;
    }

    static RealmActor update(Realm realm, RealmActor realmObject, RealmActor newObject, Map<RealmObject, RealmObjectProxy> cache) {
        realmObject.setName(newObject.getName());
        realmObject.setProfilePath(newObject.getProfilePath());
        realmObject.setBackdropPath(newObject.getBackdropPath());
        realmObject.setBiography(newObject.getBiography());
        realmObject.setJobs(newObject.getJobs());
        RealmList<RealmMovieCredits> realmMovieCreditsList = newObject.getRealmMovieCredits();
        RealmList<RealmMovieCredits> realmMovieCreditsRealmList = realmObject.getRealmMovieCredits();
        realmMovieCreditsRealmList.clear();
        if (realmMovieCreditsList != null) {
            for (int i = 0; i < realmMovieCreditsList.size(); i++) {
                RealmMovieCredits realmMovieCreditsItem = realmMovieCreditsList.get(i);
                RealmMovieCredits cacherealmMovieCredits = (RealmMovieCredits) cache.get(realmMovieCreditsItem);
                if (cacherealmMovieCredits != null) {
                    realmMovieCreditsRealmList.add(cacherealmMovieCredits);
                } else {
                    realmMovieCreditsRealmList.add(RealmMovieCreditsRealmProxy.copyOrUpdate(realm, realmMovieCreditsList.get(i), true, cache));
                }
            }
        }
        RealmList<RealmTvCredits> realmTvCreditsList = newObject.getRealmTvCredits();
        RealmList<RealmTvCredits> realmTvCreditsRealmList = realmObject.getRealmTvCredits();
        realmTvCreditsRealmList.clear();
        if (realmTvCreditsList != null) {
            for (int i = 0; i < realmTvCreditsList.size(); i++) {
                RealmTvCredits realmTvCreditsItem = realmTvCreditsList.get(i);
                RealmTvCredits cacherealmTvCredits = (RealmTvCredits) cache.get(realmTvCreditsItem);
                if (cacherealmTvCredits != null) {
                    realmTvCreditsRealmList.add(cacherealmTvCredits);
                } else {
                    realmTvCreditsRealmList.add(RealmTvCreditsRealmProxy.copyOrUpdate(realm, realmTvCreditsList.get(i), true, cache));
                }
            }
        }
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
        stringBuilder.append("{profilePath:");
        stringBuilder.append(getProfilePath() != null ? getProfilePath() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{backdropPath:");
        stringBuilder.append(getBackdropPath() != null ? getBackdropPath() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{biography:");
        stringBuilder.append(getBiography() != null ? getBiography() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{jobs:");
        stringBuilder.append(getJobs() != null ? getJobs() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{realmMovieCredits:");
        stringBuilder.append("RealmList<RealmMovieCredits>[").append(getRealmMovieCredits().size()).append("]");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{realmTvCredits:");
        stringBuilder.append("RealmList<RealmTvCredits>[").append(getRealmTvCredits().size()).append("]");
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
